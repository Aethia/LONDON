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
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import fr.m1.miage.london.network.IncomingMessageListenerClient;
import fr.m1.miage.london.network.IncomingObjectListenerClient;
import fr.m1.miage.london.network.IncomingPartieObjectListenerClient;
import fr.m1.miage.london.network.RegisterClient;
import fr.m1.miage.london.network.client.Reception;
import fr.m1.miage.london.network.client.Sender;
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
import fr.m1miage.london.ui.graphics.Score;
import fr.m1miage.london.ui.graphics.TableauScores;

public class GameScreenReseauClient extends Screen implements IncomingPartieObjectListenerClient{

	/*Boutons du menu*/
	public TextButton zoneConstructionBtn;
	public TextButton etalageCartesBtn;
	public TextButton quartiersBtn;
	public TextButton emprunterBtn;


	/* Boutons des actions */
	private Button construireBtn;
	private Button restaurerBtn;
	private Button investirBtn;
	private Button piocherBtn;
	private Button finTourBtn;

	/* Main du joueur */
	public int idCarteSelected=0;
	public Map<Integer, CarteActor> main = new HashMap<Integer,CarteActor>();
	public int idCarteOver =0;

	/* Scores */
	private Score scoreJoueur;
	private TableauScores scores;

	private Stage stage; 
	private String login;
	public static String log;

	private int time =0;
	private static final int TIME_OUT_CARD = 150;
	
	public static Button btnSauvegarde;
	private String joueurActif;
	
	public static GameScreenReseauClient game;
	
	@Override
	// arrivée d'un nouvel objet de type Partie
	public void nouvelObjet(Object o) {
		londonG.partie = (Partie)o;
		londonG.partie.joueurSuivant();
		Screen.setScreen(new GameScreenReseauClient(login,londonG.partie.getObjJoueurActif().getNom()));	
		
	}
	
	public GameScreenReseauClient(String login, String joueurActif){
		this.game = this;
		
		Timer timer = new Timer();
		
		
		
		timer.schedule(new TimerTask() {
			  @Override
			  public void run() {
				  Reception.addListenerPartie(GameScreenReseauClient.game);
			  }
			}, 5000);
		
		
		
		this.login = login;
		this.log = login;
		this.joueurActif = joueurActif;
		stage = new Stage(Prefs.LARGEUR_FENETRE, Prefs.HAUTEUR_FENETRE, false); 
		stage.clear();
		Gdx.input.setInputProcessor(stage);
		
		// si c'est a moi
		if (this.login.equalsIgnoreCase(this.joueurActif)) {
			
		
		
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
			Table tableActions = new Table();
			tableActions.setPosition(780, 485);
			construireBtn = new Button(Buttons.styleBtnConstruire);
			construireBtn.addListener(new InputListener(){

				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					return true;
				}

				@Override
				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {
					Screen.setScreen(new ZoneConstructionScreen("Choisir une carte",GameScreenReseauClient.log,londonG.partie.getObjJoueurActif().getNom(),"client"));
					super.touchUp(event, x, y, pointer, button);
				}

			});

			tableActions.add(construireBtn);

			restaurerBtn = new Button(Buttons.styleBtnRestaurer);
			tableActions.add(restaurerBtn);

			investirBtn = new Button(Buttons.styleBtnInvestir);
			investirBtn.addListener(new InputListener(){

				@Override
				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {
					Screen.setScreen(new QuartiersScreen(GameScreenReseauClient.log,londonG.partie.getObjJoueurActif().getNom(),"client"));
					super.touchUp(event, x, y, pointer, button);
				}

				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					return true;
				}

			});
			tableActions.add(investirBtn);

			piocherBtn = new Button(Buttons.styleBtnPiocher);
			piocherBtn.addListener(new InputListener(){

				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					return true;
				}

				@Override
				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {
					Screen.setScreen(new EtalageScreen(true,GameScreenReseauClient.log,londonG.partie.getObjJoueurActif().getNom(),"client"));
					super.touchUp(event, x, y, pointer, button);
				}
				
			});
			tableActions.add(piocherBtn);

			tableActions.pad(30f);		
			stage.addActor(tableActions);
		}else{ //sinon, on demande au joueur de confirmer qu'il a termine son tour
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
						// on envoie le nouvel objet partie aux autres
						Sender.e.sendObject(5, londonG.partie);
						Screen.setScreen(new GameScreenReseauClient(GameScreenReseauClient.log,j.getNom()));
						
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

		/* Parametres Boutons Menu General*/
		//faire une classe du menu ?
		Table tMenu = new Table();

		zoneConstructionBtn = new TextButton("Zone de construction",Buttons.styleInGameMenu);
		zoneConstructionBtn.addListener(new InputListener(){

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				Screen.setScreen(new ZoneConstructionScreen());
			}

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

		});
		tMenu.add(zoneConstructionBtn).row().padTop(20f);

		etalageCartesBtn = new TextButton("Etalage de cartes",Buttons.styleInGameMenu);
		etalageCartesBtn.addListener(new InputListener(){

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				Screen.setScreen(new EtalageScreen(false));
				super.touchUp(event, x, y, pointer, button);
			}
			
		});
		tMenu.add(etalageCartesBtn).row().padTop(20f);
		

		quartiersBtn = new TextButton("Quartiers",Buttons.styleInGameMenu); 
		
		quartiersBtn.addListener(new InputListener(){

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				Screen.setScreen(new QuartiersScreen());
				super.touchUp(event, x, y, pointer, button);
			}

			@Override
			public boolean touchDown(InputEvent event, float x,
					float y, int pointer, int button) {

				return true;
			}});
		tMenu.add(quartiersBtn).row().padTop(20f);


		emprunterBtn = new TextButton("Emprunter",Buttons.styleInGameMenu); //** Button text and style **//
		emprunterBtn.addListener(new InputListener(){

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				Screen.setScreen(new EmprunterScreen());
				super.touchUp(event, x, y, pointer, button);
			}

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

		});
		tMenu.add(emprunterBtn).row();

		tMenu.setPosition(200, 460);

		stage.addActor(tMenu);

		
		
		//a ameliorer
		// les cartes du joueur host
		Joueur j = londonG.partie.getJoueurParNom(login);
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
		scoreJoueur = new Score(londonG.partie.getJoueurParNom(login));
		stage.addActor(scoreJoueur);
		}
		else {
			
		}
	}


	@Override
	public void render() {
		spriteBatch.begin();
		tick();
		
		draw(Art.bg, 0, 0);
		if (this.login.equalsIgnoreCase(this.joueurActif)) {
		draw(Art.menu_bg,70,150);
		
			if(londonG.partie.isTourTermine()){
				draw(Art.finTour_bg,400,150);
			}else{
				draw(Art.action_bg,400,150);
			}
		}
		else {
			Fonts.FONT_TITLE.draw(spriteBatch, "Au tour de : "+this.joueurActif, 450, 300);
		}


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
