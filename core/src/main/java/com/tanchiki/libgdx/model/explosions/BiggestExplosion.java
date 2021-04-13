package com.tanchiki.libgdx.model.explosions;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tanchiki.libgdx.graphics.GameActor;
import com.tanchiki.libgdx.graphics.GameGroup;
import com.tanchiki.libgdx.model.buildes.Build;
import com.tanchiki.libgdx.model.tanks.Tank;
import com.tanchiki.libgdx.model.terrains.Block;
import com.tanchiki.libgdx.model.terrains.Concrete3Wall;
import com.tanchiki.libgdx.model.terrains.MainTerrain;
import com.tanchiki.libgdx.model.terrains.Trigger;
import com.tanchiki.libgdx.stage.GameStage;
import com.tanchiki.libgdx.util.ObjectVariables;
import com.tanchiki.libgdx.util.TextureLoader;

public class BiggestExplosion extends GameGroup {
    GameStage GameStage;
    float damage;
    final float T = 0.1f;
    float a = ObjectVariables.size_block * 2;
    float lastTime = 0;
    boolean destroySpike = true;

    public BiggestExplosion(float x, float y, int radius, float damage) {

        this.damage = damage;
        GameStage = GameStage.getInstance();
        int r = (int) (radius - a) / 2;

        float time_xx = 0;
        float time_yy = 0;

        for (int xx = 0; xx < radius; xx += a) {
            time_yy = time_xx;
            for (int yy = 0; yy < r; yy += a) {
                float last_x = 0, last_y = 0;
                time_yy += T;

                last_x = xx + x;
                last_y = y + yy;
                block b = new block(last_x, last_y);
                //b.small = true;//yy == r - 2;
                b.time = time_yy;
                addActor(b);

                b = new block(xx + x, y - yy);
                //b.small = yy == r - 2;
                b.time = time_yy;
                if (b.getCenterX() != last_x || b.getCenterY() != last_y)
                    addActor(b);

                b = new block(x - xx, y + yy);
                //b.small = yy == r - 2;
                b.time = time_yy;
                if (b.getCenterX() != last_x || b.getCenterY() != last_y)
                    addActor(b);

                b = new block(x - xx, y - yy);
                //b.small = yy == r - 2;
                b.time = time_yy;
                if (b.getCenterX() != last_x || b.getCenterY() != last_y)
                    addActor(b);

                lastTime = time_yy;
            }
            r -= a;
            time_xx += T;
        }
		
		/*Array<block> c1 = new Array<block>();
		c1.add(new block(x,y));
		
		Array<block> c2 = new Array<block>();
		c2.add(new block(x,y));*/
        //ObjectClass.AudioLoader.playBigExplosion().play();
    }

    @Override
    public void act(float delta) {
        if (getChildren().size <= 0)
            remove();

        super.act(delta);
    }


    private class block extends GameActor {
        int x, y;
        public float time;
        float t;
        boolean small = false;

        public block(float x, float y) {
            this.x = (int) x;
            this.y = (int) y;
            setSize(a, a);
            setCenterPosition(x, y);
        }

        private boolean hasCreate = true;

        @Override
        public void act(float delta) {
            small = time >= lastTime;
            t += delta;
            if (t >= time) {
                destroy();
                hasCreate = false;
            }

            if (t >= time)
                remove();

            super.act(delta);
        }

        private Tank lastTank = null;

        public void destroy() {
            if (!MainTerrain.getCurrentTerrain().rect.contains(x, y)) return;

            if (GameStage.world_physic_block != null && GameStage.world_tank != null) {
                Tank t = GameStage.MT.hashTanks.get(GameStage.world_block[x][y]);
                if (t != null)
                    if (lastTank != t) {
                        t.HPShield = 0;
                        t.destroyTank(damage);
                        remove();
                        lastTank = t;
                    }

                Block b = (Block) GameStage.world_physic_block[x][y];
                if (b != null && damage >= 4) {
                    if (b instanceof Concrete3Wall) b.destroyWallNow();
                    b.HP -= damage;
                    b.destroyWall();
                    remove();
                }

                Actor bb = GameStage.worldBuilds[x][y];
                if (bb != null && bb instanceof Build) {
                    ((Build) bb).HP -= damage;
                    remove();
                }
				/*Actor mine = GameStage.world_mines[x][y];
				 if(mine != null&&mine instanceof Mine)
				 {
				 mine.remove();
				 }*/

                Actor bonus = GameStage.world_bonus[x][y];
                if (bonus != null)
                    bonus.remove();

                Trigger trigger = GameStage.world_trigger[x][y];
                if (trigger != null && trigger.byExpl)
                    trigger.actived();
            }
            if (hasCreate)
                GameStage.MT.explosions.addActor(new NormalExplosion(x, y, TextureLoader.getInstance().getExpl(), small));
        }
    }
}
