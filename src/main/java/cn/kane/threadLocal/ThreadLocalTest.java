package cn.kane.threadLocal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import junit.framework.TestCase;

public class ThreadLocalTest extends TestCase {

	private String strParam = "initStr" ;
	
	public String getStrParam(){
		return strParam ;
	}
	
	public void testThreadLocal(){
		new Thread(new WriteStrRunnable()).start();
		new Thread(new WriteStrRunnable2()).start();
		System.out.println(strParam);
	}
	class WriteStrRunnable implements Runnable{
		public void run() {
			strParam = "writeRunnable" ;
			for(int i=0 ; i <10 ;i++){
				System.out.println(getStrParam());
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	class WriteStrRunnable2 implements Runnable{
		public void run() {
			strParam = "writeRunnable2" ;
			for(int i=0 ; i <10 ;i++){
				System.out.println(getStrParam());
			}
		}
	}
	
	ThreadLocal<String> threadLocal = new ThreadLocal<String>();
	ExecutorService executors = Executors.newFixedThreadPool(10);
	
	
}

