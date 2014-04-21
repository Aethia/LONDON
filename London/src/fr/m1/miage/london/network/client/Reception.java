package fr.m1.miage.london.network.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import fr.m1.miage.london.network.IncomingListenerClient;


public class Reception implements Runnable {

	private ObjectInputStream  in;
	private String message = null;
	private String login;
	private static List<IncomingListenerClient> listeners = new ArrayList<IncomingListenerClient>();
	
	
	
	public Reception(ObjectInputStream  in, String login){
		this.login = login;
		this.in = in;
	}
	
	public static void addListener(IncomingListenerClient toAdd){
		listeners.add(toAdd);
	}
	
	public void run() {
		
		while(true){
	        try {
	        	
			try {
				message = (String)in.readObject();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// on notifie tous ceux qui écoutent
			for (IncomingListenerClient list : listeners){
				list.nouveauMessage(message);
			}
			
			
		    } catch (IOException e) {
				
				e.printStackTrace();
			}
		}
	}

}