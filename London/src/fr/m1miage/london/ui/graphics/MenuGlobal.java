package fr.m1miage.london.ui.graphics;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import fr.m1miage.london.ui.screens.EmprunterScreen;
import fr.m1miage.london.ui.screens.EtalageScreen;
import fr.m1miage.london.ui.screens.GameScreen;
import fr.m1miage.london.ui.screens.GameScreenReseauClient;
import fr.m1miage.london.ui.screens.GameScreenReseauServeur;
import fr.m1miage.london.ui.screens.QuartiersScreen;
import fr.m1miage.london.ui.screens.Screen;
import fr.m1miage.london.ui.screens.ZoneConstructionScreen;

public class MenuGlobal extends Table{
	/*Boutons du menu*/
	public TextButton zoneConstructionBtn;
	public TextButton etalageCartesBtn;
	public TextButton quartiersBtn;
	public TextButton emprunterBtn;
	
	public MenuGlobal(){
		
		super();
		afficherMenuGlob();
	}
	


	private void afficherMenuGlob() {
		zoneConstructionBtn = new TextButton("Zone de construction",Buttons.styleInGameMenu);
		zoneConstructionBtn.addListener(new InputListener(){

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				if(GameScreenReseauClient.joueur!=null){
					Screen.setScreen(new ZoneConstructionScreen(GameScreenReseauClient.joueur));
				}else if(GameScreenReseauServeur.joueur!=null){
					Screen.setScreen(new ZoneConstructionScreen(GameScreenReseauServeur.joueur));
				}else{
					Screen.setScreen(new ZoneConstructionScreen(GameScreen.joueur));
				}
			}

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

		});
		this.add(zoneConstructionBtn).row().padTop(20f);

		etalageCartesBtn = new TextButton("Etalage de cartes",Buttons.styleInGameMenu);
		etalageCartesBtn.addListener(new InputListener(){

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				Screen.setScreen(new EtalageScreen(false));
				super.touchUp(event, x, y, pointer, button);
			}
			
		});
		this.add(etalageCartesBtn).row().padTop(20f);
		

		quartiersBtn = new TextButton("Quartiers",Buttons.styleInGameMenu); 
		
		quartiersBtn.addListener(new InputListener(){

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				Screen.setScreen(new QuartiersScreen());
				super.touchUp(event, x, y, pointer, button);
			}

			@Override
			public boolean touchDown(InputEvent event, float x,
					float y, int pointer, int button) {

				return true;
			}});
		this.add(quartiersBtn).row().padTop(20f);


		emprunterBtn = new TextButton("Emprunter",Buttons.styleInGameMenu); //** Button text and style **//
		emprunterBtn.addListener(new InputListener(){

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				Screen.setScreen(new EmprunterScreen());
				super.touchUp(event, x, y, pointer, button);
			}

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

		});
		this.add(emprunterBtn).row();
		

		this.setPosition(200, 460);
	}
}
