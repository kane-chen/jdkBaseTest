package cn.kane.how_use_junit;

import java.math.BigDecimal;

import junit.extensions.TestSetup;
import junit.framework.Test;
import junit.framework.TestResult;
import junit.framework.TestSuite;

public class MyTestSuite extends MyTestCase {

	public MyTestSuite(String methodName){
		super(methodName);
	}
	
	public void testAdd(){
		System.out.println("try to test add");
		BigDecimal additon = new BigDecimal("1.01");
		BigDecimal add = new BigDecimal("2.01");
		assertEquals(3.01, additon.add(add).doubleValue(), 0.1) ;
		//assertEquals(3.01, additon.add(add).doubleValue(), 0.01) ;//delta is 0.01
		System.out.println("test add done");
	}

	public void testSub(){
		BigDecimal subtion = new BigDecimal("1.01");
		BigDecimal sub = new BigDecimal("2.01");
		assertEquals(-1.01, subtion.subtract(sub).doubleValue(), 0.1) ;
		//assertEquals(3.01, additon.add(add).doubleValue(), 0.01) ;//delta is 0.01
	}
	
	@Override
	protected void setUp() throws Exception {
		System.out.println("try to setup before every method's executing");
		super.setUp();
		System.out.println("setup done");
	}
	
	@Override
	protected void tearDown() throws Exception {
		System.out.println("try to teardown before every method's executing");
		super.tearDown();
		System.out.println("teardown done");
	}
	
	public static Test suite(){
		TestSuite suite = new TestSuite() ;
		suite.addTest(new MyTestSuite("testAdd"));
		suite.addTest(new MyTestSuite("testSub"));
		
		TestSetup setupOnTestSuite = new TestSetup(suite){
			@Override
			protected void setUp() throws Exception {
				System.out.println("setup on test");;
			}
			@Override
			protected void tearDown() throws Exception {
				System.out.println("teardown on test");
			}
		};
		return setupOnTestSuite ;
	}
	
	public void testMain(){
		TestResult result = new TestResult();
		MyTestSuite.suite().run(result);
	}
}
