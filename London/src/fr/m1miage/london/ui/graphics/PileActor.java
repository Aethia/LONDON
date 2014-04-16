package fr.m1miage.london.ui.graphics;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

import fr.m1miage.london.classes.Carte;
import fr.m1miage.london.ui.Prefs;

public class PileActor extends Actor{
	private int id;
	private ArrayList<Carte> pile;
	private static final int HAUTEUR_CARTE = 300;
	private static final int LARGEUR_CARTE = 200;
	private int x;
	private int y;
	private TextureRegion img;
	
	public PileActor(ArrayList p, int x, int y){
		Texture t = new Texture(Gdx.files.internal(Prefs.REPERTOIRE_CARTES+"carte16.png"));
		img = new TextureRegion(t, 0, 0, LARGEUR_CARTE, HAUTEUR_CARTE);
		img.flip(false, false);
		
		this.pile=p;
		this.x=x;
		this.y=y;
		this.setHeight(HAUTEUR_CARTE);
		this.setWidth(LARGEUR_CARTE);
		this.setY(y);
		this.setX(x);
		this.setColor(Color.RED);
		
	}
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		//batch.end();
		batch.draw(img, x, y);
		
		
		//batch.begin();
	}

	@Override
	public void setX(float x) {
		this.x = (int) x;

		super.setX(x);
	}

	@Override
	public void setY(float y) {
		this.y = (int) y;
		super.setY(y);
	}
	
	public void setId(int id){
		this.id=id;
	}

	
	
}
