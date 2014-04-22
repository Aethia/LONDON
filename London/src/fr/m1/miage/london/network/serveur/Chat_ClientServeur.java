package fr.m1.miage.london.network.serveur;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import fr.m1miage.london.classes.Joueur;


public class Chat_ClientServeur implements Runnable {

	private Socket socket = null;
	private ObjectInputStream in = null;
	private ObjectOutputStream out = null;
	private Joueur joueur;
	private Thread t3, t4;
	
	
	public Chat_ClientServeur(Socket s, Joueur joueur){
		socket = s;
		this.joueur = joueur;
	}
	
	
	public void run() {
		
		try {
		in = new ObjectInputStream(socket.getInputStream());
		out = new ObjectOutputStream(socket.getOutputStream());
		
		
		Thread t3 = new Thread(new Reception(in,joueur));
		t3.start();
		Emission e = new Emission(out,joueur);
		Serveur.lesClients.add(e);
		Thread t4 = new Thread(e);
		t4.start();
		
		} catch (IOException e) {
			System.err.println(joueur.getNom() +"s'est d�connect� ");
		}
}
}
