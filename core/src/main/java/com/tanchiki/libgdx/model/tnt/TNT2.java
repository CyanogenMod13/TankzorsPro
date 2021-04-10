package com.tanchiki.libgdx.model.tnt;

import com.tanchiki.libgdx.util.ObjectVariables;
import com.tanchiki.libgdx.util.WeaponData;

public class TNT2 extends TNT {
    public TNT2(float x, float y) {
        super(x, y, 12, 9 * ObjectVariables.size_block * 2);
        s.setRegion(t[WeaponData.Type.tnt2]);
    }
}
