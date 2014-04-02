package fr.m1miage.london.ui.screens;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import fr.m1miage.london.ui.LondonGame;
import fr.m1miage.london.ui.Prefs;
import fr.m1miage.london.ui.graphics.Art;
import fr.m1miage.london.ui.graphics.Buttons;
import fr.m1miage.london.ui.graphics.CarteActor;
import fr.m1miage.london.ui.graphics.Fonts;

public class GameScreen extends Screen{

	/*Boutons du menu*/
	public static TextButton zoneConstructionBtn;
	public static TextButton etalageCartesBtn;
	public static TextButton quartiersBtn;
	public static TextButton emprunterBtn;

	private static int distBtn = 80;
	private static int top = 540;
	
	/* Boutons des actions */
	private static Button construireBtn;
	private static Button restaurerBtn;
	private static Button investirBtn;
	private static Button piocherBtn;

	/* Main du joueur */
	public static int idCarteSelected=0;
	public static Map<Integer, CarteActor> main = new HashMap<Integer,CarteActor>();
	public static int idCarteOver =0;

	private Stage stage; 

	private int time =0;
	private static final int TIME_OUT_CARD = 150;

	public GameScreen(){
		stage = new Stage(Prefs.LARGEUR_FENETRE, Prefs.HAUTEUR_FENETRE, false); 
		stage.clear();
		Gdx.input.setInputProcessor(stage);
		/*Parametres Boutons d'action*/
		Table tableActions = new Table();
		tableActions.setPosition(785, 525);
		construireBtn = new Button(Buttons.styleBtnConstruire);
		construireBtn.size(170,170);
		tableActions.add(construireBtn);
		
		restaurerBtn = new Button(Buttons.styleBtnRestaurer);
		restaurerBtn.size(170,170);
		tableActions.add(restaurerBtn);
		
		investirBtn = new Button(Buttons.styleBtnInvestir);
		investirBtn.size(170,170);
		tableActions.add(investirBtn);
		
		piocherBtn = new Button(Buttons.styleBtnPiocher);
		piocherBtn.size(170,170);
		tableActions.add(piocherBtn);
		
		tableActions.pad(30f);		
		stage.addActor(tableActions);
		
		/* Parametres Boutons Menu General*/
		zoneConstructionBtn = new TextButton("Zone de construction",Buttons.styleInGameMenu);
		zoneConstructionBtn.setPosition(90, top); 
		sizeOfButton(zoneConstructionBtn);
		zoneConstructionBtn.addListener(new InputListener(){

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				Screen.setScreen(new ZoneConstructionScreen());
				return super.touchDown(event, x, y, pointer, button);
			}
			
		});

		etalageCartesBtn = new TextButton("Etalage de cartes",Buttons.styleInGameMenu); //** Button text and style **//
		etalageCartesBtn.setPosition(90, (top-distBtn)); //** Button location **//
		sizeOfButton(etalageCartesBtn);

		quartiersBtn = new TextButton("Quartiers",Buttons.styleInGameMenu); //** Button text and style **//
		quartiersBtn.setPosition(90, (top-distBtn*2)); //** Button location **//
		sizeOfButton(quartiersBtn);
		quartiersBtn.setHeight(60); //** Button Height **//
		quartiersBtn.setWidth(215);
		quartiersBtn.addListener(new InputListener(

				){

			@Override
			public boolean touchDown(InputEvent event, float x,
					float y, int pointer, int button) {
				// TODO Auto-generated method stub
				Screen.setScreen(new QuartiersScreen());
				return super.touchDown(event, x, y, pointer, button);
			}});

		emprunterBtn = new TextButton("Emprunter",Buttons.styleInGameMenu); //** Button text and style **//
		emprunterBtn.setPosition(90, (top-distBtn*3)); //** Button location **//
		sizeOfButton(emprunterBtn);

		stage.addActor(zoneConstructionBtn);
		stage.addActor(etalageCartesBtn);
		stage.addActor(quartiersBtn);
		stage.addActor(emprunterBtn);
		
		
		//a ameliorer
		Joueur j = LondonGame.partie.getObjJoueurActif();
		int i=0;
		for(Carte c: j.getLesCartes()){
			i++;
			//	main.add(new CarteActor(c));
			CarteActor ca = new CarteActor(c,350+i*80,10);	

			main.put(c.getId_carte(), ca);
			stage.addActor(ca);
		}
	}

	private static void sizeOfButton(TextButton btn){
		btn.setHeight(60); //** Button Height **//
		btn.setWidth(215); //** Button Width **//
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		spriteBatch.begin();
		tick();
		draw(Art.bgPartie, 0, 0);
		draw(Art.menu_bg,70,150);
		draw(Art.action_bg,400,150);


		Fonts.FONT_ICON_WHITE.draw(spriteBatch,LondonGame.partie.getObjJoueurActif().getNom(), 70, 150);
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
		stage.act();
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
