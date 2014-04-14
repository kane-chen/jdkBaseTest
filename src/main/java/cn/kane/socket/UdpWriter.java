package cn.kane.socket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

public class UdpWriter implements Runnable {

	private DatagramSocket node = null ;
	private byte[] bytes = new byte[1024];
	
	private String remoteIp;
	private int remotePort;
	public UdpWriter(int port,String remoteIp,int remotePort){
		this.remoteIp = remoteIp ;
		this.remotePort = remotePort ;
		try {
			node = new DatagramSocket(port);
			node.setSoTimeout(5000);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	public void run() {
		System.out.println("---------RUN--------");
         String str = "I'm kane" ;
         bytes = str.getBytes() ;
         DatagramPacket dataPacket = new DatagramPacket(bytes,bytes.length);
         dataPacket.setData(bytes);
         dataPacket.setSocketAddress(new InetSocketAddress(remoteIp, remotePort));
         try {
        	node.send(dataPacket);
			node.receive(dataPacket);
			System.out.println("Rec:"+new String(dataPacket.getData(),"UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
         
	}

	public static void main(String[] args) {
        new Thread(new UdpWriter(10003,"127.0.0.1",10000),"UDP-CLIENT").start();
	}

}
