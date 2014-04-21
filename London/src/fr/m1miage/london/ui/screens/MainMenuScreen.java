package fr.m1miage.london.ui.screens;

import java.io.IOException;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import fr.m1miage.london.Partie;
import fr.m1miage.london.sound.SoundPlayer;
import fr.m1miage.london.ui.Prefs;
import fr.m1miage.london.ui.graphics.Art;
import fr.m1miage.london.ui.graphics.Buttons;

public class MainMenuScreen extends Screen {
	private static int selection=0;
	private Stage stage; 
	private Table tMenu;
	
	public MainMenuScreen(){
		
		/*if (!SoundPlayer.musique) {
			SoundPlayer.musique = true;
			SoundPlayer.jouerSon("menu.wav");
		}*/
		stage = new Stage(Prefs.LARGEUR_FENETRE, Prefs.HAUTEUR_FENETRE, false); 
		stage.clear();
		Gdx.input.setInputProcessor(stage);
		tMenu = new Table();
		tMenu.setPosition(705, 420);
		//tMenu.pad(40f);
		loadButtons();
		stage.addActor(tMenu);
		// la musique
		

	}
	
	private void loadButtons(){
		/* Parametres Boutons */
		TextButton nouvellePartie = new TextButton("Nouvelle partie",Buttons.styleInGameMenu); 
		nouvellePartie.addListener(new InputListener(){
			
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				SoundPlayer.jouerSon("clic.wav");
				Screen.setScreen(new CreationPartieScreen());
				super.touchUp(event, x, y, pointer, button);
			}

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}
			
		});
		tMenu.add(nouvellePartie).row().pad(20f);
		
		TextButton chargerPartie = new TextButton("Charger une partie",Buttons.styleInGameMenu);
		chargerPartie.addListener(new InputListener(){

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				try {

					londonG.partie = new Partie();
					
					londonG.partie.chargerPartie();
					Screen.setScreen(new GameScreen());
					System.out.println("gamescreen");
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				super.touchUp(event, x, y, pointer, button);
			}
			
		});
		tMenu.add(chargerPartie).row();
		
		
		//mode reseau
		TextButton reseauPartie = new TextButton("Jouer en réseau",Buttons.styleInGameMenu);
		reseauPartie.addListener(new InputListener(){

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				SoundPlayer.jouerSon("clic.wav");
				Screen.setScreen(new ChoixModeReseauScreen());
				super.touchUp(event, x, y, pointer, button);
			}
			
		});
		tMenu.add(reseauPartie).row().pad(20f);
		
		
		TextButton quitterJeu = new TextButton("Quitter",Buttons.styleInGameMenu);
		quitterJeu.addListener(new InputListener(){

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				selection = -1;
				SoundPlayer.jouerSon("clic.wav");
				super.touchUp(event, x, y, pointer, button);
			}

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}
			
		});
		
		tMenu.add(quitterJeu).row();
	}
	
	@Override
	public void render() {
		// TODO Auto-generated method stub
		if(selection == -1){Gdx.app.exit();};
		spriteBatch.begin();
		
		draw(Art.bg, 0, 0);
		draw(Art.menu_bg,575,200);
		spriteBatch.end();
		stage.act();
		stage.draw();
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

}
