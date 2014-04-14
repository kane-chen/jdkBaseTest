package cn.kane.inner_class;

public class InnerClassTest implements SayService{

	private String outerParam = "outerParam" ;
	
	static class StaticInnerClass{
		static String className = "static-inner-class" ;
		String instanceParam = "instance-param-of-static-inner-class";
		
		protected static void sayHi(){//the same package & child
			System.out.println(className+":say Hi");
		}
	}
	
	class InstanceInnerClass{
		//static String className = "instance-inner-class";
		//static{
		//	System.out.println("hello");
		//}
		//can not define static parameter & block
		String className = "instance-inner-class";
		String instanceParam = "instance-param-of-instance-inner-class" ;
		void sayHi(){//the same package
			System.out.println(className+":say Hi");
			System.out.println(InnerClassTest.this.outerParam);
			System.out.println(outerParam);
			callHost(InnerClassTest.this);//POINT: could not use this(this is InstanceInnerClass),must use InnerClassTest.this
		}
		
		void callHost(SayService host){
			host.sayHi("callHost");
		}
	}
	
	void testLocalInnerClass(){
		class LocalInnerClass{
			String className = "local-inner-class" ;
			//public String instanceparam = "instance-param-of-local-inner-class" ;//public/private meaningful?
			void sayHi(){
				System.out.println(className+":say Hi");
			}
		}
		new LocalInnerClass().sayHi();
	}
	
	void testAnonymousInnerClass(){
		String className = "anonymous-inner-class" ;
		new SayService() {
			public void sayHi(String callerName) {
				System.out.println(callerName+":say Hi");
			}
		}.sayHi(className);
	}
	public static void main(String[] args) {
        InnerClassTest.StaticInnerClass.sayHi();
        InnerClassTest entrance = new InnerClassTest() ;
        InnerClassTest.InstanceInnerClass instanceInnerClass =entrance.new InstanceInnerClass();//POINT:OuterClass.InnerClass instanceOfInnerClass = instanceOfOuter.new InnerClass();
        instanceInnerClass.sayHi() ;
        entrance.testLocalInnerClass();
        entrance.testAnonymousInnerClass();
	}

	public void sayHi(String callerName) {
		System.out.println(callerName+":"+outerParam);
	}

}

interface SayService{
	void sayHi(String callerName);
}