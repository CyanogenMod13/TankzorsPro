package com.tanchiki.libgdx.model.terrains;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Align;

public class Plate extends Terrain {
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
            g.mainTerrain.ground.addActor(new CrashGrass(getX(Align.center), getY(Align.center)));
            g.world_obj[(int) getX(Align.center)][(int) getY(Align.center)] = last_block;
            remove();
        } else if (last_block instanceof Sand) {
            g.mainTerrain.ground.addActor(new CrashSand(getX(Align.center), getY(Align.center)));
            g.world_obj[(int) getX(Align.center)][(int) getY(Align.center)] = last_block;
            remove();
        }
    }
}
