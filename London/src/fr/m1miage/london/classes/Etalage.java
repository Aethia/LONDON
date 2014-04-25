package fr.m1miage.london.classes;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * la classe Etalage modélise les 2 rangées de cartes dans lesquelles on défausse les cartes
 */

public class Etalage implements Serializable{
    /**
     * La taille de l'étalage (qui est fonction du nombre de joueurs)
     */
	private int tailleEtalage;
    /**
     * première rangée de l'étalage
     */
	private ArrayList<Carte> rangee1 = new ArrayList<Carte>();
    /**
     * deuxième rangée de l'étalage
     */
	private ArrayList<Carte> rangee2 = new ArrayList<Carte>();
	
	public Etalage(){		
	}
	

	/**
	 * Méthode de remplissage de la 1e rangée si la 2e est pleine
	 */
	private void remonterCartes(){
		rangee1 = new ArrayList<Carte>(rangee2);
	}

	/**
	 * Méthode de récupérage d'une carte de l'étalage
	 * @param id : l'id de la carte désirée
	 * @return Carte : la carte désirée
	 */
	public Carte recupererCarte(int idCarte){
		Carte c=null;
		c = getCarteParIdRangee1(idCarte);
		if(c!=null){
			rangee1.remove(c);
			//remplir les vides
			if(rangee1.size()<tailleEtalage){
				int i=0;
				while(rangee1.size()<tailleEtalage && rangee2.size()>0){
					if(rangee2.size()>0){
						rangee1.add(rangee2.remove(i));
					}
					i++;
				}
			}
			return c;
		}
		c = getCarteParIdRangee2(idCarte);
		if(c!=null){
			rangee2.remove(c);
		}
		return c;
	}
	

	/**
	 * Méthode d'obtention d'une carte par son id
	 * @param index : l'index de la carte désirée dans l'étalage
	 * @return Carte : la carte désirée
	 */
	public Carte getCarteParIdRangee2(int index){
		for (Carte c : rangee2){
			if (c.getId_carte() == index)
				return c;
		}
		return null;		
	}
	
	/**
	 * Méthode d'obtention d'une carte par son id
	 * @param id : l'id de la carte désirée dans l'étalage
	 * @return Carte : la carte désirée
	 */
	public Carte getCarteParIdRangee1(int index){
		for (Carte c : rangee1){
			if (c.getId_carte() == index)
				return c;
		}
		return null;		
	}
	
	/*
	 * retourner une copie de la rang�e 2
	 */
	public ArrayList<Carte> getRangee2(){
		return new ArrayList<Carte>(rangee2);
	}
	
	/*
	 * retourner une copie de la rang�e 1
	 */
	public ArrayList<Carte> getRangee1(){
		return new ArrayList<Carte>(rangee1);
	}
	

	/**
	 * Méthode d'ajout d'une carte dans l'étalage
	 * @param Carte : la carte que l'on veut ajouter
	 */
	public void ajouterCarte(Carte c){
		if (rangee1.size() < tailleEtalage){
			rangee1.add(c);}
		else if(rangee2.size() < tailleEtalage){
			rangee2.add(c);
		}else{
			remonterCartes();
			rangee2.clear();
			rangee2.add(c);
		}
	}
	
	public String toString(){
		String tmp = "1e rangee de l'�talage \n";
		for(Carte c : rangee1)
			tmp += c.toString();
		tmp += "-------------\n2e rangee de l'�talage \n";
		for(Carte c : rangee2)
			tmp += c.toString();
		return tmp;
		
	}
	
	/**
	 * Méthode de vidage des 2 rangées de l'étalage
	 */
	public void viderEtalage(){
		rangee1 = new ArrayList<Carte>();
		rangee2 = new ArrayList<Carte>();
	}

	public int getTailleEtalage() {
		return tailleEtalage;
	}

	public void setTailleEtalage(int tailleEtalage) {
		this.tailleEtalage = tailleEtalage;
	}
	
	

}
