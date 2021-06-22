package com.tanchiki.libgdx.model.tanks;

import com.tanchiki.libgdx.util.ObjectVariables;
import com.tanchiki.libgdx.util.TextureLoader;

public class TankFriend extends TankUser {
    public TankFriend(float x, float y) {
        super(x, y, ObjectVariables.tank_ally, TextureLoader.getInstance().getTankLight()[0], 0);
        setAI(new DefaultAI() {
            @Override
            public void update() {
            }
        });
    }

    @Override
    protected void userConfig() {
    }
}
