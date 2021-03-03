package com.tanchiki.libgdx.model.tanks;

import com.tanchiki.libgdx.model.tanks.Object.Tank;
import com.tanchiki.libgdx.util.ObjectClass;

public class TankLight extends Tank {
    public TankLight(float x, float y, short f, int weapon, int mode) {
        super(x, y, f, ObjectClass.GameStage.TextureLoader.getTankLight(), weapon);
        HP = 5;
        HPBackup = HP;
        speed = 0.1f;
    }

    @Override
    public void act(float delta) {

        // TODO: Implement this method
        super.act(delta);
    }

}
