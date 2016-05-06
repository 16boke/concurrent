package com.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class ServerSocketTest {
	private static Logger log = Logger.getLogger("ServerSocketTest");
	final static String response = "HTTP/1.0 200 OKrn" + "Content-type: text/plainrn" + "rn" + "Hello World";

	public static void handleRequest(Socket socket) throws IOException {
		// Read the input stream, and return "200 OK"
		try {
			Thread.sleep(5000);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			log.info("收到的消息："+in.readLine());

			OutputStream out = socket.getOutputStream();
			out.write(response.getBytes(StandardCharsets.UTF_8));
		}catch(Exception ex){
			ex.printStackTrace();
		}
		finally {
			socket.close();
		}
	}

	public static void main(String[] args) throws Exception {
		ServerSocket listener = new ServerSocket(8080);
		try {
			while (true) {
				Socket socket = listener.accept();
				try {
					handleRequest(socket);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} finally {
			listener.close();
		}
	}
}
