package fr.m1miage.london.ui.graphics;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import fr.m1miage.london.classes.Joueur;
import fr.m1miage.london.ui.Prefs;

public class TableauScores extends Table {
	private final float ATOG_COLOR = 1f/255f;
	
	public TableauScores(List<Joueur> joueurs){

		Skin skin= new Skin(Gdx.files.internal(Prefs.REPERTOIRE +"ui.json"));
		this.setSkin(skin);
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
		this.add(new Image(t)).colspan(5).row();
		
		for(Joueur j : joueurs){
			java.awt.Color c = j.getCouleur();
			Color color = new Color((float)(c.getRed()*ATOG_COLOR),(float)(c.getGreen()*ATOG_COLOR),(float)(c.getBlue()*ATOG_COLOR),(float)(c.getAlpha()*ATOG_COLOR));
			Label l = new Label(j.getNom(), skin,"score");
			l.setColor(color);
			this.add(l).colspan(1);
			this.add("£"+j.getArgent(), "score").colspan(2);
			this.add(""+j.getPoint_victoire(), "score").colspan(3);
			this.add(""+j.getPoint_pauvrete(), "score").colspan(4);
			this.add("£"+j.getMontantEmprunts(), "score").colspan(5).row();
		}
		this.setPosition(100, 80);
		this.setSize(300, 600);
		this.pack();
	}

}
