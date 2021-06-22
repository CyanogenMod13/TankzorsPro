package com.tanchiki.libgdx.model.terrains;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import com.tanchiki.libgdx.model.aircraft.Airplane;
import com.tanchiki.libgdx.model.buildes.HangarEnemy;
import com.tanchiki.libgdx.model.buildes.HangarUnity;
import com.tanchiki.libgdx.model.bullets.Bullet;
import com.tanchiki.libgdx.model.explosions.BiggestExplosion;
import com.tanchiki.libgdx.model.tanks.Tank;
import com.tanchiki.libgdx.model.tanks.TankUser;
import com.tanchiki.libgdx.model.ui.MissionCompleted;
import com.tanchiki.libgdx.stage.GameStage;
import com.tanchiki.libgdx.stage.PanelStage;
import com.tanchiki.libgdx.util.ObjectVariables;
import com.tanchiki.libgdx.util.TextureLoader;

import java.util.HashMap;
import java.util.Map;

public class Trigger extends Actor {
    public static int LAST_INDEX = 0;
    public static Map<Integer, Trigger> register;

    public boolean enabled = true;
    public int sensitivity = 0;
    public int advsen = 0;
    public int delay = 0;
    public int type = 0;
    public int param = 0;
    public int index = 0;
    public boolean active = false;
    public boolean multiEnabled = false;
    public boolean visibled = false;
    public boolean seconds = false;
    public boolean green, yellow, blue;
    public boolean byUser, byEnemy, byAlly, byBullet, byExpl;

    boolean isActived = false;

    int x, y;
    int time = 0;

    MainTerrain.Parameters prm = MainTerrain.getCurrentTerrain().getParameters();
    TextureRegion[] r = TextureLoader.getInstance().getTriggers()[0];
    Sprite s = new Sprite(r[0]);

