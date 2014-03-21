package fr.m1miage.london.classes;

import java.util.ArrayList;
import java.util.List;

public class Quartier {
	private int id;
	private String nom;
	private int prix;
	private int point_victoire;
	private int nb_carte_a_piocher;
	// on peut investir ? (quartier libre ?)
	private boolean investir_possible; 
	//quartiers adjacents
	private List<Quartier> quartiersAdjacents = new ArrayList<Quartier>();
	// métro possible sur le quartier ?
	private boolean metro;
	// y'a-t-il un métro sur le quartier ?
	private boolean metro_pose;

	// le joueur propriétaire du quartier
	private Joueur proprietaireQuartier;
	// le joueur propriétaire du métro !
	private Joueur proprietaireMetro;
	
	public Quartier(){
		
	}

	public Quartier(int id, String nom, int prix, int point_victoire,
			int nb_carte_a_piocher, boolean investir_possible,
			List<Quartier> quartiersAdjacents, boolean metro) {
		super();
		this.id = id;
		this.nom = nom;
		this.prix = prix;
		this.point_victoire = point_victoire;
		this.nb_carte_a_piocher = nb_carte_a_piocher;
		this.investir_possible = investir_possible;
		this.quartiersAdjacents = quartiersAdjacents;
		this.metro = metro;
		this.metro_pose = false;
		this.proprietaireQuartier=null;
		this.proprietaireMetro = null;
	}
	
	public void investirQuartier(Joueur joueur){
		this.proprietaireQuartier = joueur;
		this.investir_possible = false; //quartier reservé
		for(Quartier q : this.quartiersAdjacents){
			if(q.proprietaireQuartier==null){ //on rend les quartier "investissables" seulement s'ils ne sont pas deja occupés
				q.investir_possible=true;
			}
		}
	}

	
	public String getNom() {
		return nom;
	}

	public int getPrix() {
		return prix;
	}

	public int getPoint_victoire() {
		return point_victoire;
	}

	public int getNb_carte_a_piocher() {
		return nb_carte_a_piocher;
	}

	public boolean isInvestir_possible() {
		return investir_possible;
	}

	public void setInvestir_possible(boolean investir_possible) {
		this.investir_possible = investir_possible;
	}

	public boolean isMetro() {
		return metro;
	}

	public void setMetro(boolean metro) {
		this.metro = metro;
	}

	public boolean isMetro_pose() {
		return metro_pose;
	}

	public void setMetro_posé(boolean metro_pose) {
		this.metro_pose = metro_pose;
	}

	public Joueur getProprietaireQuartier() {
		return proprietaireQuartier;
	}

	public void setProprietaireQuartier(Joueur proprietaireQuartier) {
		this.proprietaireQuartier = proprietaireQuartier;
	}

	public Joueur getProprietaireMetro() {
		return proprietaireMetro;
	}

	public void setProprietaireMetro(Joueur proprietaireMetro) {
		this.proprietaireMetro = proprietaireMetro;
	}
	
	
	
}
