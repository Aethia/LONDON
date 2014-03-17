package fr.m1miage.london.classes;

import java.util.ArrayList;
import java.util.List;

public class Pioche {
	private List<Carte> laPioche = new ArrayList<Carte>(); 
	
	// constucteur
	public Pioche(){
	}
	
	// ajouter carte dans la pioche
	public void ajouterCarte(Carte c){
		laPioche.add(c);
	}
	// retourner la première carte du de la pioche
	public Carte TirerUneCarte() {
		Carte tmp = laPioche.get(0);
		laPioche.remove(0);
		return tmp;
	}
	
	// vider la pioche
	public void viderPioche() {
		laPioche.clear();
	}
	
	// obtenir le nb de cartes restantes dans la pioche
	public int getNbCartes(){
		return laPioche.size();
	}
	
}
