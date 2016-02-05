package client;

import java.io.BufferedReader;
import java.io.IOException;

import AL.PoC.DroneModel;

public class ReceptionC implements Runnable {

	private int tmp = 0;
	private DroneModel dm ;
	private BufferedReader in;
	private String message = null;
	
	public ReceptionC(BufferedReader in, DroneModel dm){
		this.in = in;
		this.dm = dm;
	}
	
	public void read() {
		try {
			message = in.readLine();
			tmp = message.split("-").length;
			if(tmp > 1) {
				dm.setPdv(message.split("-"));
				System.out.println(dm.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		while(true){
	        try {
				message = in.readLine();
				tmp = message.split("/").length;
				if(tmp > 1) {
					dm.setPdv(message.split("/"));
				}
				System.out.println("SERVEUR : " +message);
			    } catch (IOException e) {
					e.printStackTrace();
			}
		}
	}
}
