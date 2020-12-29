package com.tanchiki.libgdx.model.tanks.Object;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.tanchiki.libgdx.graphics.*;
import com.tanchiki.libgdx.model.buildes.*;
import com.tanchiki.libgdx.model.buildes.Object.*;
import com.tanchiki.libgdx.model.bullets.*;
import com.tanchiki.libgdx.model.explosions.*;
import com.tanchiki.libgdx.model.mine.*;
import com.tanchiki.libgdx.model.tanks.*;
import com.tanchiki.libgdx.model.terrains.*;
import com.tanchiki.libgdx.model.terrains.Object.*;
import com.tanchiki.libgdx.model.tnt.*;
import com.tanchiki.libgdx.stage.*;
import com.tanchiki.libgdx.util.*;
import com.tanchiki.libgdx.util.astar.*;
import java.util.*;
import java.util.concurrent.*;
import com.badlogic.gdx.utils.*;

public class Tank extends GameActor {
    protected GameStage GameStage;
    private float a = ObjectVarable.size_block;
    protected TextureRegion[] tl;
    public Animation<TextureRegion> anim;
    public short fraction;
    public Sprite s;
    private boolean Dowm, Up, Left, Right;
    private float angle = 0;
    private float angleSet = 0;
    public float HP = 3;
    public float HPbackup;
    public float HPShield = 0;
    public float HPShieldBackup = 0;
    public float speed = 1;
    protected float time;
    public boolean rotate = true;
    private Sprite helth;

    public Sprite ring;
    public TextureRegion rings[];

    private TextureRegion h[] = new TextureRegion[12];

    public boolean del_fixtue = false;

    int rotationn = 1;

    public boolean run = false;
    public int angle_for_bullet = 1, last_angle_for_bullet = 1;

    public int weapon = 1;

    public AI AI;

    private Vector2 vecbuffer = new Vector2();

    private int id = 0;

    private String str;

    private float speedr = 0;

    public float speed_skill = 1;

    public float speed_time = 0;

    public boolean speed_hack = false;

    public static final int DOWN = 1,
            LEFT = 4,
            RIGHT = 2,
            UP = 3;

    public boolean automatic = true;

    public Health Health;

    public Ring Ring;

    public long tackMillis;

    //MainTerrain.Node[][] way = null;

    Tank enemy_tank = null;

    public static boolean stop_enemy = false;

    public static boolean stop_unity = false;

    public int ringid = 0;

    public boolean hasRide = true;

    public boolean give_coin = false;
	
	public int giveDamage = 0;

    public float coin_price = 0;

	public boolean boss = false;
	
	public List<Future> futures;
	//public static ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
	
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
        
        setRotation(0);
        angleSet = getRotation();

        rings = new TextureRegion[5];
        TextureRegion[][] t = GameStage.TextureLoader.getRings();
        for (int i = 0; i < 5; i++) {
            rings[i] = t[0][i];
        }
        ring = new Sprite(rings[0]);

        if (fraction == ObjectVarable.tank_enemy) {
            ringid = 1;
            h = GameStage.TextureLoader.getHealth_Red()[0];
            if (!(this instanceof Turret))
                ObjectVarable.size_enemy++;
			if (boss) ObjectVarable.all_size_boss_enemy++;
            id = -hashCode();
            ring.setRegion(rings[ringid]);

        }

        if (fraction == ObjectVarable.tank_unity) {
            ringid = 4;
            h = GameStage.TextureLoader.getHealth_Yell()[0];
            if (!(this instanceof Turret || this instanceof TankUser))
                ObjectVarable.size_unity++;
			if (boss) ObjectVarable.all_size_boss_ally++;
            id = hashCode();
            ring.setRegion(rings[ringid]);
        }

        updateAngle();

        updateBullet();

        AI = new AI();

