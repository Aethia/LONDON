package fr.m1miage.london.classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import fr.m1miage.london.GestionErreurs;
import fr.m1miage.london.Partie;

public class Effet implements Serializable{
	/**
	 * L'id de l'effet
	 * @see Effet#getIdEffet()
	 */
	private int idEffet;
	/**
	 * Le nom de l'effet
	 * @see Effet#getNomEffet()
	 */
	private String nomEffet;
	/**
	 * Le type de l'effet: 1 = passif, 2 = actif
	 * @see Effet#getType()
	 */
	private int type;
	
	
	public transient static final int  COFFEE_SHOP = 1;
	
	public Effet(){
		
	}
	
	public Effet(int idEffet, String nomEffet, int type){
		this.idEffet = idEffet;
		this.nomEffet = nomEffet;
		this.type = type;
	}

	public int getIdEffet() {
		return idEffet;
	}

	public String getNomEffet() {
		return nomEffet;
	}

	public int getType() {
		return type;
	}

	@Override
	public String toString() {
		StringBuilder msg= new StringBuilder(); //"Effet \n\t";
				msg.append("id : ").append(idEffet).append(" - texte : ").append(nomEffet).append(" de type ");
		if(type==1){
			msg.append("passif.\n");
		}else{
			msg.append("actif.\n");
		}
		
		return msg.toString();
	}


	/**
	 * Effet : Défaussez cette carte pour piocher deux autres cartes.
	 * @param pioche : la pioche du jeu
	 * @param j : joueur qui active l'effet
	 * @return GestionErreurs : NONE si le joueur peut piocher 2 cartes, sinon NOT_ENOUGH_CARD
	 */
	public GestionErreurs prendreDeuxCartes(Pioche pioche, Joueur j){
		if(pioche.getNbCartes() >= 2){
			j.ajouterCartesMain(pioche.tirerNCartes(2));
			return GestionErreurs.NONE;
		}
		else
			return GestionErreurs.NOT_ENOUGH_CARD;
	}
	
	/**
	 * Effet : Prenez 1 PV pour chaque carte autre que brune présente dans votre zone de construction (celle-ci incluse).
	 * @param j : joueur qui active l'effet
	 */
	public void pVPourCartesNonBrune(Joueur j){
		//for(int i = 0; i < j.getZone_construction().getNbPiles(); i++){
			//ArrayList<Carte> pile= j.getZone_construction().getCartesPile(i);
		
		ArrayList<ArrayList<Carte>> zc = j.getZone_construction().getCartes();
		for(ArrayList<Carte> pile : zc){
				
			for(Carte c: pile){
				if(!c.getCouleur().equals("Brun"))
					j.setAddPoint_victoire(1);
			}
		}
	}
	
	/**
	 * Effet : Prenez £1 pour chaque carte brune dans votre zone de construction.
	 * @param j : joueur qui active l'effet
	 */
	public void pVPourCartesBrune(Joueur j){
		ArrayList<ArrayList<Carte>> zc = j.getZone_construction().getCartes();
		for(ArrayList<Carte> pile : zc){
			for(Carte c: pile){
				if(c.getCouleur().equals("Brun"))
					j.setAddPoint_victoire(1);
			}
		}
	}

	/**
	 * Effet : Prenez £2 pour chaque quartier au nord de la Tamise que vous avez investi.
	 * @param plateau : le plateau du jeu
	 * @param j : joueur qui active l'effet
	 */
	public void argentQuartiersNord(Plateau plateau, Joueur j){
		Map<Integer,Quartier> quartiers = plateau.getQuartiers();
		for(Integer key : quartiers.keySet()){
			if( ( quartiers.get(key).getProprietaireQuartier() == j ) && ( !quartiers.get(key).isAuSudTamise() ) ) {
				j.setAddArgent(2);
			}
		}
	}

	/**
	 * Effet : Prenez £2 pour chaque quartier au sud de la Tamise que vous avez investi.
	 * @param plateau : le plateau du jeu
	 * @param j : joueur qui active l'effet
	 */
	public void argentQuartiersSud(Plateau plateau, Joueur j){
		Map<Integer,Quartier> quartiers = plateau.getQuartiers();
		for(Integer key : quartiers.keySet()){
			if( ( quartiers.get(key).getProprietaireQuartier() == j ) && ( quartiers.get(key).isAuSudTamise() ) ) {
				j.setAddArgent(2);
			}
		}
	}
	
	/**
	 * Effet : Gagnez £1 pour chaque quartier que vous avez investi.
	 * @param plateau : le plateau du jeu
	 * @param j : joueur qui active l'effet
	 */
	public void argentQuartiersOccupes(Plateau plateau, Joueur j){
		Map<Integer,Quartier> quartiers = plateau.getQuartiers();
		for(Integer key : quartiers.keySet()){
			if(quartiers.get(key).getProprietaireQuartier() == j){
				j.setAddArgent(1);
			}
		}
	}
	
	/**
	 * Effet : Gagnez £2 pour chacun de vos quartiers adjacents à la Tamise.
	 * @param plateau : le plateau du jeu
	 * @param j : joueur qui active l'effet
	 */
	public void argentQuartiersAdjacentsTamise(Plateau plateau, Joueur j){
		Map<Integer,Quartier> quartiers = plateau.getQuartiers();
		for(Integer key : quartiers.keySet()){
			if(quartiers.get(key).getProprietaireQuartier() == j){
				boolean position = quartiers.get(key).isAuSudTamise();
				for(Quartier q : quartiers.get(key).getQuartiersAdjacents()){
					if(q.isAuSudTamise() != position){
						j.setAddArgent(2);
						break;
					}
				}
			}
		}
	}
	
