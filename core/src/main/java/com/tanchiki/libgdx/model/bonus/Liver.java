package com.tanchiki.libgdx.model.bonus;

import com.tanchiki.libgdx.model.bonus.Object.Bonus;
import com.tanchiki.libgdx.util.WeaponData;
import com.tanchiki.libgdx.util.*;

public class Liver extends Bonus {
    public Liver(float x, float y) {
        super(new int[]{3, 4, 5}, WeaponData.Type.live, x, y);
		sound = SoundLoader.getInstance().getPowerupPickup();
    }
}
