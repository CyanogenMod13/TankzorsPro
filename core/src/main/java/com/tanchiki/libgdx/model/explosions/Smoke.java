package com.tanchiki.libgdx.model.explosions;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.tanchiki.libgdx.graphics.GameActor;
import com.tanchiki.libgdx.stage.GameStage;
import com.tanchiki.libgdx.util.TextureLoader;

public class Smoke extends GameActor {
    float alpha = 1;
    Sprite s;

    public Smoke(float x, float y) {
        setSize(1.5f * 2, 1.5f * 2);
        setCenterPosition(x, y);
        TextureRegion[] t = TextureLoader.getInstance().getExpl()[0];
        s = new Sprite(t[15]);
        s.setSize(getWidth(), getHeight());
        s.setCenter(getCenterX(), getCenterY());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // TODO: Implement this method
        super.draw(batch, parentAlpha);
        s.setSize(getWidth(), getHeight());
        s.setCenter(getCenterX(), getCenterY());
        s.draw(batch);
    }

    @Override
    public void act(float delta) {
        // TODO: Implement this method
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
