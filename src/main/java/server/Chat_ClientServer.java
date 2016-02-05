package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class Chat_ClientServer implements Runnable {

	private Socket socket = null;
	private BufferedReader in = null;
	private PrintWriter out = null;
	private String login = "";
	
	
	public Chat_ClientServer(Socket s, String log){
		socket = s;
		login = log;
	}
	
	public void run() {
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream());
		
			Thread t3 = new Thread(new Reception(in,out,login));
			t3.start();
			Thread t4 = new Thread(new Emission(out));
			t4.start();
		
		} catch (IOException e) {
			System.err.println(login + " s'est déconnecté ");
		}
	}
}
