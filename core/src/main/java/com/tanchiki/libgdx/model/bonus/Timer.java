package com.tanchiki.libgdx.model.bonus;

import com.tanchiki.libgdx.model.bonus.Object.*;
import com.tanchiki.libgdx.util.*;

public class Timer extends Bonus {
    public Timer(float x, float y) {
        super(new int[]{6, 7, 8}, WeaponData.Type.time, x, y);
		sound = SoundLoader.getInstance().getFreezePickup();
    }
}
