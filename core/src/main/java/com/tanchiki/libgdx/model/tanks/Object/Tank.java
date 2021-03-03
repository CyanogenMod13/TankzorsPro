package com.tanchiki.libgdx.model.tanks.Object;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;
import com.tanchiki.libgdx.graphics.GameActor;
import com.tanchiki.libgdx.model.buildes.Object.Build;
import com.tanchiki.libgdx.model.buildes.Object.ObjBuild;
import com.tanchiki.libgdx.model.bullets.*;
import com.tanchiki.libgdx.model.explosions.NormalExplosion;
import com.tanchiki.libgdx.model.mine.MineUnity1;
import com.tanchiki.libgdx.model.mine.MineUnity2;
import com.tanchiki.libgdx.model.tanks.TankUser;
import com.tanchiki.libgdx.model.tanks.Turret;
import com.tanchiki.libgdx.model.terrains.*;
import com.tanchiki.libgdx.model.terrains.Object.Block;
import com.tanchiki.libgdx.model.terrains.Object.DestroyableBlock;
import com.tanchiki.libgdx.model.tnt.TNT1;
import com.tanchiki.libgdx.model.tnt.TNT2;
import com.tanchiki.libgdx.model.tnt.TNT3;
import com.tanchiki.libgdx.stage.GameStage;
import com.tanchiki.libgdx.util.*;
import com.tanchiki.libgdx.util.astar.AStarNode;
import com.tanchiki.libgdx.util.astar.AStarPath;

public class Tank extends GameActor {
    protected GameStage GameStage;
    private final float a = ObjectVarable.size_block;
    protected TextureRegion[] tl;
    public Animation<TextureRegion> anim;
    public short fraction;
    public Sprite s;
    private int angle = 0;
    private int angleSet = 0;
    public float HP = 3;
    public float HPBackup;
    public float HPShield = 0;
    public float HPShieldBackup = 0;
    public float speed = 1;
    protected float time;
    public boolean rotate = true;
    private final Sprite health;

    public Sprite ring;
    public TextureRegion[] rings;

    private TextureRegion[] h = new TextureRegion[12];
    public int direction = 1, lastDirection = 1;

    public int weapon = 1;

    public AI AI;

    private int id = 0;

    private float speedr = 0;

    public float speed_skill = 1;

    public float speed_time = 0;

    public boolean speed_hack = false;

    public static final int DOWN = 1,
                            RIGHT = 2,
                            UP = 3,
                            LEFT = 4;

    public boolean automatic = true;

    public Health Health;

    public Ring Ring;

    public static boolean stop_enemy = false;

    public static boolean stop_unity = false;

    public int ringId = 0;

    public boolean hasRide = true;

    public boolean giveCoin = false;
	
	public int giveDamage = 0;

    public float coinPrice = 0;

	public boolean boss = false;

    public Tank(float x, float y, short f, TextureRegion[][] r, int weapon) {
        this.weapon = weapon;
        this.fraction = f;
        this.GameStage = ObjectClass.GameStage;
        setSize(a * 2.6f, a * 2.6f);
        setCenterPosition(x, y);
        setOrigin(getWidth() / 2, getHeight() / 2);
		
        tl = r[0];
        s = new Sprite(tl[0]);
        anim = new Animation<>(360 / 16f, tl);

        rings = GameStage.TextureLoader.getRings()[0];//new TextureRegion[5];
        /*TextureRegion[][] t = GameStage.TextureLoader.getRings();
        for (int i = 0; i < 5; i++) {
            rings[i] = t[0][i];
        }*/
        ring = new Sprite(rings[0]);

        if (fraction == ObjectVarable.tank_enemy) {
            ringId = 1;
            h = GameStage.TextureLoader.getHealth_Red()[0];
            if (!(this instanceof Turret))
                ObjectVarable.size_enemy++;
			if (boss) ObjectVarable.all_size_boss_enemy++;
            id = -hashCode();
            ring.setRegion(rings[ringId]);

        }

        if (fraction == ObjectVarable.tank_unity) {
            ringId = 4;
            h = GameStage.TextureLoader.getHealth_Yell()[0];
            if (!(this instanceof Turret || this instanceof TankUser))
                ObjectVarable.size_unity++;
			if (boss) ObjectVarable.all_size_boss_ally++;
            id = hashCode();
            ring.setRegion(rings[ringId]);
        }
        updateDirection();

        AI = new AI();

		health = new Sprite(h[11]);
		
		Health = new Health();
		GameStage.MT.health.addActor(Health);

		Ring = new Ring();
		if (!(this instanceof Turret))
			GameStage.MT.ring.addActor(Ring);

        GameStage.MT.hashTanks.put(id, this);
    }

	public void setBossEnable() {
		if (boss) return;
		
		boss = true;
		if (fraction == ObjectVarable.tank_enemy) {
			ObjectVarable.all_size_boss_enemy++;
        }

        if (fraction == ObjectVarable.tank_unity) {
            ObjectVarable.all_size_boss_ally++;
        }
	}
	
