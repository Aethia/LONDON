package fr.m1miage.london.ui.screens;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import fr.m1miage.london.GestionErreurs;
import fr.m1miage.london.classes.Carte;
import fr.m1miage.london.classes.Joueur;
import fr.m1miage.london.ui.Prefs;
import fr.m1miage.london.ui.graphics.AreaColorRect;
import fr.m1miage.london.ui.graphics.Art;
import fr.m1miage.london.ui.graphics.Buttons;
import fr.m1miage.london.ui.graphics.CarteActor;
import fr.m1miage.london.ui.graphics.Fonts;
import fr.m1miage.london.ui.graphics.Score;

public class ZoneConstructionScreen extends Screen{
	private Stage stage;

	private TextButton btnRetour;
	private TextButton btnAnnulCarte;

	private TextButton validerConstru;

	private int idCarteSelected =0;
	private int idDefausseSelected =0;
	private int idPile = 0;

	private AreaColorRect fondChoixCartes= new AreaColorRect(200, 320, 900, 350);

	public Map<Integer, CarteActor> main = new HashMap<Integer,CarteActor>();
	private Table tPiles;
	private String messageConstruire = new String("");
	private GestionErreurs erreur;
	private Joueur joueur;

	/* Scores */
	private Score scoreJoueur;

	public ZoneConstructionScreen(){
		messageConstruire = "Voici votre zone de construction";
		joueur = londonG.partie.getObjJoueurActif();
		constructionScreen();
	}

	public ZoneConstructionScreen(Joueur j){
		messageConstruire = "Voici la zone de construction de " + j.getNom();
		this.joueur = j;
		constructionScreen();
	}

	public ZoneConstructionScreen(String msg){
		messageConstruire = msg;
		joueur = londonG.partie.getObjJoueurActif();
		constructionScreen();
	}

	private void constructionScreen() {
		stage = new Stage(Prefs.LARGEUR_FENETRE, Prefs.HAUTEUR_FENETRE, false); 
		stage.clear();
		Gdx.input.setInputProcessor(stage);
		fondChoixCartes.setVisible(false);
		fondChoixCartes.setShapeFillColor(1, 1, 1, 0.7f);

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

		afficherPiles();

		stage.addActor(fondChoixCartes);
		if(londonG.partie.getObjJoueurActif().equals(joueur)==true){
			System.out.println(londonG.partie.getObjJoueurActif().getNom());
			System.out.println(joueur.getNom());
		}
		if(londonG.partie.getObjJoueurActif().equals(joueur)==true){ //si on est bien sur le joueur actif 
			//si tour terminé, mais action construire => on peut continuer ou tour pas terminé mais zoneC du joueur Actif
			if((londonG.partie.isTourTermine()==true && londonG.partie.getActionChoisie()==1) || !londonG.partie.isTourTermine() ){ 
				afficherCartes();
				gestionBoutonsConstruction();
			}
		}

		scoreJoueur = new Score(joueur);
		stage.addActor(scoreJoueur);

	}

	private void gestionBoutonsConstruction() {
		//bouton pour retirer les cartes selectionnée
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
				idPile = 0;
				tPiles.setVisible(false);
				validerConstru.setVisible(false);
				fondChoixCartes.setVisible(false);
				messageConstruire = "";
				btnAnnulCarte.setVisible(false);
				super.touchUp(event, x, y, pointer, button);
			}

		});
		stage.addActor(btnAnnulCarte);

		//choisir la pile
		tPiles= new Table();
		final int nbpiles = joueur.getZone_construction().getNbPiles();
		TextButton btnP;
		for(int k =1; k<=nbpiles+1; k++){
			final int num =k;
			btnP= new TextButton(""+k, Buttons.styleInGameMenu);
			btnP.setSize(50, 50);
			btnP.addListener(new InputListener(){

				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					return true;
				}

				@Override
				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {
					//a changer !
					idPile =  num;
					if(num == nbpiles+1){
						idPile = 0;
					}

					System.out.println("pile choisie :"+ num);
					validerConstru.setVisible(true);
					super.touchUp(event, x, y, pointer, button);
				}

			});
			tPiles.add(btnP).row();
		}
		tPiles.setSize(50, 50);
		tPiles.setPosition(800, 500);
		tPiles.setVisible(false);
		stage.addActor(tPiles);

		//bouton de validation des choix
		validerConstru = new TextButton("OK", Buttons.styleInGameMenu);
		validerConstru.setSize(50, 50);
		validerConstru.setPosition(1000, 500);
		validerConstru.setVisible(false);
		validerConstru.addListener(new InputListener(){

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				Carte cPosee = joueur.getMainDuJoueur().choisirCarte(idCarteSelected);
				Carte cDefaussee = joueur.getMainDuJoueur().choisirCarte(idDefausseSelected);
				System.out.println("pile :"+idPile);
				erreur = joueur.construire(cPosee, cDefaussee, idPile);
				if(erreur.equals(GestionErreurs.NONE)){ //si aucune erreur, le tour est terminé
					londonG.partie.setActionChoisie(1);
					londonG.partie.setTourTermine(true);
				}
				Screen.setScreen(new ZoneConstructionScreen(erreur.getMsgErrorString())); 
				super.touchUp(event, x, y, pointer, button);
			}

		});

		stage.addActor(validerConstru);

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
					if(idCarteSelected!=0 && idDefausseSelected==0 && idCarteSelected!=c.getId_carte()){ //si on a deja select carte, mais pas la defausse
						ca.setY(ca.getyDefault()+350);
						ca.setX(600);
						ca.setSelected(true);
						idDefausseSelected = c.getId_carte();
						messageConstruire = "Choisir une pile";
						tPiles.setVisible(true);
					}else if (idCarteSelected==0){ //si on a selectionné aucune carte, on rend visible certains boutons/fonds
						ca.setY(ca.getyDefault()+350);
						ca.setX(300);
						ca.setSelected(true);
						idCarteSelected = c.getId_carte();
						if(c.isConstructible()){ //on verifie que la carte soit constructible
							btnAnnulCarte.setVisible(true);
							fondChoixCartes.setVisible(true);
							messageConstruire = "Choisir une carte à defausser";
						}else{
							messageConstruire = "Cette carte n'est pas constructible";
							btnAnnulCarte.setVisible(false);
							ca.setDefaultPosition();
							idCarteSelected=0;
						}

					}
					return super.touchDown(event, x, y, pointer, button);
				}

			});
			main.put(c.getId_carte(), ca);
		}
	}

	private void afficherPiles() {
		int left = 100;
		int i = 0;
		for(Carte pile : joueur.getZone_construction().cartesTop()){

			CarteActor ca = new CarteActor(pile, left+i*215, 360);
			System.out.println("carte dessus pile :" + pile.getNom());
			stage.addActor(ca);
			i++;
		}

	}

	@Override
	public void render() {
		spriteBatch.begin();
		draw(Art.bgPartie, 0, 0);
		Fonts.FONT_TITLE.draw(spriteBatch, "ZONE CONSTRUCTION", 200, 20);

		Fonts.FONT_BLACK.draw(spriteBatch, messageConstruire, 500, 100);

		spriteBatch.end();



		stage.act();
		stage.draw();



	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub

	}

}
