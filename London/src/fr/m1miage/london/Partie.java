package fr.m1miage.london;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import fr.m1miage.london.classes.Carte;
import fr.m1miage.london.classes.Etalage;
import fr.m1miage.london.classes.Joueur;
import fr.m1miage.london.classes.Pioche;
import fr.m1miage.london.classes.Plateau;

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

	private int typeGUI=0; // par defaut : 0 => console, 1 = Graphique

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
				//On v�rifie si l'entr�e clavier est correcte
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
								.println("Veuillez s�lectionner une des couleurs suivantes : \n 1.Rouge \n 2.Vert \n 3.Jaune \n 4.Bleu");
							break;
						}
					}
				
				//entr�e clavier incorrect, on passe � la suivante
				else{
					System.err.println("Valeur incorrecte \n");
					System.out
						.println("Veuillez s�lectionner une des couleurs suivantes : \n 1.Rouge \n 2.Vert \n 3.Jaune \n 4.Bleu");
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

		// on distribue les cartes (los cartos en espagnol)
		for (Joueur i : listeJoueurs) {
			i.ajouterCartesMain(pioche.tirerNCartes(Regles.NBCARTESDEPART));
		}
	}

	// faire passer le joueur actif au joueur suivant
	private void joueurSuivant() {
		if (joueurActif + 1 >= nbJoueurs) {
			joueurActif = 0;
		} else {
			joueurActif++;
		}
	}
	

	// la boucle de jeu
	public void lancerJeu(Boolean nouveau) {
		if(nouveau == true){
			actionEffectuee = false;
			// on initialise le premier joueur
			joueurActif = 0;
			listeJoueurs.get(joueurActif).piocher(pioche);
		}

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
			System.out.println("5. Contracter un pr�t");
			System.out.println("6. Consulter mes cartes en main");
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
						// si une action a d�j� �t� faite dans le tour
						if (actionEffectuee) {
							System.err.println("Vous avez d�j� effectu� une action pour ce tour! \n");
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
							System.err.println("Vous ne pouvez pas finir votre tour sans faire d'action! \n");
							break;
						}
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
		System.out.println(plateau.getQuartiers());
		System.out.println("Dans quel quartier souhaitez vous investir ? (indiquer son numero)");
		
		int quartier=0;
		
		if(sc.hasNextInt()){
			String message = "";
			quartier = sc.nextInt();
			
			message=listeJoueurs.get(joueurActif).invest(quartier, plateau, pioche);
			System.out.println(message);
		}
		//passe au prochaine scanner (le précédent n'étant pas un int)
		else{
			System.err.println("Numero de quartier incorrect");
			sc.next();
		}
		
	}
	
	

	private void restaurerVille() {
		System.out.println("Vous voulez restaurer la ville");
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
			System.out.println("Quelle carte de m�me couleur voulez-vous d�fausser ?");
			System.out.println(listeJoueurs.get(joueurActif).getCartesCouleur(cPosee).toString());
			int idCarteDefausse=Integer.parseInt(sc.next());
			Carte cDefausse = listeJoueurs.get(joueurActif).choisirCarteParId(idCarteDefausse);
			listeJoueurs.get(joueurActif).getZone_construction().afficherCarteDessus();
			System.out.println("Choisir une pile ou en cr�er une nouvelle (0):");
			int indexPile=Integer.parseInt(sc.next());	
			String message = listeJoueurs.get(joueurActif).construire(cPosee, cDefausse, indexPile);
			System.out.println(message);
			System.out.println(listeJoueurs.get(joueurActif).getZone_construction().getNbPiles());
			
			System.out.println(listeJoueurs.get(joueurActif).getArgent());
			listeJoueurs.get(joueurActif).afficherMain();
			System.out.println(Plateau.etalage.toString());
			
			System.out.println("1. Rejouer une carte \n 2. Finir les constructions");
			if(sc.hasNextInt()){
				finConstruction = sc.nextInt();
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

	}

	//Permet d'emprunter de l'argent (ne compte pas comme une action)
	private void contracterPret() {
		//les v�rifications sur le scanner se font dans la classe Joueur
		int Montant = 0;
		
		System.out.println("Quel montant souhaitez-vous emprunter? (Doit �tre un entier multiple de 10)");
		System.out.println("Le remboursement se fera en fin de partie au taux de 1.5%");
		
		//On v�rifie si l'entr�e clavier est correct (int)
		if(sc.hasNextInt()){
			String message ="";
			Montant = sc.nextInt();
			message = listeJoueurs.get(joueurActif).emprunter(Montant);
			System.out.println(message);
		}
		
		//On indique qu'il y a une erreur et on passe au prochaine scanner (si l'entr�e n'�tait pas un int)
		else{
			System.err.println("La montant doit �tre un entier multiple de 10 \n");
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
		return listeJoueurs.get(joueurActif);
	}
	
	public void setJoueurActif(int joueurActif) {
		this.joueurActif = joueurActif;

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
