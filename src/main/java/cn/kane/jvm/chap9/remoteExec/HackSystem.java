package cn.kane.jvm.chap9.remoteExec;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

public class HackSystem {

	public static final InputStream in = System.in ;
	private static final ByteArrayOutputStream buffer = new ByteArrayOutputStream() ;
	public static final PrintStream out = new PrintStream(buffer);
	public static final PrintStream error = out ;
	
	
	
	public static String getBufferString(){
		return buffer.toString();
	}
	public static void cleanBuffer(){
		buffer.reset() ;
	}
	public static void setSecurityManager(SecurityManager secMan){
		System.setSecurityManager(secMan);
	}
	public static SecurityManager getSecurityManager(){
		return System.getSecurityManager() ;
	}
	public static long currentTimeMillis(){
		System.out.println("--------HACK-SYSTEM-------");
		return System.currentTimeMillis();
	}
	public void arrayCopy(Object srcObj,int offset,Object targetObj,int toffset,int length){
		System.arraycopy(srcObj, offset, targetObj, toffset, length);
	}
	public static int identityHashCode(Object x){
		return System.identityHashCode(x);
	}
}
