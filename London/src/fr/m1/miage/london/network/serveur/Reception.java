package fr.m1.miage.london.network.serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import fr.m1.miage.london.network.*;


public class Reception implements Runnable {

	private ObjectInputStream in;
	private Action action = null;
	private String login = null;
	public static List<IncomingMessageListenerServeur> listeners = new ArrayList<IncomingMessageListenerServeur>();
	public static List<IncomingPartieObjectListenerServeur> listenersPartie = new ArrayList<IncomingPartieObjectListenerServeur>();
	
	public Reception(ObjectInputStream in, String login){
		
		this.in = in;
		this.login = login;
	}
	
	public static void addListener(IncomingMessageListenerServeur toAdd){
		listeners.add(toAdd);
	}
	
	public static void addListenerPartie(IncomingPartieObjectListenerServeur toAdd){
		listenersPartie.add(toAdd);
	}
	
	public void run() {
		int type;
		while(true){
	   
	        try {
				action = (Action) in.readObject();
				 if (action.getType() == 2 ) {
				        for (IncomingMessageListenerServeur list : listeners){
							list.nouveauMessage(action.getText());
						}
						for( Emission e : Serveur.lesClients){
							e.sendMessageString(action.getText());
						}
			        }
				 if (action.getType() == 5) {
					 for( Emission e : Serveur.lesClients){
							e.sendObjectPartie(action.getObject());
						}
				 }

			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        
	       
	       
	        
		
				
				



			
		}
	}
	

}