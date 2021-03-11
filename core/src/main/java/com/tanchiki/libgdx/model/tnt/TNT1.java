package com.tanchiki.libgdx.model.tnt;

import com.tanchiki.libgdx.model.tnt.Object.TNT;
import com.tanchiki.libgdx.util.ObjectVariables;
import com.tanchiki.libgdx.util.WeaponData;

public class TNT1 extends TNT {
    public TNT1(float x, float y) {
        super(x, y, 8, 7 * ObjectVariables.size_block * 2);
        s.setRegion(t[WeaponData.Type.tnt1]);
    }
}
