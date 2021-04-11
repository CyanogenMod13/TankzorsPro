package com.tanchiki.libgdx.model.mine;

import com.tanchiki.libgdx.util.ObjectVariables;
import com.tanchiki.libgdx.util.TextureLoader;

public class MineUnity1 extends Mine {
    public MineUnity1(float x, float y) {
        super(x, y, 3, ObjectVariables.tank_enemy, TextureLoader.getInstance().getMines()[0][1]);
    }
}
