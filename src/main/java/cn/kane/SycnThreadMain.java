/*
 * @(#)SycnThreadMain.java	2013年10月9日
 *
 * @Company <www.lashou-inc.com>
 */
package cn.kane;


/**
 * @Project base
 * @version 1.0
 * @Author  chenqx
 * @Date    2013年10月9日上午11:34:21
 * @description 
 */
public class SycnThreadMain {

    public static void main(String[] args) {
        final Object numObj = new Object() ;
        final Object charObj = new Object() ;
        Thread numThr = new Thread(new Runnable(){
            public void run() {
                int i=1 ;
                    try {
                        while(i<=27){
                            synchronized (charObj) {
                                if(i!=1){
                                    charObj.wait();
                                }
                            }
                            System.out.println(i);
                            i+=2 ;
                            synchronized (numObj) {
                                numObj.notifyAll();
                            }
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
            }
        }) ;
        numThr.start();
        Thread charThr = new Thread(new Runnable(){
            public void run() {
                int i = 2 ;
                    try {
                        while(i<=28){
                            synchronized (numObj) {
                                numObj.wait();
                            }
                            System.out.println(i);
                            synchronized(charObj){
                                charObj.notifyAll();
                            }
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
            }
        }) ;
        charThr.start();
        
        System.out.println("----------------");
        
        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