    public Trigger(int x, int y) {
        if (LAST_INDEX == 0) register = new HashMap<>();

        index = LAST_INDEX++;
        this.x = x;
        this.y = y;

        if (index > 19) index = 19;

        type = prm.getKey(63 + 3 * index);
        param = prm.getKey(64 + 3 * index);
        delay = prm.getKey(65 + 3 * index);
        if (delay - 128 > 0) {
            delay -= 128;
            delay *= 1000;
        } else {
            delay *= 40;
        }
        sensitivity = prm.getKey(156 + index);
        advsen = prm.getKey(176 + index);

        String sen = Integer.toBinaryString(sensitivity);
        try {
            byUser = '1' == (sen.charAt(sen.length() - 1));
            byAlly = '1' == (sen.charAt(sen.length() - 2));
            byEnemy = '1' == (sen.charAt(sen.length() - 3));
            byBullet = '1' == (sen.charAt(sen.length() - 4));
            byExpl = '1' == (sen.charAt(sen.length() - 5));
        } catch (Exception e) {
        }

        String adv = Integer.toBinaryString(advsen);
        //Gdx.app.log("trigger " + index, adv + " " + sen);
        try {
            enabled = '1' == (adv.charAt(adv.length() - 1));
            isActived = '1' == (adv.charAt(adv.length() - 2));
            multiEnabled = '1' == (adv.charAt(adv.length() - 3));
            visibled = '1' == (adv.charAt(adv.length() - 4));
            green = '1' == (adv.charAt(adv.length() - 5));
            yellow = '1' == (adv.charAt(adv.length() - 6));
            blue = adv.charAt(adv.length() - 5) == adv.charAt(adv.length() - 6);
        } catch (Exception e) {
        }

        setSize(2, 2);
        setPosition(x, y, Align.center);
        s.setSize(2, 2);
        s.setCenter(x, y);
        GameStage.getInstance().world_trigger[(int) x][(int) y] = this;
        register.put(index + 1, this);

        if (type == 20 && param - 128 >= 0) MissionCompleted.show = false;

        System.out.println("type " + type + " param " + param + " delay " + delay);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (visibled) {
            int img = (isClick) ? 1 : 0;
            if (green) img = isClick ? 3 : 2;
            if (yellow) img = isClick ? 5 : 4;
            if (blue) img = isClick ? 7 : 6;
            s.setRegion(r[img]);
            s.draw(batch);
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        LAST_INDEX = 0;

        if (enabled && !running) {
            onClick();
            activation();
        }
        if (active) function();
    }

    public void actived() {
        isActived = true;
    }

    boolean running = false;

    private void activation() {
        if (isActived) {
            running = true;
            new Thread(new Activation()).start();
            isActived = false;
            enabled = false;
        }
    }

    private void function() {
        enabled = multiEnabled;
        active = false;
        //PanelStage.getInstance().addToast("trigger " + type + " param " + param + " multi " + multiEnabled);
        switch (type) {
            case 1:
                Function.createBlocks(x, y - 2 * 2, param, true);
                break;
            case 2:
                Function.createBlocks(x - 2 * 2, y, param, false);
                break;
            case 3:
                Function.createBlocks(x, y + 2 * 2, param, true);
                break;
            case 4:
                Function.createBlocks(x + 2 * 2, y, param, false);
                break;

            case 5:
                Function.explosionNearlyBlock(x, y, 0, -1, param);
                break;
            case 6:
                Function.explosionNearlyBlock(x, y, -1, 0, param);
                break;
            case 7:
                Function.explosionNearlyBlock(x, y, 0, 1, param);
                break;
            case 8:
                Function.explosionNearlyBlock(x, y, 1, 0, param);
                break;

            case 9:
                Function.activateSpawnPoint(param);
                break;
            case 10:
                Function.disactivateSpawnPoint(param);
                break;
            case 11:
                Function.switchActiveModeSpawnPoint(param);
                break;

            case 15:
                Function.activateSpawnPointN(this, x, y, param);
                break;
            case 16:
                Function.disactivateSpawnPointN(this, x, y, param);
                break;
            case 17:
                Function.switchActiveModeSpawnPointN(x, y, param);
                break;

            case 12:
            case 18:
                Function.invokeAirplane(x, y, param);
                break;
            case 19:
                Function.makeExplosion(x, y, param);
                break;

            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
                Function.showToast(type - 20);
                break;

            case 31:
                Function.activateTrigger(this, param);
                break;
            case 32:
                Function.activateTriggersN(this, x, y, param);
                break;

            case 20:
                Function.makeWin(this, param);
                break;

            case 33:
                Function.enableTrigger(param);
                break;
            case 34:
                Function.disableTrigger(param);
                break;
            case 35:
                Function.switchModeTrigger(param);
                break;

            case 36:
                Function.enableTriggersN(x, y, param);
                break;
            case 37:
                Function.disableTriggersN(x, y, param);
                break;
            case 38:
                Function.switchModeTriggersN(x, y, param);
                break;

            case 41:
                break;
            case 42:
                break;
            case 43:
                break;

            case 46:
                break;

            case 72:
                break;
        }
    }

    boolean isClick = false;

    private void onClick() {
        if (sensitivity == 0) return;

        Tank tank = GameStage.getInstance().world_tank[(int) x][(int) y];
        if (tank != null && !isClick)
            if ((tank instanceof TankUser) && byUser ||
                    (tank.fraction == ObjectVariables.tank_enemy) && byEnemy ||
                    (tank.fraction == ObjectVariables.tank_ally) && byAlly) {
                actived();
                isClick = true;
                return;
            }

        if (tank == null) isClick = false;

        if (tank == null && byBullet) {
            Bullet bulletEnemy = GameStage.getInstance().world_bullets_enemy[x][y];
            if (bulletEnemy != null && !isClick) {
                actived();
                isClick = true;
                return;
            }

            Bullet bulletUnity = GameStage.getInstance().world_bullets_unity[x][y];
            if (bulletUnity != null && !isClick) {
                actived();
                isClick = true;
                return;
            }

            if (bulletEnemy == null && bulletUnity == null) isClick = false;
        }
    }

    public class Activation implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
            }
            active = true;
            running = false;
        }
    }

    public static class Function {
        public static void createBlocks(int x, int y, int param, boolean horizontal) {
            int type = param;
            boolean killer = false;
            boolean single = false;

            String prm = Integer.toBinaryString(param);
            try {
                killer = '1' == prm.charAt(prm.length() - 5);
                if (killer) type -= 16;
                single = '1' == prm.charAt(prm.length() - 6);
                if (single) type -= 32;
            } catch (Exception e) {
            }

            if (single) {
                Block wall = createBlock(x, y, type);
                if (wall != null)
                    MainTerrain.getCurrentTerrain().walls.addActor(wall);
                if (killer) {
                    Tank tank = GameStage.getInstance().mainTerrain.hashTanks.get(GameStage.getInstance().world_block[x][y]);
                    if (tank != null) tank.destroyTank(Float.MAX_VALUE);
                }
            } else {
                MainTerrain.getCurrentTerrain().walls.addActor(createBlock(x, y, type));
                try {
                    int posx = x;
                    int posy = y;
                    while (true) {
                        if (horizontal)
                            posx += 2;
                        else
                            posy += 2;

                        if (GameStage.getInstance().world_physic_block[posx][posy] != null) break;
                        MainTerrain.getCurrentTerrain().walls.addActor(createBlock(posx, posy, type));
                        if (killer) {
                            Tank tank = GameStage.getInstance().mainTerrain.hashTanks.get(GameStage.getInstance().world_block[posx][posy]);
                            if (tank != null) tank.destroyTank(Float.MAX_VALUE);
                        }
                    }
                } catch (Exception e) {
                }

                try {
                    int posx = x;
                    int posy = y;
                    while (true) {
                        if (horizontal)
                            posx -= 2;
                        else
                            posy -= 2;

                        if (GameStage.getInstance().world_physic_block[posx][posy] != null) break;
                        MainTerrain.getCurrentTerrain().walls.addActor(createBlock(posx, posy, type));
                        if (killer) {
                            Tank tank = GameStage.getInstance().mainTerrain.hashTanks.get(GameStage.getInstance().world_block[posx][posy]);
                            if (tank != null) tank.destroyTank(Float.MAX_VALUE);
                        }
                    }
                } catch (Exception e) {
                }
            }

        }

        private static Block createBlock(int x, int y, int type) {
            switch (type) {
                case 1:
                case 8:
                case 9:
                case 10:
                    return new StoneWall(x, y);
                case 2:
                    return new Concrete1Wall(x, y);
                case 3:
                    return new Concrete3Wall(x, y);
                case 4:
                    return new BlueIronWall(x, y);
                case 5:
                    return new OrangeIronWall(x, y);
                case 6:
                    return new Cactus(x, y);
                case 7:
                    return new SmallTree(x, y);
                case 11:
                    return new Spike(x, y);
            }
            return null;
        }

        public static void explosionNearlyBlock(final int x, final int y, final int dx, final int dy, final int param) {
            int bx = x, by = y;
            try {
                //Thread.sleep(2400);
                while (true) {
                    bx += dx * 2;
                    by += dy * 2;

                    final int bxx = bx;
                    final int byy = by;

                    Block block = GameStage.getInstance().world_physic_block[bx][by];
                    if (block != null) {
                        //block.destroyWallNow();
                        new Thread(new Runnable() {
                            final int hashCode = MainTerrain.getCurrentTerrain().hashCode();

                            @Override
                            public void run() {
                                for (int i = 0; i < 10; i++) {
                                    if (hashCode != MainTerrain.getCurrentTerrain().hashCode()) return;

                                    final Block block = GameStage.getInstance().world_physic_block[bxx][byy];
                                    if (block != null)
                                        block.destroyWallNow();
                                    try {
                                        Thread.sleep(100);
                                    } catch (InterruptedException e) {
                                    }
                                }
                            }
                        }).start();
                        int r = 0;
                        if (param >= 1 && param <= 4) r = 3;
                        if (param >= 5 && param <= 15) r = 5;
                        if (param >= 16 && param <= 63) r = 7;
                        if (r > 0)
                            MainTerrain.getCurrentTerrain().explosions.addActor(new BiggestExplosion(bx, by, 2 * (r + 1), param));
                        return;
                    }
                }
            } catch (Exception e) {
            }
        }

        public static void activateSpawnPoint(int param) {
            int num = param;
            String bin = Integer.toBinaryString(param);
            boolean allies = false;
            try {
                allies = '1' == bin.charAt(bin.length() - 8);
                if (allies) num -= 128;
            } catch (Exception e) {
            }
            if (num == 0) {
                if (allies)
                    for (int key : HangarUnity.register.keySet()) HangarUnity.register.get(key).activity = 1;
                else
                    for (int key : HangarEnemy.register.keySet()) HangarEnemy.register.get(key).activity = 1;
            } else {
                if (allies)
                    HangarUnity.register.get(num).activity = 1;
                else
                    HangarEnemy.register.get(num).activity = 1;
            }
        }

        public static void disactivateSpawnPoint(int param) {
            int num = param;
            String bin = Integer.toBinaryString(param);
            boolean allies = false;
            try {
                allies = '1' == bin.charAt(bin.length() - 8);
                if (allies) num -= 128;
            } catch (Exception e) {
            }
            if (num == 0) {
                if (allies)
                    for (int key : HangarUnity.register.keySet()) HangarUnity.register.get(key).activity = 0;
                else
                    for (int key : HangarEnemy.register.keySet()) HangarEnemy.register.get(key).activity = 0;
            } else {
                if (allies)
                    HangarUnity.register.get(num).activity = 0;
                else
                    HangarEnemy.register.get(num).activity = 0;
            }
        }

        public static void switchActiveModeSpawnPoint(int param) {
            int num = param;
            String bin = Integer.toBinaryString(param);
            boolean allies = false;
            try {
                allies = '1' == bin.charAt(bin.length() - 8);
                if (allies) num -= 128;
            } catch (Exception e) {
            }
            if (num == 0) {
                if (allies)
                    for (int key : HangarUnity.register.keySet())
                        HangarUnity.register.get(key).activity = HangarUnity.register.get(num).activity == 1 ? 0 : 1;
                else
                    for (int key : HangarEnemy.register.keySet())
                        HangarEnemy.register.get(key).activity = HangarEnemy.register.get(num).activity == 1 ? 0 : 1;
            } else {
                if (allies)
                    HangarUnity.register.get(num).activity = HangarUnity.register.get(num).activity == 1 ? 0 : 1;
                else
                    HangarEnemy.register.get(num).activity = HangarEnemy.register.get(num).activity == 1 ? 0 : 1;
            }
        }

        public static void activateSpawnPointN(Trigger trigger, int x, int y, int param) {
            int len = param;
            int count = 0;
            String bin = Integer.toBinaryString(param);
            boolean switchn = false;
            try {
                switchn = '1' == bin.charAt(bin.length() - 8);
                if (switchn) len -= 128;
            } catch (Exception e) {
            }
            len *= 2;
            Rectangle rect = new Rectangle();
            rect.setSize(len * 2, len * 2);
            rect.setCenter(x, y);

            //len *= len * 4;
            for (int key : HangarUnity.register.keySet()) {
                HangarUnity angar = HangarUnity.register.get(key);
                if (rect.contains(angar.getX(Align.center), angar.getY(Align.center))) {
                    if (angar.activity == 0) count++;
                    angar.activity = 1;
                }
            }
            for (int key : HangarEnemy.register.keySet()) {
                HangarEnemy angar = HangarEnemy.register.get(key);
                if (rect.contains(angar.getX(Align.center), angar.getY(Align.center))) {
                    if (angar.activity == 0) count++;
                    angar.activity = 1;
                }
            }

            if (switchn && count == 0) {
                trigger.enabled = true;
            }
        }

        public static void disactivateSpawnPointN(Trigger trigger, int x, int y, int param) {
            int len = param;
            int count = 0;
            String bin = Integer.toBinaryString(param);
            boolean switchn = false;
            try {
                switchn = '1' == bin.charAt(bin.length() - 8);
                if (switchn) len -= 128;
            } catch (Exception e) {
            }
            len *= 2;
            Rectangle rect = new Rectangle();
            rect.setSize(len * 2, len * 2);
            rect.setCenter(x, y);
            //len *= 8 * len;
            for (int key : HangarUnity.register.keySet()) {
                HangarUnity angar = HangarUnity.register.get(key);
                if (rect.contains(angar.getX(Align.center), angar.getY(Align.center))) {
                    if (angar.activity == 1) count++;
                    angar.activity = 0;
                }
            }
            for (int key : HangarEnemy.register.keySet()) {
                HangarEnemy angar = HangarEnemy.register.get(key);
                if (rect.contains(angar.getX(Align.center), angar.getY(Align.center))) {
                    if (angar.activity == 1) count++;
                    angar.activity = 0;
                }
            }

            if (switchn && count == 0) {
                trigger.enabled = true;
            }
        }

        public static void switchActiveModeSpawnPointN(int x, int y, int param) {
            int len = param;
            String bin = Integer.toBinaryString(param);
            boolean switchn = false;
            try {
                switchn = '1' == bin.charAt(bin.length() - 8);
                if (switchn) len -= 128;
            } catch (Exception e) {
            }
            len *= 2;
            Rectangle rect = new Rectangle();
            rect.setSize(len * 2, len * 2);
            rect.setCenter(x, y);
            //len *= 4 * len;
            for (int key : HangarUnity.register.keySet()) {
                HangarUnity angar = HangarUnity.register.get(key);
                if (rect.contains(angar.getX(Align.center), angar.getY(Align.center))) angar.activity = angar.activity == 1 ? 0 : 1;
            }
            for (int key : HangarEnemy.register.keySet()) {
                HangarEnemy angar = HangarEnemy.register.get(key);
                if (rect.contains(angar.getX(Align.center), angar.getY(Align.center))) angar.activity = angar.activity == 1 ? 0 : 1;
            }
        }

        public static void invokeAirplane(int x, int y, int param) {
            int x0 = x;
            int y0 = y;
            int damage = param / 4;

            String bin = Integer.toBinaryString(param);

            //if (bin.length() > Integer.toBinaryString(3).length())
            for (int i = 3; i > 0; i--) {
                if (bin.endsWith(Integer.toBinaryString(i))) {
                    int index = (i - 1) * 2;
                    x0 = MainTerrain.getCurrentTerrain().getParameters().getKey(27 + index) * 2;
                    y0 = GameStage.getInstance().world_height - MainTerrain.getCurrentTerrain().getParameters().getKey(28 + index) * 2;
                    damage = (param - i) / 4;
                    break;
                }
            }

            if (damage == 0) damage = 18;

            Airplane airplane = new Airplane(x0, y0, 3, damage);
            MainTerrain.getCurrentTerrain().decor.addActor(airplane);
        }

        public static void makeExplosion(int x, int y, int param) {
            int x0 = x;
            int y0 = y;
            int damage = param / 4;

            String bin = Integer.toBinaryString(param);

            //if (bin.length() > Integer.toBinaryString(3).length())
            for (int i = 3; i > 0; i--) {
                if (bin.endsWith(Integer.toBinaryString(i))) {
                    int index = (i - 1) * 2;
                    x0 = MainTerrain.getCurrentTerrain().getParameters().getKey(27 + index) * 2;
                    y0 = GameStage.getInstance().world_height - MainTerrain.getCurrentTerrain().getParameters().getKey(28 + index) * 2;
                    damage = (param - i) / 4;
                    break;
                }
            }

            if (damage == 0) damage = 12;

            BiggestExplosion expl = new BiggestExplosion(x0, y0, ((damage <= 16) ? 6 : 8) * 2, damage);
            MainTerrain.getCurrentTerrain().explosions.addActor(expl);
        }

        public static void makeWin(Trigger trigger, int param) {
            int msg = param;
            boolean byTrigger = msg - 128 >= 0;
            if (byTrigger) {
                msg -= 128;
                if (MissionCompleted.isMissionCompleted) {
                    MissionCompleted.isShow = false;
                    MissionCompleted.show = true;
                    if (msg == 0)
                        MissionCompleted.show(MissionCompleted.MISSION_COMPLETED);
                    else
                        MissionCompleted.show(MissionCompleted.MISSION_COMPLETED, MainTerrain.getCurrentTerrain().getBriefings().getBriefing(msg - 1));
                    trigger.enabled = trigger.multiEnabled;
                } else trigger.enabled = true;
            } else if (msg == 0)
                MissionCompleted.show(MissionCompleted.MISSION_COMPLETED);
            else
                MissionCompleted.show(MissionCompleted.MISSION_COMPLETED, MainTerrain.getCurrentTerrain().getBriefings().getBriefing(msg - 1));
            ;
        }

        public static void showToast(int param) {
            PanelStage.getInstance().addToast(MainTerrain.getCurrentTerrain().getBriefings().getBriefing(param - 1));
        }

        public static void activateTrigger(final Trigger trigger, int param) {
            int index = param;
            int count = 1;

            String bin = Integer.toBinaryString(param);

            if (bin.length() > Integer.toBinaryString(30).length())
                for (int i = 30; i > 0; i--) {
                    if (bin.endsWith(Integer.toBinaryString(i))) {
                        index = i;
                        count = (param - i) / 32;
                        if (count == 1) count = -1;
                        break;
                    }
                }

            final int in = index;
            final int c = count;

            new Thread(new Runnable() {
                final int hashCode = MainTerrain.getCurrentTerrain().hashCode();

                @Override
                public void run() {
                    trigger.enabled = false;
                    for (int i = 0; i < c || c == -1; i++) {
                        if (hashCode != MainTerrain.getCurrentTerrain().hashCode()) return;

                        try {
                            Trigger.register.get(in).actived();
                        } catch (Exception e) {
                            break;
                        }

                        if (c != 1)
                            try {
                                Thread.sleep(trigger.delay);
                            } catch (InterruptedException e) {
                            }
                    }
                    trigger.enabled = trigger.multiEnabled;
                }
            }).start();
        }

        public static void activateTriggersN(final Trigger trigger, final int x, final int y, int param) {
            int len = param;
            int count = 1;

            String bin = Integer.toBinaryString(param);

            if (bin.length() > Integer.toBinaryString(31).length())
                for (int i = 31; i > 0; i--) {
                    if (bin.endsWith(Integer.toBinaryString(i))) {
                        len = i;
                        count = (param - i) / 32;
                        if (count == 1) count = -1;
                        break;
                    }
                }
            len *= 2;
            final Rectangle rect = new Rectangle();
            rect.setSize(len * 2, len * 2);
            rect.setCenter(x, y);

            final int l = len;
            final int c = count;

            new Thread(new Runnable() {
                final int hashCode = MainTerrain.getCurrentTerrain().hashCode();

                @Override
                public void run() {
                    trigger.enabled = false;
                    for (int i = 0; i < c || c == -1; i++) {
                        if (hashCode != MainTerrain.getCurrentTerrain().hashCode()) return;

                        for (int key : Trigger.register.keySet()) {
                            Trigger t = Trigger.register.get(key);

                            if (t.equals(trigger)) continue;

                            if (rect.contains(t.getX(Align.center), t.getY(Align.center))) t.actived();
                        }
                        if (c != 1)
                            try {
                                Thread.sleep(trigger.delay);
                            } catch (InterruptedException e) {
                            }
                    }
                    trigger.enabled = trigger.multiEnabled;
                }
            }).start();
        }

        public static void enableTrigger(int param) {
            int num = param;
            Trigger.register.get(num).enabled = true;
        }

        public static void disableTrigger(int param) {
            int num = param;
            Trigger.register.get(num).enabled = false;
        }

        public static void switchModeTrigger(int param) {
            int num = param;
            Trigger.register.get(num).enabled = !Trigger.register.get(num - 1).enabled;
        }

        public static void enableTriggersN(int x0, int y0, int param) {
            int len = param * 2;
            Rectangle rect = new Rectangle();
            rect.setSize(len * 2, len * 2);
            rect.setCenter(x0, y0);
            for (int key : Trigger.register.keySet()) {
                Trigger trigger = Trigger.register.get(key);
                if (rect.contains(trigger.getX(Align.center), trigger.getY(Align.center))) trigger.enabled = true;
            }
        }

        public static void disableTriggersN(int x0, int y0, int param) {
            int len = param * 2;
            Rectangle rect = new Rectangle();
            rect.setSize(len * 2, len * 2);
            rect.setCenter(x0, y0);
            for (int key : Trigger.register.keySet()) {
                Trigger trigger = Trigger.register.get(key);
                if (rect.contains(trigger.getX(Align.center), trigger.getY(Align.center))) trigger.enabled = false;
            }
        }

        public static void switchModeTriggersN(int x0, int y0, int param) {
            int len = param * 2;
            Rectangle rect = new Rectangle();
            rect.setSize(len * 2, len * 2);
            rect.setCenter(x0, y0);
            for (int key : Trigger.register.keySet()) {
                Trigger trigger = Trigger.register.get(key);
                if (rect.contains(trigger.getX(Align.center), trigger.getY(Align.center)))
                    trigger.enabled = !trigger.enabled;
            }
        }
    }
}
