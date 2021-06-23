package com.tanchiki.libgdx.model.terrains;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import com.tanchiki.libgdx.stage.GameStage;
import com.tanchiki.libgdx.util.ObjectVariables;
import com.tanchiki.libgdx.util.TextureLoader;

public class Terrain extends Actor {
    public Sprite s;
    protected TextureRegion[] t;
    private float a = ObjectVariables.size_block;
    protected float size = a * 2;
    protected GameStage g = GameStage.getInstance();
    public Object last_block = null;
    public boolean isModification = true;

    public Terrain(float x, float y) {
        t = TextureLoader.getInstance().getTerrains()[0];
        s = new Sprite(t[0]);
        setSize(a * 2, a * 2);
        setPosition(x, y, Align.center);
        if (g.world_obj[(int) x][(int) y] != null)
            last_block = g.world_obj[(int) x][(int) y];
        g.world_obj[(int) x][(int) y] = this;
        s.setSize(a * 2, a * 2);
        s.setCenter(getX(Align.center), getY(Align.center));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        s.draw(batch);
    }

    @Override
    protected void drawDebugBounds(ShapeRenderer shapes) {
        shapes.setColor(getColor());
        shapes.rect(getX(), getY(), getWidth(), getHeight());
        setColor(Color.GREEN);
    }
}
