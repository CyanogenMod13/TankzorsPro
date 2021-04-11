package com.tanchiki.libgdx.model.tnt;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.tanchiki.libgdx.graphics.GameActor;
import com.tanchiki.libgdx.model.explosions.BiggestExplosion;
import com.tanchiki.libgdx.stage.GameStage;
import com.tanchiki.libgdx.util.ObjectVariables;

public class TNT extends GameActor {
    protected Sprite s;
    protected GameStage GameStage;
    protected TextureRegion[] t;
    protected float time;
    protected float T = 3;
    float damage;
    int radius;

    public TNT(float x, float y, float damage, int radius) {
        this.damage = damage;
        this.radius = radius;
        GameStage = GameStage.getInstance();
        t = GameStage.TextureLoader.getIcons()[0];
        s = new Sprite(t[0]);
        setSize(ObjectVariables.size_block * 2, ObjectVariables.size_block * 2);
        setCenterPosition(x, y);
        GameStage.world_mines[(int) x][(int) y] = this;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // TODO: Implement this method
        super.draw(batch, parentAlpha);
        s.setSize(getWidth(), getHeight());
        s.setPosition(getX(), getY());
        s.draw(batch);
    }

    @Override
    public void act(float delta) {
        time += delta;
        if (time > T) {
            GameStage.world_mines[(int) getCenterX()][(int) getCenterY()] = null;
            GameStage.addActor(new BiggestExplosion(getCenterX(), getCenterY(), radius, damage));
            remove();
        }
        // TODO: Implement this method
        super.act(delta);
    }

}
