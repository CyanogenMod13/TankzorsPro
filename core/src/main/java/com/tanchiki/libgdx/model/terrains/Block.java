package com.tanchiki.libgdx.model.terrains;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import com.tanchiki.libgdx.model.explosions.NormalExplosion;
import com.tanchiki.libgdx.model.tanks.Tank;
import com.tanchiki.libgdx.stage.GameStage;
import com.tanchiki.libgdx.util.ObjectVariables;
import com.tanchiki.libgdx.util.SoundLoader;
import com.tanchiki.libgdx.util.TextureLoader;

public abstract class Block extends Actor {
    protected GameStage GameStage;
    private float a = ObjectVariables.size_block;
    public Sprite s;
    private Animation anim;
    public float HP = 4;
    protected TextureRegion[] t;
    public Actor bullet = null;
    boolean destroy;

    public Sound sound = SoundLoader.getInstance().getHitWall();

    public Block(float x, float y) {
        GameStage = GameStage.getInstance();
        t = TextureLoader.getInstance().getWalls()[0];
        s = new Sprite(t[0]);
        setPosition(x, y, Align.center);

        Block oldBlock = GameStage.world_physic_block[(int) getX(Align.center)][(int) getY(Align.center)];
        if (oldBlock != null) oldBlock.remove();

        GameStage.world_physic_block[(int) getX(Align.center)][(int) getY(Align.center)] = this;
        GameStage.world_block[(int) x][(int) y] = 1;
        GameStage.world_nodes[(int) x][(int) y] = 1;

        s.setSize(a * 2, a * 2);
        s.setCenter(x, y);
        setBounds(s.getX(), s.getY(), s.getWidth(), s.getHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

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

        GameStage.world_physic_block[(int) getX(Align.center)][(int) getY(Align.center)] = this;
        GameStage.world_block[(int) getX(Align.center)][(int) getY(Align.center)] = 1;
        GameStage.world_nodes[(int) getX(Align.center)][(int) getY(Align.center)] = 1;
        if (destroy) {
            GameStage.world_physic_block[(int) getX(Align.center)][(int) getY(Align.center)] = null;

            if (GameStage.world_block != null)
                GameStage.world_block[(int) getX(Align.center)][(int) getY(Align.center)] = 0;

            if (GameStage.world_nodes != null)
                GameStage.world_nodes[(int) getX(Align.center)][(int) getY(Align.center)] = 0;
            remove();
            GameStage.mainTerrain.explosions.addActor(new NormalExplosion(getX(Align.center), getY(Align.center), TextureLoader.getInstance().getExpl()));
        }

        Tank tank = GameStage.world_tank[(int) getX(Align.center)][(int) getY(Align.center)];
        if (tank != null) {
            tank.destroyTank(Integer.MAX_VALUE);
        }
    }

    public abstract void destroyWall();

    public void destroyWallNow() {
        destroy = true;
    }
}
