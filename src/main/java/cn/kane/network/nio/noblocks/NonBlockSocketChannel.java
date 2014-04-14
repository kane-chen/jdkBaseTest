package cn.kane.network.nio.noblocks;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
//UNDO
//selector is not suit for socket-client,this class can not work
public class NonBlockSocketChannel {

	private final int BUFFER_SIZE = 1024;
	private Selector selector = null;

	public NonBlockSocketChannel(String serverIp, int serverPort)throws IOException {
		
		selector = Selector.open();
		SocketChannel socketChn = SocketChannel.open();
		socketChn.configureBlocking(false);
		socketChn.connect(new InetSocketAddress(serverIp, serverPort));
		socketChn.register(selector, SelectionKey.OP_CONNECT | SelectionKey.OP_READ | SelectionKey.OP_WRITE);// read&write are all listening
	}

	public void startListening() throws IOException {
		ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
		while (true) {
			synchronized (selector) {
					if (selector.select() == 0) {
						continue;
					}
					Set<SelectionKey> selectedKeys = selector.selectedKeys();
					Iterator<SelectionKey> iterator = selectedKeys.iterator();
					while (iterator.hasNext()) {
						SelectionKey selectorKey = iterator.next();
						if(selectorKey.isConnectable()){
							if (selectorKey.isReadable()) {
								try {
									SocketChannel sckChn = (SocketChannel) selectorKey.channel();
									buffer.clear() ;
									sckChn.read(buffer);
									byte[] bytes = buffer.array() ;
									System.out.println("REC" + new String(bytes));
								} catch (IOException e) {
									selectorKey.cancel() ;
									e.printStackTrace();
								}
							}
						}
						iterator.remove();
					}
			}
		}
	}

	public void startWriting() {
		ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
		byte[] bytes = new byte[BUFFER_SIZE];
		int datasLen = -1;
		while (true) {
			try {
				while ((datasLen = System.in.read(bytes)) != -1) {
					synchronized (selector) {
						if (selector.select() == 0) { //
							continue;
						}
						buffer.clear() ;
						buffer = ByteBuffer.wrap(bytes, 0, datasLen);
						Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
						while (iterator.hasNext()) {
							if (selector.select() == 0) {
								continue;
							}
							SelectionKey selector = iterator.next();
							if (selector.isWritable()) {
								// ByteBuffer buffer = (ByteBuffer)selector.attachment() ;
								// buffer.put(bytes, 0, datasLen);
								SocketChannel sckChn = (SocketChannel) selector.channel();
								sckChn.write(buffer);
								buffer.flip();
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
			final NonBlockSocketChannel nonblockSocket = new NonBlockSocketChannel("127.0.0.1", 1007);
			new Thread(new Runnable() {
				public void run() {
					try {
						nonblockSocket.startListening();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}).start();
			new Thread(new Runnable() {
				public void run() {
					nonblockSocket.startWriting();
				}
			}).start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
