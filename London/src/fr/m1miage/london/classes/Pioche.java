package fr.m1miage.london.classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import fr.m1miage.london.db.CartesManager;

public class Pioche {
	private List<Carte> laPioche = new ArrayList<Carte>(); 
	
	// constucteur
	public Pioche(){
	}
	
	
	public void init(){
		Set<Carte> tas = CartesManager.getCartes();
		
		List<Carte> rndmA = new ArrayList<Carte>();
		List<Carte> rndmB = new ArrayList<Carte>();
		List<Carte> rndmC = new ArrayList<Carte>();
		for(Carte c:tas){
			switch(c.getPeriode()){
			case "A": rndmA.add(c);
				break;
			case "B": rndmB.add(c);
			break;
			case "C": rndmC.add(c);
			break;
			}
		}
		Collections.shuffle(rndmA);
		Collections.shuffle(rndmB);
		Collections.shuffle(rndmC);
		
		laPioche.addAll(rndmA);
		laPioche.addAll(rndmB);
		laPioche.addAll(rndmC);
		
		System.out.println(laPioche.toString());
	}
	
	// ajouter carte dans la pioche
	public void ajouterCarte(Carte c){
		laPioche.add(c);
	}
	// retourner la première carte du de la pioche
	public Carte TirerUneCarte() {
		Carte tmp = laPioche.get(0);
		laPioche.remove(0);
		return tmp;
	}
	
	// vider la pioche
	public void viderPioche() {
		laPioche.clear();
	}
	
	// obtenir le nb de cartes restantes dans la pioche
	public int getNbCartes(){
		return laPioche.size();
	}
	
}
