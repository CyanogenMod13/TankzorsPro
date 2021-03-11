package com.tanchiki.libgdx.model.tanks;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.tanchiki.libgdx.model.buildes.Flag;
import com.tanchiki.libgdx.model.bullets.*;
import com.tanchiki.libgdx.model.bullets.Object.Bullet;
import com.tanchiki.libgdx.model.terrains.MainTerrain;
import com.tanchiki.libgdx.util.*;


public class TankUser extends DefaultTank {
    public Flag flag = null;
    private final Animation<TextureRegion> animModern = new Animation<>(360 / 16f, GameStage.TextureLoader.getTankHeavy()[0]);

    public TankUser(float x, float y) {
        super(x, y, ObjectVariables.tank_ally, ObjectClass.GameStage.TextureLoader.getTankLight()[0], 1);
        Settings.TankUserSettings.HPbackup = 5 + ((WeaponData.modern_tank > 0) ? 9 : 0) + WeaponData.brone1 + WeaponData.brone2;
        HPBackup = Settings.TankUserSettings.HPbackup;
        Settings.TankUserSettings.HP = (int) HPBackup;
        HP = HPBackup;

        HPShieldBackup = Settings.TankUserSettings.HPShieldBackup;
        Settings.TankUserSettings.HPShield = (int) HPShieldBackup;
        HPShield = HPShieldBackup;

        speed = 0.2f;
        ring.setRing(Ring.USER);
        setAI(new UserAI());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (WeaponData.modern_tank > 0)
            anim = animModern;
        super.draw(batch, parentAlpha);
    }

    @Override
    void incrementSize() {
    }

    @Override
    void decrementSize() {
    }

    @Override
	public void destroyTank(float damage) {
		MainTerrain.getCurrentTerrain().damageUser += (int) damage;
		super.destroyTank(damage);
	}

    @Override
    void createLightBullet() {
        super.createLightBullet();
        WeaponData.light_bullet -= 1;
        MainTerrain.getCurrentTerrain().countFire++;
    }

    @Override
    void createPlazmaBullet() {
        super.createPlazmaBullet();
        WeaponData.plazma -= 1;
        MainTerrain.getCurrentTerrain().countFire++;
    }

    @Override
    void createDoubleLightBullet() {
        super.createDoubleLightBullet();
        WeaponData.double_light_bullet -= 1;
        MainTerrain.getCurrentTerrain().countFire++;
    }

    @Override
    void createDoublePlazmaBullet() {
        super.createDoublePlazmaBullet();
        WeaponData.double_palzma -= 1;
        MainTerrain.getCurrentTerrain().countFire++;
    }

    @Override
    void createBronetBullet1() {
        super.createBronetBullet1();
        WeaponData.bronet_bullet -= 1;
        MainTerrain.getCurrentTerrain().countFire++;
    }

    @Override
    void createBronetBullet2() {
        super.createBronetBullet2();
        WeaponData.bronet_bullet2 -= 1;
        MainTerrain.getCurrentTerrain().countFire++;
    }

    @Override
    void createRocket() {
        super.createRocket();
        WeaponData.rocket -= 1;
        MainTerrain.getCurrentTerrain().countFire++;
    }

    @Override
    public void act(float delta) {
        updateMotion();
        super.act(delta);
		Settings.TankUserSettings.HPbackup = 5 + ((WeaponData.modern_tank > 0) ? 9 : 0) + WeaponData.brone1 + WeaponData.brone2;
        Settings.TankUserSettings.HP = (int) HP;
        HPBackup = Settings.TankUserSettings.HPbackup;

        Settings.TankUserSettings.HPShield = (int) HPShield;
        HPShieldBackup = Settings.TankUserSettings.HPShieldBackup;
		GameStage.moveCam(getCenterX(), getCenterY(), 0.05f);
    }

    private Vec currentMotion = Vec.NONE;

    public void updateMotion() {
        if (defaultAI.isOnBlockWithoutTransform()) {
            defaultAI.isRiding = true;
            switch (currentMotion) {
                case UP: top(); break;
                case LEFT: left(); break;
                case DOWN: bottom(); break;
                case RIGHT: right(); break;
                default:
                    defaultAI.isRiding = false;
            }
        }
    }

    public void setMotion(Vec vec) {
        currentMotion = vec;
    }

    public enum Vec {
        UP(Input.Keys.W), DOWN(Input.Keys.S), LEFT(Input.Keys.A), RIGHT(Input.Keys.D), NONE(0);

        public int key = Input.Keys.A;

        Vec(int key) {
            this.key = key;
        }
    }

	@Override
	protected void updateRun() 	{
		MainTerrain.getCurrentTerrain().distance += speed;
		super.updateRun();
	}

	public void doRepair() {
        if (GameStage.TankUser.HP != GameStage.TankUser.HPBackup)
            if (WeaponData.fix > 0) {
                GameStage.TankUser.HP = GameStage.TankUser.HPBackup;
                WeaponData.fix -= 1;
                SoundLoader.getInstance().getRepairPickup().play(Settings.volumeEffect);
            } else {
                ObjectClass.PanelStage.addToast("Рем. комплект закончился");
            }
        else
            ObjectClass.PanelStage.addToast("Танк не нуждаеться в ремонте");
    }

    public void enterHangar() {
        if (GameStage.world_buildes[(int) getCenterX()][(int) getCenterY()] != null) {
            ObjectClass.StoreStage.show();
        } else {
            ObjectClass.PanelStage.addToast("Вернитесь на базу!");
        }
    }

    public class UserAI extends DefaultAI {
        @Override
        public void update() {
            weapon = Settings.TankUserSettings.bullet_type;

            if (isOnBlock()) {
                updateDirection();
                detectGround();

                if (isRiding)
                    stepWithoutTurn();
            } else {
                updateRun();
            }
        }
    }
}
