package com.tanchiki.libgdx.model.explosions.Object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.tanchiki.libgdx.graphics.GameActor;
import com.tanchiki.libgdx.stage.GameStage;
import com.tanchiki.libgdx.util.ObjectClass;
import com.tanchiki.libgdx.util.ObjectVarable;
import com.badlogic.gdx.utils.*;

public class Explosion extends GameActor {
    protected GameStage GameStage;
    private float a = ObjectVarable.size_block;
    protected Animation<TextureRegion> anim;
    public Sprite s;
    protected float time = 0;
    public static float dtime;
	public float duration;

    public Explosion(float x, float y, TextureRegion[][] r) {
        this.GameStage = ObjectClass.GameStage;
        
        s = new Sprite(r[0][3]);
        s.setSize(a * 3.5f, a * 3.5f);
        s.setCenter(x, y);
		setSize(a * 3.5f, a * 3.5f);
       	setPosition(x, y, Align.center);
		duration = 1f / r[0].length;
        anim = new Animation<TextureRegion>(duration, r[0]);
        dtime = r[0].length * Gdx.graphics.getDeltaTime(); //anim.getFrameDuration();
        //anim.setPlayMode(Animation.PlayMode.REVERSED);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // TODO: Implement this method
        super.draw(batch, parentAlpha);
		anim.setFrameDuration(duration);
        s.setRegion(anim.getKeyFrame(time, false));
        s.draw(batch);
    }

    protected void destroyExpl() {
        if (anim.isAnimationFinished(time))
            remove();
    }

    @Override
    public void act(float delta) {
        time += delta;
        destroyExpl();
        // TODO: Implement this method
        super.act(delta);
    }

}
