package cn.kane.jvm.chap2.outOfMemory;

/**
 * @category java.lang.StackOverflowError
 * JVM-args: -Xss128k
 * @author chenqx
 *
 */
public class JvmStackOverflow {

	private int stackLen = 0 ;
	
	public void recurse(){
		stackLen++;
		recurse();
	}
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Throwable {
		JvmStackOverflow instance = new JvmStackOverflow();
		try {
			instance.recurse();
		} catch (Throwable e) {
			System.out.println("StackLength:"+instance.getStackLen());
			throw e ;
		}
	}

	public int getStackLen() {
		return stackLen;
	}

	void setStackLen(int stackLen) {
		this.stackLen = stackLen;
	}

}
