package com.tanchiki.libgdx.model.terrains;

import com.tanchiki.libgdx.model.explosions.NormalExplosion;
import com.tanchiki.libgdx.util.TextureLoader;

public class StoneWall extends DestroyableBlock {
    public StoneWall(float x, float y) {
        super(x, y);
        HP = 5;
    }

    @Override
    public void destroyWall() {
        if (HP == 5) {
            s.setRegion(t[0]);
        }
        if (HP == 4) {
            s.setRegion(t[3]);
        }
        if (HP == 3) {
            s.setRegion(t[5]);
        }
        if (HP == 2) {
            s.setRegion(t[7]);
        }
        if (HP == 1) {
            s.setRegion(t[6]);
        }
        if (HP <= 0) {
            if (GameStage.world_block != null)
                GameStage.world_block[(int) getCenterX()][(int) getCenterY()] = 0;
            if (GameStage.world_nodes != null)
                GameStage.world_nodes[(int) getCenterX()][(int) getCenterY()] = 0;
            remove();
            GameStage.world_physic_block[(int) getCenterX()][(int) getCenterY()] = null;
            GameStage.mainTerrain.explosions.addActor(new NormalExplosion(getCenterX(), getCenterY(), TextureLoader.getInstance().getExpl()));
        }
    }
}
