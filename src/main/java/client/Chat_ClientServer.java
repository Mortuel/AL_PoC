package client;

import java.io.*;
import java.net.*;

import AL.PoC.DroneModel;


public class Chat_ClientServer implements Runnable {

	private Socket socket;
	private DroneModel dm = new DroneModel();
	private PrintWriter out = null;
	private BufferedReader in = null;
	
	public Chat_ClientServer(Socket s){
		socket = s;
	}
	
	public void run() {
		try {
			out = new PrintWriter(socket.getOutputStream());
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			Thread t4 = new Thread(new EmissionC(out, dm));
			t4.start();
			Thread t3 = new Thread(new ReceptionC(in, dm));
			t3.start();
			
		} catch (IOException e) {
			System.err.println("Le serveur distant s'est déconnecté !");
		}
	}

}
