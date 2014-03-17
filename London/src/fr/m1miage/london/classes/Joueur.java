package fr.m1miage.london.classes;

import java.awt.Color;
import java.util.HashSet;

public class Joueur {
	int id;
	String nom;
	Color couleur;
	// les points de pauvreté du joueur
	int point_pauvrete;
	// les pts de victoire du joueur
	int point_victoire;
	// l'argent dont il dispose
	int argent;
	// les zones de construction qu'il peut posséder
	HashSet<Pile> zones_construction =  new HashSet<Pile>() ;
	
	// constructeur
	public Joueur(int id, String nom, Color couleur, int point_pauvrete,
			int argent) {
		super();
		this.id = id;
		this.nom = nom;
		this.couleur = couleur;
		this.point_pauvrete = point_pauvrete;
		this.argent = argent;
	}

}
