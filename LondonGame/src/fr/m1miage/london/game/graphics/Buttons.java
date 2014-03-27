package fr.m1miage.london.game.graphics;

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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

import fr.m1miage.london.game.screen.GameScreen;
import fr.m1miage.london.game.screen.Screen;

public class Buttons {
	/*----- boutons de style liste in game ------*/
	public static BitmapFont fontBtnGame;
	public static TextButtonStyle styleInGameMenu;

	public static TextButtonStyle styleInGameMenuDisabled;
	
	public static TextButton button; //** the button - the only actor in program **//
	private static BitmapFont font; //** same as that used in Tut 7 **//


	public static Map<Integer,TextButton> buttonsNbj=new HashMap<Integer,TextButton>() ; //boutons nombre de joueurs
	public static int idBtnSelected=0;
	
	public static TextButton btnValider;
	
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
	
	private static void boutonNbJoueurs(){
		TextureAtlas btnNbJoueursAtlas = new TextureAtlas("ressources/Images/nbJoueursBtn.pack"); //** button atlas image **//
		final Skin buttonSkin = new Skin();
		buttonSkin.addRegions(btnNbJoueursAtlas); //** skins for on and off **//
		int i;
		for(i=2;i<6;i++){

			final TextButtonStyle style = new TextButtonStyle(); //** Button properties **//
			style.up = buttonSkin.getDrawable("ButtonOff");
			style.over = buttonSkin.getDrawable("ButtonOver");
			style.down = buttonSkin.getDrawable("ButtonOn");
			style.font = font;
			style.fontColor = Color.BLACK;
			TextButton b = new TextButton(""+i,style); //** Button text and style **//
			b.setPosition(450+(i*100), 575); //** Button location **//
			b.setHeight(75); //** Button Height **//
			b.setWidth(75); //** Button Width **//
			buttonsNbj.put(i, b);
		}
		//pour chaque bouton, on va attribuer un listener
		for(i=2;i<6;i++){
			final int num = i;
			buttonsNbj.get(i).addListener(new InputListener(){

				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					// TODO Auto-generated method stub
					TextButton btnSelected  = buttonsNbj.get(num);
					//aucun de deja selectionné
					if(idBtnSelected!=num && idBtnSelected!=0){ //deselectionner l'ancien
						
						TextButton btnDeselect = buttonsNbj.get(idBtnSelected);
						btnDeselect.getStyle().up = buttonSkin.getDrawable("ButtonOff");
						btnDeselect.getStyle().over = buttonSkin.getDrawable("ButtonOver");
					}
					idBtnSelected=num;
					btnSelected.getStyle().up = buttonSkin.getDrawable("ButtonOn");
					btnSelected.getStyle().over = buttonSkin.getDrawable("ButtonOn");
					System.out.println("Bouton selectionné : " + idBtnSelected);
					return true;
				}				

			});
		}

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
	
	
	private static void boutonValider(){
		TextureAtlas buttonJoueursAtlas = new TextureAtlas("ressources/Images/buttonValider.pack"); //** button atlas image **// 
		Skin buttonSkin = new Skin();
		buttonSkin.addRegions(buttonJoueursAtlas); //** skins for on and off **//        
		TextButtonStyle style = new TextButtonStyle(); //** Button properties **//
		style.up = buttonSkin.getDrawable("ButtonOff");
		style.font = font;
		
		btnValider = new TextButton("", style); //** Button text and style **//

		btnValider.setPosition(600, -150); //** Button location **//
		btnValider.setHeight(256); //** Button Height **//
		btnValider.setWidth(256); //** Button Width **//
		btnValider.addListener(new InputListener() {

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				// TODO Auto-generated method stub
				System.err.println("ok");
				Screen.setScreen(new GameScreen());	
				return super.touchDown(event, x, y, pointer, button);
			}
		});
	}

	public static void load () {
		font = new BitmapFont(Gdx.files.internal("ressources/Fnt/arial.fnt")); //** font 

		fontBtnGame = new BitmapFont(Gdx.files.internal("ressources/Fnt/fnt1.fnt")); //** font 

		boutonNbJoueurs();
		boutonChoixJoueur();
		boutonValider();
		boutonStyleInGame();
		boutonStyleInGameDisabled();


	}
}
