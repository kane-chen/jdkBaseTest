package cn.kane;

import java.io.UnsupportedEncodingException;

public class EnCodeApp {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String str ="{1000} {0} {SUCCESS} {{13857390091} {{{54791009} {鏍″洯WLAN10鍏儅} {{54001001} {WLANP}} {{54416002} {WLAN30}}} {{{207180} {涓殅} {{207181} {涓殅} {{202071} {鍛?{{206393} {1101涓€} {{{6905061} {WLAN鏃秢1800.0} {0.0} { {{6033391} {鏍″洯WLAN鏃秢2400.0} {0.0} {} {657}} ";
	    String sourceStr = "OUTSTRXML=<?xml version='1.0' encoding='ISO8859-1'?>  <ATMITASK>    <RET_INFO>     <FREERES_INFO>       <USED_SUM>0.0</USED_SUM>       <RES_NAME>WLAN上网免费时长</RES_NAME>       <UNIT>分</UNIT>       <ALL_SUM>1800.0</ALL_SUM>       <RES_ID>6905061</RES_ID>     </FREERES_INFO>     <FREERES_INFO>       <USED_SUM>0.0</USED_SUM>       <RES_NAME>校园WLAN上网免费时长</RES_NAME>       <UNIT>分</UNIT>       <ALL_SUM>2400.0</ALL_SUM>       <RES_ID>6033391</RES_ID>     </FREERES_INFO>     <BILL_ID>13857390091</BILL_ID>     <FEE_SUM>789</FEE_SUM>     <PROM_INFO>       <PROM_ID>207180</PROM_ID>       <PROM_NAME>生活舒心包专业促销</PROM_NAME>     </PROM_INFO>     <PROM_INFO>       <PROM_ID>207181</PROM_ID>       <PROM_NAME>上网贴心包专业促销</PROM_NAME>     </PROM_INFO>     <PROM_INFO>       <PROM_ID>202071</PROM_ID>       <PROM_NAME>员工优惠</PROM_NAME>     </PROM_INFO>     <PROM_INFO>       <PROM_ID>206393</PROM_ID>       <PROM_NAME>1101手机电视优惠一年</PROM_NAME>     </PROM_INFO>     <FEE_INFO>       <ITEM_NAME>WLAN月费</ITEM_NAME>       <ITEM_ID>41004750</ITEM_ID>       <FEE>789</FEE>     </FEE_INFO>     <PROD_INFO>       <PROD_ID>54791009</PROD_ID>       <PROD_NAME>校园WLAN10元套餐</PROD_NAME>     </PROD_INFO>     <PROD_INFO>       <PROD_ID>54001001</PROD_ID>       <PROD_NAME>WLAN国内（不含港澳台）WAP认证</PROD_NAME>     </PROD_INFO>     <PROD_INFO>       <PROD_ID>54416002</PROD_ID>       <PROD_NAME>WLAN无线上网30套餐</PROD_NAME>     </PROD_INFO>   </RET_INFO>    <ERR_INFO>     <ERROR_HINT>SUCCESS</ERROR_HINT>     <ERROR_CODE>0</ERROR_CODE>     <ERROR_MSG>SUCCESS</ERROR_MSG>   </ERR_INFO>  </ATMITASK>";
	    
		try {
			String nmStr = new String(sourceStr.getBytes("GBK"),"GBK");
			System.out.println(nmStr);
			String tStr = new String(nmStr.getBytes("GBK"),"GBK");
			System.out.println(tStr);
			String encode = new String(str.getBytes(), "UTF-16LE");
			System.out.println(encode);
			System.out.println(System.getProperty("java.endorsed.dirs"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
   
	
}
