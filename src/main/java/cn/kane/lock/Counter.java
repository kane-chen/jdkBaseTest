package cn.kane.lock;


public class Counter {

	private Lock lock = new Lock();
    private int count = 0;

    public int incWithSync(){
        synchronized(this){
          return ++count;
        }
      }
    
    public int incWithLock() throws InterruptedException{
    	lock.lock();
    	int newCount = ++count;
    	lock.unlock();
    	return newCount;
    }
    
    public int incWithReleaseLock(){
    	int newCount =  -1 ;
    	try {
			lock.lock();
		    newCount = ++count ;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			lock.unlock();//unlock in any case;
		}
    	return newCount ;
    }

}

class Lock{
	  
	private boolean isLocked = false;
	  
	public synchronized void lock() throws InterruptedException{
		while(isLocked){
			wait();
		}
		isLocked = true;
	}
	
	public synchronized void unlock(){
		isLocked = false;
		notify();
	}
}