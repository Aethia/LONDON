package fr.m1miage.london.ui.screens;

import java.util.ArrayList;
import java.util.List;

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
import fr.m1miage.london.ui.graphics.Art;
import fr.m1miage.london.ui.graphics.Buttons;
import fr.m1miage.london.ui.graphics.CarteActor;
import fr.m1miage.london.ui.graphics.Fonts;
import fr.m1miage.london.ui.graphics.Score;

public class ZoneRestaurerScreen extends Screen{
	private Stage stage;

	private TextButton btnRetour;


	private List<CarteActor> main= new ArrayList<CarteActor>();
	TextButton activerBtn;
	private String messageAction = new String("");
	private GestionErreurs erreur;
	private Joueur joueur;

	private CarteActor carteActivation;
	private CarteActor carteDefausse;
	/* Scores */
	private Score scoreJoueur;

	public ZoneRestaurerScreen(String msg){
		messageAction = msg;
		joueur = londonG.partie.getObjJoueurActif();
		restaurerScreen();
	}

	private void restaurerScreen() {
		stage = new Stage(Prefs.LARGEUR_FENETRE, Prefs.HAUTEUR_FENETRE, false); 
		stage.clear();
		Gdx.input.setInputProcessor(stage);

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

		if(londonG.partie.getObjJoueurActif().equals(joueur)==true){ //si on est bien sur le joueur actif 
			//si tour terminé, mais action choisie => on peut continuer ou tour pas terminé mais zoneC du joueur Actif
			if((londonG.partie.isTourTermine()==true && londonG.partie.getActionChoisie()!=0) || !londonG.partie.isTourTermine() ){ 
				afficherCartesInvestir();
			}



		}


		//		//bouton de validation des choix
		//		validerRestaurer = new TextButton("Activer !", Buttons.styleInGameMenu);
		//		validerRestaurer.setSize(200, 50);
		//		validerRestaurer.setPosition(800, 280);
		//		validerRestaurer.setVisible(false);
		//		validerRestaurer.addListener(new InputListener(){
		//
		//			@Override
		//			public boolean touchDown(InputEvent event, float x, float y,
		//					int pointer, int button) {
		//				return true;
		//			}
		//
		//			@Override
		//			public void touchUp(InputEvent event, float x, float y,
		//					int pointer, int button) {
		//
		//				erreur = joueur.restaurerVille2(carteActivation.getCarte(), cDefausse.getCarte(), londonG.partie.getPlateau().getEtalage());
		//				if(erreur.equals(GestionErreurs.NONE)){ //si aucune erreur, le tour est terminé
		//					londonG.partie.setActionChoisie(Regles.RESTAURER);
		//					londonG.partie.setTourTermine(true);
		//				}else{
		//					Screen.setScreen(new ZoneConstructionScreen(erreur.getMsgErrorString())); 
		//				}
		//				super.touchUp(event, x, y, pointer, button);
		//			}
		//
		//		});
		//
		//
		//		
		//		stage.addActor(validerRestaurer);



		activerBtn = new TextButton("activer", Buttons.styleInGameMenu );
		activerBtn.setPosition(500, 400);
		activerBtn.setVisible(false);
		activerBtn.addListener(new InputListener(){

			@Override
			public boolean touchDown(InputEvent event,
					float x, float y, int pointer,
					int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x,
					float y, int pointer, int button) {

				erreur =joueur.restaurerVille2(carteActivation.getCarte(), carteDefausse.getCarte(), londonG.partie.getEtalage());
				if(erreur.equals(GestionErreurs.NONE)){
					messageAction = erreur.getMsgErrorString();
				}else{
					activation(carteActivation);
				}
				super.touchUp(event, x, y, pointer, button);
			}

		});



		scoreJoueur = new Score(joueur);
		stage.addActor(scoreJoueur);

		mainJoueur();

	}


	private void mainJoueur(){ 

		int i=0;
		for(final Carte c: joueur.getLesCartes()){
			i++;
			final CarteActor ca = new CarteActor(c,350+i*50,10);	
			ca.addListener(new InputListener(){

				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {				
					System.out.println("je clique sur une carte de la main");
					if(carteDefausse!=null && carteDefausse==ca){ //si on a deja cliqué sur cette carte
						carteDefausse =null;
						activerBtn.setVisible(false);
						carteDefausse.setDefaultPosition();
					}else if(carteDefausse != null && carteDefausse!=ca){ //si on a cliqué sur une autre carte
						carteDefausse.setDefaultPosition();
						ca.setY(300);
						activerBtn.setVisible(true);
						carteDefausse = ca;
					}else if(carteDefausse ==null){
						carteDefausse = ca;
						activerBtn.setVisible(true);
					}
					return super.touchDown(event, x, y, pointer, button);
				}


				@Override
				public void exit(InputEvent event, float x, float y,
						int pointer, Actor toActor) {
					ca.setDefaultPosition();
					super.exit(event, x, y, pointer, toActor);
				}


				@Override
				public void enter(InputEvent event, float x, float y,
						int pointer, Actor fromActor) {
					ca.setY(300);
					super.enter(event, x, y, pointer, fromActor);
				}

			});
			ca.setVisible(false);
			main.add(ca);
			stage.addActor(ca);
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

	//si le type du jeu est restaurer, les cartes ont un listener
	private void afficherPiles() {
		int left = 100;
		int i = 0;
		for(Carte pile : joueur.getZone_construction().cartesTop()){
			final CarteActor ca = new CarteActor(pile, left+i*215, 360);
			System.out.println("carte dessus pile :" + pile.getNom());

			ca.addListener(new InputListener(){

				@Override
				public boolean touchDown(InputEvent event, float x,
						float y, int pointer, int button) {
					if(!ca.getCarte().isDesactivee()){ //si c'est pas null et que la carte n'est pas desactivée
						carteActivation = ca;
						System.out.println("c'est une carte activable");
						Carte cActiv = carteActivation.getCarte();
						int type = cActiv.getCoutActivation().getTypeActiv();
						System.out.println("wut" + type);
						switch(type){
						case Regles.ACTIVATION_AUCUN :
							System.out.println("aucun cout");
							activerBtn.setText("Activer");
							activerBtn.setVisible(true);
							break;
						case Regles.ACTIVATION_LIVRES :
							//demander au joueur s'il veut payer pour activer
							activerBtn.setText("Payer £" + cActiv.getArgentActivation());
							activerBtn.setVisible(true);
							break;
						case Regles.ACTIVATION_UNIQUE :
							int nb = 0;
							nb = masquerCartes(cActiv.getCoutActivation().getCouleurADefausser());
							if(nb==0){
								messageAction = "Vous n'avez pas de carte à defausser";
							}else{
								messageAction = "choisir une carte" + cActiv.getCoutActivation().getCouleurADefausser();
								messageAction+= " à défausser";
							}

							break;
						case Regles.ACTIVATION_ANYCOLOR :
							nb = main.size();
							if(nb==0){
								messageAction = "Vous n'avez pas de carte à defausser";
							}else{
								messageAction = "choisir une carte de n'importe quelle couleur à défausser";
								afficherMain();
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
		//joueur.aActive(c);
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
		for(CarteActor ca : main){
			if(!ca.getCarte().getCouleur().equals(couleurAafficher)){
				ca.setVisible(false);
			}else{
				ca.setVisible(true);
				nb++;
			}
		}
		return nb++;
	}
	private void afficherMain(){
		for(CarteActor ca : main){
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
