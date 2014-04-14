package cn.kane.concurrent.sync_tools;

import java.util.concurrent.atomic.AtomicInteger;

import junit.framework.TestCase;

public class AtomicIntegerTest extends TestCase {

	private final AtomicInteger atomicInt = new AtomicInteger(0) ; 
	
	public void testIncream(){
		for(int i = 1 ; i <100 ;i++){
			System.out.println("Loop-"+i+": befor "+ atomicInt.get());
			System.out.println(atomicInt.getAndIncrement());
			System.out.println("Loop-"+i+": after "+ atomicInt.get());
		}
	}
	
	public void testDecream(){
		for(int i = 1 ; i <100 ;i++){
			System.out.println("Loop-"+i+": befor "+ atomicInt.get());
			System.out.println(atomicInt.getAndDecrement());
			System.out.println("Loop-"+i+": after "+ atomicInt.get());
		}
	}
}
