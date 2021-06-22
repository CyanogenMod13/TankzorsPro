package com.tanchiki.libgdx.model.terrains;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Align;
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
        GameStage.world_physic_block[(int) getX(Align.center)][(int) getY(Align.center)] = null;

        if (GameStage.world_block != null)
            GameStage.world_block[(int) getX(Align.center)][(int) getY(Align.center)] = 0;

        if (GameStage.world_nodes != null)
            GameStage.world_nodes[(int) getX(Align.center)][(int) getY(Align.center)] = 0;
        remove();
        GameStage.mainTerrain.explosions.addActor(new NormalExplosion(getX(Align.center), getY(Align.center), TextureLoader.getInstance().getExpl()));
    }
}
