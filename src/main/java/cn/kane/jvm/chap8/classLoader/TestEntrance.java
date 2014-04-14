package cn.kane.jvm.chap8.classLoader;

import java.io.File;
import java.lang.reflect.Method;

import cn.kane.jvm.Announce;
import cn.kane.jvm.chap8.classLoader.selfLoad.SayHiClient;


public class TestEntrance {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		String loaderDir = new File(System.getProperty("user.dir")).getAbsolutePath()+File.separator+"bin";
		String jarDir = new File(System.getProperty("user.dir")).getAbsolutePath();
		ClassLoader classLoader = ClassLoaderFactory.createClassLoader(new File[]{new File(loaderDir)}, new File[]{new File(jarDir)}, null,false);
		Thread.currentThread().setContextClassLoader(classLoader);
		new SayHiClient().sayHi("class-loader test");
		new Announce().announce();//superLoader load-announce
		Class<?> testJarClazz = classLoader.loadClass("cn.kane.TestJarSay");
		Method sayMethod = testJarClazz.getDeclaredMethod("sayHi",new Class[]{String.class});
		sayMethod.invoke(testJarClazz.newInstance(),"test-jar-class-loader");// it can work with jar without compiled-class
//		cn.kane.TestJarSay testJar = new cn.kane.TestJarSay() ;
//		testJar.sayHi("test-jar-class-loader");//it cannot work without compile-class
	}

}
