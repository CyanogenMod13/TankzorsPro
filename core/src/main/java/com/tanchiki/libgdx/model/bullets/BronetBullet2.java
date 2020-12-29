package com.tanchiki.libgdx.model.bullets;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.*;
import com.tanchiki.libgdx.model.bullets.Object.*;
import com.tanchiki.libgdx.model.tanks.Object.*;
import com.tanchiki.libgdx.util.*;

public class BronetBullet2 extends Bullet {
    public BronetBullet2(float x, float y, int angle, float f) {
        super(x, y, angle, 2 / 5f, f, new Array<TextureRegion>(new TextureRegion[]{ObjectClass.GameStage.TextureLoader.getBullets()[0][13], ObjectClass.GameStage.TextureLoader.getBullets()[0][14], ObjectClass.GameStage.TextureLoader.getBullets()[0][15], ObjectClass.GameStage.TextureLoader.getBullets()[0][16]}));
        HP = 5;
		expl = TextureLoader.getInstance().getRedExpl();
    }
	
	public BronetBullet2(float x, float y, int angle, float f, Tank tank) {
		this(x, y, angle, f);
		parent = tank;
	}
}
