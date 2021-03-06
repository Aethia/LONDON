package fr.m1miage.london.ui.screens;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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

import fr.m1.miage.london.network.IncomingMessageListenerClient;
import fr.m1.miage.london.network.IncomingObjectListenerClient;
import fr.m1.miage.london.network.client.Reception;
import fr.m1.miage.london.network.client.Sender;
import fr.m1miage.london.classes.Joueur;
import fr.m1miage.london.sound.SoundPlayer;
import fr.m1miage.london.ui.Prefs;
import fr.m1miage.london.ui.graphics.Art;
import fr.m1miage.london.ui.graphics.Buttons;
import fr.m1miage.london.ui.graphics.Chat;
import fr.m1miage.london.ui.graphics.Fonts;
import fr.m1miage.london.Partie;

public class ChatReseauScreenClient extends Screen implements IncomingMessageListenerClient{

	@Override
	public void nouveauMessage(String message) {
		//Screen.setScreen(new MainMenuScreen());
		System.out.println("message recu coté ui (cli)," + message);
		System.out.println("nouveau :" + message);
		messageChat(message);
	}	

	private IncomingObjectListenerClient objListener = new IncomingObjectListenerClient(){
		@Override
		public void nouvelObjet(Object o, int type) {	
			System.out.println("type de la reception : " + type);
			if(type==5){
				londonG.partie = (Partie) o;
				afficherbouton();
				SoundPlayer.jouerSon("alabataille.wav");
				
			}
			if (type == 3) {
				afficherbouton();
				SoundPlayer.jouerSon("alabataille.wav");
				
			}
			if (type == 4) {
				joueurActif = (String)o;
			}
			if (type == 0){
				lesJoueurs = (ArrayList<Joueur>)o;
			}

		}
	};
	

public void changer(){
	
}


	private List<Joueur> lesJoueurs = new ArrayList<Joueur>();
	private String joueurActif;
	private Stage stage; 
	private String login;
	private String listeMessage= new String("Chat reseau \n");
	private ShapeRenderer fondChat;
	private TextButton btnLancerPartie;
	private InputListener list;
	private Chat chat;
	private int jPosition=200;
	private int cPosition=0;
private ChatReseauScreenClient aFermer;
	public void afficherbouton(){
		btnLancerPartie.setVisible(true);
	}


	public ChatReseauScreenClient(String log){
		aFermer = this;
		Reception.addListenerM(this);
		Reception.addListenerO(objListener);
		this.login = log;
		stage = new Stage(Prefs.LARGEUR_FENETRE, Prefs.HAUTEUR_FENETRE, false); 
		stage.clear();
		Gdx.input.setInputProcessor(stage);

		fondChat = new ShapeRenderer();
		/*
		 * bouton lancer partie
		 */
		btnLancerPartie =new TextButton("Lancer partie",Buttons.styleInGameMenu); 
		btnLancerPartie.setPosition(100, 600); 
		btnLancerPartie.setVisible(false);
		btnLancerPartie.addListener(new InputListener(){
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				//lancement de la partie
				super.touchUp(event, x, y, pointer, button);			
				SoundPlayer.jouerSon("lancement.wav");
				Screen.setScreen(new GameScreenReseauClient(londonG.partie.getJoueurParNom(login)));
			}

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}
		});
		stage.addActor(btnLancerPartie);


		/*
		 * bouton retour
		 */
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


		/*
		 * le textfield
		 */
		final TextField mTextField = new TextField("", txtStyle);
		mTextField.setPosition(400 , 120);
		mTextField.setHeight(70);
		mTextField.setWidth(850);
		mTextField.setMaxLength(40);
		mTextField.addListener(new InputListener(){

			@Override
			public boolean keyUp(InputEvent event, int keycode) {
				if(keycode==66){
					if(Sender.e!=null){
						Sender.e.sendMessageString(login+" : "+mTextField.getText());
						mTextField.setText("");
					}
				}
				return super.keyUp(event, keycode);
			}

		});
		stage.addActor(mTextField);

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

				Sender.e.sendMessageString(login+" : "+mTextField.getText());
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


		// on demande à recupérer les participants
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				Sender.e.sendObject(0, null);
			}
		}, 3000);




	}

	private void messageChat(String message){
		System.out.println(message);

		if(message.contains(" : ")){
			String[] msg = message.split(" : ");
			Label proprietaire =new Label(msg[0], Art.skin);
			for(Joueur j : lesJoueurs){
				if(msg[0].equals(j.getNom())){
					proprietaire.setColor(Prefs.conversionCouleur(j.getCouleur()));
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

	@Override
	public void render() {
		spriteBatch.begin();
		draw(Art.bgPartie, 0, 0);
		Fonts.FONT_TITLE.draw(spriteBatch, "RESEAU", 500, 20);
		if(!chat.isOverTable()){
			chat.getSPChat().setScrollY(cPosition);
		}

		Fonts.FONT_BLACK.draw(spriteBatch, "Joueurs connectés", 1180, 150);




		Fonts.FONT_BLACK.draw(spriteBatch, "hôte", 1200, 200);

		for (Joueur e : lesJoueurs){
			jPosition = jPosition- 35;
			Label l = new Label(e.getNom(),Art.skin);
			java.awt.Color c = e.getCouleur();
			c.getRed();

			Color color = new Color((float)c.getRed()/255,(float)c.getGreen()/255,(float)c.getBlue()/255,1);
			l.setColor(color);
			l.setPosition(1200, jPosition);
			stage.addActor(l);
			//Fonts.FONT_BLACK.draw(spriteBatch, e.getJoueur().getNom(), 1200, jPosition);
		}
		jPosition = 565;

		spriteBatch.end();
		Gdx.gl.glEnable(GL10.GL_BLEND);
		Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		fondChat.begin(ShapeType.Filled);
		fondChat.setColor(new Color(1, 1, 1, 0.7f));
		fondChat.rect(400, 200, 700, 450);
		fondChat.end();
		Gdx.gl.glDisable(GL10.GL_BLEND);

		spriteBatch.begin();


		Fonts.FONT_BLACK.draw(spriteBatch, listeMessage, 420, 200);



		spriteBatch.end();
		stage.act();
		stage.draw();

	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub

	}






}