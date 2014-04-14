package fr.m1.miage.london.network.serveur;

import java.io.*;
import java.net.*;

public class Serveur {
 public static ServerSocket ss = null;
 public static Thread t;

 	public Serveur(){
 		
 	}
 	
 	public void hebergerPartie(){
 		try {
			ss = new ServerSocket(2009);
			System.out.println("Le serveur est à l'écoute du port "+ss.getLocalPort());
			
			t = new Thread(new Accepter_connexion(ss));
			t.start();
			
		} catch (IOException e) {
			System.err.println("Le port "+ss.getLocalPort()+" est déjà utilisé !");
		}
 	}
	
	}
