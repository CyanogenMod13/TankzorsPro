package com.tanchiki.libgdx.model.mine;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.tanchiki.libgdx.graphics.GameActor;
import com.tanchiki.libgdx.model.explosions.NormalExplosion;
import com.tanchiki.libgdx.model.tanks.Tank;
import com.tanchiki.libgdx.stage.GameStage;
import com.tanchiki.libgdx.util.ObjectVariables;
import com.tanchiki.libgdx.util.TextureLoader;

public class Mine extends GameActor {
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
        setCenterPosition(x, y);
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
        Tank t = (Tank) GameStage.world_tank[(int) getCenterX()][(int) getCenterY()];
        if (t != null) {
            if (t.fraction == fraction) {
                t.destroyTank(damage);
                GameStage.MT.explosions.addActor(new NormalExplosion(getCenterX(), getCenterY(), TextureLoader.getInstance().getExpl()));
                GameStage.world_mines[(int) getCenterX()][(int) getCenterY()] = null;
                remove();
            }
        }

        super.act(delta);
    }

}
