/*
 * @(#)ThreadPoolExecutorTest.java	2013年10月30日
 *
 * @Company <Opportune Technology Development Company LTD.>
 */
package cn.kane.multis.pool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @Author  <Administrator>
 * @Date    <2013年10月30日>
 * @description 
 */
public class ThreadPoolExecutorTest {

    private ThreadPoolExecutor poolExecutor = null;
    private int corePoolSize = 10 ;//keep in the pool, even if they are idle
    private int maximumPoolSize = 20 ;//maximum
    private long keepAliveTime = 120 ;//when the number of threads is greater than the core, this is the maximum time that excess idle threads will wait for new tasks before terminating.
    private TimeUnit unit = TimeUnit.SECONDS;
    private BlockingQueue<Runnable> workQueue;
    private ThreadFactory thrFactory = new  MyPoolThreadFactory();
    private RejectedExecutionHandler rejectHandler = new MyRejectHandler() ;
    private int maxCachedQueueSize = 10 ;
    
    public ThreadPoolExecutorTest(){
        workQueue = new ArrayBlockingQueue<Runnable>(maxCachedQueueSize) ;
        poolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,thrFactory,rejectHandler) ;
    }
    
    public Future<String> submitTask(Callable<String> task){
        return poolExecutor.submit(task) ;
    }
    
    public void shutdown(){
        poolExecutor.shutdown();
    }
    
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ThreadPoolExecutorTest instance = new ThreadPoolExecutorTest() ;
        for(int i =0 ;i<100;i++){
            try {
                Future<String> result = instance.submitTask(new MyCallableTask()) ;
                System.out.println(result.get(500,TimeUnit.SECONDS));
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }
        instance.shutdown();
    }

}

class MyPoolThreadFactory implements ThreadFactory {
    public Thread newThread(Runnable r) {
        System.out.println("thread-factory:manual-action");
        return new Thread(r);
    }
}

class MyRejectHandler implements RejectedExecutionHandler {
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        System.out.println("greater than the exceed,ignore it");
    }
}

class MyCallableTask implements Callable<String>{
    public String call() throws Exception {
        Thread.sleep(1000);
        return "CALLABLE";
    }
    
}