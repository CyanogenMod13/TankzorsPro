package com.tanchiki.libgdx.model.tanks;

import com.tanchiki.libgdx.util.TextureLoader;

public class TankHeavy extends DefaultTank {
    public TankHeavy(float x, float y, short f, int weapon, int mode) {
        super(x, y, f, TextureLoader.getInstance().getTankHeavy()[0], weapon);
        HP = 150;
        HPBackup = HP;
        HPShield = 10;
        HPShieldBackup = HPShield;
        speed = 0.1f / 2;
    }
}
