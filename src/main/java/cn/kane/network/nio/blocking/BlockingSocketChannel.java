package cn.kane.network.nio.blocking;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class BlockingSocketChannel {

	private final int BUFFER_SIZE = 1024 ;
	private SocketChannel sckChn = null ;
	
	public BlockingSocketChannel(String serverIp,int serverPort) throws IOException{
		sckChn = SocketChannel.open() ; 
		sckChn.configureBlocking(true) ;
		boolean connected = sckChn.connect(new InetSocketAddress(serverIp, serverPort));
		if(connected){
			System.out.println("connect successful");
		}else{
			System.out.println("connect failed");
		}
	}
	
	public void startWriteMode() throws IOException{
		
		byte[] bytes = new byte[BUFFER_SIZE] ;
		int datasLen = -1 ;
		while((datasLen = System.in.read(bytes)) != -1){
			sckChn.write(ByteBuffer.wrap(bytes, 0, datasLen) );
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
        try {
			new BlockingSocketChannel("127.0.0.1",1002).startWriteMode();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
