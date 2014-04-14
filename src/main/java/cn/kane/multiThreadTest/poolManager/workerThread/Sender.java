package cn.kane.multiThreadTest.poolManager.workerThread;

import java.util.Queue;

import org.apache.log4j.Logger;

import cn.kane.multiThreadTest.poolManager.entity.CommandBean;

public class Sender extends Thread {

	private static final Logger logger = Logger.getLogger(Sender.class);
	private Queue<CommandBean> reqQueue= null ;
	
	public Sender(Queue<CommandBean> reqQueue ){
		this.reqQueue = reqQueue ;
	}
	
	@Override
	public void run() {
		logger.info("try to start sender");
		CommandBean req  = null ;
		while(null!=(req= reqQueue.poll())){
			logger.debug("call service,commandBean is"+req);
			try {
				//TODO
			} catch (Exception e) {
				logger.error(e);
			}
		}
	}
}
