package com.tanchiki.libgdx.model.bonus;

import com.tanchiki.libgdx.util.ObjectVariables;
import com.tanchiki.libgdx.util.SoundLoader;

public class Moner extends Bonus {
    public Moner(float x, float y) {
        super(new int[]{15, 16, 17}, ObjectVariables.coin_id, x, y);
        sound = SoundLoader.getInstance().getStarPickup();
    }
}
