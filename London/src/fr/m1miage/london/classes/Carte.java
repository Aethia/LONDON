package fr.m1miage.london.classes;

public class Carte {
	private int id_carte;
	private String nom;
	private String periode;
	private int prix;
	private String couleur;
	private int pointsVictoire; //points de victoire gagnés en fin de partie
	
	
	private int id_effet;
	private int id_effet_activation;
	private boolean desactivee;
	private String image;
	
	//zone bas de carte => lors de l'activation
	//voir page 3 du manuel
	private int id_CoutActivation; //argent ou carte
	private boolean aRetourner;
	private int argentActivation;
	private int ptsVictActivation;
	private int ptsPauvretePerdus;
	private int ptsPauvreteGagnes;
	
	
	public Carte(){
	}
	

	
	public Carte(int id_carte, String nom, String periode, int prix,
			String couleur, int pointsVictoire, int id_effet,
			int id_effet_activation, String image,
			int id_CoutActivation, boolean aRetourner, int argentActivation,
			int ptsVictActivation, int ptsPauvretePerdus, int ptsPauvreteGagnes) {
		super();
		this.id_carte = id_carte;
		this.nom = nom;
		this.periode = periode;
		this.prix = prix;
		this.couleur = couleur;
		this.pointsVictoire = pointsVictoire;
		this.id_effet = id_effet;
		this.id_effet_activation = id_effet_activation;
		this.desactivee = false;
		this.image = image;
		this.id_CoutActivation = id_CoutActivation;
		this.aRetourner = aRetourner;
		this.argentActivation = argentActivation;
		this.ptsVictActivation = ptsVictActivation;
		this.ptsPauvretePerdus = ptsPauvretePerdus;
		this.ptsPauvreteGagnes = ptsPauvreteGagnes;
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



	public int getId_effet() {
		return id_effet;
	}



	public int getId_effet_activation() {
		return id_effet_activation;
	}



	public boolean isDesactivee() {
		return desactivee;
	}



	public String getImage() {
		return image;
	}



	public int getId_CoutActivation() {
		return id_CoutActivation;
	}



	public boolean isaRetourner() {
		return aRetourner;
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



	@Override
	public String toString() {
		String tmp = "";
		tmp += "id : "+this.id_carte+"\n";
		tmp += "nom : "+this.nom+"\n";
		tmp += "periode : "+this.periode+"\n";
		return tmp;
	}
	
	
	
}
