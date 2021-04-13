package com.tanchiki.libgdx.model.bonus;

import com.tanchiki.libgdx.util.SoundLoader;
import com.tanchiki.libgdx.util.WeaponData;

public class Timer extends Bonus {
    public Timer(float x, float y) {
        super(new int[]{6, 7, 8}, WeaponData.Type.time, x, y);
        sound = SoundLoader.getInstance().getFreezePickup();
    }
}
