package cn.kane.set;

import java.util.concurrent.ConcurrentHashMap;

public class CurrentClear {

	public static void main(String[] str){
		ConcurrentHashMap< String, String> maps = new ConcurrentHashMap<String, String>();
		
		maps.put("111", "1111");
		maps.put("222", "2222");
		maps.put("333", "3333");
		maps.put("444", "4444");
		maps.put("555", "5555");
		
		if(null == maps.get("222")){
			System.out.println("true");
		}else{
			System.out.println("false");
		}
		maps.clear();
		
		if(null == maps.get("222")){
			System.out.println("true");
		}else{
			System.out.println("false");
		}
	}
}
