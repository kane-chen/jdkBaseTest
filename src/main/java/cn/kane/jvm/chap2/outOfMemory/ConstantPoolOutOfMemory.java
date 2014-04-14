package cn.kane.jvm.chap2.outOfMemory;

import java.util.ArrayList;
import java.util.List;

/**
 * @category java.lang.OutOfMemoryError: PermGen space
 * 	JVM-args: -XX:PermSize=10m -XX:MaxPermSize=10M
 * @author chenqx
 *
 */
public class ConstantPoolOutOfMemory {

	public void createStringAndPutInPool(){
		List<String> strLst = new ArrayList<String>();
		int i = 0 ;
		while(true){
			strLst.add(String.valueOf(i++).intern());
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new ConstantPoolOutOfMemory().createStringAndPutInPool();
	}

}
