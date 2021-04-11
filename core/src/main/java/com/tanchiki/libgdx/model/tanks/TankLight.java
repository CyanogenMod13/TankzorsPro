package com.tanchiki.libgdx.model.tanks;

import com.tanchiki.libgdx.util.TextureLoader;

public class TankLight extends DefaultTank {
    public TankLight(float x, float y, short f, int weapon) {
        super(x, y, f, TextureLoader.getInstance().getTankLight()[0], weapon);
        HP = 5;
        HPBackup = HP;
        speed = 0.1f;
    }
}
