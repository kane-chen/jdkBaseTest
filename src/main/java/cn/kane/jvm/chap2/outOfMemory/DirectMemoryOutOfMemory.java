package cn.kane.jvm.chap2.outOfMemory;

import java.lang.reflect.Field;

import sun.misc.Unsafe;


/**
 * @category  Exception in thread "main" java.lang.OutOfMemoryError at sun.misc.Unsafe.allocateMemory(Native Method)
 *  JVM-args: -XX:MaxDirectMemorySize=10M
 * @author chenqx
 *
 */
public class DirectMemoryOutOfMemory {

	/**
	 * @param args
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException {
		Field unsafeField = Unsafe.class.getDeclaredFields()[0];
		unsafeField.setAccessible(true);
		Unsafe unsafe = (Unsafe)unsafeField.get(null);
		while(true)
			unsafe.allocateMemory(1024*1024);
	}

}
