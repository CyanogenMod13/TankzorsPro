package com.tanchiki.libgdx.model.tanks;

import com.badlogic.gdx.math.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.tanchiki.libgdx.model.bullets.*;
import com.tanchiki.libgdx.model.tanks.Object.*;
import com.tanchiki.libgdx.model.terrains.*;
import com.tanchiki.libgdx.util.*;

public class Turret extends Tank {
	public static final int prm[][] = {
		{8, 1},
		{15, 1},
		{8, 2},
		{15, 2},
		{15, 3},
		{15, 4},
		{20, 5},
		{25, 6},
		{35, 7},
		{35, 7}
	};
	
    public Turret(float x, float y, short f, int angle) {
        super(x, y, f, ObjectClass.TextureLoader.getTurrets(), 5);
        hasRide = false;
        HP = 10;
        HPbackup = HP;
        speed = 0;
		int i = Math.max(0, MainTerrain.getCurrentTerrain().getParameters().getKey(f == ObjectVarable.tank_enemy ? 19 : 20) - 1);
		HP = HPbackup = prm[i][0];
		weapon = prm[i][1];
		AI.radius_enemy = MainTerrain.getCurrentTerrain().getParameters().getKey(22) * 2;
		if (fraction == ObjectVarable.tank_enemy) ObjectVarable.all_size_turrets_enemy++;
		AI = new AI() {
            @Override
            public boolean hasUnDestroyableBlock(int x0, int y0, int x, int y, boolean invert) {
				if (!invert) {
					for (int i = x0; i <= x; i += 2)
						for (int u = y0; u <= y; u += 2)
							if (i >= 0 && i < GameStage.world_physic_block.length && u >= 0 && u < GameStage.world_physic_block[i].length) {
								Actor actor = GameStage.world_physic_block[i][u];
								if (actor == null) continue;
								if (actor instanceof IronWall) return true;
							}
				} else {
					for (int i = x; i >= x0; i -= 2)
						for (int u = y; u >= y0; u -= 2)
							if (i >= 0 && i < GameStage.world_physic_block.length && u >= 0 && u < GameStage.world_physic_block[i].length) {
								Actor actor = GameStage.world_physic_block[i][u];
								if (actor == null) continue;
								if (actor instanceof IronWall) return true;
							}
				}		
				return false;
			}
        };
    }

	float tt = 0;
	float ttt = MathUtils.random(4, 10);
	
	@Override
	public void act(float delta) {
		super.act(delta);
		if (AI.MODE != AI.ATTACK) {
			tt += delta;
			if (tt >= ttt) {
				switch (MathUtils.random(1, 4)) {
					case 1: bottom(); break;
					case 2: left(); break;
					case 3: right(); break;
					case 4: top(); break;
				}
				tt = 0;
				ttt = MathUtils.random(4, 10);
			}
		}
	}
	
	@Override
	public void destroyTank(float damage) {
		if (fraction == ObjectVarable.tank_enemy) ObjectVarable.all_size_turrets_enemy--;
		super.destroyTank(damage);
	}
	
	protected void creatBullet() {
        switch (weapon) {
            case 1: {
					if (time > 1.0f / speed_skill) {
						GameStage.MT.bullet.addActor(new BulletLight(getCenterX(), getCenterY(), angle_for_bullet, fraction, this));
						time = 0;
					}
					break;
				}
            case 2: {
					if (time > 0.6 / speed_skill) {
						GameStage.MT.bullet.addActor(new BulletPluzma(getCenterX(), getCenterY(), angle_for_bullet, fraction, this));
						time = 0;
					}
					break;
				}
			case 3: {
					if (time > 1.0f / speed_skill) {
						switch (angle_for_bullet) {
							case 1: 
								GameStage.MT.bullet.addActor(new BulletLight(getCenterX() + 0.2f, getCenterY(), angle_for_bullet, fraction, this));
								GameStage.MT.bullet.addActor(new BulletLight(getCenterX() - 0.2f, getCenterY(), angle_for_bullet, fraction, this));
								break;
							case 2:
								GameStage.MT.bullet.addActor(new BulletLight(getCenterX(), getCenterY() + 0.2f, angle_for_bullet, fraction, this));
								GameStage.MT.bullet.addActor(new BulletLight(getCenterX(), getCenterY() - 0.2f, angle_for_bullet, fraction, this));
								break;
							case 3: 
								GameStage.MT.bullet.addActor(new BulletLight(getCenterX() + 0.2f, getCenterY(), angle_for_bullet, fraction, this));
								GameStage.MT.bullet.addActor(new BulletLight(getCenterX() - 0.2f, getCenterY(), angle_for_bullet, fraction, this));
								break;
							case 4:
								GameStage.MT.bullet.addActor(new BulletLight(getCenterX(), getCenterY() + 0.2f, angle_for_bullet, fraction, this));
								GameStage.MT.bullet.addActor(new BulletLight(getCenterX(), getCenterY() - 0.2f, angle_for_bullet, fraction, this));
								break;	
						}
						time = 0;
					}
					break;
				}
            case 4: {
					if (time > 0.6 / speed_skill) {
						switch (angle_for_bullet) {
							case 1: 
								GameStage.MT.bullet.addActor(new BulletPluzma(getCenterX() + 0.2f, getCenterY(), angle_for_bullet, fraction, this));
								GameStage.MT.bullet.addActor(new BulletPluzma(getCenterX() - 0.2f, getCenterY(), angle_for_bullet, fraction, this));
								break;
							case 2:
								GameStage.MT.bullet.addActor(new BulletPluzma(getCenterX(), getCenterY() + 0.2f, angle_for_bullet, fraction, this));
								GameStage.MT.bullet.addActor(new BulletPluzma(getCenterX(), getCenterY() - 0.2f, angle_for_bullet, fraction, this));
								break;
							case 3: 
								GameStage.MT.bullet.addActor(new BulletPluzma(getCenterX() + 0.2f, getCenterY(), angle_for_bullet, fraction, this));
								GameStage.MT.bullet.addActor(new BulletLight(getCenterX() - 0.2f, getCenterY(), angle_for_bullet, fraction, this));
								break;
							case 4:
								GameStage.MT.bullet.addActor(new BulletPluzma(getCenterX(), getCenterY() + 0.2f, angle_for_bullet, fraction, this));
								GameStage.MT.bullet.addActor(new BulletPluzma(getCenterX(), getCenterY() - 0.2f, angle_for_bullet, fraction, this));
								break;	
						}
						time = 0;
					}
					break;
				}
            case 5: {
					if (time > 1 / speed_skill) {
						GameStage.MT.bullet.addActor(new BronetBullet1(getCenterX(), getCenterY(), angle_for_bullet, fraction, this));
						time = 0;
					}
					break;
				}
            case 6: {
					if (time > 1 / speed_skill) {
						GameStage.MT.bullet.addActor(new BronetBullet2(getCenterX(), getCenterY(), angle_for_bullet, fraction, this));
						time = 0;
					}
					break;
				}
            case 7: {
					if (time > 1.2f / speed_skill) {
						GameStage.MT.bullet.addActor(new Roket(getCenterX(), getCenterY(), angle_for_bullet, fraction, this));
						time = 0;
					}
					break;
				}
        }
    }
}
