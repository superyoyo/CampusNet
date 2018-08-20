package com.campuslink.eventplugin

import com.android.build.api.transform.DirectoryInput
import com.android.build.api.transform.JarInput
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformException
import com.android.build.api.transform.TransformInput
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.AppExtension
import com.android.build.gradle.internal.pipeline.TransformManager
import javassist.ClassPool
import javassist.CtClass
import javassist.CtField
import javassist.CtMethod
import javassist.bytecode.AnnotationsAttribute
import javassist.bytecode.AttributeInfo
import javassist.bytecode.annotation.Annotation
import org.gradle.api.Project

class TransformLogic extends Transform{

    Project mProject
    ClassPool mClassPool
    TransformLogic(Project project) {
        mProject = project
        AppExtension android = project.extensions.getByType(AppExtension)
        mClassPool = ClassPool.getDefault()
        List<File> classPaths = android.getBootClasspath()
        for(File file : classPaths){
            String classPath = file.getAbsolutePath()
            println("classPath:=======" + classPath)
            mClassPool.insertClassPath(classPath)
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
        dealInputs(transformInvocation.getInputs())
    }

    private void dealInputs(Collection<TransformInput> inputs){
        for(TransformInput item : inputs){
            dealDirectoryInputs(item.getDirectoryInputs())
            dealJarInputs(item.getJarInputs())
        }

        CtClass ctClass = mClassPool.getCtClass("com.campus.campusnet.MainActivity")
        if (ctClass.isFrozen()){
            ctClass.defrost()
        }
        CtMethod ctMethod = ctClass.getDeclaredMethod("onCreate")
        AnnotationsAttribute attribute = (AnnotationsAttribute) ctMethod.getMethodInfo().getAttribute(AnnotationsAttribute.invisibleTag)
        Annotation[] annotations = attribute.getAnnotations()
        for(Annotation item : annotations){
            println("annotation=========" + item.toString())
        }
        ctMethod.insertAfter("System.out.println(\"Hello\");")
        ctClass.writeFile("/Users/william/CampusNet/app/build/intermediates/classes/release")
        ctClass.detach()
    }

    private void dealDirectoryInputs(Collection<DirectoryInput> inputs){
        for(DirectoryInput item : inputs){
            File file = item.getFile()
            dealDirectory(file)
        }
    }

    private void dealDirectory(File file){
        if(file.isDirectory()){
            println("insertClassPath=========" + file.getAbsolutePath())
            mClassPool.insertClassPath(file.getAbsolutePath())
            File[] files = file.listFiles()
            for(File item : files){
                dealDirectory(item)
            }
        }else{
            if(file.getName().equals("MainActivity.class")){
                //mClassPool.importPackage("android.os.Bundle")
                //mClassPool.importPackage("android.support.v7.app.AppCompatActivity")
            }
        }
    }

    private void dealJarInputs(Collection<JarInput> inputs){
        for(JarInput item : inputs){
            File file = item.getFile()
            println("insertClassPath=========" + file.getAbsolutePath())
            mClassPool.insertClassPath(file.getAbsolutePath())
        }
    }
}