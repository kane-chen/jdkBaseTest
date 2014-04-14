package cn.kane.jvm.chap2.outOfMemory;

import java.util.ArrayList;
import java.util.List;

/**
 * @category java.long.OutOfMemoryError:Java heap space
 * run with jvm-args: [-Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError]
 * @author chenqx
 *
 */
public class HeapOutOfMemory {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<HeapOutOfMemory> lst = new ArrayList<HeapOutOfMemory>();
		while(true){
			lst.add(new HeapOutOfMemory());
		}
	}

}
