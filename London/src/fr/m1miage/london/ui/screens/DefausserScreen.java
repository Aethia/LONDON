package fr.m1miage.london.ui.screens;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import fr.m1miage.london.classes.Carte;
import fr.m1miage.london.classes.Joueur;
import fr.m1miage.london.ui.Prefs;
import fr.m1miage.london.ui.graphics.Art;
import fr.m1miage.london.ui.graphics.Buttons;
import fr.m1miage.london.ui.graphics.CarteActor;
import fr.m1miage.london.ui.graphics.Fonts;

public class DefausserScreen extends Screen {
	private Stage stage; 
	private TextButton btnValider;
	private Joueur joueur;
	private Map<Integer, CarteActor> cDefausse = new HashMap<Integer, CarteActor>();
	private int nbDefausse;

	private String messageDefausse= new String("");
	private int left = 200;

	public DefausserScreen(Joueur j, int nbDefausse){
		prepareAffichage(j, nbDefausse);
	}

	private void prepareAffichage(Joueur j, int nbDefausse) {
		this.nbDefausse = nbDefausse;
		this.joueur = j;
		stage = new Stage(Prefs.LARGEUR_FENETRE, Prefs.HAUTEUR_FENETRE, false); 
		stage.clear();
		Gdx.input.setInputProcessor(stage);

		btnValider = new TextButton("Valider", Buttons.styleInGameMenu);
		btnValider.setPosition(1125, 200);
		btnValider.setVisible(false);
		btnValider.addListener(new InputListener(){

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				for(Integer key : cDefausse.keySet()){
					joueur.seDefausser(cDefausse.get(key).getCarte(), londonG.partie.getPlateau().getEtalage());;
				}
				if (londonG.partie.isMultijoueur()) {
					if (GameScreenReseauClient.joueur!=null)
						Screen.setScreen(new GameScreenReseauClient(GameScreenReseauClient.joueur));
					else
						Screen.setScreen(new GameScreenReseauServeur());
				}
				else{
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

		stage.addActor(btnValider);
		afficherCartes();
	}

	private void afficherCartes() {
		int i=0;
		for(final Carte c: joueur.getLesCartes()){
			i++;
			final CarteActor ca = new CarteActor(c,350+i*50,10);
			stage.addActor(ca);
			ca.addListener(new InputListener(){

				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {		

					if(cDefausse.containsValue(ca)){ //si elle a deja été choisie, annuler ce choix
						cDefausse.remove(ca.getId());
						ca.setDefaultPosition();
					}else if (cDefausse.size()<nbDefausse){
						cDefausse.put(ca.getId(), ca);
						ca.setY(ca.getyDefault()+325);
						ca.setX(left + (cDefausse.size()-1)*250);
					}
					System.out.println("selected nb : " + cDefausse.size());
					if(cDefausse.size()==nbDefausse){ //si on a selectionné le nb de cartes necessaires
						btnValider.setVisible(true);
					}else{
						btnValider.setVisible(false);
					}

					return super.touchDown(event, x, y, pointer, button);
				}

			});
			//main.put(c.getId_carte(), ca);
		}
	}

	@Override
	public void render() {
		spriteBatch.begin();

		draw(Art.bgPartie, 0, 0);

		Fonts.FONT_TITLE.draw(spriteBatch, "DEFAUSSE", 500, 20);
		Fonts.FONT_BLACK.draw(spriteBatch, "Veuillez vous defausser de " + nbDefausse + " cartes avant de terminer votre tour", 200, 120);
		spriteBatch.end();
		stage.act();
		stage.draw();

	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub

	}

}
