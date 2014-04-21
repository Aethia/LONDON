package fr.m1.miage.london.network.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;

public class Client {

	public static Socket socket = null;
	public static Thread t1;
	public static Emission emiss;

	public void seConnecter(String client){
		// creation d'un objet de test l'objet devra juste implÃ©menter la mÃ©thode  public void nouveauMessage(String message)
		testTrigger tt = new testTrigger();
		// on ajoute cet objet dans la liste des listeners
		Reception.addListener(tt);

		try {

			System.out.println("Demande de connexion");
			socket = new Socket("127.0.0.1",2009);
			System.out.println("Connexion ï¿½tablie avec le serveur, authentification :"); // Si le message s'affiche c'est que je suis connectï¿½

			t1 = new Thread(new Connexion(socket,client));
			t1.start();



		} catch (UnknownHostException e) {
			System.err.println("Impossible de se connecter ï¿½ l'adresse "+socket.getLocalAddress());
		} catch (IOException e) {
			System.err.println("Aucun serveur a l'ecoute sur le port  "+socket.getLocalPort());
		}
	}


}
