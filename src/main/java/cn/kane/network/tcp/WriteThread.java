package cn.kane.network.tcp;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;

import org.apache.log4j.Logger;

class WriteThread implements Runnable {

	private static final Logger log = Logger.getLogger(ReadThread.class);
	private static final String SOURCE_CHARSET = "GBK";//source encoding
	private static final String SHOW_CHARSET = "UTF-8";//show's encoding
	private static final String TARGET_CHARSET = "UTF-8";//tanscation's encoding
	private Socket client;
	private ArrayBlockingQueue<String> sendMessagePool = null;

	public WriteThread(Socket client, ArrayBlockingQueue<String> sendMessagePool) {
		this.client = client;
		this.sendMessagePool = sendMessagePool;
	}

	public void run() {
		log.info("writeThread is running");
		OutputStream out_stream = null;
		try {
			while (true) {
				String message = sendMessagePool.take();
				byte[] bufferArray = message.getBytes(SOURCE_CHARSET);
				String showStr = new String(bufferArray, SHOW_CHARSET) ;
				log.info("I say:" + showStr);
				bufferArray = showStr.getBytes(TARGET_CHARSET) ;
				out_stream = client.getOutputStream();
				out_stream.write(bufferArray);
				out_stream.flush();
			}
		} catch (IOException e) {
			log.error(e.getMessage());
		} catch (InterruptedException e) {
			log.error(e.getMessage());
		} finally {
			if (null != out_stream) {
				try {
					out_stream.close();
				} catch (IOException e) {
					log.error(e);
				}
			}
		}
	}
}