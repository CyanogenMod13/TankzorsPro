package com.tanchiki.libgdx.model.bullets;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.tanchiki.libgdx.model.tanks.Tank;
import com.tanchiki.libgdx.stage.GameStage;

public class BulletLight extends Bullet {
    public BulletLight(float x, float y, int angle, float f) {
        super(x, y, angle, 2 / 5f, f, new Array<>(new TextureRegion[]{
                GameStage.getInstance().TextureLoader.getBullets()[0][0],
                GameStage.getInstance().TextureLoader.getBullets()[0][1],
                GameStage.getInstance().TextureLoader.getBullets()[0][2],
                GameStage.getInstance().TextureLoader.getBullets()[0][3]}));
        ID = 1;
    }
	
	public BulletLight(float x, float y, int angle, float f, Tank parent) {
        this(x, y, angle, f);
		this.parent = parent;
    }
}
