package com.tanchiki.libgdx.model.mine;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import com.tanchiki.libgdx.model.explosions.NormalExplosion;
import com.tanchiki.libgdx.model.tanks.Tank;
import com.tanchiki.libgdx.stage.GameStage;
import com.tanchiki.libgdx.util.ObjectVariables;
import com.tanchiki.libgdx.util.TextureLoader;

public class Mine extends Actor {
    GameStage GameStage;
    protected Sprite s;
    public float damage;
    public int fraction;
    private float size = ObjectVariables.size_block * 2;

    public Mine(float x, float y, float damage, int fraction, TextureRegion t) {
        this.GameStage = GameStage.getInstance();
        this.fraction = fraction;
        this.damage = damage;
        s = new Sprite(t);
        setSize(size, size);
        setPosition(x, y, Align.center);
        GameStage.world_mines[(int) x][(int) y] = this;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        super.draw(batch, parentAlpha);
        s.setSize(getWidth(), getHeight());
        s.setPosition(getX(), getY());
        s.draw(batch);
    }

    @Override
    public void act(float delta) {
        Tank t = (Tank) GameStage.world_tank[(int) getX(Align.center)][(int) getY(Align.center)];
        if (t != null) {
            if (t.fraction == fraction) {
                t.destroyTank(damage);
                GameStage.mainTerrain.explosions.addActor(new NormalExplosion(getX(Align.center), getY(Align.center), TextureLoader.getInstance().getExpl()));
                GameStage.world_mines[(int) getX(Align.center)][(int) getY(Align.center)] = null;
                remove();
            }
        }

        super.act(delta);
    }

}
