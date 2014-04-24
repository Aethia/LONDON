package fr.m1miage.london.ui.screens;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import fr.m1miage.london.GestionErreurs;
import fr.m1miage.london.Regles;
import fr.m1miage.london.classes.Carte;
import fr.m1miage.london.classes.Joueur;
import fr.m1miage.london.ui.Prefs;
import fr.m1miage.london.ui.graphics.Art;
import fr.m1miage.london.ui.graphics.Buttons;
import fr.m1miage.london.ui.graphics.CarteActor;
import fr.m1miage.london.ui.graphics.Fonts;
import fr.m1miage.london.ui.graphics.Score;

public class ZoneRestaurerScreen extends Screen{
	private Stage stage;

	private TextButton btnRetour;
	private TextButton validerRestaurations;


	private List<CarteActor> main= new ArrayList<CarteActor>();
	TextButton activerBtn;
	private String messageAction = new String("");
	private GestionErreurs erreur;
	private Joueur joueur;

	private CarteActor carteActivation;
	private CarteActor carteDefausse;
	/* Scores */
	private Score scoreJoueur;

	public ZoneRestaurerScreen(String msg){
		messageAction = msg;
		joueur = londonG.partie.getObjJoueurActif();
		restaurerScreen();
	}
	

	private void restaurerScreen() {
		afficherRestaurer();

	}


	
	private void afficherRestaurer() {
		stage = new Stage(Prefs.LARGEUR_FENETRE, Prefs.HAUTEUR_FENETRE, false); 
		stage.clear();
		Gdx.input.setInputProcessor(stage);

		btnRetour =new TextButton("Retour",Buttons.styleInGameMenu); 
		btnRetour.setPosition(1100, 135); 
		btnRetour.addListener(new InputListener(){

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				// si c'est une partie multijoueur
				if (londonG.partie.isMultijoueur()) {
					if (GameScreenReseauClient.joueur!=null)
						Screen.setScreen(new GameScreenReseauClient(GameScreenReseauClient.joueur));
					else
						Screen.setScreen(new GameScreenReseauServeur());
				}
				else
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



		if(londonG.partie.getObjJoueurActif().equals(joueur)==true){ //si on est bien sur le joueur actif 
			//si tour terminé, mais action choisie => on peut continuer ou tour pas terminé mais zoneC du joueur Actif
			if((londonG.partie.isTourTermine()==true && londonG.partie.getActionChoisie()!=0) || !londonG.partie.isTourTermine() ){ 
				afficherPiles();
			}



		}
		mainJoueur();
		
		activerBtn = new TextButton("activer", Buttons.styleInGameMenu );
		activerBtn.setPosition(100, 250);
		activerBtn.setVisible(false);
		activerBtn.addListener(new InputListener(){

			@Override
			public boolean touchDown(InputEvent event,
					float x, float y, int pointer,
					int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x,
					float y, int pointer, int button) {
				if(carteDefausse == null){ //si le type d'activation = aucun ou livre, rien a defausser
					erreur =joueur.restaurerVille2(carteActivation.getCarte(), null, londonG.partie.getEtalage());
				}else{
					erreur =joueur.restaurerVille2(carteActivation.getCarte(), carteDefausse.getCarte(), londonG.partie.getEtalage());
				}

				if(!erreur.equals(GestionErreurs.NONE)){
					messageAction = erreur.getMsgErrorString();
				}else{
					validerRestaurations.setVisible(true);
					activation(carteActivation);
				}
				activerBtn.setVisible(false);
				carteActivation =null;
				carteDefausse = null;
				masquerMain();
				super.touchUp(event, x, y, pointer, button);
			}

		});
		stage.addActor(activerBtn);
		
		validerRestaurations = new TextButton("Valider les restaurations", Buttons.styleInGameMenu);
		validerRestaurations.setPosition(1100, 200);
		validerRestaurations.addListener(new InputListener(){

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				//calculer les points de pauvreté a la fin
				int nbPiles = joueur.getZone_construction().getNbPiles();
				int nbCartesMain = joueur.getNb_cartes();
				//regles du jeu : nombre de piles + cartes en main - nombre de quartiers 
				int ptPauvrete  = nbPiles + nbCartesMain - joueur.getNbQuartiers();
				joueur.setPoint_pauvrete(joueur.getPoint_pauvrete() + ptPauvrete);
				Screen.setScreen(new GameScreen());
				super.touchUp(event, x, y, pointer, button);
			}
			
		});
		validerRestaurations.setVisible(false);
		stage.addActor(validerRestaurations);
		
		scoreJoueur = new Score(joueur);
		stage.addActor(scoreJoueur);

		
	}


	private void mainJoueur(){ 

		int i=0;
		for(final Carte c: joueur.getLesCartes()){
			i++;
			final CarteActor ca = new CarteActor(c,350+i*50,10);	
			ca.addListener(new InputListener(){

				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {				
					if(carteDefausse!=null && carteDefausse==ca){ //si on a deja cliqué sur cette carte
						activerBtn.setVisible(false);
						carteDefausse.setDefaultPosition();
						carteDefausse =null;
					}else if(carteDefausse != null && carteDefausse!=ca){ //si on a cliqué sur une autre carte
						carteDefausse.setDefaultPosition();
						ca.setY(300);
						activerBtn.setVisible(true);
						carteDefausse = ca;
					}else if(carteDefausse ==null){
						carteDefausse = ca;
						ca.setY(300);
						activerBtn.setVisible(true);
					}
					return super.touchDown(event, x, y, pointer, button);
				}

			});
			ca.setVisible(false);
			main.add(ca);
			stage.addActor(ca);
		}
	}





	//si le type du jeu est restaurer, les cartes ont un listener
	private void afficherPiles() {
		int left = 100;
		int i = 0;
		for(Carte pile : joueur.getZone_construction().cartesTop()){
			final CarteActor ca = new CarteActor(pile, left+i*215, 360);
System.out.println("pile");
			ca.addListener(new InputListener(){

				@Override
				public boolean touchDown(InputEvent event, float x,
						float y, int pointer, int button) {
					if(!ca.getCarte().isDesactivee()){ //si c'est pas null et que la carte n'est pas desactivée
						carteActivation = ca;
						activerBtn.setPosition(ca.getX(), 250);
						System.out.println(ca.getCarte().getNom());
						Carte cActiv = carteActivation.getCarte();
						int type = cActiv.getCoutActivation().getTypeActiv();
						switch(type){
						case Regles.ACTIVATION_AUCUN :
							masquerMain();
							activerBtn.setText("Activer");
							activerBtn.setVisible(true);
							break;
						case Regles.ACTIVATION_LIVRES :
							masquerMain();
							//demander au joueur s'il veut payer pour activer
							activerBtn.setText("Payer £" + cActiv.getCoutActivation().getLivresAPayer());
							activerBtn.setVisible(true);
							break;
						case Regles.ACTIVATION_UNIQUE :
							activerBtn.setVisible(false);
							int nb = 0;
							nb = masquerCartes(cActiv.getCoutActivation().getCouleurADefausser());
							if(nb==0){
								messageAction = "Vous n'avez pas de carte à defausser";
							}else{
								messageAction = "choisir une carte" + cActiv.getCoutActivation().getCouleurADefausser();
								messageAction+= " à défausser";
							}

							break;
						case Regles.ACTIVATION_ANYCOLOR :
							activerBtn.setVisible(false);
							nb = main.size();
							if(nb==0){
								messageAction = "Vous n'avez pas de carte à defausser";
							}else{
								messageAction = "choisir une carte de n'importe quelle couleur à défausser";
								afficherMain();
							}

							break;
						}
					}//fin if
					return false;
				}

			});


i++;
			stage.addActor(ca);
		}

	}

	private void activation(CarteActor ca){
		Carte c=ca.getCarte();
		londonG.partie.setActionChoisie(Regles.RESTAURER);
		londonG.partie.setTourTermine(true);
		if(c.isDesactivee()){
			ca.setDisabled();
		}
		stage.getRoot().removeActor(scoreJoueur);
		scoreJoueur = new Score(joueur);
		stage.addActor(scoreJoueur);
	}

	private int masquerCartes(String couleurAafficher){
		int nb=0;
		for(CarteActor ca : main){
			if(!ca.getCarte().getCouleur().equals(couleurAafficher)){
				ca.setVisible(false);
			}else{
				ca.setVisible(true);
				nb++;
			}
		}
		return nb++;
	}
	private void afficherMain(){
		for(CarteActor ca : main){
			ca.setVisible(true);
		}
	}

	private void masquerMain(){
		for(CarteActor ca : main){
			ca.setVisible(false);
		}
	}
	@Override
	public void render() {
		spriteBatch.begin();
		draw(Art.bgPartie, 0, 0);
		Fonts.FONT_TITLE.draw(spriteBatch, "RESTAURER", 200, 20);

		Fonts.FONT_BLACK.draw(spriteBatch, messageAction, 500, 100);

		spriteBatch.end();



		stage.act();
		stage.draw();



	}


	@Override
	public void tick() {
		// TODO Auto-generated method stub

	}



}
