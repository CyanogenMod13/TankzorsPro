package com.tanchiki.libgdx.model.tanks;

import com.tanchiki.libgdx.model.explosions.BiggestExplosion;
import com.tanchiki.libgdx.model.explosions.NormalExplosion;
import com.tanchiki.libgdx.util.ObjectClass;

public class TankKamikaze extends DefaultTank {
    public TankKamikaze(float x, float y, short f, int weapon) {
        super(x, y, f, ObjectClass.GameStage.TextureLoader.getTankKamikaze()[0], weapon);
        HP = 5;
        HPBackup = HP;
        speed = 0.2f;
		defaultAI.radius_enemy = 3 * 2;
    }

	boolean expl = false;
	
	@Override
	protected void createBullet() {
		if (expl) return;
		
		int x = (int) getCenterX();
		int y = (int) getCenterY();
		x += x % 2;
		y += y % 2;
		
		GameStage.MT.decor_ground.addActor(new BiggestExplosion(x, y, 6 * 2, 10));
		expl = true;
	}

	@Override
	protected void explodeTankAnimation() {
		if (expl) return;

		int x = (int) getCenterX();
		int y = (int) getCenterY();
		x += x % 2;
		y += y % 2;

		GameStage.MT.explosions.addActor(new BiggestExplosion(x, y, 5 * 2, 5));
	}
}

