package fr.m1miage.london.classes;

import java.util.ArrayList;
import java.util.List;

public class Pile {
	
	// liste a deux dimensions de la pile de cartes devant le joueur
	private ArrayList<ArrayList<Carte>> cartes; 
	

	public Pile(){
		cartes = new ArrayList<ArrayList<Carte>>();  
	}
	
	// ajouter une nouvelle pile vide
	public void addPile(){
		cartes.add(new ArrayList<Carte>());
	}
	
	// ajouter une nouvelle pile qui contient une carte
	public void addPile(Carte c){
		ArrayList<Carte> ar = new ArrayList<Carte>();
		ar.add(c);
		cartes.add(ar);	
	}
	
	// obtenir le nb de piles devant le joueur
	public int getNbPiles(){
		return cartes.size();
	}
	
	// obtenir le nb de cartes de la pile donnée
	public int getNbCartesPile(int indexPile){
		try {
		return cartes.get(indexPile).size();
		}
		catch (IndexOutOfBoundsException e){
			throw new IndexOutOfBoundsException("la pile n'existe pas!");
		}
	}
	
	//obtenir les cartes de la pile donnée
	public ArrayList<Carte> getCartesPile(int indexPile){
		return cartes.get(indexPile);
	}
	
	//ajouter une carte sur la pile donnée
	public void ajouterCarte(int indexPile, Carte c){
		cartes.get(indexPile).add(c);
	}

}
