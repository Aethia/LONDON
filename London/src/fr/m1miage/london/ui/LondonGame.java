package fr.m1miage.london.ui;



import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

import fr.m1miage.london.Partie;
import fr.m1miage.london.ui.graphics.Art;
import fr.m1miage.london.ui.screens.MainMenuScreen;
import fr.m1miage.london.ui.screens.Screen;

public class LondonGame implements ApplicationListener {

	private Screen screen;
	public Partie partie;
	



	@Override
	public void create() {	
		//a modifier et a mettre dans partie.java		
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
