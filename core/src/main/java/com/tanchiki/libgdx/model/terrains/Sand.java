package com.tanchiki.libgdx.model.terrains;

import com.badlogic.gdx.math.MathUtils;
import com.tanchiki.libgdx.model.terrains.Object.Terrains;

public class Sand extends Terrains {
    private float x, y;

    public Sand(float x, float y) {
        super(x, y);
        this.x = x;
        this.y = y;
        //s.setSize(s.getWidth()+0.5f,s.getHeight()+0.5f);
    }

    private int _random1 = MathUtils.random(60, 61),
            _random2 = MathUtils.random(63, 64),
            _random3 = MathUtils.random(65, 66),
            _random5 = MathUtils.random(48 + 8, 52 + 7);

    public void init() {
        isInit = false;

		modify = false;
		
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
                s.setRegion(t[_random1]);
                return;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }

        try {
            if ((g.world_obj[(int) (x)][(int) (y + size)] instanceof Grass)) {
                s.setRegion(t[_random2]);
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
                s.setRegion(t[_random3]);
                return;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }

		modify = true;
        s.setRegion(t[_random5]);
    }

    private boolean isInit = true;
}
