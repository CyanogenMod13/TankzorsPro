package com.tanchiki.libgdx.model.bullets.Object;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.tanchiki.libgdx.graphics.GameActor;
import com.tanchiki.libgdx.model.buildes.Object.Build;
import com.tanchiki.libgdx.model.buildes.Object.ObjBuild;
import com.tanchiki.libgdx.model.explosions.SmallExplosion;
import com.tanchiki.libgdx.model.tanks.Object.Tank;
import com.tanchiki.libgdx.model.tanks.TankUser;
import com.tanchiki.libgdx.model.tanks.Turret;
import com.tanchiki.libgdx.model.terrains.IronWall;
import com.tanchiki.libgdx.model.terrains.Object.Block;
import com.tanchiki.libgdx.model.terrains.Object.DestroyableBlock;
import com.tanchiki.libgdx.model.terrains.Spike;
import com.tanchiki.libgdx.stage.GameStage;
import com.tanchiki.libgdx.util.*;

public class Bullet extends GameActor {
    //Body Body;
    Sprite s;
    TextureRegion[] t;
    protected GameStage GameStage;
    float speed;
    public float HP = 1;
    public float fraction;
    public int angle;
    public Tank parent;
    public int ID = 0;
    //public Sound play;
    public boolean isPlay = true;

    private final Vector2 pos = new Vector2();
	private Rectangle body = new Rectangle();

	public TextureRegion[][] expl;
	
	public Sound sound = SoundLoader.getInstance().getShellBullet();
	
    public Bullet(float x, float y, int angle, float speed, float f, Array<TextureRegion> r) {
        //play = ObjectClass.AudioLoader.playBulletFire();
		expl = TextureLoader.getInstance().getExpl();
        this.angle = angle;
        fraction = f;
        this.speed = speed;
        GameStage = ObjectClass.GameStage;
        t = new TextureRegion[4];

        for (int i = 0; i < 4; i++) {
            t[i] = r.get(i);
        }
        s = new Sprite(t[0]);
        //Body.setBullet(false);
        s.setSize(s.getWidth() / 10 * ObjectVarable.size_block, s.getHeight() / 10 * ObjectVarable.size_block);
        //Body.setUserData(this);
        setSize(s.getWidth(), s.getHeight());
        setCenterPosition(x, y);
        pos.set(Math.round(getCenterX()), Math.round(getCenterY()));
        if (fraction == ObjectVarable.tank_unity) GameStage.world_bullets_unity[(int) pos.x][(int) pos.y] = this;
        if (fraction == ObjectVarable.tank_enemy) GameStage.world_bullets_enemy[(int) pos.x][(int) pos.y] = this;
		body.setSize(2, 2);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // TODO: Implement this method
        super.draw(batch, parentAlpha);
        if (angle == 1)
            s.setRegion(t[0]);
        if (angle == 4)
            s.setRegion(t[1]);
        if (angle == 3)
            s.setRegion(t[2]);
        if (angle == 2)
            s.setRegion(t[3]);
        //setCenterPosition(Body.getPosition().x,Body.getPosition().y);
        s.setCenter(getCenterX(), getCenterY());
        s.draw(batch);
        //FontLoader.f16.draw(batch, "HP:" + HP, getCenterX(), getCenterY());
    }

    public void destroyBullet() {
        if (HP <= 0) {
            GameStage.MT.explosions.addActor(new SmallExplosion(getCenterX(), getCenterY(), expl));

            if (fraction == ObjectVarable.tank_unity)
                if (pos.x >= 0 && pos.x < GameStage.world_wight && pos.y >= 0 && pos.y < GameStage.world_height)
                    GameStage.world_bullets_unity[(int) pos.x][(int) pos.y] = null;
            if (fraction == ObjectVarable.tank_enemy)
                if (pos.x >= 0 && pos.x < GameStage.world_wight && pos.y >= 0 && pos.y < GameStage.world_height)
                    GameStage.world_bullets_enemy[(int) pos.x][(int) pos.y] = null;
            if (fraction == ObjectVarable.tank_unity)
                if (x >= 0 && x < GameStage.world_wight && y >= 0 && y < GameStage.world_height)
                    GameStage.world_bullets_unity[x][y] = null;
            if (fraction == ObjectVarable.tank_enemy)
                if (x >= 0 && x < GameStage.world_wight && y >= 0 && y < GameStage.world_height)
                    GameStage.world_bullets_enemy[x][y] = null;
            remove();
        }
    }

