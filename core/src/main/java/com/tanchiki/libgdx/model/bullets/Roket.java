package com.tanchiki.libgdx.model.bullets;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.*;
import com.tanchiki.libgdx.model.bullets.Object.*;
import com.tanchiki.libgdx.model.tanks.Object.*;
import com.tanchiki.libgdx.util.*;

public class Roket extends Bullet {
	
    public Roket(float x, float y, int angle, float f) {
        super(x, y, angle, 0, f, new Array<TextureRegion>(new TextureRegion[]{ObjectClass.GameStage.TextureLoader.getBullets()[0][8], ObjectClass.GameStage.TextureLoader.getBullets()[0][9], ObjectClass.GameStage.TextureLoader.getBullets()[0][10], ObjectClass.GameStage.TextureLoader.getBullets()[0][11]}));
        HP = 9;
        a = 2 / 20f;
        a2 = 2 / 3f;
		sound = SoundLoader.getInstance().getShellRocket();
        //play = ObjectClass.AudioLoader.playMissleFire();
    }
	
	public Roket(float x, float y, int angle, float f, Tank tank) {
		this(x, y, angle, f);
		parent = tank;
	}
}
