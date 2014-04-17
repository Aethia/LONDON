package fr.m1miage.london.classes;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import fr.m1miage.london.db.QuartiersManager;

public class Plateau implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2566214763475494062L;
	private Map<Integer,Quartier> quartiers = new HashMap<Integer, Quartier>();
	private Etalage etalage;
	
	public Plateau(){
		
	}
	public void init(){
		quartiers = QuartiersManager.getQuartiers();
		etalage = new Etalage();
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
	
	public Map<Integer,Quartier> getQuartiersMetro(){
		Map<Integer,Quartier> qMetro = new HashMap<Integer, Quartier>();
		for(Integer key : quartiers.keySet()){
			if(quartiers.get(key).isMetro()){
				qMetro.put(key, quartiers.get(key));
			}
		}
		return qMetro;
	}
	
	public Quartier getQuartier(int idQuartier){
		return quartiers.get(idQuartier);
	}
	
	public Etalage getEtalage(){
		return etalage;
	}
	public void setEtalage(Etalage etalage) {
		this.etalage = etalage;
	}
	
	
	
	
}
