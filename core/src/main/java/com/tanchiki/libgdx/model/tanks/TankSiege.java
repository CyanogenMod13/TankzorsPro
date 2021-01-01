package com.tanchiki.libgdx.model.tanks;

import com.tanchiki.libgdx.model.bullets.Artiling;
import com.tanchiki.libgdx.model.tanks.Object.Tank;
import com.tanchiki.libgdx.util.ObjectClass;

public class TankSiege extends Tank {
    public TankSiege(float x, float y, short f, int weapon, int mode) {
        super(x, y, f, ObjectClass.GameStage.TextureLoader.getTankSiege(), weapon);
        HP = 150;
        HPbackup = HP;
        HPShield = 10;
        HPShieldBackup = HPShield;
        speed = 0.1f;
        AI = new AI() {

            @Override
            public boolean hasUnDestroyableBlock(int x0, int y0, int x, int y, boolean invert) {
                if (AI.distance_of_goal <= 4 * 2)
                    return super.hasUnDestroyableBlock(x0, y0, x, y, invert);
                else
                    return false;
            }

        };
		AI.radius_enemy = 7 * 2;
    }

    @Override
    protected void creatBullet() {
        if (AI.distance_of_goal <= 4 * 2) super.creatBullet();
        else if (time > 2f) {
            GameStage.MT.bullet.addActor(new Artiling(getCenterX(), getCenterY(), AI.radius_enemy, angle_for_bullet));
            time = 0;
        }
    }

}
