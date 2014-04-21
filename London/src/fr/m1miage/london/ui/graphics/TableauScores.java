package fr.m1miage.london.ui.graphics;

import java.io.Serializable;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import fr.m1miage.london.classes.Joueur;
import fr.m1miage.london.ui.Prefs;
import fr.m1miage.london.ui.screens.Screen;
import fr.m1miage.london.ui.screens.ZoneConstructionScreen;

public class TableauScores extends Table {
	private final float ATOG_COLOR = 1f/255f;
	private Skin skin = new Skin();
	public TableauScores(List<Joueur> joueurs){
		skin = Art.skinTableauScores;
		this.setSkin(Art.skinTableauScores);
		this.add("").colspan(1);
		TextureRegion t = new TextureRegion (Art.ico_Livres);
		t.flip(false, true);
		this.add(new Image(t)).colspan(2);
		t = new TextureRegion (Art.ico_PV);
		t.flip(false, true);
		this.add(new Image(t)).colspan(3);
		t = new TextureRegion (Art.ico_Pauvre);
		t.flip(false, true);
		this.add(new Image(t)).colspan(4);
		t = new TextureRegion (Art.ico_Emprunt);
		t.flip(false, true);
		this.add(new Image(t)).colspan(5);
		this.add("").colspan(6).row();
		Button zoneC;
		for(final Joueur j : joueurs){
			java.awt.Color c = j.getCouleur();
			Color color = new Color((float)(c.getRed()*ATOG_COLOR),(float)(c.getGreen()*ATOG_COLOR),(float)(c.getBlue()*ATOG_COLOR),(float)(c.getAlpha()*ATOG_COLOR));
			Label l = new Label(j.getNom(), skin,"score");
			l.setColor(color);
			this.add(l).colspan(1);
			this.add("£"+j.getArgent(), "score").colspan(2);
			this.add(""+j.getPoint_victoire(), "score").colspan(3);
			this.add(""+j.getPoint_pauvrete(), "score").colspan(4);
			this.add("£"+j.getMontantEmprunts(), "score").colspan(5);
			zoneC = new Button(Buttons.styleIcoZoneConstru);//zone de chaque joueur
			zoneC.addListener(new InputListener(){

				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					return true;
				}

				@Override
				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {
					Screen.setScreen(new ZoneConstructionScreen(j));
					super.touchUp(event, x, y, pointer, button);
				}
				
			});
			this.add(zoneC).colspan(6).row();
		}
		this.setPosition(80, 5);
		this.setSize(300, 600);
		this.pack();
	}

}
