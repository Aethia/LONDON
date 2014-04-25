package fr.m1miage.london.ui.screens;

import java.awt.Point;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import fr.m1miage.london.classes.Quartier;
import fr.m1miage.london.ui.Prefs;
import fr.m1miage.london.ui.graphics.Art;
import fr.m1miage.london.ui.graphics.Buttons;
import fr.m1miage.london.ui.graphics.Fonts;



public class MetroScreen extends Screen {
	private Stage stage;
	
	private static final int argent = -3;
	
	private Quartier q1;
	private int compteur = 2;
	private String messageMetro = new String("");
	private int quartierSelected = 0;
	
	private TextButton validerMetro;
	private TextButton retourActivation;
	
	public MetroScreen(){
		metro();
	}
	public MetroScreen(int cpt, Quartier q1){
		this.compteur = cpt;
		this.q1=q1;
		metro();
	}
	
	public void metro(){
		stage = new Stage(Prefs.LARGEUR_FENETRE, Prefs.HAUTEUR_FENETRE, false); 
		stage.clear();
		Gdx.input.setInputProcessor(stage);
		if(compteur==2){
			messageMetro="Choisir un marqueur";
		}
		else{
			messageMetro="Choisir un 2nd marqueur";
		}
		
		retourActivation = new TextButton("Retour à l'activation", Buttons.styleInGameMenu);
		retourActivation.setPosition(100, 95);
		retourActivation.setVisible(false);
		retourActivation.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				Screen.setScreen(new ZoneRestaurerScreen("Métro construit"));
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		
		stage.addActor(retourActivation);
		
		
		validerMetro = new TextButton("Valider", Buttons.styleInGameMenu);
		validerMetro.setPosition(100, 135);
		validerMetro.setVisible(false);
		validerMetro.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if(compteur == 2){
					
					Quartier q1 = londonG.partie.getPlateau().getQuartiers().get(quartierSelected);
					compteur--;
					londonG.partie.getPlateau().getQuartiers().get(quartierSelected).QuartierMetro();
					Screen.setScreen(new MetroScreen(compteur, q1));
				}
				else{
					londonG.partie.getPlateau().getQuartiers().get(quartierSelected).QuartierMetro();
					if(q1.isAuSudTamise() != londonG.partie.getPlateau().getQuartiers().get(quartierSelected).isAuSudTamise()){
						londonG.partie.getListeJoueurs().get(londonG.partie.getJoueurActif()).setAddArgent(argent);
					}
					validerMetro.setVisible(false);
					retourActivation.setVisible(true);
				}
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		
		stage.addActor(validerMetro);
		
		Prefs r = new Prefs();
		Map<Integer, Quartier> quartiers = londonG.partie.getPlateau().getQuartiers();
		for(final Integer q: quartiers.keySet()){	
			Quartier qa = quartiers.get(q);
			if(quartiers.get(q).isMetro() == true){
			Point p = r.listePoints.get(q);
			Image quartier = new Image(Art.iconeMetro);
			quartier.setX(p.x-200);
			quartier.setY(p.y);
			quartier.setHeight(30);
			quartier.setWidth(30);
			quartier.addListener(new InputListener(){
			@Override
				public void enter(InputEvent event, float x, float y,
						int pointer, Actor fromActor) {
					// TODO Auto-generated method stub
					super.enter(event, x, y, pointer, fromActor);
				}
			@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					quartierSelected=q;
					validerMetro.setVisible(true);
					return super.touchDown(event, x, y, pointer, button);
				}
			});
			stage.addActor(quartier);
			}
			
		}
		
		
	}

	@Override
	public void render() {
		spriteBatch.begin();
		draw(Art.bgPartie, 0, 0);
		draw(Art.imagesQuartiers.get(0), 400, 100);
		Fonts.FONT_TITLE.draw(spriteBatch, "METRO", 700, 20);

		Fonts.FONT_BLACK.draw(spriteBatch, messageMetro, 100, 100);

		spriteBatch.end();



		stage.act();
		stage.draw();

		
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

}
