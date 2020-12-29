package com.tanchiki.libgdx.model.tanks;

import com.tanchiki.libgdx.model.tanks.Object.Tank;
import com.tanchiki.libgdx.util.ObjectClass;

public class TankHeavy extends Tank {
    public TankHeavy(float x, float y, short f, int weapon, int mode) {
        super(x, y, f, ObjectClass.GameStage.TextureLoader.getTankHeavy(), weapon);
        HP = 150;
        HPbackup = HP;
        HPShield = 10;
        HPShieldBackup = HPShield;
        speed = 0.1f / 2;
    }

    @Override
    public void act(float delta) {

        // TODO: Implement this method
        super.act(delta);
    }

}
