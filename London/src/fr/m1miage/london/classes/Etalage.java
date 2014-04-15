package fr.m1miage.london.classes;

import java.io.Serializable;
import java.util.ArrayList;

public class Etalage implements Serializable{
	
	private int tailleEtalage;
	private ArrayList<Carte> rangee1 = new ArrayList<Carte>();
	private ArrayList<Carte> rangee2 = new ArrayList<Carte>();
	
	public Etalage(int tailleEtalage){
		this.tailleEtalage = tailleEtalage;
		
	}
	
	/*
	 * si on ajoute une carte dans la 2e rangée qui est pleine, on remonte la 2e rangée
	 */
	private void remonterCartes(){
		rangee1 = new ArrayList<Carte>(rangee2);
	}
	
	/*
	 * retourner la carte par l'id
	 */
	public Carte getCarteParIdRangee2(int index){
		for (Carte c : rangee2){
			if (c.getId_carte() == index)
				return c;
		}
		return null;		
	}
	
	/*
	 * retourner la carte par l'id
	 */
	public Carte getCarteParIdRangee1(int index){
		for (Carte c : rangee1){
			if (c.getId_carte() == index)
				return c;
		}
		return null;		
	}
	
	/*
	 * retourner une copie de la rangée 2
	 */
	public ArrayList<Carte> getRangee2(){
		return new ArrayList<Carte>(rangee2);
	}
	
	/*
	 * retourner une copie de la rangée 1
	 */
	public ArrayList<Carte> getRangee1(){
		return new ArrayList<Carte>(rangee1);
	}
	
	/*
	 * ajouter une carte dans la zone de construction
	 */
	public void ajouterCarte(Carte c){
		if (rangee1.size() < tailleEtalage){
			rangee1.add(c);}
		else {
			remonterCartes();
			rangee1.clear();
			rangee1.add(c);
		}
	}
	
	public String toString(){
		String tmp = "1e rangee de l'étalage \n";
		for(Carte c : rangee1)
			tmp += c.toString();
		tmp += "-------------\n2e rangee de l'étalage \n";
		for(Carte c : rangee2)
			tmp += c.toString();
		return tmp;
		
	}
	
	/*
	 * vider totalement la zone de construction
	 */
	public void viderEtalage(){
		rangee1 = new ArrayList<Carte>();
		rangee2 = new ArrayList<Carte>();
	}

}
