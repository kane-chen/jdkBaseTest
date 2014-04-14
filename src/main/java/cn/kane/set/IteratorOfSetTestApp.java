package cn.kane.set;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public class IteratorOfSetTestApp {

    static Set<String> set = new HashSet<String>();
	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args){
        set.add("1");
        set.add("2");
        set.add("3");
        set.add("1");
        new Thread(new Runnable(){
			public void run() {
				int i = 10;
				while(i>5){
                    System.out.println(i);
					set.add(i+"");
				}
			}
        }).start();
        
        try {
			Iterator<String> iterator = set.iterator();
			int i = 0 ;
			while(iterator.hasNext()){
				String currentStr = iterator.next() ;
				System.out.println(i+++" "+currentStr);
//        	set.remove(currentStr);//cycleVisit  modify set >>> concurrentModificationExp
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.exit(1);
		}
	}

}
