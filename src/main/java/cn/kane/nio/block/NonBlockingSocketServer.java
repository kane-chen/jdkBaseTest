package cn.kane.nio.block;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

public class NonBlockingSocketServer {

	private static Selector selector = null ;
	private static NonBlockingSocketServer instance = null ;
	private int serverPort = 2000 ;
	
	private NonBlockingSocketServer() throws IOException{
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		ServerSocket serverSocket = serverSocketChannel.socket() ;
		serverSocket.bind(new InetSocketAddress(serverPort));
		serverSocketChannel.configureBlocking(false);
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
	}
	
	public static NonBlockingSocketServer getInstance() throws IOException{
		if(null == selector){
			selector = Selector.open();
			instance = new NonBlockingSocketServer() ;
		}
		return instance ;
	}
	
	public void startSocketServer() throws IOException{
		int todoTasks = selector.select();
		while(todoTasks > 0 ){
			Set<SelectionKey> keys = selector.selectedKeys();
			Iterator<SelectionKey> iterator = keys.iterator();
			while(iterator.hasNext()){
				SelectionKey key = iterator.next() ;
				iterator.remove();//can not do this
				if(key.isAcceptable()){
					ServerSocketChannel serverSocketChannel = (ServerSocketChannel)key.channel();
					SocketChannel socket = serverSocketChannel.accept() ;
					socket.configureBlocking(false);
					socket.register(selector, SelectionKey.OP_READ);
				}else if(key.isReadable()){
					SocketChannel socketChannel = (SocketChannel)key.channel();
					ByteBuffer buffer = ByteBuffer.allocate(1024);
					socketChannel.read(buffer);
					CharBuffer charBuffer = Charset.forName("GBK").decode(buffer);
					System.out.println(charBuffer);
				}
				
			}
		}
	}
	
	public static void main(String[] args){
		try {
			NonBlockingSocketServer.getInstance().startSocketServer();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
