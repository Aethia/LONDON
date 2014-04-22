package fr.m1miage.london.ui;

import com.badlogic.gdx.graphics.Color;

public class Prefs {

	public static final int HAUTEUR_FENETRE = 800;
	public static final int LARGEUR_FENETRE = 1400;
	public static final String REPERTOIRE = "ressources/Images/";
	public static final String REPERTOIRE_CARTES = "ressources/Images/Cartes/";
	public static final String REPERTOIRE_QUARTIERS = "ressources/Images/Quartiers/";
	public static final String REPERTOIRE_EMPRUNTS = "ressources/Images/Boutons/Emprunts/";
	public static final String REPERTOIRE_ACTIONS = "ressources/Images/Boutons/Actions/";
	public static final String REPERTOIRE_BOUTONS = "ressources/Images/Boutons/";
	public static final String REPERTOIRE_ICONES = "ressources/Images/Icones/";
	

	public static final int HAUTEUR_CARTE = 300;
	public static final int LARGEUR_CARTE = 200;
	
	public static Color conversionCouleur(java.awt.Color c){
		System.out.println("huahuahu"+ (float)c.getRed()/255);
		return new Color((float)c.getRed()/255,(float)c.getGreen()/255,(float)c.getBlue()/255,1);
	}
}
