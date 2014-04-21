package fr.m1miage.london.ui.graphics;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import fr.m1miage.london.ui.screens.EtalageScreen;
import fr.m1miage.london.ui.screens.QuartiersScreen;
import fr.m1miage.london.ui.screens.Screen;
import fr.m1miage.london.ui.screens.ZoneConstructionScreen;
import fr.m1miage.london.ui.screens.ZoneRestaurerScreen;

public class MenuActions extends Table{
	/* Boutons des actions */
	private Button construireBtn;
	private Button restaurerBtn;
	private Button investirBtn;
	private Button piocherBtn;
	
	public MenuActions(){
		super();
		this.setPosition(780, 485);
		construireBtn = new Button(Buttons.styleBtnConstruire);
		construireBtn.addListener(new InputListener(){

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				Screen.setScreen(new ZoneConstructionScreen("Choisir une carte"));
				super.touchUp(event, x, y, pointer, button);
			}

		});

		this.add(construireBtn);

		restaurerBtn = new Button(Buttons.styleBtnRestaurer);
		restaurerBtn.addListener(new InputListener(){

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				Screen.setScreen(new ZoneRestaurerScreen("Choisir un batiment à restaurer"));
				super.touchUp(event, x, y, pointer, button);
			}
			
		});
		this.add(restaurerBtn);

		investirBtn = new Button(Buttons.styleBtnInvestir);
		investirBtn.addListener(new InputListener(){

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				Screen.setScreen(new QuartiersScreen());
				super.touchUp(event, x, y, pointer, button);
			}

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

		});
		this.add(investirBtn);

		piocherBtn = new Button(Buttons.styleBtnPiocher);
		piocherBtn.addListener(new InputListener(){

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				Screen.setScreen(new EtalageScreen(true));
				super.touchUp(event, x, y, pointer, button);
			}
			
		});
		this.add(piocherBtn);

		this.pad(30f);		
	}
	
}
