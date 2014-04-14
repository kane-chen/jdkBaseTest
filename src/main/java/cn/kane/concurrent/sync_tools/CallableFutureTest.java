package cn.kane.concurrent.sync_tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import junit.framework.TestCase;

public class CallableFutureTest extends TestCase {

	public void testDirectFuture(){
		System.out.println("start direct future");
		ExecutorService executor = Executors.newSingleThreadExecutor() ;
		FutureTask<Object> futureTask = new FutureTask<Object>(new Callable<Object>() {
			public Object call() throws Exception {
				//do synchronous task
				try{
					Thread.sleep(5000);
				}catch(Exception e){
					System.out.println("future was interrupted");
					throw e ;
				}
				return "FUTURE-RESULT";
			}
		});
		
		executor.execute(futureTask);
		
		if(!futureTask.isDone()){
			System.out.println("future not done yet");
			if(futureTask.cancel(true)){
				System.out.println("future cancled");
			}else{
				System.out.println("future cancle failed");
			}
		}else{
			System.out.println("future return result");
		}
		
//		Object result ;
//		try {
//			result = futureTask.get();////blocks until future done 
//			result = futureTask.get(3, TimeUnit.SECONDS) ;
//			System.out.println(result);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		} catch (ExecutionException e) {
//			e.printStackTrace();
//		} catch (TimeoutException e) {
//			e.printStackTrace();
//		}
	}
	
	public void testParamFuture(){
		int workerSize = 10 ;
		ExecutorService executor = Executors.newFixedThreadPool(workerSize);
		List<Future<Object>> results = new ArrayList<Future<Object>>() ;
		for(int i=1;i<=workerSize;i++){
			Future<Object> result = executor.submit(new CallableImpl(i));
			results.add(result);
		}
		
		for(Future<Object> result : results){
			try {
				System.out.println(System.currentTimeMillis()+"###"+result.get().toString());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
	}
}
class CallableImpl implements Callable<Object>{

	private int threadNo = -1 ;
	
	CallableImpl(int threadNo){
		this.threadNo = threadNo ;
	}
	
	public Object call() throws Exception {
		System.out.println("callable-"+threadNo+" is running");
		Thread.sleep(new Random().nextInt(5000));
		System.out.println(System.currentTimeMillis()+":::"+threadNo);
		return "callable-"+threadNo ;
	}
	
}