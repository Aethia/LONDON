package fr.m1miage.london.game.screen;

import fr.m1miage.london.game.graphics.Art;

public class QuartiersScreen extends Screen{

	@Override
	public void render() {
		// TODO Auto-generated method stub
		spriteBatch.begin();
		
		draw(Art.bgPartie, 0, 0);
		
		
		
		spriteBatch.end();
	}

}
