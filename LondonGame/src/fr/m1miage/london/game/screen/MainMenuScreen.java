package fr.m1miage.london.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

import fr.m1miage.london.game.graphics.Art;
import fr.m1miage.london.game.graphics.Buttons;
import fr.m1miage.london.game.graphics.Fonts;



public class MainMenuScreen extends Screen {
	private BitmapFont font;
	
	public MainMenuScreen(){
		font=Fonts.GLOBAL_FONT;
		font.setColor(Color.BLACK);
	}
	
	
	@Override
	public void render() {
		// TODO Auto-generated method stub
		spriteBatch.begin();
		
		draw(Art.bg, 0, 0);

		font.draw(spriteBatch, "Nombre de joueurs :", 125, 170);
		font.draw(spriteBatch, "Choix du skin/couleur :", 125, 400);
		String msg = "COPYRIGHT Aethia 2014";
		drawString(msg, 2, 800 -6 -2);
		
		
		
		spriteBatch.end();
		
		stage.addActor(Buttons.button);
		for(Integer mapKey : Buttons.buttonsNbj.keySet()){
			stage.addActor(Buttons.buttonsNbj.get(mapKey));
		}
		stage.addActor(Buttons.btnValider);
		stage.act();
		stage.draw();
	}

	
	
	


}
