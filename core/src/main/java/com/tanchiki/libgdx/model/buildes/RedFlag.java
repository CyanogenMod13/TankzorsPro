package com.tanchiki.libgdx.model.buildes;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.tanchiki.libgdx.model.terrains.MainTerrain;
import com.tanchiki.libgdx.stage.GameStage;
import com.tanchiki.libgdx.util.Settings;
import com.tanchiki.libgdx.util.SoundLoader;

public class RedFlag extends Flag {
    public static boolean died = false;

    public RedFlag(float x, float y) {
        super(x, y, 1);
    }

    @Override
    void clicked() {
        GameStage.getInstance().tankUser.flag = this;
        SoundLoader.getInstance().getFlagPickup().play(Settings.volumeEffect);
        active = false;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (active) super.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (died) {
            active = true;
            died = false;
        }

        if (!active) {
            setCenterPosition(GameStage.getInstance().tankUser.defaultAI.goal_x, GameStage.getInstance().tankUser.defaultAI.goal_y);
            switch (MainTerrain.Mission.CODE) {
                case 3:
                case 40:
                case 41:
                case 42:
                    //System.out.println(GameStage.getInstance().world_buildes[(int) getCenterX()][(int) getCenterY()]);
                    if (GameStage.getInstance().worldBuilds[(int) getCenterX()][(int) getCenterY()] instanceof HangarUnity) {
                        win();
                        remove();
                    }
                    break;
            }
        }
    }
}
