package com.tanchiki.libgdx.model.terrains;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.tanchiki.libgdx.graphics.GameActor;
import com.tanchiki.libgdx.stage.GameStage;
import com.tanchiki.libgdx.util.ObjectVariables;
import com.tanchiki.libgdx.util.TextureLoader;

public class Clound extends GameActor {
    Sprite s;
    Sprite s2;
    float alpha = 1;
    public static float time = 0;
    public static float t = MathUtils.random(30f, 60f);

    public Clound(float x, float y) {
        setCenterPosition(x, y);
        TextureRegion[] t = TextureLoader.getInstance().getClouds()[0];
        int i = MathUtils.random(1, 3);
        switch (i) {
            case 1: {
                s = new Sprite(t[0]);
                s2 = new Sprite(t[1]);
                break;
            }
            case 2: {
                s = new Sprite(t[2]);
                s2 = new Sprite(t[3]);
                break;
            }
            case 3: {
                s = new Sprite(t[4]);
                s2 = new Sprite(t[5]);
                break;
            }
        }

        setWidth(ObjectVariables.size_block * 2 * 6);
        setHeight((s.getHeight() * getWidth()) / s.getWidth());
        s.setSize(getWidth(), getHeight());
        s.setCenter(getCenterX(), getCenterY());
        s2.setSize(getWidth(), getHeight());
        s2.setCenter(getCenterX() + getWidth() / 2, getCenterY() - getHeight() / 2);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        super.draw(batch, parentAlpha);
        s2.setSize(getWidth() - ObjectVariables.size_block * 2, getHeight() - ObjectVariables.size_block * 2);
        s2.setCenter(getCenterX() + getWidth() / 1.5f, getCenterY() - getHeight() / 1.5f);
        s2.draw(batch);
        s.setSize(getWidth(), getHeight());
        s.setCenter(getCenterX(), getCenterY());
        s.draw(batch);

    }

    public static void random(float d) {
        time += d;
        if (time >= t) {
            t = MathUtils.random(3f, 60f);
            GameStage.getInstance().mainTerrain.decor.addActor(new Clound(MathUtils.random(0, GameStage.getInstance().world_wight), -10));
            time = 0;
        }
    }

    @Override
    public void act(float delta) {

        super.act(delta);
        if (s.getX() > GameStage.getInstance().world_wight)
            remove();
        float angle = 45 * MathUtils.degreesToRadians;
        float deltaX = MathUtils.cos(angle) / 30;
        float deltaY = MathUtils.sin(angle) / 30;
        moveBy(deltaX, deltaY);
    }
}
