package com.tanchiki.libgdx.model.explosions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.tanchiki.libgdx.model.explosions.Object.Explosion;

public class SmallExplosion extends Explosion {
    public SmallExplosion(float x, float y, TextureRegion[][] r) {
        super(x, y, r);
		duration = Gdx.graphics.getDeltaTime();
        s.setSize(s.getWidth() / 1.5f, s.getHeight() / 1.5f);
        s.setCenter(x, y);
    }
}
