package com.campuslink.eventplugin

import com.android.build.api.transform.DirectoryInput
import com.android.build.api.transform.Format
import com.android.build.api.transform.JarInput
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformException
import com.android.build.api.transform.TransformInput
import com.android.build.api.transform.TransformInvocation
import com.android.build.api.transform.TransformOutputProvider
import com.android.build.gradle.AppExtension
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.builder.model.ClassField
import com.android.utils.FileUtils
import javassist.ClassPool
import javassist.CtClass
import javassist.CtField
import javassist.CtMethod
import org.gradle.api.Project

import java.util.jar.JarEntry
import java.util.jar.JarFile
import java.util.jar.JarInputStream
import java.util.jar.JarOutputStream
import java.util.jar.Manifest
import java.util.zip.ZipEntry

class TransformLogic extends Transform {

    Project mProject
    static ClassPool mClassPool = ClassPool.getDefault()
    List<File> mClassPaths
    String[] modules = null
    String applicationId = ""
    String applicationEntry = ""
    String outPutPath = ""
    TransformOutputProvider mOutputProvider;
    byte[] mBytesCache = new byte[1024];

    TransformLogic(Project project) {
        mProject = project
        AppExtension android = project.extensions.getByType(AppExtension)
        Map<String, ClassField> buildConfigs = android.getBuildTypes().getAt(0).getBuildConfigFields();
        ClassField classField = buildConfigs.get("Modules")
        println("modules========" + classField.value)
        modules = classField.value.replace("\"", "").split(";")
        applicationId = android.getDefaultConfig().getApplicationId()
        applicationEntry = applicationId + ".App"
        mClassPaths = new ArrayList<>();
        List<File> classPaths = android.getBootClasspath()
        for (File file : classPaths) {
            mClassPool.insertClassPath(file.getAbsolutePath())
        }
    }

    @Override
    String getName() {
        return "TransformLogic"
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation)
        mOutputProvider = transformInvocation.outputProvider;
        dealInputs(transformInvocation.getInputs())

        registeLogicAndRouterInApp()

        //createLogicAndRouterMap()


