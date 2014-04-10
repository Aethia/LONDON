package fr.m1miage.london.classes;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import fr.m1miage.london.Regles;

public class Joueur {
	private int id;
	private String nom;
	private Color couleur;
	// les points de pauvret√© du joueur
	private int point_pauvrete;
	// les pts de victoire du joueur
	private int point_victoire;
	// l'argent dont il dispose
	private int argent;
	
	private int montantEmprunts;
	// les zones de construction qu'il peut poss√©der
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
	 * v√©rifier si le joueur peut finir son tour
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

	
	public String invest(int quartier, Plateau plateau,Pioche pioche){
		
		boolean investir;
		String msg;

			if (quartier > 0 && quartier < 21) {
				
				//on verifie si le joueur √† assez d'argent
				if(this.getArgent()>=plateau.getQuartier(quartier).getPrix()){
					
					//on verifie si on peut investir dans le quartier
					investir = plateau.getQuartier(quartier).investirQuartier(this);
					
					//si oui la fonction investirQuartier renvoie true et met a jour les quartiers adjacents
					if(investir==true){
						this.argent-=plateau.getQuartier(quartier).getPrix();
						
						//le joueur pioche le nb de cartes pr√©cis√© sur le quartier
						this.ajouterCartesMain(pioche.tirerNCartes(plateau.getQuartier(quartier).getNb_carte_a_piocher()));
						
						msg="Vous venez d'investir dans le quartier : " + plateau.getQuartier(quartier).getNom() + " !\n";
						return msg;
					}	
					else{
						msg="pas assez d'argent";
						return msg;
					}	
				}	
				else
					msg="D√©sol√© vous ne pouvez pas investir dans ce quartier !";
					return msg;
			}
			else
				msg="Numero de quartier incorrect";
				return msg;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	//affiche les cartes de la mÍme couleur qu'une carte choisie
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
	
	//on vÈrifie que la carte choisie par la joueur existe dans sa main
		public boolean verifPresenceCarte(Carte carte, List<Carte> liste){
			boolean presence=false;
			for(Carte c:liste){
				if(c.getId_carte() == carte.getId_carte()){
					presence = true;
				}
			}
			
			return presence;
		}
	
	//On construit "une carte" sur une pile donnÈe, et on dÈfausse une carte de la mÍme couleur
	public String construire(Carte cPosee, Carte cDefaussee, int indexPile){
		if(this.verifPresenceCarte(cPosee, mainDuJoueur.getLesCartes())){
			if(this.verifPresenceCarte(cDefaussee, this.getCartesCouleur(cPosee))){
				if(cPosee.getPrix()<= argent){ 		
					if(this.zoneConstruction.getNbPiles()==0 || indexPile == 0){ //s'il n'y a pas de piles ou que le joueur choisit l'option crÈer une pile
						this.zoneConstruction.addPile(cPosee);	
					}
					else{
						this.zoneConstruction.ajouterCarte(indexPile-1, cPosee); //si le joueur choisir le numÈro de la pile
					}			
					argent -= cPosee.getPrix();
					this.mainDuJoueur.supprimerCarteParId(cDefaussee.getId_carte());
					this.mainDuJoueur.supprimerCarteParId(cPosee.getId_carte());
					Plateau.etalage.ajouterCarte(cDefaussee);
					return "Construction terminÈe !";
				}
				else{
					return "Argent insuffisant";
				}
			}
			else{
				return "Carte ‡ dÈfausser n'existe pas";
				
			}
		}
		else{
			return "Carte ‡ poser n'existe pas";
		}
	}
	
	public void piocher(Pioche laPioche){
		mainDuJoueur.ajouterCarte(laPioche.tirerUneCarte());
	}


	/*
	 * Emprunter de l'argent
	 */
	public String emprunter(int Montant){
		String msg ="";
		
		//On vÈrifie 
		if (Montant > 0 && Montant % 10 == 0 && (montantEmprunts + Montant) <= 100) {
			this.argent += Montant;
			this.montantEmprunts += Montant;
			
			msg ="Vous venez d'emprunter £" + Montant + ". \n";
			//System.out.println("Vous venez d'emprunter £" + Montant + ". \n");
		} 
		else{
			msg ="Montant incorrect \n";
			//System.err.println("Montant incorrect \n");
		}
		return msg;

	}
	
	/*
	 * mÈthode pour la restauration de la ville
	 * code retour : 
	 * -2 : cartes non trouvÈes
	 * 1 : pas de pb
	 */
	public int restaurerVille(List<Integer> idCartes){
		/*
		 * VÈrifier si les cartes existent
		 */
		// les cartes "activables" (qui sont sur le dessus des piles)
		List<Carte> cartesDessus = zoneConstruction.getCarteDessus();
		List<Carte> cartesAActiver = new ArrayList<Carte>();
		Boolean trouve = false;
		// pour tous les id de cartes qu'on veut activer
		for(int i = 0;i<idCartes.size();i++){
			for (Carte c : cartesDessus) {
				// si on a trouvÈ la carte et qu'on peut l'activer
				if (c.getId_carte() == idCartes.get(i) && c.isDesactivee() == false) {
					trouve = true;		
					cartesAActiver.add(c);
				}
						
			}
			// si on a pas trouvÈ cette carte, on sort
			if (!trouve)
				return -1;
			// on remet le flag ‡ faux pour la carte suivante
			trouve = false;	
		}
		/*
		 * on creer un objet avec les ressources necessaires 
		 * pour informer le joueur sur ce que l'on a besoin
		 */
		// pour chaque carte que l'on veut activer
		TraderClassRestaurerVille.reset();
		for(Carte c : cartesAActiver){
			switch (c.coutActivation().getTypeActiv()){
			case 0 : break;
			case 1 : TraderClassRestaurerVille.addCoutEnLivres(c.coutActivation().getLivresAPayer());break;
			case 2 : {
				switch (c.coutActivation().getCouleurADefausser()){
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
		return 1;
	}
}
