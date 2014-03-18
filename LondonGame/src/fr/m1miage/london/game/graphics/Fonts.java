package fr.m1miage.london.game.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class Fonts {
	public static BitmapFont GLOBAL_FONT;

	public static void load(){
		GLOBAL_FONT=new BitmapFont(Gdx.files.internal("ressources/Fnt/arial.fnt"),true);
	}

}
