package fr.m1miage.london.ui.screens;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import fr.m1.miage.london.network.IncomingPartieObjectListenerClient;
import fr.m1.miage.london.network.IncomingPartieObjectListenerServeur;
import fr.m1.miage.london.network.client.Reception;
import fr.m1.miage.london.network.serveur.Emission;
import fr.m1.miage.london.network.serveur.Serveur;
import fr.m1miage.london.Partie;
import fr.m1miage.london.Regles;
import fr.m1miage.london.classes.Carte;
import fr.m1miage.london.classes.Joueur;
import fr.m1miage.london.ui.Prefs;
import fr.m1miage.london.ui.graphics.Art;
import fr.m1miage.london.ui.graphics.Buttons;
import fr.m1miage.london.ui.graphics.CarteActor;
import fr.m1miage.london.ui.graphics.Fonts;
import fr.m1miage.london.ui.graphics.MenuActions;
import fr.m1miage.london.ui.graphics.MenuGlobal;
import fr.m1miage.london.ui.graphics.Score;
import fr.m1miage.london.ui.graphics.TableauScores;

public class GameScreenReseauServeur extends Screen {
	public IncomingPartieObjectListenerServeur partieObjListener = new IncomingPartieObjectListenerServeur(){
		@Override
		public void nouvelObjet(Object o) {
			londonG.partie = (Partie)o;
			afficherBouton();
		}
	};
	
	private Button finTourBtn;

	/* Main du joueur */
	public int idCarteSelected=0;
	public Map<Integer, CarteActor> main = new HashMap<Integer,CarteActor>();
	public int idCarteOver =0;

	/* Scores */
	private Score scoreJoueur;
	private TableauScores scores;

	private Stage stage; 
	private static String login = "host";
	public static Joueur joueur;
	private int time =0;
	private static final int TIME_OUT_CARD = 150;
	private TextButton btnSuivant;
	public static Button btnSauvegarde;

	
	private void afficherBouton() {
		btnSuivant.setVisible(true);
	}
	
