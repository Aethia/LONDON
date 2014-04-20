package fr.m1miage.london.classes;

import java.io.Serializable;
import java.util.ArrayList;

public class Etalage implements Serializable{
	
	private int tailleEtalage;
	private ArrayList<Carte> rangee1 = new ArrayList<Carte>();
	private ArrayList<Carte> rangee2 = new ArrayList<Carte>();
	
	public Etalage(){		
	}
	
	/*
	 * si on ajoute une carte dans la 2e rang�e qui est pleine, on remonte la 2e rang�e
	 */
	private void remonterCartes(){
		rangee1 = new ArrayList<Carte>(rangee2);
	}
	/*
	 * Recuperer une carte de l'étalage (action piocher 3 cartes)
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
	
	/*
	 * ajouter une carte dans la zone de construction
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
	
	/*
	 * vider totalement la zone de construction
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
