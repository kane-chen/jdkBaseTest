package cn.kane.socket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UdpReader implements Runnable{

	byte[] byteArray = new byte[1024];
	
	DatagramSocket node = null ;
	DatagramPacket dataPacket = new DatagramPacket(byteArray,byteArray.length);

	public static void main(String[] args) {
		new Thread(new UdpReader(10000),"UDP-SERVER").start();
	}
	
	public UdpReader(int port){
		try {
			node = new DatagramSocket(port);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	
	public void run() {
		System.out.println("---------RUN--------");
		while(true){
			try {
				node.receive(dataPacket);
				byteArray = dataPacket.getData();
				System.out.println("Rec:"+new String(byteArray,"UTF-8"));
				byteArray = "Received".getBytes();
				dataPacket.setData(byteArray);
				dataPacket.setSocketAddress(dataPacket.getSocketAddress());
				dataPacket.setPort(dataPacket.getPort());
				node.send(dataPacket);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}