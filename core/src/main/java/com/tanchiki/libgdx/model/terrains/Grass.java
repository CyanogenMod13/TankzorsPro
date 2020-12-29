package com.tanchiki.libgdx.model.terrains;

import com.badlogic.gdx.math.MathUtils;
import com.tanchiki.libgdx.model.terrains.Object.Terrains;

public class Grass extends Terrains {
    public Grass(float x, float y) {
        super(x, y);
        s.setRegion(t[MathUtils.random(0, 5)]);
    }
}
