package com.tanchiki.libgdx.model.buildes;

import com.tanchiki.libgdx.model.buildes.Object.Build;
import com.tanchiki.libgdx.model.explosions.BiggestExplosion;
import com.tanchiki.libgdx.model.explosions.NormalExplosion;
import com.tanchiki.libgdx.model.terrains.MainTerrain;
import com.tanchiki.libgdx.util.ObjectClass;
import com.tanchiki.libgdx.util.ObjectVariables;

public class ReactorCore extends Build {
    public ReactorCore(float x, float y, short f) {
        super(x, y, ObjectClass.GameStage.TextureLoader.getBuilds()[0][1], f);
        fraction = f;
		HP = HPbackup = MainTerrain.getCurrentTerrain().getParameters().getKey(127);
    }

    @Override
    public void destroyBuilds() {
		GameStage.MT.explosions.addActor(new NormalExplosion(x, y, GameStage.TextureLoader.getExpl()));
		GameStage.MT.explosions.addActor(new NormalExplosion(x + a, y, GameStage.TextureLoader.getExpl()));
		GameStage.MT.explosions.addActor(new NormalExplosion(x, y - a, GameStage.TextureLoader.getExpl()));
		GameStage.MT.explosions.addActor(new NormalExplosion(x + a, y - a, GameStage.TextureLoader.getExpl()));
        Health.remove();
        remove();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
				try {
					Thread.sleep(200);
				} catch (Exception e) {}	
                try {
                    GameStage.MT.decor_ground.addActor(new BiggestExplosion(x, y, 8 * ObjectVariables.size_block * 2, 10));
                    Thread.sleep(500);
                } catch (InterruptedException e) {}
                /*try {
                    GameStage.MT.decor_ground.addActor(new BiggestExplosion(x + a, y, 8 * ObjectVarable.size_block * 2, 10));
                    Thread.sleep(500);
                } catch (InterruptedException e) {}
                try {
                    GameStage.MT.decor_ground.addActor(new BiggestExplosion(x, y - a, 8 * ObjectVarable.size_block * 2, 10));
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }*/
                try {
                    GameStage.MT.decor_ground.addActor(new BiggestExplosion(x + a, y - a, 8 * ObjectVariables.size_block * 2, 10));
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }
            }
        });
        thread.start();
    }

    public static class ReactorCoreUnity extends ReactorCore {
        public ReactorCoreUnity(float x, float y) {
            super(x, y, ObjectVariables.tank_ally);
            code_name = "reactor_unity";
			ObjectVariables.all_size_reactor_enemy++;
        }

		@Override
		public void destroyBuilds() {
			ObjectVariables.all_size_reactor_enemy--;
			super.destroyBuilds();
		}
    }

    public static class ReactorCoreEnemy extends ReactorCore {
        public ReactorCoreEnemy(float x, float y) {
            super(x, y, ObjectVariables.tank_enemy);
            code_name = "reactor_enemy";
        }
    }
}
