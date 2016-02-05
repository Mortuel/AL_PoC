package client;

import java.io.PrintWriter;
import java.util.Scanner;

import AL.PoC.DroneModel;

public class EmissionC implements Runnable {

	private PrintWriter out;
	private String message = null;
	private DroneModel dm;
	private Scanner sc = null;
	
	public EmissionC(PrintWriter out, DroneModel dm) {
		this.out = out;
		this.dm = dm;
	}
	
	public void write(String message){
		System.out.println(message);
		out.println(message);
		out.flush();
	}

	public void run() {
		sc = new Scanner(System.in);
		while(true){
			message = sc.nextLine();
			if(message.equalsIgnoreCase("GO")){
				dm.go();
				out.println("ping:" + dm.getId() + ";" + dm.getX() + ";" + dm.getY());
				out.flush();
			}
			else if(message.equalsIgnoreCase("NEXT")){
				dm.nextStep();
				out.println("ping:" + dm.getId() + ";" + dm.getX() + ";" + dm.getY());
				out.flush();
			}
			else if(message.equalsIgnoreCase("PING")){
				out.println("ping:" + dm.getId() + ";" + dm.getX() + ";" + dm.getY());
				out.flush();
			}
			else if(message.equalsIgnoreCase("BACK")) dm.back();
			else {
				out.println(message);
				out.flush();
			}
		}
	}
}
