package com.campus.william.annotationprocessor.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface ModuleDoc {
    String moduleName();
    String moduleDesc();
    int version();
    String versionName();
    boolean hasLogicDoc() default true;
    boolean hasRouterDoc() default true;
}
