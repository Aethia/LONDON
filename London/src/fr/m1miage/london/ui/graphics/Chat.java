package fr.m1miage.london.ui.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class Chat extends Table {
private ScrollPane sPChat;
private boolean overTable =false;

	public Chat(Skin skin) {
		super(skin);
			
		ScrollPaneStyle s =  new ScrollPaneStyle();
		Image r = new Image(Art.scroll);
		s.vScroll= r.getDrawable();
		s.hScrollKnob = r.getDrawable();
		s.vScrollKnob = r.getDrawable();
		s.hScroll = r.getDrawable();

		this.setSize(650, 350);
		this.setColor(Color.RED);
		this.left();
		this.pack();

		sPChat = new ScrollPane(this,s);
		sPChat.setSize(650, 350);
		sPChat.setPosition(405, 215);
		//	sPChat.setScrollBarPositions(true, true);
		sPChat.addListener(new InputListener(){

			@Override
			public void enter(InputEvent event, float x, float y, int pointer,
					Actor fromActor) {
				overTable=true;
				super.enter(event, x, y, pointer, fromActor);
			}

			@Override
			public void exit(InputEvent event, float x, float y, int pointer,
					Actor toActor) {
				overTable=false;
				super.exit(event, x, y, pointer, toActor);
			}


		});
		sPChat.setWidget(this);
	}

	public ScrollPane getSPChat() {
		return sPChat;
	}

	public boolean isOverTable() {
		return overTable;
	}

	
}
