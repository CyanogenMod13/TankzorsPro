package com.tanchiki.libgdx.model.terrains;

import com.badlogic.gdx.math.*;
import com.tanchiki.libgdx.model.explosions.*;
import com.tanchiki.libgdx.model.terrains.Object.*;
import com.tanchiki.libgdx.util.*;

public class Cactus extends DestroyableBlock {
    public Cactus(float x, float y) {
        super(x, y);
        s.setRegion(t[MathUtils.random(13, 14)]);
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
		GameStage.MT.explosions.addActor(new NormalExplosion(getCenterX(), getCenterY(), GameStage.TextureLoader.getExpl()));
	}
}
