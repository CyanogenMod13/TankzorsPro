package com.tanchiki.libgdx.model.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tanchiki.libgdx.util.TextureLoader;

public class CornerOverlayer extends Actor {
    Sprite leftUpperCorner;
    Sprite rightUpperCorner;
    Sprite leftLowerCorner;
    Sprite rightLowerCorner;

    float size = Gdx.graphics.getWidth() / 7.2f;

    public CornerOverlayer() {
        leftUpperCorner = new Sprite(TextureLoader.getInstance().getOverlayCorner()[0][1]);
        rightUpperCorner = new Sprite(TextureLoader.getInstance().getOverlayCorner()[0][2]);
        leftLowerCorner = new Sprite(TextureLoader.getInstance().getOverlayCorner()[0][0]);
        rightLowerCorner = new Sprite(TextureLoader.getInstance().getOverlayCorner()[0][3]);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // TODO: Implement this method
        super.draw(batch, parentAlpha);
        leftLowerCorner.setSize(size, size);
        leftLowerCorner.setPosition(0, 0);
        leftLowerCorner.draw(batch);

        leftUpperCorner.setSize(size, size);
        leftUpperCorner.setPosition(0, Gdx.graphics.getHeight() - size);
        leftUpperCorner.draw(batch);

        rightLowerCorner.setSize(size, size);
        rightLowerCorner.setPosition(Gdx.graphics.getWidth() - size, 0);
        rightLowerCorner.draw(batch);

        rightUpperCorner.setSize(size, size);
        rightUpperCorner.setPosition(Gdx.graphics.getWidth() - size, Gdx.graphics.getHeight() - size);
        rightUpperCorner.draw(batch);
    }
}
