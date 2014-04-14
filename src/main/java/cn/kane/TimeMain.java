/*
 * @(#)TimeMain.java	2013年10月10日
 *
 * @Company <www.lashou-inc.com>
 */
package cn.kane;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @Project base
 * @version 1.0
 * @Author  chenqx
 * @Date    2013年10月10日上午10:58:50
 * @description 
 */
public class TimeMain {

    public static void main(String[] args) throws ParseException {
//        long mtime = 1381198610167 ;
//        Date date = new Date(1381198610167l);
//        System.out.println(date);
        Date now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2013-11-05 11:00:03");
        Timestamp time = new Timestamp(now.getTime()+1) ;
        System.out.println(time.before(now));
        System.out.println(time.compareTo(now));
        
        System.out.println();
        System.out.println(System.currentTimeMillis());
//        
//        Long lon = new Long("3799") ;
//        System.out.println(lon.toString());
        
//        List<Integer> lst = new ArrayList<Integer>() ;
//        Integer in1 = new Integer(1);
//        lst.add(in1);
//        Integer in2 = new Integer(1);
//        lst.add(in2);
//        System.out.println(in1 == in2);
//        Set<Integer> s = new HashSet<Integer>(lst);
//        lst.clear();
//        lst.addAll(s);
//        System.out.println(lst.size());
        
//        String deptDescStr = "研发一部^网站前台，除机票、酒店、电影@研发二部^niu后台、商家后台、老后台、CRM@研发三部^V系统@研发五部^票务：机票、电影、酒店预订@研发六部^无线客户端、WAP、API@研发七部^BI、数据挖掘等@研发八部^ERP：人事PS、财务EBS@研发九部^网站前后台、WAP测试@研发十部^服务器、带宽、软硬件维护@test";
//        Map<String, String> deptNameMappings = null;
//        {
//            deptNameMappings = new HashMap<String, String>(10);
////            String deptDescStr = config.getDepartDescription() ;
//            if(null!=deptDescStr){
//                String[] deptDescArray = deptDescStr.split("@");
//                for(String deptDesc : deptDescArray){
//                    if(null!=deptDesc){
//                        String[] deptInfos = deptDesc.split("\\^");
//                        if(deptInfos.length==2 && null!=deptInfos[0] && null!=deptInfos[1])
//                            deptNameMappings.put(deptInfos[0], deptInfos[1]) ;
//                        else
//                            System.out.println("depart.description in crm.properties wrong-pattern:%s"+deptDescStr);
//                    }else{
//                        System.out.println("depart.description in crm.properties wrong-pattern:%s"+deptDescStr);
//                    }
//                }
//            }
//            
//        }
//        
//        System.out.println(deptNameMappings);
        
    }

}
