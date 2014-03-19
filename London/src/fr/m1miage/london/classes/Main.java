package fr.m1miage.london.classes;

import java.util.ArrayList;
import java.util.List;

public class Main {
	private List<Carte> lesCartes = new ArrayList<Carte>(); 
	
	public Main(){
	}
	
	// ajouter une carte dans la main
	public void ajouterCarte(Carte c){
		lesCartes.add(c);
	}

	public List<Carte> getLesCartes() {
		return lesCartes;
	}

	public int getNb_cartes() {
		return lesCartes.size();
	}

	@Override
	public String toString() {
		return "Main [lesCartes=" + lesCartes + "]";
	}
	
	

	
}
