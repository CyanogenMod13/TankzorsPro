package com.tanchiki.libgdx.model.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tanchiki.libgdx.stage.GameStage;
import com.tanchiki.libgdx.util.FontLoader;

public class Panel extends Actor {
    Sprite s;
    BitmapFont f;
    GameStage GameStage;

    public Panel() {
        GameStage = GameStage.getInstance();
        s = new Sprite();
        s.setSize(s.getWidth() * 2, s.getHeight() * 2);
        f = FontLoader.f16;

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        super.draw(batch, parentAlpha);
        s.setPosition(Gdx.graphics.getWidth() - s.getWidth(), Gdx.graphics.getHeight() - s.getHeight());
        s.draw(batch);
        f.draw(batch, "gg", Gdx.graphics.getWidth() - s.getWidth(), Gdx.graphics.getHeight());

    }

}
