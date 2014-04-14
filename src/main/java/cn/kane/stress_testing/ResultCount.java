package cn.kane.stress_testing;

public class ResultCount {

	private int succCount = 0 ;
	private int nullRspCount = 0 ;
	private int reqExpCount = 0 ;
	private int reqTimeoutCount = 0 ;
	private int reqIntertuptCount = 0 ;
	
	public ResultCount(){
		
	}

	public ResultCount(int succCount,int nullRspCount,int reqExpCount,int reqTimeoutCount,int reqIntertuptCount){
	    this.succCount = succCount ;
	    this.nullRspCount = nullRspCount ;
	    this.reqExpCount = reqExpCount ;
	    this.reqTimeoutCount = reqTimeoutCount ;
	    this.reqIntertuptCount = reqIntertuptCount ;
	}
	
	
	public int getSuccCounts() {
		return succCount;
	}
	public int getNullRspCount() {
		return nullRspCount;
	}
	public int getReqExpCount() {
		return reqExpCount;
	}
	public int getReqTimeoutCount() {
		return reqTimeoutCount;
	}
	public int getReqIntertuptCount() {
		return reqIntertuptCount;
	}
	
	public  synchronized void addSuccCounts(){
		this.succCount++;
	}
	public  synchronized void addNullRspCounts(){
		this.nullRspCount++;
	}
	public  synchronized void addReqExpCounts(){
		this.reqExpCount++;
	}
	public  synchronized void addReqTimeoutCounts(){
		this.reqTimeoutCount++;
	}
	public  synchronized void addReqIntertuptCounts(){
		this.reqTimeoutCount++;
	}
	
	public String toString(){
		return "ResultCount[succCounts=" + succCount
				+",nullRspCount=" + nullRspCount
				+",reqExpCount=" + reqExpCount
				+",reqTimeoutCount=" + reqTimeoutCount
				+",reqIntertuptCount=" + getReqIntertuptCount()
				+"]";
	}


}
