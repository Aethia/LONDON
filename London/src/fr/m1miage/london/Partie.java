package fr.m1miage.london;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import fr.m1miage.london.classes.Joueur;

public class Partie {

	public Partie(){
		
	}
	
	public void lancerPartie(){
		int nbJoueurs = 0;
		Color rouge = Color.red;
		Color vert = Color.green;
		Color jaune = Color.yellow;
		Color bleu = Color.blue;
		Color couleur = rouge;
		boolean condition = false;
		List<Joueur> listeJoueurs = new ArrayList<Joueur>();
		Scanner sc = new Scanner(System.in);
		while(condition == false){
			System.out.println("Entrez le nombre de joueurs : ");
			if(sc.hasNextInt()){
				int reponse=sc.nextInt();
				if(reponse < Regles.NBMAXJOUEURS){
					nbJoueurs = reponse;	
					condition = true;
				}
				else{
					System.out.println("Valeur incorrecte. Il ne peut y avoir que de 2 à 5 joueurs.");
				}
			}
			else{
				System.out.println("Valeur incorrecte.");
				sc.next();
			}
		}
		for(int i = 0; i<nbJoueurs; i++){
			System.out.println("Entrez le nom du joueur "+(i+1)+" : ");
			String nomJoueur = sc.next();
			System.out.println("Choisissez votre couleur : \n 1.Rouge \n 2.Vert \n 3.Jaune \n 4.Bleu");
			switch(sc.nextInt()){
			case 1:
				couleur=rouge;
			case 2:
				couleur=vert;
			case 3:
				couleur=jaune;
			case 4:
				couleur=bleu;
			}
			Joueur joueur = new Joueur(i, nomJoueur, couleur);
			listeJoueurs.add(joueur);
		}
		for(Joueur i: listeJoueurs){
			System.out.println(i.toString());
		}
		
		
	}
}
