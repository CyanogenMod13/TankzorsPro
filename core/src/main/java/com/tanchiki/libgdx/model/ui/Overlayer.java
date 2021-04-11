package com.tanchiki.libgdx.model.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.tanchiki.libgdx.graphics.GameActor;
import com.tanchiki.libgdx.stage.GameStage;
import com.tanchiki.libgdx.util.ObjectVariables;

public class Overlayer extends GameActor {
    GameStage GameStage;
    private Sprite overlayer;

    public Overlayer(float x, float y, short fraction) {
        setSize(6 * ObjectVariables.size_block * 2, 6 * ObjectVariables.size_block * 2);
        setCenterPosition(x, y);

        GameStage = GameStage.getInstance();
        if (fraction == ObjectVariables.tank_ally)
            overlayer = new Sprite(GameStage.TextureLoader.getOverlayers()[0][0]);
        if (fraction == ObjectVariables.tank_enemy)
            overlayer = new Sprite(GameStage.TextureLoader.getOverlayers()[0][1]);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // TODO: Implement this method
        super.draw(batch, parentAlpha);
        overlayer.setSize(getWidth(), getHeight());
        overlayer.setCenter(getCenterX(), getCenterY());
        overlayer.draw(batch);
    }
}
