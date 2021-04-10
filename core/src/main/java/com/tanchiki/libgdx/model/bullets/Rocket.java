package com.tanchiki.libgdx.model.bullets;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.tanchiki.libgdx.model.tanks.Tank;
import com.tanchiki.libgdx.util.ObjectClass;
import com.tanchiki.libgdx.util.SoundLoader;

public class Rocket extends Bullet {
	
    public Rocket(float x, float y, int angle, float f) {
        super(x, y, angle, 0, f, new Array<>(new TextureRegion[] {
                ObjectClass.GameStage.TextureLoader.getBullets()[0][8],
                ObjectClass.GameStage.TextureLoader.getBullets()[0][9],
                ObjectClass.GameStage.TextureLoader.getBullets()[0][10],
                ObjectClass.GameStage.TextureLoader.getBullets()[0][11]
        }));
        HP = 9;
        a = 2 / 20f;
        a2 = 2 / 3f;
		sound = SoundLoader.getInstance().getShellRocket();
    }
	
	public Rocket(float x, float y, int angle, float f, Tank tank) {
		this(x, y, angle, f);
		parent = tank;
	}
}
