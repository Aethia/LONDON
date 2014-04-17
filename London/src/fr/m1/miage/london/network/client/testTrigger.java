package fr.m1.miage.london.network.client;

import fr.m1.miage.london.network.IncomingListenerClient;

public class testTrigger implements IncomingListenerClient{

	@Override
	public void nouveauMessage(String message) {
		System.out.println("message recu cote console (cli), "+message);
		
	}

}
