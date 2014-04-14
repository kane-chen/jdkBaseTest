package cn.kane.jvm.chap2.outOfMemory;

/**
 * @category java.lang.OutOfMemoryError: unable to create new native method
 *           WARN:it'll cost all your machine 's memory 
 *           PC-Memory-size = heap-max-size + method-stack-size + jvm-stack-size(threads(count)*memsizePerThread)   
 *    JVM-args: -Xss2m
 * @author chenqx
 *
 */
public class JvmStackOutOfMemory {

	
	public void recurse(){
		recurse();
	}
	
	public void crateNewThread(){
		while(true){
			new Thread(new Runnable(){
				public void run() {
					recurse();
				}
				
			}).start();
		}
	}
	
	/**
	 * @category 
	 * @param args
	 */
	public static void main(String[] args) {
		new JvmStackOutOfMemory().crateNewThread();
	}

}
