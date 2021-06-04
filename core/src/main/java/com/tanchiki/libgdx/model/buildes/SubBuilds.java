package com.tanchiki.libgdx.model.buildes;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.tanchiki.libgdx.stage.GameStage;
import com.tanchiki.libgdx.util.ObjectVariables;
import com.tanchiki.libgdx.util.Settings;
import com.tanchiki.libgdx.util.TextureLoader;

abstract public class SubBuilds extends ObjBuild {
    public static final int[/*type group of tank*/][/*level difficult*/][/*parameters of tank*/] tanks_prm = {
            /*{tanktype, HP, weapon, Shield, speed}*/
            {{1, 3, 1, 0, 0}, {1, 3, 1, 0, 1}, {1, 5, 1, 0, 1}, {1, 3, 2, 4, 0}, {1, 5, 2, 0, 2}},
            {{1, 5, 1, 0, 0}, {1, 7, 1, 0, 0}, {1, 9, 1, 0, 0}, {1, 8, 3, 5, 0}, {1, 7, 1, 0, 0}},
            {{1, 6, 2, 0, 0}, {1, 8, 2, 0, 0}, {1, 9, 2, 3, 0}, {1, 9, 4, 7, 0}, {1, 10, 7, 0, 0}},
            {{2, 1, 1, 0, 0}, {2, 3, 1, 0, 0}, {2, 3, 1, 0, 1}, {2, 5, 1, 0, 2}, {2, 1, 1, 0, 2}},
            {{3, 8, 3, 0, 0}, {3, 10, 3, 3, 0}, {3, 12, 3, 4, 0}, {3, 16, 5, 8, 0}, {3, 60, 3, 10, 0}},
            {{3, 8, 4, 0, 0}, {3, 10, 4, 3, 0}, {3, 12, 4, 4, 0}, {3, 16, 6, 8, 0}, {3, 2, 4, 30, 0}},
            {{4, 5, 1, 0, 0}, {4, 10, 1, 0, 0}, {4, 15, 1, 0, 0}, {4, 30, 1, 0, 0}, {4, 90, 1, 0, 0}},
            {{3, 20, 3, 7, 0}, {3, 30, 3, 15, 0}, {3, 40, 5, 20, 1}, {3, 90, 7, 40, 1}, {3, 150, 6, 60, 1}}
    };

    private float a = ObjectVariables.size_block * 2;
    public GameStage gameStage;
    private Sprite s;
    private float time;
    public float HP = 40;
    public Sprite overlayer;

    public SubBuilds(float x, float y, TextureRegion r, short fraction) {
        gameStage = GameStage.getInstance();
        s = new Sprite(r);
        setCenterPosition(x, y);

        if (fraction == ObjectVariables.tank_ally)
            overlayer = new Sprite(TextureLoader.getInstance().getOverlayers()[0][0]);
        if (fraction == ObjectVariables.tank_enemy)
            overlayer = new Sprite(TextureLoader.getInstance().getOverlayers()[0][1]);

        overlayer.setSize(6 * ObjectVariables.size_block * 2, 6 * ObjectVariables.size_block * 2);
        overlayer.setCenter(x, y);

        s.setSize(a, a);
        s.setCenter(x, y);

        setBounds(s.getX(), s.getY(), s.getWidth(), s.getHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        super.draw(batch, parentAlpha);
        if (!Settings.edit_map_mode) {
            overlayer.draw(batch);
        }
        s.draw(batch);
    }

    public abstract void destroyBuilds();

    @Override
    public void act(float delta) {
        //time += delta;
        if (HP < 0)
            destroyBuilds();

        super.act(delta);
    }

}
