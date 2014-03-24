package fr.m1miage.london.classes;

import java.util.HashMap;
import java.util.Map;

import fr.m1miage.london.db.QuartiersManager;

public class Plateau {
	private Map<Integer,Quartier> quartiers = new HashMap<Integer, Quartier>();
	
	public Plateau(){
		
	}
	public void init(){
		quartiers = QuartiersManager.getQuartiers();
	}
	
	public Map<Integer, Quartier> getQuartiers() {
		return quartiers;
	}
	
	
	public Quartier getQuartier(int idQuartier){
		return quartiers.get(idQuartier);
	}
	
	
}
