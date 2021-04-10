package com.tanchiki.libgdx.model.mine;

import com.tanchiki.libgdx.util.ObjectClass;
import com.tanchiki.libgdx.util.ObjectVariables;

public class MineUnity1 extends Mine {
    public MineUnity1(float x, float y) {
        super(x, y, 3, ObjectVariables.tank_enemy, ObjectClass.GameStage.TextureLoader.getMines()[0][1]);
    }
}
