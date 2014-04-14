package cn.kane;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;

public class StaticsApp {

    private static LinkedBlockingQueue<Object> reqQueue = new LinkedBlockingQueue<Object>();
	
	public static void main(String[] args){
		
		new Thread(new Runnable() {
			public void run() {
				while(true){
					Iterator<Object> iterator = reqQueue.iterator();
					if(iterator.hasNext()){
						Object obj = iterator.next();
						synchronized (obj) {
							try {
								Thread.sleep(8000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							
							obj.notifyAll();
							iterator.remove();
						}
					}
				}
			}
		}).start();
		
		Object lock = new Object();
		synchronized (lock) {
			try {
				System.out.println(System.currentTimeMillis());
				req(lock);
				lock.wait(10000);
			} catch (InterruptedException e) {
				System.out.println("do not return on time");
				e.printStackTrace();
			}
			
			System.out.println(System.currentTimeMillis());
			System.out.println("return on time");
			System.exit(0);
		}
	}
	
	public static Object req(Object req){
		reqQueue.add(req);
		return req ;
	}
	public static void statics() {

		 int five = 0 ;
		 int ten = 0 ;
		 int fifth = 0 ;
		 int twen = 0 ;
		 int thirty = 0 ;
		 int forty = 0 ;
		 int bigger = 0 ;
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader("d:/1--dl.lla.2012-10-12-0-1.log"));
			System.out.println("-------- initing file content -------");
			String lineStr = null ;
			try {
				while((lineStr=reader.readLine()) != null){
					if(lineStr.contains("cost")){
						int costIndex = lineStr.indexOf("cost");
						int startIndex = costIndex + 5;
						int endIndex = lineStr.indexOf(",", startIndex);
						String costStr = lineStr.substring(startIndex, endIndex);
						int cost = Integer.parseInt(costStr);
						
						if(cost <= 5000){
							five++;
						}else if(cost<=10000){
							ten++;
						}else if(cost <=15000){
							fifth++;
						}else if(cost<=20000){
							twen++;
						}else if(cost<=30000){
							thirty++;
						}else if(cost<=40000){
							forty++;
						}else{
							bigger ++ ;
						}
					}
				}
				
				System.out.println("in 5 second is "+five);
				System.out.println("in 10 second is "+ten);
				System.out.println("in 15 second is "+fifth);
				System.out.println("in 20 second is "+twen);
				System.out.println("in 30 second is "+thirty);
				System.out.println("in 40 second is "+forty);
				System.out.println("bigger = "+bigger);				        
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
