package fr.m1miage.london.game.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class Fonts {
	public static BitmapFont GLOBAL_FONT;
	public static BitmapFont FONT_TITLE;
	public static BitmapFont FONT_TITLE_QUARTIER;

	public static void load(){
		GLOBAL_FONT=new BitmapFont(Gdx.files.internal("ressources/Fnt/arial.fnt"),true);
		FONT_TITLE=new BitmapFont(Gdx.files.internal("ressources/Fnt/title_fnt.fnt"),true);
		FONT_TITLE_QUARTIER =new BitmapFont(Gdx.files.internal("ressources/Fnt/font_quartiers.fnt"),true);
		
		
		FONT_TITLE.setColor(new Color( 0.1f,0.30f,0.1f,1.0f));
		FONT_TITLE_QUARTIER.setColor(new Color( 0.1f,0.30f,0.1f,1.0f));
	}

}
