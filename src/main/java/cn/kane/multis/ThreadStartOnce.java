/*
 * @(#)ThreadStartOnce.java	2013年11月4日
 *
 * @Company <Opportune Technology Development Company LTD.>
 */
package cn.kane.multis;

import java.lang.Thread.UncaughtExceptionHandler;


/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @Author  <Administrator>
 * @Date    <2013年11月4日>
 * @description 
 */
public class ThreadStartOnce implements Runnable {

    public static void main(String[] args) {
        Runnable runn = new ThreadStartOnce() ;
        Thread firstThd = new Thread(runn);
        firstThd.setUncaughtExceptionHandler(new MyExceptionHandler());//u can set exception-handler4thread
        firstThd.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        firstThd.start();//EXECEPTION: TERMINATED cannot to RUNNABLE
        System.out.println("TWICE");
    }

    public void run() {
        System.out.println("RUNNING-"+System.currentTimeMillis());
        throw new ClassCastException("class-cast-exception");
    }
    
}

class MyExceptionHandler implements UncaughtExceptionHandler{
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println(String.format("%s throws %s", t.getName(),e));
    }
}
