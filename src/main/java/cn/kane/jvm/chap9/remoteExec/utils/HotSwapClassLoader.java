package cn.kane.jvm.chap9.remoteExec.utils;

public class HotSwapClassLoader extends ClassLoader {

	public HotSwapClassLoader(){
		super(HotSwapClassLoader.class.getClassLoader());
	}
	
	public Class<?> loadByte(byte[] classBytes){
		return this.defineClass(null, classBytes, 0, classBytes.length) ;
	}
	
}
