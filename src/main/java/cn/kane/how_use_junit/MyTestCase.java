package cn.kane.how_use_junit;

import junit.framework.TestCase;

public class MyTestCase extends TestCase {

	public MyTestCase(String methodName){
		super(methodName);
	}
	
	public void assertNotNullAndSpace(String message ,Object target){
		//super.assertNotNull(message, target);
		//super.assertEquals(message, "", target);
		if(null == target){
			super.fail(message);
		}else{
			target.equals("");
			super.fail(message);
		}
	}
	
	public void assertNotNullAndSpace(Object target){
		assertNotNullAndSpace("",target);
	}
}
