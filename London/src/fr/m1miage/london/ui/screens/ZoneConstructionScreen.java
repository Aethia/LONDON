package fr.m1miage.london.ui.screens;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;

import fr.m1miage.london.GestionErreurs;
import fr.m1miage.london.classes.Carte;
import fr.m1miage.london.classes.Joueur;
import fr.m1miage.london.ui.Prefs;
import fr.m1miage.london.ui.graphics.AreaColorRect;
import fr.m1miage.london.ui.graphics.Art;
import fr.m1miage.london.ui.graphics.Buttons;
import fr.m1miage.london.ui.graphics.CarteActor;
import fr.m1miage.london.ui.graphics.Fonts;
import fr.m1miage.london.ui.graphics.PileActor;
import fr.m1miage.london.ui.graphics.Score;

public class ZoneConstructionScreen extends Screen{
	private Stage stage;

	private TextButton btnRetour;
	private TextButton btnAnnulCarte;

	private TextButton validerConstru;

	private int idCarteSelected =0;
	private int idDefausseSelected =0;
	private int pileSelected = -1;
	private CarteActor cDefausse;

	private List<CarteActor> cActorList= new ArrayList<CarteActor>();
	//private List<CarteActor> cActorListColor=new ArrayList<CarteActor>();


	private AreaColorRect fondChoixCartes= new AreaColorRect(950, 330, 300, 350);


	public Map<Integer, CarteActor> main = new HashMap<Integer,CarteActor>();
	//private Table tPiles;
	private List<PileActor> lPiles;
	private String messageConstruire = new String("");
	private GestionErreurs erreur;
	private Joueur joueur;
	public static String log,joueurActif,sender=null;

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
	
	public ZoneConstructionScreen(String msg,String login,String joueurActif,String sender){
		messageConstruire = msg;
		this.log = login;
		this.joueurActif = joueurActif;
		this.sender = sender;
		joueur = londonG.partie.getObjJoueurActif();
		constructionScreen();
	}
	
