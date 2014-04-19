package fr.m1miage.london.ui.graphics;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class MapActor extends Actor{
	TextureRegion t;
	float x;
	float y;
	
	public MapActor(float x, float y){
		this.x = x;
		this.y = y;
	}
	
	public void setTexture(TextureRegion t){
		this.t=t;
	}
}
