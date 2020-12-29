package com.tanchiki.libgdx.model.terrains;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.tanchiki.libgdx.graphics.GameActor;
import com.tanchiki.libgdx.util.ObjectClass;
import com.tanchiki.libgdx.util.ObjectVarable;

public class Track extends GameActor {
    public Sprite s;
    protected TextureRegion[] t;
    private float a = ObjectVarable.size_block;
    private int rot;
    private float HP = 1;

    public static final int VERTICAL = 2, HORIZONTAL = 1, RIGHT_DOWN = 4, LEFT_DOWN = 5, LEFT_UP = 7, RIGHT_UP = 8;

    public Track(float x, float y, int rot) {
        this.rot = rot;
        TextureRegion[][] r = ObjectClass.GameStage.TextureLoader.getTracks();
        t = new TextureRegion[20];
        for (int i = 0; i < 20; i++) {
            t[i] = r[0][i];
        }

        if (rot == VERTICAL)
            s = new Sprite(t[4]);
        if (rot == HORIZONTAL)
            s = new Sprite(t[0]);
        if (rot == RIGHT_DOWN)
            s = new Sprite(t[14]);
        if (rot == LEFT_DOWN)
            s = new Sprite(t[17]);
        if (rot == RIGHT_UP)
            s = new Sprite(t[11]);
        if (rot == LEFT_UP)
            s = new Sprite(t[8]);

        setCenterPosition(x, y);
        //setVisible(false);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // TODO: Implement this method
        super.draw(batch, parentAlpha);

        s.setSize(a * 2, a * 2);
        s.setOrigin(s.getWidth() / 2, s.getHeight() / 2);
        s.setCenter(getCenterX(), getCenterY());
        s.draw(batch);
    }

    @Override
    public void act(float delta) {
        if (HP <= 0)
            remove();
        s.setAlpha(HP);
        HP -= delta / 10;
        // TODO: Implement this method
        super.act(delta);
    }

}
