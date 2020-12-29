package com.tanchiki.libgdx.model.tanks;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.tanchiki.libgdx.model.buildes.AngarUnity;
import com.tanchiki.libgdx.model.buildes.Flag;
import com.tanchiki.libgdx.model.buildes.Object.ObjBuild;
import com.tanchiki.libgdx.model.bullets.*;
import com.tanchiki.libgdx.model.bullets.Object.Bullet;
import com.tanchiki.libgdx.model.tanks.Object.Tank;
import com.tanchiki.libgdx.model.ui.MissionCompleted;
import com.tanchiki.libgdx.util.ObjectClass;
import com.tanchiki.libgdx.util.ObjectVarable;
import com.tanchiki.libgdx.util.Settings;
import com.tanchiki.libgdx.util.WeaponData;
import com.badlogic.gdx.graphics.g2d.*;
import com.tanchiki.libgdx.model.buildes.*;
import com.tanchiki.libgdx.model.terrains.*;


public class TankUser extends Tank {
    public Flag flag = null;

    public TankUser(float x, float y) {
        super(x, y, ObjectVarable.tank_unity, ObjectClass.GameStage.TextureLoader.getTankLight(), 1);
        if (WeaponData.modern_tank > 0)
            anim = new Animation<>(360 / 16f, GameStage.TextureLoader.getTankHeavy()[0]);
        automatic = false;
        Settings.TankUserSettings.HPbackup = 5 + ((WeaponData.modern_tank > 0) ? 9 : 0) + WeaponData.brone1 + WeaponData.brone2;
        HPbackup = Settings.TankUserSettings.HPbackup;
        Settings.TankUserSettings.HP = (int) HPbackup;
        HP = HPbackup;

        HPShieldBackup = Settings.TankUserSettings.HPShieldBackup;
        Settings.TankUserSettings.HPShield = (int) HPShieldBackup;
        HPShield = HPShieldBackup;

        speed = 0.2f;
        ringid = 0;
        ring.setRegion(rings[0]);
		
		//GameStage.cam.position.set(0, 30, 0);
    }

	@Override
	public void destroyTank(float damage) {
		MainTerrain.getCurrentTerrain().damageUser += (int) damage;
		super.destroyTank(damage);
	}
	
