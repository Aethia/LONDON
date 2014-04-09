package fr.m1miage.london.classes;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import fr.m1miage.london.Regles;

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
	// la main du joueurs (ses cartes)
	private Main mainDuJoueur;
	
	
	// constructeur
	public Joueur(int id, String nom, Color couleur) {
		super();
		this.id = id;
		this.nom = nom;
		this.couleur = couleur;
		this.argent= Regles.ARGENT;
		this.point_pauvrete = Regles.PTPAUVRETE;
		this.argent = Regles.ARGENT;
		point_victoire=0;
		montantEmprunts=0;
		mainDuJoueur = new Main();
		zoneConstruction=new ZoneConstruction();
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


	@Override
	public String toString() {
		return "Joueur [id=" + id + ", nom=" + nom + ", couleur=" + couleur
				+ ", point_pauvrete=" + point_pauvrete + ", point_victoire="
				+ point_victoire + ", argent=" + argent + ", montantEmprunts="
				+ montantEmprunts + ", mainDuJoueur=" + mainDuJoueur + "]";
	}
	
	public Main getMainDuJoueurCopie() {
		return mainDuJoueur.clone();
	}
	public void afficherMain(){
		System.out.println("Joueur "+nom+"\n Main : "+mainDuJoueur.toString());
	}
	
	/*
	 * ajouter une carte dans la main
	 */
	public void ajouterCarteMain(Carte c){
		this.mainDuJoueur.ajouterCarte(c);
	}
	
	public void ajouterCartesMain(List<Carte> cartes) {
		this.mainDuJoueur.ajouterCartes(cartes);
	}
	
	/*
	 * supprimer une carte dans la main par l'indice de la carte
	 */
	public void supprimerCarteMainParIndice(int indice){
		this.mainDuJoueur.supprimerCarteParIndice(indice);
	}
	
	/*
	 * supprimer une carte dans la main par l'id de la carte
	 */
	public Boolean supprimerCarteMainParId(int idCarte){
		return this.mainDuJoueur.supprimerCarteParId(idCarte);
	}
	
	public Carte choisirCarteParId(int idCarte){
		return this.mainDuJoueur.choisirCarte(idCarte);				
	}
	
	/*
	 * vérifier si le joueur peut finir son tour
	 */
	public Boolean VerifierFinDeTour(){
		return this.mainDuJoueur.VerifierQteCarteFinDeTour();
	}
	
	/*
	 * retourner les cartes de la main
	 */
	public List<Carte> getLesCartes() {
		return this.mainDuJoueur.getLesCartes();
	}

	/*
	 * compter le nb de cartes dans la main
	 */
	public int getNb_cartes() {
		return this.mainDuJoueur.getNb_cartes();
	}
	
	//affiche les cartes de la même couleur qu'une carte choisie
		public List<Carte> getCartesCouleur(Carte c){
			String couleur = c.getCouleur();
			List<Carte> cartesCouleur = new ArrayList<Carte>();
			for(Carte i:this.getMainDuJoueur().getLesCartes()){
				if(i.getCouleur().compareTo(couleur)==0 && i!=c){
					cartesCouleur.add(i);
				}
			}
			return cartesCouleur;
			
		}
	
	//on vérifie que la carte choisie par la joueur existe dans sa main
		public boolean verifPresenceCarte(Carte carte, List<Carte> liste){
			boolean presence=false;
			for(Carte c:liste){
				if(c.getId_carte() == carte.getId_carte()){
					presence = true;
				}
			}
			
			return presence;
		}
	
	//On construit "une carte" sur une pile donnée, et on défausse une carte de la même couleur
	public String construire(Carte cPosee, Carte cDefaussee, int indexPile){
		if(this.verifPresenceCarte(cPosee, mainDuJoueur.getLesCartes())){
			if(this.verifPresenceCarte(cDefaussee, this.getCartesCouleur(cPosee))){
				if(cPosee.getPrix()<= argent){ 		
					if(this.zoneConstruction.getNbPiles()==0 || indexPile == 0){ //s'il n'y a pas de piles ou que le joueur choisit l'option créer une pile
						this.zoneConstruction.addPile(cPosee);	
					}
					else{
						this.zoneConstruction.ajouterCarte(indexPile-1, cPosee); //si le joueur choisir le numéro de la pile
					}			
					argent -= cPosee.getPrix();
					this.mainDuJoueur.supprimerCarteParId(cDefaussee.getId_carte());
					this.mainDuJoueur.supprimerCarteParId(cPosee.getId_carte());
					Plateau.etalage.ajouterCarte(cDefaussee);
					return "Construction terminée !";
				}
				else{
					return "Argent insuffisant";
				}
			}
			else{
				return "Carte à défausser n'existe pas";
				
			}
		}
		else{
			return "Carte à poser n'existe pas";
		}
	}
	
	public void piocher(Pioche laPioche){
		mainDuJoueur.ajouterCarte(laPioche.tirerUneCarte());
	}

	/*
	 * Emprunter de l'argent
	 */
	public void emprunter(int Montant){

			if (Montant > 0 && Montant % 10 == 0) {
				this.argent += Montant;
				this.montantEmprunts += Montant;
				System.out.println("Vous venez d'emprunter £" + Montant + ". \n");
			} 
			else{
				System.err.println("Montant incorrect \n");
			}

	}
}
