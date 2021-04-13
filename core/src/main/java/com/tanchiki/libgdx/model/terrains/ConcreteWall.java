package com.tanchiki.libgdx.model.terrains;

import com.tanchiki.libgdx.model.bullets.Rocket;
import com.tanchiki.libgdx.model.explosions.NormalExplosion;
import com.tanchiki.libgdx.util.TextureLoader;

public class ConcreteWall extends Block {
    public ConcreteWall(float x, float y) {
        super(x, y);
        HP = 1;
    }

    @Override
    public void destroyWall() {
        if (bullet instanceof Rocket) {
            if (GameStage.world_block != null)
                GameStage.world_block[(int) getCenterX()][(int) getCenterY()] = 0;
            remove();
            GameStage.world_physic_block[(int) getCenterX()][(int) getCenterY()] = null;
            GameStage.MT.explosions.addActor(new NormalExplosion(getCenterX(), getCenterY(), TextureLoader.getInstance().getExpl()));
        }
    }
}
