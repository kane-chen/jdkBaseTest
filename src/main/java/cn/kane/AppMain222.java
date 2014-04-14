/*
 * @(#)AppMain222.java	2013年10月23日
 *
 * @Company <Opportune Technology Development Company LTD.>
 */
package cn.kane;

import java.text.ParseException;


/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @Author  <Administrator>
 * @Date    <2013年10月23日>
 * @description 
 */
public class AppMain222 {

    public static void main(String[] args) throws ParseException{
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        String date = "2013-11-22 10:01:10";
//        System.out.println(sdf.parse(date));
//        Integer test = new Integer(-100);
//        if(test.compareTo(0)<0){
//            test = 0 ;
//        }
//        System.out.println(test);
//        String str1= "null" ;
//        String str2= null ;
//        System.out.println(str1.equals(str2));
//        System.out.println(Runtime.getRuntime().availableProcessors());
//        
//        str1 = " http://10.12.2.105:8080/api-crm//geturl " ;
//        System.out.println(str1.indexOf("//"));
//        System.out.println(AppMain222.formatUrl(str1,"http://"));
//        
//        String appStr = null + "," +"123" ;
//        System.out.println(appStr);
        
//        BigDecimal profit = null ;
//        BigDecimal sellingPrice = new BigDecimal(33) ;
//        BigDecimal settlementPrice = new BigDecimal(3) ;
//        try {
//            profit = sellingPrice.subtract(settlementPrice)
//                    .divide(sellingPrice, 4,  RoundingMode.UP);
//            profit = profit.multiply(new BigDecimal(100)) ;
//            profit = profit.setScale(2);
//        } catch (ArithmeticException e) {
//            e.printStackTrace();
//        }
//        System.out.println(profit);
        
//        Long lon = new Long(0);
//        System.out.println(lon == 0);
//        System.out.println(lon.equals(0));
//        Integer intg = new Integer(0);
//        System.out.println(intg.equals(0));
//        Character b = new Character('A');
//        System.out.println(b.equals(65));
//        
//        Short sh = new Short("2");
//        System.out.println(sh == 1);
//        sh = null ;
//        System.out.println(1 == sh);
       
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd") ;
//        String startDateStr = "20131010" ;
//        String endDateStr = "20131210" ;
//        String toDateStr = "20140101" ;
//        Integer validDays = -1 ;
//        Date startDate = sdf.parse(startDateStr);
//        Date toDate = sdf.parse(toDateStr);
//        Date endDate = null ;
//        if(null!=validDays){
//            GregorianCalendar calendar = new GregorianCalendar() ;
//            endDate = calendar.getTime() ;
//            System.out.println(sdf.format(endDate));
//            if(-1 == validDays){
//                calendar.setTime(toDate);
//                calendar.add(Calendar.DAY_OF_YEAR, validDays);
//            }else{
//                calendar.setTime(startDate);
//                calendar.add(Calendar.DAY_OF_YEAR, validDays);
//            }
//            endDate = calendar.getTime() ;
//            System.out.println(sdf.format(endDate));
//        }
//        if(null!=endDateStr){
//            endDate = sdf.parse(endDateStr);
//            validDays =(int) (endDate.getTime() - startDate.getTime())/(24*60*60*1000);
//            System.out.println(validDays);
//        }
//        
//        System.out.println(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        
        Long id = null ;
//        System.out.println(id.toString());
        id = 0l ;
        System.out.println(id.toString());
        String str = "6";
        String array = "1,2,3,5," ;
        String[] mtchIdArray = array.split(",");
        boolean contains = false ;
        for(String mtch : mtchIdArray){
            if(str.equals(mtch)){
                contains = true ;
                break ;
            }
        }
        if(!contains){
            array = array +","+ str ;
            array = array.replaceAll(",,", ",");
            System.out.println(array);
        }
        System.out.println(array);
    }
    
    

    private static String formatUrl(String url,String prev){
        String formatedUrl = null ;
        if(null!=url){
            formatedUrl = url.trim() ;
            int index = formatedUrl.indexOf(prev); 
            System.out.println(index);
            String subUrl = formatedUrl.substring(index+prev.length());
            subUrl = subUrl.replaceAll("//", "/");
            System.out.println(subUrl);
            formatedUrl = prev + subUrl ;
        }
        return formatedUrl ;
    }

}
