package com.tanchiki.libgdx.model.buildes;

import com.tanchiki.libgdx.model.explosions.NormalExplosion;
import com.tanchiki.libgdx.model.terrains.MainTerrain;
import com.tanchiki.libgdx.stage.GameStage;
import com.tanchiki.libgdx.util.ObjectVariables;

public class Radar extends Build {
    public Radar(float x, float y, short f) {
        super(x, y, GameStage.getInstance().TextureLoader.getBuilds()[0][0], f);
		HP = HPbackup = MainTerrain.getCurrentTerrain().getParameters().getKey(126);
    }

    @Override
    public void destroyBuilds() {
        gameStage.MT.explosions.addActor(new NormalExplosion(x, y, gameStage.TextureLoader.getExpl()));
		gameStage.MT.explosions.addActor(new NormalExplosion(x + a, y, gameStage.TextureLoader.getExpl()));
		gameStage.MT.explosions.addActor(new NormalExplosion(x, y - a, gameStage.TextureLoader.getExpl()));
		gameStage.MT.explosions.addActor(new NormalExplosion(x + a, y - a, gameStage.TextureLoader.getExpl()));
        Health.remove();
        remove();
    }

    public static class RadarUnity extends Radar {
        public RadarUnity(float x, float y) {
            super(x, y, ObjectVariables.tank_ally);
            code_name = "radar_unity";
			ObjectVariables.all_size_radar_allies++;
        }

		@Override
		public void destroyBuilds() {
			ObjectVariables.all_size_radar_allies--;
			super.destroyBuilds();
		}
    }

    public static class RadarEnemy extends Radar {
        public RadarEnemy(float x, float y) {
            super(x, y, ObjectVariables.tank_enemy);
            code_name = "radar_enemy";
			ObjectVariables.all_size_radar_enemy++;
        }

		@Override
		public void destroyBuilds() {
			ObjectVariables.all_size_radar_enemy--;
			super.destroyBuilds();
		}
    }
}
