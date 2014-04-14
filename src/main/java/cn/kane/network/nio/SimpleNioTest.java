package cn.kane.network.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;

public class SimpleNioTest {

	
	public void startServer(String serverIp ,int port) throws IOException{
		
		// 创建一个选择器，可用close()关闭，isOpen()表示是否处于打开状态，他不隶属于当前线程
		Selector selector = Selector.open();
		// 创建ServerSocketChannel，并把它绑定到指定端口上
		ServerSocketChannel server = ServerSocketChannel.open();
		server.socket().bind(new InetSocketAddress(serverIp,port));
		
		// 设置为非阻塞模式, 这个非常重要
		server.configureBlocking(false);
		// 在选择器里面注册关注这个服务器套接字通道的accept事件
		// ServerSocketChannel只有OP_ACCEPT可用，OP_CONNECT,OP_READ,OP_WRITE用于SocketChannel
		server.register(selector, SelectionKey.OP_ACCEPT);
		while (true) {
			// 测试等待事件发生，分为直接返回的selectNow()和阻塞等待的select()，另外也可加一个参数表示阻塞超时
			// 停止阻塞的方法有两种: 中断线程和selector.wakeup()，有事件发生时，会自动的wakeup()
			// 方法返回为select出的事件数(参见后面的注释有说明这个值为什么可能为0).
			// 另外务必注意一个问题是，当selector被select()阻塞时，其他的线程调用同一个selector的register也会被阻塞到select返回为止
			// select操作会把发生关注事件的Key加入到selectionKeys中(只管加不管减)
			if (selector.select() == 0) { //
				continue;
			}
			
			// 获取发生了关注时间的Key集合，每个SelectionKey对应了注册的一个通道
			Set<SelectionKey> keys = selector.selectedKeys();
			// 多说一句selector.keys()返回所有的SelectionKey(包括没有发生事件的)
			for (SelectionKey key : keys) {
				// OP_ACCEPT 这个只有ServerSocketChannel才有可能触发
				if (key.isAcceptable()) {
					// 得到与客户端的套接字通道
					SocketChannel channel = ((ServerSocketChannel) key.channel()).accept();
					// 同样设置为非阻塞模式
					channel.configureBlocking(false);
					// 同样将于客户端的通道在selector上注册，OP_READ对应可读事件(对方有写入数据),可以通过key获取关联的选择器
					
				}
				
				// OP_READ 有数据可读
				if (key.isReadable()) {
					SocketChannel channel = (SocketChannel) key.channel();
					// 得到附件，就是上面SocketChannel进行register的时候的第三个参数,可为随意Object
					ByteBuffer buffer = (ByteBuffer) key.attachment();
					// 读数据 这里就简单写一下，实际上应该还是循环读取到读不出来为止的
					channel.read(buffer);
					// 改变自身关注事件，可以用位或操作|组合时间
					key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
				}
				
				// OP_WRITE 可写状态 这个状态通常总是触发的，所以只在需要写操作时才进行关注
				if (key.isWritable()) {
					// 写数据掠过，可以自建buffer，也可用附件对象(看情况),注意buffer写入后需要flip
					// ......
					// 写完就吧写状态关注去掉，否则会一直触发写事件
					key.interestOps(SelectionKey.OP_READ);
				}
				
				// 由于select操作只管对selectedKeys进行添加，所以key处理后我们需要从里面把key去掉
				keys.remove(key);
			}
		}
	}
	
	public void startClient(String serverIp,int serverport) throws IOException{
		Selector selector = Selector.open();
		// 创建一个套接字通道，注意这里必须使用无参形式
		SocketChannel channel = SocketChannel.open();
		// 设置为非阻塞模式，这个方法必须在实际连接之前调用(所以open的时候不能提供服务器地址，否则会自动连接)
		channel.configureBlocking(false);
		// 连接服务器，由于是非阻塞模式，这个方法会发起连接请求，并直接返回false(阻塞模式是一直等到链接成功并返回是否成功)
		channel.connect(new InetSocketAddress("serverIp", serverport));
		// 注册关联链接状态
		channel.register(selector, SelectionKey.OP_CONNECT);
		
		while (true) {
		    // 前略 和服务器端的类似
		    // ...
		    // 获取发生了关注时间的Key集合，每个SelectionKey对应了注册的一个通道
		
		    Set<SelectionKey> keys = selector.selectedKeys();
		    for (SelectionKey key : keys) {
		        // OP_CONNECT 两种情况，链接成功或失败这个方法都会返回true
		        if (key.isConnectable()) {
		            // 由于非阻塞模式，connect只管发起连接请求，finishConnect()方法会阻塞到链接结束并返回是否成功
		            // 另外还有一个isConnectionPending()返回的是是否处于正在连接状态(还在三次握手中)
		            if (channel.finishConnect()) {
		                // 链接成功了可以做一些自己的处理，略
		                // ...
		                // 处理完后必须吧OP_CONNECT关注去掉，改为关注OP_READ
		                key.interestOps(SelectionKey.OP_READ);
		            }
		        }
		        // 后略 和服务器端的类似
		        // ...
		    }
		}
	}
}
