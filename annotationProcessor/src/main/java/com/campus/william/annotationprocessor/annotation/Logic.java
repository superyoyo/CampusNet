package com.campus.william.annotationprocessor.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.HashMap;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface Logic {
    String action();
    String desc();
    String[] params();
    String[] paramsDesc();
    boolean[] canNull();
    ParamType[] paramsType();
}
