package fr.m1.miage.london.network.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

import fr.m1miage.london.classes.Joueur;


public class Chat_ClientServeur implements Runnable {

	private Socket socket;
	private ObjectOutputStream  out = null;
	private ObjectInputStream  in = null;
	private Scanner sc;
	private Thread t3, t4;
	private Joueur joueur;
	
	public Chat_ClientServeur(Socket s,Joueur joueur){
		this.joueur = joueur;
		socket = s;
	}
	
	public void run() {
		try {
			out = new ObjectOutputStream (socket.getOutputStream());
			in = new ObjectInputStream (socket.getInputStream());
			
			sc = new Scanner(System.in);
			
			Emission e1 = new Emission(out,joueur);
			Sender.e = e1;
			Thread t4 = new Thread(e1);
			t4.start();
			Thread t3 = new Thread(new Reception(in,joueur));
			t3.start();
		
		   
		    
		} catch (IOException e) {
			System.err.println("Le serveur distant s'est d�connect� !");
		}
	}

}