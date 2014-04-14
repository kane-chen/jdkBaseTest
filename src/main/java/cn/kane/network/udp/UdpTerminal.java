package cn.kane.network.udp;

import java.net.SocketException;

public class UdpTerminal {

	private static final int BUFFER_SIZE = 1024 ;
	private static final String CHARSET = "UTF-8" ;

	public static void startOneUdpTerminal(int listeningPort,int bufferSize,String charset) throws SocketException{
		new Thread(new UdpReadThread(listeningPort,bufferSize,charset),"Read-"+listeningPort).start();
		new Thread(new UdpWriteThread(bufferSize, charset),"Write-"+listeningPort).start() ;
	}
	
	public static void main(String[] args){
		
		try {
			startOneUdpTerminal(1005,BUFFER_SIZE,CHARSET);
//			startOneUdpTerminal(1007,BUFFER_SIZE,CHARSET);
		} catch (SocketException e) {
			e.printStackTrace();
		}
    }
	
}