        //此处需要把操作好的文件拷贝到原来的地方
        for (TransformInput item : transformInvocation.getInputs()) {
            copyDirectoryInputs(item.getDirectoryInputs())
            copyJarInputs(item.getJarInputs())
        }
    }

    private void registeLogicAndRouterInApp(){
        CtClass ctClass = mClassPool.getCtClass(applicationEntry)
        if (ctClass.isFrozen()) {
            ctClass.defrost()
        }

        CtMethod ctMethod = ctClass.getDeclaredMethod("onCreate")

        for (String moduleName : modules) {
            try {
                ctMethod.insertAfter("com.event_filter.logics." + moduleName + "LogicMap.registe();")
            }catch (Exception e){
                e.printStackTrace();
            }
            try {
                ctMethod.insertAfter("com.router.urls." + moduleName + "RouterMap.registe();")
            }catch (Exception e){
                e.printStackTrace()
            }
        }
        ctClass.writeFile(outPutPath)
        ctClass.detach()
    }

    private void createLogicAndRouterMap(File jarPath){
        //1.在jar同级目录下解压jar
        File unJar = new File(jarPath.getParent() + File.separator + jarPath.getName().split("\\.jar")[0])
        if(!unJar.exists()){
            unJar.mkdirs()
        }
        unJarFile(jarPath, unJar)
        CtClass logicMapClass = mClassPool.getCtClass("com.campus.event_filter.logic.LogicMap")

        if (logicMapClass.isFrozen()) {
            logicMapClass.defrost()
        }

        for (String moduleName : modules) {
            try {
                CtClass modulelogicMapClass = mClassPool.getCtClass("com.event_filter.logics." + moduleName + "LogicMap");
                if (modulelogicMapClass.isFrozen()) {
                    modulelogicMapClass.defrost()
                }
                CtField[] ctFields = modulelogicMapClass.getDeclaredFields();
                for(CtField ctField : ctFields){
                    CtField newField = new CtField(ctField.getType(), ctField.getName(), logicMapClass)
                    newField.setModifiers(ctField.getModifiers())
                    logicMapClass.addField(newField, "\""+ctField.getName()+"\"")
                }
                modulelogicMapClass.detach()
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        logicMapClass.writeFile(unJar.getAbsolutePath())
        logicMapClass.detach()

        createJar(unJar, jarPath)
    }

    private void dealInputs(Collection<TransformInput> inputs) {
        for (TransformInput item : inputs) {
            dealDirectoryInputs(item.getDirectoryInputs())
            dealJarInputs(item.getJarInputs())
        }
    }

    private void dealDirectoryInputs(Collection<DirectoryInput> inputs) {
        for (DirectoryInput item : inputs) {
            File file = item.getFile()
            mClassPool.insertClassPath(file.getAbsolutePath())
            outPutPath = file.getAbsolutePath();
        }
    }

    private void copyDirectoryInputs(Collection<DirectoryInput> inputs){
        for (DirectoryInput input : inputs) {
            File outFile = mOutputProvider.getContentLocation(input.name,
                    input.contentTypes, input.scopes, Format.DIRECTORY)
            FileUtils.copyDirectory(input.file, outFile)
        }
    }

    private void copyJarInputs(Collection<JarInput> inputs){
        for (JarInput input : inputs) {
            File outFile = mOutputProvider.getContentLocation(input.name,
                    input.contentTypes, input.scopes, Format.JAR)
            FileUtils.copyFile(input.file, outFile)
        }
    }

    private void dealJarInputs(Collection<JarInput> inputs) {
        for (JarInput item : inputs) {
            File file = item.getFile()
            mClassPool.insertClassPath(file.getAbsolutePath())
            /*JarFile jarFile = new JarFile(file)
            JarEntry jarEntry = jarFile.getJarEntry("com/campus/event_filter/logic/LogicMap.class")

            println("jarEntry:" + jarEntry)
            if(jarEntry != null){
                createLogicAndRouterMap(file)
            }*/
        }
    }


    //解压jar
    private void unJarFile(File src , File desDir) throws FileNotFoundException, IOException{
        JarInputStream jarIn = new JarInputStream(new BufferedInputStream(new FileInputStream(src)));
        if(!desDir.exists()){
            desDir.mkdirs()
        }
        byte[] bytes = new byte[1024];

        while(true){
            JarEntry entry = jarIn.getNextJarEntry();
            if(entry == null){
                break
            }

            File desTemp = new File(desDir.getAbsolutePath() + File.separator + entry.getName());

            if(entry.isDirectory()){    //jar条目是空目录
                if(!desTemp.exists()){
                    desTemp.mkdirs()
                }
            }else{    //jar条目是文件
                if(!desTemp.getParentFile().exists()){
                    desTemp.getParentFile().mkdirs()
                }
                if(!desTemp.exists()){
                    desTemp.createNewFile()
                }
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(desTemp));
                int len = jarIn.read(bytes, 0, bytes.length);
                while(len != -1){
                    out.write(bytes, 0, len);
                    len = jarIn.read(bytes, 0, bytes.length);
                }

                out.flush()
                out.close()
            }
            jarIn.closeEntry()
        }

        //解压Manifest文件
        Manifest manifest = jarIn.getManifest();
        if(manifest != null){
            File manifestFile = new File(desDir.getAbsoluteFile()+File.separator+JarFile.MANIFEST_NAME);
            if(!manifestFile.getParentFile().exists())manifestFile.getParentFile().mkdirs();
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(manifestFile));
            manifest.write(out);
            out.close();
        }

        //关闭JarInputStream
        jarIn.close()
    }

    private void createJar(File src, File des){
        des.delete()
        des.createNewFile()
        JarOutputStream stream=new JarOutputStream(new FileOutputStream(des))
        for(File file : src.listFiles()){
            writeToStream(stream, file, "")
        }
        stream.flush()
        stream.close()
    }

    private void writeToStream(JarOutputStream stream, File file, String parentPath){
        if(file.isDirectory()){
            println("=========>" + parentPath + file.getName())
            for(File child : file.listFiles()){
                writeToStream(stream, child, parentPath + file.getName() + "/")
            }
        }else{
            println("=========>" + parentPath + file.getName())
            JarEntry jarEntry = new JarEntry(parentPath + file.getName())
            stream.putNextEntry(jarEntry)
            BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file))

            int len = inputStream.read(mBytesCache, 0, mBytesCache.length)
            while(len != -1){
                stream.write(mBytesCache, 0, len)
                len = inputStream.read(mBytesCache, 0, mBytesCache.length)
            }

            inputStream.close()
        }
    }
}