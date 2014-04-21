package fr.m1miage.london.ui.screens;

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
import com.badlogic.gdx.scenes.scene2d.utils.Align;

import fr.m1.miage.london.network.IncomingMessageListenerClient;
import fr.m1.miage.london.network.IncomingObjectListenerClient;
import fr.m1.miage.london.network.client.Reception;
import fr.m1.miage.london.network.client.Sender;
import fr.m1miage.london.Partie;
import fr.m1miage.london.ui.Prefs;
import fr.m1miage.london.ui.graphics.Art;
import fr.m1miage.london.ui.graphics.Buttons;
import fr.m1miage.london.ui.graphics.Chat;
import fr.m1miage.london.ui.graphics.Fonts;

public class ChatReseauScreenClient extends Screen implements IncomingMessageListenerClient,IncomingObjectListenerClient{

	@Override
	public void nouveauMessage(String message) {
		//Screen.setScreen(new MainMenuScreen());
		System.out.println("message recu coté ui (cli)," + message);
		System.out.println("nouveau :" + message);
		messageChat(message);
	}	

	
	@Override
	public void nouvelObjet(Object o, int type) {	
		if (type == 3) {
			londonG.partie = (Partie)o;		
			Screen.setScreen(new GameScreenReseauClient(login,joueurActif));	
		}
		if (type == 4) {
			this.joueurActif = (String)o;
		}
	}


	private String joueurActif;
	private Stage stage; 
	private String login;
	private String listeMessage= new String("Chat reseau \n");
	private ShapeRenderer fondChat;
	private TextButton btnLancerPartie;
	private InputListener list;
	private Chat chat;
	private int cPosition=0;
	


	public ChatReseauScreenClient(String log){
		Reception.addListenerM(this);
		Reception.addListenerO(this);
		this.login = log;
		stage = new Stage(Prefs.LARGEUR_FENETRE, Prefs.HAUTEUR_FENETRE, false); 
		stage.clear();
		Gdx.input.setInputProcessor(stage);

		fondChat = new ShapeRenderer();




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


		//creation des textfields
		final TextField mTextField = new TextField("", txtStyle);
		mTextField.setPosition(400 , 120);
		mTextField.setHeight(70);
		mTextField.setWidth(850);
		mTextField.setMaxLength(50);
		mTextField.addListener(new InputListener(){

			@Override
			public boolean keyUp(InputEvent event, int keycode) {
				if(keycode==66){
					if(Sender.e==null){
						System.out.println("wtf");
					}else{
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



	}
	
	private void messageChat(String message){
		System.out.println(message);

		Label temp = new Label(message, Art.skin);
		temp.setAlignment(Align.left,Align.left);
		temp.setWrap(true);
		temp.setColor(Color.BLACK);
		chat.add(temp).colspan(0).row();

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