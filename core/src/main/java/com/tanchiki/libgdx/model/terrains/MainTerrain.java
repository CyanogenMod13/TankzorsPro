package com.tanchiki.libgdx.model.terrains;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.tanchiki.libgdx.model.bonus.*;
import com.tanchiki.libgdx.model.buildes.*;
import com.tanchiki.libgdx.model.bullets.Bullet;
import com.tanchiki.libgdx.model.mine.Mine;
import com.tanchiki.libgdx.model.mine.MineEnemy1;
import com.tanchiki.libgdx.model.mine.MineUnity1;
import com.tanchiki.libgdx.model.tanks.Tank;
import com.tanchiki.libgdx.model.tanks.Turret;
import com.tanchiki.libgdx.model.ui.MissionCompleted;
import com.tanchiki.libgdx.stage.GameStage;
import com.tanchiki.libgdx.stage.PanelStage;
import com.tanchiki.libgdx.util.*;
import com.tanchiki.libgdx.util.astar.AStar;
import com.tanchiki.libgdx.util.astar.AStarNode;

import java.util.HashMap;

public class MainTerrain extends Group implements Disposable {
    public GameStage GameStage;
    public final Group ground;
    public final Group tanks;
    public final Group walls;
    public final Group builds;
    public final Group tanks_enemy;
    public final Group tanks_unity;
    public final Group root;
    public final Group decor;
    public final Group road;
    public final Group bonus;
    public final Group track;
    public final Group decorGround;
    public final Group mines;
    public final Group bullet;
    public final Group health;
    public final Group explosions;
    public final Group smoke;
    public final Group overlays;
    public final Group ring;
    public final Group trigger;
    public MainTerrain.Mission mission = null;
    public Rectangle rect;

    public final HashMap<Integer, Tank> hashTanks = new HashMap<>();

    public AStar AStar;
    private static MainTerrain currentTerrain = null;

    public static MainTerrain getCurrentTerrain() {
        return currentTerrain;
    }

    Parameters param;
    Briefings briefings;

    Timer timer = null;

    public int coin, star;
    public float missionTime;
    public int countFire;
    public int damageUser;
    public float distance;

    public MainTerrain() {
        currentTerrain = this;
        GameStage = GameStage.getInstance();
        ground = new Group();
        tanks = new Group();
        walls = new Group();
        builds = new Group();
        decor = new Group();
        root = new Group();
        road = new Group();
        bonus = new Group();
        track = new Group();
        decorGround = new Group();
        mines = new Group();
        bullet = new Group();
        health = new Group();
        explosions = new Group();
        smoke = new Group();
        overlays = new Group();
        ring = new Group();
        trigger = new Group();

        tanks_unity = new TanksUnity();
        tanks_enemy = new TanksEnemy();

        tanks.addActor(tanks_unity);
        tanks.addActor(tanks_enemy);
        root.addActor(ground);
        root.addActor(decorGround);
        root.addActor(road);
        root.addActor(mines);
        root.addActor(bonus);
        root.addActor(trigger);
        root.addActor(track);
        root.addActor(walls);
        root.addActor(overlays);
        root.addActor(builds);
        root.addActor(ring);
        root.addActor(tanks);
        root.addActor(smoke);
        root.addActor(explosions);
        root.addActor(bullet);
        if (!Settings.edit_map_mode)
            root.addActor(health);
        root.addActor(decor);

        GameStage.tankUser = null;

        if (Settings.start_game) {
            PanelStage.getInstance().toasts.clear();
            PanelStage.getInstance().addToast("Миссия " + (com.tanchiki.libgdx.stage.GameStage.next_level + 1));
            //MusicLoader.getInstance().getTrack((com.tanchiki.libgdx.stage.GameStage.next_level % 5) + 1).play();
        } else {
            MusicLoader.getInstance().getIntro().play();
        }

        addActor(root);
    }

    @Override
    public void act(float delta) {
        missionTime += delta;
        super.act(delta);
    }

    public void loadMap(String name) throws Exception {
        MapBinReader map = MapsDatabase.getInstance().getMap(name);
        loadMap(map);
    }

    public Briefings getBriefings() {
        return briefings;
    }

    public Parameters getParameters() {
        return param;
    }

