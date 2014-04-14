package cn.kane.network.nio.blocking;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class BlockingServerSocketChannel {

//	private int serverPort = -1 ;
//	private String serverIp = null ;
	private ServerSocketChannel serverSckChn = null ;
	
	private final int BUFFER_SIZE = 1024 ;
	
	public BlockingServerSocketChannel(String serverIp,int serverPort) throws IOException{
//		this.serverIp = serverIp ;
//		this.serverPort = serverPort ;
		serverSckChn = ServerSocketChannel.open() ;
		serverSckChn.configureBlocking(true);
		serverSckChn.socket().bind(new InetSocketAddress(serverIp, serverPort)) ;
	}
	
	public void startListenningMode() throws IOException{
		while(true){
			final SocketChannel sckChn = serverSckChn.accept() ;
			new Thread(new Runnable(){
				public void run() {
					while(true){
						ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE) ;
						int datasLength;
						try {
							datasLength = sckChn.read(buffer);
							System.out.println(datasLength);
							byte[]  bytes = buffer.array();
//							String result = new String(bytes,"GBK") ;
							String result = new String(new String(bytes).getBytes("GBK"),"UTF-8") ;
							System.out.println("REC:"+result);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				
			},"READ-"+sckChn.socket().getInetAddress()).start();
		}
	}
	
	public static void main(String[] args){
		try {
			new BlockingServerSocketChannel("127.0.0.1",1002).startListenningMode() ;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
