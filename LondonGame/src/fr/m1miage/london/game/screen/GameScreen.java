package fr.m1miage.london.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import fr.m1miage.london.game.graphics.Art;
import fr.m1miage.london.game.graphics.Buttons;
import fr.m1miage.london.game.graphics.Prefs;

public class GameScreen extends Screen{

	/*Boutons*/
	public static TextButton zoneConstructionBtn;
	public static TextButton etalageCartesBtn;
	public static TextButton quartiersBtn;
	public static TextButton emprunterBtn;

	private static int distBtn = 80;
	private static int top = 540;

	public GameScreen(){
		stage = new Stage(Prefs.LARGEUR_FENETRE, Prefs.HAUTEUR_FENETRE, false); 
		stage.clear();
		Gdx.input.setInputProcessor(stage);
		/* Parametres Boutons */
		zoneConstructionBtn = new TextButton("Zone de construction",Buttons.styleInGameMenu); //** Button text and style **//
		zoneConstructionBtn.setPosition(90, top); //** Button location **//
		sizeOfButton(zoneConstructionBtn);

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



	}

	private static void sizeOfButton(TextButton btn){
		btn.setHeight(60); //** Button Height **//
		btn.setWidth(215); //** Button Width **//
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		spriteBatch.begin();

		draw(Art.bgPartie, 0, 0);
		draw(Art.menu_bg,70,150);
		draw(Art.plateau_jeu_test,350,50);


		String msg = "COPYRIGHT Aethia 2014";
		drawString(msg, 2, 800 -6 -2);


		spriteBatch.end();
		stage.addActor(zoneConstructionBtn);
		stage.addActor(etalageCartesBtn);
		stage.addActor(quartiersBtn);
		stage.addActor(emprunterBtn);
		stage.act();
		stage.draw();

	}

}
