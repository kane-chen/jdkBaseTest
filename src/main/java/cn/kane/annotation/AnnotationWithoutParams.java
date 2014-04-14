package cn.kane.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
//just for tag some type of clazz or method or param
//such as threadSafely
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AnnotationWithoutParams {

}
