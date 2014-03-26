package fr.m1miage.london.game.screen;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

import fr.m1miage.london.London;
import fr.m1miage.london.classes.Quartier;
import fr.m1miage.london.game.LondonGame;
import fr.m1miage.london.game.form.QuartierForm;
import fr.m1miage.london.game.graphics.Art;
import fr.m1miage.london.game.graphics.Buttons;
import fr.m1miage.london.game.graphics.Fonts;

public class QuartiersScreen extends Screen{

	public static List<TextButton> listeQuartiers = new ArrayList<TextButton>();
	
	private static BitmapFont font;
	
	private static Integer nbQuartierSelected = 0;
	
	public QuartiersScreen(){
		listerQuartiers();
	}
	
	private void listerQuartiers(){
		/* Parametres Boutons */
		//changer position
		int marginLeft = 90;
		int marginTop = 70;
		int i=0;
		for(Integer q: LondonGame.quartiers.keySet()){
			final Integer j = q;
			if(i==10){marginLeft = 290; i=0;}
			TextButton btn= new TextButton(LondonGame.quartiers.get(q).getNom(),Buttons.styleInGameMenu); //** Button text and style **//
			btn.setPosition(marginLeft, marginTop+i*60); //** Button location **//
			btn.setHeight(50); //** Button Height **//
			btn.setWidth(190);
			btn.addListener(new InputListener(){

				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					// TODO Auto-generated method stub
					nbQuartierSelected = j;
					return super.touchDown(event, x, y, pointer, button);
				}
				
			});
			i++;
			listeQuartiers.add(btn);
			
		}
		
		
	}
	
	@Override
	public void render() {
		// TODO Auto-generated method stub
		spriteBatch.begin();
		
		
		
		draw(Art.bgPartie, 0, 0);
		draw(QuartierForm.quartier_bg,510,140);
		
		Fonts.FONT_TITLE.draw(spriteBatch, "QUARTIERS", 500, 20);
		
		if(nbQuartierSelected!=0){
			Quartier quartier = LondonGame.quartiers.get(nbQuartierSelected);
			Fonts.FONT_TITLE_QUARTIER.draw(spriteBatch, quartier.getNom(), 600, 170);
			
			//livres
			draw(QuartierForm.iconeLivres,600,400);
			Fonts.FONT_TITLE_QUARTIER.draw(spriteBatch, ": " + quartier.getPrix() + " � ", 680, 430);
			//points de victoire
			draw(QuartierForm.iconePV,600,480);
			Fonts.FONT_TITLE_QUARTIER.draw(spriteBatch, ": "+quartier.getPoint_victoire(), 680, 510);
			//cartes a piocher
			draw(QuartierForm.iconeCartePioche,600,560);
			Fonts.FONT_TITLE_QUARTIER.draw(spriteBatch, ": "+quartier.getNb_carte_a_piocher()+ " cartes � piocher", 680, 590);
		}
		spriteBatch.end();
		
		for(TextButton b : listeQuartiers){
			stage.addActor(b);
		}

		stage.act();
		stage.draw();
	}

}
