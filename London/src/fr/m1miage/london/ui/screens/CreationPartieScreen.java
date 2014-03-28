package fr.m1miage.london.ui.screens;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.sun.org.apache.xpath.internal.operations.Lt;

import fr.m1miage.london.ui.Prefs;
import fr.m1miage.london.ui.graphics.Art;
import fr.m1miage.london.ui.graphics.Buttons;
import fr.m1miage.london.ui.graphics.Fonts;



public class CreationPartieScreen extends Screen {
	private BitmapFont font;

	//boutons
	public static TextButton btnValider;
	public static Map<Integer,TextButton> buttonsNbj=new HashMap<Integer,TextButton>() ; //boutons nombre de joueurs


	private static List<TextField> lTextField = new ArrayList<TextField>();
	public static int idNbJSelected=0;
	private static Stage stage; 

	public CreationPartieScreen(){
		stage = new Stage(Prefs.LARGEUR_FENETRE, Prefs.HAUTEUR_FENETRE, false); 
		stage.clear();
		Gdx.input.setInputProcessor(stage);

		font=Fonts.GLOBAL_FONT;
		font.setColor(Color.BLACK);
		boutonNbJoueurs();
		boutonValider();



	}

	private static void boutonNbJoueurs(){

		int i;
		for(i=2;i<6;i++){
			TextButton b = new TextButton(""+i, new TextButtonStyle(Buttons.nbButtonStyle)); 
			b.setPosition(450+(i*100), 575); 
			buttonsNbj.put(i, b);
		}
		//pour chaque bouton, on va attribuer un listener
		for(i=2;i<6;i++){
			final int num = i;
			buttonsNbj.get(i).addListener(new InputListener(){

				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					// TODO Auto-generated method stub
					//aucun de deja selectionné
					if(idNbJSelected!=num && idNbJSelected!=0){ //deselectionner l'ancien
						TextButton btnDeselect = buttonsNbj.get(idNbJSelected);
						btnDeselect.getStyle().up = Buttons.nbButtonStyle.up;//buttonSkin.getDrawable("ButtonOff");
						btnDeselect.getStyle().over = Buttons.nbButtonStyle.over;
					}
					idNbJSelected=num;
					TextButton btnSelected  = buttonsNbj.get(idNbJSelected);
					btnSelected.getStyle().up =Buttons.nbButtonStyle.down;// buttonSkin.getDrawable("ButtonOn");
					btnSelected.getStyle().over = Buttons.nbButtonStyle.down; //buttonSkin.getDrawable("ButtonOn");
					System.out.println("Bouton selectionné : " + idNbJSelected);
					majFormJoueurs();
					return true;
				}				

			});
		}

	}

	//créé un nombre de textfield egal au nombre de joueurs choisi.
	private static void majFormJoueurs(){

		int top = 300;
		int left =200;
		for(TextField t : lTextField){
			stage.getRoot().removeActor(t);
		}
		lTextField.clear();
		Skin menuSkin = new Skin();
		TextureAtlas menuAtlas = new TextureAtlas("ressources/Images/text_area.pack");
		menuSkin.addRegions(menuAtlas);
		TextFieldStyle txtStyle = new TextFieldStyle();
		txtStyle.background = menuSkin.getDrawable("Area");
		txtStyle.font = new BitmapFont(Gdx.files.internal("ressources/Fnt/font_quartiers.fnt"), false);
		txtStyle.fontColor = Color.BLACK;
		txtStyle.background.setBottomHeight(32f);
		txtStyle.background.setLeftWidth(10f);
		for(int i=1;i<=idNbJSelected;i++){
			final TextField mTextField = new TextField("Joueur " + i, txtStyle);
			mTextField.setPosition(left + 170*i,top);
			mTextField.setHeight(70);
			mTextField.setWidth(180);
			mTextField.addListener(new InputListener(){
				boolean touched=false;
				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					// TODO Auto-generated method stub
					if(!touched){
						mTextField.setText("");
						touched = true;
					}
					return super.touchDown(event, x, y, pointer, button);
				}

			});

			lTextField.add(mTextField);
			stage.addActor(mTextField);
		}
	}

	private static void boutonValider(){		
		btnValider = new TextButton("Valider", Buttons.styleInGameMenu); //** Button text and style **//
		btnValider.setPosition(600, 20); //** Button location **//

		btnValider.addListener(new InputListener() {

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				// TODO Auto-generated method stub
				Screen.setScreen(new GameScreen());	
				return super.touchDown(event, x, y, pointer, button);
			}
		});
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub

		spriteBatch.begin();
		draw(Art.bgPartie, 0, 0);
		Fonts.FONT_TITLE.draw(spriteBatch, "Nouvelle partie", 450, 20);
		font.draw(spriteBatch, "Nombre de joueurs :", 125, 170);
		font.draw(spriteBatch, "Joueurs :", 125, 400);
		String msg = "COPYRIGHT Aethia 2014";
		drawString(msg, 2, 800 -6 -2);
		spriteBatch.end();

		for(Integer mapKey : buttonsNbj.keySet()){
			stage.addActor(buttonsNbj.get(mapKey));
		}


		stage.addActor(btnValider);

		stage.act();
		stage.draw();
	}






}
