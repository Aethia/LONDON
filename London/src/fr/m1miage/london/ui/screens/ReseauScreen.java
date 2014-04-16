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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;

import fr.m1.miage.london.network.IncomingListenerClient;
import fr.m1.miage.london.network.IncomingListenerServeur;
import fr.m1.miage.london.network.client.Client;
import fr.m1.miage.london.network.serveur.Reception;
import fr.m1.miage.london.network.serveur.Serveur;
import fr.m1miage.london.ui.Prefs;
import fr.m1miage.london.ui.graphics.Art;
import fr.m1miage.london.ui.graphics.Buttons;
import fr.m1miage.london.ui.graphics.Fonts;

public class ReseauScreen extends Screen{
	
	/**
	 * Listener du reseau (client).
	 */

	private IncomingListenerClient reseauClientListener = new IncomingListenerClient(){

		@Override
		public void nouveauMessage(String message) {
			listeMessage.concat("\n" + message);
		}


		
	};
	
	/**
	 * Listener du reseau (serveur).
	 */

	private IncomingListenerServeur reseauServeurListener = new IncomingListenerServeur(){

		@Override
		public void nouveauMessage(String message) {
			listeMessage.concat("\n" + message);
		}


		
	};

		
	private Stage stage; 

	private String listeMessage= new String("Chat reseau \n");
	private ShapeRenderer fondChat;

	private int type =0;
	
	public ReseauScreen(){
		Reception.addListener(reseauServeurListener);
		stage = new Stage(Prefs.LARGEUR_FENETRE, Prefs.HAUTEUR_FENETRE, false); 
		stage.clear();
		Gdx.input.setInputProcessor(stage);

		fondChat = new ShapeRenderer();


		TextButton btnRetour =new TextButton("Retour",Buttons.styleInGameMenu); 
		btnRetour.setPosition(1100, 135); 
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


		TextButton btnCreerPartie =new TextButton("Créer une partie",Buttons.styleInGameMenu); 
		btnCreerPartie.setPosition(100, 600); 
		btnCreerPartie.addListener(new InputListener(){
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				//traitement reseau
				type=1;
				Serveur srv = new Serveur();
				srv.hebergerPartie();
				System.out.println("wut");
				super.touchUp(event, x, y, pointer, button);
			}

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}
		});
		stage.addActor(btnCreerPartie);

		TextButton btnRejoindrePartie =new TextButton("Rejoindre une partie",Buttons.styleInGameMenu); 
		btnRejoindrePartie.setPosition(100, 500); 
		btnRejoindrePartie.addListener(new InputListener(){
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				//traitement reseau
				type=2;
				Client cli = new Client();
				cli.seConnecter();
				super.touchUp(event, x, y, pointer, button);
			}

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}
		});
		stage.addActor(btnRejoindrePartie);


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
		mTextField.setPosition(150 , 200);
		mTextField.setHeight(70);
		mTextField.setWidth(500);
		mTextField.addListener(new InputListener(){

			@Override
			public boolean keyDown(InputEvent event, int keycode) {
				if(keycode==66){
					if(type==1){
//					for( Chat_ClientServeur chat : Serveur.lesClients){
//						chat.sendMsg("serveur : "+mTextField.getText());
//					}
					
					}
					if(type==2){
						
					}
			
					mTextField.setText("");
				}
				return super.keyDown(event, keycode);
			}
			
		});
		stage.addActor(mTextField);
	}
	

	@Override
	public void render() {
		spriteBatch.begin();
		draw(Art.bgPartie, 0, 0);
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
