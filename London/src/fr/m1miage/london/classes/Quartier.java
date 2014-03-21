package fr.m1miage.london.classes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Quartier {
	private int id;
	private String nom;
	private int prix;
	private int point_victoire;
	private int nb_carte_a_piocher;
	// on peut investir ? (quartier libre ?)
	private boolean investir_possible; 
	//quartiers adjacents
	private Set<Quartier> quartiersAdjacents = new HashSet<Quartier>();
	
	private boolean auSudTamise;//si située au sud = true
	
	// métro possible sur le quartier ?
	private boolean metro;
	// y'a-t-il un métro sur le quartier ?
	private boolean metro_pose;

	// le joueur propriétaire du quartier
	private Joueur proprietaireQuartier;
	// le joueur propriétaire du métro !
	private Joueur proprietaireMetro;
	
	public Quartier(){
		this.metro = false;
		this.metro_pose=false;
		this.nom = new String();
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

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getPrix() {
		return prix;
	}

	public int getPoint_victoire() {
		return point_victoire;
	}

	public void setPoint_victoire(int point_victoire) {
		this.point_victoire = point_victoire;
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

	public void setPrix(int prix) {
		this.prix = prix;
	}

	public void setNb_carte_a_piocher(int nb_carte_a_piocher) {
		this.nb_carte_a_piocher = nb_carte_a_piocher;
	}

	public Set<Quartier> getQuartiersAdjacents() {
		return quartiersAdjacents;
	}

	public void setQuartiersAdjacents(Set<Quartier> quartiersAdjacents) {
		this.quartiersAdjacents = quartiersAdjacents;
	}

	public void setMetro_pose(boolean metro_pose) {
		this.metro_pose = metro_pose;
	}


	public boolean isAuSudTamise() {
		return auSudTamise;
	}


	public void setAuSudTamise(boolean auSudTamise) {
		this.auSudTamise = auSudTamise;
	}


	@Override
	public String toString() {
		StringBuilder msg = new StringBuilder();
		msg.append("Quartier n°").append(id).append(" : ").append(nom);
		msg.append("\n\t Prix : ").append(prix).append(" - Points de victoire : ").append(point_victoire);
		msg.append(" - Nombre de cartes à piocher : ").append(nb_carte_a_piocher);
		if(investir_possible==true){
			msg.append("\n\t Il est possible d'investir");
		}else{
			msg.append("\n\t Il n'est pas possible d'investir");
		}
		msg.append("\n\t Quartiers adjacents : ");
		
		for(Quartier q : quartiersAdjacents){
			//String n = q.getNom();
			if(q==null){
				System.out.println("wtf");
			}else{
			msg.append(q.getNom()).append(", ");
			}
		}
		
		
		return msg.toString();
	}
	
	
	
	
}
