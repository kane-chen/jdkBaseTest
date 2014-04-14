package cn.kane.threadLocal;

public class TestRunnable implements Runnable {

    public EntityBean bean = null ;
	
	public TestRunnable(EntityBean bean){
		this.bean = bean ;
	}
	public void run() {
        String threadName = Thread.currentThread().getName() ;
		bean.setInstanceVar(threadName) ;
		bean.setThreadLocalVar(threadName);
		try {
			Thread.sleep(1000);
//			System.out.println("I-"+threadName+":"+bean.getInstanceVar());
			System.out.println("T-"+threadName+":"+bean.getThreadLocalVar());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
}
