package com.tanchiki.libgdx.model.tanks;

import com.badlogic.gdx.utils.Align;
import com.tanchiki.libgdx.model.explosions.BiggestExplosion;
import com.tanchiki.libgdx.util.TextureLoader;

public class TankKamikaze extends DefaultTank {
    public TankKamikaze(float x, float y, short f, int weapon) {
        super(x, y, f, TextureLoader.getInstance().getTankKamikaze()[0], weapon);
        HP = 5;
        HPBackup = HP;
        speed = 0.2f;
        defaultAI.radius_enemy = 3 * 2;
    }

    boolean expl = false;

    @Override
    protected void createBullet() {
        if (expl) return;

        int x = (int) getX(Align.center);
        int y = (int) getY(Align.center);
        x += x % 2;
        y += y % 2;

        gameStage.mainTerrain.decorGround.addActor(new BiggestExplosion(x, y, 6 * 2, 10));
        expl = true;
    }

    @Override
    protected void explodeTankAnimation() {
        if (expl) return;

        int x = (int) getX(Align.center);
        int y = (int) getY(Align.center);
        x += x % 2;
        y += y % 2;

        gameStage.mainTerrain.explosions.addActor(new BiggestExplosion(x, y, 5 * 2, 5));
    }
}

