package server;

import java.io.*;
import java.net.*;

public class Server {
	public static ServerSocket ss = null;
	public static Thread t;
 
	public static void main(String[] args) {
		try {
			ss = new ServerSocket(2000);
			System.out.println("Connexion port : " + ss.getLocalPort());
			t = new Thread(new AcceptConnexion(ss));
			t.start();
			
		} catch (IOException e) {
			System.err.println(e);
		}
	}
}

