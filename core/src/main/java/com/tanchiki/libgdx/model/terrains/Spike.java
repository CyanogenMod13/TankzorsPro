package com.tanchiki.libgdx.model.terrains;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.tanchiki.libgdx.model.explosions.NormalExplosion;
import com.tanchiki.libgdx.model.terrains.Object.DestroyableBlock;

public class Spike extends DestroyableBlock {
    public Spike(float x, float y) {
        super(x, y);
        TextureRegion[] tex = GameStage.TextureLoader.getSpike()[0];
        s.setRegion(tex[MathUtils.random(0, 2)]);
		HP = 0;
    }

    @Override
    public void destroyWall() {
        if (Math.abs(HP) > 5) {
			GameStage.world_physic_block[(int) getCenterX()][(int) getCenterY()] = null;

			if (GameStage.world_block != null)
				GameStage.world_block[(int) getCenterX()][(int) getCenterY()] = 0;

			if (GameStage.world_nodes != null)
				GameStage.world_nodes[(int) getCenterX()][(int) getCenterY()] = 0;
			remove();
			GameStage.MT.explosions.addActor(new NormalExplosion(getCenterX(), getCenterY(), GameStage.TextureLoader.getExpl()));
		}
		HP = 0;
    }
}
