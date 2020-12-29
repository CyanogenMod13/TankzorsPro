package com.tanchiki.libgdx.model.terrains;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class CrashSand extends Sand {
    public CrashSand(float x, float y) {
        super(x, y);
        s.setRegion(t[MathUtils.random(79, 80)]);
        ((Actor) last_block).remove();
        last_block = null;
    }
}
