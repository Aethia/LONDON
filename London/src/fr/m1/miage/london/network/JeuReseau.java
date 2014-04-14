package fr.m1.miage.london.network;

import java.util.Scanner;

public class JeuReseau {
	
	public void JeuReseau(){
		
	}
	
	public void lancerPartieReseau(){
		System.out.println("\t 1 : héberger une partie");
		System.out.println("\t 2 : rejoindre une partie");
		Scanner sc = new Scanner(System.in);
		int nb;
		nb = sc.nextInt();
		switch (nb){
		case 1 : {
				ServeurClass serveur = new ServeurClass();
				serveur.hebergerPartie();
				break;
		}
		case 2 : break;
		default : break;
		}
		
	}

}
