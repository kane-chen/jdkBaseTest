package cn.kane.multiThreadTest.poolManager.entity;

import java.util.Map;

public class CommandBean {

	private  String serial_no = null;
	private  String reqMethod = null ;
	private  Map<String,String> params = null ;
	
	CommandBean(String serial_no,String reqMethod,Map<String,String> params){
		this.serial_no = serial_no ;
		this.reqMethod = reqMethod ;
		this.params = params  ;
	}
	
	CommandBean(String serial_no,Map<String,String> params){
		this.serial_no = serial_no ;
		this.params = params ;
	}

	public String getSerial_no() {
		return serial_no;
	}

	public String getReqMethod() {
		return reqMethod;
	}

	public Map<String, String> getParams() {
		return params;
	}
    
	//Override
	public String toString(){
		return "CommandBean[serial_no="+serial_no+",params="+params+(null==reqMethod? "" :"reqMethod="+reqMethod);
	}
}
