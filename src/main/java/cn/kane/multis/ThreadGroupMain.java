/*
 * @(#)ThreadGroupMain.java	2013年11月5日
 *
 * @Company <Opportune Technology Development Company LTD.>
 */
package cn.kane.multis;

import java.util.Date;
import java.util.Random;


/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @Author  <Administrator>
 * @Date    <2013年11月5日>
 * @description 
 */
public class ThreadGroupMain {

    public static void main(String[] args) {
        ThreadGroup root = new ThreadGroup("root");
        ThreadGroup sub = new ThreadGroup(root,"sub");
        Runnable runnable = new PrintRunnable();
        for(int i=1 ; i< 6; i++){
            Thread curThread = new Thread(sub,runnable) ;
            curThread.start();
        }
        Thread[] threads = new Thread[sub.activeCount()];
        sub.enumerate(threads);
        for(Thread thr : threads){
            if(null!=threads){
                System.out.println(thr.getName());
            }
        }
        
        try {
            Thread.sleep(1000);
            root.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    
}

class PrintRunnable implements Runnable{
    public void run() {
        String threadName = Thread.currentThread().getName() ; 
        Random random = new Random(new Date().getTime()) ;
//        Random random = new Random() ;
        long runTime = random.nextInt(1000)+1000 ;
        System.out.println("Start:"+threadName+":"+runTime);
        try {
            Thread.sleep(runTime);
        } catch (InterruptedException e) {
            System.out.println("Interrupt:"+threadName);
        }
        System.out.println("End:"+threadName);
    }
    
}