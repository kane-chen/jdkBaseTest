/*
 * @(#)ExchangerTest.java	2013年11月2日
 *
 * @Company <Opportune Technology Development Company LTD.>
 */
package cn.kane.multis;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @Author  <Administrator>
 * @Date    <2013年11月2日>
 * @description 
 */
public class ExchangerTest {

    public static void main(String[] args) {
        List<String> storage = new ArrayList<String>() ;
        List<String> shore = new ArrayList<String>() ;
        Exchanger<List<String>> exchanger = new Exchanger<List<String>>() ;
        Thread producer = new Thread(new Producer(storage,exchanger));
        Thread consumer = new Thread(new Consumer(shore,exchanger));
        producer.start();
        consumer.start();
    }

}

class Producer implements Runnable{

    private List<String> storedList ;
    private Exchanger<List<String>> exchanger ;
    
    public Producer(List<String> storedList,Exchanger<List<String>> exchanger){
        this.storedList = storedList ;
        this.exchanger = exchanger ;
    }
    public void run() {
//        List<String> storedList = new ArrayList<String>() ;//local-param is ok
        for(int m =0 ; m<10;m++){
            for(int i=0 ; i<10; i++){
                String pro = "PRO-"+i+"-"+System.currentTimeMillis();
                storedList.add(pro) ;
                System.out.println("ADD:"+pro);
            }
            try {
                Thread.sleep(1000);
                storedList = exchanger.exchange(storedList, 1, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }
    }
}

class Consumer implements Runnable{

    private List<String> goodsList ;
    private Exchanger<List<String>> exchanger ;
    public Consumer(List<String> goodsList,Exchanger<List<String>> exchanger){
        this.goodsList = goodsList ;
        this.exchanger = exchanger ;
    }
    public void run() {
//        List<String> goodsList = new ArrayList<String>() ;//local-param is ok
        for(int m=0;m<10;m++){
            try {
                Thread.sleep(1000);
                goodsList = exchanger.exchange(goodsList,7,TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
            if(null!=goodsList){
                Iterator<String> it = goodsList.iterator() ;
                while(it.hasNext()){
                    System.out.println("REM-"+it.next());
                    it.remove();
                }
            }
        }
    }
    
}