package fr.m1.miage.london.network.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import fr.m1.miage.london.network.Action;
import fr.m1.miage.london.network.IncomingMessageListenerClient;
import fr.m1.miage.london.network.IncomingObjectListenerClient;
import fr.m1.miage.london.network.IncomingPartieObjectListenerClient;
import fr.m1miage.london.classes.Joueur;


public class Reception implements Runnable {

	private ObjectInputStream  in;
	private Action action = null;
	private Joueur joueur;
	private static List<IncomingMessageListenerClient> listenersM = new ArrayList<IncomingMessageListenerClient>();
	private static List<IncomingObjectListenerClient> listenersO = new ArrayList<IncomingObjectListenerClient>();
	public static List<IncomingPartieObjectListenerClient> listenersPartie = new ArrayList<IncomingPartieObjectListenerClient>();



	public Reception(ObjectInputStream  in, Joueur joueur){
		this.joueur = joueur;
		this.in = in;
	}

	public static void addListenerM(IncomingMessageListenerClient toAdd){
		listenersM.add(toAdd);
	}

	public static void addListenerO(IncomingObjectListenerClient toAdd){
		listenersO.add(toAdd);
	}
	
	public static void removeListenerO(IncomingObjectListenerClient toDel){
		listenersO.remove(toDel);
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

//					for (IncomingPartieObjectListenerClient list : listenersPartie){
//						list.nouvelObjet(action.getObject());
//					}
					for (IncomingObjectListenerClient list : listenersO){
						list.nouvelObjet(action.getObject(), action.getType());
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