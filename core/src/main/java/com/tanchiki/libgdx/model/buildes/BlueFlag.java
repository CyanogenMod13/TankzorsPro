package com.tanchiki.libgdx.model.buildes;
import com.tanchiki.libgdx.model.terrains.*;
import com.tanchiki.libgdx.stage.*;
import com.tanchiki.libgdx.util.*;
import com.tanchiki.libgdx.model.aircraft.*;

public class BlueFlag extends Flag {
	public static int LAST_INDEX = 0;
	public static int COUNT = 0;
	
	public int index = 0;
	
    public BlueFlag(float x, float y) {
        super(x, y, 0);
		COUNT++;
		index = LAST_INDEX++;
    }

	@Override
	void clicked() {
		remove();
		switch (MainTerrain.Mission.CODE) {
			case 6:
			case 35:
			case 36:
			case 37: 
				if (COUNT == 0) win(); else ObjectClass.PanelStage.addToast("Отлично, ищем дальше!"); break;
			default: if (index < 3) {
				int x0 = MainTerrain.getCurrentTerrain().getParameters().getKey(27 + index) * 2;
				int y0 = GameStage.getInstance().world_height - MainTerrain.getCurrentTerrain().getParameters().getKey(28 + index) * 2;
				MainTerrain.getCurrentTerrain().decor.addActor(new airplane(x0, y0, 3, 10));
			}	
		}
	}

	@Override
	public boolean remove() {
		SoundLoader.getInstance().getFlagPickup().play(Settings.volumeEffect);
		COUNT--;
		return super.remove();
	}

	@Override
	public void act(float delta) {
		LAST_INDEX = 0;
		super.act(delta);
	}	
}
