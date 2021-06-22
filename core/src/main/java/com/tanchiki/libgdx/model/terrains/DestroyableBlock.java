package com.tanchiki.libgdx.model.terrains;

public abstract class DestroyableBlock extends Block {
    public DestroyableBlock(float x, float y) {
        super(x, y);
        //GameStage.world_nodes[(int) getX(Align.center)][(int) getY(Align.center)] = 2;
    }
}
