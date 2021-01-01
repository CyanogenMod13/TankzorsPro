package com.tanchiki.libgdx.model.buildes;

import com.tanchiki.libgdx.model.terrains.MainTerrain;
import com.tanchiki.libgdx.util.ObjectVarable;

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
