package fr.m1miage.london;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import fr.m1miage.london.classes.Carte;
import fr.m1miage.london.classes.Etalage;
import fr.m1miage.london.classes.Joueur;
import fr.m1miage.london.classes.Pioche;
import fr.m1miage.london.classes.Plateau;
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
	

	
	private boolean actionEffectuee;
	// le joueur actuellement actif
	private int joueurActif=0;
	private Joueur jActif;
	
	private int actionChoisie =0; //1 construire, 2 restaurer, 3 investir, 4 piocher
	private boolean tourTermine=false;

	private boolean multijoueur = false;
	
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

	public boolean isFinTour(){
		if(pioche.getNbCartes()>0){
			return false;
		}
		return true;
	}

	public void init(){

		plateau.init();
		plateau.getEtalage().setTailleEtalage(nbJoueurs+1);
		pioche.init();

		// on initialise le premier joueur 
		//joueurActif = (int) (0 + (Math.random() * (nbJoueurs - 0)));
		joueurActif = 0;
		jActif = listeJoueurs.get(joueurActif);

		// on distribue les cartes (los cartos en espagnol)
		for (Joueur i : listeJoueurs) {
			
			
			i.ajouterCartesMain(pioche.tirerNCartes(Regles.NBCARTESDEPART));
		
		}

		//le premier joueur pioche une carte
		jActif.piocher(pioche);
	}



	public boolean isMultijoueur() {
		return multijoueur;
	}


	public void setMultijoueur(boolean multijoueur) {
		this.multijoueur = multijoueur;
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


	
	
	
	/**
	 * Calcul des points de victoire en fin de partie
	 */
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
			//un point de pauvret� en plus par carte en main
			listeJoueurs.get(i).setAddPoint_pauvrete(listeJoueurs.get(i).getMainDuJoueur().getNb_cartes());
			
			//un point de victoire pour �
			listeJoueurs.get(i).setAddPoint_victoire(listeJoueurs.get(i).getArgent() / 3);
			
			//quartier + m�tro
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
	
	/**
	 * Calcul du gagnant : on retire des points de victoire en fonction des points de pauvreté
	 */
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
	
	public void setObjJoueurActif(Joueur j){
		this.jActif = j;
	}
	
	
	public Joueur getJoueurParNom(String nom){
		for (Joueur j : this.listeJoueurs) {
			if (j.getNom().equalsIgnoreCase(nom)) {
				return j;
			}
		}
		return null;
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
