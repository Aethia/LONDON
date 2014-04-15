package fr.m1miage.london;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

import fr.m1miage.london.classes.Carte;
import fr.m1miage.london.classes.Etalage;
import fr.m1miage.london.classes.Joueur;
import fr.m1miage.london.classes.Pioche;
import fr.m1miage.london.classes.Plateau;
import fr.m1miage.london.classes.TraderClassRestaurerVille;
import fr.m1miage.london.classes.Quartier;


public class Partie implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2213991896715924627L;
	private List<Joueur> listeJoueurs = new ArrayList<Joueur>();
	private int nbJoueurs = 0;
	private Plateau plateau;
	private Pioche pioche;
	private static transient Scanner sc = new Scanner(System.in);

	private final Color rouge = Color.red;
	private final Color vert = Color.green;
	private final Color jaune = Color.yellow;
	private final Color bleu = Color.blue;
	private boolean actionEffectuee;
	// le joueur actuellement actif
	private int joueurActif=0;
	private Joueur jActif;
	private int actionChoisie =0; //1 construire, 2 restaurer, 3 investir, 4 piocher
	private boolean tourTermine=false;

	private int typeGUI=0; // par defaut : 0 => console, 1 = Graphique

	private GestionErreurs erreur;

	public Partie(){
		this.plateau = new Plateau();
		this.pioche = new Pioche();
	}


	public Partie(List<Joueur> listeJ, int nbJ){
		this.plateau = new Plateau();
		this.pioche = new Pioche();
		this.listeJoueurs = listeJ;
		this.nbJoueurs = nbJ;
		this.typeGUI = 1;
	}

	private void creerJoueurs(){
		boolean condition = false;
		while(condition == false){
			System.out.println("Entrez le nombre de joueurs : ");
			if(sc.hasNextInt()){
				int reponse=sc.nextInt();
				if(reponse <= Regles.NBMAXJOUEURS){
					nbJoueurs = reponse;	
					condition = true;
				}
				else{
					System.out.println("Valeur incorrecte. Il ne peut y avoir que de 2 à 5 joueurs.");
				}
			}
			else{
				System.out.println("Valeur incorrecte.");
				sc.next();
			}
		}
		//	Color couleur = rouge;
		for(int i = 0; i<nbJoueurs; i++){
			System.out.println("Entrez le nom du joueur "+(i+1)+" : ");
			String nomJoueur = sc.next();
			System.out.println("Choisissez votre couleur : \n 1.Rouge \n 2.Vert \n 3.Jaune \n 4.Bleu");
			Color couleur = null;// = rouge;

			//tant que l'utilisateur n'a pas choisit de couleur
			while(couleur == null){
				//On verifie si l'entree clavier est correcte
				switch (sc.nextInt()) {
				case 1:
					couleur = rouge;
					break;
				case 2:
					couleur = vert;
					break;
				case 3:
					couleur = jaune;
					break;
				case 4:
					couleur = bleu;
					break;
					//si l'utilisateur n'entre pas une couleur correspondante
				default:
					System.err.println("Valeur incorrecte \n");
					System.out.println("Veuillez s�lectionner une des couleurs suivantes : \n 1.Rouge \n 2.Vert \n 3.Jaune \n 4.Bleu");
					break;					
				}				
				
			}
			Joueur joueur = new Joueur(i, nomJoueur, couleur);
			listeJoueurs.add(joueur);
		}

	}


	public void init(){
		if(typeGUI==0){
			creerJoueurs();
		}

		plateau.init();
		plateau.getEtalage().setTailleEtalage(nbJoueurs+1);
		pioche.init();

		// on initialise le premier joueur 
		joueurActif = (int) (0 + (Math.random() * (nbJoueurs - 0)));
		jActif = listeJoueurs.get(joueurActif);

		// on distribue les cartes (los cartos en espagnol)
		for (Joueur i : listeJoueurs) {
			i.ajouterCartesMain(pioche.tirerNCartes(Regles.NBCARTESDEPART));
		}

		//le premier joueur pioche une carte
		jActif.piocher(pioche);
	}

	// faire passer le joueur actif au joueur suivant
	public void joueurSuivant() {
		if (joueurActif + 1 >= nbJoueurs) {
			joueurActif = 0;
		} else {
			joueurActif++;
		}
		jActif = listeJoueurs.get(joueurActif);
		jActif.piocher(pioche);
		actionChoisie=0;
		tourTermine= false;
	}


	// la boucle de jeu
	public void lancerJeu() {
		Boolean actionEffectuee = false;

		//repr�sente les tours � jouer apr�s �puissement des cartes
		int decompte_final = nbJoueurs - 1;
		
		listeJoueurs.get(joueurActif).piocher(pioche);
		// on joue tant qu'il y a des cartes dans la pioche

		while ((pioche.getNbCartes() + decompte_final) > 0){
			// le joueur actif doit choisir une action
			System.out.println("C'est au tour de "
					+ listeJoueurs.get(joueurActif).getNom()
					+ ", que faites vous ?");
			if (!actionEffectuee) {
				System.out.println(" -- Les Actions --");
				System.out.println("1. Jouer une carte (poser une carte devant soi)");
				System.out.println("2. Restaurer la ville (activer des cartes)");
				System.out.println("3. Investir (acheter un quartier)");
				System.out.println("4. Piocher 3 cartes");
				System.out.println(" -- Autre --");
			}
			System.out.println("5. Contracter un pret");			System.out.println("6. Consulter mes cartes en main");
			System.out.println("7. Consulter l'étalage de cartes");
			System.out.println("8. Finir mon tour");
			System.out.println("9. Sauvegarder la partie");

			if(sc.hasNextInt()){
				// switch de l'action
				switch(sc.nextInt()){
				case 1: {
					// si une action a déjà été faite dans le tour
					if (actionEffectuee) {
						System.err.println("Vous avez déjà effectué une action pour ce tour!");
						break;
					}
					jouerCarte();
					actionEffectuee = true;
					break;
				}

				case 2: {
					// si une action a déjà été faite dans le tour
					if (actionEffectuee) {
						System.err.println("Vous avez déjà effectué une action pour ce tour!");
						break;
					}
					if (restaurerVille())
						actionEffectuee = true;
					break;
				}

				case 3: {
					// si une action a déjà été faite dans le tour
					if (actionEffectuee) {
						System.err.println("Vous avez déjà effectué une action pour ce tour!");
						break;
					}
					investir();
					actionEffectuee = true;
					break;
				}

				case 4: {
					if (actionEffectuee) {
						System.err.println("Vous avez deja effectue une action pour ce tour! \n");
						break;
					}
					piocherCartes();
					actionEffectuee = true;
					break;
				}

				case 5: {
					//permet d'emprunter de l'argent
					contracterPret();
					break;
				}
				case 6: {
					consulterMain();
					break;
				}
				case 7: {
					consulterEtalage();
					break;
				}
				case 8: {
					System.out.println("Vous voulez finir votre tour");
					if (!actionEffectuee) {
						System.err
						.println("Vous ne pouvez pas finir votre tour sans faire d'action! \n");
						break;
					}
					if(pioche.getNbCartes() == 0)
						decompte_final--;
					// passe au suivant
					joueurSuivant();
					listeJoueurs.get(joueurActif).piocher(pioche);
					actionEffectuee = false;
					break;
				}
				case 9:{
					if(actionEffectuee == true){
						joueurSuivant();
						listeJoueurs.get(joueurActif).piocher(pioche);
						actionEffectuee = false;	
					}
					try {
						sauvegarder();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

				default: {
					System.out.println("Je n'ai pas compris votre choix \n");
					break;
				}
				}

			}

			else{
				System.out.println("Je n'ai pas compris votre choix");
				sc.next();
			}
		}
		calculPointsVictoire();
		calculGagnant();		
		
	}

	private void piocherCartes() {
		System.out.println("Vous voulez piocher 3 cartes");
	}

	private void investir() {
		boolean tourValide= false;
		while(!tourValide){
			System.out.println(plateau.getQuartiersDispo());
			System.out.println("Dans quel quartier souhaitez vous investir ? (indiquer son numero)");

			int quartier=0;
			if(sc.hasNextInt()){
				quartier = sc.nextInt();

				erreur=listeJoueurs.get(joueurActif).invest(quartier, plateau, pioche);
				if(erreur.equals(GestionErreurs.NONE)){
					tourValide=true;
				}
				erreur.getMsgError();
			}
			//passe au prochaine scanner (le pr�c�dent n'�tant pas un int)
			else{
				GestionErreurs.INCORRECT_NUMBER.getMsgError();
				sc.next();
			}
		}
		//passe au prochaine scanner (le precedent n'�tant pas un int)
	}
	/*
	 * action restaurer la ville
	 * algo :
	 * le joueur choisit les cartes 
	 * on regarde si les cartes sont activables et on stock le cout d'activation de ces cartes
	 * on demande confirmation au joueur pour l'activation
	 * on active
	 */
	private Boolean restaurerVille() {
		String args;
		String[] lesValeurs;
		List<Integer> listVal = new ArrayList<Integer>();
		/*
		 * Choix des cartes � activer
		 */		System.out.println("Votre zone de construction : \n"+listeJoueurs.get(joueurActif).getZone_construction().toString());
		System.out.println("Quelle(s) carte(s) activer ?");
		args = sc.next();
		lesValeurs = args.split(" ");
		for(String val : lesValeurs) {
			try {
			int tmp = Integer.parseInt(val);
			listVal.add(tmp);
			}
			catch (NumberFormatException e){
				System.err.println("Les arguments doivent �tre les id des cartes!");
				return false;
			}
		}
		// on regarde s'il est possible de restaurer la ville avec les cartes s�lectionn�es
		if (listeJoueurs.get(joueurActif).restaurerVille(listVal) < 0) {
			System.err.println("impossible d'activer ces cartes.");
			return false;
		}
		/*
		 * Affichage du cout de l'activation
		 */
		System.out.println("Pour pouvoir activer ces cartes vous avez besoin de :");
		System.out.println("- "+TraderClassRestaurerVille.getCoutEnLivres()+" Livres");
		Boolean carte = false;
		if (TraderClassRestaurerVille.getNbCartesBleues() != 0) {
			System.out.println("- "+TraderClassRestaurerVille.getNbCartesBleues()+" Carte de couleur bleue");
			carte = true;
		}
		if (TraderClassRestaurerVille.getNbCartesRoses() != 0) {
			System.out.println("- "+TraderClassRestaurerVille.getNbCartesRoses()+" Carte de couleur rose");
			carte = true;
		}
		if (TraderClassRestaurerVille.getNbCartesBrunes() != 0) {
			System.out.println("- "+TraderClassRestaurerVille.getNbCartesBrunes()+" Carte de couleur brun");
			carte = true;
		}
		if (TraderClassRestaurerVille.getNbCartesGrises() != 0) {
			System.out.println("- "+TraderClassRestaurerVille.getNbCartesGrises()+" Carte de couleur gris");
			carte = true;
		}
		if (TraderClassRestaurerVille.getNbCartesOsefCouleur() != 0) {
			System.out.println("- "+TraderClassRestaurerVille.getNbCartesOsefCouleur()+" Carte de n'importe quelle couleur");
			carte = true;
		}
		List<Carte> listeCartes = new ArrayList<Carte>();
			if (carte){
				System.out.println("Choisissez les cartes � d�fausser dans votre main :");
				System.out.println(listeJoueurs.get(joueurActif).getMainDuJoueur().toString());
				args = sc.next();
				lesValeurs = args.split(" ");
				for(String val : lesValeurs) {
					try {
					int tmp = Integer.parseInt(val);
					listeCartes.add(listeJoueurs.get(joueurActif).getMainDuJoueur().choisirCarte(tmp));
					}
					catch (NumberFormatException e){
						System.err.println("Les arguments doivent �tre les id des cartes!");
						return false;
					}
				}
			}
			/*
			 * activation des cartes
			 */
		
			System.out.println("Voulez vous vraiment payer cette somme et restaurer la ville ? oui/non");
			String ret = sc.next();
			if (ret.equalsIgnoreCase("oui")) {
				// tente de payer la restauration de la ville
				listeJoueurs.get(joueurActif).payerRestaurationVille(listeCartes);
				// on retourne les cartes que l'on souhaite activer
				for(int idCarte : listVal){
					listeJoueurs.get(joueurActif).getZone_construction().retournerCarte(idCarte);
				}
				// todo m�thode de joueur pour payer la somme et retourner les cartes
				System.out.println("Cartes activees !");
			}

		 return true;

	}

	private void jouerCarte() {
		int finConstruction=1;
		System.out.println(Plateau.etalage.toString());
		while(finConstruction == 1){
			System.out.println(listeJoueurs.get(joueurActif).getArgent());
			listeJoueurs.get(joueurActif).afficherMain();
			System.out.println("Choisissez la carte � poser dans la zone de construction : ");
			int idCarte=(Integer.parseInt(sc.next()));
			Carte cPosee = listeJoueurs.get(joueurActif).choisirCarteParId(idCarte);
			List<Carte> lDefausse = listeJoueurs.get(joueurActif).getCartesCouleur(cPosee);
			if(lDefausse.size()==0){
				GestionErreurs.DEFAUSSE_INDISPO.getMsgError();
			}else{
				System.out.println("Quelle carte de m�me couleur voulez-vous d�fausser ?");
				System.out.println(listeJoueurs.get(joueurActif).getCartesCouleur(cPosee).toString());
				int idCarteDefausse=Integer.parseInt(sc.next());
				Carte cDefausse = listeJoueurs.get(joueurActif).choisirCarteParId(idCarteDefausse);
				listeJoueurs.get(joueurActif).getZone_construction().afficherCarteDessus();
				System.out.println("Choisir une pile ou en cr�er une nouvelle (0):");
				int indexPile=Integer.parseInt(sc.next());	
				erreur = listeJoueurs.get(joueurActif).construire(cPosee, cDefausse, indexPile);
				if(erreur.equals(GestionErreurs.NONE)){
					System.out.println(listeJoueurs.get(joueurActif).getZone_construction().getNbPiles());

					System.out.println(listeJoueurs.get(joueurActif).getArgent());
					listeJoueurs.get(joueurActif).afficherMain();
					System.out.println(Plateau.etalage.toString());

					System.out.println("1. Rejouer une carte \n 2. Finir les constructions");
					if(sc.hasNextInt()){
						finConstruction = sc.nextInt();
					}
				}
				erreur.getMsgError();

			}


		}
	}

	private void consulterEtalage() {
		
		System.out.println("Vous voulez consulter l'étalage de cartes");
		System.out.println(Plateau.etalage.toString());
	}

	private void consulterMain() {
		listeJoueurs.get(joueurActif).afficherMain();
		System.out.println("Vous voulez consulter vos cartes en main");
		jActif.afficherMain();

	}

	//Permet d'emprunter de l'argent (ne compte pas comme une action)
	private void contracterPret() {
		int Montant;
		//les verifications sur le scanner se font dans la classe Joueur		int Montant = 0;
		System.out.println("Quel montant souhaitez-vous emprunter? (Doit etre un entier multiple de 10)");		System.out.println("Le remboursement se fera en fin de partie au taux de 1.5%");
		//On verifie si l'entree clavier est correct (int)
		if(sc.hasNextInt()){
			Montant = sc.nextInt();
			erreur = listeJoueurs.get(joueurActif).emprunter(Montant);
			erreur.getMsgError();
		}
		else{
			System.err.println("La montant doit etre un entier multiple de 10 \n");
			sc.next();
		}
	}

	public void calculPointsVictoire(){
		for(int i=0; i < nbJoueurs; i++){
			
			int nb_emprunt = 0;
			//remboursement de l'emprunt (£15 pour £10 empruntées)
			if(listeJoueurs.get(i).getMontantEmprunts() > 0){
				int MontantEmprunt = (int)(listeJoueurs.get(i).getMontantEmprunts()* 1.5);
				
				nb_emprunt = MontantEmprunt/15;
				
				//nb_packet_argent est le nombre de packet de £15 que le joueur possède
				int nb_packet_argent = listeJoueurs.get(i).getArgent()/15;
				
				if(nb_emprunt >= nb_packet_argent){
					listeJoueurs.get(i).setAddArgent(-15*nb_packet_argent);
					nb_emprunt = nb_emprunt - nb_packet_argent;
				}
				else{
					listeJoueurs.get(i).setAddArgent(-15*nb_emprunt);
					nb_emprunt = 0;
				}
			}
			//un point de pauvreté en plus par carte en main
			listeJoueurs.get(i).setAddPoint_pauvrete(listeJoueurs.get(i).getMainDuJoueur().getNb_cartes());
			
			//un point de victoire pour £3
			listeJoueurs.get(i).setAddPoint_victoire(listeJoueurs.get(i).getArgent() / 3);
			
			//quartier + métro
			Map<Integer,Quartier> quartiers = plateau.getQuartiers();
			
			for(Integer key : quartiers.keySet()){
				if( (quartiers.get(key).getProprietaireQuartier() == listeJoueurs.get(i)) && quartiers.get(key).isMetro_pose() ) {
					listeJoueurs.get(i).setAddPoint_victoire(quartiers.get(key).getPoint_victoire() + 2);
					listeJoueurs.get(i).setAddQuartiers();
				}
				else if(quartiers.get(key).getProprietaireQuartier() == listeJoueurs.get(i)){
					listeJoueurs.get(i).setAddPoint_victoire(quartiers.get(key).getPoint_victoire());
					listeJoueurs.get(i).setAddQuartiers();
				}
				
			}

			//carte zone de construction
			for(int j = 0; j < listeJoueurs.get(i).getZone_construction().getNbPiles(); j++){
				ArrayList<Carte> pile= listeJoueurs.get(i).getZone_construction().getCartesPile(j);
				for(Carte c: pile){
					listeJoueurs.get(i).setAddPoint_victoire(c.getPointsVictoire());
				}
			}

			//-7 points de victoire par emprunt non remboursé
			listeJoueurs.get(i).setAddPoint_victoire(-7*nb_emprunt);
			
			
		}
	}
	
	public void calculGagnant(){
		
		//parcours points de pauvreté pour savoir qui a le moins, on supprime ce nombre pour tout le monde ce nombre
		int min = 999;
		for(int i = 0; i < nbJoueurs; i++){
			if(listeJoueurs.get(i).getPoint_pauvrete() < min)
				min = listeJoueurs.get(i).getPoint_pauvrete();
		}
		
		
		for(int i = 0; i < nbJoueurs; i++){
			
			listeJoueurs.get(i).setAddPoint_pauvrete(-min);
			
			//Penalité des points de pauvreté
			switch (listeJoueurs.get(i).getPoint_pauvrete()) {
			case 1:
			case 2:
				listeJoueurs.get(i).setAddPoint_victoire(-1);
				break;
			
			case 3:
				listeJoueurs.get(i).setAddPoint_victoire(-2);
				break;
	
			case 4:
				listeJoueurs.get(i).setAddPoint_victoire(-3);
				break;
	
			case 5:
				listeJoueurs.get(i).setAddPoint_victoire(-5);
				break;
	
			case 6:
				listeJoueurs.get(i).setAddPoint_victoire(-7);
				break;
	
			case 7:
				listeJoueurs.get(i).setAddPoint_victoire(-9);
				break;
	
			case 8:
				listeJoueurs.get(i).setAddPoint_victoire(-11);
				break;
	
			case 9:
				listeJoueurs.get(i).setAddPoint_victoire(-13);
				break;
	
			default:
				if(listeJoueurs.get(i).getPoint_pauvrete() >= 10){
					listeJoueurs.get(i).setAddPoint_victoire(-15);
					if(listeJoueurs.get(i).getPoint_pauvrete() > 10){
						listeJoueurs.get(i).setAddPoint_pauvrete(-10);
						listeJoueurs.get(i).setAddArgent(-3 * listeJoueurs.get(i).getPoint_pauvrete());
					}
				}
				break;
			}
		}
		
		//si égalité, on tri selon différents critères
		java.util.Collections.sort(listeJoueurs);
		
		for(Joueur j : listeJoueurs){
			System.out.println(j.toString());
		}
	}
	public List<Joueur> getListeJoueurs() {
		return listeJoueurs;
	}

	public void setListeJoueurs(List<Joueur> listeJoueurs) {
		this.listeJoueurs = listeJoueurs;
	}

	public Plateau getPlateau() {
		return plateau;
	}

	public void setPlateau(Plateau plateau) {
		this.plateau = plateau;
	}

	public Pioche getPioche() {
		return pioche;
	}

	public void setPioche(Pioche pioche) {
		this.pioche = pioche;
	}

	public int getJoueurActif() {
		return joueurActif;
	}

	public Joueur getObjJoueurActif(){
		return jActif;
	}

	public void setJoueurActif(int joueurActif) {
		this.joueurActif = joueurActif;
	}

	public boolean isTourTermine() {
		return tourTermine;
	}

	public void setTourTermine(boolean tourTermine) {
		this.tourTermine = tourTermine;
	}

	public int getActionChoisie() {
		return actionChoisie;
	}


	public void setActionChoisie(int actionChoisie) {
		this.actionChoisie = actionChoisie;
	}




	public void sauvegarder() throws IOException{
		FileOutputStream out = new FileOutputStream("save.txt");
		ObjectOutputStream s = new ObjectOutputStream(out);
		s.writeObject(this);
		s.writeObject(this.plateau.getEtalage());
		s.writeObject(listeJoueurs);
		for(Joueur i:listeJoueurs){
			s.writeObject(i.getMainDuJoueur());
		}
		System.out.println("Objet s�rialis�");
		s.close();
	}

	public void chargerPartie() throws IOException, ClassNotFoundException{
		FileInputStream in=new FileInputStream("save.txt");
		ObjectInputStream s = new ObjectInputStream(in);

		Partie p = (Partie) s.readObject();

		Etalage etalage = (Etalage)s.readObject();
		p.getPlateau().setEtalage(etalage);
		System.out.println("Partie charg�e");
		s.close();
		this.listeJoueurs=p.listeJoueurs;
		this.nbJoueurs=p.nbJoueurs;
		this.pioche = p.pioche;
		this.plateau = p.plateau;
		this.joueurActif=p.joueurActif;
		this.actionEffectuee=p.actionEffectuee;

	}
}
