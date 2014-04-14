package jdk5_newFeature_zxx ;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
public @interface ItcastAnnotation {
	String color() default "blue";
	String value();
	int[] arrayAttr() default {3,4,4};
	EnumTest.TrafficLamp lamp() default EnumTest.TrafficLamp.RED;
	MetaAnnotation annotationAttr() default @MetaAnnotation("lhm");
}
