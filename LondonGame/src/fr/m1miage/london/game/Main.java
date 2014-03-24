package fr.m1miage.london.game;

import java.util.Map;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import fr.m1miage.london.classes.Quartier;
import fr.m1miage.london.db.CartesManager;
import fr.m1miage.london.db.QuartiersManager;
import fr.m1miage.london.game.graphics.Prefs;


public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		
       // System.out.println(CartesManager.getCartes());
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "LondonGame";
		cfg.width = Prefs.LARGEUR_FENETRE;
		cfg.height = Prefs.HAUTEUR_FENETRE;
		
		new LwjglApplication(new LondonGame(), cfg);
	}

}
