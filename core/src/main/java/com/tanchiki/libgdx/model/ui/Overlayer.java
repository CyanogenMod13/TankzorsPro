package com.tanchiki.libgdx.model.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import com.tanchiki.libgdx.stage.GameStage;
import com.tanchiki.libgdx.util.ObjectVariables;
import com.tanchiki.libgdx.util.TextureLoader;

public class Overlayer extends Actor {
    GameStage GameStage;
    private Sprite overlayer;

    public Overlayer(float x, float y, short fraction) {
        setSize(6 * ObjectVariables.size_block * 2, 6 * ObjectVariables.size_block * 2);
        setPosition(x, y, Align.center);

        GameStage = GameStage.getInstance();
        if (fraction == ObjectVariables.tank_ally)
            overlayer = new Sprite(TextureLoader.getInstance().getOverlayers()[0][0]);
        if (fraction == ObjectVariables.tank_enemy)
            overlayer = new Sprite(TextureLoader.getInstance().getOverlayers()[0][1]);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        super.draw(batch, parentAlpha);
        overlayer.setSize(getWidth(), getHeight());
        overlayer.setCenter(getX(Align.center), getY(Align.center));
        overlayer.draw(batch);
    }
}
