package fr.m1miage.london.ui.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import fr.m1miage.london.classes.Carte;
import fr.m1miage.london.ui.Prefs;
import fr.m1miage.london.ui.screens.GameScreen;
import fr.m1miage.london.ui.screens.Screen;

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
	private boolean selected = false;
	
	private int time =0;
	
	public CarteActor(Carte c, int x, int y){
		this.carte = c;
		this.id = c.getId_carte();
		Texture t = new Texture(Gdx.files.internal(Prefs.REPERTOIRE_CARTES+carte.getImage()));
		img = new TextureRegion(t, 0, 0, LARGEUR_CARTE, HAUTEUR_CARTE);
		img.flip(false, false);
		this.x = x;
		this.y = y;
		this.xDefault = x;
		this.yDefault = y;
		this.setHeight(HAUTEUR_CARTE);
		this.setWidth(LARGEUR_CARTE);
		this.setY(y);
		this.setX(x);
		this.addListener(new InputListener(){

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				System.out.println(":(");
				
				if(selected){
					selected=false;
					GameScreen.idCarteSelected=0;
				}else{
					selected =true;
					GameScreen.idCarteSelected = carte.getId_carte();
				}
				return super.touchDown(event, x, y, pointer, button);
			}

			@Override
			public boolean mouseMoved(InputEvent event, float x, float y) {
				GameScreen.idCarteOver = carte.getId_carte();
				System.out.println(carte.getNom());
				return true;
			}
			
		});
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		//batch.end();
		batch.draw(img, x, y);
		
		
		//batch.begin();
	}

	@Override
	public void setX(float x) {
		
		super.setX(x);
	}

	@Override
	public void setY(float y) {
		this.y = (int) y;
		super.setY(y);
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
	

}
