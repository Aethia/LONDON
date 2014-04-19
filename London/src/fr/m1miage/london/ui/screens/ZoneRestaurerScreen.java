package fr.m1miage.london.ui.screens;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import fr.m1miage.london.GestionErreurs;
import fr.m1miage.london.Regles;
import fr.m1miage.london.classes.Carte;
import fr.m1miage.london.classes.Joueur;
import fr.m1miage.london.ui.Prefs;
import fr.m1miage.london.ui.graphics.AreaColorRect;
import fr.m1miage.london.ui.graphics.Art;
import fr.m1miage.london.ui.graphics.Buttons;
import fr.m1miage.london.ui.graphics.CarteActor;
import fr.m1miage.london.ui.graphics.Fonts;
import fr.m1miage.london.ui.graphics.PileActor;
import fr.m1miage.london.ui.graphics.Score;

public class ZoneRestaurerScreen extends Screen{
	private Stage stage;

	private TextButton btnRetour;
	private TextButton btnAnnulCarte;

	private TextButton validerConstru;

	private int idCarteSelected =0;
	private int idDefausseSelected =0;
	private int pileSelected = -1;
	private CarteActor cDefausse;

	private List<CarteActor> cActorList= new ArrayList<CarteActor>();
	//private List<CarteActor> cActorListColor=new ArrayList<CarteActor>();


	private AreaColorRect fondChoixCartes= new AreaColorRect(950, 330, 300, 350);


	public Map<Integer, CarteActor> main = new HashMap<Integer,CarteActor>();
	//private Table tPiles;
	private List<PileActor> lPiles;
	private String messageAction = new String("");
	private GestionErreurs erreur;
	private Joueur joueur;

	/* Scores */
	private Score scoreJoueur;

	public ZoneRestaurerScreen(){
		messageAction = "Voici votre zone de construction";
		joueur = londonG.partie.getObjJoueurActif();
		restaurerScreen();
	}

	//afficher la zone de constru d'un joueur
	public ZoneRestaurerScreen(Joueur j){
		messageAction = "Voici la zone de construction de " + j.getNom();
		this.joueur = j;
		restaurerScreen();
	}

	public ZoneRestaurerScreen(String msg){
		messageAction = msg;
		joueur = londonG.partie.getObjJoueurActif();
		restaurerScreen();
	}

