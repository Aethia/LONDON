package fr.m1miage.london.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;

import fr.m1miage.london.ui.Prefs;
import fr.m1miage.london.ui.graphics.Art;

public class ZoneConstructionScreen extends Screen{
	private Stage stage;
	
	public ZoneConstructionScreen(){
		stage = new Stage(Prefs.LARGEUR_FENETRE, Prefs.HAUTEUR_FENETRE, false); 
		stage.clear();
		Gdx.input.setInputProcessor(stage);
		
	}
	
	@Override
	public void render() {
		spriteBatch.begin();
		draw(Art.bgPartie, 0, 0);
		spriteBatch.end();
		
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

}