    public void loadMap(MapBinReader map) throws Exception {
        System.out.println("MAP " + map.getName());

        ObjectVariables.size_enemies = 0;
        ObjectVariables.size_allies = 0;

        loadBinaryMap(map);
        ObjectVariables.max_tanks_enemy = param.getKey(1);
        ObjectVariables.max_tanks_ally = param.getKey(10);
        ObjectVariables.level_difficulty = param.getKey(24);

        int[] tanks_type_enemy = new int[8];
        for (int i = 0; i < 8; i++) {
            tanks_type_enemy[i] = param.getKey(i + 2);
            ObjectVariables.all_size_enemies += param.getKey(i + 2);
        }
        ObjectVariables.tanks_type_enemy = tanks_type_enemy;

        int[] tanks_type_ally = new int[8];
        for (int i = 0; i < 8; i++) {
            tanks_type_ally[i] = param.getKey(i + 11);
            ObjectVariables.all_size_allies += param.getKey(i + 11);
        }
        ObjectVariables.tanks_type_ally = tanks_type_ally;
        if (Settings.start_game) {
            mission = new MainTerrain.Mission(param.getKey(26));
            root.addActor(mission);
        }
    }

    private void setSize(float[] pointXY) {
        GameStage.world_nodes = new int[(int) pointXY[0]][(int) pointXY[1]];
        GameStage.world_block = new int[(int) pointXY[0]][(int) pointXY[1]];
        GameStage.world_physic_block = new Block[(int) pointXY[0]][(int) pointXY[1]];
        GameStage.world_obj = new Object[(int) pointXY[0]][(int) pointXY[1]];
        GameStage.world_tank = new Tank[(int) pointXY[0]][(int) pointXY[1]];
        GameStage.world_mines = new Actor[(int) pointXY[0]][(int) pointXY[1]];
        GameStage.world_wight = (int) pointXY[0];
        GameStage.world_height = (int) pointXY[1];
        GameStage.worldBuilds = new ObjBuild[(int) pointXY[0]][(int) pointXY[1]];
        GameStage.world_bonus = new Actor[(int) pointXY[0]][(int) pointXY[1]];
        GameStage.world_bullets_enemy = new Bullet[(int) pointXY[0]][(int) pointXY[1]];
        GameStage.world_bullets_unity = new Bullet[(int) pointXY[0]][(int) pointXY[1]];
        GameStage.world_trigger = new Trigger[(int) pointXY[0]][(int) pointXY[1]];
        this.rect = new Rectangle(-1, -1, GameStage.world_wight, GameStage.world_height);
        GameStage.cam.position.set(pointXY[0] / 2f - 1f, pointXY[1] / 2f - 1, 0);
        AStar = new AStar(GameStage.world_nodes, new AStar.AStarListener() {
            @Override
            public float h(AStarNode end, AStarNode current) {
                return (end.x - current.x) * (end.x - current.x) + (end.y - current.y) * (end.y - current.y) + ((current.parent != null) ? ((current.code == current.parent.code) ? 0 : 100) : 0);
            }

            @Override
            public float g(AStarNode begin, AStarNode current) {
                return (begin.x - current.x) * (begin.x - current.x) + (begin.y - current.y) * (begin.y - current.y);
            }
        });
    }

    public void loadBinaryMap(final MapBinReader input) throws Exception {
        System.out.println("Binary map reading started...");
        param = new Parameters(input.getParametersPart());

        briefings = new Briefings(input.getHints());
        final int width = input.getSizeMapPart()[0];
        final int height = input.getSizeMapPart()[1];
        setSize(new float[]{width * 2, height * 2});

        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++) {
                ground.addActor(new Grass(j * 2, i * 2));
            }

        int idx = 0;
        for (int i = height - 1; i >= 0; i--) {
            for (int j = 0; j < width; j++) {
                int code = input.getMapDataPart()[idx++] + 1;
                createTile(code, j, i);
            }
        }

