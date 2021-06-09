package com.tanchiki.libgdx.model.tanks;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;
import com.tanchiki.libgdx.graphics.GameActor;
import com.tanchiki.libgdx.model.buildes.Build;
import com.tanchiki.libgdx.model.buildes.ObjBuild;
import com.tanchiki.libgdx.model.bullets.*;
import com.tanchiki.libgdx.model.explosions.NormalExplosion;
import com.tanchiki.libgdx.model.mine.MineUnity1;
import com.tanchiki.libgdx.model.mine.MineUnity2;
import com.tanchiki.libgdx.model.terrains.*;
import com.tanchiki.libgdx.model.tnt.TNT1;
import com.tanchiki.libgdx.model.tnt.TNT2;
import com.tanchiki.libgdx.model.tnt.TNT3;
import com.tanchiki.libgdx.stage.GameStage;
import com.tanchiki.libgdx.util.*;
import com.tanchiki.libgdx.util.astar.AStarNode;
import com.tanchiki.libgdx.util.astar.AStarPath;

public class Tank extends GameActor {
    protected GameStage gameStage = GameStage.getInstance();
    private final float a = ObjectVariables.size_block;
    public Animation<TextureRegion> anim;
    public short fraction;
    private int angle = 0;
    private int angleSet = 0;
    public float HP = 3;
    public float HPBackup;
    public float HPShield = 0;
    public float HPShieldBackup = 0;
    public float speed = 1;
    protected float time;
    public boolean rotate = true;
    int direction = 1;
    int lastDirection = 1;
    public int weapon = 1;
    public DefaultAI defaultAI;
    private final int id;
    private float speedr = 0;
    public float speedSkill = 1;
    public float speedTime = 0;
    public boolean speedHack = false;
    public static final int DOWN = 1,
            RIGHT = 2,
            UP = 3,
            LEFT = 4;
    public final Health health = new Health();
    public final Ring ring = new Ring();
    public static boolean stop_enemy = false;
    public static boolean stop_unity = false;
    private boolean hasRide = true;
    public boolean giveCoin = false;
    public int giveDamage = 0;
    public float coinPrice = 0;
    public boolean boss = false;

    public Tank(float x, float y, short fraction, TextureRegion[] regions, int weapon) {
        this.weapon = weapon;
        this.fraction = fraction;
        setSize(a * 2.6f, a * 2.6f);
        setCenterPosition(x, y);
        setOrigin(getWidth() / 2, getHeight() / 2);

        anim = new Animation<>(360 / 16f, regions);

        switch (this.fraction) {
            case ObjectVariables.tank_enemy:
                health.setRegions(TextureLoader.getInstance().getHealthRed()[0]);
                ring.setRing(Ring.ENEMY);
                break;
            case ObjectVariables.tank_ally:
                health.setRegions(TextureLoader.getInstance().getHealthYell()[0]);
                ring.setRing(Ring.ALLY);
        }
        updateDirection();

        gameStage.MT.health.addActor(health);
        gameStage.MT.ring.addActor(ring);

        gameStage.MT.hashTanks.put(id = this.fraction * hashCode(), this);
    }

    public int getDirection() {
        return direction;
    }

    public void setBossEnable() {
        if (boss) return;

        boss = true;
        if (fraction == ObjectVariables.tank_enemy) {
            ObjectVariables.all_size_boss_enemies++;
        }

        if (fraction == ObjectVariables.tank_ally) {
            ObjectVariables.all_size_boss_allies++;
        }
    }

    void incrementSize() {
        switch (fraction) {
            case ObjectVariables.tank_enemy:
                ObjectVariables.size_enemies++;
                if (boss) ObjectVariables.all_size_boss_enemies++;
                break;
            case ObjectVariables.tank_ally:
                ObjectVariables.size_allies++;
                if (boss) ObjectVariables.all_size_boss_allies++;
        }
    }

