package fr.m1miage.london.ui.graphics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

import fr.m1miage.london.ui.Prefs;
import fr.m1miage.london.ui.screens.GameScreen;
import fr.m1miage.london.ui.screens.Screen;



public class Buttons {
	/*----- boutons de style liste in game ------*/
	public static BitmapFont fontBtnGame;
	public static TextButtonStyle styleInGameMenu;
	public static TextButtonStyle styleInGameMenuDisabled;
	
	public static ButtonStyle styleBtnConstruire;
	public static ButtonStyle styleBtnRestaurer;
	public static ButtonStyle styleBtnInvestir;
	
	public static TextButtonStyle nbButtonStyle;
	
	public static TextButton button; 
	private static BitmapFont font;

	private static void boutonInvestir(){
		TextureAtlas btnAtlas = new TextureAtlas(Prefs.REPERTOIRE+"buttonInvestir.pack"); 
		Skin buttonSkin = new Skin();
		buttonSkin.addRegions(btnAtlas); 
		styleBtnInvestir = new ButtonStyle();
		styleBtnInvestir.up = buttonSkin.getDrawable("ButtonOff");
		styleBtnInvestir.down = buttonSkin.getDrawable("ButtonOn");
	}
	
	private static void boutonRestaurer(){
		TextureAtlas btnAtlas = new TextureAtlas(Prefs.REPERTOIRE+"buttonRestaurer.pack"); 
		Skin buttonSkin = new Skin();
		buttonSkin.addRegions(btnAtlas); 
		styleBtnRestaurer = new ButtonStyle();
		styleBtnRestaurer.up = buttonSkin.getDrawable("ButtonOff");
		styleBtnRestaurer.down = buttonSkin.getDrawable("ButtonOn");		
	}
	
	private static void boutonConstruire(){
		TextureAtlas btnAtlas = new TextureAtlas(Prefs.REPERTOIRE+"buttonConstruire.pack"); 
		Skin buttonSkin = new Skin();
		buttonSkin.addRegions(btnAtlas); 
		styleBtnConstruire = new ButtonStyle();
		styleBtnConstruire.up = buttonSkin.getDrawable("ButtonOff");
		styleBtnConstruire.down = buttonSkin.getDrawable("ButtonOn");
	}

	
	private static void boutonStyleNbJ(){
		TextureAtlas btnNbJoueursAtlas = new TextureAtlas("ressources/Images/nbJoueursBtn.pack"); //** button atlas image **//
		Skin buttonSkin = new Skin();
		buttonSkin.addRegions(btnNbJoueursAtlas); //** skins for on and off **//
		nbButtonStyle = new TextButtonStyle(); //** Button properties **//
		nbButtonStyle.up = buttonSkin.getDrawable("ButtonOff");
		nbButtonStyle.over = buttonSkin.getDrawable("ButtonOver");
		nbButtonStyle.down = buttonSkin.getDrawable("ButtonOn");
		nbButtonStyle.font = font;
		nbButtonStyle.fontColor = Color.BLACK;
	}
	
	private static void boutonStyleInGameDisabled(){
		TextureAtlas btnNbJoueursAtlas = new TextureAtlas("ressources/Images/btnMenu.pack");
		Skin buttonSkin = new Skin();
		buttonSkin.addRegions(btnNbJoueursAtlas);
		
		styleInGameMenuDisabled = new TextButtonStyle();
		styleInGameMenuDisabled.up = buttonSkin.getDrawable("ButtonDisabled");
		styleInGameMenuDisabled.over = buttonSkin.getDrawable("ButtonDisabled");
		styleInGameMenuDisabled.font = Buttons.fontBtnGame;
		styleInGameMenuDisabled.fontColor = Color.WHITE;
	}
	
	private static void boutonStyleInGame(){
		TextureAtlas btnNbJoueursAtlas = new TextureAtlas("ressources/Images/btnMenu.pack");
		Skin buttonSkin = new Skin();
		buttonSkin.addRegions(btnNbJoueursAtlas);
		
		styleInGameMenu = new TextButtonStyle();
		styleInGameMenu.up = buttonSkin.getDrawable("ButtonOff");
		styleInGameMenu.over = buttonSkin.getDrawable("ButtonOver");
		styleInGameMenu.font = Buttons.fontBtnGame;
		styleInGameMenu.fontColor = Color.WHITE;
	}
	
	

	private static void boutonChoixJoueur(){
		TextureAtlas buttonJoueursAtlas = new TextureAtlas("ressources/Images/buttonJoueurs.pack"); //** button atlas image **// 
		Skin buttonSkin = new Skin();
		buttonSkin.addRegions(buttonJoueursAtlas); //** skins for on and off **//        




		TextButtonStyle style = new TextButtonStyle(); //** Button properties **//
		style.up = buttonSkin.getDrawable("ButtonOff");
		style.over = buttonSkin.getDrawable("ButtonOn");
		style.down = buttonSkin.getDrawable("ButtonOn");
		style.font = font;
		button = new TextButton("", style); //** Button text and style **//

		button.setPosition(600, 120); //** Button location **//
		button.setHeight(430); //** Button Height **//
		button.setWidth(160); //** Button Width **//
		button.addListener(new InputListener() {

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				// TODO Auto-generated method stub
			//a dev
				return super.touchDown(event, x, y, pointer, button);
			}
		});

	}
	
	
	

	public static void load () {
		font = new BitmapFont(Gdx.files.internal("ressources/Fnt/arial.fnt")); //** font 

		fontBtnGame = new BitmapFont(Gdx.files.internal("ressources/Fnt/fnt1.fnt")); //** font 

		boutonChoixJoueur();
		boutonStyleInGame();
		boutonStyleInGameDisabled();
		boutonStyleNbJ();
		boutonConstruire();
		boutonRestaurer();
		

	}
}
