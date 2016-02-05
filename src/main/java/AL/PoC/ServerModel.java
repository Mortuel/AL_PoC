package AL.PoC;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import dijkstra.Dijkstra;
import dijkstra.Edge;
import dijkstra.Graph;
import dijkstra.Vertex;

// Pas de sauvegarde du graphe, on le refait a chaque fois pour le PoC
// Sinon il serait sauvegarde dans une DB orientee graphe la première fois

public class ServerModel {
	
	private final static double SIZE = 16; 		  				 // Nombre de noeuds
	private static Graph graph = null; 			  				 // graphe
	private static List<Vertex> nodes = new ArrayList<Vertex>(); // noeuds
	private static List<Edge> edges = new ArrayList<Edge>(); 	 // couloirs
	
	public ServerModel(){
		
	}
	
	public String getPdv(String id) throws IOException {
		String ligne, res = "";
		BufferedReader br = null;
		
		try {
			br = new BufferedReader(new FileReader("/Users/Mortuel/Documents/TMP/DB_PDV/pdv.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while((ligne = br.readLine()) != null) {
			String[] tmp = ligne.split(";");
			if(tmp[0].equalsIgnoreCase(id)) res = tmp[1];
		}
		br.close();
		return res;
	}
	
	// Copie les informations en local
	private static void copyInformations(String file) throws IOException {
		String ligne;
		BufferedReader br = null;
		BufferedWriter bw = null;
		
		try {
			bw = new BufferedWriter(new FileWriter("/Users/Mortuel/Documents/TMP/DB_Couloirs/couloirs.txt"));
			br = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while((ligne = br.readLine()) != null) {
			bw.write(ligne + "\n");
		}
		br.close();
		bw.close();
	}
	
	// Cree le graphe en fonction des informations copiees dans "couloirs.txt"
	private static void createGraph() throws IOException {
		String ligne;
		BufferedReader br = null;
		int cpt = 0;
		
		// Cree et ajoute les noeuds à "nodes"
		for (int i = 0; i < SIZE; i++) {
			Vertex location = new Vertex("Node_" + i, "Node_" + i);
			nodes.add(location);
		}
		
		// Lit le fichier pour recuperer les couloirs
		try {
			br = new BufferedReader(new FileReader("/Users/Mortuel/Documents/TMP/DB_Couloirs/couloirs.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while((ligne = br.readLine()) != null) {
			String[] couloir = ligne.split(";");
			edges.add(new Edge("Edge_" + cpt++, 
					           nodes.get(Integer.parseInt(couloir[1]) + Integer.parseInt(couloir[2]) * (int)(Math.sqrt(SIZE))), 
							   nodes.get(Integer.parseInt(couloir[3]) + Integer.parseInt(couloir[4]) * (int)(Math.sqrt(SIZE))), 
							   1));
			edges.add(new Edge("Edge_" + cpt++, 
			           		   nodes.get(Integer.parseInt(couloir[3]) + Integer.parseInt(couloir[4]) * (int)(Math.sqrt(SIZE))), 
			           		   nodes.get(Integer.parseInt(couloir[1]) + Integer.parseInt(couloir[2]) * (int)(Math.sqrt(SIZE))), 
			           		   1));
		}
		graph = new Graph(nodes, edges);
		br.close();
	}
	
	// Trouve le plus court chemin entre depart et arrivee
	private static String foundPath(int depart, int arrivee){
	    Dijkstra dijkstra = new Dijkstra(graph);
	    
	    dijkstra.execute(nodes.get(depart));
	    LinkedList<Vertex> path = dijkstra.getPath(nodes.get(arrivee));
	    String res = "";
	    if(path != null) {
	    	for(Vertex vertex : path) {
	    		res += vertex + ";";
	    	}
	    }
	    return res;
	}
	
	// Calcule les PDV en fonction des livraisons
	private static void computePdvs(Graph graph, String file) throws IOException {
		String ligne;
		BufferedReader br = null;
		BufferedWriter bw = null;
		
		try {
			br = new BufferedReader(new FileReader(file));
			bw = new BufferedWriter(new FileWriter("/Users/Mortuel/Documents/TMP/DB_PDV/pdv.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while((ligne = br.readLine()) != null) {
			String[] infos = ligne.split(";"); 	 // int id ; string Node_X
			String[] node = infos[1].split("_"); // Node ; X
			String[] listNodes = foundPath(0, Integer.parseInt(node[1])).split(";");
			bw.write(infos[0] + ";");
			for(int i = 0 ; i < listNodes.length - 1 ; i++){
				int nodeNumber = Integer.parseInt(listNodes[i].split("_")[1]);
				bw.write((nodeNumber % (int)(Math.sqrt(SIZE))) + "-" + (nodeNumber / (int)(Math.sqrt(SIZE))) + "/");
			}
			int nodeNumber = Integer.parseInt(listNodes[listNodes.length - 1].split("_")[1]);
			bw.write((nodeNumber % (int)(Math.sqrt(SIZE))) + "-" + (nodeNumber / (int)(Math.sqrt(SIZE))));
			bw.write("\n");
		}
		br.close();
		bw.close();
	}
	
	public void main(String[] args) throws IOException {
		if(args.length > 2) {
			System.out.println("Nombre d'arguments invalide");
		}
		else if(args.length == 2 && args[0].compareTo("--copyInformations") == 0){
			copyInformations(args[1]);
			System.out.println("Informations sauvegardees !");
		}
		else if(args.length == 1 && args[0].equalsIgnoreCase("--createGraph")){
			createGraph();
			System.out.println("Graphe cree et sauvegarde !");
			//foundPath(0, 12); // affiche a la console le chemin entre les noeuds 0 et 15
		}
		else if(args.length == 1 && args[0].equalsIgnoreCase("--computePDVs")){
			createGraph();
			computePdvs(graph, "/Users/Mortuel/Documents/TMP/DB_Livraisons/livraison.txt");
			System.out.println("Plans de vol calcules et sauvegardes !");
		}
		else if(args.length == 2 && args[0].equalsIgnoreCase("getPdv")){
			getPdv(args[1]);
		}
	}

	// De la forme ping:idDrone;x;y
	public void ping(String ping) throws IOException {
		long millis = System.currentTimeMillis();
		
		FileWriter fw = new FileWriter("/Users/Mortuel/Documents/TMP/DB_Logs/logs.txt", true);
		fw.write(ping + ";" + millis + "\n");
		fw.close();
	}

	// Update le statut de la livraison, format : updateStatus:status;idCommande
	public void updateStatus(String status, String id) throws IOException {
		BufferedReader br = null;
		BufferedWriter bw = null;
		String ligne = "";
		
		try {
			br = new BufferedReader(new FileReader("/Users/Mortuel/Documents/TMP/DB_Commandes/commandes.txt"));
			bw = new BufferedWriter(new FileWriter("/Users/Mortuel/Documents/TMP/DB_Commandes/tmpFile.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while((ligne = br.readLine()) != null) {
			String[] tmp = ligne.split(";");
			if(tmp[0].equalsIgnoreCase(id)) {
				tmp[2] = status;
				ligne = tmp[0];
				for(int i = 1 ; i < tmp.length ; i++) ligne += ";" + tmp[i];
			}
			bw.write(ligne + "\n");
		}
		
		new File("/Users/Mortuel/Documents/TMP/DB_Commandes/commandes.txt").delete();
		File f = new File("/Users/Mortuel/Documents/TMP/DB_Commandes/tmpFile.txt");
		f.renameTo(new File("/Users/Mortuel/Documents/TMP/DB_Commandes/commandes.txt"));
		
		br.close();
		bw.close();
	}
}
