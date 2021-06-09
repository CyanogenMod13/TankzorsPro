package com.tanchiki.libgdx.model.tanks;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tanchiki.libgdx.model.terrains.Block;
import com.tanchiki.libgdx.model.terrains.IronWall;
import com.tanchiki.libgdx.model.terrains.MainTerrain;
import com.tanchiki.libgdx.util.ObjectVariables;
import com.tanchiki.libgdx.util.TextureLoader;

public class Turret extends NonRidingTank {
    public static final int[][] prm = {
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

    public Turret(float x, float y, short f) {
        super(x, y, f, TextureLoader.getInstance().getTurrets()[0], 5);
        setAI(new DefaultAI() {
            @Override
            public boolean isNotDestroyableBlock(Block block) {
                return block instanceof IronWall;
            }

            @Override
            protected void searchEnemy() {}
        });
        HP = 10;
        HPBackup = HP;
        speed = 0;
        int i = Math.max(0, MainTerrain.getCurrentTerrain().getParameters().getKey(f == ObjectVariables.tank_enemy ? 19 : 20) - 1);
        HP = HPBackup = prm[i][0];
        weapon = prm[i][1];
        defaultAI.radius_enemy = MainTerrain.getCurrentTerrain().getParameters().getKey(22) * 2;
        if (fraction == ObjectVariables.tank_enemy) ObjectVariables.all_size_turrets_enemy++;
    }

    float tt = 0;
    float ttt = MathUtils.random(4, 10);

    @Override
    public void act(float delta) {
        super.act(delta);
        if (defaultAI.MODE != defaultAI.ATTACK) {
            tt += delta;
            if (tt >= ttt) {
                switch (MathUtils.random(1, 4)) {
                    case 1:
                        bottom();
                        break;
                    case 2:
                        left();
                        break;
                    case 3:
                        right();
                        break;
                    case 4:
                        top();
                        break;
                }
                tt = 0;
                ttt = MathUtils.random(4, 10);
            }
        }
    }

    @Override
    public void destroyTank(float damage) {
        if (fraction == ObjectVariables.tank_enemy) ObjectVariables.all_size_turrets_enemy--;
        super.destroyTank(damage);
    }
}
