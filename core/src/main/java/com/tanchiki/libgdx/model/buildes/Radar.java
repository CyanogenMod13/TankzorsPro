package com.tanchiki.libgdx.model.buildes;

import com.tanchiki.libgdx.model.buildes.Object.Build;
import com.tanchiki.libgdx.model.explosions.NormalExplosion;
import com.tanchiki.libgdx.model.terrains.MainTerrain;
import com.tanchiki.libgdx.util.ObjectClass;
import com.tanchiki.libgdx.util.ObjectVarable;

public class Radar extends Build {
    public Radar(float x, float y, short f) {
        super(x, y, ObjectClass.GameStage.TextureLoader.getBuilds()[0][0], f);
		HP = HPbackup = MainTerrain.getCurrentTerrain().getParameters().getKey(126);
    }

    @Override
    public void destroyBuilds() {
        GameStage.MT.explosions.addActor(new NormalExplosion(x, y, GameStage.TextureLoader.getExpl()));
		GameStage.MT.explosions.addActor(new NormalExplosion(x + a, y, GameStage.TextureLoader.getExpl()));
		GameStage.MT.explosions.addActor(new NormalExplosion(x, y - a, GameStage.TextureLoader.getExpl()));
		GameStage.MT.explosions.addActor(new NormalExplosion(x + a, y - a, GameStage.TextureLoader.getExpl()));
        Health.remove();
        remove();
    }

    public static class RadarUnity extends Radar {
        public RadarUnity(float x, float y) {
            super(x, y, ObjectVarable.tank_unity);
            code_name = "radar_unity";
			ObjectVarable.all_size_radar_allies++;
        }

		@Override
		public void destroyBuilds() {
			ObjectVarable.all_size_radar_allies--;
			super.destroyBuilds();
		}
    }

    public static class RadarEnemy extends Radar {
        public RadarEnemy(float x, float y) {
            super(x, y, ObjectVarable.tank_enemy);
            code_name = "radar_enemy";
			ObjectVarable.all_size_radar_enemy++;
        }

		@Override
		public void destroyBuilds() {
			ObjectVarable.all_size_radar_enemy--;
			super.destroyBuilds();
		}
    }
}