	/**
	 * Effet : Donnez au joueur de votre choix un de vos points de pauvreté.
	 * @param numJoueur : id du joueur visé par l'effet
	 * @param partie : la partie actuelle
	 * @param j : joueur qui active l'effet
	 * @param nbJoueur : nombre de joueurs de la partie courante
	 * @return GestionErreurs : NONE si le joueur visé est différent du joueur activant la carte, sinon WRONG_PLAYER, et
	 * 							NONEXISTANT_PLAYER si l'id du joueur visé n'existe pas
	 */
	public GestionErreurs donneUnDeVosPP(int numJoueur, Partie partie, Joueur j, int nbJoueur){
		if(j.getPoint_pauvrete() < 1)
			return GestionErreurs.NOT_ENOUGH_PAUPERS;
		
		List<Joueur> l = partie.getListeJoueurs();
		if(numJoueur <= nbJoueur)
			if(j.getId() != numJoueur){
				j.setAddPoint_pauvrete(-1);
				l.get(numJoueur-1).setAddPoint_pauvrete(1);;
				return GestionErreurs.NONE;
			}
			else
				return GestionErreurs.WRONG_PLAYER;
		else 
			return GestionErreurs.NONEXISTANT_PLAYER;
	}
	

	/**
	 * Effet : Un joueur de votre choix doit prendre deux points de pauvreté depuis le stock.
	 * @param numJoueur : id du joueur visé par l'effet
	 * @param partie : la partie actuelle
	 * @param j : joueur qui active l'effet
	 * @param nbJoueur : nombre de joueurs de la partie courante
	 * @return GestionErreurs : NONE si le joueur visé est différent du joueur activant la carte, sinon WRONG_PLAYER, et
	 * 							NONEXISTANT_PLAYER si l'id du joueur visé n'existe pas
	 */
	public GestionErreurs prendDeuxPP(int numJoueur, Partie partie, Joueur j, int nbJoueur){ 
		List<Joueur> l = partie.getListeJoueurs();
		
		if(numJoueur <= nbJoueur)
			if(j.getId() != numJoueur){
				l.get(numJoueur-1).setAddPoint_pauvrete(2);;
				return GestionErreurs.NONE;
			}
			else
				return GestionErreurs.WRONG_PLAYER;
		else 
			return GestionErreurs.NONEXISTANT_PLAYER;
	}
	

	/**
	 * Effet : Chaque autre joueur doit payer £1 à la banque pour chaque quartier que vous avez investi.
	 * @param partie : la partie actuelle
	 * @param jcourant : joueur qui active l'effet
	 */
	public void joueursPayeQuartiersInvestir(Partie partie, Joueur jcourant){
		Map<Integer,Quartier> quartiers = partie.getPlateau().getQuartiers();
		for(Integer key : quartiers.keySet()){
			if(quartiers.get(key).getProprietaireQuartier() == jcourant){
				List<Joueur> l = partie.getListeJoueurs();
				for(Joueur joueur : l){
					//on vérifie si le joueur a au moins £1
					if(joueur.getArgent() >= 1 || !joueur.equals(jcourant)){
						joueur.setAddArgent(-1);
						jcourant.setAddArgent(1);
					}
				}
			}
		}
	}
	

	/**
	 * Effet : Prenez £2 à chacun des autres joueurs.
	 * @param partie : la partie actuelle
	 * @param jcourant : joueur qui active l'effet
	 */
	public void argentRecolterDeuxParJoueur(Partie partie, Joueur jcourant){
		List<Joueur> l = partie.getListeJoueurs();
		for(Joueur joueur : l){
			if(!joueur.equals(jcourant)){
				//on vérifie si le joueur a £2
				if(joueur.getArgent() >= 2){
					joueur.setAddArgent(-2);
					jcourant.setAddArgent(2);
				}
				//ou au moins £1
				else if(joueur.getArgent() == 1){
					joueur.setAddArgent(-1);
					jcourant.setAddArgent(1);
				}
			}
		}
	}
	
	/**
	 * Effet : Prenez £1 et perdez un point de pauvreté pour chaque carte "Pauvres" que vous placez sur l'étalage.
	 * @param partie : la partie actuelle
	 * @param j : joueur qui active l'effet
	 * @param c : carte pauvre choisie par le joueur (la boucle pour selectionner plusieurs cartes ne se fait pas dans cette fonction)
	 */
	public void pauvresSurEtalage(Partie partie, Joueur j, Carte c){
		if(c.getCouleur().equals("Gris")){
			j.setAddArgent(1);
			j.setAddPoint_pauvrete(-1);
			j.getMainDuJoueur().supprimerCarteParId(c.getId_carte());
			partie.getEtalage().ajouterCarte(c);
		}
	}
	

	/**
	 * Effet : Perdez un point de pauvreté par carte de votre main que vous placez sur l'étalage (jusqu'à 3 cartes).
	 * @param partie : la partie actuelle
	 * @param j : joueur qui active l'effet
	 * @param c : carte choisie par le joueur
	 */
	public void pauvresParCarteSurEtalage(Partie partie, Joueur j, Carte c){
		j.setAddPoint_pauvrete(-1);
		j.getMainDuJoueur().supprimerCarteParId(c.getId_carte());
		partie.getEtalage().ajouterCarte(c);
	}
}
