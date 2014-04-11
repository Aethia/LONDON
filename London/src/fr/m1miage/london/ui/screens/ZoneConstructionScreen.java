package fr.m1miage.london.ui.screens;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import fr.m1miage.london.classes.Carte;
import fr.m1miage.london.classes.Joueur;
import fr.m1miage.london.ui.Prefs;
import fr.m1miage.london.ui.graphics.Art;
import fr.m1miage.london.ui.graphics.Buttons;
import fr.m1miage.london.ui.graphics.CarteActor;
import fr.m1miage.london.ui.graphics.Fonts;

public class ZoneConstructionScreen extends Screen{
	private Stage stage;

	private TextButton btnRetour;
	private TextButton btnAnnulCarte;
	private boolean tourValide=false;

	private int idCarteSelected =0;
	private int idDefausseSelected =0;
	public Map<Integer, CarteActor> main = new HashMap<Integer,CarteActor>();

	private String messageConstruire = new String("");

	public ZoneConstructionScreen(){
		stage = new Stage(Prefs.LARGEUR_FENETRE, Prefs.HAUTEUR_FENETRE, false); 
		stage.clear();
		Gdx.input.setInputProcessor(stage);

		messageConstruire = "Choisir une carte à poser";

		btnRetour =new TextButton("Retour",Buttons.styleInGameMenu); 
		btnRetour.setPosition(1100, 100); 

		btnRetour.addListener(new InputListener(){

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
		stage.addActor(btnRetour);

		btnAnnulCarte = new TextButton("X", Buttons.styleInGameMenu);
		btnAnnulCarte.setSize(50, 50);
		btnAnnulCarte.setPosition(375, 295);
		btnAnnulCarte.setVisible(false);
		btnAnnulCarte.addListener(new InputListener(){

			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer,
					int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer,
					int button) {
				CarteActor c = main.get(idCarteSelected);
				c.setDefaultPosition();
				if(idDefausseSelected!=0){
					c = main.get(idDefausseSelected);
					c.setDefaultPosition();
				}
				idCarteSelected=0;
				idDefausseSelected=0;
				messageConstruire = "";
				btnAnnulCarte.setVisible(false);
				super.touchUp(event, x, y, pointer, button);
			}

		});
		stage.addActor(btnAnnulCarte);
	
		Joueur j = londonG.partie.getObjJoueurActif();
		int i=0;
		for(final Carte c: j.getLesCartes()){
			i++;
			final CarteActor ca = new CarteActor(c,350+i*50,10);
			stage.addActor(ca);
			ca.addListener(new InputListener(){

				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {				
					if(idCarteSelected!=0){ //si on a deja selectionné une carte, alors c'est une carte a defausser
						ca.setY(ca.getyDefault()+350);
						ca.setX(600);
						ca.setSelected(true);
						idDefausseSelected = c.getId_carte();
						messageConstruire = "Choisir une pile";
					}else{
						ca.setY(ca.getyDefault()+350);
						ca.setX(300);
						ca.setSelected(true);
						idCarteSelected = c.getId_carte();
						if(c.isConstructible()){
							btnAnnulCarte.setVisible(true);
							messageConstruire = "Choisir une carte à defausser";
						}else{
							messageConstruire = "Cette carte n'est pas constructible";
							btnAnnulCarte.setVisible(false);
						}

					}
					return super.touchDown(event, x, y, pointer, button);
				}

			});
			main.put(c.getId_carte(), ca);
		}

		Table tPiles = new Table();
		int nbpiles = j.getZone_construction().getNbPiles();
		TextButton btnP;
		for(i =0; i<=nbpiles; i++){
			btnP= new TextButton("i", Buttons.styleInGameMenu);
			btnP.setSize(50, 50);
			btnP.setVisible(false);
			btnP.addListener(new InputListener(){
				
			});
			tPiles.add(btnP);
		}
		stage.addActor(tPiles);
		
	}

	@Override
	public void render() {
		spriteBatch.begin();
		draw(Art.bgPartie, 0, 0);
		Fonts.FONT_TITLE.draw(spriteBatch, "ZONE CONSTRUCTION", 200, 20);

		Fonts.FONT_BLACK.draw(spriteBatch, messageConstruire, 500, 100);

		spriteBatch.end();

		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub

	}

}
