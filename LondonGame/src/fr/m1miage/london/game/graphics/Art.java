package fr.m1miage.london.game.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

import fr.m1miage.london.game.form.QuartierForm;

/**
 * 
 * @author Aethia
 * Classe ou sont "initialisées" toutes nos ressources
 */
public class Art {
	public static TextureRegion bg;
	public static TextureRegion bgPartie;
	public static TextureRegion[][] fnt;
	
	public static TextureRegion plateau_jeu_test;
	
	public static TextureRegion menu_bg;
	
	public static void load () {
		Fonts.load();
		Buttons.load();
		QuartierForm.load();
		bg = load("ressources/Images/background.png", 1400, 800);
		bgPartie = load("ressources/Images/backgroundPartie.png", 1400, 800);
		
		menu_bg = Art.load("ressources/Images/menu_background.png", 256, 512);
				
		fnt = split("ressources/Images/guys.png", 6, 6);
		plateau_jeu_test = load("ressources/Images/plateau_jeu_test.png", 1024, 1024);
		
	//	player1Button = load("ressources/Images/joueur1.png", 512, 1024);
		
		
        
	}
	
	public static TextureRegion load (String name, int width, int height) {
		Texture texture = new Texture(Gdx.files.internal(name));
		
		TextureRegion region = new TextureRegion(texture, 0, 0, width, height);
		region.flip(false, true);
		return region;
	}
	
	private static TextureRegion[][] split (String name, int width, int height) {
		return split(name, width, height, false);
	}
	
	private static TextureRegion[][] split (String name, int width, int height, boolean flipX) {
		Texture texture = new Texture(Gdx.files.internal(name));
		int xSlices = texture.getWidth() / width;
		int ySlices = texture.getHeight() / height;
		TextureRegion[][] res = new TextureRegion[xSlices][ySlices];
		for (int x = 0; x < xSlices; x++) {
			for (int y = 0; y < ySlices; y++) {
				res[x][y] = new TextureRegion(texture, x * width, y * height, width, height);
				res[x][y].flip(flipX, true);
			}
		}
		return res;
	}
	
}
