package fr.m1miage.london.classes;

import java.io.Serializable;
import java.util.ArrayList;

public class ZoneConstruction implements Serializable{
	
	// liste a deux dimensions de la pile de cartes devant le joueur
	private ArrayList<ArrayList<Carte>> cartes; 
	

	public ZoneConstruction(){
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
		try{
			return cartes.size();
		}catch(NullPointerException e){
			System.out.println("Votre zone de construction est vide.");
			return 0;
		}
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
	
	//affiche la carte au sommet d'une pile
	public  void afficherCarteDessus(){
		if(this.getNbPiles()>0){
			for(int i=0; i<this.getNbPiles();i++){
				System.out.println("Pile n°"+(i+1)+" : \n");
				System.out.println(cartes.get(i).get(cartes.get(i).size()-1).toString()+"----- \n");
			}
		}
	}
	
	
}

