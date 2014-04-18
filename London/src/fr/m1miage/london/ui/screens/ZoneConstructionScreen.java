package fr.m1miage.london.ui.screens;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
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
	private int pileSelected = 0;
	private int idPile = 0;
	
	private List<CarteActor> cActorList= new ArrayList<CarteActor>();
	private List<CarteActor> cActorListColor=new ArrayList<CarteActor>();
	

	private AreaColorRect fondChoixCartes= new AreaColorRect(200, 320, 900, 350);

	public Map<Integer, CarteActor> main = new HashMap<Integer,CarteActor>();
	private Table tPiles;
	private List<PileActor> lPiles;
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
		//fondChoixCartes.setVisible(false);
		//fondChoixCartes.setShapeFillColor(1, 1, 1, 0.7f);

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
				//tPiles.setVisible(false);
				validerConstru.setVisible(false);
				fondChoixCartes.setVisible(false);
				messageConstruire = "";
				
				btnAnnulCarte.setVisible(false);
				super.touchUp(event, x, y, pointer, button);
			}

		});
		stage.addActor(btnAnnulCarte);

		//choisir la pile
		/*tPiles= new Table();
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
		}*/
		//tPiles.setSize(50, 50);
		//tPiles.setPosition(800, 500);
		//tPiles.setVisible(false);
		//stage.addActor(tPiles);

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

				System.out.println("pile "+pileSelected);
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
		if(pileSelected != 0 && idCarteSelected != 0 && idDefausseSelected !=0){
			validerConstru.setVisible(true);
		}
	}
	
	
	private void afficherCartesCouleurs(CarteActor c){
		for(final Integer i : main.keySet()){
			if(main.get(i).getCarte().getCouleur().compareTo(c.getCarte().getCouleur())!=0 && main.get(i).getId()!=c.getId()){
				cActorList.add(main.get(i));
				main.get(i).setVisible(false);
			}
			else if(main.get(i).getId()!=c.getId()){
				main.get(i).addListener(new InputListener(){
					 @Override
					public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {		
						 idDefausseSelected=main.get(i).getId();
						 //System.out.println(idDefausseSelected);
						 validerConstru.setVisible(true);
						 
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
			final Texture t = new Texture(Gdx.files.internal(Prefs.REPERTOIRE_CARTES+"validTarget.png"));
			final Texture tNew = new Texture(Gdx.files.internal(Prefs.REPERTOIRE_CARTES+"carteNew.png"));
			ca.addListener(new DragListener(){
				public void touchDragged (InputEvent event, float x, float y, int pointer) {
                    float dx = x-ca.getWidth()*0.5f; 
                    float dy = y-ca.getHeight()*0.5f; 
                    ca.setX(ca.getX()+dx);
                    ca.setY(ca.getY()+dy);
                	for(PileActor c : lPiles){
						if(c.inZone(ca) == true && c.empty()==true ){
							c.setImg(t);
						}
						else if(c.empty()==true) {
							
							c.setImg(tNew);
						}
					}
                    if(idCarteSelected!=0 && idDefausseSelected==0 && idCarteSelected!=c.getId_carte() && pileSelected != 0){ //si on a deja select carte, mais pas la defausse	
						
                    	ca.setSelected(true);
						messageConstruire = "Choisir une carte à défausser";
						idDefausseSelected = c.getId_carte();
						ca.setPosition(dx, dy);
						System.out.println("Carte à défausser : "+idDefausseSelected);
						validerConstru.setVisible(true);
					}else if (idCarteSelected==0){ //si on a selectionné aucune carte, on rend visible certains boutons/fonds
						ca.setSelected(true);
						idCarteSelected = c.getId_carte();
						System.out.println("Je sélectionne une carte : "+idCarteSelected);
						if(c.isConstructible()){ //on verifie que la carte soit constructible
							btnAnnulCarte.setVisible(true);
						}else{
							messageConstruire = "Cette carte n'est pas constructible";
							btnAnnulCarte.setVisible(false);
							ca.setDefaultPosition();
							idCarteSelected=0;
						}

					}
                    super.touchDragged(event, dx, dy, pointer);
                }
				
				public void touchUp(InputEvent event,float x,float y,int pointer,int button){
					PileActor pA = new PileActor();
					if(idCarteSelected != 0){
						Iterator<PileActor> pileI = lPiles.iterator();
						while(pileI.hasNext() && pileSelected == 0){
							pA = pileI.next();
							if(pA.inZone(ca) == true){
								
								ca.setX(pA.getX());
								ca.setY(pA.getY());
								pileSelected=pA.getId();
		                    	afficherCartesCouleurs(ca);	
								break;
									
								}
								if(pileSelected == 0){
									
									pileSelected = 0;
									//ca.setDefaultPosition();
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
		System.out.println("size : "+joueur.getZone_construction().cartesTop().size());
		for(Carte pileC : joueur.getZone_construction().cartesTop()){

			CarteActor ca = new CarteActor(pileC, left+i*215, 360);
			
			//System.out.println("carte dessus pile :" + pileC.getNom());
			stage.addActor(ca);
			
			final PileActor paLi = new PileActor(pileC, left+i*215, 360);
			Point p = new Point(left+i*215, 360);
	        paLi.sethGauche(p);
	        paLi.setbDroit(p);
	        //System.out.println("Haut gauche "+paLi.gethGauche());
	        //System.out.println("Bas droit  "+paLi.getbDroit());
	        paLi.setId(i);
			i++;
			stage.addActor(paLi);
			lPiles.add(paLi);
		}
        
        Carte pile = new Carte();
        final PileActor pa= new PileActor(pile, left+i*215, 360);
        Point p = new Point(left+i*215, 360);
        
        pa.sethGauche(p);
        pa.setbDroit(p);
        //System.out.println("Haut gauche new "+pa.gethGauche());
        //System.out.println("Bas droit new "+pa.getbDroit());
        pa.setId(i);
        lPiles.add(pa);
        stage.addActor(pa);
        Texture t = new Texture(Gdx.files.internal(Prefs.REPERTOIRE_CARTES+"carteNew.png"));
		
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