    void decrementSize() {
        switch (fraction) {
            case ObjectVariables.tank_enemy:
                ObjectVariables.size_enemies--;
                ObjectVariables.all_size_enemies--;
                if (boss) ObjectVariables.all_size_boss_enemies--;
                break;
            case ObjectVariables.tank_ally:
                ObjectVariables.size_allies--;
                ObjectVariables.all_size_allies--;
                if (boss) ObjectVariables.all_size_boss_allies--;
        }
    }

    public boolean isHasRide() {
        return hasRide;
    }

    public void setHasRide(boolean hasRide) {
        this.hasRide = hasRide;
    }

    public DefaultAI getAI() {
        return defaultAI;
    }

    public void setAI(DefaultAI DefaultAI) {
        this.defaultAI = DefaultAI;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(anim.getKeyFrame(Math.abs(angleSet), true),
                getX(), getY(),
                a * 2.6f, a * 2.6f);
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

    protected void updateMove() {
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

    public void updateDirection() {
        if (angle != 0) return;

        lastDirection = direction;
        switch (angleSet) {
            case 0:
            case 360:
                direction = DOWN;
                break;
            case 90:
                direction = LEFT;
                break;
            case 180:
                direction = UP;
                break;
            case 270:
                direction = RIGHT;
                break;
        }
    }

    float timeBullet = 1.0f;

    void createLightBullet() {
        gameStage.MT.bullet.addActor(new BulletLight(getCenterX(), getCenterY(), direction, fraction, this));
    }

    float timePlasma = 0.6f;

    void createPlasmaBullet() {
        gameStage.MT.bullet.addActor(new BulletPlusma(getCenterX(), getCenterY(), direction, fraction, this));
    }

    void createDoubleLightBullet() {
        switch (direction) {
            case 1:
            case 3:
                gameStage.MT.bullet.addActor(new BulletLight(getCenterX() + 0.2f, getCenterY(), direction, fraction, this));
                gameStage.MT.bullet.addActor(new BulletLight(getCenterX() - 0.2f, getCenterY(), direction, fraction, this));
                break;
            case 2:
            case 4:
                gameStage.MT.bullet.addActor(new BulletLight(getCenterX(), getCenterY() + 0.2f, direction, fraction, this));
                gameStage.MT.bullet.addActor(new BulletLight(getCenterX(), getCenterY() - 0.2f, direction, fraction, this));
                break;
        }
    }

    void createDoublePlasmaBullet() {
        switch (direction) {
            case 1:
            case 3:
                gameStage.MT.bullet.addActor(new BulletPlusma(getCenterX() + 0.2f, getCenterY(), direction, fraction, this));
                gameStage.MT.bullet.addActor(new BulletPlusma(getCenterX() - 0.2f, getCenterY(), direction, fraction, this));
                break;
            case 2:
            case 4:
                gameStage.MT.bullet.addActor(new BulletPlusma(getCenterX(), getCenterY() + 0.2f, direction, fraction, this));
                gameStage.MT.bullet.addActor(new BulletPlusma(getCenterX(), getCenterY() - 0.2f, direction, fraction, this));
                break;
        }
    }

    void createArmoredBullet1() {
        gameStage.MT.bullet.addActor(new ArmoredBullet1(getCenterX(), getCenterY(), direction, fraction, this));
    }

    void createArmoredBullet2() {
        gameStage.MT.bullet.addActor(new ArmoredBullet2(getCenterX(), getCenterY(), direction, fraction, this));
    }

    float timeRocket = 1.2f;

    void createRocket() {
        gameStage.MT.bullet.addActor(new Rocket(getCenterX(), getCenterY(), direction, fraction, this));
    }

    protected void createBullet() {
        switch (weapon) {
            case 1:
                if (time > timeBullet / speedSkill) {
                    createLightBullet();
                    time = 0;
                }
                break;
            case 2:
                if (time > timePlasma / speedSkill) {
                    createPlasmaBullet();
                    time = 0;
                }
                break;
            case 3:
                if (time > timeBullet / speedSkill) {
                    createDoubleLightBullet();
                    time = 0;
                }
                break;
            case 4:
                if (time > timePlasma / speedSkill) {
                    createDoublePlasmaBullet();
                    time = 0;
                }
                break;
            case 5:
                if (time > timeBullet / speedSkill) {
                    createArmoredBullet1();
                    time = 0;
                }
                break;
            case 6:
                if (time > timeBullet / speedSkill) {
                    createArmoredBullet2();
                    time = 0;
                }
                break;
            case 7:
                if (time > timeRocket / speedSkill) {
                    createRocket();
                    time = 0;
                }
                break;
        }
    }

    protected void explodeTankAnimation() {
        int x = (int) getCenterX();
        int y = (int) getCenterY();
        x += x % 2;
        y += y % 2;

        gameStage.MT.explosions.addActor(new NormalExplosion(x, y, TextureLoader.getInstance().getExpl()));
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
            gameStage.world_tank[defaultAI.goal_x][defaultAI.goal_y] = null;
            gameStage.world_block[defaultAI.goal_x][defaultAI.goal_y] = 0;
            if (defaultAI.lastPoint != null) {
                gameStage.world_block[(int) defaultAI.lastPoint.x][(int) defaultAI.lastPoint.y] = 0;
                gameStage.world_tank[(int) defaultAI.lastPoint.x][(int) defaultAI.lastPoint.y] = null;
            }
            decrementSize();
            remove();
            if (giveCoin) {
                coinPrice = giveDamage * weapon;
                Settings.TankUserSettings.coin += coinPrice;
                MainTerrain.getCurrentTerrain().coin += coinPrice;
                if (boss) {
                    MainTerrain.getCurrentTerrain().star++;
                    Settings.TankUserSettings.star++;
                }
            }
            explodeTankAnimation();
        }
    }

    @Override
    public boolean remove() {
        health.remove();
        ring.remove();
        return super.remove();
    }

    public void left() {
        if (angle != 0) return;
        switch (angleSet) {
            case 0:
            case 360:
                angle = 90;
                break;
            //case 90: angle = 0; break;
            case 180:
                angle = -90;
                break;
            case 270:
                angle = -180;
                break;
        }
    }

    public void right() {
        if (angle != 0) return;
        switch (angleSet) {
            case 0:
            case 360:
                angle = -90;
                break;
            case 90:
                angle = -180;
                break;
            case 180:
                angle = 90;
                break;
            //case 270: angle = 0; break;
        }
    }

    public void top() {
        if (angle != 0) return;
        switch (angleSet) {
            case 0:
                angle = 180;
                break;
            case 360:
                angle = -180;
                break;
            case 90:
                angle = 90;
                break;
            //case 180: angle = 0; break;
            case 270:
                angle = -90;
                break;
        }
    }

    public void bottom() {
        if (angle != 0) return;
        switch (angleSet) {
            /*case 0: angle = 0; break;
            case 360: angle = 0; break;*/
            case 90:
                angle = -90;
                break;
            case 180:
                angle = -180;
                break;
            case 270:
                angle = 90;
                break;
        }
    }

    public void activateSpeedSkill() {
        speedHack = true;
        speedTime = 0;
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
        if (speedHack) {
            speedTime += delta;
            if (speedTime > 30) {
                speedHack = false;
                speedSkill = 1;
            } else {
                speedSkill = 2f;
            }
        }
        time += delta;
        updateRotation();
        if (defaultAI != null)
            defaultAI.update();
        ring.setPosition(Tank.this.getCenterX(), Tank.this.getCenterY(), Align.center);
        health.setPosition(Tank.this.getCenterX(), Tank.this.getCenterY() + a + health.getHeight());
        super.act(delta);
    }

    public class DefaultAI {
        public final int NORMAL = 1;
        public final int ATTACK = 2;
        public int goal_x = (int) getCenterX(), goal_y = (int) getCenterY();
        private final int block = ObjectVariables.size_block * 2;
        private Vector2 lastPoint = null;
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

        public DefaultAI() {
            gameStage.world_block[goal_x][goal_y] = id;
            gameStage.world_tank[goal_x][goal_y] = Tank.this;
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
                        if (!stilling) searchEnemy();
                    }
                    if (step_place) path = null;
                    step_place = false;
                    if (hasRide)
                        switch (MODE) {
                            case ATTACK:
                                ATTACK();
                                break;
                            case NORMAL:
                                NORMAL();
                                break;
                        }

                    MODE = NORMAL;
                    stilling = true;
                } else {
                    stilling = false;
                    if (step_place) createBullet();
                    step_place = false;
                    isRiding = true;
                    if (hasRide) updateMove();
                }
        }

