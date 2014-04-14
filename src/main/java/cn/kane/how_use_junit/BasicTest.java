package cn.kane.how_use_junit;

public class BasicTest extends MyTestCase {

	public BasicTest(String methodName){
		super(methodName) ;
	}
	
	public void testAdd(){
		assertEquals(1+2, 1+2);
	}
	
	public void testSub(){
//		assertEquals("Subtract", 2-1, 1-2);
		assertEquals("Subtract", 1-2, 1-2);
	}
}
