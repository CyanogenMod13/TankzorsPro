package com.tanchiki.libgdx.model.tanks;

import com.tanchiki.libgdx.util.ObjectClass;

public class TankLight extends DefaultTank {
    public TankLight(float x, float y, short f, int weapon) {
        super(x, y, f, ObjectClass.GameStage.TextureLoader.getTankLight()[0], weapon);
        HP = 5;
        HPBackup = HP;
        speed = 0.1f;
    }
}
