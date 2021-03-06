package fr.m1miage.london;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import fr.m1miage.london.classes.Carte;
import fr.m1miage.london.classes.Effet;
import fr.m1miage.london.classes.Joueur;
import fr.m1miage.london.classes.TraderClassRestaurerVille;

public class Console {
	
	private final Color rouge = Color.red;
	private final Color vert = Color.green;
	private final Color jaune = Color.yellow;
	private final Color bleu = Color.blue;
	private static Scanner sc = new Scanner(System.in);
	private GestionErreurs erreur;
	
	private int nbJoueurs;
	
	private Partie partie =  new Partie();
	private Effet effet = new Effet();
	
	public Console(){
	}
	
	
	
	public void demarrer(){
		System.out.println("1. Lancer une nouvelle partie");
		System.out.println("2. Charger une partie");
		int nb = sc.nextInt();
		if(nb == 2){
			try {
				partie.chargerPartie();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else{
			creerJoueurs();
			partie.init();
		}
		
		lancerJeu();
	}
	private void creerJoueurs(){
		List<Joueur> listeJoueurs = new ArrayList<Joueur>();
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
				//On verifie si l'entree clavier est correcte				if(sc.hasNextInt()){
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
		partie.setListeJoueurs(listeJoueurs);
	}
	
	
	// la boucle de jeu
		public void lancerJeu() {
			Boolean actionEffectuee = false;
			// on joue tant qu'il y a des cartes dans la pioche
			while (partie.getPioche().getNbCartes() > 0){
				// le joueur actif doit choisir une action
				System.out.println("C'est au tour de "
						+ partie.getObjJoueurActif().getNom()
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
					}
					case 9:{
						if(actionEffectuee == true){
							partie.joueurSuivant();
							partie.getObjJoueurActif().piocher(partie.getPioche());
							actionEffectuee = false;	
						}
						try {
							partie.sauvegarder();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
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
			partie.calculPointsVictoire();
			partie.calculGagnant();
		}

		private void jouerCarte() {
			int finConstruction=1;
			System.out.println(partie.getEtalage().toString());
			while(finConstruction == 1){
				Joueur jActif = partie.getObjJoueurActif();
				System.out.println(jActif.getArgent());
				jActif.afficherMain();
				System.out.println("Choisissez la carte � poser dans la zone de construction : ");
				int idCarte=(Integer.parseInt(sc.next()));
				Carte cPosee = jActif.choisirCarteParId(idCarte);
				List<Carte> lDefausse = jActif.getCartesCouleur(cPosee);
				if(lDefausse.size()==0){
					GestionErreurs.DEFAUSSE_INDISPO.getMsgError();
				}else{
					System.out.println("Quelle carte de m�me couleur voulez-vous d�fausser ?");
					System.out.println(jActif.getCartesCouleur(cPosee).toString());
					int idCarteDefausse=Integer.parseInt(sc.next());
					Carte cDefausse = jActif.choisirCarteParId(idCarteDefausse);
					jActif.getZone_construction().afficherCarteDessus();
					System.out.println("Choisir une pile ou en cr�er une nouvelle (0):");
					int indexPile=Integer.parseInt(sc.next());

					erreur = jActif.construire(cPosee, cDefausse, indexPile,partie.getEtalage());
					if(erreur.equals(GestionErreurs.NONE)){
						System.out.println(jActif.getZone_construction().getNbPiles());

						System.out.println(jActif.getArgent());
						jActif.afficherMain();
						System.out.println(partie.getEtalage().toString());

						System.out.println("1. Rejouer une carte \n 2. Finir les constructions");
						if(sc.hasNextInt()){
							finConstruction = sc.nextInt();
						}
					}
					erreur.getMsgError();

				}


			}
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
			 * Choix des cartes a activer
			 */		System.out.println("Votre zone de construction : \n"+partie.getObjJoueurActif().getZone_construction().toString());
			System.out.println("Quelle(s) carte(s) activer ?");
			args = sc.next();
			lesValeurs = args.split(" ");
			for(String val : lesValeurs) {
				try {
				int tmp = Integer.parseInt(val);
				listVal.add(tmp);
				}
				catch (NumberFormatException e){
					System.err.println("Les arguments doivent ï¿½tre les id des cartes!");
					return false;
				}
			}
			// on regarde s'il est possible de restaurer la ville avec les cartes selectionnees
			erreur = partie.getObjJoueurActif().restaurerVille(listVal);
			if(!erreur.equals(GestionErreurs.NONE)){
				erreur.getMsgError();
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
					System.out.println("Choisissez les cartes à défausser dans votre main :");
					System.out.println(partie.getObjJoueurActif().getMainDuJoueur().toString());
					args = sc.next();
					lesValeurs = args.split(" ");
					for(String val : lesValeurs) {
						try {
						int tmp = Integer.parseInt(val);
						listeCartes.add(partie.getObjJoueurActif().getMainDuJoueur().choisirCarte(tmp));
						}
						catch (NumberFormatException e){
							System.err.println("Les arguments doivent être les id des cartes!");
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
					partie.getObjJoueurActif().payerRestaurationVille(listeCartes);
					// on retourne les cartes que l'on souhaite activer
					for(int idCarte : listVal){
						partie.getObjJoueurActif().getZone_construction().retournerCarte(idCarte);
						
						switch(idCarte){
						
							//Lance la fonction metro si la carte est wait for it ...metro
							case 76:
							case 79:
							case 84:
							case 85:
							case 97:
								metro();
								break;
							
							//cartes 19, 106, 110
							//effet 5
							//on pioche 2 cartes
							case 19:
							case 106:
							case 110:
								effet.prendreDeuxCartes(partie.getPioche(), partie.getObjJoueurActif());
								break;
							
							//cartes 37, 63
							//effet 7
							//on reçoit un point de victoire pour chaque carte non brune dans la zone de construction
							case 37:
							case 63:
								effet.pVPourCartesNonBrune(partie.getObjJoueurActif());
								break;
							
							//cartes 50, 62, 94
							//effet 11
							//on reçoit un point de victoire pour chaque carte brune dans la zone de construction
							case 50:
							case 62:
							case 94:
								effet.pVPourCartesBrune(partie.getObjJoueurActif());
								break;
							
							//cartes 91, 93
							//effet 25
							//£2 pour chaque quartier au nord de la tamise
							case 91:
							case 93:
								effet.argentQuartiersNord(partie.getPlateau(), partie.getObjJoueurActif());
								break;
							
							//cartes 83
							//effet 23
							//£2 pour chaque quartier au sud de la tamise
							case 83:
								effet.argentQuartiersSud(partie.getPlateau(), partie.getObjJoueurActif());
								break;
							
							//cartes 54, 67
							//effet 12
							//£1 pour chaque quartier occupé
							case 54:
							case 67:
								effet.argentQuartiersOccupes(partie.getPlateau(), partie.getObjJoueurActif());
								break;
							
							//cartes 57, 59
							//effet 15
							//£2 pour chaque quartier adjacent à la tamise
							case 57:
							case 59:
								effet.argentQuartiersAdjacentsTamise(partie.getPlateau(), partie.getObjJoueurActif());
								break;
							
							//cartes 39
							//effet 8
							//donne à un joueur de notre choix 1 point de pauvreté
							case 39:
								System.out.println("Entrez le numéro du joueur : ");
								if(sc.hasNextInt()){
									int numJoueur = sc.nextInt();
									effet.donneUnDeVosPP(numJoueur, partie, partie.getObjJoueurActif(), nbJoueurs);
								}
								else{
									System.out.println("Valeur incorrecte.");
									sc.next();
								}
								break;

							
							//cartes 41
							//effet 9
							//le joueur de votre choix prend 2 points de pauvreté
							case 41:
								System.out.println("Entrez le numéro du joueur : ");
								if(sc.hasNextInt()){
									int numJoueur = sc.nextInt();
									effet.prendDeuxPP(numJoueur, partie, partie.getObjJoueurActif(), nbJoueurs);
								}
								else{
									System.out.println("Valeur incorrecte.");
									sc.next();
								}
								break;
							
							//cartes 71
							//effet 19
							//Chaque autre joueur doit payer £1 à la banque pour chaque quartier que vous avez investi
							case 71:
								effet.joueursPayeQuartiersInvestir(partie, partie.getObjJoueurActif());
								break;
								
							//cartes 42
							//effet 10
							//On prend £2 à chaque autres joueurs
							case 42:
								effet.argentRecolterDeuxParJoueur(partie, partie.getObjJoueurActif());
								break;
								
							//cartes 103
							//effet 28
							//Prenez £1 et perdez un point de pauvreté pour chaque carte "Pauvres" que vous placez sur l'étalage
							case 103:
								echangePauvresContreArgent();
								break;
								
							//cartes 56
							//effet 14
							//Perdez un point de pauvreté par carte de votre main que vous placez sur l'étalage (jusqu'à 3 cartes).
							case 56:
								echangePauvresContrePV();
								break;
								
							default:
								
								break;
						}
						
					}

				// todo méthode de joueur pour payer la somme et retourner les cartes

					System.out.println("Cartes activees !");
				}

			 return true;

		}

		private void investir() {
			boolean tourValide= false;
			while(!tourValide){
				System.out.println(partie.getPlateau().getQuartiersDispo());
				System.out.println("Dans quel quartier souhaitez vous investir ? (indiquer son numero)");

				int quartier=0;
				if(sc.hasNextInt()){
					quartier = sc.nextInt();

					erreur=partie.getObjJoueurActif().invest(quartier, partie.getPlateau(), partie.getPioche());
					if(erreur.equals(GestionErreurs.NONE)){
						tourValide=true;
					}
					erreur.getMsgError();
				}
				//passe au prochaine scanner (le precedent n'etant pas un int)
				else{
					GestionErreurs.INCORRECT_NUMBER.getMsgError();
					sc.next();
				}
			}
			//passe au prochaine scanner (le precedent n'etant pas un int)
		}
		private void piocherCartes() {
			System.out.println("Vous voulez piocher 3 cartes");
		}
		//Permet d'emprunter de l'argent (ne compte pas comme une action)
		private void contracterPret() {
			int Montant;
			//les verifications sur le scanner se font dans la classe Joueur		int Montant = 0;
			System.out.println("Quel montant souhaitez-vous emprunter? (Doit etre un entier multiple de 10)");		System.out.println("Le remboursement se fera en fin de partie au taux de 1.5%");
			//On verifie si l'entree clavier est correct (int)
			if(sc.hasNextInt()){
				Montant = sc.nextInt();
				erreur = partie.getObjJoueurActif().emprunter(Montant);
				erreur.getMsgError();
			}
			else{
				System.err.println("La montant doit etre un entier multiple de 10 \n");
				sc.next();
			}
		}
		


		private void consulterEtalage() {
			
			System.out.println("Vous voulez consulter l'Ã©talage de cartes");
			System.out.println(partie.getEtalage().toString());
		}

		private void consulterMain() {
			partie.getObjJoueurActif().afficherMain();
			System.out.println("Vous voulez consulter vos cartes en main");
			partie.getObjJoueurActif().afficherMain();

		}
	//-----------------------------------------------------------------------------------	
		private void metro(){
		
			if(partie.getPlateau().PoserMetro()==true){
				System.out.println(partie.getPlateau().getQuartiersMetro());
				System.out.println("Dans quel quartier voulez-vous placer le 1er marqueur");
				int quartier1;
				
				if(sc.hasNextInt()){
					quartier1 = sc.nextInt();
					
					//fonction effet quartier1.metro()
					partie.getPlateau().getQuartier(quartier1).QuartierMetro();
					
					System.out.println("Marqueur ajouté !");
					
					System.out.println(partie.getPlateau().getQuartiersMetro());
					System.out.println("Dans quel quartier voulez-vous placer le 2ème marqueur");
					
					int quartier2;
					
					if(sc.hasNextInt()){
						quartier2 = sc.nextInt();
						
						//fonction effet quartier2.metro()
						partie.getPlateau().getQuartier(quartier2).QuartierMetro();
						
						if(partie.getPlateau().getQuartier(quartier1).isAuSudTamise()
						   != partie.getPlateau().getQuartier(quartier2).isAuSudTamise()){
							partie.getObjJoueurActif().setAddArgent(-3);
							
						}
						
						partie.getObjJoueurActif().setAddPoint_victoire(4);
						
					}
					else{
						System.err.println("Numero de quartier incorrect \n");
						sc.next();
					}
				}
				else{
					System.err.println("Numero de quartier incorrect \n");
					sc.next();
				}
			}
			
			else{
				System.out.println("Vous ne pouvez pas poser de metro");
			}			

		}
		
		private void echangePauvresContreArgent() {
			int finEchange=1;
			Joueur jActif = partie.getObjJoueurActif();
			while(finEchange == 1){
				jActif.afficherMain();
				System.out.println("Choisissez la carte à échanger : ");
				int idCarte=(Integer.parseInt(sc.next()));
				Carte cChoix = jActif.choisirCarteParId(idCarte);
				
				effet.pauvresSurEtalage(partie, jActif, cChoix);

				jActif.afficherMain();
				System.out.println(partie.getEtalage().toString());
				System.out.println("1. Echanger une autre carte \n 2. Finir l'échange");
				if(sc.hasNextInt()){
					finEchange = sc.nextInt();
				}
			}
		}
		
		private void echangePauvresContrePV() {
			int finEchange=1;
			int nbCarteEchanged=0;
			Joueur jActif = partie.getObjJoueurActif();
			while(finEchange == 1 || nbCarteEchanged <= 3){
				jActif.afficherMain();
				System.out.println("Choisissez la carte à échanger : ");
				int idCarte=(Integer.parseInt(sc.next()));
				Carte cChoix = jActif.choisirCarteParId(idCarte);
				
				effet.pauvresParCarteSurEtalage(partie, jActif, cChoix);
				nbCarteEchanged++;

				jActif.afficherMain();
				System.out.println(partie.getEtalage().toString());
				System.out.println("1. Echanger une autre carte \n 2. Finir l'échange");
				if(sc.hasNextInt()){
					finEchange = sc.nextInt();
				}
			}
		}
}
