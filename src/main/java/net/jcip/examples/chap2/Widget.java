package net.jcip.examples.chap2;

public class Widget {
   synchronized void doSomething(){
	   //
   }
}

class SubWidget extends Widget{
	synchronized void doSomething(){
		System.out.println("do other");
		super.doSomething();//ReentrantLock
	}
}