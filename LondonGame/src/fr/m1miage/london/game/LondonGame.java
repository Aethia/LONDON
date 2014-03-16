package fr.m1miage.london.game;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

import fr.m1miage.london.game.screen.MainMenuScreen;
import fr.m1miage.london.game.screen.Screen;

public class LondonGame implements ApplicationListener {
	
	private Screen screen;
	
	
	private Stage stage; //** stage holds the Button **//
	
	private static TextureAtlas button1Atlas; //** image of buttons **//
	private static Skin buttonSkin; //** images are used as skins of the button **//
	public static TextButton button; //** the button - the only actor in program **//
	private BitmapFont font; //** same as that used in Tut 7 **//
	
	
	@Override
	public void create() {		
		Art.load();
		
		
		
		
		setScreen(new MainMenuScreen());
		
		
		//test bouton
				button1Atlas = new TextureAtlas("ressources/Images/button1.pack"); //** button atlas image **// 
		        buttonSkin = new Skin();
		        buttonSkin.addRegions(button1Atlas); //** skins for on and off **//        
		        font = new BitmapFont(); //** font 
		        
		        stage = new Stage(1280, 720, true); 
				stage.clear();
		        Gdx.input.setInputProcessor(stage); //** stage is responsive **//
		        
		        TextButtonStyle style = new TextButtonStyle(); //** Button properties **//
		        style.up = buttonSkin.getDrawable("ButtonOff");
		        style.down = buttonSkin.getDrawable("ButtonOn");
		        style.font = font;
		        button = new TextButton("", style); //** Button text and style **//
		        
		        button.setPosition(100, -20); //** Button location **//
		        button.setHeight(700); //** Button Height **//
		        button.setWidth(280); //** Button Width **//
		        button.addListener(new InputListener() {

					@Override
					public boolean touchDown(InputEvent event, float x, float y,
							int pointer, int button) {
						// TODO Auto-generated method stub
						System.err.println("ok");
						return super.touchDown(event, x, y, pointer, button);
						//return true;
					}

					@Override
					public void touchUp(InputEvent event, float x, float y,
							int pointer, int button) {
						// TODO Auto-generated method stub
						//super.touchUp(event, x, y, pointer, button);
						System.err.println("ok");
					}
		        	
		        	
		        });
		        
		        stage.addActor(button);
	}

	@Override
	public void dispose() {
	}

	@Override
	public void render() {		
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		screen.render();

		 stage.act();
		 stage.draw();
	}

	public void setScreen (Screen newScreen) {
		if (screen != null) screen.removed();
		screen = newScreen;
		if (screen != null) screen.init(this);
	}
	
	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
