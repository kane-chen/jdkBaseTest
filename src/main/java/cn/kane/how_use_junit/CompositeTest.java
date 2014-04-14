package cn.kane.how_use_junit;

import junit.framework.Test;
import junit.framework.TestResult;
import junit.framework.TestSuite;

public class CompositeTest extends MyTestCase {

	public CompositeTest(String methodName) {
		super(methodName);
	}
	
	public Test getSuite(){
		TestSuite suite = new TestSuite();
		suite.addTestSuite(BasicTest.class);
		suite.addTest(MyTestSuite.suite());
		return suite ;
	}
	
	public void testSuite(){
		TestResult result = new TestResult();
		Test test = getSuite();
		System.out.println(test.countTestCases());
		test.run(result);
	}

}
