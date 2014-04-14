package cn.kane.stress_testing;

public interface ITestingService {

	Object request(Object reqParam) throws Exception ;
	
	void notifyReq();
}