	public GameScreenReseauServeur(){
		synchronized (Reception.listenersPartie) {
			fr.m1.miage.london.network.serveur.Reception.addListenerPartie(partieObjListener);
		}
		stage = new Stage(Prefs.LARGEUR_FENETRE, Prefs.HAUTEUR_FENETRE, false); 
		stage.clear();
		Gdx.input.setInputProcessor(stage);
		
		/* Parametres Boutons Menu General*/
		MenuGlobal tMenu = new MenuGlobal();
		stage.addActor(tMenu);

		joueur = londonG.partie.getJoueurParNom("host");
		
		//a ameliorer
		// les cartes du joueur host
		Joueur j = londonG.partie.getJoueurParNom("host");
		int i=0;
		for(final Carte c: j.getLesCartes()){
			i++;
			final CarteActor ca = new CarteActor(c,350+i*50,10);	
			ca.addListener(new InputListener(){

				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {				
					if(ca.isSelected()){
						ca.setSelected(false);
						idCarteSelected=0;
					}else{
						ca.setSelected(true);
						idCarteSelected = c.getId_carte();
					}
					return super.touchDown(event, x, y, pointer, button);
				}

				@Override
				public boolean mouseMoved(InputEvent event, float x, float y) {
					idCarteOver = c.getId_carte();
					return true;
				}

			});
			main.put(c.getId_carte(), ca);
			stage.addActor(ca);
		}
		
		scores = new TableauScores(londonG.partie.getListeJoueurs());
		stage.addActor(scores);
		scoreJoueur = new Score(j);
		stage.addActor(scoreJoueur);

		
		btnSuivant =new TextButton("A moi",Buttons.styleInGameMenu); 
		btnSuivant.setPosition(600, 350); 
		btnSuivant.setVisible(false);
		btnSuivant.addListener(new InputListener(){
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				//traitement reseau
				// event ok client

				/*
				 * On lance le client
				 */
				Screen.setScreen(new GameScreenReseauServeur());
				super.touchUp(event, x, y, pointer, button);
			}

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}
		});
		stage.addActor(btnSuivant);
		
		
		// si c'est a moi
		if (GameScreenReseauServeur.joueur.getNom().equals(londonG.partie.getObjJoueurActif().getNom())) {
			//sauvegarde
			btnSauvegarde = new Button(Buttons.styleBtnSauvegarde);
			btnSauvegarde.setPosition(1250, 720); //changer la position
			btnSauvegarde.addListener(new InputListener(){

				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					return true;
				}

				@Override
				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {
					super.touchUp(event, x, y, pointer, button);
				}

			});
			stage.addActor(btnSauvegarde);

			/*Parametres Boutons d'action -> si le tour n'est pas terminé, on continue d'afficher actions*/
			if(!londonG.partie.isTourTermine()){
				MenuActions tableActions = new MenuActions();			
				stage.addActor(tableActions);
			}else{ /*sinon, on demande au joueur de confirmer qu'il a termine son tour*/
				finTourBtn = new Button(Buttons.styleBtnFinTour);
				finTourBtn.setPosition(700, 400); //changer la position
				finTourBtn.addListener(new InputListener(){


					@Override
					public void touchUp(InputEvent event, float x, float y,
							int pointer, int button) {
						//avant de finir le tour, on verifie la taille de la main
						Joueur j = londonG.partie.getObjJoueurActif();
						if(j.getMainDuJoueur().getNb_cartes()>Regles.NBMAXCARTES){
							int nbD = j.getMainDuJoueur().getNb_cartes()- Regles.NBMAXCARTES;
							londonG.setScreen(new DefausserScreen(j,nbD));
						}else{
							londonG.partie.joueurSuivant();	
							j = londonG.partie.getObjJoueurActif();


							// on envoie le nouvel objet au clients
							for (Emission e : Serveur.lesClients){		
								Partie partie = londonG.partie;
								e.sendObjectPartie(partie);
							}
							Screen.setScreen(new GameScreenReseauServeur());
						}
						super.touchUp(event, x, y, pointer, button);
					}

					@Override
					public boolean touchDown(InputEvent event, float x, float y,
							int pointer, int button) {
						return true;
					}

				});
				stage.addActor(finTourBtn);	
			}


		}







	}


	@Override
	public void render() {
		spriteBatch.begin();
		tick();

		draw(Art.bg, 0, 0);
		if (GameScreenReseauServeur.joueur.getNom().equals(londonG.partie.getObjJoueurActif().getNom())) {
			draw(Art.menu_bg,70,150);

			if(londonG.partie.isTourTermine()){
				draw(Art.finTour_bg,400,150);
			}else{
				draw(Art.action_bg,400,150);
			}
		}
		else {
			Fonts.FONT_TITLE.draw(spriteBatch, "Au tour de : "+londonG.partie.getObjJoueurActif().getNom(), 450, 300);
		}
		String msg = "COPYRIGHT Aethia 2014";
		drawString(msg, 2, 800 -6 -2);


		spriteBatch.end();
		if(idCarteSelected!=0){
			CarteActor c = main.get(idCarteSelected);
			if(c.isSelected()){
				c.setY(c.getyDefault()+300);
			}else{
				c.setDefaultPosition();
			}
		}else if(idCarteOver!=0){

			CarteActor c = main.get(idCarteOver);
			c.setY(c.getyDefault()+100);
			for(Integer key : main.keySet()){
				if(main.get(key)!=c){
					main.get(key).setDefaultPosition();
				}
			}
		}
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();

	}

	@Override
	public void tick() {
		time++;
		if (time > TIME_OUT_CARD && idCarteOver!=0) {
			main.get(idCarteOver).setDefaultPosition();
			idCarteOver=0;
			time=0;
		}

	}




}
