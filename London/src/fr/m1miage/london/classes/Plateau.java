package fr.m1miage.london.classes;

import java.util.HashMap;
import java.util.Map;

import fr.m1miage.london.Regles;
import fr.m1miage.london.db.QuartiersManager;

public class Plateau {
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
	
	public Map<Integer,Quartier> getQuartiersDispo(){
		Map<Integer,Quartier> qDispo = new HashMap<Integer, Quartier>();
		for(Integer key : quartiers.keySet()){
			if(quartiers.get(key).isInvestir_possible()){
				qDispo.put(key, quartiers.get(key));
			}
		}
		return qDispo;
	}
	
	public Quartier getQuartier(int idQuartier){
		return quartiers.get(idQuartier);
	}
	
	public Etalage getEtalage(){
		return etalage;
	}
	
	
}
