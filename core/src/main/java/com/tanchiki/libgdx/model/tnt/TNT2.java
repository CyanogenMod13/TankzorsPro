package com.tanchiki.libgdx.model.tnt;

import com.tanchiki.libgdx.model.tnt.Object.TNT;
import com.tanchiki.libgdx.util.ObjectVarable;
import com.tanchiki.libgdx.util.WeaponData;

public class TNT2 extends TNT {
    public TNT2(float x, float y) {
        super(x, y, 12, 9 * ObjectVarable.size_block * 2);
        s.setRegion(t[WeaponData.Type.tnt2]);
    }
}
