package com.tanchiki.libgdx.model.tnt;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import com.tanchiki.libgdx.model.explosions.BiggestExplosion;
import com.tanchiki.libgdx.stage.GameStage;
import com.tanchiki.libgdx.util.ObjectVariables;
import com.tanchiki.libgdx.util.TextureLoader;

public class TNT extends Actor {
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
        t = TextureLoader.getInstance().getIcons()[0];
        s = new Sprite(t[0]);
        setSize(ObjectVariables.size_block * 2, ObjectVariables.size_block * 2);
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
        time += delta;
        if (time > T) {
            GameStage.world_mines[(int) getX(Align.center)][(int) getY(Align.center)] = null;
            GameStage.addActor(new BiggestExplosion(getX(Align.center), getY(Align.center), radius, damage));
            remove();
        }

        super.act(delta);
    }

}
