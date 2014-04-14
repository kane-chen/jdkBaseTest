package cn.kane.multiThreadTest.poolManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import org.apache.log4j.Logger;

import cn.kane.multiThreadTest.poolManager.entity.CommandBean;
import cn.kane.multiThreadTest.poolManager.workerThread.Sender;
import cn.kane.multiThreadTest.poolManager.workerThread.Worker;
import cn.kane.multiThreadTest.service.DefaultTestingServiceImpl;
import cn.kane.multiThreadTest.service.ITestingService;

public class PoolManager {

	private static final Logger logger = Logger.getLogger(PoolManager.class);
	
	private Queue<CommandBean> reqQueue = null ;
	private Queue<CommandBean> rspQueue = null ;
	
	private List<Sender> sender = null ;
	private List<Worker> worker = null ;
	
	private Map<String,ITestingService> servicesMap = null ;
	
	public void init(Map<String,ITestingService> servicesMap){
		this.servicesMap = servicesMap ;
	}
	
	public void init(){
		servicesMap = new HashMap<String,ITestingService>();
		servicesMap.put("test", new DefaultTestingServiceImpl());
	}
	
	public void initSize(int senderCount,int workerCount,int reqQueueSize,int rspQueueSize){
		if(null == sender)
			sender = new ArrayList<Sender>();
		for(int i = 0 ; i < senderCount ; i++ ){
			Sender senderBean = new Sender(reqQueue);
			sender.add(senderBean);
		}
		if(null == worker)
			worker = new ArrayList<Worker>();
		for(int i = 0 ; i < workerCount ; i++ ){
			Worker workerBean = new Worker();
			worker.add(workerBean);
		}
		
		if(null == reqQueue)
			reqQueue = new ArrayBlockingQueue<CommandBean>(reqQueueSize);
		if(null == rspQueue)
			rspQueue = new ArrayBlockingQueue<CommandBean>(rspQueueSize);
	}
	
	public Object putReqIntoReqQueue(CommandBean reqBean){
		logger.info("putReqIntoReqQueue,param:::{}"+reqBean);
		Object result = null ;
		reqQueue.add(reqBean);
		try {
			reqBean.wait();//wait some special seconds?
		} catch (InterruptedException e) {
			logger.error(e);
		}
		return result ;
	}
}
