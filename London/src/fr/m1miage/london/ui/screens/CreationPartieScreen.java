package fr.m1miage.london.ui.screens;

import java.awt.TextArea;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

import fr.m1miage.london.ui.Prefs;
import fr.m1miage.london.ui.graphics.Art;
import fr.m1miage.london.ui.graphics.Buttons;
import fr.m1miage.london.ui.graphics.Fonts;



public class CreationPartieScreen extends Screen {
	private BitmapFont font;
	Skin skin;

	private  TextField mTextField;
	public CreationPartieScreen(){
		stage = new Stage(Prefs.LARGEUR_FENETRE, Prefs.HAUTEUR_FENETRE, false); 
		stage.clear();
		Gdx.input.setInputProcessor(stage);
		
		font=Fonts.GLOBAL_FONT;
		font.setColor(Color.BLACK);
		//	 NinePatch patch = new NinePatch(new Texture(Gdx.files.internal("ressources/Images/text_area.png")), 16, 16, 16, 16);
		
		Skin menuSkin = new Skin();
				TextureAtlas menuAtlas = new TextureAtlas("ressources/Images/text_area.pack");
		
		        menuSkin.addRegions(menuAtlas);
				 TextFieldStyle txtStyle = new TextFieldStyle();
		    
			        txtStyle.background = menuSkin.getDrawable("Area");
						        txtStyle.font = new BitmapFont(Gdx.files.internal("ressources/Fnt/arial.fnt"), false);;
						        txtStyle.fontColor = Color.BLACK;
		mTextField = new TextField("", txtStyle);

		mTextField.setPosition(500, 500);
		mTextField.setHeight(100);
		mTextField.setWidth(150);
		

if(stage==null){
	System.out.println("lu");
}else{
		stage.addActor(mTextField);
}
		System.out.println("d" + mTextField.isDisabled());
		System.out.println("v"+mTextField.isVisible());
		
		
		//		test = new TextField("t", txtStyle);
		//		//
		//		test.setPosition(500, 500);
		//		test.setMessageText("WTFFFFFFFF");
		//	
		//		test.setTextFieldListener(new TextFieldListener() {
		//		
		//			@Override
		//			public void keyTyped(TextField textField, char key) {
		//				// TODO Auto-generated method stub
		//				Gdx.app.log("Skillaria", "" + key);
		//				System.out.println("wtf");
		//			}
		//		});

	}


	@Override
	public void render() {
		// TODO Auto-generated method stub
		spriteBatch.begin();
		draw(Art.bgPartie, 0, 0);
		Fonts.FONT_TITLE.draw(spriteBatch, "Nouvelle partie", 450, 20);
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
		Table.drawDebug(stage);
	}






}
