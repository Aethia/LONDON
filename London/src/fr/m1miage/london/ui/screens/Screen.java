package fr.m1miage.london.ui.screens;


import java.util.Random;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;

import fr.m1miage.london.ui.LondonGame;
import fr.m1miage.london.ui.Prefs;
import fr.m1miage.london.ui.graphics.Art;

/**
 * 
 * Source : https://github.com/libgdx/libgdx (inspiré de)
 * 
 */
public abstract class Screen {
	private final String[] chars = {"ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789", ".,!?:;\"'+-=/\\< "};
	protected static Random random = new Random();
	protected static LondonGame londonG;
	public SpriteBatch spriteBatch;
	private static int time = 0;

	public void removed () {
		spriteBatch.dispose();
	}

	public final void init (LondonGame londonG) {
		Screen.londonG = londonG;
		Matrix4 projection = new Matrix4();
		projection.setToOrtho(0, Prefs.LARGEUR_FENETRE, Prefs.HAUTEUR_FENETRE, 0, -1, 1);

		spriteBatch = new SpriteBatch(100);
		spriteBatch.setProjectionMatrix(projection);
		
		
	}

	public static void setScreen (Screen screen) {

		time = 0;
		while(time<100){
			time++;
			System.out.println(time);
		}
		londonG.setScreen(screen);
	}

	public void draw (TextureRegion region, int x, int y) {
		int width = region.getRegionWidth();
		if (width < 0) width = -width;
		spriteBatch.draw(region, x, y, width, region.getRegionHeight());
	}

	public void drawString (String string, int x, int y) {
		string = string.toUpperCase();
		for (int i = 0; i < string.length(); i++) {
			char ch = string.charAt(i);
			for (int ys = 0; ys < chars.length; ys++) {
				int xs = chars[ys].indexOf(ch);
				if (xs >= 0) {
					draw(Art.fnt[xs][ys + 9], x + i * 6, y);
				}
			}
		}
	}
	
	public abstract void render ();

	public abstract void tick();

}
