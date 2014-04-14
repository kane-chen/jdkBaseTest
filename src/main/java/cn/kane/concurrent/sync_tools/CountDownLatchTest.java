package cn.kane.concurrent.sync_tools;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.log4j.Logger;

import junit.framework.TestCase;

public class CountDownLatchTest extends TestCase {

	private static final Logger log = Logger.getLogger(CountDownLatchTest.class);
	private final int COUNT_SIZE = 5 ;
	
	public void testSimpleCountDownLatch(){
		log.info("try to start test simple count-down-latch");
		
		final CountDownLatch countDownLatch = new CountDownLatch(COUNT_SIZE) ;
		final AtomicBoolean isRunning = new AtomicBoolean(true) ;
		
		
		for(int i=1;i<=COUNT_SIZE;i++){
			new Thread(new Runnable(){
				public void run() {
					String threadName =Thread.currentThread().getName() ;
					log.info(threadName+" is running") ;
					if(isRunning.get()){
						try {
							Thread.sleep(new Random().nextInt(3000));
							countDownLatch.countDown();
							log.info(threadName+" arrived,waitings are"+countDownLatch.getCount());
						} catch (InterruptedException e) {
							log.error(e) ;
						}
					}
				}
			},"THD-"+i).start();
		}
		
//		try {
//			countDownLatch.await();//blocking here
//			Thread.sleep(100);//ensure print order
//			log.info("countDownLatch was notify");
//		} catch (InterruptedException e1) {
//			log.error(e1) ;
//		}
		
		try {
			boolean noTimeOut = countDownLatch.await(10000,TimeUnit.MILLISECONDS);
			if(noTimeOut){
				log.info("countDownLatch was notify");
			}else{
				log.info("countDownLatch was not notify within the special time") ;
			}
		} catch (InterruptedException e) {
			log.error(e);
		}
		
	}
}
