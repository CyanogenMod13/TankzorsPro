package com.tanchiki.libgdx.model.bonus;

import com.tanchiki.libgdx.model.bonus.Object.Bonus;
import com.tanchiki.libgdx.util.ObjectVarable;
import com.tanchiki.libgdx.util.*;

public class Starer extends Bonus {
    public Starer(float x, float y) {
        super(new int[]{12, 13, 14}, ObjectVarable.star_id, x, y);
		sound = SoundLoader.getInstance().getStarPickup();
    }
}
