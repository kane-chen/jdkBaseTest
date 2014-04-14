package cn.kane.multiThreadTest.timer;

import org.apache.log4j.Logger;

public class TimerThread4Testing extends Thread {

	private static final Logger logger = Logger.getLogger(TimerThread4Testing.class);
	private long stayMillis = 60*1000 ;
	private Thread testingThread = null ;
	private Object lock = null ;
	public TimerThread4Testing(long stayMillis,Thread testingThread,Object lock){
		this.stayMillis = stayMillis ;
		this.testingThread = testingThread ;
		this.lock = lock ;
	}
	
	@Override
	public void run(){
		logger.info("try to start timer");
		try {
			synchronized ( lock ) {
				lock.wait();
			}
			logger.info("timer started");
			Thread.sleep(stayMillis);
			logger.info("timer end");
			testingThread.interrupt();
		} catch (InterruptedException e) {
			logger.error(e);
		}
	}
}
