package fr.m1miage.london.game;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import fr.m1miage.london.game.screen.MainMenuScreen;
import fr.m1miage.london.game.screen.Screen;

public class LondonGame implements ApplicationListener {
	
	private Screen screen;
	
	@Override
	public void create() {		
		Art.load();
		setScreen(new MainMenuScreen());
		
		
	}

	@Override
	public void dispose() {
	}

	@Override
	public void render() {		
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		screen.render();
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
