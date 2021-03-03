package com.tanchiki.libgdx.model.buildes;

import com.tanchiki.libgdx.model.buildes.Object.SubBuilds;
import com.tanchiki.libgdx.model.tanks.Object.Tank;
import com.tanchiki.libgdx.model.tanks.TankHeavy;
import com.tanchiki.libgdx.model.tanks.TankKamikaze;
import com.tanchiki.libgdx.model.tanks.TankLight;
import com.tanchiki.libgdx.model.tanks.TankSiege;
import com.tanchiki.libgdx.model.terrains.MainTerrain;
import com.tanchiki.libgdx.util.ObjectClass;
import com.tanchiki.libgdx.util.ObjectVarable;

import java.util.HashMap;
import java.util.Map;

public class AngarEnemy extends SubBuilds {
	public static Map<Integer, AngarEnemy> register = null;
	
	private static int LAST_INDEX = 0;
    float time = 1;
    int count = -1;
	MainTerrain.Parameters prm = MainTerrain.getCurrentTerrain().getParameters();

	private int index = 0;
	private int spawnCount = 10;
	private int spawnAI = 0;
	public int activity = 1;
	
    public AngarEnemy(float x, float y) {
        super(x, y, ObjectClass.GameStage.TextureLoader.getBuildings()[0][1], ObjectVarable.tank_enemy);
		if (LAST_INDEX == 0) register = new HashMap<>();
        code_name = "angar_enemy";
		index = LAST_INDEX++;
		if (index < 10) {
			spawnCount = prm.getKey(33 + index);
			spawnAI = prm.getKey(43 + index);
			activity = prm.getKey(53 + index);
		} else {
			spawnCount = prm.getKey(33 + 9);
			spawnAI = prm.getKey(43 + 9);
			activity = prm.getKey(53 + 9);
		}
		System.out.println(this.toString() + " index: " + index);
		register.put(index + 1, this);
    }

    @Override
    public void destroyBuilds() {
        // TODO: Implement this method
    }
	
    @Override
    public void act(float delta) {
		LAST_INDEX = 0;
		
		if (ObjectVarable.size_enemy < ObjectVarable.max_tanks_enemy && GameStage.world_block[(int) getCenterX()][(int) getCenterY()] == 0)
			time += delta;
		
		if (activity == 1)
        if (time >= 1) {
			if (spawnCount > 0)
            if (ObjectVarable.size_enemy < ObjectVarable.max_tanks_enemy) {
				for (int i = 0; i < 8; i++) {
					if (ObjectVarable.tanks_type_enemy[i] > 0) {
						ObjectVarable.tanks_type_enemy[i]--;
						int prm[] = tanks_prm[i][Math.max(0, ObjectVarable.level_diffculty - 1)];
						Tank tank = null;
						switch (prm[0]) {
							case 1: tank = new TankLight(getCenterX(), getCenterY(), ObjectVarable.tank_enemy, prm[2], 0); break;
							case 2: tank = new TankKamikaze(getCenterX(), getCenterY(), ObjectVarable.tank_enemy, prm[2], 0); break;
							case 3: tank = new TankHeavy(getCenterX(), getCenterY(), ObjectVarable.tank_enemy, prm[2], 0); break;
							case 4: tank = new TankSiege(getCenterX(), getCenterY(), ObjectVarable.tank_enemy, prm[2], 0); break;
						}
						tank.HP = prm[1];
						tank.HPBackup = tank.HP;
						tank.HPShield = prm[3];
						tank.HPShieldBackup = tank.HPShield;
						if (i == 7) tank.setBossEnable();
						
						MainTerrain.getCurrentTerrain().tanks_enemy.addActor(tank);
						spawnCount--;
						break;
					}
				}
			}
            time = 0;
        }
        // TODO: Implement this method
        super.act(delta);
    }

}
