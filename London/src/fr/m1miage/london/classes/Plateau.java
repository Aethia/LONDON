package fr.m1miage.london.classes;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import fr.m1miage.london.Regles;
import fr.m1miage.london.db.QuartiersManager;

public class Plateau implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2566214763475494062L;
	private Map<Integer,Quartier> quartiers = new HashMap<Integer, Quartier>();
	public static Etalage etalage;
	
	public Plateau(){
		
	}
	public void init(){
		quartiers = QuartiersManager.getQuartiers();
		etalage = new Etalage(Regles.NBMAXJOUEURS);
	}
	
	public Map<Integer, Quartier> getQuartiers() {
		return quartiers;
	}
	
	
	public Quartier getQuartier(int idQuartier){
		return quartiers.get(idQuartier);
	}
	
	public Etalage getEtalage(){
		return etalage;
	}
	public void setEtalage(Etalage etalage) {
		Plateau.etalage = etalage;
	}
	
	
	
	
}
