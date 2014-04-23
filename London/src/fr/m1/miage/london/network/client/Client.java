package fr.m1.miage.london.network.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import fr.m1miage.london.classes.Joueur;

public class Client {

	public static Socket socket = null;
	public static Thread t1;
	public static Emission emiss;

	public void seConnecter(Joueur client){

		try {

			System.out.println("Demande de connexion");
			socket = new Socket("10.10.127.83",2009);

			t1 = new Thread(new Connexion(socket,client));
			t1.start();



		} catch (UnknownHostException e) {
			System.err.println("Impossible de se connecter à l'adresse "+socket.getLocalAddress());
		} catch (IOException e) {
			System.err.println("Aucun serveur a l'ecoute sur le port  "+socket.getLocalPort());
		}
	}


}
