package com.tanchiki.libgdx.model.tanks;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.tanchiki.libgdx.model.buildes.Flag;
import com.tanchiki.libgdx.model.terrains.MainTerrain;
import com.tanchiki.libgdx.stage.GameStage;
import com.tanchiki.libgdx.stage.PanelStage;
import com.tanchiki.libgdx.stage.StoreStage;
import com.tanchiki.libgdx.util.ObjectVariables;
import com.tanchiki.libgdx.util.Settings;
import com.tanchiki.libgdx.util.SoundLoader;
import com.tanchiki.libgdx.util.WeaponData;


public class TankUser extends DefaultTank {
    public Flag flag = null;
    private final Animation<TextureRegion> animModern = new Animation<>(360 / 16f, gameStage.TextureLoader.getTankHeavy()[0]);

    public TankUser(float x, float y) {
        super(x, y, ObjectVariables.tank_ally, GameStage.getInstance().TextureLoader.getTankLight()[0], 1);
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
    void createPlasmaBullet() {
        super.createPlasmaBullet();
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
    void createDoublePlasmaBullet() {
        super.createDoublePlasmaBullet();
        WeaponData.double_palzma -= 1;
        MainTerrain.getCurrentTerrain().countFire++;
    }

    @Override
    void createArmoredBullet1() {
        super.createArmoredBullet1();
        WeaponData.bronet_bullet -= 1;
        MainTerrain.getCurrentTerrain().countFire++;
    }

    @Override
    void createArmoredBullet2() {
        super.createArmoredBullet2();
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
        updateStateMotion();
        updateStateFire();
        super.act(delta);
		Settings.TankUserSettings.HPbackup = 5 + ((WeaponData.modern_tank > 0) ? 9 : 0) + WeaponData.brone1 + WeaponData.brone2;
        Settings.TankUserSettings.HP = (int) HP;
        HPBackup = Settings.TankUserSettings.HPbackup;

        Settings.TankUserSettings.HPShield = (int) HPShield;
        HPShieldBackup = Settings.TankUserSettings.HPShieldBackup;
		gameStage.moveCam(getCenterX(), getCenterY(), 0.05f);
    }

    private Vec currentStateMotion = Vec.NONE;

    public void updateStateMotion() {
        if (defaultAI.isOnBlockWithoutTransform()) {
            defaultAI.isRiding = true;
            switch (currentStateMotion) {
                case UP: top(); break;
                case LEFT: left(); break;
                case DOWN: bottom(); break;
                case RIGHT: right(); break;
                default:
                    defaultAI.isRiding = false;
            }
        }
    }

    public void setStateMotion(Vec vec) {
        currentStateMotion = vec;
    }

    public Vec getCurrentStateMotion() {
        return currentStateMotion;
    }

    public enum Vec {
        UP(Input.Keys.W), DOWN(Input.Keys.S), LEFT(Input.Keys.A), RIGHT(Input.Keys.D), NONE(0);

        public final int key;

        Vec(int key) {
            this.key = key;
        }
    }

    private Fire currentStateFire = Fire.NONE;

    private void updateStateFire() {
        switch (currentStateFire) {
            case NONE: /* NONE */ break;
            case AIR: defaultAI.AIR(); break;
            case BULLET: defaultAI.BULLET(); break;
        }
    }

    public void setStateFire(Fire fire) {
        currentStateFire = fire;
    }

    public Fire getCurrentStateFire() {
        return currentStateFire;
    }

    public enum Fire {
        BULLET(Input.Keys.NUMPAD_1), AIR(Input.Keys.NUMPAD_3), NONE(0);

        public final int key;

        Fire(int key) {
            this.key = key;
        }
    }

	@Override
	protected void updateRun() 	{
		MainTerrain.getCurrentTerrain().distance += speed;
		super.updateRun();
	}

	public void doRepair() {
        if (gameStage.TankUser.HP != gameStage.TankUser.HPBackup)
            if (WeaponData.fix > 0) {
                gameStage.TankUser.HP = gameStage.TankUser.HPBackup;
                WeaponData.fix -= 1;
                SoundLoader.getInstance().getRepairPickup().play(Settings.volumeEffect);
            } else {
                PanelStage.getInstance().addToast("Рем. комплект закончился");
            }
        else
            PanelStage.getInstance().addToast("Танк не нуждаеться в ремонте");
    }

    public void enterHangar() {
        if (gameStage.worldBuilds[(int) getCenterX()][(int) getCenterY()] != null) {
            StoreStage.getInstance().show();
        } else {
            PanelStage.getInstance().addToast("Вернитесь на базу!");
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
