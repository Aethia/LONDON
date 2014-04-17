package fr.m1miage.london.ui.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

import fr.m1miage.london.classes.Carte;

public class PileActor extends Actor{
	private int id;
	private Carte pile;
	private static final int HAUTEUR_CARTE = 400;
	private static final int LARGEUR_CARTE = 200;
	private int x;
	private int y;
	private TextureRegion img;
	
	public PileActor(Carte c, int x, int y){
		
		this.pile=c;
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
		if(this.img != null){
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
	public void setY(float y) {
		this.y = (int) y;
		super.setY(y);
	}
	
	public void setId(int id){
		this.id=id;
	}

	public int getId(){
		return this.id;
	}

	public void setImg(Texture t){
		this.img=new TextureRegion(t, 0, 0, LARGEUR_CARTE, HAUTEUR_CARTE);
	}
}
