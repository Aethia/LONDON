package fr.m1miage.london.classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import fr.m1miage.london.GestionErreurs;
import fr.m1miage.london.Partie;

public class Effet implements Serializable{
	private int idEffet;
	private String nomEffet;
	private int type; //1 = passif, 2 = actif
	
	
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

	//cartes 19, 106, 110
	//effet 5
	//on pioche 2 cartes
	public GestionErreurs prendreDeuxCartes(Pioche pioche, Joueur j){
		if(pioche.getNbCartes() >= 2){
			j.ajouterCartesMain(pioche.tirerNCartes(2));
			return GestionErreurs.NONE;
		}
		else
			return GestionErreurs.NOT_ENOUGH_CARD;
	}
	
	//cartes 37, 63
	//effet 7
	//on reçoit un point de victoire pour chaque carte non brune dans la zone de construction
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
	
	//cartes 50, 62, 94
	//effet 11
	//on reçoit un point de victoire pour chaque carte brune dans la zone de construction
	public void pVPourCartesBrune(Joueur j){
		ArrayList<ArrayList<Carte>> zc = j.getZone_construction().getCartes();
		for(ArrayList<Carte> pile : zc){
			for(Carte c: pile){
				if(c.getCouleur().equals("Brun"))
					j.setAddPoint_victoire(1);
			}
		}
	}
	
	//cartes 91, 93
	//effet 25
	//£2 pour chaque quartier au nord de la tamise
	public void argentQuartiersNord(Plateau plateau, Joueur j){
		Map<Integer,Quartier> quartiers = plateau.getQuartiers();
		for(Integer key : quartiers.keySet()){
			if( ( quartiers.get(key).getProprietaireQuartier() == j ) && ( !quartiers.get(key).isAuSudTamise() ) ) {
				j.setAddArgent(2);
			}
		}
	}
	
	//cartes 83
	//effet 23
	//£2 pour chaque quartier au sud de la tamise
	public void argentQuartiersSud(Plateau plateau, Joueur j){
		Map<Integer,Quartier> quartiers = plateau.getQuartiers();
		for(Integer key : quartiers.keySet()){
			if( ( quartiers.get(key).getProprietaireQuartier() == j ) && ( quartiers.get(key).isAuSudTamise() ) ) {
				j.setAddArgent(2);
			}
		}
	}
	
	//cartes 54, 67
	//effet 12
	//£1 pour chaque quartier occupé
	public void argentQuartiersOccupes(Plateau plateau, Joueur j){
		Map<Integer,Quartier> quartiers = plateau.getQuartiers();
		for(Integer key : quartiers.keySet()){
			if(quartiers.get(key).getProprietaireQuartier() == j){
				j.setAddArgent(1);
			}
		}
	}
	
	//cartes 57, 59
	//effet 15
	//£2 pour chaque quartier adjacent à la tamise
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
	
	//cartes 39
	//effet 8
	//donne à un joueur de notre choix 1 point de pauvreté
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
	
	//cartes 41
	//effet 9
	//le joueur de votre choix prend 2 points de pauvreté
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
	
	//cartes 71
	//effet 19
	//Chaque autre joueur doit payer £1 à la banque pour chaque quartier que vous avez investi
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
	
	//cartes 42
	//effet 10
	//On prend £2 à chaque autres joueurs
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
	
	
	
	//cartes 103
	//effet 28
	//Prenez £1 et perdez un point de pauvreté pour chaque carte "Pauvres" que vous placez sur l'étalage
	public void pauvresSurEtalage(Partie partie, Joueur j, Carte c){
		//for(Carte c : lc){
		if(c.getCouleur().equals("Gris")){
			j.setAddArgent(1);
			j.setAddPoint_pauvrete(-1);
			j.getMainDuJoueur().supprimerCarteParId(c.getId_carte());
			partie.getEtalage().ajouterCarte(c);
		}
		//}
	}
	
	//cartes 56
	//effet 14
	//Perdez un point de pauvreté par carte de votre main que vous placez sur l'étalage (jusqu'à 3 cartes).
	public void pauvresParCarteSurEtalage(Partie partie, Joueur j, Carte c){
		j.setAddPoint_pauvrete(-1);
		j.getMainDuJoueur().supprimerCarteParId(c.getId_carte());
		partie.getEtalage().ajouterCarte(c);
	}
}