    @Override
    protected void creatBullet() {
        switch (weapon) {
            case 1: {
                if (time > 0.8 / speed_skill) {
                    if (WeaponData.light_bullet > 0) {
                        Bullet bullet = new BulletLight(getCenterX(), getCenterY(), angle_for_bullet, fraction);
                        bullet.parent = this;
                        GameStage.MT.bullet.addActor(bullet);
                        WeaponData.light_bullet -= 1;
						MainTerrain.getCurrentTerrain().countFire++;
                    }
                    time = 0;
                }
                break;
            }
            case 2: {
                if (time > 0.5 / speed_skill) {
                    if (WeaponData.plazma > 0) {
                        Bullet bullet = new BulletPluzma(getCenterX(), getCenterY(), angle_for_bullet, fraction);
                        bullet.parent = this;
                        GameStage.MT.bullet.addActor(bullet);
                        WeaponData.plazma -= 1;
						MainTerrain.getCurrentTerrain().countFire++;
                    }
                    time = 0;
                }
                break;
            }
			case 3: {
					if (time > 0.8 / speed_skill) {
						if (WeaponData.double_light_bullet > 0) {
							switch (angle_for_bullet) {
								case 1: 
									GameStage.MT.bullet.addActor(new BulletLight(getCenterX() + 0.2f, getCenterY(), angle_for_bullet, fraction, this));
									GameStage.MT.bullet.addActor(new BulletLight(getCenterX() - 0.2f, getCenterY(), angle_for_bullet, fraction, this));
									break;
								case 2:
									GameStage.MT.bullet.addActor(new BulletLight(getCenterX(), getCenterY() + 0.2f, angle_for_bullet, fraction, this));
									GameStage.MT.bullet.addActor(new BulletLight(getCenterX(), getCenterY() - 0.2f, angle_for_bullet, fraction, this));
									break;
								case 3: 
									GameStage.MT.bullet.addActor(new BulletLight(getCenterX() + 0.2f, getCenterY(), angle_for_bullet, fraction, this));
									GameStage.MT.bullet.addActor(new BulletLight(getCenterX() - 0.2f, getCenterY(), angle_for_bullet, fraction, this));
									break;
								case 4:
									GameStage.MT.bullet.addActor(new BulletLight(getCenterX(), getCenterY() + 0.2f, angle_for_bullet, fraction, this));
									GameStage.MT.bullet.addActor(new BulletLight(getCenterX(), getCenterY() - 0.2f, angle_for_bullet, fraction, this));
									break;	
							}
							WeaponData.double_light_bullet -= 1;
							MainTerrain.getCurrentTerrain().countFire++;
						}
						time = 0;
					}
					break;
				}
            case 4: {
					if (time > 0.5 / speed_skill) {
						if (WeaponData.double_palzma > 0) {
							switch (angle_for_bullet) {
								case 1: 
									GameStage.MT.bullet.addActor(new BulletPluzma(getCenterX() + 0.2f, getCenterY(), angle_for_bullet, fraction, this));
									GameStage.MT.bullet.addActor(new BulletPluzma(getCenterX() - 0.2f, getCenterY(), angle_for_bullet, fraction, this));
									break;
								case 2:
									GameStage.MT.bullet.addActor(new BulletPluzma(getCenterX(), getCenterY() + 0.2f, angle_for_bullet, fraction, this));
									GameStage.MT.bullet.addActor(new BulletPluzma(getCenterX(), getCenterY() - 0.2f, angle_for_bullet, fraction, this));
									break;
								case 3: 
									GameStage.MT.bullet.addActor(new BulletPluzma(getCenterX() + 0.2f, getCenterY(), angle_for_bullet, fraction, this));
									GameStage.MT.bullet.addActor(new BulletPluzma(getCenterX() - 0.2f, getCenterY(), angle_for_bullet, fraction, this));
									break;
								case 4:
									GameStage.MT.bullet.addActor(new BulletPluzma(getCenterX(), getCenterY() + 0.2f, angle_for_bullet, fraction, this));
									GameStage.MT.bullet.addActor(new BulletPluzma(getCenterX(), getCenterY() - 0.2f, angle_for_bullet, fraction, this));
									break;	
							}
							WeaponData.double_palzma -= 1;
							MainTerrain.getCurrentTerrain().countFire++;
						}
						time = 0;
					}
					break;
				}
            case 5: {
                if (time > 0.8 / speed_skill) {
                    if (WeaponData.bronet_bullet > 0) {
                        Bullet bullet = new BronetBullet1(getCenterX(), getCenterY(), angle_for_bullet, fraction);
                        bullet.parent = this;
                        GameStage.MT.bullet.addActor(bullet);
                        WeaponData.bronet_bullet -= 1;
						MainTerrain.getCurrentTerrain().countFire++;
                    }
                    time = 0;
                }
                break;
            }
            case 6: {
                if (time > 0.8 / speed_skill) {
                    if (WeaponData.bronet_bullet2 > 0) {
                        Bullet bullet = new BronetBullet2(getCenterX(), getCenterY(), angle_for_bullet, fraction);
                        bullet.parent = this;
                        GameStage.MT.bullet.addActor(bullet);
                        WeaponData.bronet_bullet2 -= 1;
						MainTerrain.getCurrentTerrain().countFire++;
                    }
                    time = 0;
                }
                break;
            }
            case 7: {
                if (time > 1 / speed_skill) {
                    if (WeaponData.rocket > 0) {
                        Bullet bullet = new Roket(getCenterX(), getCenterY(), angle_for_bullet, fraction);
                        bullet.parent = this;
                        GameStage.MT.bullet.addActor(bullet);
                        WeaponData.rocket -= 1;
						MainTerrain.getCurrentTerrain().countFire++;
                    }
                    time = 0;
                }
                break;
            }
        }
    }

    @Override
    public void act(float delta) {
        updateMotion();
        //System.out.println(getCenterX() + " " + getCenterY());
        super.act(delta);
		Settings.TankUserSettings.HPbackup = 5 + ((WeaponData.modern_tank > 0) ? 9 : 0) + WeaponData.brone1 + WeaponData.brone2;
        Settings.TankUserSettings.HP = (int) HP;
        HPbackup = Settings.TankUserSettings.HPbackup;

        Settings.TankUserSettings.HPShield = (int) HPShield;
        HPShieldBackup = Settings.TankUserSettings.HPShieldBackup;
		
		//if (HP <= 0 && flag != null) flag.active = true;
		//System.out.println(AI.goal_x + " " + AI.goal_y);
		GameStage.moveCam(getCenterX(), getCenterY(), 0.05f);
        //GameStage.cam.position.set(Body.getPosition().x, Body.getPosition().y, 0);
        // TODO: Implement this method
    }

    private Vec currentMotion = Vec.NONE;

    public void updateMotion() {
        switch (currentMotion) {
            case UP: {
                if (AI.isOnBlockWithoutTransform()) {
					top();
				}
                AI.isRiding = true;
                break;
            }
            case LEFT: {
                if (AI.isOnBlockWithoutTransform()) {
					left();
				}
                AI.isRiding = true;
                break;
            }
            case DOWN: {
                if (AI.isOnBlockWithoutTransform()) {
					bottom();
				}	
                AI.isRiding = true;
                break;
            }
            case RIGHT: {
                if (AI.isOnBlockWithoutTransform()) { 
					right();
				}	
                AI.isRiding = true;
                break;
            }
            default:
                AI.isRiding = false;
        }
    }

    public void setMotion(Vec vec) {
        currentMotion = vec;
    }

    public static enum Vec {
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
}
