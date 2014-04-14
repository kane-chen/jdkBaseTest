package cn.kane.thread.intterupt;

public class BlockThreadIntteruptAppTest extends Thread {

	private static boolean flag = true ;  //1.shared FLAG
	
	public static void main(String[] args) {

		try {
			Thread t = new BlockThreadIntteruptAppTest();
			System.out.println("try to start");
			Thread.sleep(1000);
			t.start();
			System.out.println("try to interrupt");
			Thread.sleep(1000);
			flag = false ;
			t.interrupt();// 2.thread.interrupt(),then thread will throw InterruptedException
			System.out.println(System.currentTimeMillis()+" call thread's interrupt ");
			
			Thread.sleep(1000);
			System.out.println("main end");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void run(){
		while(flag){
			try {
				Thread.sleep(500);
				System.out.println("running");
			} catch (InterruptedException e) {
				//3.we can do something (call interrupt() ,it will throw InterruptedException here)
				System.out.println(System.currentTimeMillis() +" was interrupted");
			}
		}
	}
}
