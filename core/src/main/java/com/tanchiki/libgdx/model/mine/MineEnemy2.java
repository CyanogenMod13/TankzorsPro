package com.tanchiki.libgdx.model.mine;

import com.tanchiki.libgdx.util.ObjectVariables;
import com.tanchiki.libgdx.util.TextureLoader;

public class MineEnemy2 extends Mine {
    public MineEnemy2(float x, float y) {
        super(x, y, 5, ObjectVariables.tank_ally, TextureLoader.getInstance().getMines()[0][0]);
    }
}
