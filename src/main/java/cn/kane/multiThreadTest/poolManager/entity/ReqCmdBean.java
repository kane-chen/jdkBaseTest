package cn.kane.multiThreadTest.poolManager.entity;

import java.util.Map;

import cn.kane.multiThreadTest.service.ITestingService;

public class ReqCmdBean {

	private ITestingService service = null ;  
	private String reqMethodName = null ;
	private Map<String,Object> params = null ;
	
	public ReqCmdBean(ITestingService service,String reqMethodName,Map<String,Object> params){
		this.service = service ;
		this.reqMethodName = reqMethodName ;
		this.params = params ;
	}
	
	public ITestingService getService() {
		return service;
	}

	public String getReqMethodName() {
		return reqMethodName;
	}

	public Map<String, Object> getParams() {
		return params;
	}

}
