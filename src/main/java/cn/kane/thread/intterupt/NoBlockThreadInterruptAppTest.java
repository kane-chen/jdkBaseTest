package cn.kane.thread.intterupt;

class NoBlockThreadInterruptAppTest extends Thread {

	volatile static boolean isRunnable = true;

	public static void main(String args[]) throws Exception {
		NoBlockThreadInterruptAppTest thread = new NoBlockThreadInterruptAppTest();
		
		System.out.println("try to start thread");
		thread.start();
		Thread.sleep(2000);
		
		System.out.println("try to end thread");
		isRunnable = false ;
		Thread.sleep(2000);
		
		System.exit(0);
	}

	public void run() {

		while (isRunnable) {
			long startTime = System.currentTimeMillis();
			System.out.println("Thread running...");
            while(System.currentTimeMillis()-startTime > 100){
            	//100millis之后，再次执行
            }
		}

		System.out.println("Thread exiting under request...");

	}

}
