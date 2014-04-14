package cn.kane.sychronized;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class RetranLock extends Thread {

	private int bussId = -1 ;
	private ReentrantLock lock = null ;
	
	public RetranLock(int bussId , ReentrantLock lock){
		this.bussId = bussId ;
		this.lock = lock ;
	}
	
	@Override
	public void run(){
		lock.lock();
		System.out.println(System.currentTimeMillis()+" Thread["+bussId+"] got lock");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		lock.unlock();
	}
	
	public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(5);
		
		ReentrantLock lock = new ReentrantLock();
        for(int i=0 ; i<8;i++){
        	RetranLock relock = new RetranLock(i,lock) ;
        	service.execute(relock);
        }
//        if(lock.isHeldByCurrentThread()){
//        	service.shutdownNow();
//        }else{
//        	try {
//				lock.lockInterruptibly();
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//        	service.shutdownNow();
//        }
	}

}
