package fr.m1miage.london.classes;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import fr.m1miage.london.db.QuartiersManager;
import fr.m1miage.london.ui.graphics.AreaColorRect;

public class Plateau implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2566214763475494062L;
	private Map<Integer,Quartier> quartiers = new HashMap<Integer, Quartier>();
	
	private Etalage etalage;
	private HashMap<Integer, AreaColorRect> listeZones = new HashMap<Integer, AreaColorRect>();
	
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
			if(quartiers.get(key).isMetro() && quartiers.get(key).getProprietaireQuartier()!=null){
				qMetro.put(key, quartiers.get(key));
			}
		}
		return qMetro;
	}
	
	public boolean PoserMetro(){
		
		boolean tmp=false;
		
		if(this.getQuartiersMetro().size()==1){
			for(Integer key : this.getQuartiersMetro().keySet()){
				for(Quartier q : quartiers.get(key).getQuartiersAdjacents()){	
					if (q.getProprietaireQuartier()!=null){
						tmp=true;
					}
				}
			}
		}
		else if(this.getQuartiersMetro().size()>1){
			tmp=true;
		}
		return tmp;
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
	
	public HashMap getInvestis(){
		return listeZones;
	}
	
	
	
	
}
