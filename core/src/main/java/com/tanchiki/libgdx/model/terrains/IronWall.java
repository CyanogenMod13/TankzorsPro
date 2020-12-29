package com.tanchiki.libgdx.model.terrains;

import com.tanchiki.libgdx.model.terrains.Object.*;
import com.tanchiki.libgdx.util.*;

public class IronWall extends Block {
    public IronWall(float x, float y) {
        super(x, y);
        s.setRegion(t[11]);
		sound = SoundLoader.getInstance().getHitMetal();
    }

    @Override
    public void destroyWall() {
        // TODO: Implement this method
    }
}
