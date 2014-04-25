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
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import fr.m1.miage.london.network.client.Client;
import fr.m1miage.london.classes.Joueur;
import fr.m1miage.london.sound.SoundPlayer;
import fr.m1miage.london.ui.Prefs;
import fr.m1miage.london.ui.graphics.AreaColorRect;
import fr.m1miage.london.ui.graphics.Art;
import fr.m1miage.london.ui.graphics.Buttons;
import fr.m1miage.london.ui.graphics.Fonts;

public class ChoixModeReseauScreen extends Screen{
	


	private Stage stage; 

	private ShapeRenderer fondChat;

	private AreaColorRect colorJ;
	
	public ChoixModeReseauScreen(){
		stage = new Stage(Prefs.LARGEUR_FENETRE, Prefs.HAUTEUR_FENETRE, false); 
		stage.clear();
		Gdx.input.setInputProcessor(stage);

		fondChat = new ShapeRenderer();


		TextButton btnRetour =new TextButton("Retour",Buttons.styleInGameMenu); 
		btnRetour.setPosition(600, 135); 
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
		btnCreerPartie.setPosition(600, 600); 
		btnCreerPartie.addListener(new InputListener(){
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				//traitement reseau
				// event serveur
				SoundPlayer.jouerSon("clic.wav");
				Screen.setScreen(new ChatReseauScreenServeur());
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
		btnRejoindrePartie.setPosition(600, 500); 
		btnRejoindrePartie.addListener(new InputListener(){
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
					// event client
				colorJ = new AreaColorRect(650,380 , 100f, 100f);
				colorJ.setShapeFillColor((float)Math.random(), (float)Math.random(), (float)Math.random(),1.0f);
				colorJ.setPosition(650, 380);
				colorJ.setHeight(100); colorJ.setWidth(100);
				colorJ.addListener(new InputListener(){

					@Override
					public boolean touchDown(InputEvent event, float x, float y,
							int pointer, int button) {
						// Si click sur acteur, on change la couleur de la forme
						stage.getRoot().removeActor(colorJ);
						colorJ.setShapeFillColor((float)Math.random(), (float)Math.random(), (float)Math.random(), 1.0f);
						stage.addActor(colorJ);
						return super.touchDown(event, x, y, pointer, button);
					}

				});
				//ajout des nouveaux elements au stage
				stage.addActor(colorJ);
				
				
				

				Skin menuSkin = new Skin();
				TextureAtlas menuAtlas = new TextureAtlas("ressources/Images/text_area.pack");
				menuSkin.addRegions(menuAtlas);
				TextFieldStyle txtStyle = new TextFieldStyle();
				txtStyle.background = menuSkin.getDrawable("Area");
				txtStyle.font = new BitmapFont(Gdx.files.internal("ressources/Fnt/font_quartiers.fnt"), false);
				txtStyle.fontColor = Color.BLACK;
				txtStyle.background.setBottomHeight(32f);
				txtStyle.background.setLeftWidth(10f);
				
				
				final TextField textBoxPseudo = new TextField("pseudo", txtStyle);
				
				
				
				textBoxPseudo.setPosition(580 , 300);
				textBoxPseudo.setHeight(70);
				textBoxPseudo.setWidth(300);
				textBoxPseudo.addListener(new ClickListener(){
					 @Override
					 public void clicked(InputEvent event, float x, float y) {
						 textBoxPseudo.setText("");
					 
					 }
				});
				stage.addActor(textBoxPseudo);
				
				final TextField textBoxIp = new TextField("127.0.0.1", txtStyle);
				textBoxIp.setPosition(580 , 250);
				textBoxIp.setHeight(70);
				textBoxIp.setWidth(300);
				textBoxIp.addListener(new ClickListener(){
					 @Override
					 public void clicked(InputEvent event, float x, float y) {
						 textBoxIp.setText("");
					 
					 }
				});
				stage.addActor(textBoxIp);
				
				
				
				TextButton btnRejoindrePartie =new TextButton("Rejoindre",Buttons.styleInGameMenu); 
				btnRejoindrePartie.setPosition(600, 200); 
				btnRejoindrePartie.addListener(new InputListener(){
					@Override
					public void touchUp(InputEvent event, float x, float y,
							int pointer, int button) {
						//traitement reseau
						// event ok client
						
						/*
						 * On lance le client
						 */
						SoundPlayer.jouerSon("clic.wav");
						String login = textBoxPseudo.getText();
						System.out.println(colorJ.getColor().r*255);
						System.out.println(colorJ.getColor().g*255);
						System.out.println(colorJ.getColor().b*255);
						java.awt.Color c = new java.awt.Color((float)colorJ.getColor().r,(float)colorJ.getColor().g,(float)colorJ.getColor().b);
						Joueur joueur = new Joueur(0, login, c);
						Client cli = new Client(textBoxIp.getText());
						cli.seConnecter(joueur);
						
						
						Screen.setScreen(new ChatReseauScreenClient(login));
						super.touchUp(event, x, y, pointer, button);
					}

					@Override
					public boolean touchDown(InputEvent event, float x, float y,
							int pointer, int button) {
						return true;
					}
				});
				stage.addActor(btnRejoindrePartie);
				

				super.touchUp(event, x, y, pointer, button);
			}

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}
		});
		stage.addActor(btnRejoindrePartie);




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
		//fondChat.rect(400, 200, 700, 450);
		fondChat.end();
		Gdx.gl.glDisable(GL10.GL_BLEND);

		spriteBatch.begin();
		//maj a deplacer
		

		

		spriteBatch.end();
		stage.act();
		stage.draw();

	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub

	}

}
