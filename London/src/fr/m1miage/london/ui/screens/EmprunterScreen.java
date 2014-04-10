package fr.m1miage.london.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import fr.m1miage.london.Regles;
import fr.m1miage.london.classes.Joueur;
import fr.m1miage.london.ui.Prefs;
import fr.m1miage.london.ui.graphics.Art;
import fr.m1miage.london.ui.graphics.Buttons;
import fr.m1miage.london.ui.graphics.Fonts;
import fr.m1miage.london.ui.graphics.Score;

public class EmprunterScreen extends Screen{

	private Stage stage; 

	/*MontantEmprunt*/
	private String messageMontant = new String("");
	private int montantEmprunt=0;

	/* Scores */
	private Score scoreJoueur;

	public EmprunterScreen(){
		stage = new Stage(Prefs.LARGEUR_FENETRE, Prefs.HAUTEUR_FENETRE, false); 
		stage.clear();
		Gdx.input.setInputProcessor(stage);

		final Joueur j = londonG.partie.getObjJoueurActif();
		Table tableauEmprunts = new Table();
		tableauEmprunts.setPosition(700, 465);
		for(int i=10; i<=Regles.EMPRUNTMAX;i=i+10){
			final int mt = i;

			TextButton emp = new TextButton("£"+i,Buttons.styleEmprunt1);
			if(i>50){
				emp.setStyle(Buttons.styleEmprunt2);
			}
			emp.addListener(new InputListener(){

				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					montantEmprunt = mt;
					messageMontant = "Montant souhaité : £" + montantEmprunt;
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
		paramBtn.setPosition(700, 200);
		TextButton btnValider = new TextButton("Valider", Buttons.styleInGameMenu);
		btnValider.addListener(new InputListener(){

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				if(montantEmprunt==0){
					messageMontant = "Veuillez selectionner un montant";
				}else{
					j.emprunter(montantEmprunt);
					Screen.setScreen(new GameScreen());
				}
				super.touchUp(event, x, y, pointer, button);
			}

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				
				return true;
			}
			
		
		});
		
		paramBtn.add(btnValider);

		TextButton btnAnnuler = new TextButton("Annuler", Buttons.styleInGameMenu);
		btnAnnuler.addListener(new InputListener(){
			
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				Screen.setScreen(new GameScreen());
				super.touchUp(event, x, y, pointer, button);
			}

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

		});

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
		Fonts.FONT_BLACK.draw(spriteBatch, messageMontant , 560, 480);
		spriteBatch.end();
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}

	@Override
	public void tick() {
	}

}
