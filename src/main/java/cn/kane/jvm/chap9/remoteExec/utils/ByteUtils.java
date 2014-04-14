package cn.kane.jvm.chap9.remoteExec.utils;

public class ByteUtils {

	public static int bytes2Int(byte[] bytes,int start,int length){
		int sum = 0 ;
		int end = start + length ;
		for(int i = start ;i< end;i++){
			int temp = ((int)bytes[i]) & 0xff ;
			temp <<= (--length)*8 ;
			sum = sum + temp ;
		}
		return sum ;
	}
	
	public static byte[] int2Bytes(int value,int length){
		byte[] result = new byte[length];
		for(int i=0 ; i<length;i++){
			result[length-i-1] = (byte)((value >> i*8 )& 0xff );
		}
		return result ;
	}
	
	public static String bytes2String(byte[] bytes,int start,int length){
		return new String(bytes,start,length);
	}
	
	public static byte[] string2Bytes(String value){
		return value.getBytes();
	}
	
	public static byte[] replaceBytes(byte[] orgiBytes,int start,int length,byte[] replaceBytes){
		byte[] newBytes = new byte[orgiBytes.length-length+replaceBytes.length] ;
		System.arraycopy(orgiBytes, 0, newBytes,0, start);// Orgi[0-start] >> new
		System.arraycopy(replaceBytes, 0, newBytes, start, replaceBytes.length);
		System.arraycopy(orgiBytes, start+length, newBytes, start+replaceBytes.length, orgiBytes.length-length-start);
		return newBytes ;
	}
}
