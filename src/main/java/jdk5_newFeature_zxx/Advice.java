package jdk5_newFeature_zxx;

import java.lang.reflect.Method;

public interface Advice {
	void beforeMethod(Method method);
	void afterMethod(Method method);
}
