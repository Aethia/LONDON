package fr.m1.miage.london.network.serveur;

import fr.m1.miage.london.network.IncomingListenerServeur;

public class testTrigger implements IncomingListenerServeur {
	public void nouveauMessage(String message) {
		System.out.println("trigger déclanché serveur : "+message);
		
	}
}
