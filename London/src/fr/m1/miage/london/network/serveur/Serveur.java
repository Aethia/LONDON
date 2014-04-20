package fr.m1.miage.london.network.serveur;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class Serveur {
	public static ServerSocket ss = null;
	public static Thread t;
	public static ArrayList<Emission> lesClients = new ArrayList<Emission>();

	public void hebergerPartie(){
		testTrigger tt = new testTrigger();
		Reception.addListener(tt);

		try {
			ss = new ServerSocket(2009);
			System.out.println("Le serveur est a l'ecoute du port"+ss.getLocalPort());

			t = new Thread(new Accepter_connexion(ss));
			t.start();

		} catch (IOException e) {
			System.err.println("Le port "+ss.getLocalPort()+" est d�j� utilis� !");
		}

	}





}