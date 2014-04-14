package cn.kane.multiThreadTest;

//import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;

import cn.kane.multiThreadTest.service.DefaultTestingServiceImpl;
import cn.kane.multiThreadTest.service.ITestingService;

public class AppMain {

	public static void main(String[] args) throws Exception {

//		BasicConfigurator.configure();//默认配置
		PropertyConfigurator.configure("src/resource/log4j.properties");
		
		ITestingService service = new DefaultTestingServiceImpl();
		String inputParam = "inputParam";
		String outputParam = null ;
		
		int threadCounts = 100 ;
		long intervalMillis = 100 ;
		long executeMillis = 10*1000 ;
		
		MultiThreadTestScriptMain testingMain = new MultiThreadTestScriptMain(service, inputParam, outputParam, intervalMillis, threadCounts, executeMillis);
		testingMain.start();
	}

}
