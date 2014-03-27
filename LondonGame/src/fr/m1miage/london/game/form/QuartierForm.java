package fr.m1miage.london.game.form;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import fr.m1miage.london.classes.Quartier;
import fr.m1miage.london.game.graphics.Art;
import fr.m1miage.london.game.graphics.Fonts;

public class QuartierForm {
	public static TextureRegion quartier_bg;
	
	public static TextureRegion iconePV;
	public static TextureRegion iconeLivres;
	public static TextureRegion iconeCartePioche;
	
	
	private final static int tailleIcon = 64;
	
	public static void load(){
		
		//background
		quartier_bg = Art.load("ressources/Images/fond_quartier.png", 525, 650);
		iconePV = Art.load("ressources/Images/icone_pv.png", tailleIcon, tailleIcon);
		iconeLivres = Art.load("ressources/Images/icone_livre.png", tailleIcon, tailleIcon);
		iconeCartePioche = Art.load("ressources/Images/icone_cartePioche.png", tailleIcon, tailleIcon);

	}
	
	public static void chargerQuartier(Quartier quartier,SpriteBatch spriteBatch){
		System.out.println(quartier.getNom());
		

	}
	
	
	
}
