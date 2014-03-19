package fr.m1miage.london.classes;

import java.util.ArrayList;

public class Etalage {
	
	private int tailleEtalage;
	private ArrayList<Carte> rangee1 = new ArrayList<Carte>();
	private ArrayList<Carte> rangee2 = new ArrayList<Carte>();
	
	public Etalage(int tailleEtalage){
		this.tailleEtalage = tailleEtalage;
		
	}
	
	private void remonterCartes(){
		rangee1 = new ArrayList<Carte>(rangee2);
	}
	
	public Carte getCarteParIdRangee2(int index){
		for (Carte c : rangee2){
			if (c.getId_carte() == index)
				return c;
		}
		return null;		
	}
	
	public Carte getCarteParIdRangee1(int index){
		for (Carte c : rangee1){
			if (c.getId_carte() == index)
				return c;
		}
		return null;		
	}
	
	public ArrayList<Carte> getRangee2(){
		return rangee2;
	}
	
	public ArrayList<Carte> getRangee1(){
		return rangee1;
	}
	
	public void ajouterCarte(Carte c){
		if (rangee2.size() < tailleEtalage)
			rangee2.add(c);
		else {
			remonterCartes();
			rangee2.clear();
			rangee2.add(c);
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
	
	public void viderEtalage(){
		rangee1 = new ArrayList<Carte>();
		rangee2 = new ArrayList<Carte>();
	}

}
