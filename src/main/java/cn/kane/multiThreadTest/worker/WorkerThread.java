package cn.kane.multiThreadTest.worker;

import org.apache.log4j.Logger;

import cn.kane.multiThreadTest.service.ITestingService;
import cn.kane.multiThreadTest.utils.ResultCountNumber;

public class WorkerThread extends Thread {

	private static final Logger logger = Logger.getLogger(WorkerThread.class);
	
	private ITestingService service = null ;
	private Object inputParam = null ; //请求参数
	private Object outputParam = null; //返回参数
	
	private long intervalMillis = 0 ;
	private ResultCountNumber resultCount = null ;
	
	private Boolean isRunning = true ;
	
	public WorkerThread(long intervalMillis,ITestingService service,Object inputParam,Object outputParam,ResultCountNumber resultCount){
		this.intervalMillis = intervalMillis ;
		this.service = service ;
		this.inputParam = inputParam ;
		this.outputParam = outputParam ;
		this.resultCount = resultCount ;
		
	}
	
	@Override
	public void run() {
        logger.trace(" try to start worker");
        
       
        synchronized (resultCount) {
			try {
//				resultCount.wait();
				resultCount.wait(1000);//设定超时继续执行时间
			} catch (InterruptedException e) {
				logger.warn("start worker failed ");
			}
		}
        logger.info("start worker");
        while(isRunning){
        	try {
        		resultCount.addReq();//request++
				outputParam = service.doSomeThing(inputParam);
				resultCount.addRsp();//response++
			    //TODO add request queue
				if(null == outputParam){
					resultCount.addNullRsp();//timeOut++
				}
				
				Thread.sleep(intervalMillis);
				
        	} catch (InterruptedException e){
//        		logger.error(e);
        		logger.info("intterupt ++");
        		resultCount.addIntterupt();//intterupt++
			} catch (Exception e) {
				logger.error(e);
				resultCount.addException();//exception++
			}
        }
        
        logger.info("worker done");
	}

	public void shutdown(){
		logger.trace(" try to stop worker");
		changeFlag( false );
		this.interrupt();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			logger.info("worker shutdown interrupt");
		}
		logger.trace("stop worker done");
	}
	
	public void changeFlag(boolean flag){
		synchronized (isRunning) {
			isRunning = flag ;
		}
	}
}
