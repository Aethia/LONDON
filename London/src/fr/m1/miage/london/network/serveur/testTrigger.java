package fr.m1.miage.london.network.serveur;

import fr.m1.miage.london.network.IncomingMessageListenerServeur;

public class testTrigger implements IncomingMessageListenerServeur {
	public void nouveauMessage(String message) {
		System.out.println("trigger déclanché serveur : "+message);
		
	}
}
