package com.tanchiki.libgdx.model.bullets;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.tanchiki.libgdx.model.bullets.Object.Bullet;
import com.tanchiki.libgdx.model.tanks.Tank;
import com.tanchiki.libgdx.util.ObjectClass;

public class BulletLight extends Bullet {
    public BulletLight(float x, float y, int angle, float f) {
        super(x, y, angle, 2 / 5f, f, new Array<>(new TextureRegion[]{ObjectClass.GameStage.TextureLoader.getBullets()[0][0], ObjectClass.GameStage.TextureLoader.getBullets()[0][1], ObjectClass.GameStage.TextureLoader.getBullets()[0][2], ObjectClass.GameStage.TextureLoader.getBullets()[0][3]}));
        ID = 1;
    }
	
	public BulletLight(float x, float y, int angle, float f, Tank parent) {
        this(x, y, angle, f);
		this.parent = parent;
    }
}
