package fr.m1miage.london.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;


import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

import fr.m1miage.london.game.Art;



public class MainMenuScreen extends Screen {
	
	
	@Override
	public void render() {
		// TODO Auto-generated method stub
		spriteBatch.begin();
		draw(Art.bg, 0, 0);
		drawString("Choisissez votre joueur", 550, 10);
		//draw(Art.player1Button, 200, 50);
		
		
		
		String msg = "COPYRIGHT Aethia 2014";
		drawString(msg, 2, 720 -6 -2);

		spriteBatch.end();
	}

	
	
	


}
