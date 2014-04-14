package cn.kane.jvm.chap9.remoteExec;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import cn.kane.jvm.chap9.remoteExec.utils.ClassModifier;
import cn.kane.jvm.chap9.remoteExec.utils.HotSwapClassLoader;

public class ClassExecutor {


	public static void execute(byte[] clazzByte) {
		ClassModifier clazzModifier = new ClassModifier(clazzByte);
		byte[] newClazzBytes = clazzModifier.modifyUtf8Constant("java/lang/System", "cn/kane/chap9/remoteExec/HackSystem");
		Class<?> clazz = new HotSwapClassLoader().loadByte(newClazzBytes);
		try {
			Method method = clazz.getDeclaredMethod("printCurrentMills", String.class);
			Object obj = clazz.newInstance();
			method.invoke(obj, "KANE");
			System.out.println(HackSystem.getBufferString());
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException{
		InputStream inStream = ClassExecutor.class.getResourceAsStream("CurrentMills.class");
		byte[] classBytes = new byte[inStream.available()];
		inStream.read(classBytes);
		execute(classBytes);
	}
	
}
