package com.tanchiki.libgdx.model.explosions;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import com.tanchiki.libgdx.util.TextureLoader;

public class Smoke extends Actor {
    float alpha = 1;
    Sprite s;

    public Smoke(float x, float y) {
        setSize(1.5f * 2, 1.5f * 2);
        setPosition(x, y, Align.center);
        TextureRegion[] t = TextureLoader.getInstance().getExpl()[0];
        s = new Sprite(t[15]);
        s.setSize(getWidth(), getHeight());
        s.setCenter(getX(Align.center), getY(Align.center));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        super.draw(batch, parentAlpha);
        s.setSize(getWidth(), getHeight());
        s.setCenter(getX(Align.center), getY(Align.center));
        s.draw(batch);
    }

    @Override
    public void act(float delta) {

        super.act(delta);
        alpha -= delta / 5;
        if (alpha <= 0)
            remove();
        s.setAlpha(alpha);
        float angle = 45 * MathUtils.degreesToRadians;
        float deltaX = MathUtils.cos(angle) / 50;
        float deltaY = MathUtils.sin(angle) / 50;
        moveBy(deltaX, deltaY);
    }

}
