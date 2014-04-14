/*
 * @(#)InterruptTest.java	2013年11月4日
 *
 * @Company <Opportune Technology Development Company LTD.>
 */
package cn.kane.multis;


/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @Author  <Administrator>
 * @Date    <2013年11月4日>
 * @description 
 */
public class InterruptTest extends Thread {

    public static void main(String[] args){
        InterruptTest test = new InterruptTest();
        Thread thd = new Thread(test) ;
        thd.start();
        try {
            Thread.sleep(2000);
            thd.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void run() {
        while(true){
            for(int i = 1 ;i<Integer.MAX_VALUE;i++){
                if(this.isPrime(i))
                    System.out.println(i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.exit(0);
                }
            }
        }
    }

    private boolean isPrime(int number){
        boolean result = false ;
        if(number <= 2)
            result = true ;
        else{
            for(int i=2;i<number;i++){
                if(number % i == 0){
                    return false ;
                }
            }
            result = true ;
        }
        return result ;
    }
}
