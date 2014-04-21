package fr.m1.miage.london.network.client;

import fr.m1.miage.london.network.IncomingMessageListenerClient;

public class testTrigger implements IncomingMessageListenerClient{

	@Override
	public void nouveauMessage(String message) {
		System.out.println("message recu cote console (cli), "+message);
		
	}

}
