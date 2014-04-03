package fr.m1miage.london.ui.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import fr.m1miage.london.ui.Prefs;

public class Score extends Table{
	private String name;
	private String livres;
	private String ptsPauvrete;
	private String ptsVictoire;
	private static TextureRegionDrawable score_bg;

	public Score(String name, int livres, int ptsPauvrete, int ptsVictoire) {
		super();
		this.name = name;
		this.livres = String.valueOf(livres);
		this.ptsPauvrete = String.valueOf(ptsPauvrete);
		this.ptsVictoire = String.valueOf(ptsVictoire);


		Skin skin= new Skin(Gdx.files.internal(Prefs.REPERTOIRE +"ui.json"));

		score_bg = new TextureRegionDrawable(Art.scoreJoueur_bg);

		TextureRegion tr = Art.scoreJoueur_bg;
		tr.flip(false,true);
		score_bg.setRegion(tr);
		this.setBackground(score_bg);
		this.setSkin(skin);
		this.setPosition(1075, 0);
		this.setSize(300, 100);
		this.add(this.name).colspan(3).row().size(75,25).padLeft(25f);
		this.add(this.livres).expand().padBottom(15f);
		this.add(this.ptsVictoire).expand().padBottom(15f);
		this.add(this.ptsPauvrete).expand().padBottom(15f);


		this.pack();

	}



}
