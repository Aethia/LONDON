package fr.m1miage.london.classes;

import java.util.ArrayList;
import java.util.List;

public class ZoneConstruction{
	
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
	
	// obtenir le nb de cartes de la pile donn�e
	public int getNbCartesPile(int indexPile){
		try {
		return cartes.get(indexPile).size();
		}
		catch (IndexOutOfBoundsException e){
			throw new IndexOutOfBoundsException("la pile n'existe pas!");
		}
	}
	
	//obtenir les cartes de la pile donn�e
	public ArrayList<Carte> getCartesPile(int indexPile){
		return cartes.get(indexPile);
	}
	

	//ajouter une carte sur la pile donnée (commence à 0)
	public void ajouterCarte(int indexPile, Carte c){
		cartes.get(indexPile).add(c);
	}
	
	//affiche la carte au sommet d'une pile
	public  void afficherCarteDessus(){
		if(this.getNbPiles()>0){
			for(int i=0; i<this.getNbPiles();i++){
				System.out.println("Pile n�"+(i+1)+" : \n");
				System.out.println(cartes.get(i).get(cartes.get(i).size()-1).toString());
				System.out.println("carte desactivee "+cartes.get(i).get(cartes.get(i).size()-1).isDesactivee()+"----- \n");
			}
		}
	}
	
	public List<Carte> cartesTop(){
		List<Carte> cTop = new ArrayList<Carte>();
		if(this.getNbPiles()>0){
			for(int i=0; i<this.getNbPiles();i++){
				cTop.add(cartes.get(i).get(cartes.get(i).size()-1));
			}
		}
		return cTop;
	}
	
	public String toString(){
		String msg= "";
		int i = 1;
		// pour tous les tas
		for(ArrayList<Carte> unTas : cartes){
			msg += "Tas n�"+i+++"\n";
			// on veut celle sur le haut du tas
			msg += unTas.get(unTas.size()-1);
			msg += "carte desactivee : "+unTas.get(unTas.size()-1).isDesactivee();
		}
		return msg;
	}

	public ArrayList<ArrayList<Carte>> getCartes() {
		return cartes;
	}
	
	// retourne une liste de toutes les cartes sur le dessus des tas
	public List<Carte> getCarteDessus(){
		List<Carte> lesCartesDessus = new ArrayList<Carte>();
		if(this.getNbPiles()>0){
			for(int i=0; i<this.getNbPiles();i++){
				lesCartesDessus.add(cartes.get(i).get(cartes.get(i).size()-1));
			}
		}
		return lesCartesDessus;
	}
	
	public void retournerCarte(int idCarte){
		List<Carte> lesCartesDessus = getCarteDessus();
		for(Carte c : lesCartesDessus) {
			if (c.getId_carte() == idCarte && c.coutActivation().isaRetourner() == true)
				c.setDesactivee(true);
		}
		
	}
	
	
}

