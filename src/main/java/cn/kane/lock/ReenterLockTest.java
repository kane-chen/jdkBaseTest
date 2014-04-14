package cn.kane.lock;

public class ReenterLockTest {

	public synchronized void  method1(){
		//do something
		method2();//handler [this] 's monitor lock,can call method2
	}
	
	public synchronized void method2(){
		//do something
	}
	
	ReentranceLock lock = new ReentranceLock() ;
	public void methodA() throws InterruptedException{
		lock.lock();
		//do something;
		methodB();
		lock.unlock();
	}
	
	public void methodB() throws InterruptedException{
		lock.lock();
		//do something
		lock.unlock();
	}
}

class ReentranceLock {

	boolean isLocked = false;
	Thread  lockedBy = null;
	int     lockedCount = 0;
	
	public synchronized void lock() throws InterruptedException{
		Thread callingThread = Thread.currentThread();
		while(isLocked && lockedBy != callingThread){
			wait();
		}
		isLocked = true;
		lockedCount++;
		lockedBy = callingThread;
	}
	
	
	public synchronized void unlock(){
		if(Thread.currentThread() == this.lockedBy){
			lockedCount--;
			
			if(lockedCount == 0){
				isLocked = false;
				notify();
			}
		}
	}
}