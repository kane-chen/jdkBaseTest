package cn.kane.nio.block;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketClient4Test implements Runnable {

	private Integer clientNo = 0 ;
	private String serverIp = "127.0.0.1" ;
	private int serverPort = 2000 ;
	
	public void run() {
		Socket socket = null ;
		PrintWriter writer  = null ;
		try {
			String clientNoStr = Thread.currentThread().getName() ;
			socket = new Socket(serverIp, serverPort);
			writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
			int i = 0 ;
			int sleepMills = 0 ;
			while(i<5){
				i++ ;
				sleepMills = (int) (Math.random()*10000);
				Thread.sleep(sleepMills);
				String sendStr = clientNoStr + ":SayHi="+ i;
				writer.println(sendStr);
				writer.flush();
				System.out.println(sendStr);
			}
			writer.println(clientNoStr + ":EXIT");
			writer.flush();
			System.out.println(clientNoStr + ":EXIT");

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			try {
				if(null!=socket)
					socket.close() ;
				if(null!=writer)
					writer.close() ;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
        new SocketClient4Test().geneSocketClients4Test();
	}

	
	private void geneSocketClients4Test() {
		for(int i=0;i<10;i++){
        	String clientNoStr = getCilentNo();
        	new Thread(this,clientNoStr).start();
        }		
	}

	private String getCilentNo() {
        String  clientNoStr = "Client-" ;
        synchronized(clientNo){
        	clientNo ++ ;
        	clientNoStr += clientNo ;
        }
		return clientNoStr;
	}

}
