package cn.kane.nio.block;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Iterator;
import java.util.Set;


public class NonBlockSocketServer {

	 private static Selector roller = null;  
	 private static final int port = 2000;  
	 private static NonBlockSocketServer instance = null;  
	 private ThreadLocal<StringBuffer> stringLocal = new ThreadLocal<StringBuffer>();  
	  
	 private NonBlockSocketServer() throws IOException {  
	      ServerSocketChannel serverChannel = ServerSocketChannel.open();  
	      serverChannel.socket().bind(new InetSocketAddress(port));  
	      serverChannel.configureBlocking(false);  
	      serverChannel.register(roller, SelectionKey.OP_ACCEPT);  
	 }  
	  
	 public synchronized static NonBlockSocketServer getInstance() throws IOException {  
	        if (instance == null) {  
	            roller = Selector.open();  
	            instance = new NonBlockSocketServer();  
	        }  
	        return instance;  
	 }  
	  
	 public void start() throws IOException {  
	        while (  roller.select()  > 0) {  
	            Set<SelectionKey> keySets = roller.selectedKeys();  
	            Iterator<SelectionKey> iter = keySets.iterator();  
	            while (iter.hasNext()) {  
	                SelectionKey key = iter.next();  
	                iter.remove();  
	                actionHandler(key);  
	            }  
	        }  
	    }  
	  
	    public void actionHandler(SelectionKey key) throws IOException {  
	        if (key.isAcceptable()) {  
	            ServerSocketChannel serverChannel = (ServerSocketChannel) key  
	                    .channel();  
	            SocketChannel socketChannel = serverChannel.accept();  
	            socketChannel.configureBlocking(false);  
	            socketChannel.register(roller, SelectionKey.OP_READ);  
	        } else if (key.isReadable()) {  
	            ByteBuffer buffer = ByteBuffer.allocate(16);  
	            SocketChannel socketChannel = (SocketChannel) key.channel();  
	            socketChannel.read(buffer);  
	            buffer.flip();  
	            String temp = decode(buffer);  
	            StringBuffer strBuffer = stringLocal.get();  
	            if (strBuffer == null) {  
	                strBuffer = new StringBuffer();  
	            }  
	  
	            strBuffer.append(temp);  
	  
	            if (temp.equals("\r\n")) {  
	                System.out.println(strBuffer.toString());  
	                strBuffer = null;  
	            }  
	            stringLocal.set(strBuffer);  
	        }  
	    }  
	  
	    public String decode(ByteBuffer buffer) {  
	        Charset charset = null;  
	        CharsetDecoder decoder = null;  
	        CharBuffer charBuffer = null;  
	        try {  
	            charset = Charset.forName("UTF-8");  
	            decoder = charset.newDecoder();  
	            charBuffer = decoder.decode(buffer);  
	            return charBuffer.toString();  
	        } catch (Exception ex) {  
	            ex.printStackTrace();  
	            return "";  
	        }  
	    }  
	  
	    public static void main(String[] args) {  
	        try {  
	        	NonBlockSocketServer.getInstance().start();  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
	    }  
}
