package cn.kane;

public class AppMainTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

//		String patternStr = "\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b";
//		String ipStr = "127.256.0.11111";
//		
//		System.out.println(Pattern.matches(patternStr, ipStr));
//		
//		String en ="ww@!";
//		String chn ="中文字符";
//		System.out.println(en.length());
//		System.out.println(chn.length());
		
		int retryTimes = 0 ; //using for check-connect-valid
		while(retryTimes<=1){
			try {
				int i= 3/0 ;
				System.out.println(i);
				break ;
			} catch (Exception e) {
				System.out.println("1111111");
				retryTimes ++ ;
				if(retryTimes <= 1){
					System.out.println("reconn");
				}else{
					System.out.println("222222");
				}
			}
		}
	}

}
