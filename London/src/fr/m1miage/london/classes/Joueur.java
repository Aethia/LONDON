package fr.m1miage.london.classes;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import fr.m1miage.london.GestionErreurs;
import fr.m1miage.london.Regles;

public class Joueur implements Comparable{
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
	
	private int nbQuartiers;


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
		nbQuartiers = 0;
	}


	public ZoneConstruction getZone_construction() {
		return zoneConstruction;
	}


	public void setZoneConstruction(ZoneConstruction zoneConstruction) {
		this.zoneConstruction = zoneConstruction;
	}


	public void setMontantEmprunts(int montantEmprunts) {
		this.montantEmprunts = montantEmprunts;
	}


	public void setAddPoint_pauvrete(int point_pauvrete) {
		this.point_pauvrete += point_pauvrete;
	}

	public void setAddQuartiers() {
		this.nbQuartiers ++;
	}
	
	public void setAddPoint_victoire(int point_victoire) {
		this.point_victoire += point_victoire;
	}


	public void setAddArgent(int argent) {
		this.argent += argent;
	}

	public void setArgent(int argent) {
		this.argent = argent;
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

	public int getQuartiers() {
		return nbQuartiers;
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


	public GestionErreurs invest(int quartier, Plateau plateau,Pioche pioche){

		boolean investir;
		if (quartier > 0 && quartier < 21) {

			//on verifie si le joueur à assez d'argent
			if(this.getArgent()>=plateau.getQuartier(quartier).getPrix()){
				//on verifie si on peut investir dans le quartier
				investir = plateau.getQuartier(quartier).investirQuartier(this);
				//si oui la fonction investirQuartier renvoie true et met a jour les quartiers adjacents
				if(investir==true){
					this.argent-=plateau.getQuartier(quartier).getPrix();

					//le joueur pioche le nb de cartes précisé sur le quartier
					this.ajouterCartesMain(pioche.tirerNCartes(plateau.getQuartier(quartier).getNb_carte_a_piocher()));

					return GestionErreurs.NONE;
				}	
				else{
					return GestionErreurs.DISABLED_QUARTIER;
				}	
			}	
			else
				return GestionErreurs.NOT_ENOUGH_MONEY;
		}
		else
			return GestionErreurs.INCORRECT_NUMBER;

	}

	//affiche les cartes de la m�me couleur qu'une carte choisie
	public List<Carte> getCartesCouleur(Carte c){

		List<Carte> cartesCouleur = new ArrayList<Carte>();
		if(c!=null){
			String couleur = c.getCouleur();
			for(Carte i:this.getMainDuJoueur().getLesCartes()){
				if(i.getCouleur().compareTo(couleur)==0 && i!=c){
					cartesCouleur.add(i);
				}
			}
		}
		return cartesCouleur;

	}

	//on v�rifie que la carte choisie par la joueur existe dans sa main
	public boolean verifPresenceCarte(Carte carte, List<Carte> liste){

		boolean presence=false;
		if(carte!=null){
			for(Carte c:liste){
				if(c.getId_carte() == carte.getId_carte()){
					presence = true;
				}
			}
		}
		return presence;
	}

	//On construit "une carte" sur une pile donn�e, et on d�fausse une carte de la m�me couleur
	public GestionErreurs construire(Carte cPosee, Carte cDefaussee, int indexPile){
		if(this.verifPresenceCarte(cPosee, mainDuJoueur.getLesCartes())){
			if(this.verifPresenceCarte(cDefaussee, this.getCartesCouleur(cPosee))){
				if(cPosee.getPrix()<= argent){ 		
					if(this.zoneConstruction.getNbPiles()==0 || indexPile == 0){ //s'il n'y a pas de piles ou que le joueur choisit l'option cr�er une pile
						this.zoneConstruction.addPile(cPosee);	
					}
					else{
						this.zoneConstruction.ajouterCarte(indexPile-1, cPosee); //si le joueur choisir le num�ro de la pile
					}			
					argent -= cPosee.getPrix();
					this.mainDuJoueur.supprimerCarteParId(cDefaussee.getId_carte());
					this.mainDuJoueur.supprimerCarteParId(cPosee.getId_carte());
					Plateau.etalage.ajouterCarte(cDefaussee);
					return GestionErreurs.NONE;
				}
				else{
					return GestionErreurs.NOT_ENOUGH_MONEY;
				}
			}
			else{
				return GestionErreurs.INCORRECT_CARTE_DEFAUSSE;

			}
		}
		else{
			return GestionErreurs.INCORRECT_CARTE;
		}
	}

	public GestionErreurs piocher(Pioche laPioche){
		if(laPioche.getNbCartes() > 0){
			mainDuJoueur.ajouterCarte(laPioche.tirerUneCarte());
			return GestionErreurs.NONE;
		}
		else
			return GestionErreurs.NOT_ENOUGH_CARD;
			
	}


	/*
	 * Emprunter de l'argent
	 */
	public GestionErreurs emprunter(int montant){
		//On v�rifie 
		if (montant > 0 && montant % 10 == 0 && montant <=100){
			if((montantEmprunts + montant) <= 100) {
				this.argent += montant;
				this.montantEmprunts += montant;

				return GestionErreurs.NONE;
			}else{
				return GestionErreurs.MAX_EMPRUNT;
			}
		} 
		return GestionErreurs.MONTANT_INCORRECT;


	}

	/*
	 * m�thode pour la restauration de la ville
	 */
	public int restaurerVille(int[] idCartes){
		return 0;
	}
	
	
	//En cas d'égalité lors du calcul du vainqueur
	@Override
	public int compareTo(Object joueur) {
		Joueur j = (Joueur)joueur;
		if(this.point_victoire < j.getPoint_victoire())
			return 1;
		else if(this.point_victoire > j.getPoint_victoire())
			return -1;
		else{
			if(this.point_pauvrete > j.getPoint_pauvrete())
				return 1;
			else if(this.point_pauvrete < j.getPoint_pauvrete())
				return -1;
			else{
				if(this.nbQuartiers < j.getQuartiers())
					return 1;
				else if(this.nbQuartiers > j.getQuartiers())
					return -1;
				else
					return 0;
				
			}
		}
	}
}