        for (Actor a : ground.getChildren())
            if (a instanceof Sand) {
                Sand s = (Sand) a;
                s.postInit();
            }
        for (Actor a : road.getChildren())
            if (a instanceof Road) {
                Road s = (Road) a;
                s.init();
            }
        System.out.println("Done!");
    }

    public void createTile(int code, int j, int i) {
        if (code == 167 || code == 207 || code == 247) {
            bonus.addActor(new Box(j * 2, i * 2, 1));
        }
        if (code == 168 || code == 208 || code == 248) {
            bonus.addActor(new Box(j * 2, i * 2, 2));
        }
        if (code == 169 || code == 209 || code == 249) {
            bonus.addActor(new Box(j * 2, i * 2, 3));
        }
        if (code == 161 || code == 201 || code == 241) {
            bonus.addActor(new Speeder(j * 2, i * 2));
        }
        if (code == 162 || code == 202 || code == 242) {
            bonus.addActor(new Liver(j * 2, i * 2));
        }
        if (code == 163 || code == 203 || code == 243) {
            bonus.addActor(new com.tanchiki.libgdx.model.bonus.Timer(j * 2, i * 2));
        }
        if (code == 164 || code == 204 || code == 244) {
            bonus.addActor(new Fixer(j * 2, i * 2));
        }
        if (code == 165 || code == 205 || code == 245) {
            bonus.addActor(new Starer(j * 2, i * 2));
        }
        if (code == 166 || code == 206 || code == 246) {
            bonus.addActor(new Moner(j * 2, i * 2));
        }
        if (code == 99) {
            decor.addActor(new Palm(j * 2, i * 2));
        }
        if (code >= 151 && code <= 159 || code >= 191 && code <= 199 || code >= 231 && code <= 239) {
            tanks_enemy.addActor(new Turret(j * 2, i * 2, ObjectVariables.tank_enemy));
        }
        if (code == 160 || code == 200 || code == 240) {
            tanks_unity.addActor(new Turret(j * 2, i * 2, ObjectVariables.tank_ally));
        }
        if (code == 102) {
            trigger.addActor(new Trigger(j * 2, i * 2));
            ground.addActor(new Grass(j * 2, i * 2));
        }
        if (code == 107) {
            trigger.addActor(new Trigger(j * 2, i * 2));
            ground.addActor(new Plate(j * 2, i * 2));
        }
        if (code == 112) {
            trigger.addActor(new Trigger(j * 2, i * 2));
            ground.addActor(new Sand(j * 2, i * 2));
        }
        if (code >= 1 && code <= 10 || code >= 15 && code <= 23 || code >= 53 && code <= 55 || code >= 161 && code <= 169) {
            ground.addActor(new Grass(j * 2, i * 2));
        }
        if (code >= 11 && code <= 14 || code >= 56 && code <= 90 || code >= 241 && code <= 249) {
            ground.addActor(new Sand(j * 2, i * 2));
        }
        if (code >= 24 && code <= 33) {
            road.addActor(new Road(j * 2, i * 2));
        }
        if (code >= 34 && code <= 52 || code >= 201 && code <= 209) {
            ground.addActor(new Plate(j * 2, i * 2));
        }
        if (code == 91) {
            builds.addActor(new ReactorCore.ReactorCoreUnity(j * 2, i * 2));
        }
        if (code == 92) {
            builds.addActor(new ReactorCore.ReactorCoreEnemy(j * 2, i * 2));
        }
        if (code == 93) {
            builds.addActor(new Radar.RadarUnity(j * 2, i * 2));
        }
        if (code == 94) {
            builds.addActor(new Radar.RadarEnemy(j * 2, i * 2));
        }
        if (code >= 95 && code <= 96) {
            road.addActor(new Road(j * 2, i * 2));
            mines.addActor(new MineEnemy1(j * 2, i * 2));
        }
        if (code >= 97 && code <= 98) {
            road.addActor(new Road(j * 2, i * 2));
            mines.addActor(new MineUnity1(j * 2, i * 2));
        }
        if (code == 100) {
            ground.addActor(new Grass(j * 2, i * 2));
            builds.addActor(new HangarUnity(j * 2, i * 2));
        }
        if (code == 105) {
            ground.addActor(new Plate(j * 2, i * 2));
            builds.addActor(new HangarUnity(j * 2, i * 2));
        }
        if (code == 110) {
            ground.addActor(new Sand(j * 2, i * 2));
            builds.addActor(new HangarUnity(j * 2, i * 2));
        }
        if (code == 101) {
            ground.addActor(new Grass(j * 2, i * 2));
            builds.addActor(new HangarEnemy(j * 2, i * 2));
        }
        if (code == 106) {
            ground.addActor(new Plate(j * 2, i * 2));
            builds.addActor(new HangarEnemy(j * 2, i * 2));
        }
        if (code == 111) {
            ground.addActor(new Sand(j * 2, i * 2));
            builds.addActor(new HangarEnemy(j * 2, i * 2));
        }
        if (code == 115) {
            ground.addActor(new Grass(j * 2, i * 2));
            builds.addActor(new YellowFlag(j * 2, i * 2));
        }
        if (code == 125) {
            ground.addActor(new Sand(j * 2, i * 2));
            builds.addActor(new YellowFlag(j * 2, i * 2));
        }
        if (code == 120) {
            ground.addActor(new Plate(j * 2, i * 2));
            builds.addActor(new YellowFlag(j * 2, i * 2));
        }
        if (code == 116) {
            ground.addActor(new Grass(j * 2, i * 2));
            builds.addActor(new BlueFlag(j * 2, i * 2));
        }
        if (code == 121) {
            ground.addActor(new Plate(j * 2, i * 2));
            builds.addActor(new BlueFlag(j * 2, i * 2));
        }
        if (code == 126) {
            ground.addActor(new Sand(j * 2, i * 2));
            builds.addActor(new BlueFlag(j * 2, i * 2));
        }
        if (code == 117) {
            ground.addActor(new Grass(j * 2, i * 2));
            builds.addActor(new RedFlag(j * 2, i * 2));
        }
        if (code == 122) {
            ground.addActor(new Plate(j * 2, i * 2));
            builds.addActor(new RedFlag(j * 2, i * 2));
        }
        if (code == 127) {
            ground.addActor(new Sand(j * 2, i * 2));
            builds.addActor(new RedFlag(j * 2, i * 2));
        }
        if (code >= 130 && code <= 137) {
            ground.addActor(new Grass(j * 2, i * 2));
            walls.addActor(new StoneWall(j * 2, i * 2));
        }
        if (code == 138) {
            ground.addActor(new Grass(j * 2, i * 2));
            walls.addActor(new Concrete1Wall(j * 2, i * 2));
        }
        if (code == 139) {
            ground.addActor(new Grass(j * 2, i * 2));
            walls.addActor(new Concrete2Wall(j * 2, i * 2));
        }
        if (code == 140) {
            ground.addActor(new Grass(j * 2, i * 2));
            walls.addActor(new Concrete3Wall(j * 2, i * 2));
        }
        if (code == 141) {
            ground.addActor(new Grass(j * 2, i * 2));
            walls.addActor(new BlueIronWall(j * 2, i * 2));
        }
        if (code == 142) {
            ground.addActor(new Grass(j * 2, i * 2));
            walls.addActor(new OrangeIronWall(j * 2, i * 2));
        }
        if (code >= 143 && code <= 144) {
            ground.addActor(new Grass(j * 2, i * 2));
            walls.addActor(new Cactus(j * 2, i * 2));
        }
        if (code == 145) {
            ground.addActor(new Grass(j * 2, i * 2));
            walls.addActor(new SmallTree(j * 2, i * 2));
        }
        if (code >= 146 && code <= 148) {
            ground.addActor(new Grass(j * 2, i * 2));
            walls.addActor(new Spike(j * 2, i * 2));
        }
        if (code == 149) {
            ground.addActor(new Grass(j * 2, i * 2));
            mines.addActor(new MineEnemy1(j * 2, i * 2));
        }
        if (code == 150) {
            ground.addActor(new Grass(j * 2, i * 2));
            mines.addActor(new MineUnity1(j * 2, i * 2));
        }

        if (code >= 170 && code <= 177) {
            ground.addActor(new Plate(j * 2, i * 2));
            walls.addActor(new StoneWall(j * 2, i * 2));
        }
        if (code == 178) {
            ground.addActor(new Plate(j * 2, i * 2));
            walls.addActor(new Concrete1Wall(j * 2, i * 2));
        }
        if (code == 179) {
            ground.addActor(new Plate(j * 2, i * 2));
            walls.addActor(new Concrete2Wall(j * 2, i * 2));
        }
        if (code == 180) {
            ground.addActor(new Plate(j * 2, i * 2));
            walls.addActor(new Concrete3Wall(j * 2, i * 2));
        }
        if (code == 181) {
            ground.addActor(new Plate(j * 2, i * 2));
            walls.addActor(new BlueIronWall(j * 2, i * 2));
        }
        if (code == 182) {
            ground.addActor(new Plate(j * 2, i * 2));
            walls.addActor(new OrangeIronWall(j * 2, i * 2));
        }
        if (code >= 183 && code <= 184) {
            ground.addActor(new Plate(j * 2, i * 2));
            walls.addActor(new Cactus(j * 2, i * 2));
        }
        if (code == 185) {
            ground.addActor(new Plate(j * 2, i * 2));
            walls.addActor(new SmallTree(j * 2, i * 2));
        }
        if (code >= 186 && code <= 188) {
            ground.addActor(new Plate(j * 2, i * 2));
            walls.addActor(new Spike(j * 2, i * 2));
        }
        if (code == 189) {
            ground.addActor(new Plate(j * 2, i * 2));
            mines.addActor(new MineEnemy1(j * 2, i * 2));
        }
        if (code == 190) {
            ground.addActor(new Plate(j * 2, i * 2));
            mines.addActor(new MineUnity1(j * 2, i * 2));
        }

        if (code >= 210 && code <= 217) {
            ground.addActor(new Sand(j * 2, i * 2));
            walls.addActor(new StoneWall(j * 2, i * 2));
        }
        if (code == 218) {
            ground.addActor(new Sand(j * 2, i * 2));
            walls.addActor(new Concrete1Wall(j * 2, i * 2));
        }
        if (code == 219) {
            ground.addActor(new Sand(j * 2, i * 2));
            walls.addActor(new Concrete2Wall(j * 2, i * 2));
        }
        if (code == 220) {
            ground.addActor(new Sand(j * 2, i * 2));
            walls.addActor(new Concrete3Wall(j * 2, i * 2));
        }
        if (code == 221) {
            ground.addActor(new Sand(j * 2, i * 2));
            walls.addActor(new BlueIronWall(j * 2, i * 2));
        }
        if (code == 222) {
            ground.addActor(new Sand(j * 2, i * 2));
            walls.addActor(new OrangeIronWall(j * 2, i * 2));
        }
        if (code >= 223 && code <= 224) {
            ground.addActor(new Sand(j * 2, i * 2));
            walls.addActor(new Cactus(j * 2, i * 2));
        }
        if (code == 225) {
            ground.addActor(new Sand(j * 2, i * 2));
            walls.addActor(new SmallTree(j * 2, i * 2));
        }
        if (code >= 226 && code <= 228) {
            ground.addActor(new Sand(j * 2, i * 2));
            walls.addActor(new Spike(j * 2, i * 2));
        }
        if (code == 229) {
            ground.addActor(new Sand(j * 2, i * 2));
            mines.addActor(new MineEnemy1(j * 2, i * 2));
        }
        if (code == 230) {
            ground.addActor(new Sand(j * 2, i * 2));
            mines.addActor(new MineUnity1(j * 2, i * 2));
        }
    }

    @Override
    public void dispose() {
        currentTerrain = null;
        Settings.tanktypeenemy.clear();
        Settings.tanktypeunity.clear();
        Settings.tanktypeenemyData.clear();
        Settings.tanktypeunityData.clear();
        Settings.tanksData.clear();
        missions.clear();
        remove();
    }

    public static final Array<Integer> missions = new Array<Integer>(5);

    public static class Mission extends Actor {
        public static int CODE = 0;
        int code = 0;

        public Mission(int code) {
            this.code = code;
            CODE = code;
            switch (code) {
                case 30:
                case 40:
                case 35:
                    MainTerrain.getCurrentTerrain().setTimer(1 * 60, MainTerrain.Timer.MODE.LOSE);
                    break;
                case 31:
                case 41:
                case 36:
                    MainTerrain.getCurrentTerrain().setTimer(2 * 60, MainTerrain.Timer.MODE.LOSE);
                    break;
                case 32:
                case 42:
                case 37:
                    MainTerrain.getCurrentTerrain().setTimer(3 * 60, MainTerrain.Timer.MODE.LOSE);
                    break;

                case 20:
                    MainTerrain.getCurrentTerrain().setTimer(40, MainTerrain.Timer.MODE.WIN);
                    break;
                case 50:
                case 21:
                    MainTerrain.getCurrentTerrain().setTimer(1 * 60, MainTerrain.Timer.MODE.WIN);
                    break;
                case 51:
                case 22:
                    MainTerrain.getCurrentTerrain().setTimer(2 * 60, MainTerrain.Timer.MODE.WIN);
                    break;
                case 52:
                case 23:
                    MainTerrain.getCurrentTerrain().setTimer(3 * 60, MainTerrain.Timer.MODE.WIN);
                    break;
                case 53:
                case 24:
                    MainTerrain.getCurrentTerrain().setTimer(5 * 60, MainTerrain.Timer.MODE.WIN);
                    break;
                case 54:
                    MainTerrain.getCurrentTerrain().setTimer(7 * 60, MainTerrain.Timer.MODE.WIN);
                    break;
            }
        }

        @Override
        public void act(float delta) {
            switch (code) {
                case 30:
                case 31:
                case 32:
                case 1:
                case 11:
                    if (ObjectVariables.all_size_enemies == 0) win();
                    break;

                case 12:
                    if (ObjectVariables.all_size_allies < 1) lose();
                    if (ObjectVariables.all_size_enemies == 0) win();
                    break;
                case 13:
                    if (ObjectVariables.all_size_allies < 2) lose();
                    if (ObjectVariables.all_size_enemies == 0) win();
                    break;
                case 14:
                    if (ObjectVariables.all_size_allies < 3) lose();
                    if (ObjectVariables.all_size_enemies == 0) win();
                    break;

                case 15:
                    if (ObjectVariables.all_size_allies < 1) lose();
                    break;
                case 16:
                    if (ObjectVariables.all_size_allies < 2) lose();
                    break;
                case 17:
                    if (ObjectVariables.all_size_allies < 3) lose();
                    break;

                case 50:
                case 51:
                case 52:
                case 53:
                case 54:
                    if (ObjectVariables.all_size_radar_allies == 0) lose();
                    break;
                case 55:
                    if (ObjectVariables.all_size_radar_allies == 0) lose();
                    if (ObjectVariables.all_size_enemies == 0) win();
                    break;

                case 56:
                    if (ObjectVariables.all_size_radar_enemy == 0) win();
                    break;
                case 57:
                    if (ObjectVariables.all_size_reactor_enemy == 0) win();
                    break;
                case 58:
                    if (ObjectVariables.all_size_radar_enemy == 0 && ObjectVariables.all_size_reactor_enemy == 0) win();
                    break;
                case 59:
                    if (ObjectVariables.all_size_turrets_enemy == 0) win();
                    break;
                case 60:
                    if (ObjectVariables.all_size_turrets_enemy == 0 && ObjectVariables.all_size_enemies == 0) win();
                    break;
                case 66:
                    break;
                default:
                    remove();
            }
        }

        private void win() {
            MissionCompleted.show(MissionCompleted.MISSION_COMPLETED);
            remove();
            MainTerrain.getCurrentTerrain().removeTimer();
        }

        private void lose() {
            MissionCompleted.show(MissionCompleted.MISSION_FAILED);
            remove();
            MainTerrain.getCurrentTerrain().removeTimer();
        }
    }

    public static class Timer extends Actor {
        public int time;
        public float delta;
        private MODE mode;

        public Timer(int time, MODE mode) {
            this.time = time;
            this.mode = mode;
        }

        @Override
        public void act(float delta) {
            if (this.delta >= time)
                switch (mode) {
                    case WIN:
                        win();
                        break;
                    case LOSE:
                        lose();
                        break;
                }
            this.delta += delta;
            super.act(delta);
        }

        private void win() {
            MissionCompleted.show(MissionCompleted.MISSION_COMPLETED);
            remove();
        }

        private void lose() {
            MissionCompleted.show(MissionCompleted.MISSION_FAILED);
            remove();
        }

        public enum MODE {
            WIN, LOSE;
        }
    }

    public void setTimer(int time, MainTerrain.Timer.MODE mode) {
        timer = new Timer(time, mode);
        root.addActor(timer);
    }

    public Timer getTimer() {
        return timer;
    }

    public boolean removeTimer() {
        if (timer == null) return false;

        return timer.remove();
    }

    public class TanksGroup extends Group {
    }

    public class TanksUnity extends TanksGroup {
        @Override
        public void act(float delta) {
            if (!Tank.stop_unity)
                super.act(delta);
        }
    }

    public class TanksEnemy extends TanksGroup {
        @Override
        public void act(float delta) {
            if (!Tank.stop_enemy)
                super.act(delta);
        }
    }

    public static final class Parameters {
        final int[] data;

        public Parameters(int[] data) {
            this.data = data;
        }

        public int getKey(int key) {
            return data[key];
        }
    }

    public static final class Briefings {
        String[] str;

        public Briefings(String[] str) {
            this.str = str;
        }

        public String getBriefing(int index) {
            return str[index];
        }
    }

    public static class GridMapData {
        public static class Cell {
            int block;
            int node;
            Object object;
            Tank tank;
            Mine mine;
            Block physicBlock;
            Bonus bonus;
            ObjBuild build;
            Bullet bulletAlly;
            Bullet bulletEnemy;

        }
    }
}
