package com.tanchiki.libgdx.model.terrains;

import com.badlogic.gdx.utils.Align;
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
                GameStage.world_block[(int) getX(Align.center)][(int) getY(Align.center)] = 0;
            remove();
            GameStage.world_physic_block[(int) getX(Align.center)][(int) getY(Align.center)] = null;
            GameStage.mainTerrain.explosions.addActor(new NormalExplosion(getX(Align.center), getY(Align.center), TextureLoader.getInstance().getExpl()));
        }
    }
}
