package fr.m1miage.london.ui.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * 
 * source : http://stackoverflow.com/questions/16426494/drawing-a-transparent-shape-in-libgdx-as-an-actor
 *
 */
public class AreaColorRect extends Actor {

    private Color shapeFillColor = new Color();
    public Rectangle area;
    public ShapeRenderer shapeRen;

    public AreaColorRect(float x, float y, float w, float h) {
        shapeRen = new ShapeRenderer();
        this.area = new Rectangle(x, y, w, h);
    }


    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
    	batch.end();
        shapeRen.begin(ShapeType.Filled);
        shapeRen.setColor(new Color(shapeFillColor.r, shapeFillColor.g, shapeFillColor.b,
            0.0f));
        shapeRen.rect(area.x, area.y, area.width, area.height);
        shapeRen.end();
        batch.begin();
    }

    public void setShapeFillColor(float r, float g, float b) {
        this.shapeFillColor = new Color(r, g, b, 1);
    }

}