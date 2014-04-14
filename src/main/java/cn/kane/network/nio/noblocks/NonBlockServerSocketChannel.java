package cn.kane.network.nio.noblocks;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NonBlockServerSocketChannel {

	private Selector selector = null;
	private final int BUFFER_SIZE = 1024;

	public NonBlockServerSocketChannel(String serverIp, int serverPort)	throws IOException {
		selector = Selector.open();
		ServerSocketChannel serverSckChn = ServerSocketChannel.open();
		serverSckChn.socket().bind(new InetSocketAddress(serverIp, serverPort));
		serverSckChn.configureBlocking(false);
		serverSckChn.register(selector, SelectionKey.OP_ACCEPT);// serverSocketChannel's validOps is SelectionKey.OP_ACCEPT
		System.out.println("Server[" + serverIp + ":" + serverPort + "]'s runn-status:" + serverSckChn.isOpen() + ",blocking-mod:" + serverSckChn.isBlocking());
	}

	public void startListenning() throws IOException {
		ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
		System.out.println("startListenning is running");
		while (true) {
			synchronized (selector) {
					if (selector.select() == 0) {
						continue;//break others in this loop,enter next loop
					}
					Set<SelectionKey> selectedKeys = selector.selectedKeys();
					Iterator<SelectionKey> iterator = selectedKeys.iterator();
					while (iterator.hasNext()) {
						SelectionKey selectedKey = iterator.next();
						if (selectedKey.isAcceptable()) {
							System.out.println("socket accept");
							ServerSocketChannel serverSckChn = (ServerSocketChannel) selectedKey.channel();
							SocketChannel sckChn = serverSckChn.accept();
							sckChn.configureBlocking(false);
							sckChn.register(selector, SelectionKey.OP_READ	| SelectionKey.OP_WRITE);
							System.out.println("CONNECTED-"	+ sckChn.socket().getInetAddress());
						}
						if (selectedKey.isReadable()) {
							try {
								SocketChannel sckChn = (SocketChannel) selectedKey.channel();
								buffer.clear() ; // POINT: before using buffer,please clear first ; otherwise repeat!!!!
								sckChn.read(buffer);
								//ByteBuffer buffer = (ByteBuffer) selectedKey.attachment() ;
								byte[] bytes = buffer.array();
								System.out.println(new String(new String(bytes).getBytes("GBK"), "UTF-8"));
							}catch(java.io.UnsupportedEncodingException e){
								e.printStackTrace();
							} catch (IOException e) {
								selectedKey.cancel();
							}
						}
						iterator.remove();
					}
					
			}
		}
	}

	public void startWritingThread() {
		ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
		byte[] bytes = new byte[BUFFER_SIZE];
		int datasLen = -1;
		while (true) {
			try {
				while ((datasLen = System.in.read(bytes)) != -1) {
					buffer.clear() ;
					buffer = ByteBuffer.wrap(bytes, 0, datasLen);
					synchronized (selector) {
						if (selector.select() == 0) {
							continue;
						}
						Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
						while (iterator.hasNext()) {
							SelectionKey selectionKey = iterator.next();
							if (selectionKey.isWritable()) {
								SocketChannel sckChn = (SocketChannel) selectionKey.channel();
								sckChn.write(buffer);
								buffer.flip();
//								selectionKey.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
							}
							iterator.remove();
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		try {
			final NonBlockServerSocketChannel noblockServer = new NonBlockServerSocketChannel("127.0.0.1", 1007);
			new Thread(new Runnable() {
				public void run() {
					try {
						noblockServer.startListenning();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}, "READ").start();
			new Thread(new Runnable() {
				public void run() {
					noblockServer.startWritingThread();
				}
			}, "WRITE").start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
