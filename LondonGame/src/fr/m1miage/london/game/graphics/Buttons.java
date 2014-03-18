package fr.m1miage.london.game.graphics;

import java.util.ArrayList;
import java.util.List;

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


	public static TextButton button; //** the button - the only actor in program **//
	private static BitmapFont font; //** same as that used in Tut 7 **//


	public static TextButton[] buttonsNbj=new TextButton[4] ; //** the button - the only actor in program **//

	private static void boutonNbJoueurs(){
		TextureAtlas btnNbJoueursAtlas = new TextureAtlas("ressources/Images/nbJoueursBtn.pack"); //** button atlas image **//
		final Skin buttonSkin = new Skin();
		buttonSkin.addRegions(btnNbJoueursAtlas); //** skins for on and off **//
		int i=0;
		for(i=0;i<4;i++){

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
			buttonsNbj[i]= b;
		}

		for(i=0;i<4;i++){
			TextButton b = buttonsNbj[i];
			final int num = i;
			b.addListener(new InputListener(){
				
				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					// TODO Auto-generated method stub
					System.out.println("num : "+num + "checked : "+buttonsNbj[num].isChecked());
					if(!buttonsNbj[num].isChecked()){
						buttonsNbj[num].setChecked(true);
						System.out.println("dans le if :"+buttonsNbj[num].isChecked());
						System.out.println(""+num);
						buttonsNbj[num].getStyle().up = buttonSkin.getDrawable("ButtonOn");
						buttonsNbj[num].getStyle().over = buttonSkin.getDrawable("ButtonOn");
						//b.toggle();
						for(TextButton tb : buttonsNbj){
							if(tb!=buttonsNbj[num]){
								tb.getStyle().up = buttonSkin.getDrawable("ButtonOff");
								tb.getStyle().over = buttonSkin.getDrawable("ButtonOver");
							}
						}
					}else{
						buttonsNbj[num].setChecked(true);
					}
					return false;
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
		});

	}


	public static void load () {
		font = new BitmapFont(Gdx.files.internal("ressources/Fnt/arial.fnt")); //** font 



		boutonNbJoueurs();
		boutonChoixJoueur();





	}
}
