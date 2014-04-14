package cn.kane.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//for tag clazz/method/param property
//such as copyright
@Target({ElementType.FIELD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AnnotationWithOneParam {
     // use[value()],when using it,not '=' is ok
     String value();

}
