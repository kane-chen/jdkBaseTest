package cn.kane.network.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.charset.Charset;

import junit.framework.TestCase;

public class NioBufferTest extends TestCase {
	
	public void testWrite() throws IOException{
		FileOutputStream fileOutStream  = null ;
		FileChannel fileChannel = null ;
		ByteBuffer byteBuffer = ByteBuffer.allocate(1024) ;
		byteBuffer = ByteBuffer.wrap("TEST-STRING".getBytes());
		try{
			fileOutStream = new FileOutputStream(new File("d:\\aaa.txt"));
			fileChannel = fileOutStream.getChannel();
			FileLock fileLock = fileChannel.tryLock();
			if(null!=fileLock){
				if(fileLock.isValid()){
					fileChannel.write(byteBuffer);
					byteBuffer.flip();
					System.out.println("DONE");
				}else{
					System.out.println("NO VALID LOCK");
				}
			}else{
				System.out.println("OTHER PROCESS OPERATE IT");
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			fileChannel.close();
			fileOutStream.close();
		}
	}


	public void testRead()throws IOException{
		FileInputStream fileInStream  = null ;
		FileChannel fileChannel = null ;
		ByteBuffer byteBuffer = ByteBuffer.allocate(1024) ;
		System.out.println("----------begin---------");
		try{
			fileInStream = new FileInputStream(new File("d:\\aaa.txt"));
			fileChannel = fileInStream.getChannel();
			fileChannel.read(byteBuffer);
			System.out.println("==========DONE========");
			CharBuffer charBuffer = Charset.forName("UTF-8").decode(byteBuffer);
			System.out.println(charBuffer);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			fileChannel.close();
			fileInStream.close();
		}
	}
	
}
