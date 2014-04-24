package fr.m1miage.london.ui.screens;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import fr.m1miage.london.classes.Joueur;
import fr.m1miage.london.ui.Prefs;
import fr.m1miage.london.ui.graphics.Art;
import fr.m1miage.london.ui.graphics.CarteActor;
import fr.m1miage.london.ui.graphics.Fonts;

public class FinPartieScreen extends Screen {
	
	private Stage stage;
	private Joueur joueurGagnant;
	
	public FinPartieScreen(){
		londonG.partie.calculPointsVictoire();
		londonG.partie.calculGagnant();
		
		stage = new Stage(Prefs.LARGEUR_FENETRE, Prefs.HAUTEUR_FENETRE, false); 
		stage.clear();
		Gdx.input.setInputProcessor(stage);
		
		Table tableauFinal = new Table();
		List<Joueur> joueurs = londonG.partie.getListeJoueurs();
		
		joueurGagnant = joueurs.get(0);
		Label lJG = new Label("Le gagnant est : " + joueurGagnant.getNom(), Art.skin);
		lJG.setPosition(500, 400);
		stage.addActor(lJG);
		for(Joueur joueur : joueurs){
			Label l = new Label(joueur.getNom(), Art.skin);
			l.setColor(Prefs.conversionCouleur(joueur.getCouleur()));
			
			tableauFinal.add(l).colspan(1);
			tableauFinal.add("£"+joueur.getArgent(), "score").colspan(2);
			tableauFinal.add(""+joueur.getPoint_victoire(), "score").colspan(3);
			tableauFinal.add(""+joueur.getPoint_pauvrete(), "score").colspan(4);
			tableauFinal.add("£"+joueur.getMontantEmprunts(), "score").colspan(5);
		}
		tableauFinal.setPosition(500, 350);
		stage.addActor(tableauFinal);
		
	}

	@Override
	public void render() {
		spriteBatch.begin();
		draw(Art.bg, 0, 0);
		Fonts.FONT_TITLE.draw(spriteBatch, "FIN DE LA PARTIE", 200, 20);

		
		spriteBatch.end();
		
		stage.act();
		stage.draw();
	}

	@Override
	public void tick() {
		
	}

}