    @Override
    public void draw(Batch batch, float parentAlpha) {
        // TODO: Implement this method
        super.draw(batch, parentAlpha);
        float scale = 2.6f;
        s.setSize(a * scale, a * scale);
        s.setCenter(getCenterX(), getCenterY());
        s.setRegion(anim.getKeyFrame(Math.abs(angleSet), true));
        s.draw(batch);
    }

    private void updateRotation() {
        int rotate = Integer.signum(angle) * 10;
        if (rotate > 0 && angleSet == 360)
            angleSet = 0;
        if (rotate < 0 && angleSet == 0)
            angleSet = 360;

        angle -= rotate;
        angleSet += rotate;
        this.rotate = !(angle == 0);
    }

    protected void updateRun() {
        if (angle != 0) return;

        updateDirection();
        float b = 2f / speed;
        float x = getCenterX();
        float y = getCenterY();
        switch (angleSet) {
            case 0:
            case 360:
                y = Math.round((getCenterY() - speed) * b) / b;
                break;
            case 90:
                x = Math.round((getCenterX() - speed) * b) / b;
                break;
            case 180:
                y = Math.round((getCenterY() + speed) * b) / b;
                break;
            case 270:
                x = Math.round((getCenterX() + speed) * b) / b;
                break;
        }
        setCenterPosition(x, y);
    }

    private void updateDirection() {
        if (angle != 0) return;

        lastDirection = direction;
        switch (angleSet) {
            case 0:
            case 360: direction = DOWN; break;
            case 90: direction = LEFT; break;
            case 180: direction = UP; break;
            case 270: direction = RIGHT; break;
        }
    }

    protected void createBullet() {
        switch (weapon) {
            case 1: {
					if (time > 1.0f / speed_skill) {
						GameStage.MT.bullet.addActor(new BulletLight(getCenterX(), getCenterY(), direction, fraction, this));
						time = 0;
					}
					break;
				}
            case 2: {
					if (time > 0.6 / speed_skill) {
						GameStage.MT.bullet.addActor(new BulletPluzma(getCenterX(), getCenterY(), direction, fraction, this));
						time = 0;
					}
					break;
				}
			case 3: {
					if (time > 1.0f / speed_skill) {
						switch (direction) {
							case 1: 
								GameStage.MT.bullet.addActor(new BulletLight(getCenterX() + 0.2f, getCenterY(), direction, fraction, this));
								GameStage.MT.bullet.addActor(new BulletLight(getCenterX() - 0.2f, getCenterY(), direction, fraction, this));
								break;
							case 2:
								GameStage.MT.bullet.addActor(new BulletLight(getCenterX(), getCenterY() + 0.2f, direction, fraction, this));
								GameStage.MT.bullet.addActor(new BulletLight(getCenterX(), getCenterY() - 0.2f, direction, fraction, this));
								break;
							case 3: 
								GameStage.MT.bullet.addActor(new BulletLight(getCenterX() + 0.2f, getCenterY(), direction, fraction, this));
								GameStage.MT.bullet.addActor(new BulletLight(getCenterX() - 0.2f, getCenterY(), direction, fraction, this));
								break;
							case 4:
								GameStage.MT.bullet.addActor(new BulletLight(getCenterX(), getCenterY() + 0.2f, direction, fraction, this));
								GameStage.MT.bullet.addActor(new BulletLight(getCenterX(), getCenterY() - 0.2f, direction, fraction, this));
								break;	
						}
						time = 0;
					}
					break;
				}
            case 4: {
					if (time > 0.6 / speed_skill) {
						switch (direction) {
							case 1: 
								GameStage.MT.bullet.addActor(new BulletPluzma(getCenterX() + 0.2f, getCenterY(), direction, fraction, this));
								GameStage.MT.bullet.addActor(new BulletPluzma(getCenterX() - 0.2f, getCenterY(), direction, fraction, this));
								break;
							case 2:
								GameStage.MT.bullet.addActor(new BulletPluzma(getCenterX(), getCenterY() + 0.2f, direction, fraction, this));
								GameStage.MT.bullet.addActor(new BulletPluzma(getCenterX(), getCenterY() - 0.2f, direction, fraction, this));
								break;
							case 3: 
								GameStage.MT.bullet.addActor(new BulletPluzma(getCenterX() + 0.2f, getCenterY(), direction, fraction, this));
								GameStage.MT.bullet.addActor(new BulletPluzma(getCenterX() - 0.2f, getCenterY(), direction, fraction, this));
								break;
							case 4:
								GameStage.MT.bullet.addActor(new BulletPluzma(getCenterX(), getCenterY() + 0.2f, direction, fraction, this));
								GameStage.MT.bullet.addActor(new BulletPluzma(getCenterX(), getCenterY() - 0.2f, direction, fraction, this));
								break;	
						}
						time = 0;
					}
					break;
				}
            case 5: {
					if (time > 1 / speed_skill) {
						GameStage.MT.bullet.addActor(new BronetBullet1(getCenterX(), getCenterY(), direction, fraction, this));
						time = 0;
					}
					break;
				}
            case 6: {
					if (time > 1 / speed_skill) {
						GameStage.MT.bullet.addActor(new BronetBullet2(getCenterX(), getCenterY(), direction, fraction, this));
						time = 0;
					}
					break;
				}
            case 7: {
					if (time > 1.2f / speed_skill) {
						GameStage.MT.bullet.addActor(new Roket(getCenterX(), getCenterY(), direction, fraction, this));
						time = 0;
					}
					break;
				}
        }
    }

