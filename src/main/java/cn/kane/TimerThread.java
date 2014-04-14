package cn.kane;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import org.apache.log4j.Logger;

import cn.kane.stress_testing.MultiCallerMan;

public class TimerThread implements Runnable {

	private static final Logger logger = Logger.getLogger(TimerThread.class);
	private boolean runnable = true ;
	private MultiCallerMan callerMan = null ;
	private long stayMilis = 60000 ;
	private CyclicBarrier barrier = null ;
	
	public TimerThread(MultiCallerMan callerMan,CyclicBarrier barrier,long stayMilis){
		this.barrier = barrier ;
		this.stayMilis = stayMilis ;
		this.callerMan = callerMan ;
	}
	
	public void run() {

		logger.info("try to start timer thread");

		try {
			barrier.await();
		} catch (InterruptedException e) {
			logger.error("CyclicBarrier was interrupted");
		} catch (BrokenBarrierException e) {
			logger.error("CyclicBarrier was broken");
		}
		
		if(runnable){
			logger.info("stress testing begin");
			try {
				Thread.sleep(stayMilis);
			} catch (InterruptedException e) {
			    logger.error("it's interrupt in stress testing");
			}
			callerMan.destroy();
		}
	}

}
