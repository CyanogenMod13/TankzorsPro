package com.tanchiki.libgdx.model.bonus;

import com.tanchiki.libgdx.util.SoundLoader;
import com.tanchiki.libgdx.util.WeaponData;

public class Speeder extends Bonus {
    public Speeder(float x, float y) {
        super(new int[]{0, 1, 2}, WeaponData.Type.speed, x, y);
        sound = SoundLoader.getInstance().getSpeedPickup();
    }
}
