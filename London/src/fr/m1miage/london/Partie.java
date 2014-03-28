package fr.m1miage.london;

import java.awt.Color;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

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
	private int joueurActif;

	public Partie() {
		this.plateau = new Plateau();
		this.pioche = new Pioche();
	}

	private void creerJoueurs(int nbJoueurs) {
		Color couleur = rouge;
		for (int i = 0; i < nbJoueurs; i++) {
			System.out.println("Entrez le nom du joueur " + (i + 1) + " : ");
			String nomJoueur = sc.next();
			System.out
					.println("Choisissez votre couleur : \n 1.Rouge \n 2.Vert \n 3.Jaune \n 4.Bleu");
			switch (sc.nextInt()) {
			case 1:
				couleur = rouge;
			case 2:
				couleur = vert;
			case 3:
				couleur = jaune;
			case 4:
				couleur = bleu;
			}
			Joueur joueur = new Joueur(i, nomJoueur, couleur);
			listeJoueurs.add(joueur);
		}
	}

	public void init() {
		boolean condition = false;
		while (condition == false) {
			System.out.println("Entrez le nombre de joueurs : ");
			if (sc.hasNextInt()) {
				int reponse = sc.nextInt();
				if (reponse < Regles.NBMAXJOUEURS) {
					nbJoueurs = reponse;
					condition = true;
				} else {
					System.out
							.println("Valeur incorrecte. Il ne peut y avoir que de 2 à 5 joueurs.");
				}
			} else {
				System.out.println("Valeur incorrecte.");
				sc.next();
			}
		}
		creerJoueurs(nbJoueurs);

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
	public void lancerJeu() {
		Boolean actionEffectuee = false;
		// on initialise le premier joueur
		joueurActif = 0;
		// on joue tant qu'il y a des cartes dans la pioche
		while (pioche.getNbCartes() > 0) {

			// le joueur actif doit choisir une action
			System.out.println("c'est au tour de "
					+ listeJoueurs.get(joueurActif).getNom()
					+ ", que faites vous ?");
			if (!actionEffectuee) {
				System.out.println(" -- Les Actions --");
				System.out
						.println("1. Jouer une carte (poser une carte devant soi)");
				System.out
						.println("2. Restaurer la ville (activer des cartes)");
				System.out.println("3. Investir (acheter un quartier)");
				System.out.println("4. Piocher 3 cartes");
				System.out.println(" -- Autre --");
			}
			System.out.println("5. Contracter un prêt");
			System.out.println("6. Consulter mes cartes en main");
			System.out.println("7. Consulter l'étalage de cartes");
			System.out.println("8. Finir mon tour");

			// switch de l'action
			switch (sc.nextInt()) {
			case 1: {
				// si une action a déjà été faite dans le tour
				if (actionEffectuee) {
					System.err
							.println("Vous avez déjà effectué une action pour ce tour!");
					break;
				}
				jouerCarte();
				actionEffectuee = true;
				break;
			}

			case 2: {
				// si une action a déjà été faite dans le tour
				if (actionEffectuee) {
					System.err
							.println("Vous avez déjà effectué une action pour ce tour!");
					break;
				}
				restaurerVille();
				actionEffectuee = true;
				break;
			}

			case 3: {
				// si une action a déjà été faite dans le tour
				if (actionEffectuee) {
					System.err
							.println("Vous avez déjà effectué une action pour ce tour!");
					break;
				}
				investir();
				actionEffectuee = true;
				break;
			}

			case 4: {
				// si une action a déjà été faite dans le tour
				if (actionEffectuee) {
					System.err
							.println("Vous avez déjà effectué une action pour ce tour!");
					break;
				}
				piocherCartes();
				actionEffectuee = true;
				break;
			}

			case 5: {
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
							.println("Vous ne pouvez pas finir votre tour sans faire d'action!");
					break;
				}
				// passe au suivant
				joueurSuivant();
				actionEffectuee = false;
				break;
			}

			default: {
				System.out.println("Je n'ai pas compris votre choix");
				break;
			}
			}
		}
	}

	private void piocherCartes() {
		System.out.println("Vous voulez piocher 3 cartes");
	}

	private void investir() {
		System.out.println("Vous voulez investir");
	}

	private void restaurerVille() {
		System.out.println("Vous voulez restaurer la ville");
	}

	private void jouerCarte() {
		System.out.println("vous voulez jouer une carte");
	}

	private void consulterEtalage() {
		System.out.println("Vous voulez consulter l'étalage de cartes");
	}

	private void consulterMain() {
		System.out.println("Vous voulez consulter vos cartes en main");

	}

	private void contracterPret() {
		System.out.println("Quel montant ? (Doit être un multiple de 10");
		int Montant = 0;
		
		try {
			Montant = sc.nextInt();
		} catch (Exception e) {
			System.err.println("Montant incorrect");
			return;
		}
		if (Montant > 0 && Montant % 10 == 0) {
			listeJoueurs.get(joueurActif).emprunter(Montant);
			System.out.println("Vous venez d'emprunter £" + Montant);
		} else
			System.err.println("Montant incorrect");
	}
}
