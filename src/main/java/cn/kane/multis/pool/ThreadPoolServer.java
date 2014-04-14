/*
 * @(#)ThreadPoolServer.java	2013年10月30日
 *
 * @Company <Opportune Technology Development Company LTD.>
 */
package cn.kane.multis.pool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;


/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @Author  <Administrator>
 * @Date    <2013年10月30日>
 * @description 
 */
public class ThreadPoolServer {
    
    private int maxSize = 10 ;
    private ExecutorService pool = null ;
    
    public ThreadPoolServer(){
        pool = Executors.newFixedThreadPool(maxSize);
    }
    
    public void commitTask(Runnable task){
        System.out.println(String.format("BEFORE:%s-%s", task,System.currentTimeMillis()));
        pool.execute(task);
        System.out.println(String.format("AFTER:%s-%s", task,System.currentTimeMillis()));
    }

    public Future<String> submitTask(Callable<String> task){
        System.out.println(String.format("BEFORE:%s-%s", task,System.currentTimeMillis()));
        Future<String> resultFuture = pool.submit(task);
        System.out.println(String.format("AFTER:%s-%s", task,System.currentTimeMillis()));
        return resultFuture ;
    }
    
    public void shutdown(){
        System.out.println("shutdown");
        pool.shutdown(); 
    }
    
    public static void main(String[] args){
        ThreadPoolServer server = new ThreadPoolServer() ;
//        for(int i=0 ;i<50;i++){
//            server.commitTask(new Task(i));
//        }
        List<Future<String>> results = new ArrayList<Future<String>>() ;
        for(int i=0 ; i<50 ;i++){
            Future<String> resultFuture = server.submitTask(new CallableTask(i));
            results.add(resultFuture);
        }
        try {
            results.get(0).cancel(true);
            System.out.println(results.get(0).get(5, TimeUnit.SECONDS));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        server.shutdown();
    }
}

class Task implements Runnable{
    private int id ;
    public Task(int id){
        this.id = id ;
    }
    public void run() {
        try {
            Thread.sleep(5000);
            System.out.println(String.format("------%s------",id));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class CallableTask implements Callable<String>{
    private int id ;
    public CallableTask(int id){
        this.id = id ;
    }
    public String call() throws Exception {
        try{
            Thread.sleep(3000);
            System.out.println(String.format("=========%s========", id));
        }catch(Exception e){
            e.printStackTrace();
            throw e ;
        }
        return "THR-"+id ;
    }
    
}