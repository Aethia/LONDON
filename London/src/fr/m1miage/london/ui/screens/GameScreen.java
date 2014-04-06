package fr.m1miage.london.ui.screens;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import fr.m1miage.london.classes.Carte;
import fr.m1miage.london.classes.Joueur;
import fr.m1miage.london.ui.Prefs;
import fr.m1miage.london.ui.graphics.Art;
import fr.m1miage.london.ui.graphics.Buttons;
import fr.m1miage.london.ui.graphics.CarteActor;
import fr.m1miage.london.ui.graphics.Score;

public class GameScreen extends Screen{

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
	public static int idCarteSelected=0;
	public static Map<Integer, CarteActor> main = new HashMap<Integer,CarteActor>();
	public static int idCarteOver =0;

	/* Scores */
	private Score scoreJoueur;

	private Stage stage; 

	private int time =0;
	private static final int TIME_OUT_CARD = 150;

	public GameScreen(){
		stage = new Stage(Prefs.LARGEUR_FENETRE, Prefs.HAUTEUR_FENETRE, false); 
		stage.clear();
		Gdx.input.setInputProcessor(stage);
		/*Parametres Boutons d'action -> si le tour n'est pas termin�, on continue d'afficher actions*/
		if(!londonG.partie.isTourTermine()){
			Table tableActions = new Table();
			tableActions.setPosition(785, 525);
			construireBtn = new Button(Buttons.styleBtnConstruire);
			tableActions.add(construireBtn);

			restaurerBtn = new Button(Buttons.styleBtnRestaurer);
			tableActions.add(restaurerBtn);

			investirBtn = new Button(Buttons.styleBtnInvestir);
			tableActions.add(investirBtn);

			piocherBtn = new Button(Buttons.styleBtnPiocher);
			tableActions.add(piocherBtn);

			tableActions.pad(30f);		
			stage.addActor(tableActions);
		}else{ /*sinon, on demande au joueur de confirmer qu'il a termin� son tour*/
			finTourBtn = new Button(Buttons.styleBtnFinTour);
			finTourBtn.setPosition(500, 500); //changer la position
			stage.addActor(finTourBtn);	
		}

		/* Parametres Boutons Menu General*/
		//faire une classe du menu ?
		Table tMenu = new Table();
		
		zoneConstructionBtn = new TextButton("Zone de construction",Buttons.styleInGameMenu);
		zoneConstructionBtn.addListener(new InputListener(){

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				Screen.setScreen(new ZoneConstructionScreen());
				return super.touchDown(event, x, y, pointer, button);
			}

		});
		tMenu.add(zoneConstructionBtn).row().padTop(20f);

		etalageCartesBtn = new TextButton("Etalage de cartes",Buttons.styleInGameMenu); //** Button text and style **//
		tMenu.add(etalageCartesBtn).row().padTop(20f);
		
		quartiersBtn = new TextButton("Quartiers",Buttons.styleInGameMenu); //** Button text and style **//
		quartiersBtn.addListener(new InputListener(

				){

			@Override
			public boolean touchDown(InputEvent event, float x,
					float y, int pointer, int button) {
				Screen.setScreen(new QuartiersScreen());
				return super.touchDown(event, x, y, pointer, button);
			}});
		tMenu.add(quartiersBtn).row().padTop(20f);

		
		emprunterBtn = new TextButton("Emprunter",Buttons.styleInGameMenu); //** Button text and style **//
		emprunterBtn.addListener(new InputListener(){

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				Screen.setScreen(new EmprunterScreen());
				return super.touchDown(event, x, y, pointer, button);
			}

		});
		tMenu.add(emprunterBtn).row();

		tMenu.setPosition(200, 460);
		
		stage.addActor(tMenu);

		//a ameliorer
		Joueur j = londonG.partie.getObjJoueurActif();
		int i=0;
		for(Carte c: j.getLesCartes()){
			i++;
			//	main.add(new CarteActor(c));
			CarteActor ca = new CarteActor(c,350+i*80,10);	

			main.put(c.getId_carte(), ca);
			stage.addActor(ca);
		}

		scoreJoueur = new Score(j);
		stage.addActor(scoreJoueur);
	}


	@Override
	public void render() {
		spriteBatch.begin();
		tick();
		draw(Art.bgPartie, 0, 0);
		draw(Art.menu_bg,70,150);
		draw(Art.action_bg,400,150);

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
