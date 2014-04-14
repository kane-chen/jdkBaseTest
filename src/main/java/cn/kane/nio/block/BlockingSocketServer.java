package cn.kane.nio.block;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BlockingSocketServer implements Runnable {

	private int serverPort = 2000 ;
	private Integer socketsCounts = 0 ;
	private Map<String,Socket> socketsMapping = new ConcurrentHashMap<String,Socket>();
	public BlockingSocketServer() throws IOException{
		ServerSocket server = new ServerSocket(serverPort);
		System.out.println("------------SERVER START-----------");
		while(true){
			Socket socket = server.accept() ;
			String socketName = geneSocketName();
			socketsMapping.put(socketName, socket);
			startSocketIOThread(socketName);
		}
	}

	private String geneSocketName() {
		String socketName = "SOCKET-" ;
		synchronized(socketsCounts){
			socketsCounts++;
			socketName += socketsCounts ;
		}
		return socketName ;
	}
	
	private void startSocketIOThread(String socketName) {
		new Thread(this,socketName).start();
	}

	public void run() {
        String socketName = Thread.currentThread().getName();
        BufferedReader reader = null ;
        Socket socket = null ;
        try {
            socket = socketsMapping.get(socketName);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        	while(true){
        		String inputStr = null ;
        		inputStr = reader.readLine();
        		if(null!=inputStr && !"exit".endsWith(inputStr)){
        			System.out.println(inputStr);
        		}else{
        			System.out.println("####"+socketName+" EXIT");
        		}
        	}
		} catch (IOException e) {
			System.out.println("*********Handle Socket InputStream Error********");
			e.printStackTrace();
		} finally{
			try {
				if(null!=socket)
				   socket.close();
				if(null!=reader)
					reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args){
		try {
			new BlockingSocketServer();
		} catch (IOException e) {
			System.out.println("--------BlockingSocketServer Instance Error-------");
			e.printStackTrace();
		}
	}
}
