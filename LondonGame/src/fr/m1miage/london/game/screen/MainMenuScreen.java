package fr.m1miage.london.game.screen;

import fr.m1miage.london.game.Art;



public class MainMenuScreen extends Screen {

	@Override
	public void render() {
		// TODO Auto-generated method stub
		spriteBatch.begin();
		draw(Art.bg, 0, 0);
		String msg = "COPYRIGHT MOJANG 2010";
		drawString(msg, 2, 240 - 6 - 2);
		//drawString(msg, 160 - msg.length() * 3, 140 - 3 - (int)Math.abs(Math.sin(500 * 0.1) * 10));
		spriteBatch.end();
	}


}
