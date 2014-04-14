package fr.m1.miage.london.network;

import java.io.IOException;
import java.net.Socket;

public class ClientClass {
	public void ClientClass(){
		
	}
	
	public void rejoindrePartie(){
		Socket socket;
		try {
		socket = new Socket("localhost",2009);
		socket.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
}
