package fr.m1miage.london.classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

import fr.m1miage.london.GestionErreurs;
import fr.m1miage.london.Partie;

public class Effet implements Serializable{
	private int idEffet;
	private String nomEffet;
	private int type; //1 = passif, 2 = actif
	
	public Effet(){
		
	}
	
	public Effet(int idEffet, String nomEffet, int type){
		this.idEffet = idEffet;
		this.nomEffet = nomEffet;
		this.type = type;
	}

	public int getIdEffet() {
		return idEffet;
	}

	public String getNomEffet() {
		return nomEffet;
	}

	public int getType() {
		return type;
	}

	@Override
	public String toString() {
		StringBuilder msg= new StringBuilder(); //"Effet \n\t";
				msg.append("id : ").append(idEffet).append(" - texte : ").append(nomEffet).append(" de type ");
		if(type==1){
			msg.append("passif.\n");
		}else{
			msg.append("actif.\n");
		}
		
		return msg.toString();
	}

	//cartes 19, 106, 110
	//effet 5
	//on pioche 2 cartes
	public GestionErreurs prendreDeuxCartes(Pioche pioche, Joueur j){
		if(pioche.getNbCartes() >= 2){
			j.ajouterCartesMain(pioche.tirerNCartes(2));
			return GestionErreurs.NONE;
		}
		else
			return GestionErreurs.NOT_ENOUGH_CARD;
	}
	
	//cartes 37, 63
	//effet 7
	//on reçoit un point de victoire pour chaque carte non brune dans la zone de construction
	public void pVPourCartesNonBrune(Joueur j){
		//for(int i = 0; i < j.getZone_construction().getNbPiles(); i++){
			//ArrayList<Carte> pile= j.getZone_construction().getCartesPile(i);
		
		ArrayList<ArrayList<Carte>> zc = j.getZone_construction().getCartes();
		for(ArrayList<Carte> pile : zc){
				
			for(Carte c: pile){
				if(!c.getCouleur().equals("Brun"))
					j.setAddPoint_victoire(1);
			}
		}
	}
	
	//cartes 50, 62, 94
	//effet 11
	//on reçoit un point de victoire pour chaque carte brune dans la zone de construction
	public void pVPourCartesBrune(Joueur j){
		ArrayList<ArrayList<Carte>> zc = j.getZone_construction().getCartes();
		for(ArrayList<Carte> pile : zc){
			for(Carte c: pile){
				if(c.getCouleur().equals("Brun"))
					j.setAddPoint_victoire(1);
			}
		}
	}
	
	//cartes 91, 93
	//effet 25
	//£2 pour chaque quartier au nord de la tamise
	public void argentQuartiersNord(Plateau plateau, Joueur j){
		Map<Integer,Quartier> quartiers = plateau.getQuartiers();
		for(Integer key : quartiers.keySet()){
			if( ( quartiers.get(key).getProprietaireQuartier() == j ) && ( !quartiers.get(key).isAuSudTamise() ) ) {
				j.setAddArgent(2);
			}
		}
	}
	
	//cartes 83
	//effet 23
	//£2 pour chaque quartier au sud de la tamise
	public void argentQuartiersSud(Plateau plateau, Joueur j){
		Map<Integer,Quartier> quartiers = plateau.getQuartiers();
		for(Integer key : quartiers.keySet()){
			if( ( quartiers.get(key).getProprietaireQuartier() == j ) && ( quartiers.get(key).isAuSudTamise() ) ) {
				j.setAddArgent(2);
			}
		}
	}
	
	//cartes 54, 67
	//effet 12
	//£1 pour chaque quartier occupé
	public void argentQuartiersOccupes(Plateau plateau, Joueur j){
		Map<Integer,Quartier> quartiers = plateau.getQuartiers();
		for(Integer key : quartiers.keySet()){
			if(quartiers.get(key).getProprietaireQuartier() == j){
				j.setAddArgent(1);
			}
		}
	}
	
	//cartes 57, 59
	//effet 15
	//£2 pour chaque quartier adjacent à la tamise
	public void argentQuartiersAdjacentsTamise(Plateau plateau, Joueur j){
		Map<Integer,Quartier> quartiers = plateau.getQuartiers();
		for(Integer key : quartiers.keySet()){
			if(quartiers.get(key).getProprietaireQuartier() == j){
				boolean position = quartiers.get(key).isAuSudTamise();
				for(Quartier q : quartiers.get(key).getQuartiersAdjacents()){
					if(q.isAuSudTamise() != position){
						j.setAddArgent(2);
						break;
					}
				}
			}
		}
	}
}
