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
import javassist.bytecode.AnnotationsAttribute
import javassist.bytecode.AttributeInfo
import javassist.bytecode.annotation.Annotation
import org.gradle.api.Project

class TransformLogic extends Transform {

    Project mProject
    static ClassPool mClassPool = ClassPool.getDefault()
    List<File> mClassPaths
    String[] modules = null
    String applicationId = ""
    String applicationEntry = ""
    String outPutPath = ""
    TransformOutputProvider mOutputProvider;

    TransformLogic(Project project) {
        mProject = project
        AppExtension android = project.extensions.getByType(AppExtension)
        Map<String, ClassField> buildConfigs = android.getBuildTypes().getAt(0).getBuildConfigFields();
        ClassField classField = buildConfigs.get("Modules")
        println("modules========" + classField.value)
        modules = classField.value.replace("\"", "").split(";")
        applicationId = android.getDefaultConfig().getApplicationId()
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



        //此处需要把操作好的文件拷贝到原来的地方
        for (TransformInput item : transformInvocation.getInputs()) {
            copyDirectoryInputs(item.getDirectoryInputs())
            copyJarInputs(item.getJarInputs())
        }
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
            println("dealDirectoryInputs:" + file.getAbsolutePath())
            dealDirectory(file, file.getAbsolutePath())
        }
    }

    private void copyDirectoryInputs(Collection<DirectoryInput> inputs){
        for (DirectoryInput input : inputs) {
            File outFile = mOutputProvider.getContentLocation(input.name,
                    input.contentTypes, input.scopes, Format.DIRECTORY)
            println("director out:" + outFile.getAbsolutePath())
            FileUtils.copyDirectory(input.file, outFile)
        }
    }

    private void copyJarInputs(Collection<JarInput> inputs){
        for (JarInput input : inputs) {
            File outFile = mOutputProvider.getContentLocation(input.name,
                    input.contentTypes, input.scopes, Format.JAR)
            println("jar out:" + outFile.getAbsolutePath())
            FileUtils.copyFile(input.file, outFile)
        }
    }

    private void dealDirectory(File file, String packageName) {
        mClassPool.insertClassPath(file.getAbsolutePath())
        if (file.isDirectory()) {
            File[] files = file.listFiles()
            for (File item : files) {
                dealDirectory(item, packageName)
            }
        } else {
            if (file.getAbsolutePath().endsWith("App.class")) {
                applicationEntry = applicationId + ".App"
                outPutPath = packageName

                println("outPutPath:" + outPutPath + " applicationEntry:" + applicationEntry)
            }
        }
    }

    private void dealJarInputs(Collection<JarInput> inputs) {
        for (JarInput item : inputs) {
            File file = item.getFile()
            mClassPool.insertClassPath(file.getAbsolutePath())
        }
    }
}