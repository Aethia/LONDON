package fr.m1miage.london.classes;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import fr.m1miage.london.GestionErreurs;
import fr.m1miage.london.Regles;

/**
 * la classe Joueur modélise un jour et tout ce qui lui appartient
 */
public class Joueur implements Serializable, Comparable {
    /**
     * L'id du joueur
     * @see Joueur#getId()
     */
	private int id;
    /**
     * Le nom du joueur
     * @see Joueur#getNom()
     */
	private String nom;
    /**La couleur du joueur
     * @see Joueur#getCouleur()
     */
	private Color couleur;
    /** Les points de peuvreté du joueur
     * @see Joueur#getPoint_pauvrete()
     */
	private int point_pauvrete;
    /** Les points de victoire du joueur
     * @see Joueur#getPoint_victoire()
     */
	private int point_victoire;
    /** L'argent dont dispose le joueur
     * @see Joueur#getArgent()
     */
	private int argent;
    /** Le montant d'argent que le joueur a emprunté
     * @see Joueur#getMontantEmprunts()
     */
	private int montantEmprunts;
    /** La zone de construction (zone ou il pose ses cartes) du joueur
     * @see Joueur#getZone_construction()
     */
	private ZoneConstruction zoneConstruction;
    /** La main du joueur (les cartes qu'il a en main)
     * @see Joueur#getMainDuJoueur()
     */
	private Main mainDuJoueur;
    /** le nombre de quartiers dont le joueur dispose
     * @see Joueur#getMainDuJoueur()
     */
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


	public int getNbQuartiers() {
		return nbQuartiers;
	}


