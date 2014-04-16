package fr.m1.miage.london.network.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.m1.miage.london.network.IncomingListenerClient;


public class Reception implements Runnable {

	private BufferedReader in;
	private String message = null;
	List<IncomingListenerClient> listeners = new ArrayList<IncomingListenerClient>();
	
	public Reception(BufferedReader in){
		
		this.in = in;
	}
	
	public void addListener(IncomingListenerClient toAdd){
		listeners.add(toAdd);
	}
	
	public void run() {
		
		while(true){
	        try {
	        	
			message = in.readLine();
			for (IncomingListenerClient list : listeners){
				list.nouveauMessage(message);
			}
			System.out.println(message);
			
			
		    } catch (IOException e) {
				
				e.printStackTrace();
			}
		}
	}

}