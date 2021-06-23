package com.tanchiki.libgdx.model.terrains;

import com.badlogic.gdx.math.MathUtils;

public class Sand extends Terrain {
    private final float x;
    private final float y;

    public Sand(float x, float y) {
        super(x, y);
        this.x = x;
        this.y = y;
    }

    private final int random1 = MathUtils.random(60, 61);
    private final int random2 = MathUtils.random(63, 64);
    private final int random3 = MathUtils.random(65, 66);
    private final int random5 = MathUtils.random(48 + 8, 52 + 7);

    public void postInit() {
        isModification = false;

        try {
            if ((g.world_obj[(int) (x + size)][(int) y] instanceof Grass) &&
                    (g.world_obj[(int) (x)][(int) (y - size)] instanceof Grass)) {
                s.setRegion(t[70]);
                return;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }
        try {
            if ((g.world_obj[(int) (x + size)][(int) y] instanceof Grass) &&
                    (g.world_obj[(int) (x)][(int) (y + size)] instanceof Grass)) {
                s.setRegion(t[69]);
                return;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }

        try {
            if ((g.world_obj[(int) (x - size)][(int) y] instanceof Grass) &&
                    (g.world_obj[(int) (x)][(int) (y + size)] instanceof Grass)) {
                s.setRegion(t[68]);
                return;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }

        try {
            if ((g.world_obj[(int) (x - size)][(int) y] instanceof Grass) &&
                    (g.world_obj[(int) (x)][(int) (y - size)] instanceof Grass)) {
                s.setRegion(t[67]);
                return;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }

        try {
            if ((g.world_obj[(int) (x + size)][(int) y] instanceof Sand) &&
                    (g.world_obj[(int) (x)][(int) (y - size)] instanceof Sand) &&
                    (g.world_obj[(int) (x + size)][(int) (y - size)] instanceof Grass)
                /*(g.world_obj[(int)(x)][(int)(y + size)] instanceof Grass)*/) {
                s.setRegion(t[74]);
                return;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }
        try {
            if ((g.world_obj[(int) (x + size)][(int) y] instanceof Sand) &&
                    (g.world_obj[(int) (x)][(int) (y + size)] instanceof Sand) &&
                    (g.world_obj[(int) (x + size)][(int) (y + size)] instanceof Grass) /*&&
				(g.world_obj[(int)(x)][(int)(y - size)] instanceof Grass)*/) {
                s.setRegion(t[73]);
                return;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }

        try {
            if ((g.world_obj[(int) (x - size)][(int) y] instanceof Sand) &&
                    (g.world_obj[(int) (x)][(int) (y + size)] instanceof Sand) &&
                    (g.world_obj[(int) (x - size)][(int) (y + size)] instanceof Grass) /*&&
				(g.world_obj[(int)(x)][(int)(y - size)] instanceof Grass)*/) {
                s.setRegion(t[72]);
                return;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }

        try {
            if ((g.world_obj[(int) (x - size)][(int) y] instanceof Sand) &&
                    (g.world_obj[(int) (x)][(int) (y - size)] instanceof Sand) &&
                    (g.world_obj[(int) (x - size)][(int) (y - size)] instanceof Grass) /*&&
				(g.world_obj[(int)(x)][(int)(y + size)] instanceof Grass)*/) {
                s.setRegion(t[71]);
                return;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }

        try {
            if ((g.world_obj[(int) (x)][(int) (y - size)] instanceof Grass)) {
                s.setRegion(t[random1]);
                return;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }

        try {
            if ((g.world_obj[(int) (x)][(int) (y + size)] instanceof Grass)) {
                s.setRegion(t[random2]);
                return;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }

        try {
            if ((g.world_obj[(int) (x - size)][(int) (y)] instanceof Grass)) {
                s.setRegion(t[62]);
                return;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }
        try {
            if ((g.world_obj[(int) (x + size)][(int) (y)] instanceof Grass)) {
                s.setRegion(t[random3]);
                return;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }

        isModification = true;
        s.setRegion(t[random5]);
    }

}
