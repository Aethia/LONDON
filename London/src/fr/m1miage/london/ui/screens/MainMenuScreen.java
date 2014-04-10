package fr.m1miage.london.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import fr.m1miage.london.ui.Prefs;
import fr.m1miage.london.ui.graphics.Art;
import fr.m1miage.london.ui.graphics.Buttons;

public class MainMenuScreen extends Screen {
	private static TextButton nouvellePartie;
	private static TextButton chargerPartie;
	private static TextButton quitterJeu;
	
	private static int selection=0;
	private Stage stage; 
	
	public MainMenuScreen(){
		loadButtons();
		stage = new Stage(Prefs.LARGEUR_FENETRE, Prefs.HAUTEUR_FENETRE, false); 
		stage.clear();
		Gdx.input.setInputProcessor(stage);
		
	}
	
	private static void loadButtons(){
		/* Parametres Boutons */
		nouvellePartie = new TextButton("Nouvelle partie",Buttons.styleInGameMenu); 
		nouvellePartie.setPosition(600, 510); 
		nouvellePartie.addListener(new InputListener(){
			
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				Screen.setScreen(new CreationPartieScreen());
				super.touchUp(event, x, y, pointer, button);
			}

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}
			
		});
		chargerPartie = new TextButton("Charger une partie",Buttons.styleInGameMenuDisabled);
		chargerPartie.setPosition(600, 435); 
		
		quitterJeu = new TextButton("Quitter",Buttons.styleInGameMenu);
		quitterJeu.setPosition(600, 235); 
		quitterJeu.addListener(new InputListener(){

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				selection = -1;
				super.touchUp(event, x, y, pointer, button);
			}

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}
			
		});
		
	}
	
	@Override
	public void render() {
		// TODO Auto-generated method stub
		if(selection == -1){Gdx.app.exit();};
		spriteBatch.begin();
		
		draw(Art.bg, 0, 0);
		draw(Art.menu_bg,575,200);
		spriteBatch.end();
		stage.addActor(nouvellePartie);
		stage.addActor(chargerPartie);
		stage.addActor(quitterJeu);
		stage.act();
		stage.draw();
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

}
