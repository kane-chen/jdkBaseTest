package cn.kane.threadLocal;

public class TestApp {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		EntityBean bean = new EntityBean();
		TestRunnable runnable = new TestRunnable(bean) ;
		for(int i = 1 ; i<6 ; i++){
        	String threadName = "THR-"+ i ;
        	new Thread(runnable,threadName).start();
        }
	}

}