    protected float a = 0;
    protected float a2 = 0;

    protected void speed(float delta) {
        if (a > a2)
            a = a2;
        switch (angle) {
            case 1: {
                moveBy(0, -(speed + this.a));
                break;
            }
            case 2: {
                moveBy(speed + this.a, 0);
                break;
            }
            case 3: {
                moveBy(0, speed + this.a);
                break;
            }
            case 4: {
                moveBy(-(speed + this.a), 0);
                break;
            }
        }
        if (this.a <= this.a2)
            this.a += this.a * delta;
        else
            this.a = this.a2;
    }

    private int x, y;

    @Override
    public void act(float delta) {
        //if (isPlay) play.play();

		if (sound != null) {
			sound.play(Settings.volumeEffect);
			sound = null;
		}
		
        isPlay = false;

        if (!GameStage.MT.rect.contains(getCenterX(), getCenterY())) {
            //Body.setUserData("delete");
            HP = 0;
            destroyBullet();
            return;
        }

        destroyBullet();
        speed(delta);

        x = Math.round(getCenterX());
        y = Math.round(getCenterY());

        if (!(x >= 0 && x < GameStage.world_wight && y >= 0 && y < GameStage.world_height)) return;

        if (fraction == ObjectVarable.tank_unity) GameStage.world_bullets_unity[(int) pos.x][(int) pos.y] = null;
        if (fraction == ObjectVarable.tank_enemy) GameStage.world_bullets_enemy[(int) pos.x][(int) pos.y] = null;
        pos.set(x, y);
        if (fraction == ObjectVarable.tank_unity) GameStage.world_bullets_unity[(int) pos.x][(int) pos.y] = this;
        if (fraction == ObjectVarable.tank_enemy) GameStage.world_bullets_enemy[(int) pos.x][(int) pos.y] = this;

        Tank tank = GameStage.MT.hashTanks.get(GameStage.world_block[x][y]);
        if (tank != null)
            if (tank.fraction != fraction) {
                body.setCenter(tank.getCenterX(), tank.getCenterY());
				if (body.contains(getCenterX(), getCenterY())) {
					if (parent instanceof TankUser) {
						tank.giveCoin = true;
						tank.giveDamage += HP;
					}	
					final float tankHP = tank.HP + tank.HPShield;
					if (tank.HPShield > 0) 
						SoundLoader.getInstance().getHitShield().play(Settings.volumeEffect);
					else 
						SoundLoader.getInstance().getHitMetal().play(Settings.volumeEffect);
					tank.destroyTank(HP);
					HP -= Math.max(tankHP, 0);
					destroyBullet();
				}
            }

        Bullet bullet = (fraction == ObjectVarable.tank_enemy) ? GameStage.world_bullets_unity[(int) pos.x][(int) pos.y] : GameStage.world_bullets_enemy[(int) pos.x][(int) pos.y];
        if (bullet != null) {
            final float bulletHP = bullet.HP;
            bullet.HP -= Math.max(HP, 0);
            HP -= Math.max(bulletHP, 0);
            bullet.destroyBullet();
            destroyBullet();
        }

        Block block = GameStage.world_physic_block[x][y];
        if (block != null && !(block instanceof Spike) && !(parent instanceof Turret) || block instanceof IronWall) {
            block.bullet = this;
            final float blockHP = block.HP;
            block.HP -= Math.max(HP, 0);
            if (block instanceof DestroyableBlock) HP -= blockHP;
            else HP = 0;
            block.destroyWall();
			block.sound.play(Settings.volumeEffect);
            destroyBullet();
        }

        ObjBuild objbuild = GameStage.world_buildes[x][y];
        Build build = objbuild instanceof Build ? (Build) objbuild : null;
        if (build != null)
            if (build.fraction != fraction) {
				if (objbuild instanceof Build && parent instanceof TankUser) ((Build) objbuild).give_coin = true;
                final float buildHP = build.HP;
                build.HP -= Math.max(HP, 0);
                HP -= Math.max(buildHP, 0);
                destroyBullet();
            }

        // TODO: Implement this method
        super.act(delta);
    }

}
