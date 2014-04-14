package cn.kane.multiThreadTest;

import org.apache.log4j.Logger;

import cn.kane.multiThreadTest.concurrent.ConcurrentThread;
import cn.kane.multiThreadTest.service.ITestingService;
import cn.kane.multiThreadTest.timer.TimerThread4Testing;
import cn.kane.multiThreadTest.utils.ResultCountNumber;



public class MultiThreadTestScriptMain extends Thread{

    private static final Logger logger = Logger.getLogger(MultiThreadTestScriptMain.class);

    //请求接口相关
    private ITestingService service = null ;//被测试接口
    private Object inputParam = null ; //请求参数
    private Object outputParam = null; //返回参数
    
    //并发测试相关参数
    private int threadCounts = 5 ;      //并发请求线程数
    private long intervalMillis = 1000 ; //二次请求间时间间隔
    private long executeMillis = 60*1000;//测试持续时间
    
    //并发测试所需工具类
    private Thread testingThread = null ;
    private Thread timerThread = null ;
    private ResultCountNumber resultCount = new ResultCountNumber();  //测试结果统计

    
    public MultiThreadTestScriptMain(ITestingService service,Object inputParam,Object outputParam,
			long intervalMillis,int threadCounts,long executeMillis){
    	logger.info("try to init MultiThreadTestScriptMain");
    	
    	this.service=service;
		
		this.inputParam = inputParam ;
		this.outputParam = outputParam ;
		
		this.threadCounts=threadCounts;
		this.intervalMillis=intervalMillis;
		this.executeMillis=executeMillis;
	}
    
    @Override
    public void run(){
    	logger.info("try to start testing");
    	testingThread = new ConcurrentThread(service, inputParam, outputParam, threadCounts, intervalMillis, executeMillis, resultCount);
    	timerThread = new TimerThread4Testing(executeMillis,testingThread,resultCount);
    	testingThread.start();
    	timerThread.start();
    }
    
	public ITestingService getService() {
		return service;
	}

	public void setService(ITestingService service) {
		this.service = service;
	}

	public Object getInputParam() {
		return inputParam;
	}

	public void setInputParam(Object inputParam) {
		this.inputParam = inputParam;
	}

	public Object getOutputParam() {
		return outputParam;
	}

	public void setOutputParam(Object outputParam) {
		this.outputParam = outputParam;
	}

	public int getThreadCounts() {
		return threadCounts;
	}

	public void setThreadCounts(int threadCounts) {
		this.threadCounts = threadCounts;
	}

	public long getIntervalMillis() {
		return intervalMillis;
	}

	public void setIntervalMillis(long intervalMillis) {
		this.intervalMillis = intervalMillis;
	}

	public long getExecuteMillis() {
		return executeMillis;
	}

	public void setExecuteMillis(long executeMillis) {
		this.executeMillis = executeMillis;
	}

	public Thread getTestingThread() {
		return testingThread;
	}

	public void setTestingThread(Thread testingThread) {
		this.testingThread = testingThread;
	}

	public Thread getTimerThread() {
		return timerThread;
	}

	public void setTimerThread(Thread timerThread) {
		this.timerThread = timerThread;
	}

	public ResultCountNumber getResultCount() {
		return resultCount;
	}

}
