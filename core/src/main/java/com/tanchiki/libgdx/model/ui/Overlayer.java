package com.tanchiki.libgdx.model.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.tanchiki.libgdx.graphics.GameActor;
import com.tanchiki.libgdx.stage.GameStage;
import com.tanchiki.libgdx.util.ObjectClass;
import com.tanchiki.libgdx.util.ObjectVarable;

public class Overlayer extends GameActor {
    GameStage GameStage;
    private Sprite overlayer;

    public Overlayer(float x, float y, short fraction) {
        setSize(6 * ObjectVarable.size_block * 2, 6 * ObjectVarable.size_block * 2);
        setCenterPosition(x, y);

        GameStage = ObjectClass.GameStage;
        if (fraction == ObjectVarable.tank_unity)
            overlayer = new Sprite(GameStage.TextureLoader.getOverlayers()[0][0]);
        if (fraction == ObjectVarable.tank_enemy)
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
