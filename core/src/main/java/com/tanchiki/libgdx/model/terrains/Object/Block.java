package com.tanchiki.libgdx.model.terrains.Object;

import com.badlogic.gdx.audio.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.tanchiki.libgdx.graphics.*;
import com.tanchiki.libgdx.model.explosions.*;
import com.tanchiki.libgdx.model.tanks.Object.*;
import com.tanchiki.libgdx.stage.*;
import com.tanchiki.libgdx.util.*;

public abstract class Block extends GameActor {
    protected GameStage GameStage;
    private float a = ObjectVarable.size_block;
    public Sprite s;
    private Animation anim;
    public float HP = 4;
    protected TextureRegion[] t;
    public Actor bullet = null;
	boolean destroy;

	public Sound sound = SoundLoader.getInstance().getHitWall();
	
    public Block(float x, float y) {
        GameStage = ObjectClass.GameStage;
        t = ObjectClass.GameStage.TextureLoader.getWalls()[0];
        s = new Sprite(t[0]);
        setCenterPosition(x, y);

        Block oldBlock = GameStage.world_physic_block[(int) getCenterX()][(int) getCenterY()];
        if (oldBlock != null) oldBlock.remove();

        GameStage.world_physic_block[(int) getCenterX()][(int) getCenterY()] = this;
        GameStage.world_block[(int) x][(int) y] = 1;
        GameStage.world_nodes[(int) x][(int) y] = 1;
		
		s.setSize(a * 2, a * 2);
        s.setCenter(x, y);
		setBounds(s.getX(), s.getY(), s.getWidth(), s.getHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // TODO: Implement this method
        super.draw(batch, parentAlpha);
        s.draw(batch);
    }

    @Override
    protected void drawDebugBounds(ShapeRenderer shapes) {
        shapes.setColor(getColor());
        shapes.rect(getX(), getY(), getWidth(), getHeight());
        setColor(Color.GREEN);
    }

	@Override
	public void act(float delta) {
		// TODO: Implement this method
		GameStage.world_physic_block[(int) getCenterX()][(int) getCenterY()] = this;
        GameStage.world_block[(int) getCenterX()][(int) getCenterY()] = 1;
        GameStage.world_nodes[(int) getCenterX()][(int) getCenterY()] = 1;
		if (destroy) {
			GameStage.world_physic_block[(int) getCenterX()][(int) getCenterY()] = null;

			if (GameStage.world_block != null)
				GameStage.world_block[(int) getCenterX()][(int) getCenterY()] = 0;

			if (GameStage.world_nodes != null)
				GameStage.world_nodes[(int) getCenterX()][(int) getCenterY()] = 0;
			remove();
			GameStage.MT.explosions.addActor(new NormalExplosion(getCenterX(), getCenterY(), GameStage.TextureLoader.getExpl()));
		}
		
		Tank tank = GameStage.world_tank[(int) getCenterX()][(int) getCenterY()];
		if (tank != null) {
			tank.destroyTank(Integer.MAX_VALUE);
		}
	}
	
    public abstract void destroyWall();

	public void destroyWallNow() {
		destroy = true;
	}
}
