package cn.kane.multiThreadTest.service;

import org.apache.log4j.Logger;

public class DefaultTestingServiceImpl implements ITestingService {

	private static final Logger logger = Logger.getLogger(DefaultTestingServiceImpl.class);
	public  String doSomeThing(Object param) throws Exception {
		
		logger.info(" get request "+param);
		if(param instanceof String){
			return (String)param;
		}
		Thread.sleep(5000);
		return param.toString();
	}

}
