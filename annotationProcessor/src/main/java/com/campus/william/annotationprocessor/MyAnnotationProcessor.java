package com.campus.william.annotationprocessor;

import com.campus.william.annotationprocessor.annotation.AutoLogicMap;
import com.campus.william.annotationprocessor.annotation.LogicUrl;
import com.campus.william.annotationprocessor.contant.Constants;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_7)
@SupportedAnnotationTypes({Constants.LogicUrl})
public class MyAnnotationProcessor extends AbstractProcessor{
    private final String CLASS = "LogicMap";
    private final String MODULE_NAME = "moduleName";
    private final String PACKAGE_NAME = "packageName";
    private String moduleName = "";
    private String packageName = "";
    private Filer mFiler;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mFiler = processingEnvironment.getFiler();
        Map<String, String> options = processingEnv.getOptions();
        if (options != null && !options.isEmpty()) {
            moduleName = options.get(MODULE_NAME);
            packageName = options.get(PACKAGE_NAME);
        }
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment) {

        if(moduleName == null || moduleName.trim().equals("")
                || packageName == null || packageName.trim().equals("")){
            return false;
        }

        Set<? extends Element> routeElements = roundEnvironment.getElementsAnnotatedWith(LogicUrl.class);

        if (routeElements == null || routeElements.isEmpty()) {
            return false;
        }

        MethodSpec registeMethod = null;
        MethodSpec.Builder builder = MethodSpec.methodBuilder("registe")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(void.class);


        for(Element item : routeElements){
            //注解的值
            String annotationValue = item.getAnnotation(LogicUrl.class).url();
            try {
                builder.addStatement("$T.getInstance().registeLogic($S, $T.class)",
                        ClassName.get("com.campus.event_filter.logic", "LogicFactory"),
                        annotationValue , ClassName.get((TypeElement) item));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        registeMethod = builder.build();

        AnnotationSpec annotationSpec = AnnotationSpec.builder(AutoLogicMap.class)
                .addMember("fullName", "$S", packageName + "." + moduleName + CLASS)
                .build();

        TypeSpec logicMap = TypeSpec.classBuilder(moduleName + CLASS)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(registeMethod)
                .addAnnotation(annotationSpec)
                .build();

        JavaFile javaFile = JavaFile.builder(packageName, logicMap)
                .build();
        try {
            javaFile.writeTo(mFiler);
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

}