    public void destroyTank(float damage) {
        float damage1 = 0;
        HPShield -= damage;
        if (HPShield < 0) {
            damage1 = Math.abs(HPShield);
            HPShield = 0;
        }
        HP -= damage1;
        if (HP <= 0) {
            if (automatic == true) {
                Health.remove();
                Ring.remove();
            } else {
                Health.setVisible(false);
                Ring.setVisible(false);
            }
            GameStage.world_tank[AI.goal_x][AI.goal_y] = null;
            GameStage.world_block[AI.goal_x][AI.goal_y] = 0;
            if (AI.last_point != null) {
                GameStage.world_block[(int) AI.last_point.x][(int) AI.last_point.y] = 0;
                GameStage.world_tank[(int) AI.last_point.x][(int) AI.last_point.y] = null;
            }
            //d.setUserData(null);
            if (automatic == true)
                if (fraction == ObjectVarable.tank_enemy) 
					if (!(this instanceof Turret)) {
						if (boss) {
							ObjectVarable.all_size_boss_enemy--;
							MainTerrain.getCurrentTerrain().star++;
							Settings.TankUserSettings.star++;
						}	
                    	ObjectVarable.all_size_enemy--;
                    	ObjectVarable.size_enemy--;

                }
            if (automatic == true)
                if (fraction == ObjectVarable.tank_unity)
					if (!(this instanceof Turret || this instanceof TankUser)) {
						if (boss) ObjectVarable.all_size_boss_ally--;
                    	ObjectVarable.size_unity--;
                    	ObjectVarable.all_size_unity--;
                }
            remove();
            if (!(this instanceof TankUser)) GameStage.MT.hashTanks.remove(id);
            //if (automatic == false)
            //GameStage.TankUser = null;
            if (giveCoin) {
				coinPrice = giveDamage * weapon;
				Settings.TankUserSettings.coin += coinPrice;
				MainTerrain.getCurrentTerrain().coin += coinPrice;
			}
            GameStage.MT.explosions.addActor(new NormalExplosion(Math.round(getCenterX()), Math.round(getCenterY()), GameStage.TextureLoader.getExpl()));
            //ObjectClass.AudioLoader.playTankExplosion().play();
        }
    }

    public void left() {
        if (angle != 0) return;
        switch (angleSet) {
            case 0:
            case 360: angle = 90; break;
            //case 90: angle = 0; break;
            case 180: angle = -90; break;
            case 270: angle = -180; break;
        }
    }

    public void right() {
        if (angle != 0) return;
        switch (angleSet) {
            case 0:
            case 360: angle = -90; break;
            case 90: angle = -180; break;
            case 180: angle = 90; break;
            //case 270: angle = 0; break;
        }
    }

    public void top() {
        if (angle != 0) return;
        switch (angleSet) {
            case 0: angle = 180; break;
            case 360: angle = -180; break;
            case 90: angle = 90; break;
            //case 180: angle = 0; break;
            case 270: angle = -90; break;
        }
    }

    public void bottom() {
        if (angle != 0) return;
        switch (angleSet) {
            /*case 0: angle = 0; break;
            case 360: angle = 0; break;*/
            case 90: angle = -90; break;
            case 180: angle = -180; break;
            case 270: angle = 90; break;
        }
    }

    public void activate_speed_skill() {
        speed_hack = true;
        speed_time = 0;
    }

    private float time_shield = 0;

    @Override
    public void act(float delta) {
        if (HPShield < HPShieldBackup)
            time_shield += delta;
        if (time_shield > 3) {
            HPShield++;
            time_shield = 0;
        }
        if (speed_hack) {
            speed_time += delta;
            if (speed_time > 30) {
                speed_hack = false;
                speed_skill = 1;
            } else {
                speed_skill = 2f;
            }
        }
        time += delta;
        updateRotation();
        System.out.println("angle " + angle + " angle set " + angleSet);
		if (automatic)
			AI.update();
		else
			AI.updateUser();
        super.act(delta);
    }

    public class AI {
        public final int NORMAL = 1;
        public final int ATTACK = 2;
        public int goal_x = (int) getCenterX(), goal_y = (int) getCenterY();
        private final int block = ObjectVarable.size_block * 2;
        private Vector2 last_point = null;
        public int MODE = NORMAL;
        public int radius_enemy = MainTerrain.getCurrentTerrain().getParameters().getKey(129) >= 4 ?
                MainTerrain.getCurrentTerrain().getParameters().getKey(129) * 2 : 10;
        public Tank tankTarget = null;
        public Build buildTarget = null;
        public Actor actorTarget = null;
        public AStarPath path = null;
        public boolean stilling = false;
        public boolean step_place = false;
        public boolean isRiding = false;

