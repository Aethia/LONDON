package fr.m1.miage.london.network.serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import fr.m1.miage.london.network.IncomingListenerServeur;

public class Authentification implements Runnable {

	private Socket socket;
	private PrintWriter out = null;
	private BufferedReader in = null;
	private String login = "zero", pass =  null;
	public boolean authentifier = false;
	public Thread t2;
	
	public Authentification(Socket s){
		 socket = s;
		}
	public void run() {
	
		try {
			
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream());
			
		
			
			out.println("Entrez votre login :");
			out.flush();
			login = in.readLine();


				
				out.println("connecte");
				for(Emission e : Serveur.lesClients){
					e.sendMessage(login +" vient de se connecter ");
				}
				for (IncomingListenerServeur list : Reception.listeners){
					list.nouveauMessage(login +" vient de se connecter ");
				}
				out.flush();
				authentifier = true;	

		 
			t2 = new Thread(new Chat_ClientServeur(socket,login));
			t2.start();
			
		} catch (IOException e) {
			
			System.err.println(login+" ne r�pond pas !");
		}
	}
	
	private static boolean isValid(String login, String pass) {
		return true;
		
	}

}