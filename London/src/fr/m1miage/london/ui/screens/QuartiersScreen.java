package fr.m1miage.london.ui.screens;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import fr.m1miage.london.GestionErreurs;
import fr.m1miage.london.Regles;
import fr.m1miage.london.classes.Joueur;
import fr.m1miage.london.classes.Quartier;
import fr.m1miage.london.ui.Prefs;
import fr.m1miage.london.ui.graphics.AreaColorRect;
import fr.m1miage.london.ui.graphics.Art;
import fr.m1miage.london.ui.graphics.Buttons;
import fr.m1miage.london.ui.graphics.Fonts;
import fr.m1miage.london.ui.graphics.Score;

public class QuartiersScreen extends Screen{

	private Integer nbQuartierSelected = 0;
	private Integer nbQuartierHovered = 0;
	private static int iconsMarginLeft = 815;
	private static int iconsMarginTop = 400;
	private HashMap<Integer, AreaColorRect> listeInvestis;

	private ShapeRenderer fondQuartier;
	private TextButton btnRetour;
	private TextButton btnValider;
	private TextButton btnRetourMap;
	

	private Stage stage; 

	private GestionErreurs erreur;
	private String messageInvestir = new String("");
	
	private Joueur joueur;

	/* Scores */
	private Score scoreJoueur;

