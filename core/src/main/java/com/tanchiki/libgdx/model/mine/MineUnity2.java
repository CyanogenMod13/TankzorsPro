package com.tanchiki.libgdx.model.mine;

import com.tanchiki.libgdx.util.ObjectVariables;
import com.tanchiki.libgdx.util.TextureLoader;

public class MineUnity2 extends Mine {
    public MineUnity2(float x, float y) {
        super(x, y, 5, ObjectVariables.tank_enemy, TextureLoader.getInstance().getMines()[0][2]);
    }
}
