package com.campus.william.annotationprocessor;

import com.campus.william.annotationprocessor.annotation.RouterUrl;
import com.campus.william.annotationprocessor.annotation.StateDesc;
import com.campus.william.annotationprocessor.constant.Constants;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
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
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes({Constants.RouterUrl})
public class RouterAnnotationProcessor extends AbstractProcessor{
    private final String CLASS = "RouterMap";
    private final String MODULE_NAME = "moduleName";
    private final String PACKAGE_NAME = "packageName";
    private String moduleName = "";
    private String packageName = "com.router.urls";
    private Filer mFiler;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mFiler = processingEnvironment.getFiler();
        Map<String, String> options = processingEnv.getOptions();
        if (options != null && !options.isEmpty()) {
            moduleName = options.get(MODULE_NAME);
        }
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment) {

        if(moduleName == null || moduleName.trim().equals("")
                || packageName == null || packageName.trim().equals("")){
            return false;
        }

        Set<? extends Element> routeElements = roundEnvironment.getElementsAnnotatedWith(RouterUrl.class);

        if (routeElements == null || routeElements.isEmpty()) {
            return false;
        }

        TypeSpec.Builder typeBuilder = TypeSpec.classBuilder(moduleName + CLASS)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL);

        TypeSpec.Builder typeUrlBuilder = TypeSpec.classBuilder("States")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL);

        MethodSpec registeMethod = null;
        MethodSpec.Builder builder = MethodSpec.methodBuilder("registe")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(void.class);

        for(Element item : routeElements){
            //注解的值
            String state = item.getAnnotation(RouterUrl.class).state();
            String desc = item.getAnnotation(RouterUrl.class).desc();
            try {
                builder.addStatement("$T.getInstance().registe($T.class, $S, $S)",
                        ClassName.get("com.campus.william.router.logic", "RouterFactory"),
                        ClassName.get((TypeElement) item), state, desc);
            } catch (Exception e) {
                e.printStackTrace();
            }


            /*AnnotationSpec annotationSpec = AnnotationSpec.builder(StateDesc.class)
                    .addMember("desc", "$S", desc)
                    .build();*/

            FieldSpec.Builder fieldBuilder = FieldSpec.builder(String.class,
                    state.replace("/" ,"_"), Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL);
            //fieldBuilder.addAnnotation(annotationSpec);
            fieldBuilder.addJavadoc("$S", desc);
            typeUrlBuilder.addField(fieldBuilder.initializer("$S", state).build());
        }

        registeMethod = builder.build();

        TypeSpec logicMap = typeBuilder
                .addType(typeUrlBuilder.build())
                .addMethod(registeMethod)
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
