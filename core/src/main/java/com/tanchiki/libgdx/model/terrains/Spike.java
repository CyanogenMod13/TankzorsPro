package com.tanchiki.libgdx.model.terrains;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Align;
import com.tanchiki.libgdx.model.explosions.NormalExplosion;
import com.tanchiki.libgdx.util.TextureLoader;

public class Spike extends DestroyableBlock {
    public Spike(float x, float y) {
        super(x, y);
        TextureRegion[] tex = TextureLoader.getInstance().getSpike()[0];
        s.setRegion(tex[MathUtils.random(0, 2)]);
        HP = 0;
    }

    @Override
    public void destroyWall() {
        if (Math.abs(HP) > 5) {
            GameStage.world_physic_block[(int) getX(Align.center)][(int) getY(Align.center)] = null;

            if (GameStage.world_block != null)
                GameStage.world_block[(int) getX(Align.center)][(int) getY(Align.center)] = 0;

            if (GameStage.world_nodes != null)
                GameStage.world_nodes[(int) getX(Align.center)][(int) getY(Align.center)] = 0;
            remove();
            GameStage.mainTerrain.explosions.addActor(new NormalExplosion(getX(Align.center), getY(Align.center), TextureLoader.getInstance().getExpl()));
        }
        HP = 0;
    }
}
