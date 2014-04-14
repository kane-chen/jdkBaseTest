package cn.kane;
public class MethodStackTest {

	static SupportClass support = null;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        support = new SupportClass() ;
        new Thread(new Runnable(){
    			public void run() {
    				support.sayHi();
    			}
        }).start();
        
        new Thread(new Runnable(){
			public void run() {
				support.sayBey();
			}
        	
        }).start();
        
        new Thread(new Runnable(){
			public void run() {
				support.sayHi();
			}
    }).start();
	}

}

class SupportClass{
	
	int instanceVar = 0 ;
	
	public void sayHi(){
		System.out.println(System.currentTimeMillis()+"HI");
//		echoNativeVar(instanceVar);
		echoInstanceVar(this);
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(System.currentTimeMillis()+"Exit");
	}
	public void sayBey(){
		System.out.println(System.currentTimeMillis()+"BEY");
//		echoNativeVar(instanceVar);
		echoInstanceVar(this);
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(System.currentTimeMillis()+"Exit");
	}

	/**
	 * NativeType inputParam,trans Values
	 * @param instanceVar nativeType
	 */
	public void echoNativeVar(int instanceVar){
		System.out.println(instanceVar++);
	}
	
	/**
	 * Instance InputParam,trans Handler
	 * @param instance  instance
	 */
	public void echoInstanceVar(SupportClass instance){
		System.out.println(this.instanceVar++);
	}
}