package com.tanchiki.libgdx.model.bonus;

import com.tanchiki.libgdx.model.bonus.Object.Bonus;
import com.tanchiki.libgdx.util.SoundLoader;
import com.tanchiki.libgdx.util.WeaponData;

public class Fixer extends Bonus {
    public Fixer(float x, float y) {
        super(new int[]{9, 10, 11}, WeaponData.Type.fix, x, y);
		sound = SoundLoader.getInstance().getRepairPickup();
    }
}
