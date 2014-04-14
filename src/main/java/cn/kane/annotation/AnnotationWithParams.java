package cn.kane.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AnnotationWithParams {

	//default accessModifer is public
	String string_param();//string is ok
	Class<?> clazz_param();//clazz is ok
	int int_param();//primitive type is ok;Integer is invalid type 
	NewEnum enum_param();//enum is ok
//	AnnotationWithOneParam anno_param();//annotation is ok,how to init?
	String[] string_array_param();//array of valid type is ok
}
