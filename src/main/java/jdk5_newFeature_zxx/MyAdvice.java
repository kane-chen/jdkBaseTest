package jdk5_newFeature_zxx;

import java.lang.reflect.Method;

public class MyAdvice implements Advice {
	long beginTime = 0;
	public void afterMethod(Method method) {
		System.out.println("after method notify");		
		long endTime = System.currentTimeMillis();
		System.out.println(method.getName() + " running time of " + (endTime - beginTime));

	}

	public void beforeMethod(Method method) {
		System.out.println("before method notify");
		beginTime = System.currentTimeMillis();
	}

}
