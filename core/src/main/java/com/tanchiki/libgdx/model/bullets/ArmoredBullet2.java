package com.tanchiki.libgdx.model.bullets;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.tanchiki.libgdx.model.tanks.Tank;
import com.tanchiki.libgdx.stage.GameStage;
import com.tanchiki.libgdx.util.TextureLoader;

public class ArmoredBullet2 extends Bullet {
    public ArmoredBullet2(float x, float y, int angle, float f) {
        super(x, y, angle, 2 / 5f, f, new Array<>(new TextureRegion[]{
        		GameStage.getInstance().TextureLoader.getBullets()[0][13],
				GameStage.getInstance().TextureLoader.getBullets()[0][14],
				GameStage.getInstance().TextureLoader.getBullets()[0][15],
				GameStage.getInstance().TextureLoader.getBullets()[0][16]}));
        HP = 5;
		expl = TextureLoader.getInstance().getRedExpl();
    }
	
	public ArmoredBullet2(float x, float y, int angle, float f, Tank tank) {
		this(x, y, angle, f);
		parent = tank;
	}
}
