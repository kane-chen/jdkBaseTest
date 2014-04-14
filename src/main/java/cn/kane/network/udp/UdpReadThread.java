package cn.kane.network.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import org.apache.log4j.Logger;

public class UdpReadThread implements Runnable {

	private static final Logger log = Logger.getLogger(UdpReadThread.class) ;
	
	private int localPort = -1 ;
	private int bufferSize = -1 ;
	private String charset = "UTF-8";

	private DatagramSocket udpSocket = null ;
	
	public UdpReadThread(int localPort,int bufferSize,String charset) throws SocketException{
		this.localPort = localPort ;
		this.bufferSize = bufferSize ;
		this.charset = charset ;
		this.udpSocket = new DatagramSocket(localPort) ;
	}
	
	public void run() {

		log.info("udp-read-thread on local-port["+localPort+"] is running");
		byte[] bufferArr = new byte[bufferSize] ;
		DatagramPacket dataPacket = new DatagramPacket(bufferArr, bufferSize) ;
		while(true){
			try {
				udpSocket.receive(dataPacket) ; //blocks until data-gram arrived
				log.info(dataPacket.getSocketAddress() + ":" + new String(dataPacket.getData(),charset ));
			} catch (IOException e) {
				log.error(e) ;
			}
		}
	}

}
