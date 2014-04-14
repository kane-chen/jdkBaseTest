package cn.kane.concurrent.sync_tools;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

import junit.framework.TestCase;

public class ConditionTest extends TestCase {

	private static final Logger log = Logger.getLogger(ConditionTest.class);

	/**
	 * implements function : thd1>thd2>thd3>thd1>thd2......
	 */
	public void testCondition() {
		log.info("testCondition start");
		final AtomicBoolean isRunning = new AtomicBoolean(true);
		final long runMilis = 10000 ;
		
		final Lock lock = new ReentrantLock();
		final Condition cond1 = lock.newCondition(); // only final-temporary-parameter can use in inner-class,because of the final-param's memory-address is not-changed
		final Condition cond2 = lock.newCondition();
		final Condition cond3 = lock.newCondition();

		new Thread(new Runnable() {
			public void run() {
				while (isRunning.get()) {
					lock.lock();
					try {
						cond1.await();
						Thread.sleep(100);
						log.info(Thread.currentThread().getName() + " RUN");
						cond2.signal();
					} catch (InterruptedException e) {
						log.error(e);
					}finally{
						lock.unlock();
					}
				}
			}
		}, "THRD-1").start();

		new Thread(new Runnable() {
			public void run() {
				while (isRunning.get()) {
					lock.lock();
					try {
						cond2.await();
						Thread.sleep(100);
						log.info(Thread.currentThread().getName() + " RUN");
						cond3.signal();
					} catch (InterruptedException e) {
						log.error(e);
					}finally{
						lock.unlock();
					}
				}
			}
		}, "THRD-2").start();

		new Thread(new Runnable() {
			public void run() {
				while (isRunning.get()) {
					lock.lock();
					try {
						cond3.await();
						Thread.sleep(100);
						log.info(Thread.currentThread().getName() + " RUN");
						cond1.signal();
					} catch (InterruptedException e) {
						log.error(e);
					}finally{
						lock.unlock();
					}
				}
			}
		}, "THRD-3").start();

		log.info("sinal thread");
		lock.lock();
		cond2.signal();
		lock.unlock();
		
		
		/**
		 * main thread in JUnit will be shutdown not until delay-time[runMilis]
		 */
		/*
		log.info("START TIMER:"+System.currentTimeMillis());
		new java.util.Timer("TIMER").schedule(new java.util.TimerTask() {
			public void run() {
				log.info("STOP TIMER:"+System.currentTimeMillis());
				isRunning.set(false);
				System.exit(0) ;
			}
		}, runMilis);
		*/
		
		try {
			Thread.sleep(runMilis) ;
			isRunning.set(false);
			System.exit(0) ;
		} catch (InterruptedException e) {
			log.error(e);
		}
	}
	
