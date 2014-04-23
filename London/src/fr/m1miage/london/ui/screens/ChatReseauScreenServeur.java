package fr.m1miage.london.ui.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;

import fr.m1.miage.london.network.IncomingMessageListenerServeur;
import fr.m1.miage.london.network.serveur.Emission;
import fr.m1.miage.london.network.serveur.Reception;
import fr.m1.miage.london.network.serveur.Serveur;
import fr.m1miage.london.Partie;
import fr.m1miage.london.classes.Joueur;
import fr.m1miage.london.ui.Prefs;
import fr.m1miage.london.ui.graphics.Art;
import fr.m1miage.london.ui.graphics.Buttons;
import fr.m1miage.london.ui.graphics.Chat;
import fr.m1miage.london.ui.graphics.Fonts;

public class ChatReseauScreenServeur extends Screen implements IncomingMessageListenerServeur{



	/**
	 * Listener du reseau (serveur).
	 */

	@Override
	public void nouveauMessage(String message) {
		//Screen.setScreen(new MainMenuScreen());
		messageChat(message);
	}	



	private Stage stage; 

	private ShapeRenderer fondChat;
	private int jPosition=200;

	private Chat chat;
	private int cPosition=0;

	public ChatReseauScreenServeur(){
		Reception.addListener(this);
		stage = new Stage(Prefs.LARGEUR_FENETRE, Prefs.HAUTEUR_FENETRE, false); 
		stage.clear();
		Gdx.input.setInputProcessor(stage);

		fondChat = new ShapeRenderer();

		//zone de texte
		Skin menuSkin = new Skin();
		TextureAtlas menuAtlas = new TextureAtlas("ressources/Images/text_area.pack");
		menuSkin.addRegions(menuAtlas);
		TextFieldStyle txtStyle = new TextFieldStyle();
		txtStyle.background = menuSkin.getDrawable("Area");
		txtStyle.font = new BitmapFont(Gdx.files.internal("ressources/Fnt/font_quartiers.fnt"), false);
		txtStyle.fontColor = Color.BLACK;
		txtStyle.background.setBottomHeight(32f);
		txtStyle.background.setLeftWidth(10f);

		//creation des textfields
		final TextField mTextField = new TextField("", txtStyle);
		mTextField.setPosition(400 , 120);
		mTextField.setHeight(70);
		mTextField.setWidth(850);
		mTextField.setMaxLength(40);
		mTextField.addListener(new InputListener(){

			@Override
			public boolean keyUp(InputEvent event, int keycode) {
				if(keycode==66){
					// on envoie le message au clients
					for (Emission e : Serveur.lesClients){
						e.sendMessageString("hôte : "+mTextField.getText());
					}
					messageChat("hôte : "+mTextField.getText());
					mTextField.setText("");
				}
				return super.keyUp(event, keycode);
			}

		});
		stage.addActor(mTextField);

		TextButton btnRetour =new TextButton("Retour",Buttons.styleInGameMenu); 
		btnRetour.setPosition(100, 135); 
		btnRetour.addListener(new InputListener(){
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				Screen.setScreen(new MainMenuScreen());
				super.touchUp(event, x, y, pointer, button);
			}

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}
		});
		stage.addActor(btnRetour);


		TextButton btnLancerPartie =new TextButton("Lancer partie",Buttons.styleInGameMenu); 
		btnLancerPartie.setPosition(100, 600); 
		btnLancerPartie.addListener(new InputListener(){
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				//lancement de la partie
				super.touchUp(event, x, y, pointer, button);

				// on récupère les joueurs
				ArrayList<Joueur> listeJoueurs = new ArrayList<Joueur>();
				Joueur j = new Joueur(0, "host", java.awt.Color.BLUE);
				listeJoueurs.add(j);
				int i=1;
				for(Emission cli : Serveur.lesClients){

					j = new Joueur(i++, cli.getJoueur().getNom(), cli.getJoueur().getCouleur());
					listeJoueurs.add(j);
				}


				// on lance la partie
				londonG.partie = new Partie(listeJoueurs,listeJoueurs.size());
				londonG.partie.init();
				londonG.partie.setMultijoueur(true);
				
				
				String joueurActif= londonG.partie.getObjJoueurActif().getNom();
				// on envoie le joueur
				for (Emission e : Serveur.lesClients){		
					Object o = (String)joueurActif;
					int type = 4;
					e.sendObject(type, o);
				}



				// on distribue les cartes à tout le monde
				for (Emission e : Serveur.lesClients){		
					Object partie = londonG.partie;
					e.sendObjectPartie(partie);
				}






				Screen.setScreen(new GameScreenReseauServeur());	


			}

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}
		});
		stage.addActor(btnLancerPartie);

		/*
		 * bouton envoyer (pour le chat)
		 */
		TextButton btnEnvoyer =new TextButton("Envoyer",Buttons.styleInGameMenu); 
		btnEnvoyer.setPosition(1100, 100); 
		btnEnvoyer.addListener(new InputListener(){
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				// code event
				// on envoie le message au clients
				for (Emission e : Serveur.lesClients){
					e.sendMessageString("hôte : "+mTextField.getText());
				}
				messageChat("hôte : "+mTextField.getText());
				mTextField.setText("");
				super.touchUp(event, x, y, pointer, button);
			}

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}
		});
		stage.addActor(btnEnvoyer);


		//creation chat
		chat = new Chat(Art.skin);
		stage.addActor(chat.getSPChat());


		/*
		 * On lance le serveur (mais pas trop loin)
		 */
		Serveur srv = new Serveur();
		srv.hebergerPartie();
	}

	private void messageChat(String message){
		System.out.println(message);

		if(message.contains(" : ")){
			System.out.println("huehue "+message);
			String[] msg = message.split(" : ");
			System.out.println(msg[0]);
			System.out.println(msg[1]);
			Label proprietaire =new Label(msg[0], Art.skin);
			for(Emission e : Serveur.lesClients){
				System.out.println(msg[0] + e.getJoueur().getNom());
				if(msg[0].equals(e.getJoueur().getNom())){
					proprietaire.setColor(Prefs.conversionCouleur(e.getJoueur().getCouleur()));
				}
			}


			Label temp = new Label(": " +msg[1], Art.skin);
			temp.setColor(Color.BLACK);
			chat.add(proprietaire).colspan(0);
			chat.add(temp).colspan(2).padLeft(175f).row();

		}else{
			Label temp = new Label(message, Art.skin);
			temp.setColor(Color.BLACK);
			chat.add(temp).colspan(0).row();
		}

		cPosition=cPosition+100;
	}

	//	private void messageChat(String message){
	//		System.out.println(message);
	//
	//		Label temp = new Label(message, Art.skin);
	//		temp.setAlignment(Align.left,Align.left);
	//		temp.setWrap(true);
	//		temp.setColor(Color.BLACK);
	//		chat.add(temp).colspan(0).row();
	//
	//		cPosition=cPosition+100;
	//	}

	@Override
	public void render() {
		spriteBatch.begin();
		draw(Art.bgPartie, 0, 0);

		if(!chat.isOverTable()){
			chat.getSPChat().setScrollY(cPosition);
		}

		Fonts.FONT_BLACK.draw(spriteBatch, "Joueurs connectés", 1180, 150);

		Fonts.FONT_BLACK.draw(spriteBatch, "hôte", 1200, 200);

		for (Emission e : Serveur.lesClients){
			jPosition = jPosition- 35;
			Label l = new Label(e.getJoueur().getNom(),Art.skin);
			java.awt.Color c = e.getJoueur().getCouleur();
			c.getRed();

			Color color = new Color((float)c.getRed()/255,(float)c.getGreen()/255,(float)c.getBlue()/255,1);
			l.setColor(color);
			l.setPosition(1200, jPosition);
			stage.addActor(l);
			//Fonts.FONT_BLACK.draw(spriteBatch, e.getJoueur().getNom(), 1200, jPosition);
		}
		jPosition = 565;

		Fonts.FONT_TITLE.draw(spriteBatch, "RESEAU", 500, 20);
		spriteBatch.end();
		Gdx.gl.glEnable(GL10.GL_BLEND);
		Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		fondChat.begin(ShapeType.Filled);
		fondChat.setColor(new Color(1, 1, 1, 0.7f));
		fondChat.rect(400, 200, 700, 450);
		fondChat.end();
		Gdx.gl.glDisable(GL10.GL_BLEND);

		spriteBatch.begin();
		//maj a deplacer


		//	Fonts.FONT_BLACK.draw(spriteBatch, listeMessage, 420, 200);



		spriteBatch.end();
		stage.act();
		stage.draw();

	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub

	}

}
