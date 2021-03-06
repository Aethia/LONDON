package fr.m1.miage.london.network;

import java.awt.Color;
import java.util.Scanner;

import fr.m1.miage.london.network.client.Client;
import fr.m1.miage.london.network.serveur.Serveur;
import fr.m1miage.london.classes.Joueur;

public class JeuReseau {
	
	public JeuReseau(){
		
	}
	
	public static void main(String[] args){
		lancerPartieReseau();
	}
	
	public static void lancerPartieReseau(){
		System.out.println("\t 1 : héberger une partie");
		System.out.println("\t 2 : rejoindre une partie");
		Scanner sc = new Scanner(System.in);
		int nb;
		nb = sc.nextInt();
		switch (nb){
		case 1 : {
				Serveur srv = new Serveur();
				srv.hebergerPartie();
				break;
		}
		case 2 : {
				Client cli = new Client("127.0.0.1");
				cli.seConnecter(new Joueur(0,"toto",Color.RED));
				break;
		}
		default : break;
		}
		sc.close();
	}

}
