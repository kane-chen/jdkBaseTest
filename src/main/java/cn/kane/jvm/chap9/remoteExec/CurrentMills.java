package cn.kane.jvm.chap9.remoteExec;

public class CurrentMills {

	public void printCurrentMills(String name){
		System.out.println(name+"&&&"+System.currentTimeMillis());
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Current::::"+System.currentTimeMillis());
	}

}
