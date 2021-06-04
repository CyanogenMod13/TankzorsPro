package com.tanchiki.libgdx.model.buildes;

import com.badlogic.gdx.math.MathUtils;
import com.tanchiki.libgdx.model.tanks.*;
import com.tanchiki.libgdx.model.terrains.MainTerrain;
import com.tanchiki.libgdx.model.ui.MissionCompleted;
import com.tanchiki.libgdx.util.ObjectVariables;
import com.tanchiki.libgdx.util.Settings;
import com.tanchiki.libgdx.util.TextureLoader;
import com.tanchiki.libgdx.util.WeaponData;

import java.util.HashMap;
import java.util.Map;

public class AngarUnity extends SubBuilds {
    public static Map<Integer, AngarUnity> register = null;

    private static int LAST_INDEX = 0;
    private static int lastIndex = 0;
    private static MissionCompleted missionCompleted;
    private static int nextIndexSpawn = 0;

    float time = 1;

    MainTerrain.Parameters prm = MainTerrain.getCurrentTerrain().getParameters();

    public int ourSpawn = 0;
    public static boolean firstSpawn = false;

    private int index = 0;
    private int spawnCount = 10;
    private int spawnAI = 0;
    public int activity = 1;

    public AngarUnity(float x, float y) {
        super(x, y, TextureLoader.getInstance().getBuildings()[0][0], ObjectVariables.tank_ally);
        setCenterPosition(x, y);
        if (LAST_INDEX == 0) register = new HashMap<>();
        index = LAST_INDEX++;
        lastIndex = index;
        code_name = "angar_unity";
        gameStage.worldBuilds[(int) x][(int) y] = this;
        if (index < 5) {
            spawnCount = prm.getKey(141 + index);
            spawnAI = prm.getKey(146 + index);
            activity = prm.getKey(151 + index);
        } else {
            spawnCount = prm.getKey(141 + 4);
            spawnAI = prm.getKey(146 + 4);
            activity = prm.getKey(151 + 4);
        }

        register.put(index + 1, this);

        ourSpawn = MainTerrain.getCurrentTerrain().getParameters().getKey(140);
        if (ourSpawn - 128 >= 0) ourSpawn -= 128;
        nextIndexSpawn = (ourSpawn == 0 || ourSpawn == 6) ? MathUtils.random(0, lastIndex) : (ourSpawn - 1);
        //System.out.println(this.toString() + " index: " + index + " spawn: " + nextIndexSpawn + " our spawn: " + ourSpawn);
    }

    @Override
    public void destroyBuilds() {

    }

    @Override
    public void act(float delta) {
        LAST_INDEX = 0;

        super.act(delta);
        if (gameStage.world_block[(int) getCenterX()][(int) getCenterY()] == 0 && (ObjectVariables.size_allies < ObjectVariables.max_tanks_ally || gameStage.TankUser != null && gameStage.TankUser.HP <= 0))
            time += delta;

        if (time >= 1) {

            if (Settings.start_game && (nextIndexSpawn == -1 || index == nextIndexSpawn)) {
                if (gameStage.TankUser == null) {
                    gameStage.TankUser = new TankUser(getCenterX(), getCenterY());
                    gameStage.MT.tanks_unity.addActor(gameStage.TankUser);
                    if (ourSpawn == 0) nextIndexSpawn = -1;
                    time = 0;
                    return;
                }

                if (gameStage.TankUser.HP <= 0) {
                    if (WeaponData.live > 0 && MainTerrain.Mission.CODE != 66) {
                        TankUser t = gameStage.TankUser;
                        //t.destroyTank();
                        if (t.flag != null) t.flag.active = true;
                        gameStage.MT.health.addActor(t.health);
                        gameStage.MT.ring.addActor(t.ring);
                        t.setCenterPosition(getCenterX(), getCenterY());
                        t.defaultAI.goal_x = (int) getCenterX();
                        t.defaultAI.goal_y = (int) getCenterY();
                        t.HP = t.HPBackup;
                        Settings.TankUserSettings.HPShieldBackup = 0;

                        gameStage.MT.tanks_unity.addActor(t);
                        WeaponData.live--;
                        if (ourSpawn == 0) nextIndexSpawn = -1;
                        time = 0;
                        return;
                    } else {
                        if (MainTerrain.Mission.CODE == 66) MissionCompleted.show(MissionCompleted.MISSION_COMPLETED);
                        MissionCompleted.show(MissionCompleted.MISSION_FAILED);
                        missionCompleted = null;
                        gameStage.MT.mission.remove();
                    }
                }
            }
            if (activity == 1)
                if (spawnCount > 0)
                    if (ObjectVariables.size_allies < ObjectVariables.max_tanks_ally) {
                        for (int i = 0; i < 8; i++) {
                            if (ObjectVariables.tanks_type_ally[i] > 0) {
                                ObjectVariables.tanks_type_ally[i]--;
                                int prm[] = tanks_prm[i][Math.max(ObjectVariables.level_difficulty - 1, 0)];
                                Tank tank = null;
                                switch (prm[0]) {
                                    case 1:
                                        tank = new TankLight(getCenterX(), getCenterY(), ObjectVariables.tank_ally, prm[2]);
                                        break;
                                    case 2:
                                        tank = new TankKamikaze(getCenterX(), getCenterY(), ObjectVariables.tank_ally, prm[2]);
                                        break;
                                    case 3:
                                        tank = new TankHeavy(getCenterX(), getCenterY(), ObjectVariables.tank_ally, prm[2], 0);
                                        break;
                                    case 4:
                                        tank = new TankSiege(getCenterX(), getCenterY(), ObjectVariables.tank_ally, prm[2]);
                                        break;
                                }
                                tank.HP = prm[1];
                                tank.HPBackup = tank.HP;
                                tank.HPShield = prm[3];
                                tank.HPShieldBackup = tank.HPShield;
                                if (i == 7) tank.setBossEnable();

                                MainTerrain.getCurrentTerrain().tanks_unity.addActor(tank);
                                spawnCount--;
                                time = 0;
                                break;
                            }
                        }
                    }
        }
    }

}