        public AI() {
            GameStage.world_block[goal_x][goal_y] = id;
            GameStage.world_tank[goal_x][goal_y] = Tank.this;
        }
		
        public void update() {
            actorTarget = (buildTarget != null) ? buildTarget : tankTarget;
            cleanup();
            isRiding = false;

            if (!rotate)
                if (isOnBlock()) {
                    updateDirection();
                    detectGround();
                    if (!step_place) {
                        startAttackTank();
                        if (!(Tank.this instanceof Turret) && !stilling) attack();
                    }
                    if (step_place) path = null;

                    step_place = false;

                    if (hasRide)
                        switch (MODE) {
                            case ATTACK: ATTACK(); break;
                            case NORMAL: NORMAL(); break;
                        }

                    MODE = NORMAL;
					stilling = true;
                } else {
					stilling = false;
                    if (step_place) createBullet();
                    step_place = false;
                    isRiding = true;
                    if (hasRide)
                        updateRun();
                }
        }

        public void cleanup() {
            if (tankTarget != null && tankTarget.HP <= 0)
                    tankTarget = null;
            if (buildTarget != null && buildTarget.HP <= 0)
                    buildTarget = null;
            if (path == null) currentWay = preWay = nextWay = null;
        }

        public boolean hasUnDestroyableBlock(int x0, int y0, int x, int y, boolean invert) {
			if (!invert) {
            for (int i = x0; i <= x; i += 2)
                for (int u = y0; u <= y; u += 2)
                    if (i >= 0 && i < GameStage.world_physic_block.length && u >= 0 && u < GameStage.world_physic_block[i].length) {
                        Actor actor = GameStage.world_physic_block[i][u];
                        if (actor == null) continue;
                        if (!(actor instanceof DestroyableBlock)) return true;
                    }
			} else {
				for (int i = x; i >= x0; i -= 2)
					for (int u = y; u >= y0; u -= 2)
						if (i >= 0 && i < GameStage.world_physic_block.length && u >= 0 && u < GameStage.world_physic_block[i].length) {
							Actor actor = GameStage.world_physic_block[i][u];
							if (actor == null) continue;
							if (!(actor instanceof DestroyableBlock)) return true;
						}
			}		
            return false;
        }

        public void startAttackBuild() {
            Group builds = GameStage.MT.builds;
            for (int i = 0; i < builds.getChildren().size; i++) {
                ObjBuild objbuild = (ObjBuild) builds.getChildren().get(i);
                Build build = objbuild instanceof Build ? (Build) objbuild : null;

                if (build == null) continue;
                if (build.fraction == fraction) continue;

                float x = targetX = Math.round(build.x);
                float y = targetY = Math.round(build.y);

                if (x == goal_x)
                    if ((distance_of_goal = Math.abs(goal_y - y)) <= radius_enemy)
                        if (y > goal_y) {
                            if (hasUnDestroyableBlock(goal_x, goal_y, goal_x, goal_y + (int) distance_of_goal, false)) continue;

                            top();
                            if (direction == UP) createBullet();
                            MODE = ATTACK;
                            break;
                        } else {
                            if (hasUnDestroyableBlock(goal_x, goal_y - (int) distance_of_goal, goal_x, goal_y, true)) continue;

                            bottom();
                            if (direction == DOWN) createBullet();
                            MODE = ATTACK;
                            break;
                        }

                if (y == goal_y)
                    if ((distance_of_goal = Math.abs(goal_x - x)) <= radius_enemy)
                        if (x > goal_x) {
                            if (hasUnDestroyableBlock(goal_x, goal_y, goal_x + (int) distance_of_goal, goal_y, false)) continue;

                            right();
                            if (direction == RIGHT) createBullet();
                            MODE = ATTACK;
                            break;
                        } else {
                            if (hasUnDestroyableBlock(goal_x - (int) distance_of_goal, goal_y, goal_x, goal_y, true)) continue;

                            left();
                            if (direction == LEFT) createBullet();
                            MODE = ATTACK;
                            break;
                        }
                MODE = NORMAL;
            }
        }

        public float distance_of_goal;
        public float targetX, targetY;