		helth = new Sprite(h[11]);
		
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
        s.setRegion(anim.getKeyFrame(Math.abs(getRotation()), true));
        s.draw(batch);
    }

	/*@Override
	public void drawDebug(ShapeRenderer shapes)
	{
		// TODO: Implement this method
		//super.drawDebug(shapes);
		shapes.setColor(Color.RED);
		shapes.circle(AI.goal_x, AI.goal_y, 1);
		if (AI.last_point != null)
		{
			shapes.setColor(Color.GREEN);
			shapes.circle(AI.last_point.x, AI.last_point.y, 1);
		}
	}*/

    private void rotate() {
        //AI.last_point = null;
        float rotate = 0;
        if (angle > 0) {
            if (angleSet == 360)
                angleSet = 0;
            rotate = 10;
        }
        if (angle < 0) {
            if (angleSet == 0)
                angleSet = 360;
            rotate = -10;
        }
        angle -= rotate;
        angleSet += rotate;
        if (angle == 0)
            this.rotate = false;
        else
            this.rotate = true;
    }

    protected void updateRun() {
        float b = 2f / speed;
        
        if (getRotation() == 0 && angle == 0) {
            angle_for_bullet = 1;
            setCenterPosition(getCenterX(), Math.round((getCenterY() - speed) * b) / b);
        }
        if (getRotation() == 90 && angle == 0) {
            angle_for_bullet = 4;
            setCenterPosition(Math.round((getCenterX() - speed) * b) / b, getCenterY());
        }
        if (getRotation() == 180 && angle == 0) {
            angle_for_bullet = 3;
            setCenterPosition(getCenterX(), Math.round((getCenterY() + speed) * b) / b);
        }
        if (getRotation() == 270 && angle == 0) {
            angle_for_bullet = 2;
            setCenterPosition(Math.round((getCenterX() + speed) * b) / b, getCenterY());
        }
        if (getRotation() == 360 && angle == 0) {
            angle_for_bullet = 1;
            setCenterPosition(getCenterX(), Math.round((getCenterY() - speed) * b) / b);
        }
        //setCenterPosition(Math.round(getCenterX() * 10) / 10f, Math.round(getCenterY() * 10) / 10f);
    }

    private void updateBullet() {
        if (getRotation() == 0 && angle == 0) {
            last_angle_for_bullet = angle_for_bullet;
            angle_for_bullet = 1;

        }
        if (getRotation() == 90 && angle == 0) {
            last_angle_for_bullet = angle_for_bullet;
            angle_for_bullet = 4;

        }
        if (getRotation() == 180 && angle == 0) {
            last_angle_for_bullet = angle_for_bullet;
            angle_for_bullet = 3;

        }
        if (getRotation() == 270 && angle == 0) {
            last_angle_for_bullet = angle_for_bullet;
            angle_for_bullet = 2;

        }
        if (getRotation() == 360 && angle == 0) {
            last_angle_for_bullet = angle_for_bullet;
            angle_for_bullet = 1;

        }
    }

    private void updateAngle() {
        if (getRotation() == 0 && angle == 0) {
            Dowm = true;
            Left = false;
            Right = false;
            Up = false;
        }
        if (getRotation() == 90 && angle == 0) {
            Dowm = false;
            Left = false;
            Right = true;
            Up = false;
        }
        if (getRotation() == 180 && angle == 0) {
            Dowm = false;
            Left = false;
            Right = false;
            Up = true;
        }
        if (getRotation() == 270 && angle == 0) {
            Dowm = false;
            Left = false;
            Right = true;
            Up = false;
        }
        if (getRotation() == 360 && angle == 0) {
            Dowm = true;
            Left = false;
            Right = false;
            Up = false;
        }
    }

    protected void creatBullet() {
        switch (weapon) {
            case 1: {
					if (time > 1.0f / speed_skill) {
						GameStage.MT.bullet.addActor(new BulletLight(getCenterX(), getCenterY(), angle_for_bullet, fraction, this));
						time = 0;
					}
					break;
				}
            case 2: {
					if (time > 0.6 / speed_skill) {
						GameStage.MT.bullet.addActor(new BulletPluzma(getCenterX(), getCenterY(), angle_for_bullet, fraction, this));
						time = 0;
					}
					break;
				}
			case 3: {
					if (time > 1.0f / speed_skill) {
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
						time = 0;
					}
					break;
				}
            case 4: {
					if (time > 0.6 / speed_skill) {
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
						time = 0;
					}
					break;
				}
            case 5: {
					if (time > 1 / speed_skill) {
						GameStage.MT.bullet.addActor(new BronetBullet1(getCenterX(), getCenterY(), angle_for_bullet, fraction, this));
						time = 0;
					}
					break;
				}
            case 6: {
					if (time > 1 / speed_skill) {
						GameStage.MT.bullet.addActor(new BronetBullet2(getCenterX(), getCenterY(), angle_for_bullet, fraction, this));
						time = 0;
					}
					break;
				}
            case 7: {
					if (time > 1.2f / speed_skill) {
						GameStage.MT.bullet.addActor(new Roket(getCenterX(), getCenterY(), angle_for_bullet, fraction, this));
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
            if (give_coin) {
				coin_price = giveDamage * weapon;
				Settings.TankUserSettings.coin += coin_price;
				MainTerrain.getCurrentTerrain().coin += coin_price;
			}
            GameStage.MT.explosions.addActor(new NormalExplosion(Math.round(getCenterX()), Math.round(getCenterY()), GameStage.TextureLoader.getExpl()));
            //ObjectClass.AudioLoader.playTankExplosion().play();
        }
    }

    public void left() {
        float now_angle = getRotation();
        float angle_set = 0;
        if (now_angle == 0)
            angle_set = 90;
        if (now_angle == 90)
            angle_set = 0;
        if (now_angle == 180)
            angle_set = -90;
        if (now_angle == 270)
            angle_set = -180;
        if (now_angle == 360)
            angle_set = 90;
        if (angle == 0)
            angle = angle_set;

    }

    public void right() {
        float now_angle = getRotation();
        float angle_set = 0;
        if (now_angle == 0)
            angle_set = -90;
        if (now_angle == 90)
            angle_set = -180;
        if (now_angle == 180)
            angle_set = 90;
        if (now_angle == 270)
            angle_set = 0;
        if (now_angle == 360)
            angle_set = -90;
        if (angle == 0)
            angle = angle_set;

    }

    public void top() {
        float now_angle = getRotation();
        float angle_set = 0;
        if (now_angle == 0)
            angle_set = 180;
        if (now_angle == 90)
            angle_set = 90;
        if (now_angle == 180)
            angle_set = 0;
        if (now_angle == 270)
            angle_set = -90;
        if (now_angle == 360)
            angle_set = -180;
        if (angle == 0)
            angle = angle_set;
    }

    public void bottom() {
        float now_angle = getRotation();
        float angle_set = 0;
        if (now_angle == 0)
            angle_set = 0;
        if (now_angle == 90)
            angle_set = -90;
        if (now_angle == 180)
            angle_set = -180;
        if (now_angle == 270)
            angle_set = 90;
        if (now_angle == 360)
            angle_set = 0;
        if (angle == 0)
            angle = angle_set;

    }

    public void activate_speed_skill() {
        speed_hack = true;
        speed_time = 0;
    }

    private float time_shield = 0;

    private float delta;

    @Override
    public void act(float delta) {
        this.delta = delta;
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
        updateAngle();

        time += delta;
        rotate();
        setRotation(angleSet);
		if (automatic)
			AI.update();
		else
			AI.updateUser();
        super.act(delta);
    }

    public class AI {

        public final int CODE_WAY_UP = 2;

        public final int CODE_WAY_DOWN = -2;

        public final int CODE_WAY_LEFT = -1;

        public final int CODE_WAY_RIGHT = 1;

        public final int NORMAL = 1;

        public final int SEEKWAY = 0;

        public final int ATTACK = 2;

        public final int PROTECTED = 3;

        public int goal_x = (int) getCenterX(), goal_y = (int) getCenterY();

        private int block = ObjectVarable.size_block * 2;

        private Vector2 last_point = null;

        public int MODE = NORMAL;

        public boolean CONTER_ATTACK = false;

        public int radius_enemy = MainTerrain.getCurrentTerrain().getParameters().getKey(129) >= 4 ? MainTerrain.getCurrentTerrain().getParameters().getKey(129) * 2 : 10;

        private boolean new_track = true;

        private int last_x = -1, last_y = -1;

        //private boolean boolWay;

        public Tank tankTarget = null;

        public Build buildTarget = null;

        public Actor actorTarget = null;

        public Vector2 posBegin = null;

        public AStarPath path = null;
		
        public AI() {
            GameStage.world_block[goal_x][goal_y] = id;
            GameStage.world_tank[goal_x][goal_y] = Tank.this;
            //way = GameStage.MT.seekWay(goal_x, goal_y, 0, 0);
        }
        //boolean b = true;

        int step = 0;

		public boolean stilling = false;
		
        public boolean step_place = false;

        public boolean isRiding = false;
		
        int counter = 0, max_counter = (Tank.this instanceof Turret) ? 5 : 1;
		
		public Runnable updater = new Runnable() {
			@Override
			public void run() {
				update();
			}
		};
		
        public synchronized void update() {
            actorTarget = (buildTarget != null) ? buildTarget : tankTarget;
            //System.out.println(this);
            cleanup();
            //System.out.println("cleanup");
            isRiding = false;

            if (rotate == false)
                if (isOnBlock()) {
                    updateBullet();
                    //System.out.println("updateBullet");
                    detectGround();
                    //System.out.println("detectGround");
					
                    if (counter == max_counter) {
						if (!step_place) {
							start_attack_tank();
							//System.out.println("attack tank");
							if (!(Tank.this instanceof Turret) && stilling == false) attack();
							//System.out.println("attack");
						}
						counter = 0;
					}
					counter++;

                    if (step_place) path = null;

                    step_place = false;

                    if (hasRide)
                        switch (MODE) {
                            case ATTACK: {
                                ATTACK();
                                break;
                            }

                            case NORMAL: {
                                NORMAL();
                                break;
                            }
                        }

                    MODE = NORMAL;
					stilling = true;
                } else {
					stilling = false;
                    if (step_place) creatBullet();
                    step_place = false;
                    isRiding = true;
                    if (hasRide)
                        updateRun();
                    //System.out.println("update run");
                    //attackEnemy();
                }

            //destroyTank();
        }

        public void cleanup() {
            //clean up target tank, build
            if (tankTarget != null)
                if (tankTarget.HP <= 0) {
                    tankTarget = null;
                }
            if (buildTarget != null)
                if (buildTarget.HP <= 0) {
                    buildTarget = null;
                }

            if (path == null) currentWay = preWay = nextWay = null;
        }

        public boolean hasUnDestroyableBlock(int x0, int y0, int x, int y, boolean invert) {
			if (!invert) {
            for (int i = x0; i <= x; i += 2)
                for (int u = y0; u <= y; u += 2)
                    if (i >= 0 && i < GameStage.world_physic_block.length && u >= 0 && u < GameStage.world_physic_block[i].length) {
                        Actor actor = GameStage.world_physic_block[i][u];
                        if (actor == null) continue;
                        if (actor instanceof DestroyableBlock == false) return true;
                    }
			} else {
				for (int i = x; i >= x0; i -= 2)
					for (int u = y; u >= y0; u -= 2)
						if (i >= 0 && i < GameStage.world_physic_block.length && u >= 0 && u < GameStage.world_physic_block[i].length) {
							Actor actor = GameStage.world_physic_block[i][u];
							if (actor == null) continue;
							if (actor instanceof DestroyableBlock == false) return true;
						}
			}		
            return false;
        }

        public void start_attack_build() {
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
                            if (angle_for_bullet == UP) creatBullet();
                            MODE = ATTACK;
                            break;
                        } else {
                            if (hasUnDestroyableBlock(goal_x, goal_y - (int) distance_of_goal, goal_x, goal_y, true)) continue;

                            bottom();
                            if (angle_for_bullet == DOWN) creatBullet();
                            MODE = ATTACK;
                            break;
                        }

                if (y == goal_y)
                    if ((distance_of_goal = Math.abs(goal_x - x)) <= radius_enemy)
                        if (x > goal_x) {
                            if (hasUnDestroyableBlock(goal_x, goal_y, goal_x + (int) distance_of_goal, goal_y, false)) continue;

                            right();
                            if (angle_for_bullet == RIGHT) creatBullet();
                            MODE = ATTACK;
                            break;
                        } else {
                            if (hasUnDestroyableBlock(goal_x - (int) distance_of_goal, goal_y, goal_x, goal_y, true)) continue;

                            left();
                            if (angle_for_bullet == LEFT) creatBullet();
                            MODE = ATTACK;
                            break;
                        }
                MODE = NORMAL;


            }
        }

        public float distance_of_goal;
        public float targetX, targetY;

        public void start_attack_tank() {
            Group tanks = (fraction == ObjectVarable.tank_unity) ? GameStage.MT.tanks_enemy : GameStage.MT.tanks_unity;
            for (int i = 0; i < tanks.getChildren().size; i++) {
                Tank tank = (Tank) tanks.getChildren().get(i);
                float x = targetX = tank.AI.goal_x;
                float y = targetY = tank.AI.goal_y;


                if (x == goal_x)
                    if ((distance_of_goal = Math.abs(goal_y - y)) <= radius_enemy)
                        if (y > goal_y) {
                            if (hasUnDestroyableBlock(goal_x, goal_y, goal_x, goal_y + (int) distance_of_goal, false)) continue;

                            top();
                            if (angle_for_bullet == UP) creatBullet();
                            MODE = ATTACK;
                            return;
                        } else {
                            if (hasUnDestroyableBlock(goal_x, goal_y - (int) distance_of_goal, goal_x, goal_y, true)) continue;

                            bottom();
                            if (angle_for_bullet == DOWN) creatBullet();
                            MODE = ATTACK;
                            return;
                        }

                if (y == goal_y)
                    if ((distance_of_goal = Math.abs(goal_x - x)) <= radius_enemy)
                        if (x > goal_x) {
                            if (hasUnDestroyableBlock(goal_x, goal_y, goal_x + (int) distance_of_goal, goal_y, false)) continue;

                            right();
                            if (angle_for_bullet == RIGHT) creatBullet();
                            MODE = ATTACK;
                            return;
                        } else {
                            if (hasUnDestroyableBlock(goal_x - (int) distance_of_goal, goal_y, goal_x, goal_y, true)) continue;

                            left();
                            if (angle_for_bullet == LEFT) creatBullet();
                            MODE = ATTACK;
                            return;
                        }
                MODE = NORMAL;
            }
			int code = MainTerrain.Mission.CODE;
            if (fraction == ObjectVarable.tank_enemy && code >= 50 && code <= 55) start_attack_build();
			if (fraction == ObjectVarable.tank_unity && code >= 56 && code <= 60) start_attack_build();
            //GameStage.world_nodes[goal_x][goal_y] = MODE == NORMAL ? 0 : id;
        }

		private Tank lastBuf = null;
		
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
			
			/*if (isRiding) step_place = false;
			
			if (step_place) 
			{
				step();
				step_place = false;
				if(!hasNextBlock())
				{
					Vector2 pos = nextBlockPosition();
					if(pos != null)
					{
						Tank tank = GameStage.MT.hashTanks.get(GameStage.world_block[(int)pos.x][(int)pos.y]);
						if (tank != null)
						{
							if (tank.AI.isOnBlock())
								tank.AI.step_place = true;
							return;
						}
					}
					else
						step_place = false;
				}
				else
					step_place = false;
			}*/
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
                if (isDestroyableBlockForBullet(block)) creatBullet();

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

                        if (angle_for_bullet == DOWN) {
							preWay = nodeWay;
							currentWay = nextWay;
							nextWay = path.next();
						}
                        break;
                    }

                    case AStarNode.UP: {
                        UP();

                        if (findBarrier(UP)) break;

							if (angle_for_bullet == UP) {
								preWay = nodeWay;
								currentWay = nextWay;
								nextWay = path.next();
							}
                        break;
                    }

                    case AStarNode.LEFT: {
                        LEFT();

                        if (findBarrier(LEFT)) break;

							if (angle_for_bullet == LEFT) {
								preWay = nodeWay;
								currentWay = nextWay;
								nextWay = path.next();
							}
                        break;
                    }

                    case AStarNode.RIGHT: {
                        RIGHT();

                        if (findBarrier(RIGHT)) break;

							if (angle_for_bullet == RIGHT) {
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
            if (rotate == false)
                if (hasNextBlock()) {
                    if (rotate == false) {
                        nextBlock();
                        createTrack();
                        updateRun();
                    }
                }
        }

        public void step() {
            if (rotate == false)
                if (hasNextBlock()) {
                    if (rotate == false) {
                        nextBlock();
                        createTrack();
                        updateRun();
                    }
                } else {
                    turn();
                }
        }

        //private int last = -1;

        //private int isFinishSeek = 0;

        public void updateUser() {
            weapon = Settings.TankUserSettings.bullet_type;


            if (isOnBlock()) {
                updateBullet();
                detectGround();

                if (isRiding)
                    stepWithoutTurn();
            } else {
                updateRun();
            }
            //destroyTank();
        }

        public boolean isDestroyableBlockForBullet(Block block) {
            if (block instanceof DestroyableBlock && !(block instanceof Spike)) return true;
            if (weapon == BulletList.ROKET && block instanceof ConcreteWall) return true;

            return false;
        }

        public void wUP() {
            if (isOnBlockWithoutTransform()) {
                top();
                if (angle_for_bullet == UP)
                    if (hasNextBlock(UP) && rotate == false) {
                        createTrack();
                        nextBlock(UP);
                        updateRun();
                    }
            }
        }

        public void wDOWN() {
            if (isOnBlockWithoutTransform()) {
                bottom();
                if (angle_for_bullet == DOWN)
                    if (hasNextBlock(DOWN) && rotate == false) {
                        createTrack();
                        nextBlock(DOWN);
                        updateRun();
                    }
            }
        }

        public void wLEFT() {
            if (isOnBlockWithoutTransform()) {
                left();
                if (angle_for_bullet == LEFT)
                    if (hasNextBlock(LEFT) && rotate == false) {
                        createTrack();
                        nextBlock(LEFT);
                        updateRun();
                    }
            }
        }

        public void wRIGHT() {
            if (isOnBlockWithoutTransform()) {
                right();
                if (angle_for_bullet == RIGHT)
                    if (hasNextBlock(RIGHT) && rotate == false) {
                        createTrack();
                        nextBlock(RIGHT);
                        updateRun();
                    }
            }
        }

        public void UP() {
            if (isOnBlock()) {
                top();
                if (angle_for_bullet == UP)
                    if (hasNextBlock(UP) && rotate == false) {
                        createTrack();
                        nextBlock(UP);
                        updateRun();
                    }
            }
        }

        public void DOWN() {
            if (isOnBlock()) {
                bottom();
                if (angle_for_bullet == DOWN)
                    if (hasNextBlock(DOWN) && rotate == false) {
                        createTrack();
                        nextBlock(DOWN);
                        updateRun();
                    }
            }
        }

        public void LEFT() {
            if (isOnBlock()) {
                left();
                if (angle_for_bullet == LEFT)
                    if (hasNextBlock(LEFT) && rotate == false) {
                        createTrack();
                        nextBlock(LEFT);
                        updateRun();
                    }
            }
        }

        public void RIGHT() {
            if (isOnBlock()) {
                right();
                if (angle_for_bullet == RIGHT)
                    if (hasNextBlock(RIGHT) && rotate == false) {
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
								Artiling art = new Artiling(goal_x, goal_y, len, angle_for_bullet);
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
            if (rotate == false)
                creatBullet();
        }

        private void createTrack() {
            switch (angle_for_bullet) {
                case DOWN: {
                    if (last_angle_for_bullet == LEFT) {
                        GameStage.MT.track.addActor(new Track(getCenterX(), getCenterY(), Track.RIGHT_DOWN));
                        break;
                    }
                    if (last_angle_for_bullet == RIGHT) {
                        GameStage.MT.track.addActor(new Track(getCenterX(), getCenterY(), Track.LEFT_DOWN));
                        break;
                    }
                    GameStage.MT.track.addActor(new Track(getCenterX(), getCenterY(), Track.VERTICAL));
                    break;
                }
                case RIGHT: {
                    if (last_angle_for_bullet == UP) {
                        GameStage.MT.track.addActor(new Track(getCenterX(), getCenterY(), Track.RIGHT_DOWN));
                        break;
                    }
                    if (last_angle_for_bullet == DOWN) {
                        GameStage.MT.track.addActor(new Track(getCenterX(), getCenterY(), Track.RIGHT_UP));
                        break;
                    }
                    GameStage.MT.track.addActor(new Track(getCenterX(), getCenterY(), Track.HORIZONTAL));
                    break;
                }
                case UP: {
                    if (last_angle_for_bullet == RIGHT) {
                        GameStage.MT.track.addActor(new Track(getCenterX(), getCenterY(), Track.LEFT_UP));
                        break;
                    }
                    if (last_angle_for_bullet == LEFT) {
                        GameStage.MT.track.addActor(new Track(getCenterX(), getCenterY(), Track.RIGHT_UP));
                        break;
                    }
                    GameStage.MT.track.addActor(new Track(getCenterX(), getCenterY(), Track.VERTICAL));
                    break;
                }
                case LEFT: {
                    if (last_angle_for_bullet == UP) {
                        GameStage.MT.track.addActor(new Track(getCenterX(), getCenterY(), Track.LEFT_DOWN));
                        break;
                    }
                    if (last_angle_for_bullet == DOWN) {
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
            return nextBlockPosition(angle_for_bullet);
        }

        private boolean hasNextBlock() {
			return hasNextBlock(angle_for_bullet);
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
			nextBlock(angle_for_bullet);
		}

        private void turn() {
            //GameStage.world_tank[goal_x][goal_y] = Tank.this;
            goal_x = (int) getCenterX();
            goal_y = (int) getCenterY();
            switch (angle_for_bullet) {
                case 1: {

                    //goal_x = (int)getCenterX();
                    //goal_y = (int)getCenterY();
                    if (hasNextBlock(2)) {
                        RIGHT();
                        //nextBlock(2);
                    } else if (hasNextBlock(4)) {
                        LEFT();
                        //nextBlock(4);
                    } else if (hasNextBlock(3)) {
                        UP();
                        //nextBlock(3);
                    }
                    break;
                }
                case 4: {
                    //goal_x = (int)getCenterX();
                    //goal_y = (int)getCenterY();
                    if (hasNextBlock(3)) {
                        UP();
                        //nextBlock(3);
                    } else if (hasNextBlock(1)) {
                        DOWN();
                        //nextBlock(1);
                    } else if (hasNextBlock(2)) {
                        RIGHT();
                        //nextBlock(2);
                    }
                    break;
                }
                case 3: {
                    //goal_x = (int)getCenterX();
                    //goal_y = (int)getCenterY();
                    if (hasNextBlock(4)) {
                        LEFT();
                        //nextBlock(4);
                    } else if (hasNextBlock(2)) {
                        RIGHT();
                        //nextBlock(2);
                    } else if (hasNextBlock(1)) {
                        DOWN();
                        //nextBlock(1);
                    }
                    break;
                }
                case 2: {
                    //goal_x = (int)getCenterX();
                    //goal_y = (int)getCenterY();
                    if (hasNextBlock(1)) {
                        DOWN();
                        //nextBlock(1);
                    } else if (hasNextBlock(3)) {
                        UP();
                        //nextBlock(3);
                    } else if (hasNextBlock(4)) {
                        LEFT();
                        //nextBlock(4);
                    }
                    break;
                }
            }
        }


        public boolean isOnBlockWithoutTransform() {
            boolean b = false;

            //if(rotate == false)
            switch (angle_for_bullet) {
                case 1: {
                    if (getCenterY() <= goal_y) {
                        //Body.setTransform(Body.getPosition().x, goal_y, Body.getAngle());
                        GameStage.world_block[goal_x][goal_y] = id;
                        GameStage.world_tank[goal_x][goal_y] = Tank.this;
                        if (last_point != null) {
                            GameStage.world_tank[(int) last_point.x][(int) last_point.y] = null;
                            GameStage.world_block[(int) last_point.x][(int) last_point.y] = 0;
                        }
                        last_point = null;
                        b = true;
							/*GameStage.world_tank[goal_x][goal_y] = Tank.this;
							 if(last_x >= 0&&last_y >= 0)
							 {
							 GameStage.world_tank[last_x][last_y] = null;
							 }
							 last_x = goal_x;
							 last_y = goal_y;*/
                    } else {
                        b = false;
                    }
                    break;
                }
                case 2: {
                    if (getCenterX() >= goal_x) {
                        //Body.setTransform(goal_x, Body.getPosition().y, Body.getAngle());
                        GameStage.world_block[goal_x][goal_y] = id;
                        GameStage.world_tank[goal_x][goal_y] = Tank.this;
                        if (last_point != null) {
                            GameStage.world_tank[(int) last_point.x][(int) last_point.y] = null;
                            GameStage.world_block[(int) last_point.x][(int) last_point.y] = 0;
                        }
                        last_point = null;
                        b = true;
							/*GameStage.world_tank[goal_x][goal_y] = Tank.this;
							 if(last_x >= 0&&last_y >= 0)
							 {
							 GameStage.world_tank[last_x][last_y] = null;
							 }
							 last_x = goal_x;
							 last_y = goal_y;*/
                    } else {
                        b = false;
                    }
                    break;
                }
                case 3: {
                    if (getCenterY() >= goal_y) {
                        //Body.setTransform(Body.getPosition().x, goal_y, Body.getAngle());
                        GameStage.world_block[goal_x][goal_y] = id;
                        GameStage.world_tank[goal_x][goal_y] = Tank.this;
                        if (last_point != null) {
                            GameStage.world_tank[(int) last_point.x][(int) last_point.y] = null;
                            GameStage.world_block[(int) last_point.x][(int) last_point.y] = 0;
                        }
                        last_point = null;
                        b = true;
							/*GameStage.world_tank[goal_x][goal_y] = Tank.this;
							 if(last_x >= 0&&last_y >= 0)
							 {
							 GameStage.world_tank[last_x][last_y] = null;
							 }
							 last_x = goal_x;
							 last_y = goal_y;*/
                    } else {
                        b = false;
                    }
                    break;
                }
                case 4: {
                    if (getCenterX() <= goal_x) {
                        //Body.setTransform(goal_x, Body.getPosition().y, Body.getAngle());
                        GameStage.world_block[goal_x][goal_y] = id;
                        GameStage.world_tank[goal_x][goal_y] = Tank.this;
                        if (last_point != null) {
                            GameStage.world_tank[(int) last_point.x][(int) last_point.y] = null;
                            GameStage.world_block[(int) last_point.x][(int) last_point.y] = 0;
                        }
                        last_point = null;
                        b = true;
							/*GameStage.world_tank[goal_x][goal_y] = Tank.this;
							 if(last_x >= 0&&last_y >= 0)
							 {
							 GameStage.world_tank[last_x][last_y] = null;
							 }
							 last_x = goal_x;
							 last_y = goal_y;*/
                    } else {
                        b = false;
                    }
                    break;
                }
            }

            return b;
        }

        public boolean isOnBlock() {
            boolean b = false;
            //if(rotate == false)

            switch (angle_for_bullet) {
                case 1: {
                    if (getCenterY() <= goal_y) {
                        setCenterPosition(getCenterX(), goal_y);
                        GameStage.world_block[goal_x][goal_y] = id;
                        GameStage.world_tank[goal_x][goal_y] = Tank.this;
                        if (last_point != null) {
                            GameStage.world_tank[(int) last_point.x][(int) last_point.y] = null;
                            GameStage.world_block[(int) last_point.x][(int) last_point.y] = 0;
                        }
                        last_point = null;
                        b = true;
							/*GameStage.world_tank[goal_x][goal_y] = Tank.this;
							 if(last_x >= 0&&last_y >= 0)
							 {
							 GameStage.world_tank[last_x][last_y] = null;
							 }
							 last_x = goal_x;
							 last_y = goal_y;*/
                    } else {
                        b = false;
                    }
                    break;
                }
                case 2: {
                    if (getCenterX() >= goal_x) {
                        setCenterPosition(goal_x, getCenterY());
                        GameStage.world_block[goal_x][goal_y] = id;
                        GameStage.world_tank[goal_x][goal_y] = Tank.this;
                        if (last_point != null) {
                            GameStage.world_tank[(int) last_point.x][(int) last_point.y] = null;
                            GameStage.world_block[(int) last_point.x][(int) last_point.y] = 0;
                        }
                        last_point = null;
                        b = true;
							/*GameStage.world_tank[goal_x][goal_y] = Tank.this;
							 if(last_x >= 0&&last_y >= 0)
							 {
							 GameStage.world_tank[last_x][last_y] = null;
							 }
							 last_x = goal_x;
							 last_y = goal_y;*/
                    } else {
                        b = false;
                    }
                    break;
                }
                case 3: {
                    if (getCenterY() >= goal_y) {
                        setCenterPosition(getCenterX(), goal_y);
                        GameStage.world_block[goal_x][goal_y] = id;
                        GameStage.world_tank[goal_x][goal_y] = Tank.this;
                        if (last_point != null) {
                            GameStage.world_tank[(int) last_point.x][(int) last_point.y] = null;
                            GameStage.world_block[(int) last_point.x][(int) last_point.y] = 0;
                        }
                        last_point = null;
                        b = true;
							/*GameStage.world_tank[goal_x][goal_y] = Tank.this;
							 if(last_x >= 0&&last_y >= 0)
							 {
							 GameStage.world_tank[last_x][last_y] = null;
							 }
							 last_x = goal_x;
							 last_y = goal_y;*/
                    } else {
                        b = false;
                    }
                    break;
                }
                case 4: {
                    if (getCenterX() <= goal_x) {
                        setCenterPosition(goal_x, getCenterY());
                        GameStage.world_block[goal_x][goal_y] = id;
                        GameStage.world_tank[goal_x][goal_y] = Tank.this;
                        if (last_point != null) {
                            GameStage.world_tank[(int) last_point.x][(int) last_point.y] = null;
                            GameStage.world_block[(int) last_point.x][(int) last_point.y] = 0;
                        }
                        last_point = null;
                        b = true;
							/*GameStage.world_tank[goal_x][goal_y] = Tank.this;
							 if(last_x >= 0&&last_y >= 0)
							 {
							 GameStage.world_tank[last_x][last_y] = null;
							 }
							 last_x = goal_x;
							 last_y = goal_y;*/
                    } else {
                        b = false;
                    }
                    break;
                }
            }

            return b;
        }
    }

    public class Health extends Actor {
        public Health() {
			helth.setSize(a * 2, 0.3f * ObjectVarable.size_block);
            helth.setCenter(Tank.this.getCenterX(), Tank.this.getCenterY() + a + helth.getHeight());
			//helth.setRegion(h[Math.max((int) ((12 * HP) / HPbackup) - 1, 0)]);
			setSize(helth.getWidth(), helth.getHeight());
			setPosition(helth.getX(), helth.getY());
        }

		@Override
		public void act(float delta) {
			helth.setSize(a * 2, 0.3f * ObjectVarable.size_block);
            helth.setCenter(Tank.this.getCenterX(), Tank.this.getCenterY() + a + helth.getHeight());
			
			setSize(helth.getWidth(), helth.getHeight());
			setPosition(helth.getX(), helth.getY());
			// TODO: Implement this method
			super.act(delta);
		}
		
        @Override
        public void draw(Batch batch, float parentAlpha) {
			super.draw(batch, parentAlpha);
			helth.setRegion(h[Math.min(11, Math.max((int) ((12 * HP) / HPbackup) - 1, 0))]);
            helth.draw(batch);
		}
    }

    public class Ring extends Actor {
		@Override
		public void act(float delta) {
			setSize(ObjectVarable.size_block * 3, ObjectVarable.size_block * 3);
			setPosition(Tank.this.getCenterX(), Tank.this.getCenterY(), Align.center);
			super.act(delta);
		}
		
        @Override
        public void draw(Batch batch, float parentAlpha) {
            // TODO: Implement this method
            super.draw(batch, parentAlpha);
			if (HPShield > 0)
                ring.setRegion(rings[2]);
            else
                ring.setRegion(rings[ringid]);
            ring.setSize(ObjectVarable.size_block * 3, ObjectVarable.size_block * 3);
            ring.setCenter(Tank.this.getCenterX(), Tank.this.getCenterY());
            ring.draw(batch);
        }
    }

    public static enum TankCharacter {

    }
}
