package client;

import java.io.*;
import java.net.*;

public class Client {

	public static Socket socket = null;
	public static Thread t1;
	
	public static void main(String[] args) {
		try {
			socket = new Socket(args[0],Integer.parseInt(args[1]));
			t1 = new Thread(new Connexion(socket));
			t1.start();
		} catch (UnknownHostException e) {
			System.err.println("Impossible de se connecter à l'adresse "+socket.getLocalAddress());
		} catch (IOException e) {
			System.err.println("Aucun serveur à l'écoute du port "+socket.getLocalPort());
		}
	}
}
