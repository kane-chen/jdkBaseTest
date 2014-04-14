/*
 * @(#)IneBytesConvertUtils.java	2013年10月8日
 *
 * @Company <www.lashou-inc.com>
 */
package cn.kane;


/**
 * @Project base
 * @version 1.0
 * @Author  chenqx
 * @Date    2013年10月8日下午4:22:47
 * @description 
 */
public class IneBytesConvertUtils {

    public static byte[] int2Bytes(int org){
        byte[] bytes = new byte[4] ;
        for(int i=0;i<4;i++)
            bytes[4-1-i] = (byte)(org >> 8*i) ;
        return bytes ;
    }
    
    public static int bytes2Int(byte[] bytes){
        int result = 0 ;
        for(int i=0;i<4;i++)
            //result += (bytes[i] & 0xffff) << 8*i ;
            result += bytes[i] << 8*(4-i-1) ;
        return result ;
    }
    
    public static void main(String[] args){
        int testInt = 256 ;
        byte[] bytes = int2Bytes(testInt) ;
        for(byte b : bytes){
            System.out.print(b);
        }
        System.out.println("");
        System.out.println(bytes2Int(bytes) );
        
        int testInteger = 256 ;
        System.out.println(bytes2Int(int2Bytes(testInteger)) == testInteger);

    }

}
