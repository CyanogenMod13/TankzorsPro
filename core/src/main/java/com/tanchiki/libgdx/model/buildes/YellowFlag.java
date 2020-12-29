package com.tanchiki.libgdx.model.buildes;

import com.tanchiki.libgdx.model.tanks.*;
import com.tanchiki.libgdx.model.ui.*;
import com.tanchiki.libgdx.util.*;
import com.tanchiki.libgdx.model.terrains.*;

public class YellowFlag extends Flag
 {
    public YellowFlag(float x, float y) {
        super(x, y, 2);
    }

	@Override
	void clicked() {
		switch (MainTerrain.Mission.CODE) {
			case 15:
			case 16:
			case 17:	
			case 2: win(); break;
			case 4: if (ObjectVarable.all_size_enemy == 0) win(); break;
			case 5: if (ObjectVarable.all_size_boss_enemy == 0) win(); break;
		}
	}
}
