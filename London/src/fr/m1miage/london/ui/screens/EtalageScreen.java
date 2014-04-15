package fr.m1miage.london.ui.screens;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import fr.m1miage.london.classes.Carte;
import fr.m1miage.london.classes.Etalage;
import fr.m1miage.london.ui.Prefs;
import fr.m1miage.london.ui.graphics.Art;
import fr.m1miage.london.ui.graphics.Buttons;
import fr.m1miage.london.ui.graphics.CarteActor;
import fr.m1miage.london.ui.graphics.Fonts;

public class EtalageScreen extends Screen{
	
	private ShapeRenderer fondEtalage;
	private List<CarteActor> cartesTop = new ArrayList<CarteActor>();
	private List<CarteActor> cartesBottom = new ArrayList<CarteActor>();
	private Etalage etalage;
	private int nbColonnes=0;
	private int topC = 400;
	private int leftC =  185;
	
	private TextButton btnRetour;
	private TextButton btnValider;
	
	private Stage stage; 
	
	public EtalageScreen(){
		stage = new Stage(Prefs.LARGEUR_FENETRE, Prefs.HAUTEUR_FENETRE, false); 
		stage.clear();
		Gdx.input.setInputProcessor(stage);
		
		btnRetour =new TextButton("Retour",Buttons.styleInGameMenu); 
		btnRetour.setPosition(1100, 135); 

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
		
		etalage = londonG.partie.getPlateau().getEtalage();
		nbColonnes = londonG.partie.getListeJoueurs().size()+1;
		fondEtalage = new ShapeRenderer();
		int i = 0;
		for(Carte c :etalage.getRangee1()){
			CarteActor ca = new CarteActor(c, leftC+i*250,topC);
			cartesTop.add(ca);
			stage.addActor(ca);
			i++;
		}
		i=0;
		for(Carte c :etalage.getRangee2()){
			CarteActor ca = new CarteActor(c, leftC+i*250,topC-300);
			cartesBottom.add(ca);
			stage.addActor(ca);
			i++;
		}
	}

	@Override
	public void render() {
		spriteBatch.begin();
		draw(Art.bgPartie, 0, 0);
		Fonts.FONT_TITLE.draw(spriteBatch, "ETALAGE", 500, 20);
		spriteBatch.end();
		
		Gdx.gl.glEnable(GL10.GL_BLEND);
		Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		fondEtalage.begin(ShapeType.Filled);

		fondEtalage.setColor(new Color(0.36f, 0.38f, 0.27f, 1f));
		fondEtalage.rect(150, 50, 1000, 650);
		fondEtalage.end();
		Gdx.gl.glDisable(GL10.GL_BLEND);

		spriteBatch.begin();
		for(int i=0; i < nbColonnes ; i++){
			draw(Art.emplacement_etalage,180+i*250,100);
		}
		for(int i=0; i < nbColonnes ; i++){
			draw(Art.emplacement_etalage,180+i*250,400);
		}
		spriteBatch.end();
		stage.act();
		stage.draw();
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

}
