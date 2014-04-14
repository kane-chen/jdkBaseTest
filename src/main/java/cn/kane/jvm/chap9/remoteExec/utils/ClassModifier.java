package cn.kane.jvm.chap9.remoteExec.utils;


public class ClassModifier {

	private static final int CONSTANT_POOL_INDEX = 8 ;//constant-pool-start-index
	private static final int CONSTANT_UTF8_INFO = 1 ; //utf8-info's tag
	private static final int[] CONSTANT_ITEM_LENGTH = {-1,-1,5,-1,5,9,9,3,3,5,5,5,5};
	private static final int u1 = 1 ;
	private static final int u2 = 2 ;
	
	private byte[] classBytes ;
	
	public ClassModifier(byte[] classBytes){
		this.classBytes = classBytes ;
	}
	
	public byte[] modifyUtf8Constant(String oldStr,String newStr){
		int constsInPool = ByteUtils.bytes2Int(classBytes, CONSTANT_POOL_INDEX, u2) ;
		int constOffsetIndex = CONSTANT_POOL_INDEX + u2 ;
		for(int i=0 ;i <constsInPool;i++){
			int tag = ByteUtils.bytes2Int(classBytes, constOffsetIndex, u1);
			if(tag == CONSTANT_UTF8_INFO){
				int length = ByteUtils.bytes2Int(classBytes, constOffsetIndex+u1, u2);
				constOffsetIndex += (u1+u2) ;
				String value = ByteUtils.bytes2String(classBytes, constOffsetIndex, length);
				if(value.equalsIgnoreCase(oldStr)){
					byte[] newBytes = ByteUtils.string2Bytes(newStr);
					byte[] lenBytes = ByteUtils.int2Bytes(newStr.length(), u2);
					classBytes = ByteUtils.replaceBytes(classBytes,constOffsetIndex-u2, u2, lenBytes);
					classBytes = ByteUtils.replaceBytes(classBytes, constOffsetIndex, length, newBytes);
				}else{
					constOffsetIndex += length ;
				}
			}else if(tag<13){
				constOffsetIndex += CONSTANT_ITEM_LENGTH[tag] ;
			}
		}
		return classBytes ;
	}
	
}
