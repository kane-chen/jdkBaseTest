package cn.kane.network.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import org.apache.log4j.Logger;

class ReadThread implements Runnable {

	private static final Logger log = Logger.getLogger(ReadThread.class);
	private static final String TARGET_CHARSET = "UTF-8";
	private final int BUFFER_SIZE = 1024;
	private final Socket client;

	public ReadThread(Socket client) {
		this.client = client;
	}

	public void run() {
		log.info("readThread is running");
		byte[] bufferByteArray = new byte[BUFFER_SIZE];
		InputStream in_stream = null;
		try {
			while (true) {
				in_stream = client.getInputStream();
				/**
				 * if in_stream is longer than bufferByteArray,
				 * then in_stream will be split ; 
				 * and while's loop put them into bufferByteArray
				 */
				while (in_stream.read(bufferByteArray) != -1) {
					log.info(Thread.currentThread().getName() + " say:"	+ new String(bufferByteArray, TARGET_CHARSET));
				}
			}
		} catch (IOException e) {
			log.error(e.getMessage());
		} finally { //point: IO-stream's close could cause the socket's close;so IO-stream's close only in exit. 
			if (in_stream != null) {
				try {
					in_stream.close();
				} catch (IOException e) {
					log.error(e);
				}
			}
		}
	}

}