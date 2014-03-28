package fr.m1miage.london.ui.screens;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;

import fr.m1miage.london.ui.Prefs;
import fr.m1miage.london.ui.graphics.AreaColorRect;
import fr.m1miage.london.ui.graphics.Art;
import fr.m1miage.london.ui.graphics.Buttons;
import fr.m1miage.london.ui.graphics.Fonts;


/**
 * 
 * @author Aethia
 * Interface de cr�ation de partie : recupere le nombre de joueurs, leur nom et leur couleur
 */
public class CreationPartieScreen extends Screen {
	private BitmapFont font;

	//boutons
	public static TextButton btnValider;
	public static Map<Integer,TextButton> buttonsNbj=new HashMap<Integer,TextButton>() ; //boutons nombre de joueurs


	private static List<TextField> lTextField = new ArrayList<TextField>();
	private static List<AreaColorRect> lColors = new ArrayList<AreaColorRect>();
	
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
			b.setPosition(450+(i*100), 525); 
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
					//aucun de deja selectionn�
					if(idNbJSelected!=num && idNbJSelected!=0){ //deselectionner l'ancien
						TextButton btnDeselect = buttonsNbj.get(idNbJSelected);
						btnDeselect.getStyle().up = Buttons.nbButtonStyle.up;//buttonSkin.getDrawable("ButtonOff");
						btnDeselect.getStyle().over = Buttons.nbButtonStyle.over;
					}
					idNbJSelected=num;
					TextButton btnSelected  = buttonsNbj.get(idNbJSelected);
					btnSelected.getStyle().up =Buttons.nbButtonStyle.down;// buttonSkin.getDrawable("ButtonOn");
					btnSelected.getStyle().over = Buttons.nbButtonStyle.down; //buttonSkin.getDrawable("ButtonOn");
					System.out.println("Bouton selectionn� : " + idNbJSelected);
					majFormJoueurs();
					return true;
				}				

			});
		}

	}
	
	
	

	//cr�� un nombre de textfield egal au nombre de joueurs choisi.
	private static void majFormJoueurs(){
		int top = 215;
		int left =200;
		//on enleve les anciens elements du stage
		for(TextField t : lTextField){
			stage.getRoot().removeActor(t);
		}
		for(AreaColorRect a : lColors){
			stage.getRoot().removeActor(a);
		}
		lColors.clear();
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
			//creation des textfields
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
			//creation des couleurs associ�es
			final AreaColorRect colorJ = new AreaColorRect(left+30+i*168,top+100 , 100f, 100f);
			colorJ.setShapeFillColor((float)Math.random(), (float)Math.random(), (float)Math.random());
			colorJ.setPosition(left+30+i*168, top+100);
			colorJ.setHeight(100); colorJ.setWidth(100);

			colorJ.addListener(new InputListener(){

				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					// TODO Auto-generated method stub
					stage.getRoot().removeActor(colorJ);
					System.out.println("ok");
					colorJ.setShapeFillColor((float)Math.random(), (float)Math.random(), (float)Math.random());
					stage.addActor(colorJ);
					return super.touchDown(event, x, y, pointer, button);
				}
				
			});
			//ajout des nouveaux elements au stage
			stage.addActor(colorJ);
			lColors.add(colorJ);
			lTextField.add(mTextField);
			stage.addActor(mTextField);
		}
	}

	private static void boutonValider(){	
		btnValider = new TextButton("Valider", Buttons.styleInGameMenu); 
		btnValider.setPosition(600, 80); 

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
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
	    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		spriteBatch.begin();
		 
		draw(Art.bgPartie, 0, 0);
		Fonts.FONT_TITLE.draw(spriteBatch, "Nouvelle partie", 450, 20);
		font.draw(spriteBatch, "Nombre de joueurs :", 125, 220);
		font.draw(spriteBatch, "Joueurs :", 125, 430);
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
