package cn.kane.sychronized;

import java.util.Date;

public class SyncTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		SyncObject syncObj = new SyncObject();
        new Thread(new CallSyncThread(syncObj)).start();
        try {
        	Thread.sleep(3000);
			syncObj.syncMethod("MAIN");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
class CallSyncThread implements Runnable{

	SyncObject syncObj = null ;
	
	public CallSyncThread (SyncObject syncObj){
		this.syncObj = syncObj ;
	}
	public void run() {
		while(true){
			try {
				syncObj.syncMethod(this.toString());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}

class SyncObject {
	
	public  synchronized void syncMethod(String callerName) throws InterruptedException{
		Thread.sleep(5000);
		System.out.println(new Date().getTime()+"   " +callerName+"========== enter into syncMethod =========");
	}
	
	public void unsyncMethod(){
		System.out.println("********** enter into unsyncMethod **********");
	}
}