        public void startAttackTank() {
            Group tanks = (fraction == ObjectVarable.tank_unity) ?
                    GameStage.MT.tanks_enemy : GameStage.MT.tanks_unity;
            for (int i = 0; i < tanks.getChildren().size; i++) {
                Tank tank = (Tank) tanks.getChildren().get(i);
                float x = targetX = tank.AI.goal_x;
                float y = targetY = tank.AI.goal_y;

                if (x == goal_x)
                    if ((distance_of_goal = Math.abs(goal_y - y)) <= radius_enemy)
                        if (y > goal_y) {
                            if (hasUnDestroyableBlock(goal_x, goal_y, goal_x, goal_y + (int) distance_of_goal, false)) continue;

                            top();
                            if (direction == UP) createBullet();
                            MODE = ATTACK;
                            return;
                        } else {
                            if (hasUnDestroyableBlock(goal_x, goal_y - (int) distance_of_goal, goal_x, goal_y, true)) continue;

                            bottom();
                            if (direction == DOWN) createBullet();
                            MODE = ATTACK;
                            return;
                        }

                if (y == goal_y)
                    if ((distance_of_goal = Math.abs(goal_x - x)) <= radius_enemy)
                        if (x > goal_x) {
                            if (hasUnDestroyableBlock(goal_x, goal_y, goal_x + (int) distance_of_goal, goal_y, false)) continue;

                            right();
                            if (direction == RIGHT) createBullet();
                            MODE = ATTACK;
                            return;
                        } else {
                            if (hasUnDestroyableBlock(goal_x - (int) distance_of_goal, goal_y, goal_x, goal_y, true)) continue;

                            left();
                            if (direction == LEFT) createBullet();
                            MODE = ATTACK;
                            return;
                        }
                MODE = NORMAL;
            }
			int code = MainTerrain.Mission.CODE;
            if (fraction == ObjectVarable.tank_enemy && code >= 50 && code <= 55) startAttackBuild();
			if (fraction == ObjectVarable.tank_unity && code >= 56 && code <= 60) startAttackBuild();
            //GameStage.world_nodes[goal_x][goal_y] = MODE == NORMAL ? 0 : id;
        }

        public void attack() {
            if (MODE == ATTACK) return;

            if (path != null) return;

            Tank buffer = null;
            float last_dis = Float.MAX_VALUE;

            Group tanks = (fraction == ObjectVarable.tank_unity) ? GameStage.MT.tanks_enemy : GameStage.MT.tanks_unity;
            for (int i = 0; i < tanks.getChildren().size; i++) {
                Tank tank = (Tank) tanks.getChildren().get(i);

                Rectangle rect = new Rectangle(goal_x - radius_enemy, goal_y - radius_enemy, radius_enemy * 2, radius_enemy * 2);

                if (rect.contains(tank.AI.goal_x, tank.AI.goal_y)) {
                    float dst = Vector2.dst2(tank.AI.goal_x, tank.AI.goal_y, goal_x, goal_y);
                    if (dst < last_dis) {
                        last_dis = dst;
                        buffer = tank;
                    }
                }
            }

            if (!(Tank.this instanceof Turret))
                if (buffer != null) {
                    path = GameStage.MT.AStar.search(buffer.AI.goal_x, buffer.AI.goal_y, goal_x, goal_y);
					//if (path != null) path.next();
					//lastBuf = buffer;
				}	
        }

        public void ATTACK() {
            path = null;
            buildTarget = null;
            tankTarget = null;
        }

        private AStarNode preWay;
		
		private AStarNode currentWay;
		
		private AStarNode nextWay;

        private boolean findBarrier(int angle) {
            if (!hasNextBlock(angle)) {
                Tank tank = null;
                Block block = null;

                Vector2 pos = nextBlockPosition(angle);
                if (pos == null) return false;

                int x = (int) pos.x;
                int y = (int) pos.y;

                tank = GameStage.MT.hashTanks.get(GameStage.world_block[x][y]);
                if (!(tank instanceof TankUser) && tank != null) {
					path = null;
					if (tank.AI.MODE == ATTACK) tank.AI.step_place = true;
				}   

                block = GameStage.world_physic_block[x][y];
                if (isDestroyableBlockForBullet(block)) createBullet();

                return true;
            }
            return false;
        }

        private void decodeWay() {
            if (path.hasNext()) {
                if (preWay == null && currentWay == null) {
					currentWay = path.next();
					if (path.hasNext()) nextWay = path.next();
				}

				AStarNode nodeWay = currentWay;
				
				if (preWay != null && nextWay != null) {
					//if (preWay.code != nextWay.code) nodeWay.code = nextWay.code;
				}
				
                switch (-nodeWay.code) {
                    case AStarNode.DOWN: {
                        DOWN();

                        if (findBarrier(DOWN)) break;

                        if (direction == DOWN) {
							preWay = nodeWay;
							currentWay = nextWay;
							nextWay = path.next();
						}
                        break;
                    }

                    case AStarNode.UP: {
                        UP();

                        if (findBarrier(UP)) break;

							if (direction == UP) {
								preWay = nodeWay;
								currentWay = nextWay;
								nextWay = path.next();
							}
                        break;
                    }

                    case AStarNode.LEFT: {
                        LEFT();

                        if (findBarrier(LEFT)) break;

							if (direction == LEFT) {
								preWay = nodeWay;
								currentWay = nextWay;
								nextWay = path.next();
							}
                        break;
                    }

                    case AStarNode.RIGHT: {
                        RIGHT();

                        if (findBarrier(RIGHT)) break;

							if (direction == RIGHT) {
								preWay = nodeWay;
								currentWay = nextWay;
								nextWay = path.next();
							}
                        break;
                    }

                    default:
                        path.next();
                }
            } else {
                currentWay = preWay = nextWay = null;
                path = null;
            }
        }

