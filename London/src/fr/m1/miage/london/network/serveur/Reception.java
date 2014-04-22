package fr.m1.miage.london.network.serveur;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import fr.m1.miage.london.network.Action;
import fr.m1.miage.london.network.IncomingMessageListenerServeur;
import fr.m1.miage.london.network.IncomingPartieObjectListenerServeur;
import fr.m1miage.london.classes.Joueur;


public class Reception implements Runnable {

	private ObjectInputStream in;
	private Action action = null;
	private Joueur joueur = null;
	public static List<IncomingMessageListenerServeur> listeners = new ArrayList<IncomingMessageListenerServeur>();
	public static List<IncomingPartieObjectListenerServeur> listenersPartie = new ArrayList<IncomingPartieObjectListenerServeur>();
	
	public Reception(ObjectInputStream in,Joueur joueur){
		
		this.in = in;
		this.joueur = joueur;
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
				// demande de recup les joueurs du chat
				if(action.getType()==0){
					List<Joueur> lesJoueurs = new ArrayList<Joueur>();				
					 for( Emission e : Serveur.lesClients){
						 lesJoueurs.add(e.getJoueur());
					}

					 for( Emission e : Serveur.lesClients){
							e.sendObject(0, lesJoueurs);
						}
					
				}
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
					 for (IncomingPartieObjectListenerServeur list : listenersPartie){
							list.nouvelObjet(action.getObject());
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