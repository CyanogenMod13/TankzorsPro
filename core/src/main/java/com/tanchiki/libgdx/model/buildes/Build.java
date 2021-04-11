package com.tanchiki.libgdx.model.buildes;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tanchiki.libgdx.model.terrains.MainTerrain;
import com.tanchiki.libgdx.stage.GameStage;
import com.tanchiki.libgdx.util.ObjectVariables;
import com.tanchiki.libgdx.util.Settings;

abstract public class Build extends ObjBuild {
    public float a = ObjectVariables.size_block * 2;
    public GameStage gameStage;
    private float size;
    private Sprite s;
    private float time;
    public float HP = 10;
    public float HPbackup = HP;
    public float fraction;
    public Health Health;
    public float x, y;
    public Sprite overlayer;
	public boolean give_coin = false;
	public int coin_price;

    public Build(float x, float y, TextureRegion r, short f) {
        this.fraction = f;
        this.x = x;
        this.y = y;
        gameStage = gameStage.getInstance();
        s = new Sprite(r);
        size = (s.getHeight() * a) / s.getWidth();
        setCenterPosition(x, y);
        try {
            if (gameStage.world_nodes != null) {
                gameStage.world_nodes[(int) x][(int) y] = 1;
                gameStage.world_nodes[(int) (x + a)][(int) y] = 1;
                gameStage.world_nodes[(int) (x)][(int) (y - a)] = 1;
                gameStage.world_nodes[(int) (x + a)][(int) (y - a)] = 1;
            }
            if (gameStage.world_block != null) {
                gameStage.world_block[(int) x][(int) y] = f;
                gameStage.world_block[(int) (x + a)][(int) y] = f;
                gameStage.world_block[(int) (x)][(int) (y - a)] = f;
                gameStage.world_block[(int) (x + a)][(int) (y - a)] = f;
            }
            if (gameStage.worldBuilds != null) {
                gameStage.worldBuilds[(int) x][(int) y] = this;
                gameStage.worldBuilds[(int) (x + a)][(int) y] = this;
                gameStage.worldBuilds[(int) (x)][(int) (y - a)] = this;
                gameStage.worldBuilds[(int) (x + a)][(int) (y - a)] = this;
            }

        } catch (ArrayIndexOutOfBoundsException e) {
        }
        Health = new Health();

        gameStage.MT.health.addActor(Health);
        //overlayer = new Overlayer(Body[0].getPosition().x + a / 2, Body[0].getPosition().y - a / 2,f);
        if (fraction == ObjectVariables.tank_ally)
            overlayer = new Sprite(gameStage.TextureLoader.getOverlayers()[0][0]);
        if (fraction == ObjectVariables.tank_enemy)
            overlayer = new Sprite(gameStage.TextureLoader.getOverlayers()[0][1]);
			
		overlayer.setSize(6 * ObjectVariables.size_block * 2, 6 * ObjectVariables.size_block * 2);
		overlayer.setCenter(x + a / 2, y - a / 2);
		
		s.setSize(a * 2.5f, size * 2.5f);
        s.setCenter(x + a / 2, y - a / 2);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // TODO: Implement this method
        super.draw(batch, parentAlpha);
        if (!Settings.edit_map_mode) {
            overlayer.draw(batch);
        }
        s.draw(batch);

        setBounds(s.getX(), s.getY(), s.getWidth(), s.getHeight());
        //Health.draw(batch, parentAlpha);
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public float getX(int alignment) {
        return getX();
    }

    @Override
    public float getY(int alignment) {
        return getY();
    }

    public abstract void destroyBuilds();

    @Override
    public void act(float delta) {
        //time += delta;
		coin_price = (int) HPbackup * 10;
        if (HP <= 0) {
			if (give_coin) {
				MainTerrain.getCurrentTerrain().coin += coin_price;
				Settings.TankUserSettings.coin += coin_price;
			}
            destroyBuilds();
            try {
                if (gameStage.world_nodes != null) {
                    gameStage.world_nodes[(int) x][(int) y] = 0;
                    gameStage.world_nodes[(int) (x + a)][(int) y] = 0;
                    gameStage.world_nodes[(int) (x)][(int) (y - a)] = 0;
                    gameStage.world_nodes[(int) (x + a)][(int) (y - a)] = 0;
                }
                if (gameStage.world_block != null) {
                    gameStage.world_block[(int) x][(int) y] = 0;
                    gameStage.world_block[(int) (x + a)][(int) y] = 0;
                    gameStage.world_block[(int) (x)][(int) (y - a)] = 0;
                    gameStage.world_block[(int) (x + a)][(int) (y - a)] = 0;
                }

                if (gameStage.worldBuilds != null) {
                    gameStage.worldBuilds[(int) x][(int) y] = null;
                    gameStage.worldBuilds[(int) (x + a)][(int) y] = null;
                    gameStage.worldBuilds[(int) (x)][(int) (y - a)] = null;
                    gameStage.worldBuilds[(int) (x + a)][(int) (y - a)] = null;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
            }
        }
        //Health.act(delta);
        // TODO: Implement this method
        super.act(delta);
    }

    public class Health extends Actor {
        private TextureRegion h[] = new TextureRegion[12];
        private Sprite helth;

        public Health() {
            if (fraction == ObjectVariables.tank_enemy) {
                h = gameStage.TextureLoader.getHealthRed()[0];
            }
            if (fraction == ObjectVariables.tank_ally) {
                h = gameStage.TextureLoader.getHealthYell()[0];
            }
            helth = new Sprite(h[11]);
			helth.setSize(a * 2, 0.3f);
			helth.setCenter(x + a / 2, y + a + 0.1f);
			//helth.setRegion(h[Math.max((int) ((12 * HP) / HPbackup) - 1, 0)]);
			setSize(helth.getWidth(), helth.getHeight());
			setPosition(helth.getX(), helth.getY());
        }

		@Override
		public void act(float delta) {
			helth.setSize(a * 2, 0.3f);
			helth.setCenter(x + a / 2, y + a + 0.1f);
			helth.setRegion(h[Math.max((int) ((12 * HP) / HPbackup) - 1, 0)]);
			setSize(helth.getWidth(), helth.getHeight());
			setPosition(helth.getX(), helth.getY());
			// TODO: Implement this method
			super.act(delta);
		}

        @Override
        public void draw(Batch batch, float parentAlpha) {
            super.draw(batch, parentAlpha);
            helth.draw(batch);
        }

    }

}
