package fr.m1miage.london.game.form;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

import fr.m1miage.london.game.graphics.Art;
import fr.m1miage.london.game.graphics.Buttons;
import fr.m1miage.london.game.screen.QuartiersScreen;
import fr.m1miage.london.game.screen.Screen;

public class MenuForm {

	public static TextureRegion menu_bg;
	/*Boutons*/
	public static TextButton zoneConstructionBtn;
	public static TextButton etalageCartesBtn;
	public static TextButton quartiersBtn;
	public static TextButton emprunterBtn;
	private static int distBtn = 80;
	private static int top = 540;

	private static BitmapFont font; //** same as that used in Tut 7 **//

	public static void load(){
		font = new BitmapFont(Gdx.files.internal("ressources/Fnt/fnt1.fnt")); //** font 
		//background
		menu_bg = Art.load("ressources/Images/menu_background.png", 256, 512);

		/* Parametres Boutons */
		TextureAtlas btnNbJoueursAtlas = new TextureAtlas("ressources/Images/btnMenu.pack");
		Skin buttonSkin = new Skin();
		buttonSkin.addRegions(btnNbJoueursAtlas);
		TextButtonStyle style = new TextButtonStyle(); //** Button properties **//
		style.up = buttonSkin.getDrawable("ButtonOff");
		style.over = buttonSkin.getDrawable("ButtonOver");
		style.font = font;
		style.fontColor = Color.WHITE;


		zoneConstructionBtn = new TextButton("Zone de construction",style); //** Button text and style **//
		zoneConstructionBtn.setPosition(90, top); //** Button location **//
		sizeOfButton(zoneConstructionBtn);

		etalageCartesBtn = new TextButton("Etalage de cartes",style); //** Button text and style **//
		etalageCartesBtn.setPosition(90, (top-distBtn)); //** Button location **//
		sizeOfButton(etalageCartesBtn);

		quartiersBtn = new TextButton("Quartiers",style); //** Button text and style **//
		quartiersBtn.setPosition(90, (top-distBtn*2)); //** Button location **//
		sizeOfButton(quartiersBtn);
		quartiersBtn.addListener(new InputListener(

				){

			@Override
			public boolean touchDown(InputEvent event, float x,
					float y, int pointer, int button) {
				// TODO Auto-generated method stub
				Screen.setScreen(new QuartiersScreen());
				return super.touchDown(event, x, y, pointer, button);
			}});

		emprunterBtn = new TextButton("Emprunter",style); //** Button text and style **//
		emprunterBtn.setPosition(90, (top-distBtn*3)); //** Button location **//
		sizeOfButton(emprunterBtn);

	}

	private static void sizeOfButton(TextButton btn){
		btn.setHeight(60); //** Button Height **//
		btn.setWidth(215); //** Button Width **//
	}
}
