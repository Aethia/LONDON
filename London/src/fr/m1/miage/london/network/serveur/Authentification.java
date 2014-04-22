package fr.m1.miage.london.network.serveur;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import fr.m1.miage.london.network.IncomingMessageListenerServeur;
import fr.m1miage.london.classes.Joueur;

public class Authentification implements Runnable {

	private Socket socket;
	private ObjectOutputStream out = null;
	private ObjectInputStream in = null;
	private Joueur joueur = null;
	public boolean authentifier = false;
	public Thread t2;
	
	public Authentification(Socket s){
		 socket = s;
		}
	public void run() {
	
		try {
			
			in = new ObjectInputStream (socket.getInputStream());
			out = new ObjectOutputStream (socket.getOutputStream());
			
		
			
/*			out.println("Entrez votre login :");
			out.flush();*/
			try {
				joueur = (Joueur)in.readObject();
				System.out.println("recu du client : "+joueur.getNom());
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}



				out.writeInt(1);
				
				for(Emission e : Serveur.lesClients){
					e.sendMessageString(joueur.getNom() +" vient de se connecter ");
				}
				for (IncomingMessageListenerServeur list : Reception.listeners){
					list.nouveauMessage(joueur.getNom() +" vient de se connecter ");
				}
				out.flush();
				authentifier = true;	

		 
			t2 = new Thread(new Chat_ClientServeur(socket,joueur));
			t2.start();
			
		} catch (IOException e) {
			
			System.err.println(joueur.getNom()+" ne r�pond pas !");
		}
	}
	
	private static boolean isValid(String login, String pass) {
		return true;
		
	}

}