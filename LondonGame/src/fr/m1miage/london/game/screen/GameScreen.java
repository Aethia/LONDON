package fr.m1miage.london.game.screen;

import fr.m1miage.london.game.Art;
import fr.m1miage.london.game.Buttons;

public class GameScreen extends Screen{

	@Override
	public void render() {
		// TODO Auto-generated method stub
		spriteBatch.begin();
		
		draw(Art.bg, 0, 0);
		draw(Art.plateau_jeu_test,350,50);

		drawString("Voici le jeu", 500, 10);
		//draw(Art.player1Button, 200, 50);


		String msg = "COPYRIGHT Aethia 2014";
		drawString(msg, 2, 720 -6 -2);


		spriteBatch.end();

	}

}
