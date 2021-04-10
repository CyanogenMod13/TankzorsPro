package com.tanchiki.libgdx.model.mine;

import com.tanchiki.libgdx.util.ObjectClass;
import com.tanchiki.libgdx.util.ObjectVariables;

public class MineUnity2 extends Mine {
    public MineUnity2(float x, float y) {
        super(x, y, 5, ObjectVariables.tank_enemy, ObjectClass.GameStage.TextureLoader.getMines()[0][2]);
    }
}
