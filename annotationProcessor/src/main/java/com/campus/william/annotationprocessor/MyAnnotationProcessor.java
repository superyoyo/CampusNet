package com.campus.william.annotationprocessor;

import com.campus.william.annotationprocessor.annotation.Action;

import java.io.Writer;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

public class MyAnnotationProcessor extends AbstractProcessor{
    private final String PACKAGE = "com.campus.william.user";
    private final String CLASS = "LogicMap";
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment) {
        System.out.println("roundEnvironment" + roundEnvironment.toString());
        StringBuilder builder = new StringBuilder();
        builder.append("package " + PACKAGE + ";\n\n");
        builder.append("import com.campus.event_filter.logic.LogicFactory;\n\n");

        builder.append("public class " + CLASS + " {");
        builder.append("    public " + CLASS + " (){");

        for(Element item : roundEnvironment.getElementsAnnotatedWith(Action.class)){
            TypeElement enclosingElement = (TypeElement) item.getEnclosingElement();
            //所在类名
            String className = enclosingElement.getSimpleName().toString();
            //所在类全名
            String classFullName = enclosingElement.getQualifiedName().toString();
            //注解的值
            String annotationValue = item.getAnnotation(Action.class).value();
            System.out.println("className:" + classFullName + "  Action:" + annotationValue);
            builder.append("LogicFactory.getInstance().registeLogic(" + annotationValue + ", "+ classFullName+ ".class)");
        }

        builder.append("    }");
        builder.append("}");

        try {
            JavaFileObject source = processingEnv.getFiler().createSourceFile(PACKAGE + "." + CLASS);
            Writer writer = source.openWriter();
            writer.write(builder.toString());
            writer.flush();
            writer.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(Action.class.getCanonicalName());
        return types;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
}
