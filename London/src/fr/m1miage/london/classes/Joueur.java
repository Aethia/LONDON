package fr.m1miage.london.classes;

import java.awt.Color;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

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
	
	
	public void invest(Scanner sc, Plateau plateau,Pioche pioche){
		
		boolean investir;
		int quartier=0;
		
		if(sc.hasNextInt()){
			quartier = sc.nextInt();
			
			if (quartier > 0 && quartier < 21) {
				
				//on verifie si le joueur à assez d'argent
				if(this.getArgent()>=plateau.getQuartier(quartier).getPrix()){
					
					//on verifie si on peut investir dans le quartier
					investir = plateau.getQuartier(quartier).investirQuartier(this);
					
					//si oui la fonction investirQuartier renvoie true et met a jour les quartiers adjacents
					if(investir==true){
						System.out.println(this.argent);
						this.argent-=plateau.getQuartier(quartier).getPrix(); 
						System.out.println(this.argent);
						
						//le joueur pioche le nb de cartes précisé sur le quartier
						this.ajouterCartesMain(pioche.tirerNCartes(plateau.getQuartier(quartier).getNb_carte_a_piocher()));
						System.out.println("Vous venez d'investir dans le quartier : " + plateau.getQuartier(quartier).getNom() + " !\n");
					
					}	
					else{
						System.out.println("pas assez d'argent");
						return;					
					}	
				}	
				else
					System.out.println("Désolé vous ne pouvez pas investir dans ce quartier !");
			}
			else
				System.err.println("Numero de quartier incorrect");
		}
		else{
			System.err.println("Numero de quartier incorrect");
			sc.next();
		}		
	}
	
	
	
	
	
	
	
	

}
