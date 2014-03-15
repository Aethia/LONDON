package fr.m1miage.london.game;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import fr.m1miage.london.db.CartesManager;


public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

       // System.out.println(CartesManager.getCartes());
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "LondonGame";
		cfg.width = 1280;
		cfg.height = 720;
		
		new LwjglApplication(new LondonGame(), cfg);
	}

}
