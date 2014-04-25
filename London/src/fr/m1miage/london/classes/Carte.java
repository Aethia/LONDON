package fr.m1miage.london.classes;

import java.io.Serializable;


/**
 * Classe Carte qui permet de stocker toutes les informations sur les cartes
 */

public class Carte implements Serializable{
    /**
     * L'id de la carte
     * @see Carte#getId_carte()
     */
	private int id_carte;
    /**
     * Le nom de la carte
     * @see Carte#getNom()
     */
	private String nom;
    /**
     * La période de la carte (A,B,C)
     * @see Carte#getPeriode()
     */
	private String periode;
    /**
     * Le prix à payer pour poser la carte
     * @see Carte#getPrix()
     */
	private int prix;
    /**
     * La couleur de la carte
     * @see Carte#getCouleur()
     */
	private String couleur;
    /**
     * les points de victoire que la carte rapportera en fin de partie
     * @see Carte#getPointsVictoire()
     */
	private int pointsVictoire; 

    /**
     * L'effet que va produire cette carte lorsque qu'elle sera posée
     * @see Carte#getEffet_passif()
     */
	private Effet effet_passif;
    /**
     * L'effet que va produire cette carte lorsque qu'elle sera activée
     * @see Carte#getEffet_actif()
     */
	private Effet effet_actif;
    /**
     * True si la carte est retournée et donc non jouable, false sinon
     * @see Carte#isDesactivee()
     */
	private boolean desactivee;
    /**
     * L'image qui représente cette carte
     * @see Carte#getImage()
     */
	private String image;

	//zone bas de carte => lors de l'activation
	//voir page 3 du manuel
    /**
     * le cout pour l'activation de cette carte (argent, autre carte...)
     * @see CoutActivation
     */
	private CoutActivation coutActivation; //argent ou carte

    /**
     * L'argent gagné à l'activation
     * @see Carte#getCoutActivation()
     */
	private int argentActivation;
    /**
     * Les points de victoire gagnés a l'activation
     * @see Carte#getPtsVictActivation()
     */
	private int ptsVictActivation;
    /**
     * Les points de pauvreté perdus a l'activation
     * @see Carte#getPtsPauvretePerdus()
     */
	private int ptsPauvretePerdus;
    /**
     * Les points de pauvreté gagnés a l'activation
     * @see Carte#getPtsPauvreteGagnes()
     */
	private int ptsPauvreteGagnes;


	public Carte(){
	}







	public Carte(int id_carte, String nom, String periode, int prix,
			String couleur, String image) {
		super();
		this.id_carte = id_carte;
		this.nom = nom;
		this.periode = periode;
		this.prix = prix;
		this.couleur = couleur;
		this.image = image;
	}







	public CoutActivation getCoutActivation() {
		return coutActivation;
	}







	public String getNom() {
		return nom;
	}

	public String getPeriode() {
		return periode;
	}


	public int getId_carte() {
		return id_carte;
	}



	public int getPrix() {
		return prix;
	}



	public String getCouleur() {
		return couleur;
	}



	public int getPointsVictoire() {
		return pointsVictoire;
	}




	public Effet getEffet_passif() {
		return effet_passif;
	}



	public Effet getEffet_actif() {
		return effet_actif;
	}



	public boolean isDesactivee() {
		return desactivee;
	}



	public String getImage() {
		return image;
	}

	public int getArgentActivation() {
		return argentActivation;
	}



	public int getPtsVictActivation() {
		return ptsVictActivation;
	}



	public int getPtsPauvretePerdus() {
		return ptsPauvretePerdus;
	}



	public int getPtsPauvreteGagnes() {
		return ptsPauvreteGagnes;
	}



	public void setPointsVictoire(int pointsVictoire) {
		this.pointsVictoire = pointsVictoire;
	}







	public void setEffet_passif(Effet effet_passif) {
		this.effet_passif = effet_passif;
	}







	public void setEffet_actif(Effet effet_actif) {
		this.effet_actif = effet_actif;
	}







	public void setDesactivee(boolean desactivee) {
		this.desactivee = desactivee;
	}







	public void setcoutActivation(CoutActivation coutActivation) {
		this.coutActivation = coutActivation;
	}








	public void setArgentActivation(int argentActivation) {
		this.argentActivation = argentActivation;
	}







	public void setPtsVictActivation(int ptsVictActivation) {
		this.ptsVictActivation = ptsVictActivation;
	}







	public void setPtsPauvretePerdus(int ptsPauvretePerdus) {
		this.ptsPauvretePerdus = ptsPauvretePerdus;
	}







	public void setPtsPauvreteGagnes(int ptsPauvreteGagnes) {
		this.ptsPauvreteGagnes = ptsPauvreteGagnes;
	}




	public boolean isConstructible(){
		if(this.couleur.equals("Gris")){
			return false;
		}
		return true;
	}


	@Override
	public String toString() {

		StringBuilder msg = new StringBuilder();
		msg.append("Carte n°").append(id_carte).append(" : \n");
		msg.append("\t Nom : ").append(nom).append(" -  Periode ").append(periode);
		msg.append(" - Prix : ").append(prix).append(" - Couleur : ").append(couleur);
		msg.append("\n\t Points de victoires en fin de partie : ").append(pointsVictoire);
		if(effet_passif!=null){
			msg.append("\n\t Effet passif : ").append(effet_passif.toString());
		}
		if(effet_actif!=null){
			msg.append("\t Effet actif : ").append(effet_actif.toString());
		}
		if(coutActivation!=null){
			msg.append("Cout de l'activation : ").append(coutActivation.toString());
		}
		msg.append("\n\t £ gagnés à l'activation : ").append(argentActivation);
		msg.append(" - Points de victoire activation : ").append(ptsVictActivation);
		msg.append(" - Points de pauvretés perdus : ").append(ptsPauvretePerdus);
		msg.append(" - Points de pauvretés gagnés : ").append(ptsPauvreteGagnes).append("\n");
		return msg.toString();
	}






}
