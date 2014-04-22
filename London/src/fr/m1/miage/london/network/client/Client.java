package fr.m1.miage.london.network.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

	public static Socket socket = null;
	public static Thread t1;
	public static Emission emiss;

	public void seConnecter(String client){

		try {

			System.out.println("Demande de connexion");
			//socket = new Socket("192.168.227.71",2009);	
			socket = new Socket("127.0.0.1",2009);
			System.out.println("Connexion établie avec le serveur, authentification :"); // Si le message s'affiche c'est que je suis connectï¿½

			t1 = new Thread(new Connexion(socket,client));
			t1.start();



		} catch (UnknownHostException e) {
			System.err.println("Impossible de se connecter à l'adresse "+socket.getLocalAddress());
		} catch (IOException e) {
			System.err.println("Aucun serveur a l'ecoute sur le port  "+socket.getLocalPort());
		}
	}


}
