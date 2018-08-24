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
import com.android.builder.model.ClassField
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
    ClassPool mClassPool
    String[] modules = null;
    String applicationId = "";
    String applicationEntry = "";
    String outPutPath = "";

    TransformLogic(Project project) {
        mProject = project
        mClassPool = ClassPool.getDefault()
        AppExtension android = project.extensions.getByType(AppExtension)
        Map<String, ClassField> buildConfigs = android.getBuildTypes().getAt(0).getBuildConfigFields();
        ClassField classField = buildConfigs.get("Modules")
        println("modules========" + classField.value)
        modules = classField.value.replace("\"", "").split(";")
        applicationId = android.getDefaultConfig().getApplicationId()
        List<File> classPaths = android.getBootClasspath()
        for (File file : classPaths) {
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
        CtClass ctClass = mClassPool.getCtClass(applicationEntry)
        if (ctClass.isFrozen()) {
            ctClass.defrost()
        }

        CtMethod ctMethod = ctClass.getDeclaredMethod("onCreate")

        for (String moduleName : modules) {
            ctMethod.insertAfter("com.event_filter.logics." + moduleName + "LogicMap.registe();")
        }
        ctClass.writeFile(outPutPath)
        ctClass.detach()
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
            dealDirectory(file, file.getAbsolutePath())
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
                outPutPath = packageName;
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