        public void NORMAL() {
            step_place = false;

            if (path != null)
                decodeWay();
            else
                step();
        }

        public void stepWithoutTurn() {
            if (!rotate)
                if (hasNextBlock()) {
                    nextBlock();
                    createTrack();
                    updateRun();
                }
        }

        public void step() {
            if (!rotate)
                if (hasNextBlock()) {
                    nextBlock();
                    createTrack();
                    updateRun();
                } else {
                    turn();
                }
        }

        public void updateUser() {
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

        public boolean isDestroyableBlockForBullet(Block block) {
            if (block instanceof DestroyableBlock && !(block instanceof Spike)) return true;
            return weapon == BulletList.ROKET && block instanceof ConcreteWall;
        }

        public void ride(int direction) {
            if (isOnBlock()) {
                top();
                if (Tank.this.direction == direction)
                    if (hasNextBlock(direction) && !rotate) {
                        createTrack();
                        nextBlock(direction);
                        updateRun();
                    }
            }
        }

        public void UP() {
            if (isOnBlock()) {
                top();
                if (direction == UP)
                    if (hasNextBlock(UP) && !rotate) {
                        createTrack();
                        nextBlock(UP);
                        updateRun();
                    }
            }
        }

        public void DOWN() {
            if (isOnBlock()) {
                bottom();
                if (direction == DOWN)
                    if (hasNextBlock(DOWN) && !rotate) {
                        createTrack();
                        nextBlock(DOWN);
                        updateRun();
                    }
            }
        }

        public void LEFT() {
            if (isOnBlock()) {
                left();
                if (direction == LEFT)
                    if (hasNextBlock(LEFT) && !rotate) {
                        createTrack();
                        nextBlock(LEFT);
                        updateRun();
                    }
            }
        }

        public void RIGHT() {
            if (isOnBlock()) {
                right();
                if (direction == RIGHT)
                    if (hasNextBlock(RIGHT) && !rotate) {
                        createTrack();
                        nextBlock(RIGHT);
                        updateRun();
                    }
            }
        }

        public void AIR() {
            int air = Settings.TankUserSettings.plus_bullet_type2;
            if (!automatic)
				switch (air) {
					case 1:
						if (WeaponData.art > 0)
							if (time > 1) {
								int damage = 9 + WeaponData.Upgrade.art * 3;
								int len = 7 * 2 + (WeaponData.Upgrade.art >= 3 ? 1 : 0) * 2;
								Artiling art = new Artiling(goal_x, goal_y, len, direction);
								art.damage = damage;
								art.diameter = WeaponData.Upgrade.art == 5 ? 8 * 2 : 6 * 2;
								GameStage.MT.bullet.addActor(art);

								//GameStage.MT.mines.addActor(new MineUnity1(goal_x,goal_y));
								WeaponData.art -= 1;
								time = 0;
							}
							break;
					case 2: 
                        if (WeaponData.air > 0) {
							GameStage.createAircraft();
					}		
				}
        }

        public void MINES() {
            int mine = Settings.TankUserSettings.plus_bullet_type1;
            if (GameStage.world_mines[goal_x][goal_y] == null)
				if (!automatic)
					switch (mine) {
                        case 1: 
							if (WeaponData.mine1 > 0) {
								GameStage.MT.mines.addActor(new MineUnity1(goal_x, goal_y));
								WeaponData.mine1 -= 1;
							}
							break;
                        case 2: if (WeaponData.mine2 > 0) {
								GameStage.MT.mines.addActor(new MineUnity2(goal_x, goal_y));
								WeaponData.mine2 -= 1;
							}
							break;
                        case 3: 
							if (WeaponData.tnt1 > 0) {
								GameStage.MT.mines.addActor(new TNT1(goal_x, goal_y));
								WeaponData.tnt1 -= 1;
							}
							break;
                        case 4:
							if (WeaponData.tnt2 > 0) {
								GameStage.MT.mines.addActor(new TNT2(goal_x, goal_y));
								WeaponData.tnt2 -= 1;
							}
							break;
                        case 5: 
							if (WeaponData.tnt3 > 0) {
								GameStage.MT.mines.addActor(new TNT3(goal_x, goal_y));
								WeaponData.tnt3 -= 1;
							}
                    }
			SoundLoader.getInstance().getMineDeploy().play(Settings.volumeEffect);
        }

        public void BULLET() {
            if (!rotate)
                createBullet();
        }

        private void createTrack() {
            switch (direction) {
                case DOWN: {
                    if (lastDirection == LEFT) {
                        GameStage.MT.track.addActor(new Track(getCenterX(), getCenterY(), Track.RIGHT_DOWN));
                        break;
                    }
                    if (lastDirection == RIGHT) {
                        GameStage.MT.track.addActor(new Track(getCenterX(), getCenterY(), Track.LEFT_DOWN));
                        break;
                    }
                    GameStage.MT.track.addActor(new Track(getCenterX(), getCenterY(), Track.VERTICAL));
                    break;
                }
                case RIGHT: {
                    if (lastDirection == UP) {
                        GameStage.MT.track.addActor(new Track(getCenterX(), getCenterY(), Track.RIGHT_DOWN));
                        break;
                    }
                    if (lastDirection == DOWN) {
                        GameStage.MT.track.addActor(new Track(getCenterX(), getCenterY(), Track.RIGHT_UP));
                        break;
                    }
                    GameStage.MT.track.addActor(new Track(getCenterX(), getCenterY(), Track.HORIZONTAL));
                    break;
                }
                case UP: {
                    if (lastDirection == RIGHT) {
                        GameStage.MT.track.addActor(new Track(getCenterX(), getCenterY(), Track.LEFT_UP));
                        break;
                    }
                    if (lastDirection == LEFT) {
                        GameStage.MT.track.addActor(new Track(getCenterX(), getCenterY(), Track.RIGHT_UP));
                        break;
                    }
                    GameStage.MT.track.addActor(new Track(getCenterX(), getCenterY(), Track.VERTICAL));
                    break;
                }
                case LEFT: {
                    if (lastDirection == UP) {
                        GameStage.MT.track.addActor(new Track(getCenterX(), getCenterY(), Track.LEFT_DOWN));
                        break;
                    }
                    if (lastDirection == DOWN) {
                        GameStage.MT.track.addActor(new Track(getCenterX(), getCenterY(), Track.LEFT_UP));
                        break;
                    }
                    GameStage.MT.track.addActor(new Track(getCenterX(), getCenterY(), Track.HORIZONTAL));
                    break;
                }
            }
        }

        private void detectGround() {
            if (!hasRide)
                return;

            if (MODE == ATTACK)
                return;

            if (speedr == 0)
                speedr = speed;
            if (GameStage.world_obj[goal_x][goal_y] instanceof Sand)
                speed = _new_speed(2f);
            else if (GameStage.world_obj[goal_x][goal_y] instanceof Grass)
                speed = speedr;
            else if (GameStage.world_obj[goal_x][goal_y] instanceof Road)
                speed = _new_speed(0.8f);
            else if (GameStage.world_obj[goal_x][goal_y] instanceof Plate)
                speed = _new_speed(0.8f);
					
        }

        private float _new_speed(float scl) {
            return 2f / ((2f / speedr) * scl);
        }

        private boolean hasNextBlock(int o) {
            int x = (int) getCenterX(), y = (int) getCenterY();
            try {
                switch (o) {
                    case 1: {
                        int next_x = x;
                        int next_y = y - block;
                        if (GameStage.world_block[next_x][next_y] == (0)) {
                            return true;
                        } else if (GameStage.world_block[next_x][next_y] == (id)) {
                            return true;
                        }
                        break;
                    }
                    case 2: {
                        int next_x = x + block;
                        int next_y = y;
                        if (GameStage.world_block[next_x][next_y] == (0)) {
                            return true;
                        } else if (GameStage.world_block[next_x][next_y] == (id)) {
                            return true;
                        }
                        break;
                    }
                    case 3: {
                        int next_x = x;
                        int next_y = y + block;
                        if (GameStage.world_block[next_x][next_y] == (0)) {
                            return true;
                        } else if (GameStage.world_block[next_x][next_y] == (id)) {
                            return true;
                        }
                        break;
                    }
                    case 4: {
                        int next_x = x - block;
                        int next_y = y;
                        if (GameStage.world_block[next_x][next_y] == (0)) {
                            return true;
                        } else if (GameStage.world_block[next_x][next_y] == (id)) {
                            return true;
                        }
                        break;
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                return false;
            }
            return false;
        }

        private Vector2 nextBlockPosition(int angle) {
            int x = (int) getCenterX(), y = (int) getCenterY();
            try {
                switch (angle) {
                    case 1: {
                        int next_x = x;
                        int next_y = y - block;
                        if (GameStage.world_block[next_x][next_y] != 0) {
                        }
                        return new Vector2(next_x, next_y);
                    }
                    case 2: {
                        int next_x = x + block;
                        int next_y = y;
                        if (GameStage.world_block[next_x][next_y] != 0) {
                        }
                        return new Vector2(next_x, next_y);
                    }
                    case 3: {
                        int next_x = x;
                        int next_y = y + block;
                        if (GameStage.world_block[next_x][next_y] != 0) {
                        }
                        return new Vector2(next_x, next_y);
                    }
                    case 4: {
                        int next_x = x - block;
                        int next_y = y;
                        if (GameStage.world_block[next_x][next_y] != 0) {
                        }
                        return new Vector2(next_x, next_y);
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                return null;
            }
            return null;
        }

        private Vector2 nextBlockPosition() {
            return nextBlockPosition(direction);
        }

        private boolean hasNextBlock() {
			return hasNextBlock(direction);
		}

        private void nextBlock(int t) {
            int x = (int) getCenterX(), y = (int) getCenterY();
            switch (t) {
                case 1: {
                    last_point = new Vector2(goal_x, goal_y);
                    //GameStage.MT.track.addActor(new Track(goal_x, goal_y, 2));
                    goal_x = x;
                    goal_y = y - block;
                    GameStage.world_block[goal_x][goal_y] = id;
                    break;
                }
                case 2: {
                    last_point = new Vector2(goal_x, goal_y);
                    //GameStage.MT.track.addActor(new Track(goal_x, goal_y, 1));
                    goal_x = x + block;
                    goal_y = y;
                    GameStage.world_block[goal_x][goal_y] = id;
                    break;
                }
                case 3: {
                    last_point = new Vector2(goal_x, goal_y);
                    //GameStage.MT.track.addActor(new Track(goal_x, goal_y, 2));
                    goal_x = x;
                    goal_y = y + block;
                    GameStage.world_block[goal_x][goal_y] = id;
                    break;
                }
                case 4: {
                    last_point = new Vector2(goal_x, goal_y);
                    //GameStage.MT.track.addActor(new Track(goal_x, goal_y, 1));
                    goal_x = x - block;
                    goal_y = y;
                    GameStage.world_block[goal_x][goal_y] = id;
                    break;
                }
            }
        }

        private void nextBlock() {
			nextBlock(direction);
		}

        private void turn() {
            goal_x = (int) getCenterX();
            goal_y = (int) getCenterY();
            switch (direction) {
                case 1:
                    if (hasNextBlock(2))
                        RIGHT();
                    else if (hasNextBlock(4))
                        LEFT();
                    else if (hasNextBlock(3))
                        UP();
                    break;
                case 2:
                    if (hasNextBlock(1))
                        DOWN();
                    else if (hasNextBlock(3))
                        UP();
                    else if (hasNextBlock(4))
                        LEFT();
                    break;
                case 3:
                    if (hasNextBlock(4))
                        LEFT();
                    else if (hasNextBlock(2))
                        RIGHT();
                    else if (hasNextBlock(1))
                        DOWN();
                    break;
                case 4:
                    if (hasNextBlock(3))
                        UP();
                    else if (hasNextBlock(1))
                        DOWN();
                    else if (hasNextBlock(2))
                        RIGHT();
                    break;
            }
        }


        public boolean isOnBlockWithoutTransform() {
            return isOnBlock(false);
        }

        public boolean isOnBlock() {
            return isOnBlock(true);
        }

        public boolean isOnBlock(boolean isTransform) {
            boolean b = false;
            float x = getCenterX();
            float y = getCenterY();
            switch (direction) {
                case 1:
                    if (b = y <= goal_y) y = goal_y; break;
                case 2:
                    if (b = x >= goal_x) x = goal_x; break;
                case 3:
                    if (b = y >= goal_y) y = goal_y; break;
                case 4:
                    if (b = x <= goal_x) x = goal_x; break;
            }
            if (b) {
                if (isTransform) setCenterPosition(x, y);
                GameStage.world_block[goal_x][goal_y] = id;
                GameStage.world_tank[goal_x][goal_y] = Tank.this;
                if (last_point != null) {
                    GameStage.world_tank[(int) last_point.x][(int) last_point.y] = null;
                    GameStage.world_block[(int) last_point.x][(int) last_point.y] = 0;
                }
                last_point = null;
            }
            return b;
        }
    }

    public class Health extends Actor {
        public Health() {
            setSize(a * 2, 0.3f * ObjectVarable.size_block);
			setPosition(Tank.this.getCenterX(), Tank.this.getCenterY() + a + health.getHeight());
        }

        @Override
        public void setSize(float width, float height) {
            super.setSize(width, height);
            health.setSize(width, height);
        }

        @Override
        public void setPosition(float x, float y) {
            health.setCenter(x, y);
            super.setPosition(health.getX(), health.getY());
        }

        @Override
        public void draw(Batch batch, float parentAlpha) {
			super.draw(batch, parentAlpha);
            setPosition(Tank.this.getCenterX(), Tank.this.getCenterY() + a + health.getHeight());
			health.setRegion(h[Math.min(11, Math.max((int) ((12 * HP) / HPBackup) - 1, 0))]);
            health.draw(batch);
		}
    }

    public class Ring extends Actor {
        public Ring() {
            setSize(ObjectVarable.size_block * 3, ObjectVarable.size_block * 3);
            setPosition(Tank.this.getCenterX(), Tank.this.getCenterY(), Align.center);
        }

        @Override
        public void setSize(float width, float height) {
            ring.setSize(width, height);
            super.setSize(width, height);
        }

        @Override
        public void setPosition(float x, float y, int alignment) {
            if (alignment == Align.center)
                ring.setCenter(Tank.this.getCenterX(), Tank.this.getCenterY());
            super.setPosition(x, y, alignment);
        }

        @Override
        public void draw(Batch batch, float parentAlpha) {
            super.draw(batch, parentAlpha);
            setPosition(Tank.this.getCenterX(), Tank.this.getCenterY(), Align.center);
            ring.setRegion(rings[HPShield > 0 ? 2 : ringId]);
            ring.draw(batch);
        }

        public void setRing(int index) {

        }
    }
}
