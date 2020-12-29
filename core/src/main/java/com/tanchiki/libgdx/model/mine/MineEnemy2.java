package com.tanchiki.libgdx.model.mine;

import com.tanchiki.libgdx.model.mine.Object.Mine;
import com.tanchiki.libgdx.util.ObjectClass;
import com.tanchiki.libgdx.util.ObjectVarable;

public class MineEnemy2 extends Mine {
    public MineEnemy2(float x, float y) {
        super(x, y, 5, ObjectVarable.tank_unity, ObjectClass.GameStage.TextureLoader.getMines()[0][0]);
    }
}
