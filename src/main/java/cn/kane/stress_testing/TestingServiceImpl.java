package cn.kane.stress_testing;

import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;

public class TestingServiceImpl implements ITestingService {

	private static final Logger logger = Logger.getLogger(TestingServiceImpl.class);
	private LinkedBlockingQueue<Object> blockList = new LinkedBlockingQueue<Object>();
	
	public Object request(Object reqParam) throws Exception {
		logger.info("handle request");
		blockList.add(reqParam);
		return reqParam ;
	}

	public void notifyReq(){
		logger.info("ready for handling request");
		Iterator<Object> it = blockList.iterator();
		while(it.hasNext()){
			Object reqParam = it.next();
			synchronized (reqParam) {
				try {
					Thread.sleep(6000);
				} catch (InterruptedException e) {
					logger.error("BlockingQueue notify req error");
				}
				reqParam.notify();
				it.remove();
			}
		}
	}
}
