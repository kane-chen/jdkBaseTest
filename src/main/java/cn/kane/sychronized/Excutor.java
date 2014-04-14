package cn.kane.sychronized;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Excutor extends Thread {

	private String threadName = "UNDEFINE" ;
	
	public Excutor(String thdName){
		threadName = thdName ;
	}
	private void echoExcutorTimes(){
		System.out.println("Thread["+threadName+"] excute");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		echoExcutorTimes();
	};
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
	   ExecutorService executorService = Executors.newFixedThreadPool(5);
	   for(int i=1 ; i<7 ; i++){
		   Excutor exc = new Excutor(i+"") ;
		   executorService.execute(exc);
	   }
	}

}
