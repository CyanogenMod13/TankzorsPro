package com.tanchiki.libgdx.model.bullets;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.*;
import com.tanchiki.libgdx.model.bullets.Object.*;
import com.tanchiki.libgdx.model.tanks.Object.*;
import com.tanchiki.libgdx.util.*;

public class BulletPluzma extends Bullet {
    public BulletPluzma(float x, float y, int angle, float f) {
        super(x, y, angle, 2 / 3f, f, new Array<TextureRegion>(new TextureRegion[]{ObjectClass.GameStage.TextureLoader.getBullets()[0][4], ObjectClass.GameStage.TextureLoader.getBullets()[0][5], ObjectClass.GameStage.TextureLoader.getBullets()[0][6], ObjectClass.GameStage.TextureLoader.getBullets()[0][7]}));
        ID = 2;
		expl = TextureLoader.getInstance().getBlueExpl();
		sound = SoundLoader.getInstance().getShellPlazma();
        //play = ObjectClass.AudioLoader.playPlasmaFire();
    }
	
	public BulletPluzma(float x, float y, int angle, float f, Tank parent) {
        this(x, y, angle, f);
		this.parent = parent;
    }
}
