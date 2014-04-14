/*
 * @(#)SyncFlagMain.java	2013年10月13日
 *
 * @Company <Opportune Technology Development Company LTD.>
 */
package cn.kane;

/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @Author  <Administrator>
 * @Date    <2013年10月13日>
 * @description 
 */
public class SyncFlagMain {

    public void sycnPrint(){
        Print print = new Print() ;
        new Thread(new NumThread(print)).start();
        new Thread(new CharThread(print)).start();
    }
    
    public static void main(String[] args) {
        new SyncFlagMain().sycnPrint();
    }
    
    class Print{
        int num ;
        char c = 'a' ;
        volatile boolean numPrint = true ;
        
        public synchronized void printNum(){
            if(numPrint){
                System.out.println(num++);
                numPrint = false ;
            }
        }
        
        public synchronized void printChar(){
            if(!numPrint){
                System.out.println(c++);
                numPrint = true ;
            }
        }
    }
    
    class NumThread implements Runnable{
        private Print print ;
        NumThread(Print print){
            this.print = print ;
        }
        public void run() {
            while(print.num<=26)
                print.printNum();
        }
    }
    
    class CharThread implements Runnable{
        private Print print ;
        CharThread(Print print){
            this.print = print ;
        }
        public void run() {
            while(print.c <= 'z')
                print.printChar();
        }
    }
}
