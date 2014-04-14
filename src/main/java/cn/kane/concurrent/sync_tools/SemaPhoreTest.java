package cn.kane.concurrent.sync_tools;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;

import junit.framework.TestCase;

public class SemaPhoreTest extends TestCase {

	private static final Logger log = Logger.getLogger(SemaPhoreTest.class) ;
	
	public void testSimpleSemaphore(){
		log.info("try to testSimpleSemaphore") ;
		final Semaphore sema = new Semaphore(3,true);
		
		for(int i =1 ; i<11 ;i ++){
			new Thread(new Runnable(){
				public void run() {
					String threadName = Thread.currentThread().getName() ;
					log.info(threadName+" is running");
					try {
						log.info(threadName+" try to acquire semaphore,left "+sema.availablePermits());
						sema.acquire();
						Thread.sleep(500);
						log.info(threadName+" acquired semaphore,left "+sema.availablePermits());
						sema.release();
						log.info(threadName+" release semaphore,left " + sema.availablePermits());
					} catch (InterruptedException e) {
						log.error(e) ;
					}
				}
			},"THRD-"+i).start();
		}
		
		try {
			Thread.sleep(10000) ;
		} catch (InterruptedException e) {
			log.error(e) ;
		}
	}
	
	public void testCallingNo(){
		final AtomicInteger callingNo = new AtomicInteger(1);
		final Semaphore sema = new Semaphore(3,true) ;
		
		for(int i =1 ; i<11 ;i ++){
			new Thread(new Runnable(){
				public void run() {
					String threadName = Thread.currentThread().getName() ;
					log.info(threadName+" is running");
					while(true){
						try {
							int no = callingNo.getAndIncrement() ;
							log.info(no+" try to acquire semaphore,left "+sema.availablePermits() +",waiting " + sema.getQueueLength());
							sema.acquire();
							Thread.sleep(500);
							log.info(no+" acquired semaphore,left "+sema.availablePermits());
							sema.release();
							log.info(no+" release semaphore,left " + sema.availablePermits());
						} catch (InterruptedException e) {
							log.error(e) ;
						}
					}
				}
			},"THRD-"+i).start();
		}
		
		try {
			Thread.sleep(10000) ;
		} catch (InterruptedException e) {
			log.error(e) ;
		}
	}
}
