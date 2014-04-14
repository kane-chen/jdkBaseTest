package cn.kane.network.tcp;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.apache.log4j.Logger;

public class TcpSocketServer implements Runnable{

	private static final Logger log = Logger.getLogger(TcpSocketServer.class) ;
	
	private volatile boolean isRunning = true ;
	private int serverPort = 10000 ;
	private ServerSocket serverSocket = null ; 
	
	private final static int BYTE_BUFFER_SIZE = 1024 ;
	private final static int SCKT_BUFFER_SIZE = 10 ;
	private final static String SOURCE_CHARSET = "GBK" ;
	private final static String SHOW_CHARSET = "UTF-8" ;
	private final static String TARGET_CHARSET = "UTF-8" ;
	private BlockingQueue<Socket> clientSocketQueue = new ArrayBlockingQueue<Socket>(SCKT_BUFFER_SIZE);
	
	
	public TcpSocketServer(int serverPort) throws Exception{
		if( serverPort>0 && serverPort<65535 ){
			this.serverPort = serverPort ;
			getServerInstance() ;
		}else{
			throw new Exception("PORT must be [0-65535]");
		}
	}
	public ServerSocket getServerInstance() throws IOException {
		if(null == serverSocket){
			serverSocket = new ServerSocket(serverPort) ;
		}
		return serverSocket ;
	}
	
	public void run() {
		log.info("try to start socket server with port-"+serverPort);
		while(isRunning){
			try {
				startWriteThread();
				while(true){
					Socket clientSocket = serverSocket.accept() ;
					clientSocketQueue.put(clientSocket);
					new Thread(new ReadThread(clientSocket),"Read"+clientSocket.getRemoteSocketAddress().toString()).start();
				}
			} catch (IOException e) {
				log.error(e.getMessage());
			} catch (InterruptedException e) {
				log.error(e.getMessage());
			}
		}
	}

	public void startWriteThread(){
		new Thread(new Runnable(){
			public void run() {
				byte[] bufferArr = new byte[BYTE_BUFFER_SIZE] ;
				try {
					while(System.in.read(bufferArr)!= -1){
						String targetStr = new String(new String(bufferArr).getBytes(SOURCE_CHARSET),SHOW_CHARSET) ;
						log.info("I SAY :" + targetStr);
						bufferArr = targetStr.getBytes(TARGET_CHARSET) ;
						Iterator<Socket> clientSocketIterator =  clientSocketQueue.iterator();
						while(clientSocketIterator.hasNext()){
							Socket client = clientSocketIterator.next() ;
							OutputStream outStream = null ;
							try {
								outStream = client.getOutputStream() ;
								outStream.write(bufferArr);
								outStream.flush() ;
							} catch (IOException e) {
								log.error(e);
							}
//							finally{
//								if(null!=outStream){
//									try {
//										outStream.close();//io-stream's close could cause the socket's close
//									} catch (Exception e) {
//									}
//								}
//							}
						}
					}
				} catch (IOException e) {
					log.error(e);
				}
				
			}
		},"SERVER-WRITE").start();
	}
	
	public static void main(String[] args) {
		log.info("main start");
		try {
			new Thread(new TcpSocketServer(1003)).start() ;
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		
	}
	
}



