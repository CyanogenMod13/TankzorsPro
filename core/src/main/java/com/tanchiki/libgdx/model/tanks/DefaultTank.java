package com.tanchiki.libgdx.model.tanks;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class DefaultTank extends Tank {
    public DefaultTank(float x, float y, short f, TextureRegion[] regions, int weapon) {
        super(x, y, f, regions, weapon);
        setAI(new DefaultAI());
        incrementSize();
    }
}
