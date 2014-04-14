package cn.kane.concurrent.sync_tools;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;


import org.apache.log4j.Logger;

import junit.framework.TestCase;

public class CyclicBarrierTest extends TestCase {

	private static final Logger log = Logger.getLogger(CyclicBarrierTest.class) ;
	private final int TOTAL_SIZE = 5 ;
	
	public void testCyclicBarrierWithTourAsExample(){
		log.info("try to start a tour-example to show how to use cyclic-barrier");
		final AtomicBoolean running = new AtomicBoolean(true) ;
		final CyclicBarrier barrier = new CyclicBarrier(TOTAL_SIZE,new Runnable(){
			public void run() {
				log.info("we are all here");
			}
		}) ;
		
		for(int i=1;i<=TOTAL_SIZE;i++){
			new Thread(new Runnable(){
				public void run() {
					String threadName = Thread.currentThread().getName();
					while(running.get()){
						try {
							Thread.sleep(new Random().nextInt(4000));
							log.info("1ST::"+threadName+" is arried,waitings is "+barrier.getNumberWaiting()+",left is "+ (barrier.getParties()-barrier.getNumberWaiting()-1));
							barrier.await() ;
							log.info("everyone is here,let's play 1st tour within 5 seconds");
							Thread.sleep(5000);
							log.info("we will goto 2nd,now wait for person's back");
							Thread.sleep(new Random().nextInt(3000));
							log.info("2ND::"+threadName+" is arried,waitings is "+barrier.getNumberWaiting()+",left is "+ (barrier.getParties()-barrier.getNumberWaiting()-1));
							barrier.await() ;
							log.info("everyone is here,let's play 2nd tour within 5 seconds");
							Thread.sleep(5000);
							log.info("we will goback,now wait for person's back");
							Thread.sleep(new Random().nextInt(3000));
							log.info("BACK::"+threadName+" is arried,waitings is "+barrier.getNumberWaiting()+",left is "+ (barrier.getParties()-barrier.getNumberWaiting()-1));
							barrier.await() ;
							
						} catch (InterruptedException e) {
							log.error(e);
						} catch (BrokenBarrierException e) {
							log.error(e);
						}
					}
				}
			},"PERSON-"+i).start();
		}
		
		try {
			Thread.sleep(60000);
			running.set(false) ;
		} catch (InterruptedException e) {
			log.error(e);
		}
	}
	
	public void testSimpleCyclicBarrier(){
		log.info("try to test simple cyclic-barrier");
		
		final AtomicBoolean isRunning = new AtomicBoolean(true) ;
		final CyclicBarrier cycBarrier = new CyclicBarrier(TOTAL_SIZE);

		for(int i=1 ; i<=TOTAL_SIZE ;i++){
			new Thread(new Runnable(){
				public void run(){
					String threadName = Thread.currentThread().getName() ;
					log.debug(threadName + " is running");
					while(isRunning.get()){
						try{
							Thread.sleep(new Random().nextInt(5000));
							log.info(System.currentTimeMillis()+" : "+threadName + " arrived,waitings is "+cycBarrier.getNumberWaiting()+",total-size is "+cycBarrier.getParties()) ;
							int arrivedIndex = cycBarrier.await();
							log.info("arrived-index is "+arrivedIndex);
							log.info(System.currentTimeMillis()+" : "+threadName + " notify,everyone is here.") ;
						}catch(Exception e){
							log.error(e) ;
						}
					}
				}
			},"THRD-"+i).start();
		}
		
		try {
			Thread.sleep(20000) ;
			isRunning.set(false) ;
		} catch (InterruptedException e) {
			log.error(e) ;
		}
	}
	
	public void testBarrierRunnable(){
		log.info("try to start test barrier-runnable in cyclic-barrier");
		
		final AtomicBoolean isRunning = new AtomicBoolean(true) ;
		final CyclicBarrier cycBarrier= new CyclicBarrier(TOTAL_SIZE,new Runnable(){
			public void run() {
				log.info(System.currentTimeMillis()+":"+" everyone is here,let's do something together.");
			}
		});
		
		for(int i=1;i<=TOTAL_SIZE;i++){
			new Thread(new Runnable(){
				public void run(){
					String threadName = Thread.currentThread().getName() ;
					log.info(threadName + " is running");
					while(isRunning.get()){
						try {
							Thread.sleep(new Random().nextInt(3000)) ;
							log.info(System.currentTimeMillis()+" : "+threadName + " is arrived,waitings is "+cycBarrier.getNumberWaiting());
							cycBarrier.await() ;
						} catch (InterruptedException e) {
							log.error(e) ;
						} catch (BrokenBarrierException e) {
							log.error(e) ;
						}
					}
				}
			},"THD-"+i).start();
		}
		
		try {
			Thread.sleep(20000) ;
			isRunning.set(false) ;
		} catch (InterruptedException e) {
			log.error(e) ;
		}
	}
	
	public void testWaitTimeBarrier(){
		log.info("try to start test wait-time in cyclic-barrier");
		final int sleep_time = 5000 ;
		final int wait_time = 3000 ;
		
		final AtomicBoolean isRunning = new AtomicBoolean(true) ;
		final CyclicBarrier cycBarrier = new CyclicBarrier(TOTAL_SIZE,new Runnable(){
			public void run(){
				//if barrier was broken , the runnable could not be run .
				log.info(Thread.currentThread().getName()+"&"+System.currentTimeMillis()+":"+" run barrier-runnable.");
			}
		});
		
		for(int i=0;i<=TOTAL_SIZE;i++){
			new Thread(new Runnable(){
				public void run() {
					String threadName = Thread.currentThread().getName() ;
					log.info(threadName + " is running");
					while(isRunning.get()){
						try {
							Thread.sleep(new Random().nextInt(sleep_time));
							log.info(System.currentTimeMillis()+" : "+threadName + " is arrived,waitings is "+cycBarrier.getNumberWaiting());
							int arrivedIndex = cycBarrier.await(wait_time, TimeUnit.MILLISECONDS);
							log.info("arrived-index is "+arrivedIndex);
						} catch (InterruptedException e) {
							log.error(e) ;
						} catch (BrokenBarrierException e) {
							log.error(threadName +":cycBarrier was broken,as :"+e);
						} catch (TimeoutException e) {
							log.error(threadName +":cycBarrier wait time out,as :" + e) ;
							assertTrue(cycBarrier.isBroken());
						}
						log.info(threadName +" do something after await");
					}
				}
			},"THD-"+i).start();
		}
		
		try {
			Thread.sleep(20000) ;
			isRunning.set(false) ;
		} catch (InterruptedException e) {
			log.error(e) ;
		}
	}
}
