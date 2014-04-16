package fr.m1.miage.london.network;

import java.util.Scanner;

import fr.m1.miage.london.network.client.Client;
import fr.m1.miage.london.network.serveur.Serveur;

public class JeuReseau {
	
	public void JeuReseau(){
		
	}
	
	public static void main(String[] args){
		lancerPartieReseau();
	}
	
	public static void lancerPartieReseau(){
		System.out.println("\t 1 : h�berger une partie");
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
				Client cli = new Client();
				cli.seConnecter();
				break;
		}
		default : break;
		}
		
	}

}
