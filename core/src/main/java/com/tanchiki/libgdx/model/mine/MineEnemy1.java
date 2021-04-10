package com.tanchiki.libgdx.model.mine;

import com.tanchiki.libgdx.util.ObjectClass;
import com.tanchiki.libgdx.util.ObjectVariables;

public class MineEnemy1 extends Mine {
    public MineEnemy1(float x, float y) {
        super(x, y, 3, ObjectVariables.tank_ally, ObjectClass.GameStage.TextureLoader.getMines()[0][0]);
    }
}
