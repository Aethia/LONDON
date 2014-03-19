package fr.m1miage.london.game.screen;

import fr.m1miage.london.game.graphics.Art;
import fr.m1miage.london.game.graphics.Buttons;

public class GameScreen extends Screen{

	@Override
	public void render() {
		// TODO Auto-generated method stub
		spriteBatch.begin();
		
		draw(Art.bgPartie, 0, 0);
		draw(Art.plateau_jeu_test,350,50);

		
		String msg = "COPYRIGHT Aethia 2014";
		drawString(msg, 2, 800 -6 -2);


		spriteBatch.end();

	}

}
