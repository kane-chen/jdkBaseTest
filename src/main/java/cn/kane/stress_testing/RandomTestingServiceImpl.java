package cn.kane.stress_testing;

import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;

public class RandomTestingServiceImpl implements ITestingService {

	private static final Logger logger = Logger.getLogger(RandomTestingServiceImpl.class);
	
	private LinkedBlockingQueue<Object> reqQueue = new LinkedBlockingQueue<Object>(100); 
	
	public Object request(Object reqParam) throws Exception {
		reqQueue.add(reqParam);
		int randomInt = (int) Math.round(Math.random()*100);
		if(randomInt%3==0){
			return null;
		}else{
			return reqParam ;
		}
	}

	public void notifyReq() {
		logger.info("try to notify request");
        while(true){
        	Iterator<Object> iterator = reqQueue.iterator();
        	while(iterator.hasNext()){
        		Object obj = iterator.next() ;
        		synchronized(obj){
        			long randomSleepMilis = Math.round(Math.random()*700) ;
        			logger.info("sleepMilis is "+randomSleepMilis);
        			try {
						Thread.sleep(randomSleepMilis);
					} catch (InterruptedException e) {
						logger.error("NotifyThread was interrupt");
					}
        			obj.notify();
        			iterator.remove();
        		}
        	}
        }
	}

}
