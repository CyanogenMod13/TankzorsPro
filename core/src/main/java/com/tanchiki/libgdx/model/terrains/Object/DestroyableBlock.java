package com.tanchiki.libgdx.model.terrains.Object;

public abstract class DestroyableBlock extends Block {
    public DestroyableBlock(float x, float y) {
        super(x, y);
        GameStage.world_nodes[(int) getCenterX()][(int) getCenterY()] = 2;
    }
}
