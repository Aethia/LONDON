package fr.m1miage.london.classes;

import java.util.ArrayList;

public class MainJoueurs {
	ArrayList<Carte> lesCartes = new ArrayList<Carte>(); 
	int nb_cartes;
	
	public MainJoueurs(){
	}
	
	// ajouter une carte dans la main
	public void ajouterCarte(Carte c){
		lesCartes.add(c);
	}
}
