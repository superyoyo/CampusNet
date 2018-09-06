package com.campus.william.annotationprocessor;

import com.campus.william.annotationprocessor.annotation.ActivityUrl;
import com.campus.william.annotationprocessor.annotation.PageUrl;
import com.campus.william.annotationprocessor.constant.Constants;
import com.campus.william.annotationprocessor.manager.DocManager;
import com.google.auto.service.AutoService;
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
@SupportedAnnotationTypes({Constants.PageUrl, Constants.ActivityUrl})
public class RouterAnnotationProcessor extends AbstractProcessor{
    private final String CLASS = "RouterMap";
    private final String MODULE_NAME = "moduleName";
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

        Set<? extends Element> pageEmelements = roundEnvironment.getElementsAnnotatedWith(PageUrl.class);
        Set<? extends Element> activityEmelements = roundEnvironment.getElementsAnnotatedWith(ActivityUrl.class);

        if ((pageEmelements == null || pageEmelements.isEmpty()
                && (activityEmelements == null || activityEmelements.isEmpty()))) {
            DocManager.getInstance().pageUrlsDone();
            DocManager.getInstance().activityUrlsDone();
            DocManager.getInstance().build();
            return false;
        }

        TypeSpec.Builder typeBuilder = TypeSpec.classBuilder(moduleName + CLASS)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL);

        MethodSpec registeMethod = null;
        MethodSpec.Builder builder = MethodSpec.methodBuilder("registe")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(void.class);

        if(pageEmelements != null && pageEmelements.size() > 0){
            for(Element item : pageEmelements){
                //注解的值
                PageUrl pageUrl = item.getAnnotation(PageUrl.class);
                String state = pageUrl.state();
                String desc = pageUrl.desc();
                try {
                    builder.addStatement("$T.getInstance().registeFragment($T.class, $S, $S)",
                            ClassName.get("com.campus.william.router.logic", "RouterFactory"),
                            ClassName.get((TypeElement) item), state, desc);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                DocManager.getInstance().addPageUrl(pageUrl);
            }
        }

        DocManager.getInstance().pageUrlsDone();

        if (activityEmelements != null && activityEmelements.size() > 0) {
            for(Element item : activityEmelements){
                //注解的值
                ActivityUrl activityUrl = item.getAnnotation(ActivityUrl.class);
                String state = activityUrl.url();
                try {
                    builder.addStatement("$T.getInstance().registeActivity($T.class, $S)",
                            ClassName.get("com.campus.william.router.logic", "RouterFactory"),
                            ClassName.get((TypeElement) item), state);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                DocManager.getInstance().addActivityUrl(activityUrl);
            }
        }

        DocManager.getInstance().activityUrlsDone();

        registeMethod = builder.build();

        TypeSpec logicMap = typeBuilder
                .addMethod(registeMethod)
                .build();

        JavaFile javaFile = JavaFile.builder(packageName, logicMap)
                .build();
        try {
            javaFile.writeTo(mFiler);
        }catch (Exception e){
            e.printStackTrace();
        }

        DocManager.getInstance().build();

        return true;
    }
}
