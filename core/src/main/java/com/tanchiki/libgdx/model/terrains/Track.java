package com.tanchiki.libgdx.model.terrains;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.tanchiki.libgdx.graphics.GameActor;
import com.tanchiki.libgdx.util.ObjectVariables;
import com.tanchiki.libgdx.util.TextureLoader;

public class Track extends GameActor {
    protected TextureRegion[] t = TextureLoader.getInstance().getTracks()[0];
    private float a = ObjectVariables.size_block;
    private float HP = 1;
    private int index;

    public static final int VERTICAL = 2,
            HORIZONTAL = 1,
            RIGHT_DOWN = 4,
            LEFT_DOWN = 5,
            LEFT_UP = 7,
            RIGHT_UP = 8;

    public Track(float x, float y) {
        this(x, y, HORIZONTAL);
    }

    public Track(float x, float y, int rot) {
        setOrientation(rot);
        setSize(a * 2, a * 2);
        setCenterPosition(x, y);
    }

    public void setOrientation(int rot) {
        int index = -1;
        switch (rot) {
            case VERTICAL:
                index = 4;
                break;
            case HORIZONTAL:
                index = 0;
                break;
            case RIGHT_DOWN:
                index = 14;
                break;
            case LEFT_DOWN:
                index = 17;
                break;
            case RIGHT_UP:
                index = 11;
                break;
            case LEFT_UP:
                index = 8;
                break;
        }
        this.index = index;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.setColor(1, 1, 1, HP);
        batch.draw(t[index],
                getX(), getY(),
                getWidth(), getHeight());
        batch.setColor(1, 1, 1, 1);
    }

    @Override
    public void act(float delta) {
        if (HP <= 0) remove();
        HP -= delta / 10;
        super.act(delta);
    }
}
