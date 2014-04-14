package fr.m1.miage.london.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServeurClass {
	
	ServerSocket socket  ;

	
	
	public void ServeurClass(){
		
	}
	
	public void hebergerPartie(){
		try {
			socket = new ServerSocket(2009);
			Thread t = new Thread(new AccepterClient(socket));
			t.start();
			System.out.println("En attente des clients");			
			} catch (IOException e) {				
				e.printStackTrace();
			}
	}

}
