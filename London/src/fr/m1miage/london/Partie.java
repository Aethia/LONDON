package fr.m1miage.london;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
	

	
	private boolean actionEffectuee;
	// le joueur actuellement actif
	private int joueurActif=0;
	private Joueur jActif;
	private int actionChoisie =0; //1 construire, 2 restaurer, 3 investir, 4 piocher
	private boolean tourTermine=false;



	public Partie(){
		this.plateau = new Plateau();
		this.pioche = new Pioche();
	}


	public Partie(List<Joueur> listeJ, int nbJ){
		this.plateau = new Plateau();
		this.pioche = new Pioche();
		this.listeJoueurs = listeJ;
		this.nbJoueurs = nbJ;
	}

	


	public void init(){

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


	public Etalage getEtalage(){
		return plateau.getEtalage();
	}


	
	
	
	

	public List<Joueur> getListeJoueurs() {
		return listeJoueurs;
	}

	public void setListeJoueurs(List<Joueur> listeJoueurs) {
		this.listeJoueurs = listeJoueurs;
		this.nbJoueurs = listeJoueurs.size();
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
		System.out.println("Objet sérialisé");
		s.close();
	}

	public void chargerPartie() throws IOException, ClassNotFoundException{
		FileInputStream in=new FileInputStream("save.txt");
		ObjectInputStream s = new ObjectInputStream(in);

		Partie p = (Partie) s.readObject();

		Etalage etalage = (Etalage)s.readObject();
		p.getPlateau().setEtalage(etalage);
		System.out.println("Partie chargé");
		s.close();
		this.listeJoueurs=p.listeJoueurs;
		this.nbJoueurs=p.nbJoueurs;
		this.pioche = p.pioche;
		this.plateau = p.plateau;
		this.joueurActif=p.joueurActif;
		this.actionEffectuee=p.actionEffectuee;
		this.jActif = p.getObjJoueurActif();
		this.tourTermine = p.tourTermine;
		this.actionChoisie = p.actionChoisie;

	}
}
