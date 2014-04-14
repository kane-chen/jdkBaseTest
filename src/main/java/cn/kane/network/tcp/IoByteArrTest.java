package cn.kane.network.tcp;

import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.Arrays;

import org.apache.log4j.Logger;

import junit.framework.TestCase;

public class IoByteArrTest extends TestCase {

	private final static Logger log = Logger.getLogger(IoByteArrTest.class);
	private final int BUFFER_SIZE = 1024;

	private static void outputCharset() {
		Constructor<?>[] ctors = Console.class.getDeclaredConstructors();
		Constructor<?> ctor = null;
		for (int i = 0; i < ctors.length; i++) {
			ctor = ctors[i];
			if (ctor.getGenericParameterTypes().length == 0)
				break;
		}
		try {
			ctor.setAccessible(true);
			Console c = (Console) ctor.newInstance();
			Field f = c.getClass().getDeclaredField("cs");
			f.setAccessible(true);
			System.out.println(String.format("Console charset:%s", f.get(c)));
			System.out.println(String.format("Charset.defaultCharset():%s",	Charset.defaultCharset()));
		} catch (Exception x) {
			x.printStackTrace();
		}
	}

	public void testConsoleCharset() {
		outputCharset();
		System.out.println("1234中文5678");
	}

	public void testIoByte() throws IOException {
		log.info("try to start test read&write byte-array within io-stream");
		File storedFile = new File("tempFile.txt");
		if (storedFile.exists()) {
			storedFile.delete();
		}
		storedFile.createNewFile();
		

		System.out.println(System.getProperty("file.encoding"));

	}
	
	public void testReadIoStream(){
		byte[] buffer = new byte[BUFFER_SIZE];
		try {
			int bytesLength = 0 ;
			/*
			System.in.read(bufferArr):
			    if System.in's length is longer than bufferArr's length;
			    System.in will be split pieces(max-length is bufferArr's length)
			*/
			while ( (bytesLength = System.in.read(buffer)) != -1 ) {
				//System.out.println(buffer);
				//System.out.println(new String(buffer,"UTF-8"));
				//System.out.println(new String(new String(buffer).getBytes("GBK"), "UTF-8"));
				/*
				 * InputStream from System.in charSet is console's charSet[GBK]
				 * Arrays.copyOfRange(bytes[],0,x) get only data in array 
				*/
				String inStr = new String(new String(Arrays.copyOfRange(buffer, 0, bytesLength)).getBytes("GBK"), "UTF-8") ;
				System.out.println(inStr + "&END");//inStr contains [\r\n]
				if(inStr.endsWith("\r\n")){
					inStr = inStr.replaceFirst("\r\n", "");
				}
				System.out.println(inStr+"&END");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void testReadBuffer(){
		String message = null ;
		BufferedReader reader = null ;
		try {
			reader = new BufferedReader(new InputStreamReader(System.in)) ;
			while((message = reader.readLine()) != null ) {
				System.out.println(message+"&END");//message not contains \r\n,but char-set is wrong
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(null!=reader){
				try {reader.close();} catch (IOException e) {}
			}
		}
	}

	public void testEncoding(){
		String str = "服务器";
		try {
			byte[] bytes = str.getBytes("GBK");
			System.out.println(new String(new String(bytes).getBytes("GBK"),"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
