package fr.m1.miage.london.network.serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.m1.miage.london.network.IncomingListenerServeur;


public class Reception implements Runnable {

	private BufferedReader in;
	private String message = null, login = null;
	public static List<IncomingListenerServeur> listeners = new ArrayList<IncomingListenerServeur>();
	
	public Reception(BufferedReader in, String login){
		
		this.in = in;
		this.login = login;
	}
	
	public static void addListener(IncomingListenerServeur toAdd){
		listeners.add(toAdd);
	}
	
	public void run() {
		
		while(true){
	        try {
	        	
			message = in.readLine();
			for (IncomingListenerServeur list : listeners){
				list.nouveauMessage(message);
			}
			//System.out.println(login+" : "+message);
			for( Emission e : Serveur.lesClients){
				e.sendMessage(message);
			}
			
		    } catch (IOException e) {
				
				e.printStackTrace();
			}
		}
	}
	

}