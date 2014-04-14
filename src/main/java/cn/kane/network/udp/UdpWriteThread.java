package cn.kane.network.udp;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.Arrays;

import org.apache.log4j.Logger;

public class UdpWriteThread implements Runnable {

	private static final Logger log = Logger.getLogger(UdpWriteThread.class) ;
	private int bufferSize = 1024 ;
	private String charset = "UTF-8" ;
	
	public UdpWriteThread(int bufferSize,String charset){
		this.bufferSize = bufferSize ;
		this.charset = charset ;
	}
	
	public void run() {

		log.info("udp-write-thread is running");
		log.info("please input your message,pattern:[ip],[port],[message]");
		
		String targetIp,datas;
		int targetPort ;
		int datasLength = 0 ;
		byte[] bufferArr = new byte[bufferSize] ;
		try {
			while((datasLength=System.in.read(bufferArr))!= -1){
				try{
					log.debug("source:"+new String(bufferArr)+"&END");
					bufferArr = Arrays.copyOfRange(bufferArr, 0, datasLength) ;//remove  byte[n..]=0 >>> {space in string}
					log.debug("removeSpace:"+new String(bufferArr)+"&END");
					String inputStr = new String(new String(bufferArr).getBytes("GBK"),"UTF-8") ;
					String[] params = inputStr.split(",");
					if(params.length == 1){
						targetIp = "127.0.0.1" ;
						targetPort = 1005 ;
						datas = params[0] ;
					}else if(params.length == 3){
						targetIp = params[0] ;
						targetPort = Integer.parseInt(params[1]);
						datas = params[2] ;
					}else{
						throw new Exception("input pattern wrong:");
					}
					sendDatagramToSpecialAddr(targetIp,targetPort,datas);
				}catch(Exception e){
					log.warn("input pattern wrong:"+e.getMessage());
					e.printStackTrace();
				}
					
			}
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}


	private void sendDatagramToSpecialAddr(String targetIp, int targetPort,	String datas) {
		
		datas = datas.replaceAll("\r\n", "");
		log.info(datas+" >>> ["+targetIp+":"+targetPort+"]");
		DatagramSocket udpSocket = null ;
		try {
			byte[] buffer = datas.getBytes(charset) ;
			udpSocket = new DatagramSocket();
			DatagramPacket dataPacket = new DatagramPacket(buffer, buffer.length) ;
			dataPacket.setSocketAddress(new InetSocketAddress(targetIp, targetPort));
			udpSocket.send(dataPacket) ;
		} catch (UnsupportedEncodingException e) {
			log.warn("wrong charset");
		} catch (SocketException e) {
			log.warn("connect-error with"+targetIp+":"+targetPort) ;
		} catch (IOException e) {
			log.error(e);
		}finally{
			if(null!=udpSocket){
				udpSocket.close();
			}
		}
		
	}

}
