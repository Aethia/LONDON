package fr.m1.miage.london.network.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.m1.miage.london.network.IncomingListenerClient;


public class Reception implements Runnable {

	private BufferedReader in;
	private String message = null;
	private String login;
	private static List<IncomingListenerClient> listeners = new ArrayList<IncomingListenerClient>();
	
	
	
	public Reception(BufferedReader in, String login){
		this.login = login;
		this.in = in;
	}
	
	public static void addListener(IncomingListenerClient toAdd){
		listeners.add(toAdd);
	}
	
	public void run() {
		
		while(true){
	        try {
	        	
			message = in.readLine();
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