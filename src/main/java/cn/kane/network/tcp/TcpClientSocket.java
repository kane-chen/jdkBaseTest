package cn.kane.network.tcp;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;

import org.apache.log4j.Logger;

public class TcpClientSocket{
	private static final Logger log = Logger.getLogger(TcpSocketServer.class) ;
	
	private final static int BYTE_BUFFER_SIZE = 1024 ;
	private final static int MESG_BUFFER_SIZE = 1024 ;
	private static final ArrayBlockingQueue<String> sendMessagePool = new ArrayBlockingQueue<String>(MESG_BUFFER_SIZE) ;
	
	public static void main(String[] args) {
		log.info("client -main start");
		try {
			Socket clientSocket = new Socket("127.0.0.1",1007);
			new Thread(new ReadThread(clientSocket),"Read"+clientSocket.getRemoteSocketAddress().toString()).start();
			new Thread(new WriteThread(clientSocket,sendMessagePool),clientSocket.getRemoteSocketAddress().toString()).start();
			byte[] bufferArr = new byte[BYTE_BUFFER_SIZE];
			//System.in.read(bufferArr):
			//  if System.in's length is longer than bufferArr's length;
			//  System.in will be split pieces(max-length is bufferArr's length)
			while(System.in.read(bufferArr) != -1){     
				sendMessagePool.put(new String(bufferArr));
			}
		} catch (IOException e) {
			log.error(e.getMessage());
		} catch (InterruptedException e) {
			log.error(e.getMessage());
		}
	}
}
