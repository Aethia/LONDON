package fr.m1miage.london;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import fr.m1miage.london.classes.Carte;
import fr.m1miage.london.classes.Joueur;
import fr.m1miage.london.classes.Pioche;
import fr.m1miage.london.classes.Plateau;

public class Partie {
	private List<Joueur> listeJoueurs = new ArrayList<Joueur>();
	private int nbJoueurs = 0;
	private Plateau plateau;
	private Pioche pioche;
	private Scanner sc = new Scanner(System.in);

	private final Color rouge = Color.red;
	private final Color vert = Color.green;
	private final Color jaune = Color.yellow;
	private final Color bleu = Color.blue;

	// le joueur actuellement actif
	private int joueurActif=0;
	private Joueur jActif;
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
				//On vérifie si l'entrée clavier est correcte
				if(sc.hasNextInt()){
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
						System.out
						.println("Veuillez sélectionner une des couleurs suivantes : \n 1.Rouge \n 2.Vert \n 3.Jaune \n 4.Bleu");
						break;
					}
				}

				//entrée clavier incorrect, on passe à la suivante
				else{
					System.err.println("Valeur incorrecte \n");
					System.out
					.println("Veuillez sélectionner une des couleurs suivantes : \n 1.Rouge \n 2.Vert \n 3.Jaune \n 4.Bleu");
					sc.next();
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
		tourTermine= false;
	}


	// la boucle de jeu
	public void lancerJeu() {
		Boolean actionEffectuee = false;
		// on joue tant qu'il y a des cartes dans la pioche
		while (pioche.getNbCartes() > 0){
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
			System.out.println("5. Contracter un prêt");
			System.out.println("6. Consulter mes cartes en main");
			System.out.println("7. Consulter l'étalage de cartes");
			System.out.println("8. Finir mon tour");

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
					restaurerVille();
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
					// si une action a déjà été faite dans le tour
					if (actionEffectuee) {
						System.err.println("Vous avez déjà effectué une action pour ce tour! \n");
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
					// passe au suivant
					joueurSuivant();
					listeJoueurs.get(joueurActif).piocher(pioche);
					actionEffectuee = false;
					break;
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
			//passe au prochaine scanner (le précédent n'étant pas un int)
			else{
				GestionErreurs.INCORRECT_NUMBER.getMsgError();
				sc.next();
			}
		}

	}



	private void restaurerVille() {
		System.out.println("Votre zone de construction : \n"+listeJoueurs.get(joueurActif).getZone_construction().toString());
		System.out.println("Quelle(s) carte(s) activer ?");
	}

	private void jouerCarte() {
		int finConstruction=1;
		System.out.println(Plateau.etalage.toString());
		while(finConstruction == 1){
			System.out.println(listeJoueurs.get(joueurActif).getArgent());
			listeJoueurs.get(joueurActif).afficherMain();
			System.out.println("Choisissez la carte à poser dans la zone de construction : ");
			int idCarte=(Integer.parseInt(sc.next()));
			Carte cPosee = listeJoueurs.get(joueurActif).choisirCarteParId(idCarte);
			List<Carte> lDefausse = listeJoueurs.get(joueurActif).getCartesCouleur(cPosee);
			if(lDefausse.size()==0){
				GestionErreurs.DEFAUSSE_INDISPO.getMsgError();
			}else{
				System.out.println("Quelle carte de même couleur voulez-vous défausser ?");
				System.out.println(listeJoueurs.get(joueurActif).getCartesCouleur(cPosee).toString());
				int idCarteDefausse=Integer.parseInt(sc.next());
				Carte cDefausse = listeJoueurs.get(joueurActif).choisirCarteParId(idCarteDefausse);
				listeJoueurs.get(joueurActif).getZone_construction().afficherCarteDessus();
				System.out.println("Choisir une pile ou en créer une nouvelle (0):");
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
	}

	private void consulterMain() {
		System.out.println("Vous voulez consulter vos cartes en main");
		jActif.afficherMain();

	}

	//Permet d'emprunter de l'argent (ne compte pas comme une action)
	private void contracterPret() {
		//les vérifications sur le scanner se font dans la classe Joueur
		int Montant = 0;

		System.out.println("Quel montant souhaitez-vous emprunter? (Doit être un entier multiple de 10)");
		System.out.println("Le remboursement se fera en fin de partie au taux de 1.5%");

		//On vérifie si l'entrée clavier est correct (int)
		if(sc.hasNextInt()){
			Montant = sc.nextInt();
			erreur = listeJoueurs.get(joueurActif).emprunter(Montant);
			erreur.getMsgError();
		}

		//On indique qu'il y a une erreur et on passe au prochaine scanner (si l'entrée n'était pas un int)
		else{
			System.err.println("La montant doit être un entier multiple de 10 \n");
			sc.next();
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




}
