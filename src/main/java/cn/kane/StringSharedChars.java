/*
 * @(#)StringSharedChars.java	2013年10月30日
 *
 * @Company <Opportune Technology Development Company LTD.>
 */
package cn.kane;


/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @Author  <Administrator>
 * @Date    <2013年10月30日>
 * @description 
 */
public class StringSharedChars {

    public static void main(String[] args) {
        String originStr = "hello-world" ;
        String subStr = originStr.substring(6, 11);
        System.out.println(subStr);//subStr.value[] == originStr.value[],offset diff
    }

}
