package fr.m1miage.london;

public class Regles {
	public final static int PTPAUVRETE = 5;
	public final static int ARGENT = 5;
	public final static int NBMAXCARTES=9;
	public final static int NBCARTESDEPART=6;
	public final static int NBMAXJOUEURS=5;
	public final static int EMPRUNTMAX = 100;
	public final static int PIOCHER_X_CARTES = 3;
	
	public static final int CONSTRUIRE = 1;
	public static final int RESTAURER = 2;
	
	//0: aucun cout, 1 :montantLivres, 2 : Carte couleur unique, 3 : n'importe quelle couleur
	public static final int ACTIVATION_AUCUN = 0;
	public static final int ACTIVATION_LIVRES = 1;
	public static final int ACTIVATION_UNIQUE = 2;
	public static final int ACTIVATION_ANYCOLOR = 3;
}
