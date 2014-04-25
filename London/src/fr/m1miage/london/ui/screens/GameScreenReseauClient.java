package fr.m1miage.london.ui.screens;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;

import fr.m1.miage.london.network.IncomingObjectListenerClient;
import fr.m1.miage.london.network.client.Reception;
import fr.m1.miage.london.network.client.Sender;
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

public class GameScreenReseauClient extends Screen{

	public IncomingObjectListenerClient partieObjListener = new IncomingObjectListenerClient(){

		@Override
		public void nouvelObjet(Object o, int type) {
			System.out.println("le client a recu une partie");
			Partie partieRecue =  (Partie)o;
			//si on est le nouveau joueur actif de la partie recue
			if(partieRecue.getObjJoueurActif().getNom().equals(GameScreenReseauClient.joueur.getNom())){
				GameScreenReseauClient.joueur = partieRecue.getObjJoueurActif();
				afficherBouton();
				afficherMain();
			}else{
				masquerBouton();
			}
			londonG.partie =  (Partie)o;

			stage.getRoot().removeActor(scores);
			scores = new TableauScores(londonG.partie.getListeJoueurs());
			stage.addActor(scores);
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
	public static Joueur joueur=null;

	private MenuGlobal tMenu;
	private MenuActions tableActions;

	private int time =0;
	private static final int TIME_OUT_CARD = 150;

	//	private TextButton btnSuivant;


	private void masquerBouton() {
		//btnSuivant.setVisible(true);
		tableActions.setVisible(false);
		tMenu.emprunterBtn.setVisible(false);
	}

	private void afficherBouton() {
		//btnSuivant.setVisible(true);
		tableActions.setVisible(true);
		tMenu.emprunterBtn.setVisible(true);
	}

	private void afficherMain(){
		//a ameliorer
		//maj de la main : on supprime d'abord celles qui exitent deja
		for(Integer id : main.keySet()){
			stage.getRoot().removeActor(main.get(id));
		}
		main.clear();
		// les cartes du joueur host
		Joueur j = GameScreenReseauClient.joueur;
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
	}

	public GameScreenReseauClient(Joueur joueur){
		GameScreenReseauClient.joueur = londonG.partie.getJoueurParNom(joueur.getNom());
		//this.init(londonG);
		Timer timer = new Timer();



		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				Reception.addListenerO(partieObjListener);
				//Reception.addListenerO(GameScreenReseauClient.game);
			}
		}, 2000);




		stage = new Stage(Prefs.LARGEUR_FENETRE, Prefs.HAUTEUR_FENETRE, false); 
		stage.clear();
		Gdx.input.setInputProcessor(stage);

		/* Parametres Boutons Menu General (menu de droite)*/
		tMenu = new MenuGlobal();
		stage.addActor(tMenu);

		afficherMain();

		scores = new TableauScores(londonG.partie.getListeJoueurs());
		stage.addActor(scores);
		scoreJoueur = new Score(GameScreenReseauClient.joueur);
		stage.addActor(scoreJoueur);
		//
		//		btnSuivant =new TextButton("A moi",Buttons.styleInGameMenu); 
		//		btnSuivant.setPosition(600, 350); 
		//		btnSuivant.setVisible(false);
		//		btnSuivant.addListener(new InputListener(){
		//			@Override
		//			public void touchUp(InputEvent event, float x, float y,
		//					int pointer, int button) {
		//				Screen.setScreen(new GameScreenReseauClient(GameScreenReseauClient.joueur));
		//				super.touchUp(event, x, y, pointer, button);
		//			}
		//
		//			@Override
		//			public boolean touchDown(InputEvent event, float x, float y,
		//					int pointer, int button) {
		//				return true;
		//			}
		//		});
		//		stage.addActor(btnSuivant);

		tableActions = new MenuActions()	;	
		tableActions.setVisible(false);
		stage.addActor(tableActions);

		// si c'est a moi
		if (GameScreenReseauClient.joueur.getNom().equals(londonG.partie.getObjJoueurActif().getNom())) {
			/*Parametres Boutons d'action -> si le tour n'est pas terminé, on continue d'afficher actions*/
			if(!londonG.partie.isTourTermine()){
				tableActions.setVisible(true);
			}else{ //sinon, on demande au joueur de confirmer qu'il a termine son tour
				tableActions.setVisible(false);
				finTourBtn = new Button(Buttons.styleBtnFinTour);
				finTourBtn.setPosition(700, 400); //changer la position
				finTourBtn.addListener(new InputListener(){


					@Override
					public void touchUp(InputEvent event, float x, float y,
							int pointer, int button) {
						//avant de finir le tour, on verifie la taille de la main
						Joueur j =GameScreenReseauClient.joueur;
						londonG.partie.setObjJoueurActif(GameScreenReseauClient.joueur);
						if(j.getMainDuJoueur().getNb_cartes()>Regles.NBMAXCARTES){
							int nbD = j.getMainDuJoueur().getNb_cartes()- Regles.NBMAXCARTES;
							londonG.setScreen(new DefausserScreen(j,nbD));
						}else{
							londonG.partie.joueurSuivant();	
							// on envoie le nouvel objet partie aux autres
							Sender.e.sendObject(5, londonG.partie);
							Screen.setScreen(new GameScreenReseauClient(GameScreenReseauClient.joueur));

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
		}else{//si c'est pas mon tour
			masquerBouton();
		}
	}


	@Override
	public void render() {
		spriteBatch.begin();
		tick();

		draw(Art.bg, 0, 0);
		if (GameScreenReseauClient.joueur.getNom().equals(londonG.partie.getObjJoueurActif().getNom())) {


			if(londonG.partie.isTourTermine()){
				draw(Art.finTour_bg,400,150);
			}else{
				draw(Art.action_bg,400,150);
			}
		}
		else {
			Fonts.FONT_TITLE.draw(spriteBatch, "Au tour de : "+londonG.partie.getObjJoueurActif().getNom(), 450, 300);
		}

		draw(Art.menu_bg,70,150);
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