        public void cleanup() {
            if (tankTarget != null && tankTarget.HP <= 0)
                tankTarget = null;
            if (buildTarget != null && buildTarget.HP <= 0)
                buildTarget = null;
            if (path == null) currentWay = preWay = nextWay = null;
        }

        public boolean isNotDestroyableBlock(Block block) {
            return !(block instanceof DestroyableBlock);
        }

        public boolean hasNotDestroyableBlock(int x0, int y0, int x, int y) {
            boolean result = false;
            int yy = y - y0;
            int xx = x - x0;
            int signx = Integer.signum(xx) * 2;
            int signy = Integer.signum(yy) * 2;
            for (int i = 0; i <= Math.abs(xx); i += 2, x0 += signx)
                for (int j = 0; j <= Math.abs(yy); j += 2, y0 += signy)
                    if (x0 >= 0 && x0 < gameStage.world_physic_block.length && y0 >= 0 && y0 < gameStage.world_physic_block[0].length) {
                        Block actor = gameStage.world_physic_block[x0][y0];
                        if (actor == null) continue;
                        result |= isNotDestroyableBlock(actor);
                    }
            return result;
        }

        public float distance_of_goal;
        public float targetX, targetY;

        public boolean startAttack(float x, float y) {
            if (x == goal_x)
                if ((distance_of_goal = Math.abs(goal_y - y)) <= radius_enemy)
                    if (y > goal_y) {
                        if (hasNotDestroyableBlock(goal_x, goal_y, goal_x, goal_y + (int) distance_of_goal))
                            return false;
                        top();
                        if (direction == UP) createBullet();
                        MODE = ATTACK;
                        return true;
                    } else {
                        if (hasNotDestroyableBlock(goal_x, goal_y, goal_x, goal_y - (int) distance_of_goal))
                            return false;
                        bottom();
                        if (direction == DOWN) createBullet();
                        MODE = ATTACK;
                        return true;
                    }

            if (y == goal_y)
                if ((distance_of_goal = Math.abs(goal_x - x)) <= radius_enemy)
                    if (x > goal_x) {
                        if (hasNotDestroyableBlock(goal_x, goal_y, goal_x + (int) distance_of_goal, goal_y))
                            return false;
                        right();
                        if (direction == RIGHT) createBullet();
                        MODE = ATTACK;
                        return true;
                    } else {
                        if (hasNotDestroyableBlock(goal_x, goal_y, goal_x - (int) distance_of_goal, goal_y))
                            return false;
                        left();
                        if (direction == LEFT) createBullet();
                        MODE = ATTACK;
                        return true;
                    }
            MODE = NORMAL;
            return false;
        }

