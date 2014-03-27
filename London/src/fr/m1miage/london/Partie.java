package fr.m1miage.london;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import fr.m1miage.london.classes.Joueur;
import fr.m1miage.london.classes.Pioche;
import fr.m1miage.london.classes.Plateau;


public class Partie {
	private List<Joueur> listeJoueurs = new ArrayList<Joueur>();
	private int nbJoueurs = 0;
	private Plateau plateau;
	private Pioche pioche;
	private Scanner sc = new Scanner(System.in);

	private final Color rouge = Color.red;
	private final Color vert = Color.green;
	private final Color jaune = Color.yellow;
	private final Color bleu = Color.blue;
	
	// le joueur actuellement actif
	private int joueurActif;
	
	
	public Partie(){
		this.plateau = new Plateau();
		this.pioche = new Pioche();
	}
	
	private void creerJoueurs(int nbJoueurs){
		Color couleur = rouge;
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
	}
	
	public void init(){
		boolean condition = false;
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
		creerJoueurs(nbJoueurs);
		
		
		plateau.init();
		pioche.init();
		
		// on distribue les cartes (los cartos en espagnol)
		for(Joueur i: listeJoueurs){
			System.out.println("Hello");
			i.ajouterCartesMain(pioche.tirerNCartes(Regles.NBCARTESDEPART));
		}
	}
	
	// faire passer le joueur actif au joueur suivant
	private void joueurSuivant(){
		if (joueurActif+1 >= nbJoueurs) {
			joueurActif = 0;
		}
		else {
			joueurActif++;
		}
		
	}
	
	public void lancerJeu(){
		joueurActif = 0;
		System.out.println("patie lancée c'est au tour de "+listeJoueurs.get(joueurActif));
		joueurSuivant();
		System.out.println("patie lancée c'est au tour de "+listeJoueurs.get(joueurActif));
	}
}
