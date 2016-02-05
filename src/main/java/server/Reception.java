package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import AL.PoC.ServerModel;

public class Reception implements Runnable {

	private ServerModel sm = new ServerModel();
	private BufferedReader in;
	private PrintWriter out;
	private String message = null, login = null;
	
	public Reception(BufferedReader in, PrintWriter out, String login){
		this.in = in;
		this.out = out;
		this.login = login;
	}
	
	public void run() {
		while(true){
	        try {
	        	message = in.readLine();
	        	if(message.split(":")[0].equalsIgnoreCase("getPdv")){
					String info = sm.getPdv(message.split(":")[1]);
					out.println(info);
					out.flush();
				} else if(message.split(":")[0] != null && 
						  message.split(":")[0].equalsIgnoreCase("ping")){
					String s = message.split(":")[1];
					s.substring(0, s.length() - 1);
					sm.ping(s);
				} else if(message.split(":")[0] != null && 
						  message.split(":")[0].equalsIgnoreCase("updateStatus")){
					String s = message.split(":")[1];
					s.substring(0, s.length() - 1);
					sm.updateStatus(s.split(";")[1],s.split(";")[0]);
				} else {
					message += " (RIEN COMPRIS)";
				}
					System.out.println(login+" : " + message);
		    } catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void read() throws IOException {
		message = in.readLine();
		try {
        	if(message.split(":")[0].equalsIgnoreCase("getPdv")){
				String info = sm.getPdv(message.split(":")[1]);
				out.println(info);
				out.flush();
			} else if(message.split(":")[0] != null && 
					  message.split(":")[0].equalsIgnoreCase("ping")){
				String s = message.split(":")[1];
				s.substring(0, s.length() - 1);
				sm.ping(s);
			} else if(message.split(":")[0] != null && 
					  message.split(":")[0].equalsIgnoreCase("updateStatus")){
				String s = message.split(":")[1];
				s.substring(0, s.length() - 1);
				sm.updateStatus(s.split(";")[1],s.split(";")[0]);
			} else {
				message += " (RIEN COMPRIS)";
			}
				System.out.println(login+" : " + message);
	    } catch (IOException e) {
			e.printStackTrace();
		}
	}
}
