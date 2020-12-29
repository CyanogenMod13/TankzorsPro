package com.tanchiki.libgdx.model.bonus;

import com.tanchiki.libgdx.model.bonus.Object.*;
import com.tanchiki.libgdx.util.*;

public class Moner extends Bonus {
    public Moner(float x, float y) {
        super(new int[]{15, 16, 17}, ObjectVarable.coin_id, x, y);
		sound = SoundLoader.getInstance().getStarPickup();
    }
}
