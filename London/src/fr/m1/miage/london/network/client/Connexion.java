package fr.m1.miage.london.network.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import fr.m1miage.london.classes.Joueur;

public class Connexion implements Runnable {

	private Socket socket = null;
	public static Thread t2;
	public static Joueur joueur = null;
	private ObjectOutputStream  out = null;
	private ObjectInputStream  in = null;
	private boolean connect = false;
	
	public Connexion(Socket s,Joueur client){
		this.joueur = client;
		socket = s;
	}
	
	public void run() {
		
		try {
			
		out = new ObjectOutputStream (socket.getOutputStream());
		in = new ObjectInputStream (socket.getInputStream());		
		
		out.writeObject(joueur);
		out.flush();
		
		
		if(in.readInt() == 1){
			
		System.out.println("Je suis connect� "); 
		connect = true;
		  }
		else {
			System.err.println("Impossible de se connecter au serveur"); 
		  }
		
	
			
			t2 = new Thread(new Chat_ClientServeur(socket,joueur));
			t2.start();
		
		} catch (IOException e) {
			
			System.err.println("Le serveur ne r�pond plus ");
		}
	}

}