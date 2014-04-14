package cn.kane.stress_testing;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import org.apache.log4j.Logger;

public class ReqCaller implements Runnable {

	private static final Logger logger = Logger.getLogger(ReqCaller.class);
	
	private String threadName = null ;
	private boolean runnable = true ;
	
	private long reqTimeoutMilis = 3000 ;
	private long intervalMilis = 100 ;
	private boolean isSleepBetwenReqs = false ;
	
	private ITestingService service = null ;
	private Object reqParam = null ;
	private CyclicBarrier barrier = null ;
	
	private ResultCount reusltCount = null ;
	
	public ReqCaller(int threadNo,long reqTimeoutMilis,long intervalMilis,boolean isSleepBetwenReqs,ITestingService service,CyclicBarrier barrier){
		this.threadName = "CALLER-"+threadNo ;
		Thread.currentThread().setName(threadName);
		this.reqTimeoutMilis = reqTimeoutMilis ;
		this.intervalMilis = intervalMilis ;
		this.isSleepBetwenReqs = isSleepBetwenReqs ;
		this.service = service ;
		this.barrier = barrier ;
		this.reusltCount = new ResultCount() ;
	}
	
	public String getThreadName(){
		return this.threadName;
	}
	
	public ResultCount getReusltCount(){
		return this.reusltCount ;
	}
	
	public void destroy(){
		logger.info("try to stop " + threadName);
		runnable = false ;
		Thread.currentThread().interrupt();
	}
	
	public void run() {
		logger.info("try to start " + threadName);
		Object rspObj = null ;
		
		try {
			barrier.await();
		} catch (InterruptedException e) {
			logger.error("CyclicBarrier was interrupted");
		} catch (BrokenBarrierException e) {
			logger.error("CyclicBarrier was broken");
		}
		
		logger.info("try to request "+threadName);
		while(runnable){
			long startMilis,costMilis;
			reqParam = Math.random()*1000+"";
			synchronized(reqParam){
				try {
					try {
						rspObj = service.request(reqParam);
					} catch (Exception e) {
						logger.error("request error : {}",e);
						reusltCount.addReqExpCounts();
					}
					startMilis = System.currentTimeMillis();
					logger.info("try to wait rsp");
					reqParam.wait(reqTimeoutMilis);
					costMilis = System.currentTimeMillis()-startMilis;
					logger.info("Request COST(milis) " + costMilis);
					
					if(costMilis>=reqTimeoutMilis){
						logger.info("request timeout");
						reusltCount.addReqTimeoutCounts();
					}else{
						if(null!= rspObj){
							logger.debug("rsp is not null");
							reusltCount.addSuccCounts();
						}else{
							logger.info("rsp null");
							reusltCount.addNullRspCounts();
						}
					}
				} catch (InterruptedException e) {
					logger.warn("requst interrupt");
				}
			}
			if(isSleepBetwenReqs){
				try {
					Thread.sleep(intervalMilis);
				} catch (InterruptedException e) {
					logger.info("interrupt between 2 request");
				}
			}
		}
	}

}
