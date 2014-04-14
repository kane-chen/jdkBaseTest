package cn.kane.sync;

public class ModifierSync {

	public static void main(String[] args) throws InterruptedException {
         TestThread test = new TestThread();
         System.out.println("Before setValue:"+test.getValue());
         test.start();
         Thread.sleep(5);
         System.out.println("After setValue:");
         System.out.println(test.getValue());
         System.out.println(test.getSyncIntVar());
         Thread.sleep(1000);
         System.out.println("After 1 second:");
         System.out.println(test.getValue());
         System.out.println(test.getSyncIntVar());
	}

}

class TestThread extends Thread{
	int intVar = -1 ;
	volatile int syncIntVar = -1;
	
	
	public void setValue(int value){
		intVar = value ;//here,not write value into intVal
		syncIntVar = value ;//here,not write value into syncIntVar
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//this method finished,value will be write into intVal,syncIntVar
	}
	
	public String getValue(){
		return "[intVar=" + intVar+"] " ;
	}
	
	public String getSyncIntVar(){
		return "[syncIntVar=" + syncIntVar+"] " ;
	}
	
	@Override
	public void run(){
		intVar = 20 ;//will not write 20 into intVar
		syncIntVar = 30 ;//will notice syscIntVar,write 30 into syncIntVar
		setValue(100);
	}
}