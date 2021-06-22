package com.tanchiki.libgdx.model.terrains;

import com.badlogic.gdx.utils.Align;
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
                GameStage.world_block[(int) getX(Align.center)][(int) getY(Align.center)] = 0;
            if (GameStage.world_nodes != null)
                GameStage.world_nodes[(int) getX(Align.center)][(int) getY(Align.center)] = 0;
            remove();
            GameStage.world_physic_block[(int) getX(Align.center)][(int) getY(Align.center)] = null;
            GameStage.mainTerrain.explosions.addActor(new NormalExplosion(getX(Align.center), getY(Align.center), TextureLoader.getInstance().getExpl()));
        }
    }
}
