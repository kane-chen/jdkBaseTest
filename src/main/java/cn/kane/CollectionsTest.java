/*
 * @(#)CollectionsTest.java	2013年10月9日
 *
 * @Company <www.lashou-inc.com>
 */
package cn.kane;

import java.util.ArrayList;
import java.util.List;


/**
 * @Project base
 * @version 1.0
 * @Author  chenqx
 * @Date    2013年10月9日下午3:31:30
 * @description 
 */
public class CollectionsTest {

    public static void main(String[] args) {
        List<String> ls1 = new ArrayList<String>();
        ls1.add("1");
        ls1.add("2");
        ls1.add("3");
        ls1.add("4");
        List<String> ls2 = null ;
//        ls2.add("3");
//        ls2.add("4");
//        ls2.add("5");
        List<String> ls3 = new ArrayList<String>();
        ls3.addAll(ls1);
        ls3.addAll(ls2);
        for(String str : ls3)
            System.out.println(str);
    }

}
