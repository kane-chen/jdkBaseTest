/*
 * @(#)BigNumAdd.java	2013年10月9日
 *
 * @Company <www.lashou-inc.com>
 */
package cn.kane;


/**
 * @Project base
 * @version 1.0
 * @Author  chenqx
 * @Date    2013年10月9日上午10:54:40
 * @description 
 */
public class BigNumAdd {

    public static void main(String[] args) {
        String str1 = "999999" ;
        String str2 = "222222222" ;
        System.out.println(addBigNum(str1,str2));
    }
    
    public static String addBigNum(String str1,String str2){
        StringBuffer result = new StringBuffer() ;
        
        boolean ascOrder = str1.length() < str2.length() ;
        int minLength = ascOrder ? str1.length() : str2.length() ;
        int maxLength = ascOrder ? str2.length() : str1.length() ;
        int[] array = new int[maxLength] ;
        for(int i=0; i<minLength ; i++){
            int add1 = str1.charAt(i) - 48 ;
            int add2 = str2.charAt(i) - 48 ;
            array[i] = add1+add2 ;
        }
        
        String biggerNum = ascOrder ? str2 : str1 ;
        for(int i= minLength ; i<maxLength ;i++){
            array[i] = biggerNum.charAt(i) - 48 ;
        }
        
        for(int i=0 ; i<maxLength  ; i++){
            if(i < maxLength-1){
                if(array[i]>=10){
                    int carry = array[i]/10 ;
                    int left = array[i] % 10 ;
                    
                    array[i+1] += carry ;
                    array[i] = left ;
                }
            }
        }
        
        for(int i=maxLength-1 ; i>=0  ;i--){
            result.append(array[i]) ;
        }
        return result.toString() ;
    }
}
