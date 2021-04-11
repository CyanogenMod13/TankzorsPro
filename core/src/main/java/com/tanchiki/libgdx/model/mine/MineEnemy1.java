package com.tanchiki.libgdx.model.mine;

import com.tanchiki.libgdx.util.ObjectVariables;
import com.tanchiki.libgdx.util.TextureLoader;

public class MineEnemy1 extends Mine {
    public MineEnemy1(float x, float y) {
        super(x, y, 3, ObjectVariables.tank_ally, TextureLoader.getInstance().getMines()[0][0]);
    }
}
