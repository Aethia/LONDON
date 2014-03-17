package fr.m1miage.london.classes;

import java.util.ArrayList;

public class Pioche {
	ArrayList<Carte> lesCartes = new ArrayList<Carte>(); 
	int nb_cartes;
	
	// constucteur
	public Pioche(){
	}
	
	// ajouter carte dans la pioche
	public void ajouterCarte(Carte c){
		lesCartes.add(c);
	}
	// retourner la première carte du de la pioche
	public Carte TirerUneCarte() {
		Carte tmp = lesCartes.get(0);
		lesCartes.remove(0);
		return tmp;
	}
	
	// vider la pioche
	public void viderPioche() {
		lesCartes.clear();
	}
	
	// obtenir le nb de cartes restantes dans la pioche
	public int getNbCartes(){
		return this.nb_cartes;
	}
	
}