	private void restaurerScreen() {
		stage = new Stage(Prefs.LARGEUR_FENETRE, Prefs.HAUTEUR_FENETRE, false); 
		stage.clear();
		Gdx.input.setInputProcessor(stage);
		fondChoixCartes.setVisible(false);
		fondChoixCartes.setShapeFillColor(1, 1, 1, 0.7f);

		btnRetour =new TextButton("Retour",Buttons.styleInGameMenu); 
		btnRetour.setPosition(1100, 135); 
		btnRetour.addListener(new InputListener(){

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				Screen.setScreen(new GameScreen());
				super.touchUp(event, x, y, pointer, button);
			}

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}
		});
		stage.addActor(btnRetour);

		afficherPiles();

		stage.addActor(fondChoixCartes);
		if(londonG.partie.getObjJoueurActif().equals(joueur)==true){ //si on est bien sur le joueur actif 
			//si tour terminé, mais action choisie => on peut continuer ou tour pas terminé mais zoneC du joueur Actif
			if((londonG.partie.isTourTermine()==true && londonG.partie.getActionChoisie()!=0) || !londonG.partie.isTourTermine() ){ 
				afficherCartesInvestir();
			}



		}

		scoreJoueur = new Score(joueur);
		stage.addActor(scoreJoueur);

	}


	private void afficherCartesCouleurs(CarteActor c){ //Méthode qui va afficher les cartes à défausser possibles
		messageAction="Choisissez une carte à défausser";
		for(final Integer i : main.keySet()){
			//Si la carte n'a pas la même couleur que la carte choisie, on va mettre son setVisible à false
			if(main.get(i).getCarte().getCouleur().compareTo(c.getCarte().getCouleur())!=0 && main.get(i).getId()!=c.getId()){
				cActorList.add(main.get(i));
				main.get(i).setVisible(false);
			}

			//sinon, et si ce n'est pas la carte choisie, on met un input listener 
			else if(main.get(i).getId()!=c.getId()){

				main.get(i).addListener(new InputListener(){

					@Override
					public void enter(InputEvent event, float x, float y,
							int pointer, Actor fromActor) {
						//L'inputListener ne doit agir que sur les cartes à défausser
						if(idCarteSelected!=0 && main.get(i).getId() != idCarteSelected){
							if(cDefausse != null && idDefausseSelected == 0){
								cDefausse.setDefaultPosition();
							}
							if(idDefausseSelected==0){
								cDefausse=main.get(i);
								main.get(i).setY(110);
							}
						}

						super.enter(event, x, y, pointer, fromActor);


					}



					@Override
					public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {		
						//On va sélectionner la carte à défausser (on peut changer avec le clic) 
						if(idCarteSelected!=0 && main.get(i).getId() != idCarteSelected){	 
							if(idDefausseSelected != 0 && cDefausse != null){
								cDefausse.setDefaultPosition();
							}
							cDefausse=main.get(i);	
							idDefausseSelected=main.get(i).getId();
							fondChoixCartes.setVisible(true);
							main.get(i).setX(1000);
							main.get(i).setY(360);
							//On peut affiche le bouton pour valider
							validerConstru.setVisible(true);
						}

						super.touchDown(event, x, y, pointer, button);
						return true;
					}
				});


			}
		}
	}


	private void afficherCartesInvestir(){
		int i=0;
		for(final Carte c: joueur.getLesCartes()){
			i++;
			final CarteActor ca = new CarteActor(c,350+i*50,10);
			stage.addActor(ca);
			ca.addListener(new InputListener(){

			});
		}
	}

	private void cacherCartes(){		
		for(CarteActor c : cActorList){
			c.setVisible(true);
		}
		cActorList.clear();
	}

	//si le type du jeu est restaurer, les cartes ont un listener
	private void afficherPiles() {
		int left = 100;
		int i = 0;
		for(Carte pile : joueur.getZone_construction().cartesTop()){
			final CarteActor ca = new CarteActor(pile, left+i*215, 360);
			System.out.println("carte dessus pile :" + pile.getNom());
			final List<Integer> lCartes = new ArrayList<Integer>();

			ca.addListener(new InputListener(){

				@Override
				public boolean touchDown(InputEvent event, float x,
						float y, int pointer, int button) {
					if(idCarteSelected!=0 && idCarteSelected == ca.getId()){ //si on a recliqué sur la carte, on la met a sa place
						idCarteSelected=0;
						afficherCartes();
					}else if(idCarteSelected ==0 && !ca.getCarte().isDesactivee()){
						idCarteSelected = ca.getId();
						Carte c = ca.getCarte();
						lCartes.add(c.getId_carte());
						int type = c.getCoutActivation().getTypeActiv();
						System.out.println("wut" + type);
						switch(type){
						case Regles.ACTIVATION_AUCUN :
							System.out.println("aucun cout");
							erreur = joueur.restaurerVille(lCartes);
							if(!erreur.equals(GestionErreurs.NONE)){
								messageAction = erreur.getMsgErrorString();
							}else{
								System.out.println("aucun cout");
								activation(ca);
							}

							break;
						case Regles.ACTIVATION_LIVRES :
							erreur = joueur.restaurerVille(lCartes);
							if(!erreur.equals(GestionErreurs.NONE)){
								messageAction = erreur.getMsgErrorString();
							}else{
								activation(ca);

							}
							break;
						case Regles.ACTIVATION_UNIQUE :
							erreur = joueur.restaurerVille(lCartes);
							if(!erreur.equals(GestionErreurs.NONE)){
								messageAction = erreur.getMsgErrorString();
							}else{//carte activable
								int nb = 0;
								nb = masquerCartes(c.getCoutActivation().getCouleurADefausser());
								if(nb==0){
									messageAction = "Vous n'avez pas de carte à defausser";
								}else{
									messageAction = "choisir une carte" + c.getCoutActivation().getCouleurADefausser();
									messageAction+= " à défausser";
								}
							}
							break;
						case Regles.ACTIVATION_ANYCOLOR :
							if(!erreur.equals(GestionErreurs.NONE)){
								messageAction = erreur.getMsgErrorString();
							}else{//carte activable
								int nb = main.size();
								if(nb==0){
									messageAction = "Vous n'avez pas de carte à defausser";
								}else{
									messageAction = "choisir une carte de n'importe quelle couleur à défausser";
								}
							}
							break;
						}
					}//fin if
					return false;
				}

			});



			stage.addActor(ca);
		}

	}

	private void activation(CarteActor ca){
		Carte c=ca.getCarte();
		joueur.aActive(c);
		londonG.partie.setActionChoisie(Regles.RESTAURER);
		londonG.partie.setTourTermine(true);
		if(c.isDesactivee()){
			ca.setDisabled();
		}
		stage.getRoot().removeActor(scoreJoueur);
		scoreJoueur = new Score(joueur);
		stage.addActor(scoreJoueur);
	}

	private int masquerCartes(String couleurAafficher){
		int nb=0;
		for(Integer key : main.keySet()){
			CarteActor ca = main.get(key);
			if(!ca.getCarte().getCouleur().equals(couleurAafficher)){
				ca.setVisible(false);
			}else{
				nb++;
			}
		}
		return nb++;
	}
	private void afficherCartes(){
		for(Integer key : main.keySet()){
			CarteActor ca = main.get(key);
			ca.setVisible(true);

		}
	}

	@Override
	public void render() {
		spriteBatch.begin();
		draw(Art.bgPartie, 0, 0);
		Fonts.FONT_TITLE.draw(spriteBatch, "RESTAURER", 200, 20);

		Fonts.FONT_BLACK.draw(spriteBatch, messageAction, 500, 100);

		spriteBatch.end();



		stage.act();
		stage.draw();



	}


	@Override
	public void tick() {
		// TODO Auto-generated method stub

	}



}
