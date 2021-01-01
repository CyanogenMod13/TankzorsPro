package com.tanchiki.libgdx.model.tanks;

import com.tanchiki.libgdx.model.explosions.BiggestExplosion;
import com.tanchiki.libgdx.model.tanks.Object.Tank;
import com.tanchiki.libgdx.util.ObjectClass;

public class TankKamikaze extends Tank {
    public TankKamikaze(float x, float y, short f, int weapon, int mode) {
        super(x, y, f, ObjectClass.GameStage.TextureLoader.getTankKamikaze(), weapon);
        HP = 5;
        HPbackup = HP;
        speed = 0.2f;
		AI.radius_enemy = 3 * 2;
    }

	boolean expl = false;
	
	@Override
	protected void creatBullet() {
		if (expl) return;
		
		int x = (int) getCenterX();
		int y = (int) getCenterY();
		x += x % 2;
		y += y % 2;
		
		GameStage.MT.decor_ground.addActor(new BiggestExplosion(x, y, 6 * 2, 10));
		expl = true;
	}
}

