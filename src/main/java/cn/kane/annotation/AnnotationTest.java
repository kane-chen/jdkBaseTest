package cn.kane.annotation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import junit.framework.TestCase;

class WithoutParam {
	@AnnotationWithoutParams
	public void testWithoutParam() {
		Method[] methods = this.getClass().getDeclaredMethods();
		for (Method method : methods) {
				if (method.isAnnotationPresent(AnnotationWithoutParams.class))
				System.out.println("TRUE");
		}
	}
}

class WithOneParam {
	@AnnotationWithOneParam("kane@2013 copyright")
	public String param = "test";

	public void testWithOneParam() throws SecurityException,
			NoSuchFieldException {
		Field field = this.getClass().getDeclaredField("param");
		if(null!=field){
			AnnotationWithOneParam anno =  field.getAnnotation(AnnotationWithOneParam.class);
			System.out.println(anno.value());
		}
		
	}
}

@AnnotationWithParams(
		string_param = "String", 
        clazz_param = WithParams.class,
        int_param = 1,
        enum_param = NewEnum.TYPE2,
        string_array_param = {"str1", "str2" }
		)
class WithParams {
     public void testParams(){
    	 AnnotationWithParams anno = this.getClass().getAnnotation(AnnotationWithParams.class);
    	 System.out.println(anno.string_param());
    	 System.out.println(anno.clazz_param().getName());
    	 System.out.println(anno.int_param());
    	 NewEnum nenum = anno.enum_param();
    	 System.out.println(nenum.valueOf());
    	 String[] strArray = anno.string_array_param() ; 
    	 String strings = "" ;
    	 for(String str : strArray){
    		 strings += str+"," ;
    	 }
    	 System.out.println(strings);
     }
}

public class AnnotationTest extends TestCase {
	public void testOneParam() throws SecurityException, NoSuchFieldException {
//		new WithoutParam().testWithoutParam();
		new WithOneParam().testWithOneParam();
//		new WithParams().testParams();
	}
}
