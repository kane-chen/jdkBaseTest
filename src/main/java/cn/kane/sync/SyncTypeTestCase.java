package cn.kane.sync;

import junit.framework.TestCase;

public class SyncTypeTestCase extends TestCase {

	private boolean isRunning = true ;
	
	public void testInstanceVar() throws InterruptedException{
		Thread thread1 = new Thread(new Runnable(){
			public void run() {
				while(isRunning){
					System.out.println(Thread.currentThread().getName()+" is Running");
				}
				System.out.println(Thread.currentThread().getName()+" exit");
			}
		},"instanceVarThread");
		thread1.start();
		Thread.sleep(1000);
		isRunning = false ;
	}

//	__________________________________________________________________

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}
	
	public void testMethod() throws InterruptedException{
		Thread thread1 = new Thread(new Runnable(){
			public void run() {
				while(isRunning()){
					System.out.println(Thread.currentThread().getName()+" is Running");
				}
				System.out.println(Thread.currentThread().getName()+" exit");
			}
		},"instanceVarThread");
		thread1.start();
		Thread.sleep(1000);
		setRunning(false) ;
	}

//___________________________________________________________
	
	public synchronized boolean isRunningBySync() {
		return isRunning;
	}

	public synchronized void setRunningBySync(boolean isRunning) {
		this.isRunning = isRunning;
	}
	
	public void testMethodInSync() throws InterruptedException{
		Thread thread1 = new Thread(new Runnable(){
			public void run() {
				while(isRunningBySync()){
					System.out.println(Thread.currentThread().getName()+" is Running");
				}
				System.out.println(Thread.currentThread().getName()+" exit");
			}
		},"instanceVarThread");
		thread1.start();
		Thread.sleep(1000);
		setRunningBySync(false);//setRunning(false) ;//it can work?
	}
	
//	_________________________________________________________
	
	private volatile boolean  lastRunningFlag = true ;
	
	public void testVolatileVar() throws InterruptedException{
		Thread thread = new Thread(new Runnable(){
			public void run(){
				while(lastRunningFlag){
					System.out.println(Thread.currentThread().getName()+" is running");
				}
				System.out.println(Thread.currentThread().getName()+" exit");
			}
		},"volatileVarThread");
		thread.start();
		Thread.sleep(1000);
		lastRunningFlag = false ;
	}
	
//  ________________________________________________________
	
	private volatile int identity = 0 ;
	
	public int nextidentity(){
		return identity ++ ; // "++" is not atomic operate
	}

	Runnable untomic = new Runnable(){
		public void run() {
			String thdName = Thread.currentThread().getName() ;
			while(identity	< 100){
				System.out.println(thdName +" : " + nextidentity());
			}
		}
	};
	public void testVolatileVarInUnAtomic(){
		Thread thread1 = new Thread(untomic,"THD-"+1);
		Thread thread2 = new Thread(untomic,"THD-"+2);
		thread1.start();
		thread2.start();
	}
	
}
