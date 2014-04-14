package cn.kane.multiThreadTest.poolManager.entity;

import java.util.Map;

public class CommandBeanFactory {

	static CommandBean getReqCommandBean (String serial_no,String reqMethod,Map<String,String> params){
		return new CommandBean(serial_no,reqMethod,params);
	}
	
	static CommandBean getRspCommandBean(String serial_no,Map<String,String> params){
		return new CommandBean(serial_no,params);
	}
}
