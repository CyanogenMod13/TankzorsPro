package com.tanchiki.libgdx.model.terrains;

import com.badlogic.gdx.math.MathUtils;

public class Plate extends Terrains {
    public int HP = 1;

    public static final int PLATE1 = 49,
            PLATE2 = 50,
            PLATE3 = 51;

    public Plate(float x, float y) {
        super(x, y);
        s.setRegion(t[MathUtils.random(33, 38)]);
    }

    public void crash() {
        HP -= 1;
        if (HP >= 0)
            s.setRegion(t[MathUtils.random(PLATE1, PLATE3)]);
        else if (last_block instanceof Grass) {
            g.MT.ground.addActor(new CrashGrass(getCenterX(), getCenterY()));
            g.world_obj[(int) getCenterX()][(int) getCenterY()] = last_block;
            remove();
        } else if (last_block instanceof Sand) {
            g.MT.ground.addActor(new CrashSand(getCenterX(), getCenterY()));
            g.world_obj[(int) getCenterX()][(int) getCenterY()] = last_block;
            remove();
        }
    }
}
