package com.tanchiki.libgdx.model.terrains;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.tanchiki.libgdx.stage.GameStage;
import com.tanchiki.libgdx.util.ObjectVariables;

public class Palm extends Decorate {
    public Sprite s;
    public Sprite s2;
    protected TextureRegion[] t;
    private float a = ObjectVariables.size_block;
    private GameStage g = GameStage.getInstance();

    public Palm(float x, float y) {
        TextureRegion[][] r = GameStage.getInstance().TextureLoader.getPalms();
        t = new TextureRegion[5];
        for (int i = 0; i < 5; i++) {
            t[i] = r[0][i];
        }

        s = new Sprite(t[MathUtils.random(0, 2)]);
        s2 = new Sprite(t[MathUtils.random(3, 4)]);
        setCenterPosition(x, y);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // TODO: Implement this method
        super.draw(batch, parentAlpha);
        s.setSize(a * 8, a * 8);
        s.setCenter(getCenterX(), getCenterY());
        s.draw(batch);

        s2.setSize(a * 8, a * 8);
        s2.setCenter(getCenterX() + a * 2, getCenterY() - a * 2);
        s2.draw(batch);

        setBounds(s.getX(), s.getY(), s.getWidth(), s.getHeight());
    }
}
