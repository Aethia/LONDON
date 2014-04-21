package fr.m1.miage.london.network.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import fr.m1.miage.london.network.*;


public class Reception implements Runnable {

	private ObjectInputStream  in;
	private Action action = null;
	private String login;
	private static List<IncomingMessageListenerClient> listenersM = new ArrayList<IncomingMessageListenerClient>();
	private static List<IncomingObjectListenerClient> listenersO = new ArrayList<IncomingObjectListenerClient>();
	public static List<IncomingPartieObjectListenerClient> listenersPartie = new ArrayList<IncomingPartieObjectListenerClient>();
	
	
	
	public Reception(ObjectInputStream  in, String login){
		this.login = login;
		this.in = in;
	}
	
	public static void addListenerM(IncomingMessageListenerClient toAdd){
		listenersM.add(toAdd);
	}
	
	public static void addListenerO(IncomingObjectListenerClient toAdd){
		listenersO.add(toAdd);
	}
	
	public static void addListenerPartie(IncomingPartieObjectListenerClient toAdd){
		listenersPartie.add(toAdd);
	}
	
	public void run() {
		
		while(true){
	        try {
	        	
	        try {
	        	
				action = (Action)in.readObject();
				
				
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	        	// message de chat
	        	if (action.getType() == 2) {
					// on notifie tous ceux qui écoutent
					for (IncomingMessageListenerClient list : listenersM){
						list.nouveauMessage(action.getText());
					}
				}
	        	// echange de l'objet partie
	        	else if (action.getType() == 5) {

	        			for (IncomingPartieObjectListenerClient list : listenersPartie){
		        			list.nouvelObjet(action.getObject());
		        		}
				
	        		
	        	}
			

	        	else {
					// on notifie tous ceux qui écoutent
					for (IncomingObjectListenerClient list : listenersO){
						list.nouvelObjet(action.getObject(), action.getType());
					}
				}
				
			
			
			
		
			
			
		    } catch (IOException e) {
				
				e.printStackTrace();
			}
	        
	        action = null;
		}
	}

}