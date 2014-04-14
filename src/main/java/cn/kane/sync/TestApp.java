package cn.kane.sync;

public class TestApp {

	private static boolean flag = false ;
	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
        Thread thd = new Thread(new Runnable(){
			public void run() {
				// TODO Auto-generated method stub
				while(!flag){
					System.out.println("RUNNING");
				}
				System.out.println("Exit");
			}
        	
        });
        
        thd.start();
        
        Thread.sleep(4000);
        System.out.println("TIME UP");
        flag = true ; }

}