	public ZoneConstructionScreen(String login,String joueurActif,String sender){
		this.log = login;
		this.joueurActif = joueurActif;
		this.sender = sender; 
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
				// si c'est une partie multijoueur
				if (ZoneConstructionScreen.log != null) {
					if (ZoneConstructionScreen.sender.equals("client"))
						Screen.setScreen(new GameScreenReseauClient(ZoneConstructionScreen.log, ZoneConstructionScreen.joueurActif));
					else
						Screen.setScreen(new GameScreenReseauServeur(ZoneConstructionScreen.joueurActif));
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

		afficherPiles();

		stage.addActor(fondChoixCartes);
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
				cacherCartes();
				idCarteSelected=0;
				idDefausseSelected=0;
				cDefausse=null;
				pileSelected=-1;

				validerConstru.setVisible(false);
				fondChoixCartes.setVisible(false);
				messageConstruire = "";

				btnAnnulCarte.setVisible(false);
				super.touchUp(event, x, y, pointer, button);
			}

		});
		stage.addActor(btnAnnulCarte);


		//bouton de validation des choix
		validerConstru = new TextButton("Valider construction", Buttons.styleInGameMenu);
		validerConstru.setSize(200, 50);
		validerConstru.setPosition(1000, 280);
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

				erreur = joueur.construire(cPosee, cDefaussee, pileSelected+1, londonG.partie.getPlateau().getEtalage());
				if(erreur.equals(GestionErreurs.NONE)){ //si aucune erreur, le tour est terminé
					londonG.partie.setActionChoisie(1);
					londonG.partie.setTourTermine(true);
				}
				Screen.setScreen(new ZoneConstructionScreen(erreur.getMsgErrorString())); 
				super.touchUp(event, x, y, pointer, button);
			}

		});


		stage.addActor(validerConstru);
		if(pileSelected != -1 && idCarteSelected != 0 && idDefausseSelected !=0){
			validerConstru.setVisible(true);
		}
	}


	private void afficherCartesCouleurs(CarteActor c){ //Méthode qui va afficher les cartes à défausser possibles
		messageConstruire="Choisissez une carte à défausser";
		for(final Integer i : main.keySet()){
			//Si la carte n'a pas la même couleur que la carte choisie, on va mettre son setVisible à false
			if(main.get(i).getCarte().getCouleur().compareTo(c.getCarte().getCouleur())!=0 && main.get(i).getId()!=c.getId()){
				cActorList.add(main.get(i));
				main.get(i).setVisible(false);
			}

			//sinon, et si ce n'est pas la carte choisie, on met un input listener 
			else if(main.get(i).getId()!=c.getId()){

				main.get(i).addListener(new InputListener(){

					@Override
					public void enter(InputEvent event, float x, float y,
							int pointer, Actor fromActor) {
						//L'inputListener ne doit agir que sur les cartes à défausser
						if(idCarteSelected!=0 && main.get(i).getId() != idCarteSelected){
							if(cDefausse != null && idDefausseSelected == 0){
								cDefausse.setDefaultPosition();
							}
							if(idDefausseSelected==0){
								cDefausse=main.get(i);
								main.get(i).setY(110);
							}
						}

						super.enter(event, x, y, pointer, fromActor);


					}



					@Override
					public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {		
						//On va sélectionner la carte à défausser (on peut changer avec le clic) 
						if(idCarteSelected!=0 && main.get(i).getId() != idCarteSelected){	 
							if(idDefausseSelected != 0 && cDefausse != null){
								cDefausse.setDefaultPosition();
							}
							cDefausse=main.get(i);	
							idDefausseSelected=main.get(i).getId();
							fondChoixCartes.setVisible(true);
						 	main.get(i).setX(1000);
						 	main.get(i).setY(360);
						 	//On peut affiche le bouton pour valider
						 	validerConstru.setVisible(true);
						 }

						 super.touchDown(event, x, y, pointer, button);
						 return true;
					}
				});


			}
		}
	}


	private void afficherCartes() {
		int i=0;

		for(final Carte c: joueur.getLesCartes()){

			i++;
			final CarteActor ca = new CarteActor(c,350+i*50,10);
			stage.addActor(ca);
			final Texture t = Art.validTarget;
			final Texture tNew = Art.carteEtalage;
			ca.addListener(new DragListener(){
				public void touchDragged (InputEvent event, float x, float y, int pointer) {
                    float dx = x-ca.getWidth()*0.5f; 
                    float dy = y-ca.getHeight()*0.5f; 
                    ca.setX(ca.getX()+dx);
                    ca.setY(ca.getY()+dy);
                	for(PileActor c : lPiles){
                		//Si la carte se trouve dans l'une des zones de la pile, on met une autre texture
						if(c.inZone(ca) == true && c.empty()==true ){
							c.setImg(t);
						}
						else if(c.empty()==true) {

							c.setImg(tNew);
						}
					}
                	if (idCarteSelected==0){ //si on a selectionné aucune carte, on rend visible certains boutons/fonds
						idCarteSelected = c.getId_carte();

					}
                    super.touchDragged(event, dx, dy, pointer);
                }



				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					if(c.isConstructible()==false){
						messageConstruire = "Cette carte n'est pas constructible";
						ca.setDefaultPosition();
						idCarteSelected=0;
					}
					return super.touchDown(event, x, y, pointer, button);
				}



				public void touchUp(InputEvent event,float x,float y,int pointer,int button){
					PileActor pA = new PileActor();
					if(idCarteSelected != 0){
						Iterator<PileActor> pileI = lPiles.iterator();
						while(pileI.hasNext() && pileSelected < 0){
							//On va parcourir chaque pile pour vérifier si la carte se trouve dans une des zones
							pA = pileI.next();
							if(pA.inZone(ca) == true){
								ca.setX(pA.getX());
								ca.setY(pA.getY());
								pileSelected=pA.getId();
								btnAnnulCarte.setX(ca.getX()+75);
								btnAnnulCarte.setVisible(true);

		                    	afficherCartesCouleurs(ca);	


								}
							if(pileSelected < 0 && pileI.hasNext()==false){
								//Si à la fin du parcours, elle ne se trouve dans aucune pile, on la remet à sa place
								ca.setDefaultPosition();
								}
						}
					}
					super.touchUp(event, x, y, pointer, button);
				}

		});

			main.put(c.getId_carte(), ca);

		}
	}

	private void cacherCartes(){		
		for(CarteActor c : cActorList){
			c.setVisible(true);
		}
		cActorList.clear();
	}

	private void afficherPiles() {
		int left = 100;
		int i = 0;
		lPiles = new ArrayList<PileActor>();
		for(Carte pileC : joueur.getZone_construction().cartesTop()){
			CarteActor ca = new CarteActor(pileC, left+i*215, 360);
			stage.addActor(ca);

			final PileActor paLi = new PileActor(pileC, left+i*215, 360);
			Point p = new Point(left+i*215, 360);
	        paLi.sethGauche(p);
	        paLi.setbDroit(p);
	        paLi.setId(i);
			i++;
			stage.addActor(paLi);
			lPiles.add(paLi);
		}
        //Permet de rajouter une pile fictive lorsqu'on veut ajouter une pile à la zone de construction
        Carte pile = new Carte();
        final PileActor pa= new PileActor(pile, left+i*215, 360);
        Point p = new Point(left+i*215, 360);
        
        pa.sethGauche(p);
        pa.setbDroit(p);
        pa.setId(i);
        lPiles.add(pa);
        stage.addActor(pa);
        Texture t = Art.carteEtalage;

        pa.setImg(t);   
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