	public QuartiersScreen(){
		stage = new Stage(Prefs.LARGEUR_FENETRE, Prefs.HAUTEUR_FENETRE, false); 
		stage.clear();
		
		final Regles r = new Regles();
		Gdx.input.setInputProcessor(stage);
		joueur = londonG.partie.getObjJoueurActif();
		scoreJoueur = new Score(joueur);
		stage.addActor(scoreJoueur);
		
		fondQuartier = new ShapeRenderer();
		listeInvestis = londonG.partie.getPlateau().getInvestis();

		listerQuartiers();
				btnRetour =new TextButton("Retour",Buttons.styleInGameMenu); 
		btnRetour.setPosition(190, 70); 
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

		btnRetourMap =new TextButton("Annuler",Buttons.styleInGameMenu); 
		btnRetourMap.setPosition(980, 135); 
		btnRetourMap.addListener(new InputListener(){
		
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				nbQuartierSelected=0;
				nbQuartierHovered=0;
				btnRetourMap.setVisible(false);
				btnValider.setVisible(false);
				super.touchUp(event, x, y, pointer, button);
			}
		
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}
		});
		btnRetourMap.setVisible(false);
		stage.addActor(btnRetourMap);
		


		if(!londonG.partie.isTourTermine()){
			btnValider = new TextButton("Valider", Buttons.styleInGameMenu);
			btnValider.setPosition(700, 135);
			btnValider.setVisible(false);
			btnValider.addListener(new InputListener(){

				@Override
				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {
					if(nbQuartierSelected==0){
						messageInvestir = "Veuillez selectionner un quartier";
					}else{
						Joueur j = londonG.partie.getObjJoueurActif();

						erreur = j.invest(nbQuartierSelected, londonG.partie.getPlateau(), londonG.partie.getPioche());
						if(erreur.equals(GestionErreurs.NONE)){
							Point p = r.listePoints.get(nbQuartierSelected);
							final AreaColorRect proprietaire = new AreaColorRect(p.x, p.y, 15, 15);
							proprietaire.setShapeFillColor((float)j.getCouleur().getRed()/255, (float)j.getCouleur().getGreen()/255, (float)j.getCouleur().getBlue()/255, 1.0f);
							stage.addActor(proprietaire);
							listeInvestis.put(nbQuartierSelected, proprietaire);
							btnValider.setVisible(true);
							
							londonG.partie.setActionChoisie(3);
							londonG.partie.setTourTermine(true);
							Screen.setScreen(new GameScreen());
						}else{
							messageInvestir=erreur.getMsgErrorString();
						}


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
		}
	}

	private void listerQuartiers(){
		/* Parametres Boutons */
		int i=0;
		Table tQuartiers = new Table();
		
		
		final Map<Integer, Quartier> quartiers = londonG.partie.getPlateau().getQuartiers();
		for(final Integer q: quartiers.keySet()){	
			final Integer j = q;
			final Quartier quartier = quartiers.get(q);
			TextButton btn;
			if(quartier.isInvestir_possible()){
				btn= new TextButton(quartier.getNom(),Buttons.styleInGameMenu); 
			}else{
				btn= new TextButton(quartier.getNom(),Buttons.styleInGameMenuDisabled); 
			}
			if(nbQuartierSelected == 0){		
				if(quartiers.get(q).getProprietaireQuartier()!= null){
					stage.addActor(listeInvestis.get(q));
					listeInvestis.get(q).setVisible(true);
				}
			}
			

			btn.addListener(new InputListener(){
				@Override
				public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
					nbQuartierHovered = j;
					super.enter(event, x, y, pointer, fromActor);
				}
				
				@Override
				public void exit(InputEvent event, float x, float y, int pointer, Actor toActor){
					nbQuartierHovered=0;
				}

				@Override
				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {
					nbQuartierSelected = j;
					btnRetourMap.setVisible(true);
					
					btnRetour.setVisible(false);
					for(Integer zone : listeInvestis.keySet()){
						listeInvestis.get(zone).setVisible(false);
					}
					
					super.touchUp(event, x, y, pointer, button);
				}

				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					return true;
				}

			});
			i++;
			if(i%2==0){
				tQuartiers.add(btn).row();
			}else{
				tQuartiers.add(btn);
			}

		}
		tQuartiers.setPosition(300, 400);

		stage.addActor(tQuartiers);



	}
	
	@Override
	public void render() {
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		spriteBatch.begin();

		draw(Art.bgPartie, 0, 0);

		Fonts.FONT_TITLE.draw(spriteBatch, "QUARTIERS", 500, 20);
		
		if(nbQuartierSelected!=0){

			spriteBatch.end();
			Gdx.gl.glEnable(GL10.GL_BLEND);
			Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
			fondQuartier.begin(ShapeType.Filled);

			fondQuartier.setColor(new Color(1, 1, 1, 0.7f));
			fondQuartier.rect(585, 200, 700, 450);
			fondQuartier.end();
			Gdx.gl.glDisable(GL10.GL_BLEND);

			spriteBatch.begin();

			Quartier quartier = londonG.partie.getPlateau().getQuartier(nbQuartierSelected);
			Fonts.FONT_TITLE_QUARTIER.draw(spriteBatch, quartier.getNom(), 600, 170);

			Texture t = new Texture(Gdx.files.internal(Prefs.REPERTOIRE_QUARTIERS+"quartier.png"));

			TextureRegion img = new TextureRegion(t, 0, 0, 512, 256);
			img.flip(true, true);
			draw(img, 660,210);
			//livres
			draw(Art.iconeLivres,iconsMarginLeft,iconsMarginTop);
			Fonts.FONT_ICON_WHITE.draw(spriteBatch, "" + quartier.getPrix() + "£", iconsMarginLeft + 20, iconsMarginTop + 18);
			//points de victoire
			draw(Art.iconePV,iconsMarginLeft+ 80,iconsMarginTop);
			Fonts.FONT_ICON_WHITE.draw(spriteBatch, ""+quartier.getPoint_victoire(), iconsMarginLeft + 105 , iconsMarginTop+ 18);
			//cartes a piocher
			draw(Art.iconeCartePioche,iconsMarginLeft + 160,iconsMarginTop);
			Fonts.FONT_ICON_WHITE.draw(spriteBatch, ""+quartier.getNb_carte_a_piocher(), iconsMarginLeft + 185, iconsMarginTop + 18);

			if(quartier.getProprietaireQuartier()==null){
				Fonts.FONT_BLACK.draw(spriteBatch, "Aucun propriétaire", 600, 470);
			}else{
				Fonts.FONT_BLACK.draw(spriteBatch, "Propriétaire : "+quartier.getProprietaireQuartier().getNom(), 600, 470);
			}
			
			Fonts.FONT_BLACK.draw(spriteBatch, messageInvestir , 700, 250);
			btnRetour.setVisible(true);
			btnValider.setVisible(true);
		}
		else if(nbQuartierHovered < 21){
			
		
			draw(Art.imagesQuartiers.get(0), 580, 100);
			draw(Art.imagesQuartiers.get(nbQuartierHovered), 580, 100);
		}
		
		spriteBatch.end();
		stage.act();
		stage.draw();
	}

	@Override
	public void tick() {
	}

}
