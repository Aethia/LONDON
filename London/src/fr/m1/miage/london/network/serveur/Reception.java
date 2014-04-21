package fr.m1.miage.london.network.serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import fr.m1.miage.london.network.IncomingListenerServeur;


public class Reception implements Runnable {

	private ObjectInputStream in;
	private String message = null, login = null;
	public static List<IncomingListenerServeur> listeners = new ArrayList<IncomingListenerServeur>();
	
	public Reception(ObjectInputStream in, String login){
		
		this.in = in;
		this.login = login;
	}
	
	public static void addListener(IncomingListenerServeur toAdd){
		listeners.add(toAdd);
	}
	
	public void run() {
		
		while(true){
	        try {
	        	
			try {
				message = (String) in.readObject();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
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