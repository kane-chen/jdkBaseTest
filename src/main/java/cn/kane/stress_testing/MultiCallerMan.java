package cn.kane.stress_testing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CyclicBarrier;

import org.apache.log4j.Logger;

public class MultiCallerMan implements Runnable {

	private static final Logger logger = Logger.getLogger(MultiCallerMan.class);
	private int threadCounts = 5 ;
    private List<ReqCaller> callerLst = new ArrayList<ReqCaller>() ;
    private Map<String,ResultCount> resultCounts = new HashMap<String,ResultCount>();
    
    private long reqTimeoutMilis = 3000 ;
	private long intervalMilis = 100 ;
	private boolean isSleepBetwenReqs = false ;
	private ITestingService service = null ;
	CyclicBarrier barrier = null ;
	
	public MultiCallerMan(int threadCounts,long reqTimeoutMilis,ITestingService service,CyclicBarrier barrier){
		this.threadCounts = threadCounts ;
		this.reqTimeoutMilis = reqTimeoutMilis ;
		this.service = service ;
		this.barrier = barrier ;
	}
	
	public void run() {
        logger.info("try to start MultiCallerMan");
        int threadNo = 1 ;
        while(threadNo < threadCounts+1){
        	ReqCaller caller = new ReqCaller(threadNo,reqTimeoutMilis,intervalMilis,isSleepBetwenReqs,service,barrier);
        	new Thread(caller).start() ;
        	callerLst.add(caller) ;
        	threadNo ++ ;
        }
	}
	
	@SuppressWarnings("deprecation")
	public void destroy(){
		logger.info("try to destroy MultiCallerMan");
		if(null != callerLst){
			for(ReqCaller caller : callerLst){
				if(null!=caller){
					resultCounts.put(caller.getThreadName(), caller.getReusltCount());
					caller.destroy();
				}
			}
		}
		logger.info("############## RESULT ###############");
		logger.info(resultCounts);
		
		int succCount = 0 ;
		int nullRspCount = 0 ;
		int reqExpCount = 0 ;
		int reqTimeoutCount = 0 ;
		int reqIntertuptCount = 0 ;
		for(String threadName : resultCounts.keySet()){
			ResultCount bean = resultCounts.get(threadName);
			succCount+=bean.getSuccCounts();
			nullRspCount+=bean.getNullRspCount();
			reqExpCount+=bean.getReqExpCount();
			reqTimeoutCount+=bean.getReqTimeoutCount();
			reqIntertuptCount += bean.getReqIntertuptCount();
		}
		
		logger.info("$$$$$$$$$$$$$$$Rusult Analize$$$$$$$$$$$$$$");
		ResultCount result = new ResultCount(succCount,nullRspCount,reqExpCount,reqTimeoutCount,reqIntertuptCount);
		logger.info(result);
		Thread.currentThread().stop();
	}

}
