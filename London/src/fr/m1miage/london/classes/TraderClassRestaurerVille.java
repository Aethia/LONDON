package fr.m1miage.london.classes;
/*
 * Le but est de mettre dans cette classe les ressources 
 * dont on a besoin pour activer les cartes que le joueur
 * a choisi d'activer
 */
public class TraderClassRestaurerVille {
	private static int CoutEnLivres = 0;
	private static int nbCartesGrises = 0;
	private static int nbCartesBleues = 0;
	private static int nbCartesBrunes = 0;
	private static int nbCartesRoses = 0;
	private static int nbCartesOsefCouleur = 0;
	
	
	public static int getCoutEnLivres() {
		return CoutEnLivres;
	}
	public static int getNbCartesGrises() {
		return nbCartesGrises;
	}
	public static int getNbCartesBleues() {
		return nbCartesBleues;
	}
	public static int getNbCartesBrunes() {
		return nbCartesBrunes;
	}
	public static int getNbCartesRoses() {
		return nbCartesRoses;
	}
	public static int getNbCartesOsefCouleur() {
		return nbCartesOsefCouleur;
	}
	public static void addCoutEnLivres(int coutEnLivres) {
		CoutEnLivres = coutEnLivres;
	}
	public static void addNbCartesGrises() {
		TraderClassRestaurerVille.nbCartesGrises ++;
	}
	public static void addNbCartesBleues() {
		TraderClassRestaurerVille.nbCartesBleues ++;
	}
	public static void addNbCartesBrunes() {
		TraderClassRestaurerVille.nbCartesBrunes ++;
	}
	public static void addNbCartesRoses() {
		TraderClassRestaurerVille.nbCartesRoses ++;
	}
	public static void addNbCartesOsefCouleur() {
		TraderClassRestaurerVille.nbCartesOsefCouleur ++;
	}
	
	public static void reset(){
		TraderClassRestaurerVille.CoutEnLivres = 0;
		TraderClassRestaurerVille.nbCartesGrises = 0;
		TraderClassRestaurerVille.nbCartesBleues = 0;
		TraderClassRestaurerVille.nbCartesBrunes = 0;
		TraderClassRestaurerVille.nbCartesRoses = 0;
		TraderClassRestaurerVille.nbCartesOsefCouleur = 0;
	}
	
	
}
