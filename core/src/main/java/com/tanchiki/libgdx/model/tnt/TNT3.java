package com.tanchiki.libgdx.model.tnt;

import com.tanchiki.libgdx.model.tnt.Object.TNT;
import com.tanchiki.libgdx.util.ObjectVariables;
import com.tanchiki.libgdx.util.WeaponData;

public class TNT3 extends TNT {
    public TNT3(float x, float y) {
        super(x, y, 20, 11 * ObjectVariables.size_block * 2);
        s.setRegion(t[WeaponData.Type.tnt3]);
        T = 5;
    }
}
