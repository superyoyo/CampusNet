package com.campus.william.annotationprocessor;

import com.campus.william.annotationprocessor.annotation.ModuleDoc;
import com.campus.william.annotationprocessor.constant.Constants;
import com.campus.william.annotationprocessor.manager.DocManager;
import com.google.auto.service.AutoService;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_7)
@SupportedAnnotationTypes(Constants.ModuleDoc)
public class ModuleDocProcessor extends AbstractProcessor{
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(ModuleDoc.class);
        if(elements == null || elements.isEmpty()){
            return false;
        }

        for(Element element : elements){
            ModuleDoc moduleDoc = element.getAnnotation(ModuleDoc.class);
            DocManager.getInstance().setModuleDoc(moduleDoc);
        }
        DocManager.getInstance().build();
        return true;
    }
}