	public void testProductorConsumer(){
		log.info("test productor&consumer start");
		final AtomicBoolean isRunning = new AtomicBoolean(true);
		final long runMilis = 10000 ;
		
		final int STORAGE_SIZE = 10 ;
		final String[] storage = new String[STORAGE_SIZE] ;
		final AtomicInteger storedAmount = new AtomicInteger(0) ;
		
		final Lock lock = new ReentrantLock() ;
		final Condition isEmpty = lock.newCondition() ;
		final Condition isFull = lock.newCondition() ;
		
		new Thread(new Runnable(){
			public void run() {
				log.info("productor is running");
				while(isRunning.get()){
					lock.lock() ;
					try {
						while(storedAmount.get() >= STORAGE_SIZE)//use [while] ,but not [if] ;is for stop virtual-notify
							isFull.await();
						Thread.sleep(100) ;
						String product = new Random().nextLong()+"" ;
						log.info(Thread.currentThread().getName()+":"+product);
						int amount = storedAmount.getAndIncrement();
						log.debug("amount = "+amount) ;
						storage[amount-1] = product ;
						log.info("storedAmount is "+ storedAmount.get() + " currently") ;
						isEmpty.signal() ;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}finally{
						lock.unlock();
					}
				}
			}
			
		},"Productor").start();
		
		new Thread(new Runnable(){
			public void run() {
				log.info("consumer is running");
				while(isRunning.get()){
					lock.lock();
					try{
						while(storedAmount.get() == 0)//use [while] ,but not [if] ;is for stop virtual-notify
							isEmpty.await();
						Thread.sleep(100) ;
						String product = storage[storedAmount.get()-1] ;
						log.info(Thread.currentThread().getName() + ":" + product);
						int cosumedIndex = storedAmount.decrementAndGet();
						storage[cosumedIndex] = null ;
						log.info("storedAmount is "+ storedAmount.get() + " currently") ;
						isFull.signal() ;
					}catch(Exception e){
						log.error(e);
					}finally{
						lock.unlock();
					}
				}
			}
		},"Consumer").start();
		
		
		try {
			Thread.sleep(runMilis) ;
			isRunning.set(false);
			System.exit(0) ;
		} catch (InterruptedException e) {
			log.error(e);
		}
	}
	
	
	public void testProductorConsumerInClass(){
		log.info("test ProductorConsumerInClass start");
		final AtomicBoolean isRunning = new AtomicBoolean(true);
		final long runMilis = 10000 ;
		
		final StorageUtils storage = new StorageUtils() ;
		
		new Thread(new Runnable(){
			int waitTime = 100 ;
			TimeUnit timeUnit =  TimeUnit.MILLISECONDS ;
			public void run() {
				while(isRunning.get()){
					String product = new Random().nextLong() + "" ;
					storage.storeProduct(product, waitTime, timeUnit) ;
				}
			}
		},"THRD-S-1").start();
		
		new Thread(new Runnable(){
			int waitTime = 200 ;
			TimeUnit timeUnit =  TimeUnit.MILLISECONDS ;
			public void run() {
				while(isRunning.get()){
					storage.consumeProduct( waitTime, timeUnit) ;
				}
			}
		},"THRD-S-1").start();
		
		try {
			Thread.sleep(runMilis) ;
			isRunning.set(false);
			System.exit(0) ;
		} catch (InterruptedException e) {
			log.error(e);
		}
		
	}
}

class StorageUtils{
	private static final Logger logger = Logger.getLogger(StorageUtils.class) ;
	private final int STORAGE_SIZE = 10 ;
	private String[] storage = new String[STORAGE_SIZE] ;
	private AtomicInteger storedAmount = new AtomicInteger(0) ;
	
	private Lock lock = new ReentrantLock() ;
	private Condition isEmpty = lock.newCondition() ;
	private Condition isFull = lock.newCondition() ;
	
	
	
	public boolean storeProduct(String product,long waitTime,TimeUnit timeUnit){
		logger.debug("try to store product-"+product);
		boolean isStored = false ;
		
		lock.lock() ;
		try{
			while(storedAmount.get() >= STORAGE_SIZE){//use [while] ,but not [if] ;is for stop virtual-notify
				if(!isFull.await(waitTime,timeUnit)){
					throw new Exception("storeProduct time out");
				}
				
			}
			Thread.sleep(110);
			logger.debug(Thread.currentThread().getName() +" BEFORE : stored-"+product + " , size is " + storedAmount.get()) ;
			int storedIndex = storedAmount.getAndIncrement();
			storage[storedIndex] = product ;
			isStored = true ;
			logger.debug(Thread.currentThread().getName() +" AFTER : stored-storage["+storedIndex+"]="+product + " , size is " + storedAmount.get()) ;
			isEmpty.signal() ;
		}catch(Exception e){
			logger.error(e);
		}finally{
			lock.unlock() ;
		}
		return isStored ;
	}
	
	public String  consumeProduct(long waitTime,TimeUnit timeUnit){
		logger.debug("try to consume product");
		String product = null ;
		
		lock.lock();
		try{
			while(storedAmount.get() == 0){ //use [while] ,but not [if] ;is for stop virtual-notify
				if(!isEmpty.await(waitTime,timeUnit)){
					throw new Exception("consume-product time out");
				}
			}
			Thread.sleep(110);
			logger.debug(Thread.currentThread().getName() +" BEFORE consume , size is " + storedAmount.get());
			product = storage[storedAmount.get() - 1 ] ;
			int consumedIndex = storedAmount.getAndDecrement() ;
			storage[storedAmount.get()] = null ; 
			logger.debug(Thread.currentThread().getName() +" AFTER consume-"+consumedIndex+"]="+product+", size is " + storedAmount.get());
			isFull.signal() ;
		}catch(Exception e){
			logger.error(e) ;
		}finally{
			lock.unlock() ;
		}
		
		return product ;
	}
}