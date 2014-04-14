package cn.kane.multiThreadTest.utils;

public class ResultCountNumber {

	private Integer reqCounts = 0 ; //请求数
	private Integer rspCounts = 0 ; //响应数
	private Integer nullRspCounts = 0 ;   //空响应数
	private Integer exceptionCounts = 0 ; //发生异常数
	private Integer intteruptCounts = 0 ; //被中断请求数
	
	public void addReq(){
		synchronized (reqCounts) {
			reqCounts ++ ;
		}
	}
	
	public void addRsp(){
		synchronized (rspCounts) {
			rspCounts ++ ;
		}
	}
	
	public void addNullRsp(){
		synchronized (nullRspCounts) {
			nullRspCounts ++ ;
		}
	}
	
	public void addException(){
		synchronized (exceptionCounts) {
			exceptionCounts ++ ;
		}
	}
	
	public void addIntterupt(){
		synchronized (intteruptCounts) {
			intteruptCounts ++ ;
		}
	}
	@Override
	public String toString(){
		return "ResultCountNumber[" +
				"reqCounts="+reqCounts +
				",rspCounts="+rspCounts +
				",nullRspCounts="+nullRspCounts +
				",exceptionCounts="+exceptionCounts +
		        ",intteruptCounts="+intteruptCounts ;
	}
}
