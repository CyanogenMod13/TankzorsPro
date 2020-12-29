package com.tanchiki.libgdx.model.mine;

import com.tanchiki.libgdx.model.mine.Object.Mine;
import com.tanchiki.libgdx.util.ObjectClass;
import com.tanchiki.libgdx.util.ObjectVarable;

public class MineEnemy1 extends Mine {
    public MineEnemy1(float x, float y) {
        super(x, y, 3, ObjectVarable.tank_unity, ObjectClass.GameStage.TextureLoader.getMines()[0][0]);
    }
}