        protected void startAttackBuild() {
            Group builds = gameStage.MT.builds;
            for (int i = 0; i < builds.getChildren().size; i++) {
                ObjBuild objbuild = (ObjBuild) builds.getChildren().get(i);
                Build build = objbuild instanceof Build ? (Build) objbuild : null;

                if (build == null) continue;
                if (build.fraction == fraction) continue;

                float x = targetX = Math.round(build.x);
                float y = targetY = Math.round(build.y);

                if (startAttack(x, y)) break;
            }
        }

        protected void startAttackTank() {
            Group tanks = (fraction == ObjectVariables.tank_ally) ?
                    gameStage.MT.tanks_enemy : gameStage.MT.tanks_unity;
            for (int i = 0; i < tanks.getChildren().size; i++) {
                Tank tank = (Tank) tanks.getChildren().get(i);
                float x = targetX = tank.defaultAI.goal_x;
                float y = targetY = tank.defaultAI.goal_y;

                if (startAttack(x, y)) return;
            }
            int code = MainTerrain.Mission.CODE;
            if (fraction == ObjectVariables.tank_enemy && code >= 50 && code <= 55) startAttackBuild();
            if (fraction == ObjectVariables.tank_ally && code >= 56 && code <= 60) startAttackBuild();
        }

        protected void searchEnemy() {
            if (MODE == ATTACK) return;
            if (path != null) return;

            Tank buffer = null;
            float last_dis = Float.MAX_VALUE;

            Group tanks = (fraction == ObjectVariables.tank_ally) ? gameStage.MT.tanks_enemy : gameStage.MT.tanks_unity;
            for (int i = 0; i < tanks.getChildren().size; i++) {
                Tank tank = (Tank) tanks.getChildren().get(i);

                Rectangle rect = new Rectangle(goal_x - radius_enemy, goal_y - radius_enemy, radius_enemy * 2, radius_enemy * 2);

                if (rect.contains(tank.defaultAI.goal_x, tank.defaultAI.goal_y)) {
                    float dst = Vector2.dst2(tank.defaultAI.goal_x, tank.defaultAI.goal_y, goal_x, goal_y);
                    if (dst < last_dis) {
                        last_dis = dst;
                        buffer = tank;
                    }
                }
            }
            if (buffer != null)
                path = gameStage.MT.AStar.search(buffer.defaultAI.goal_x,
                        buffer.defaultAI.goal_y,
                        goal_x,
                        goal_y);
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

                tank = gameStage.MT.hashTanks.get(gameStage.world_block[x][y]);
                if (!(tank instanceof TankUser) && tank != null) {
                    path = null;
                    if (tank.defaultAI.MODE == ATTACK) tank.defaultAI.step_place = true;
                }

                block = gameStage.world_physic_block[x][y];
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
                    updateMove();
                }
        }

        public void step() {
            if (!rotate)
                if (hasNextBlock()) {
                    nextBlock();
                    createTrack();
                    updateMove();
                } else {
                    turn();
                }
        }

        public boolean isDestroyableBlockForBullet(Block block) {
            if (block instanceof DestroyableBlock && !(block instanceof Spike)) return true;
            return weapon == BulletList.ROCKET && block instanceof ConcreteWall;
        }

        public void UP() {
            if (isOnBlock()) {
                top();
                if (direction == UP)
                    if (hasNextBlock(UP) && !rotate) {
                        createTrack();
                        nextBlock(UP);
                        updateMove();
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
                        updateMove();
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
                        updateMove();
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
                        updateMove();
                    }
            }
        }

        public void AIR() {
            int air = Settings.TankUserSettings.plus_bullet_type2;
            switch (air) {
                case 1:
                    if (WeaponData.art > 0)
                        if (time > 1) {
                            int damage = 9 + WeaponData.Upgrade.art * 3;
                            int len = 7 * 2 + (WeaponData.Upgrade.art >= 3 ? 1 : 0) * 2;
                            Artiling art = new Artiling(goal_x, goal_y, len, direction);
                            art.damage = damage;
                            art.diameter = WeaponData.Upgrade.art == 5 ? 8 * 2 : 6 * 2;
                            gameStage.MT.bullet.addActor(art);

                            //GameStage.MT.mines.addActor(new MineUnity1(goal_x,goal_y));
                            WeaponData.art -= 1;
                            time = 0;
                        }
                    break;
                case 2:
                    if (WeaponData.air > 0) {
                        gameStage.createAircraft();
                    }
            }
        }

        public void MINES() {
            int mine = Settings.TankUserSettings.plus_bullet_type1;
            if (gameStage.world_mines[goal_x][goal_y] == null)
                switch (mine) {
                    case 1:
                        if (WeaponData.mine1 > 0) {
                            gameStage.MT.mines.addActor(new MineUnity1(goal_x, goal_y));
                            WeaponData.mine1 -= 1;
                        }
                        break;
                    case 2:
                        if (WeaponData.mine2 > 0) {
                            gameStage.MT.mines.addActor(new MineUnity2(goal_x, goal_y));
                            WeaponData.mine2 -= 1;
                        }
                        break;
                    case 3:
                        if (WeaponData.tnt1 > 0) {
                            gameStage.MT.mines.addActor(new TNT1(goal_x, goal_y));
                            WeaponData.tnt1 -= 1;
                        }
                        break;
                    case 4:
                        if (WeaponData.tnt2 > 0) {
                            gameStage.MT.mines.addActor(new TNT2(goal_x, goal_y));
                            WeaponData.tnt2 -= 1;
                        }
                        break;
                    case 5:
                        if (WeaponData.tnt3 > 0) {
                            gameStage.MT.mines.addActor(new TNT3(goal_x, goal_y));
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
            Track track = new Track(getCenterX(), getCenterY());
            int orientation = 0;
            switch (direction) {
                case DOWN:
                    orientation = Track.VERTICAL;
                    switch (lastDirection) {
                        case LEFT:
                            orientation = Track.RIGHT_DOWN;
                            break;
                        case RIGHT:
                            orientation = Track.LEFT_DOWN;
                            break;
                    }
                    break;
                case RIGHT:
                    orientation = Track.HORIZONTAL;
                    switch (lastDirection) {
                        case UP:
                            orientation = Track.RIGHT_DOWN;
                            break;
                        case DOWN:
                            orientation = Track.RIGHT_UP;
                            break;
                    }
                    break;
                case UP:
                    orientation = Track.VERTICAL;
                    switch (lastDirection) {
                        case LEFT:
                            orientation = Track.RIGHT_UP;
                            break;
                        case RIGHT:
                            orientation = Track.LEFT_UP;
                            break;
                    }
                    break;
                case LEFT:
                    orientation = Track.HORIZONTAL;
                    switch (lastDirection) {
                        case UP:
                            orientation = Track.LEFT_DOWN;
                            break;
                        case DOWN:
                            orientation = Track.LEFT_UP;
                            break;
                    }
                    break;
            }
            track.setOrientation(orientation);
            gameStage.MT.track.addActor(track);
        }

        public void detectGround() {
            if (!hasRide)
                return;

            if (MODE == ATTACK)
                return;

            if (speedr == 0)
                speedr = speed;
            if (gameStage.world_obj[goal_x][goal_y] instanceof Sand)
                speed = newSpeed(2f);
            else if (gameStage.world_obj[goal_x][goal_y] instanceof Grass)
                speed = speedr;
            else if (gameStage.world_obj[goal_x][goal_y] instanceof Road)
                speed = newSpeed(0.8f);
            else if (gameStage.world_obj[goal_x][goal_y] instanceof Plate)
                speed = newSpeed(0.8f);

        }

        private float newSpeed(float scl) {
            return 2f / ((2f / speedr) * scl);
        }

        private boolean hasNextBlock(int o) {
            int x = (int) getCenterX(), y = (int) getCenterY();
            try {
                switch (o) {
                    case 1: {
                        int next_x = x;
                        int next_y = y - block;
                        if (gameStage.world_block[next_x][next_y] == (0)) {
                            return true;
                        } else if (gameStage.world_block[next_x][next_y] == (id)) {
                            return true;
                        }
                        break;
                    }
                    case 2: {
                        int next_x = x + block;
                        int next_y = y;
                        if (gameStage.world_block[next_x][next_y] == (0)) {
                            return true;
                        } else if (gameStage.world_block[next_x][next_y] == (id)) {
                            return true;
                        }
                        break;
                    }
                    case 3: {
                        int next_x = x;
                        int next_y = y + block;
                        if (gameStage.world_block[next_x][next_y] == (0)) {
                            return true;
                        } else if (gameStage.world_block[next_x][next_y] == (id)) {
                            return true;
                        }
                        break;
                    }
                    case 4: {
                        int next_x = x - block;
                        int next_y = y;
                        if (gameStage.world_block[next_x][next_y] == (0)) {
                            return true;
                        } else if (gameStage.world_block[next_x][next_y] == (id)) {
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
                        if (gameStage.world_block[next_x][next_y] != 0) {
                        }
                        return new Vector2(next_x, next_y);
                    }
                    case 2: {
                        int next_x = x + block;
                        int next_y = y;
                        if (gameStage.world_block[next_x][next_y] != 0) {
                        }
                        return new Vector2(next_x, next_y);
                    }
                    case 3: {
                        int next_x = x;
                        int next_y = y + block;
                        if (gameStage.world_block[next_x][next_y] != 0) {
                        }
                        return new Vector2(next_x, next_y);
                    }
                    case 4: {
                        int next_x = x - block;
                        int next_y = y;
                        if (gameStage.world_block[next_x][next_y] != 0) {
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
            lastPoint = new Vector2(goal_x, goal_y);
            switch (t) {
                case 1:
                    goal_x = x;
                    goal_y = y - block;
                    break;
                case 2:
                    goal_x = x + block;
                    goal_y = y;
                    break;
                case 3:
                    goal_x = x;
                    goal_y = y + block;
                    break;
                case 4:
                    goal_x = x - block;
                    goal_y = y;
                    break;
            }
            gameStage.world_block[goal_x][goal_y] = id;
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
                    if (b = y <= goal_y) y = goal_y;
                    break;
                case 2:
                    if (b = x >= goal_x) x = goal_x;
                    break;
                case 3:
                    if (b = y >= goal_y) y = goal_y;
                    break;
                case 4:
                    if (b = x <= goal_x) x = goal_x;
                    break;
            }
            if (b) {
                if (isTransform) setCenterPosition(x, y);
                gameStage.world_block[goal_x][goal_y] = id;
                gameStage.world_tank[goal_x][goal_y] = Tank.this;
                if (lastPoint != null) {
                    gameStage.world_tank[(int) lastPoint.x][(int) lastPoint.y] = null;
                    gameStage.world_block[(int) lastPoint.x][(int) lastPoint.y] = 0;
                }
                lastPoint = null;
            }
            return b;
        }
    }

    public class Health extends Actor {
        private TextureRegion[] regions;

        public Health() {
            setSize(a * 2, 0.3f * ObjectVariables.size_block);
            //setPosition(Tank.this.getCenterX(), Tank.this.getCenterY() + a + health.getHeight());
        }

        public void setRegions(TextureRegion[] regions) {
            this.regions = regions;
        }

        @Override
        public void draw(Batch batch, float parentAlpha) {
            super.draw(batch, parentAlpha);
            batch.draw(regions[Math.min(11, Math.max((int) ((12 * HP) / HPBackup) - 1, 0))],
                    getX() - getWidth() / 2, getY(),
                    getWidth(), getHeight());
        }
    }

    public class Ring extends Actor {
        public static final int USER = 0;
        public static final int ENEMY = 1;
        public static final int SHIELD = 2;
        public static final int SHIELD_DAMAGED = 3;
        public static final int ALLY = 4;

        private final TextureRegion[] rings = TextureLoader.getInstance().getRings()[0];
        private int index;

        public Ring() {
            setSize(ObjectVariables.size_block * 3, ObjectVariables.size_block * 3);
            //setPosition(Tank.this.getCenterX(), Tank.this.getCenterY(), Align.center);
        }

        @Override
        public void draw(Batch batch, float parentAlpha) {
            super.draw(batch, parentAlpha);
            batch.draw(rings[HPShield > 0 ? SHIELD : index],
                    getX(), getY(),
                    getWidth(), getHeight());
        }

        public void setRing(int index) {
            this.index = index;
        }
    }
}
