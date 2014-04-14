/*
 * @(#)ByteTest.java	2013年11月11日
 *
 * @Company <Opportune Technology Development Company LTD.>
 */
package cn.kane;


/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @Author  <Administrator>
 * @Date    <2013年11月11日>
 * @description 
 */
public class ByteTest {

    public static void main(String[] args) {
        char c = '&' ;
        ByteTest.char2Bytes(c) ;
    }

    public static byte[] char2Bytes(char c){
        byte[] bytes = new byte[2] ;
        bytes[0] = (byte)(c<< 8 & 0xff) ;
        bytes[1] = (byte)(c & 0xff) ;
        System.out.println(bytes[0]);
        System.out.println(bytes[1]);
        return bytes ;
    }
}
