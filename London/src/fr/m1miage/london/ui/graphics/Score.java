package fr.m1miage.london.ui.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import fr.m1miage.london.classes.Joueur;
import fr.m1miage.london.ui.Prefs;

public class Score extends Table{
	private static final float ATOG_COLOR = 1f/255f;
	private String name;
	private String livres;
	private String ptsPauvrete;
	private String ptsVictoire;
	private Color color;
	private static TextureRegionDrawable score_bg;

	public Score(Joueur j) {
		super();
		java.awt.Color c = j.getCouleur();
		this.color = new Color((float)(c.getRed()*ATOG_COLOR),(float)(c.getGreen()*ATOG_COLOR),(float)(c.getBlue()*ATOG_COLOR),(float)(c.getAlpha()*ATOG_COLOR));
		this.name = j.getNom();
		this.livres = String.valueOf(j.getArgent());
		this.ptsPauvrete = String.valueOf(j.getPoint_pauvrete());
		this.ptsVictoire = String.valueOf(j.getPoint_victoire());


		Skin skin= new Skin(Gdx.files.internal(Prefs.REPERTOIRE +"ui.json"));
		score_bg = new TextureRegionDrawable(Art.scoreJoueur_bg);

		TextureRegion tr = new TextureRegion (Art.scoreJoueur_bg);
		tr.flip(false,true);
		score_bg.setRegion(tr);
		this.setBackground(score_bg);
		this.setSkin(skin);
		this.setPosition(1075, 0);
		this.setSize(300, 100);
		Label l = new Label(this.name, skin);
		l.setColor(color);
		this.add(l).colspan(3).row().size(75,25).padLeft(25f);
		this.add(this.livres).expand().padBottom(15f);
		this.add(this.ptsVictoire).expand().padBottom(15f);
		this.add(this.ptsPauvrete).expand().padBottom(15f);


		this.pack();

	}



}
