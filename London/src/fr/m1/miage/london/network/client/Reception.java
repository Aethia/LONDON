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
	
	public void run() {
		
		while(true){
	        try {
	        	
	        try {
				action = (Action)in.readObject();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (action.getType() == 2) {
				// on notifie tous ceux qui écoutent
				for (IncomingMessageListenerClient list : listenersM){
					list.nouveauMessage(action.getText());
				}
			}
			if (action.getType() == 3) {
				// on notifie tous ceux qui écoutent
				for (IncomingObjectListenerClient list : listenersO){
					list.nouvelObjet(action.getObject(), action.getType());
				}
			}
			
			
		    } catch (IOException e) {
				
				e.printStackTrace();
			}
		}
	}

}