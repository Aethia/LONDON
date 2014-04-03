package fr.m1miage.london.ui.screens;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import fr.m1miage.london.Regles;
import fr.m1miage.london.classes.Joueur;
import fr.m1miage.london.ui.LondonGame;
import fr.m1miage.london.ui.Prefs;
import fr.m1miage.london.ui.graphics.Art;
import fr.m1miage.london.ui.graphics.Buttons;
import fr.m1miage.london.ui.graphics.Fonts;
import fr.m1miage.london.ui.graphics.Score;

public class EmprunterScreen extends Screen{
	
	private Stage stage; 
	private List<TextButton> listeEmprunts = new ArrayList<TextButton>();
	
	/*idMontantEmprunt*/
	private static int montantEmprunt=0;
	
	/* Scores */
	private Score scoreJoueur;
	
	public EmprunterScreen(){
		stage = new Stage(Prefs.LARGEUR_FENETRE, Prefs.HAUTEUR_FENETRE, false); 
		stage.clear();
		Gdx.input.setInputProcessor(stage);
		
		Joueur j = LondonGame.partie.getObjJoueurActif();
		Table tableauEmprunts = new Table();
		tableauEmprunts.setPosition(650, 400);
		for(int i=10; i<=Regles.EMPRUNTMAX;i=i+10){
			final int mt = i;
			TextButton emp = new TextButton("£"+i,Buttons.styleEmprunt1);
			emp.addListener(new InputListener(){

				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					montantEmprunt = mt;
					return super.touchDown(event, x, y, pointer, button);
				}
				
			});
			if(i%50 == 0){
				tableauEmprunts.add(emp).row();
			}else{
				tableauEmprunts.add(emp);
			}
		}
		
		stage.addActor(tableauEmprunts);
		
		Table paramBtn = new Table();
		paramBtn.setPosition(600, 200);
		TextButton btnValider = new TextButton("Valider", Buttons.styleInGameMenu);
		paramBtn.add(btnValider);
		TextButton btnAnnuler = new TextButton("Annuler", Buttons.styleInGameMenu);
		paramBtn.add(btnAnnuler);
		stage.addActor(paramBtn);
		
		
		scoreJoueur = new Score(j);
		stage.addActor(scoreJoueur);
	}
	
	@Override
	public void render() {
		spriteBatch.begin();
		draw(Art.bgPartie, 0, 0);
		Fonts.FONT_TITLE.draw(spriteBatch, "EMPRUNTER", 450, 20);
		spriteBatch.end();
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}

	@Override
	public void tick() {
	}

}
