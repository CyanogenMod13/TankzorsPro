package com.tanchiki.libgdx.model.tanks;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class NonRidingTank extends Tank {
    public NonRidingTank(float x, float y, short f, TextureRegion[] regions, int weapon) {
        super(x, y, f, regions, weapon);
        setAI(new DefaultAI());
        setHasRide(false);
        ring.remove();
    }

    @Override
    void incrementSize() {
    }

    @Override
    void decrementSize() {
    }
}
