package fr.m1miage.london.classes;

import java.awt.Color;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Joueur {
	private int id;
	private String nom;
	private Color couleur;
	// les points de pauvreté du joueur
	private int point_pauvrete;
	// les pts de victoire du joueur
	private int point_victoire;
	// l'argent dont il dispose
	private int argent;
	
	private int montantEmprunts;
	// les zones de construction qu'il peut posséder
	private ZoneConstruction zoneConstruction;
	
	private Main mainDuJoueur;
	
	
	// constructeur
	public Joueur(int id, String nom, Color couleur, int point_pauvrete,
			int argent) {
		super();
		this.id = id;
		this.nom = nom;
		this.couleur = couleur;
		this.point_pauvrete = point_pauvrete;
		this.argent = argent;
		point_victoire=0;
		montantEmprunts=0;
		mainDuJoueur = new Main();
	}


	public int getId() {
		return id;
	}


	public String getNom() {
		return nom;
	}


	public Color getCouleur() {
		return couleur;
	}


	public int getPoint_pauvrete() {
		return point_pauvrete;
	}


	public int getPoint_victoire() {
		return point_victoire;
	}


	public int getArgent() {
		return argent;
	}


	public int getMontantEmprunts() {
		return montantEmprunts;
	}


	public ZoneConstruction getZone_construction() {
		return zoneConstruction;
	}


	public Main getMainDuJoueur() {
		return mainDuJoueur;
	}
	
	public Main getMainDuJoueurCopie() {
		return mainDuJoueur.clone();
	}
	
	public void ajouterCarteMain(Carte c){
		this.mainDuJoueur.ajouterCarte(c);
	}
	
	public void supprimerCarteMainParIndice(int indice){
		this.mainDuJoueur.supprimerCarteParIndice(indice);
	}
	
	public Boolean supprimerCarteMainParId(int idCarte){
		return this.mainDuJoueur.supprimerCarteParId(idCarte);
	}
	
	public Boolean VerifierFinDeTour(){
		return this.mainDuJoueur.VerifierQteCarteFinDeTour();
	}
	
	public List<Carte> getLesCartes() {
		return this.mainDuJoueur.getLesCartes();
	}

	public int getNb_cartes() {
		return this.mainDuJoueur.getNb_cartes();
	}
	

}
