package server;

import java.net.*;

public class Authentification implements Runnable {
	private static int cpt = 0;
	private Socket socket;
	public Thread t2;
	
	public Authentification(Socket s){
		 socket = s;
	}
	
	public void run() {
		t2 = new Thread(new Chat_ClientServer(socket,"drone"+cpt++));
		t2.start();
	}

}

