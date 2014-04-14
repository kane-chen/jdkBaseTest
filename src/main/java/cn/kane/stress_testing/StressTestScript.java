package cn.kane.stress_testing;

import java.util.concurrent.CyclicBarrier;

import org.apache.log4j.PropertyConfigurator;

import cn.kane.TimerThread;

public class StressTestScript {
	
	public static void main(String[] args) {
		PropertyConfigurator.configure("src//log4j.properties");

//		ITestingService service = new TestingServiceImpl() ;
		ITestingService service = new RandomTestingServiceImpl() ;
		Thread notifyThread = new Thread(new NotifyReqThread(service));
		notifyThread.setDaemon(true);
		notifyThread.start();
		
		int threadCounts = 1000 ;
		long reqTimeoutMilis = 500 ; 
		long stayTestingMilis = 2000000 ;
		CyclicBarrier cbarrier = new CyclicBarrier( threadCounts + 1 );
		MultiCallerMan callerMan = new MultiCallerMan(threadCounts,reqTimeoutMilis,service,cbarrier);
		Thread callerThread = new Thread(callerMan);
		callerThread.setDaemon(true);
		callerThread.start();
        TimerThread timer = new TimerThread(callerMan,cbarrier,stayTestingMilis);
        new Thread(timer).start() ;
	}

}

class NotifyReqThread implements Runnable{

	ITestingService service = null ;
	public NotifyReqThread(ITestingService service){
		this.service = service;
	}
	public void run() {
		service.notifyReq();
	}
	
}