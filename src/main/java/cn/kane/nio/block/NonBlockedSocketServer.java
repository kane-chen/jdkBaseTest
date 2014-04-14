package cn.kane.nio.block;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

public class NonBlockedSocketServer {

	private static Selector selector = null ;
	private static NonBlockedSocketServer instance = null ;

	private int serverPort = 2000 ;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			NonBlockedSocketServer.getInstance().startSocketServer();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	private void startSocketServer() throws IOException {
        while(selector.select()>0){
        	Set<SelectionKey> keysSet = selector.selectedKeys() ;
        	Iterator<SelectionKey> iterator = keysSet.iterator() ;
        	while(iterator.hasNext()){
        		SelectionKey key = iterator.next() ;
        		iterator.remove() ;
        		if(key.isAcceptable()){
        			ServerSocketChannel serverChn = (ServerSocketChannel)key.channel() ;
        			SocketChannel socketChn = serverChn.accept() ;
        			socketChn.configureBlocking(false);
        			socketChn.register(selector, SelectionKey.OP_READ);
        		}else if(key.isReadable()){
        			SocketChannel socketChn = (SocketChannel)key.channel() ;
        			ByteBuffer buffer = ByteBuffer.allocate(1024);
        			socketChn.read(buffer);
        			buffer.flip();
        			String recStr = Charset.forName("GBK").decode(buffer).toString();
        			if(!"".equals(recStr))
        			  System.out.println("###"+recStr);
        		}
        	}
        }
	}

	private static NonBlockedSocketServer getInstance() throws IOException {
		System.out.println("------------GET SERVER INSTANCE-----------");
		if(null == selector){
        	 selector = Selector.open() ;
        	 instance = new NonBlockedSocketServer();
         }
		return instance ;
	}
   
	private NonBlockedSocketServer() throws IOException{
		ServerSocketChannel serverChn = ServerSocketChannel.open();
		ServerSocket serverSocket = serverChn.socket() ;
		serverSocket.bind(new InetSocketAddress(serverPort));
		serverChn.configureBlocking(false);
		serverChn.register(selector, SelectionKey.OP_ACCEPT);
		System.out.println("==============SERVER START UP==============");
	}
}
