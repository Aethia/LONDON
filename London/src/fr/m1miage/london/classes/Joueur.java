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
	
	public void condictionVictoireCalcul(){
		
		//remboursement de l'emprunt (�15 pour �10 emprunt�)
		montantEmprunts = (int) (montantEmprunts*1.5);
		int nb_emprunt = montantEmprunts/15;
		//nb_packet_argent est le nombre de packet de �15 que le joueur poss�de
		int nb_packet_argent = argent/15;
		
		if(nb_emprunt >= nb_packet_argent){
			argent += (-15*nb_packet_argent);
			nb_emprunt = nb_emprunt - nb_packet_argent;
		}
		else{
			argent += (-15*nb_emprunt);
			nb_emprunt = 0;
		}
		
		//un point de pauvret� en plus par carte en main
		point_pauvrete += mainDuJoueur.getNb_cartes();
		
		//un point de victoire pour �3
		point_victoire += argent / 3;
		
		//quartier + m�tro
		
		//carte zone de construction
		for(int i=0;i<zoneConstruction.getNbPiles();i++){
			ArrayList<Carte> pile= zoneConstruction.getCartesPile(i);
			for(Carte c: pile){
				point_victoire += c.getPointsVictoire();
			}
		}

		//-7 points de victoire par emprunt non rembours�
		point_victoire += (-7*nb_emprunt);
		
		//parcours points de pauvret� pour savoir qui a le moins, on supprime � tout le monde ce nombre
		
		
		//Penalit� des points de pauvret�
		if(point_pauvrete <= 2)
			point_victoire += -1;
		else if(point_pauvrete == 3)
			point_victoire += -2;
		else if(point_pauvrete == 4)
			point_victoire += -3;
		else if(point_pauvrete == 5)
			point_victoire += -5;
		else if(point_pauvrete == 6)
			point_victoire += -7;
		else if(point_pauvrete == 7)
			point_victoire += -9;
		else if(point_pauvrete == 8)
			point_victoire += -11;
		else if(point_pauvrete == 9)
			point_victoire += -13;
		else{
			point_victoire += -15;
			if(point_pauvrete > 10){
				point_pauvrete = point_pauvrete-10;
				point_victoire += -3*point_pauvrete;
			}
		}
		//si �galit�		
		
	}


	@Override
	public int compareTo(Object joueur) {
		Joueur j = (Joueur)joueur;
		if(this.point_victoire > j.getPoint_victoire())
			return 1;
		else if(this.point_victoire < j.getPoint_victoire())
			return -1;
		else{
			if(this.point_pauvrete > j.getPoint_pauvrete())
				return 1;
			else if(this.point_pauvrete < j.getPoint_pauvrete())
				return -1;
			else{
				if(this.nbQuartiers > j.getQuartiers())
					return 1;
				else if(this.nbQuartiers < j.getQuartiers())
					return -1;
				else{
					return 0;
				}
			}
		}
	}
}