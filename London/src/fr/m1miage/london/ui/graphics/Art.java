package fr.m1miage.london.ui.graphics;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import fr.m1miage.london.ui.Prefs;


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

	/*-- screen quartiers **/
	public static TextureRegion quartier_bg;
	public static TextureRegion iconePV;
	public static TextureRegion iconeLivres;
	public static TextureRegion iconeCartePioche;
	public static HashMap<Integer, TextureRegion> imagesQuartiers = new HashMap<Integer, TextureRegion>();
	private static int tailleIcon =64;
	
	/*-- Game screen --*/
	public static TextureRegion action_bg;
	public static TextureRegion finTour_bg;
	
	public static TextureRegion scoreJoueur_bg;
	
	/*-- Icones tableau scores--*/ 
	public static TextureRegion ico_PV;
	public static TextureRegion ico_Livres;
	public static TextureRegion ico_Emprunt;
	public static TextureRegion ico_Pauvre;
	
	/*--- carte ---*/
	public static TextureRegion  emplacement_etalage;
	public static TextureRegion carteDisabled;
	public static Map<Integer, TextureRegion> cartes = new HashMap<Integer, TextureRegion>();
	public static Texture validTarget;
	public static Texture carteEtalage;
	public static Skin skinTableauScores;
	public static Skin skinScoreJoueur;
	public static Skin skin;
	public static TextureRegion iconeMetro;
	
	
	public static Texture scroll;
	public static TextureRegion getCarteID(int idCarte){
		return cartes.get(idCarte);
	}
	
	public static void load () {
		Fonts.load();
		Buttons.load();
		scroll = new Texture(Gdx.files.internal(Prefs.REPERTOIRE+"default-scroll.png"));
		bg = load("ressources/Images/background.png", 1400, 800);
		bgPartie = load("ressources/Images/backgroundPartie.png", 1400, 800);
		menu_bg = Art.load("ressources/Images/menu_background.png", 256, 512);

		fnt = split("ressources/Images/guys.png", 6, 6);
		plateau_jeu_test = load("ressources/Images/plateau_jeu_test.png", 1024, 1024);

		/*----- quartiers -----*/
		quartier_bg = Art.load("ressources/Images/fond_quartier.png", 525, 650);
		iconePV = Art.load("ressources/Images/icone_pv.png", tailleIcon, tailleIcon);
		iconeLivres = Art.load("ressources/Images/icone_livre.png", tailleIcon, tailleIcon);
		iconeCartePioche = Art.load("ressources/Images/icone_cartePioche.png", tailleIcon, tailleIcon);
		iconeMetro = Art.load("ressources/Images/Quartiers/metro.png", 50, 50);

		/*---- game ---- */
		action_bg = load(Prefs.REPERTOIRE+"background_action.png", 750,315);
		finTour_bg = load(Prefs.REPERTOIRE+"finTour_bg.png", 750,315);
		scoreJoueur_bg = load(Prefs.REPERTOIRE+"score_joueur.png", 300,100);
		
		/*--score joueur*/
		ico_PV = load(Prefs.REPERTOIRE_ICONES+"ico_pv.png", 40,40);
		ico_Livres = load(Prefs.REPERTOIRE_ICONES+"ico_livre.png", 40,40);
		ico_Emprunt = load(Prefs.REPERTOIRE_ICONES+"ico_emprunt.png", 64,40);
		ico_Pauvre = load(Prefs.REPERTOIRE_ICONES+"ico_pauvrete.png", 40,40);
		
		chargerImagesQuartiers();
		skinTableauScores= new Skin(Gdx.files.internal(Prefs.REPERTOIRE +"ui.json"));
		skinScoreJoueur =new Skin( Gdx.files.internal(Prefs.REPERTOIRE +"ui.json"));
		skin = new Skin( Gdx.files.internal(Prefs.REPERTOIRE +"ui.json"));
		emplacement_etalage = load(Prefs.REPERTOIRE + "etalage_carte.png", 256,512);
		/*cartes*/
		for(int i=1;i<=110;i++){
			TextureRegion c = load(Prefs.REPERTOIRE_CARTES + "carte"+i+".png", 200,300);
			c.flip(false, true);
			cartes.put(i, c);
		}
		
		validTarget= new Texture(Gdx.files.internal(Prefs.REPERTOIRE_CARTES+"validTarget.png"));
		carteEtalage = new Texture(Gdx.files.internal(Prefs.REPERTOIRE+"carte_etalage.png"));
		Texture t= new Texture(Gdx.files.internal(Prefs.REPERTOIRE_CARTES+"carteDisabled.png"));
		carteDisabled = new TextureRegion(t, 0, 0, Prefs.LARGEUR_CARTE, Prefs.HAUTEUR_CARTE);
		carteDisabled.flip(false, false);
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
	
	private static void chargerImagesQuartiers(){
		for(int i = 0; i<=20; i++){
			imagesQuartiers.put(i,load("ressources/Images/Quartiers/q"+i+".png", 1024, 1024));
		}
	}

}
