package com.tanchiki.libgdx.model.terrains;

import com.badlogic.gdx.math.MathUtils;
import com.tanchiki.libgdx.model.explosions.NormalExplosion;
import com.tanchiki.libgdx.util.SoundLoader;
import com.tanchiki.libgdx.util.TextureLoader;

public class SmallTree extends DestroyableBlock {
    public SmallTree(float x, float y) {
        super(x, y);
        s.setRegion(t[MathUtils.random(15, 17)]);
        HP = 1;
        sound = SoundLoader.getInstance().getHitBush();
    }

    @Override
    public void destroyWall() {
        GameStage.world_physic_block[(int) getCenterX()][(int) getCenterY()] = null;

        if (GameStage.world_block != null)
            GameStage.world_block[(int) getCenterX()][(int) getCenterY()] = 0;

        if (GameStage.world_nodes != null)
            GameStage.world_nodes[(int) getCenterX()][(int) getCenterY()] = 0;
        remove();
        GameStage.mainTerrain.explosions.addActor(new NormalExplosion(getCenterX(), getCenterY(), TextureLoader.getInstance().getExpl()));
    }
}
