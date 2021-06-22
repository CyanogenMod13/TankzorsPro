package com.tanchiki.libgdx.model.explosions;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import com.tanchiki.libgdx.model.terrains.*;
import com.tanchiki.libgdx.util.Settings;
import com.tanchiki.libgdx.util.SoundLoader;
import com.tanchiki.libgdx.util.TextureLoader;

public class NormalExplosion extends Explosion {
    private Sprite overlayer;
    private Animation anim;
    public Sound sound = SoundLoader.getInstance().getExpl();

    public NormalExplosion(float x, float y, TextureRegion[][] r) {
        super(x, y, r);
        setPosition(x, y, Align.center);
        if (GameStage.world_obj[(int) x][(int) y] instanceof Sand) {
            if (((Sand) GameStage.world_obj[(int) x][(int) y]).modify)
                GameStage.mainTerrain.ground.addActor(new CrashSand(x, y));
        } else if (GameStage.world_obj[(int) x][(int) y] instanceof Grass)
            GameStage.mainTerrain.ground.addActor(new CrashGrass(x, y));
        else if (GameStage.world_obj[(int) x][(int) y] instanceof Plate) {
            Plate p = (Plate) GameStage.world_obj[(int) x][(int) y];
            p.crash();
        }

        if (GameStage.world_mines[(int) x][(int) y] != null) {
            Actor a = GameStage.world_mines[(int) x][(int) y];
            a.remove();
            GameStage.world_mines[(int) x][(int) y] = null;
        }
        GameStage.mainTerrain.smoke.addActor(new Smoke(getX(Align.center), getY(Align.center)));

        overlayer = new Sprite(TextureLoader.getInstance().getOverlayers()[0][4]);

        anim = new Animation<TextureRegion>(dtime / 3f, TextureLoader.getInstance().getOverlayers()[0][4], TextureLoader.getInstance().getOverlayers()[0][3], TextureLoader.getInstance().getOverlayers()[0][2]);
    }

    public NormalExplosion(float x, float y, TextureRegion[][] r, boolean small) {
        this(x, y, r);
        if (small) {
            s.setSize(s.getWidth() / 2f, s.getHeight() / 2f);
            s.setCenter(x, y);
        }
        sound = null;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        super.draw(batch, parentAlpha);
        //overlayer.setRegion((TextureRegion) anim.getKeyFrame(time, false));
        overlayer.setSize(2 * 3, 2 * 3);
        overlayer.setCenter(getX(Align.center), getY(Align.center));
        overlayer.setAlpha(1f - time);
        overlayer.draw(batch);
    }

    @Override
    public void act(float delta) {
        if (sound != null) {
            sound.play(Settings.volumeEffect);
            sound = null;
        }
        super.act(delta);
    }
}
