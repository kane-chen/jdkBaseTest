package cn.kane;
import org.apache.log4j.Logger;


public class EncodingApp {

	/**
	 * Encoding :
	 *   1. *.java  >>> file's encoding [editing]
	 *   2. compile (*.java -> *.class): 
	 *                 the same to *.java's encoding; 
	 *                 u can use [ javac -encoding xxx *.java ]
	 *   2. *.class >>> it's UNICODE(no-special-encoding)
	 *   3. JVM     >>> -Dfile.encoding=[***]
	 *                  I. [***] should be the same to OS's encoding,otherwise log's messy code
	 *                  II.-Dfile.encoding=***; read input(as files) with ***;and write output(as console,files) with ***
	 *   4. log     >>> log's encoding ; 
	 *                  like -Dfile.encoding : write informations(content from JVM are UNICODE)) with ***
	 */
	
	
	//javac -cp .;../lib/log4j-1.2.14.jar -encoding utf-8 EncodingApp.java
	//java -cp .;../lib/log4j-1.2.14.jar -Dfile.encoding=utf-8  EncodingApp
	private static final Logger log = Logger.getLogger(EncodingApp.class) ;
	
	
	
	public static void main(String[] args){
		System.out.println("ENGLISH CHARACTOR");
		System.out.println("中文字符测试——CHINESE");
		log.info("中文LOG测试");
	}
}