	public void setPoint_pauvrete(int point_pauvrete) {
		this.point_pauvrete = point_pauvrete;
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
	/*
	 * Defausse
	 */
	public void seDefausser(Carte c, Etalage etalage){
		etalage.ajouterCarte(c);
		mainDuJoueur.supprimerCarteParId(c.getId_carte());
	}


	public Carte choisirCarteParId(int idCarte){
		return this.mainDuJoueur.choisirCarte(idCarte);				
	}

	/*
	 * v�rifier si le joueur peut finir son tour
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

	/**
	 * @return int : nombre de cartes dans la main du joueur
	 */
	public int getNb_cartes() {
		return this.mainDuJoueur.getNb_cartes();
	}

	/**
	 * permet d'investir un quartier
	 * @param quartier : numero du quartier que le joueur souhaite investir
	 * @param plateau
	 * @param pioche : permet au joueur de piocher les cartes necessaires apres son investissement
	 * @return GestionErreurs : message d'erreur
	 */

	public GestionErreurs invest(int quartier, Plateau plateau,Pioche pioche){

		boolean investir;
		if (quartier > 0 && quartier < 21) {

			//on verifie si le joueur � assez d'argent
			if(this.getArgent()>=plateau.getQuartier(quartier).getPrix()){
				//on verifie si on peut investir dans le quartier
				investir = plateau.getQuartier(quartier).investirQuartier(this);
				//si oui la fonction investirQuartier renvoie true et met a jour les quartiers adjacents
				if(investir==true){
					this.argent-=plateau.getQuartier(quartier).getPrix();

					//le joueur pioche le nb de cartes pr�cis� sur le quartier
					this.ajouterCartesMain(pioche.tirerNCartes(plateau.getQuartier(quartier).getNb_carte_a_piocher()));
					nbQuartiers++;
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

	/**
	 * Obtenir toutes les cartes de la même couleur que la carte donnée en paramètre dans la main du joueur
	 * @param carte : carte dont on cherche celles de la même couleur
	 * @return List<Carte> : cartes d'une même couleur
	 */
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

	/**
	 * Rechercher si la carte ets présente dans une liste de cartes
	 * @param carte : la carte que l'on cherche
	 * @param List<Carte> : la liste où l'on recherche
	 * @return presence : true si trouvé, false sinon
	 */
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

	//On construit "une carte" sur une pile donn?e, et on d?fausse une carte de la m?me couleur
	/**
	 * Permet a un joueur de construire sur sa zone 
	 * @param cPosee : carte que l'on souhaite poser
	 * @param cDefaussee : carte que l'on souhaite defausser
	 * @param indexPile : index de la pile ou l'on souhaite poser
	 * @param etalage
	 * @return
	 */
	public GestionErreurs construire(Carte cPosee, Carte cDefaussee, int indexPile, Etalage etalage){

		if(this.verifPresenceCarte(cPosee, mainDuJoueur.getLesCartes())){
			if(!cPosee.isConstructible()){
				return GestionErreurs.NON_CONSTRUCTIBLE_CARD;
			}
			if(this.verifPresenceCarte(cDefaussee, this.getCartesCouleur(cPosee))){
				if(cPosee.getPrix()<= argent){ 		
					if(indexPile-1 < this.zoneConstruction.getNbPiles() ){ //s'il n'y a pas de piles ou que le joueur choisit l'option cr?er une pile
						this.zoneConstruction.ajouterCarte(indexPile-1, cPosee); //si le joueur choisir le num?ro de la pile
					}
					else{
						this.zoneConstruction.addPile(cPosee);	
					}
					argent -= cPosee.getPrix();
					this.mainDuJoueur.supprimerCarteParId(cDefaussee.getId_carte());
					this.mainDuJoueur.supprimerCarteParId(cPosee.getId_carte());
					etalage.ajouterCarte(cDefaussee);
					return GestionErreurs.NONE;
				}
				else{
					return GestionErreurs.NOT_ENOUGH_MONEY;
				}
			}
			else if(cPosee.getId_carte()==8 || cPosee.getId_carte()==12 || cDefaussee.getId_carte() == 24){
				if(indexPile-1 < this.zoneConstruction.getNbPiles() ){ //s'il n'y a pas de piles ou que le joueur choisit l'option cr?er une pile
					this.zoneConstruction.ajouterCarte(indexPile-1, cPosee); //si le joueur choisir le num?ro de la pile
				}
				else{
					this.zoneConstruction.addPile(cPosee);	
				}
				this.mainDuJoueur.supprimerCarteParId(cPosee.getId_carte());
				return GestionErreurs.NONE;
			}
			else{
				return GestionErreurs.INCORRECT_CARTE_DEFAUSSE;

			}
		}
		else{
			return GestionErreurs.INCORRECT_CARTE;
		}
	}
	/**
	 * Méthode de tirage d'une carte dans la pioche
	 * @param pioche : la pioche où l'on essaye de tirer une carte
	 * @return GestionErreurs : message d'erreur 
	 */
	public GestionErreurs piocher(Pioche laPioche){
		if(laPioche.getNbCartes() > 0){
			Carte c = laPioche.tirerUneCarte();
			if(c!=null){
				mainDuJoueur.ajouterCarte(c);
				return GestionErreurs.NONE;
			}else{
				return GestionErreurs.PIOCHE_VIDE;
			}


		}
		else
			return GestionErreurs.NOT_ENOUGH_CARD;

	}


	/**
	 * 
	 * @param montant : montant souhaité pour l'emprunt
	 * @return GestionErreurs : message d'erreur
	 */
	public GestionErreurs emprunter(int montant){
		//On v?rifie 
		if (montant > 0 && montant % 10 == 0 && montant <=100){
			if((montantEmprunts + montant) <= 100) {
				boolean carte72=false;

				for(int j = 0; j < this.getZone_construction().getNbPiles(); j++){
					ArrayList<Carte> pile= this.getZone_construction().getCartesPile(j);
					for(Carte c: pile){
						if(!c.isDesactivee() && c.getId_carte()==72){
							carte72=true;
						}
					}
				}
				if(carte72==true){		
					this.argent += (montant+(0.2*montant));
				}
				else{
					this.argent += montant;
				}
				this.montantEmprunts += montant;

				return GestionErreurs.NONE;
			}else{
				return GestionErreurs.MAX_EMPRUNT;
			}
		} 
		return GestionErreurs.MONTANT_INCORRECT;


	}

	public GestionErreurs restaurerVille2(Carte carteAActiver, Carte cartedefausse, Etalage letalage){
		// les cartes "activables" (qui sont sur le dessus des piles)
		List<Carte> cartesDessus = zoneConstruction.getCarteDessus();
		Boolean trouve = false;
		for (Carte c : cartesDessus) {
			if (c.getId_carte() == carteAActiver.getId_carte() && c.isDesactivee() == false) {
				carteAActiver = c;
				trouve = true;		
			}
		}

		// si on a pas trouvé
		if (!trouve){
			return GestionErreurs.CARTE_NON_TROUVEE;
		}

		// on va stocker le cout d'activation de toutes ces cartes
		int coutLivres=0;

		// si la carte n'est pas asctivable
		if (carteAActiver.getCoutActivation() == null ){
			return GestionErreurs.CARTE_NON_TROUVEE;
		}

		// on regarde le cout d'activation de cette carte
		switch(carteAActiver.getCoutActivation().getTypeActiv()){
		// l'activation est gratuite
		case 0 : break;
		// l'activation ne coute que des livres
		case 1 : coutLivres = carteAActiver.getCoutActivation().getLivresAPayer();break;
		// si l'activation coute une carte
		case 2 : 
			if (cartedefausse == null )
				return GestionErreurs.CARTE_DEFAUSSE_MANQUE;
			else {
				if (!cartedefausse.getCoutActivation().getCouleurADefausser().equalsIgnoreCase(carteAActiver.getCoutActivation().getCouleurADefausser()))
					return GestionErreurs.CARTE_DEFAUSSE_COULEUR;
				break;
			}
			// l'activation coute une carte, peu importe la couleur
		case 3 : if (cartedefausse == null ) 
			return GestionErreurs.CARTE_DEFAUSSE_MANQUE;
		break;		
		}

		// si le joueur n'a pas assez d'argent
		if (this.argent < coutLivres) {
			return GestionErreurs.NOT_ENOUGH_MONEY;
		}else{
			this.argent -= coutLivres;
		}

		// on donne l'argent d'activation
		this.argent += carteAActiver.getArgentActivation();
		// on donne les pts de victoire d'activation
		this.point_victoire += carteAActiver.getPtsVictActivation();
		// on donne les pts de pauvreté
		this.point_pauvrete += carteAActiver.getPtsPauvreteGagnes();
		this.point_pauvrete -= carteAActiver.getPtsPauvretePerdus();
		if (this.point_pauvrete < 0)
			this.point_pauvrete = 0;

		// on supprime la carte qui a été jouée dans la main
		this.mainDuJoueur.supprimerCarteParId(carteAActiver.getId_carte());

		// on place la carte défaussée dans l'étalage s'il y en a une
		if(cartedefausse!=null){
			this.mainDuJoueur.supprimerCarteParId(cartedefausse.getId_carte());
			letalage.ajouterCarte(cartedefausse);
		}
		//si la carte etait a retourner, on la retourne
		if(carteAActiver.getCoutActivation().isaRetourner()){

			carteAActiver.setDesactivee(true);
		}


		return GestionErreurs.NONE;
	}

	/*
	 * m�thode pour la restauration de la ville
	 * code retour : 
	 * -1 : cartes non trouv�es
	 * 1 : pas de pb
	 */
	public GestionErreurs restaurerVille(List<Integer> idCartes){
		/*
		 * V�rifier si les cartes existent
		 */
		// les cartes "activables" (qui sont sur le dessus des piles)
		List<Carte> cartesDessus = zoneConstruction.getCarteDessus();
		List<Carte> cartesAActiver = new ArrayList<Carte>();
		Boolean trouve = false;
		// pour tous les id de cartes qu'on veut activer
		for(int i = 0;i<idCartes.size();i++){
			for (Carte c : cartesDessus) {
				// si on a trouv� la carte et qu'on peut l'activer
				if (c.getId_carte() == idCartes.get(i) && c.isDesactivee() == false) {
					trouve = true;		
					cartesAActiver.add(c);
				}

			}
			// si on a pas trouv� cette carte, on sort
			if (!trouve)
				return GestionErreurs.INCORRECT_NUMBER;
			// on remet le flag � faux pour la carte suivante
			trouve = false;	
		}
		/*
		 * on creer un objet avec les ressources necessaires 
		 * pour informer le joueur sur ce que l'on a besoin
		 */
		// pour chaque carte que l'on veut activer
		TraderClassRestaurerVille.reset();
		for(Carte c : cartesAActiver){
			// si la carte n'est pas activable
			if (c.getCoutActivation() == null){
				return GestionErreurs.NON_ACTIVABLE_CARD;
			}
			switch (c.getCoutActivation().getTypeActiv()){
			case 0 : break;
			case 1 : TraderClassRestaurerVille.addCoutEnLivres(c.getCoutActivation().getLivresAPayer());break;
			case 2 : {
				switch (c.getCoutActivation().getCouleurADefausser()){
				case "Brun" : TraderClassRestaurerVille.addNbCartesBrunes();break;
				case "Bleu" : TraderClassRestaurerVille.addNbCartesBleues();break;
				case "Gris" : TraderClassRestaurerVille.addNbCartesGrises();break;
				case "Rose" : TraderClassRestaurerVille.addNbCartesRoses();break;			
				}

				break;
			}
			case 3 : TraderClassRestaurerVille.addNbCartesOsefCouleur();break;
			}
		}
		return GestionErreurs.NONE;
	}

	public void aActive(Carte carte){
		this.argent = this.argent + carte.getArgentActivation();
		this.point_pauvrete= this.point_pauvrete + carte.getPtsPauvreteGagnes();
		this.point_pauvrete= this.point_pauvrete - carte.getPtsPauvretePerdus();
		this.point_victoire= this.point_victoire + carte.getPointsVictoire();

	}

	/*
	 * m�thode pour payer la restauration de la ville
	 * -1 : manque d'argent
	 * -2 : manque carte rose
	 * -3 : manque carte bleue
	 * -4 : manque carte grise
	 * -5 : manque carte brun
	 * -6 : carte non trouv�e
	 */
	public GestionErreurs payerRestaurationVille(List<Carte> cartesADefausser){
		int roseADefausser = 0,bleuADefausser = 0,grisADefausser = 0,brunADefausser = 0;
		int rose = 0,bleu = 0,gris = 0,brun = 0;
		// on compte le nb de carte de chaque couleur que l'on veut d�fausser
		for(Carte c : cartesADefausser){
			switch (c.getCouleur()){
			case "Rose" : roseADefausser++;break;
			case "Bleu" : bleuADefausser++;break;
			case "Gris" : grisADefausser++;break;
			case "Brun" : brunADefausser++;break;		
			}
		}
		// on compte le nb de carte de chaque couleur de la main
		for(Carte c : this.mainDuJoueur.getLesCartes()){
			switch (c.getCouleur()){
			case "Rose" : rose++;break;
			case "Bleu" : bleu++;break;
			case "Gris" : gris++;break;
			case "Brun" : brun++;break;		
			}
		}

		// test pour voir si on manque de ressources
		//System.err.println( "argent du joueur :" + this.argent);
		//System.err.println( "truc :" + TraderClassRestaurerVille.getCoutEnLivres());
		if (this.argent - TraderClassRestaurerVille.getCoutEnLivres() < 0)
			return GestionErreurs.NOT_ENOUGH_MONEY;
		if (rose-roseADefausser < 0){
			return GestionErreurs.NO_ROSE_CARD;
		}
		if (bleu-bleuADefausser < 0){
			return GestionErreurs.NO_BLEU_CARD;
		}
		if (gris-grisADefausser < 0){
			return GestionErreurs.NO_GRIS_CARD;
		}
		if (brun-brunADefausser < 0){
			return GestionErreurs.NO_BRUN_CARD;
		}
		for(Carte c : cartesADefausser){
			if (!this.mainDuJoueur.supprimerCarteParId(c.getId_carte())){
				return GestionErreurs.INCORRECT_CARTE;
			}
		}

		return GestionErreurs.NONE;
	}


	/**
	 * Méthode de comparaison entre deux joueurs
	 * @param Joueur : le joueur à comparer
	 * @return int : -1 si joueur A > joueur B, 0 si joueur A = joueur B, 1 si joueur A < joueur B
	 */
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

	/**
	 * Méthode de jeu d'une carte
	 * @param Joueur : le joueur qui veut jouer une carte
	 * @param Carte : la carte à jouer
	 * @param Pioche : la pioche dans laquelle est la carte
	 */
	public void jouerCarte(Joueur j, Carte c, Pioche pioche){

		if(c.getEffet_passif()!= null){
			int idEffet = c.getEffet_passif().getIdEffet();
			switch(idEffet){
			case 5:
				c.getEffet_passif().prendreDeuxCartes(pioche, j);
				j.getMainDuJoueur().supprimerCarteParId(c.getId_carte());
				break;
			}

		}
	}
}
