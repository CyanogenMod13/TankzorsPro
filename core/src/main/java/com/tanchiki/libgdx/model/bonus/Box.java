package com.tanchiki.libgdx.model.bonus;

import com.badlogic.gdx.math.MathUtils;
import com.tanchiki.libgdx.model.bonus.Object.Bonus;
import com.tanchiki.libgdx.util.SoundLoader;

public class Box extends Bonus {
    public Box(float x, float y, int type) {
        super(type, x, y);
        s.setRegion(t[MathUtils.random(18, 19)]);
		sound = SoundLoader.getInstance().getPowerupPickup();
    }
}
