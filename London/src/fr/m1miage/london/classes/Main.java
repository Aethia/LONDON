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
	
	// suppression d'une carte dans la main par l'indice dans la main
	public void supprimerCarteParIndice(int indice){
		try {
			lesCartes.remove(indice);
		}
		catch (IndexOutOfBoundsException e){
			throw new IndexOutOfBoundsException("erreur lors de la suppression");
		}
	}
	
	// suppression d'une carte dans la main par l'id de carte
	public Boolean supprimerCarteParId(int idCarte){
		for (Carte c : lesCartes) {
			if (c.getId_carte() == idCarte) {
				lesCartes.remove(c);
				return true;
			}
		}
		return false;
	}

	public List<Carte> getLesCartes() {
		return lesCartes;
	}

	public int getNb_cartes() {
		return lesCartes.size();
	}

	@Override
	public String toString() {
		String tmp = "";
		for(Carte c : lesCartes) {
			tmp += c.toString();
			tmp += "----------\n";
		}
		return tmp;
	}
	
	public Boolean VerifierQteCarteFinDeTour(){
		if (this.getNb_cartes() <= 9)
			return true;
		else
			return false;
	}
	
	

	
}
