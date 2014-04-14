package cn.kane.multiThreadTest.concurrent;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import cn.kane.multiThreadTest.MultiThreadTestScriptMain;
import cn.kane.multiThreadTest.service.ITestingService;
import cn.kane.multiThreadTest.utils.ResultCountNumber;
import cn.kane.multiThreadTest.worker.WorkerThread;

public class ConcurrentThread extends Thread {

	private static final Logger logger = Logger.getLogger(MultiThreadTestScriptMain.class);

	private ITestingService service = null;// 被测试接口
	private Object inputParam = null; // 请求参数
	private Object outputParam = null; // 返回参数

	private int threadCounts = 5; // 并发请求线程数
	private long intervalMillis = 1000; // 二次请求间时间间隔
	private long executeMillis = 60*1000;//测试持续时

	private ResultCountNumber resultCount = null; // 测试结果统计
	private List<WorkerThread> threads = new ArrayList<WorkerThread>(); // 并发测试线程

	public ConcurrentThread(ITestingService service, 
			Object inputParam,
			Object outputParam, 
			int threadCounts, 
			long intervalMillis,
			long executeMillis,
			ResultCountNumber resultCount) {

		this.service = service;

		this.inputParam = inputParam;
		this.outputParam = outputParam;

		this.intervalMillis = intervalMillis;
		this.executeMillis = executeMillis;
		this.threadCounts = threadCounts;
		
		this.resultCount = resultCount;
	}

	@Override
	public void run() {
		logger.info("try to start ConcurrentThread");
		int number = 1;
		while (number < threadCounts + 1) {
			WorkerThread worker = new WorkerThread(intervalMillis,
					service, 
					inputParam, 
					outputParam, 
					resultCount);
			worker.setName("worker-"+number);
			threads.add(worker);
			worker.start();
			number ++ ;
		}
		logger.info("testing start");
		synchronized (resultCount) {
			resultCount.notifyAll();
		}
	}
	@Override
	public void interrupt(){
		logger.info("try to shutdown testing");
		if(null != threads ){
			for(WorkerThread thread : threads){
				if(null!= thread)
				  thread.shutdown();
			}
		}
		
		logger.info("######################### StressTestingThread shutdown ##########################");
		logger.info( "#########################    stress testing result  #############################");
		logger.info( "########## threadCounts="+threadCounts+" intervalMillis="+intervalMillis+"millis executeMillis="+executeMillis+"millis"+"##########");
		logger.info("####### "+ resultCount.toString() +" ########");
	}
}
