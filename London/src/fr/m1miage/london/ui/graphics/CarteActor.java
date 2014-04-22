package fr.m1miage.london.ui.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

import fr.m1miage.london.classes.Carte;
import fr.m1miage.london.ui.Prefs;

public class CarteActor extends Actor{
	private int id;
	private Carte carte;
	private static final int HAUTEUR_CARTE = 300;
	private static final int LARGEUR_CARTE = 200;
	private int x;
	private int y;
	private int xDefault;
	private int yDefault;
	private TextureRegion img;
	private boolean visible=true;
	private boolean selected = false;

	public CarteActor(Carte c, int x, int y){
		this.carte = c;
		this.id = c.getId_carte();
		if(c.isDesactivee()){
			setDisabled();
		}else{
			Texture t = new Texture(Gdx.files.internal(Prefs.REPERTOIRE_CARTES+carte.getImage()));
			img = new TextureRegion(t, 0, 0, LARGEUR_CARTE, HAUTEUR_CARTE);
			img.flip(false, false);
		}
		this.x = x;
		this.y = y;
		this.xDefault = x;
		this.yDefault = y;
		this.setHeight(HAUTEUR_CARTE);
		this.setWidth(LARGEUR_CARTE);
		this.setY(y);
		this.setX(x);
	}

	public void setDisabled(){
		this.img =Art.carteDisabled;
	}
	

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		//batch.end();
		if(this.visible){
			batch.draw(img, x, y);
		}


		//batch.begin();
	}

	@Override
	public void setX(float x) {
		this.x = (int) x;

		super.setX(x);
	}

	@Override
	public void setPosition(float x, float y) {
		this.x = (int) x;
		this.y = (int)y;
		super.setPosition(x, y);
	}

	@Override
	public void setY(float y) {
		this.y = (int) y;
		super.setY(y);
	}
	
	public TextureRegion getImage(){
		return this.img;
	}
	public int getxDefault() {
		return xDefault;
	}

	public int getyDefault() {
		return yDefault;
	}

	public void setDefaultPosition(){
		this.setPosition(xDefault, yDefault);
		this.y = yDefault;
		this.x = xDefault;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public int getId() {
		return id;
	}

	public Carte getCarte() {
		return carte;
	}

}
