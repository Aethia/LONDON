package fr.m1miage.london.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

import fr.m1miage.london.game.screen.GameScreen;
import fr.m1miage.london.game.screen.Screen;

public class Buttons {

	
	
	//Bouton choix du skin joueur
	private static TextureAtlas buttonJoueursAtlas; //** image of buttons **//
	private static Skin buttonSkin; //** images are used as skins of the button **//
	public static TextButton button; //** the button - the only actor in program **//
	private static BitmapFont font; //** same as that used in Tut 7 **//
	
	//bouton nombre de joueurs
	private static TextureAtlas btnNbJoueursAtlas; //** image of buttons **//
	public static List<Button> buttonsNbj=new ArrayList<Button>() ; //** the button - the only actor in program **//
	
	public static void boutonNbJoueurs(){
		btnNbJoueursAtlas = new TextureAtlas("ressources/Images/nbJoueursBtn.pack"); //** button atlas image **//
		buttonSkin = new Skin();
	    buttonSkin.addRegions(btnNbJoueursAtlas); //** skins for on and off **//
	    
	    for(int i=0;i<5;i++){
	    	TextButtonStyle style = new TextButtonStyle(); //** Button properties **//
	        style.up = buttonSkin.getDrawable("Button1Off");
	        style.over = buttonSkin.getDrawable("Button1On");
	        style.down = buttonSkin.getDrawable("Button1On");
	        style.font = font;
	        Button b = new Button(style); //** Button text and style **//
	        b.setPosition(200, -100); //** Button location **//
	        b.setHeight(88); //** Button Height **//
	        b.setWidth(88); //** Button Width **//
	        buttonsNbj.add(b);
	    }
	    
	}


	public static void load () {
		  font = new BitmapFont(); //** font 
		boutonNbJoueurs();
		buttonJoueursAtlas = new TextureAtlas("ressources/Images/buttonJoueurs.pack"); //** button atlas image **// 
        buttonSkin = new Skin();
        buttonSkin.addRegions(buttonJoueursAtlas); //** skins for on and off **//        
      
        
       
        
        TextButtonStyle style = new TextButtonStyle(); //** Button properties **//
        style.up = buttonSkin.getDrawable("ButtonOff");
        style.over = buttonSkin.getDrawable("ButtonOn");
        style.down = buttonSkin.getDrawable("ButtonOn");
        style.font = font;
        button = new TextButton("", style); //** Button text and style **//
        
        button.setPosition(100, -100); //** Button location **//
        button.setHeight(700); //** Button Height **//
        button.setWidth(280); //** Button Width **//
        button.addListener(new InputListener() {

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				// TODO Auto-generated method stub
				System.err.println("ok");
				Screen.setScreen(new GameScreen());	
				return super.touchDown(event, x, y, pointer, button);
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				// TODO Auto-generated method stub						
			}
        	
        	
        });
        
        
        
		
		
        
	}
}
