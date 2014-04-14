package cn.kane.sychronized;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class Semaphor extends Thread {

	private int custNo = -1 ;
	private Semaphore sema = null ;
	
	public Semaphor(int custNo , Semaphore sema){
		this.custNo = custNo ; 
		this.sema = sema ;
	}

	@Override
	public void run(){
		while(!sema.tryAcquire()){
			System.out.println(" Customer["+custNo+"]:current avaliablePermits="+sema.availablePermits());
			System.out.println("BUSY! Customer["+custNo+"] wait");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		try {
//			sema.acquire();//tryAcquire : queryAvalidPermit>>acquirePermit
			System.out.println("Customer["+custNo+"]  in service");
			Thread.sleep(1500);
			sema.release();
			System.out.println(" Customer["+custNo+"]:After release,avaliablePermits="+sema.availablePermits());
			System.out.println("Customer["+custNo+"] left");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
        
		ExecutorService service = Executors.newFixedThreadPool(5);
		Semaphore semaphore = new Semaphore(3);
        for(int i = 0 ; i<10; i++){
        	Semaphor sem = new Semaphor(i+1,semaphore);
        	service.execute(sem);
        }
        if(!semaphore.hasQueuedThreads()){
        	System.out.println("no customer");
        	semaphore.acquireUninterruptibly(3);
        	System.out.println("Out of service");
            service.shutdownNow();
        	semaphore.release(3);
        }
	}

}
