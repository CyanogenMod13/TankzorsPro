package com.tanchiki.libgdx.model.tanks;

import com.badlogic.gdx.utils.Align;
import com.tanchiki.libgdx.model.bullets.Artiling;
import com.tanchiki.libgdx.util.TextureLoader;

public class TankSiege extends DefaultTank {
    public TankSiege(float x, float y, short f, int weapon) {
        super(x, y, f, TextureLoader.getInstance().getTankSiege()[0], weapon);
        HP = 150;
        HPBackup = HP;
        HPShield = 10;
        HPShieldBackup = HPShield;
        speed = 0.1f;
        defaultAI = new DefaultAI() {
            @Override
            public boolean hasNotDestroyableBlock(int x0, int y0, int x, int y) {
                if (defaultAI.distanceOfGoal <= 4 * 2)
                    return super.hasNotDestroyableBlock(x0, y0, x, y);
                return false;
            }
        };
        defaultAI.radius_enemy = 7 * 2;
    }

    @Override
    protected void createBullet() {
        if (defaultAI.distanceOfGoal <= 4 * 2) super.createBullet();
        else if (time > 2f) {
            gameStage.mainTerrain.bullet.addActor(new Artiling(getX(Align.center), getY(Align.center), defaultAI.radius_enemy, direction));
            time = 0;
        }
    }
}
