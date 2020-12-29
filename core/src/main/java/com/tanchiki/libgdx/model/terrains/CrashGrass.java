package com.tanchiki.libgdx.model.terrains;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class CrashGrass extends Grass {
    public CrashGrass(float x, float y) {
        super(x, y);
        s.setRegion(t[MathUtils.random(52, 54)]);
        ((Actor) last_block).remove();
        last_block = null;
    }
}
