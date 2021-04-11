package com.tanchiki.libgdx.model.bullets;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.tanchiki.libgdx.model.tanks.Tank;
import com.tanchiki.libgdx.stage.GameStage;
import com.tanchiki.libgdx.util.SoundLoader;
import com.tanchiki.libgdx.util.TextureLoader;

public class BulletPlusma extends Bullet {
    public BulletPlusma(float x, float y, int angle, float f) {
        super(x, y, angle, 2 / 3f, f, new Array<>(new TextureRegion[] {
                GameStage.getInstance().TextureLoader.getBullets()[0][4],
                GameStage.getInstance().TextureLoader.getBullets()[0][5],
                GameStage.getInstance().TextureLoader.getBullets()[0][6],
                GameStage.getInstance().TextureLoader.getBullets()[0][7]}));
        ID = 2;
		expl = TextureLoader.getInstance().getBlueExpl();
		sound = SoundLoader.getInstance().getShellPlazma();
        //play = ObjectClass.AudioLoader.playPlasmaFire();
    }
	
	public BulletPlusma(float x, float y, int angle, float f, Tank parent) {
        this(x, y, angle, f);
		this.parent = parent;
    }
}
