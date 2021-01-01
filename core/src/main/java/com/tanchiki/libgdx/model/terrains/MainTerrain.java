package com.tanchiki.libgdx.model.terrains;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.tanchiki.libgdx.model.bonus.*;
import com.tanchiki.libgdx.model.buildes.*;
import com.tanchiki.libgdx.model.buildes.Object.ObjBuild;
import com.tanchiki.libgdx.model.bullets.Object.Bullet;
import com.tanchiki.libgdx.model.mine.MineEnemy1;
import com.tanchiki.libgdx.model.mine.MineUnity1;
import com.tanchiki.libgdx.model.tanks.Object.Tank;
import com.tanchiki.libgdx.model.tanks.Turret;
import com.tanchiki.libgdx.model.terrains.Object.Block;
import com.tanchiki.libgdx.model.ui.MissionCompleted;
import com.tanchiki.libgdx.stage.GameStage;
import com.tanchiki.libgdx.util.*;
import com.tanchiki.libgdx.util.astar.AStar;
import com.tanchiki.libgdx.util.astar.AStarNode;

import java.io.BufferedWriter;
import java.util.HashMap;
import java.util.Map;

public class MainTerrain extends Group implements Disposable {
    //public RingTanks RingTanks;
    public GameStage GameStage;
    public Group ground;
    public Group tanks;
    public Group walls;
    public Group builds;
    public Group tanks_enemy;
    public Group tanks_unity;
    public Group root;
    public Group decor;
    public Group road;
    public Group bonus;
    public Group track;
    public Group decor_ground;
    public Group mines;
    public Group bullet;
    public Group health;
    public Group explosions;
    public Group smoke;
    public Group overlayer;
    public Group ring;
	public Group trigger;
    //private Node[][] graph;
    public MainTerrain.Mission mission = null;
    public Rectangle rect;
    BufferedWriter w;

    public HashMap<Integer, Tank> hashTanks = new HashMap<>();

    //public Texture groundTexture;

    public AStar AStar;

    //public static TanksHoldGroup TanksHoldGroupUnity = new TanksHoldGroup();

    //public static TanksHoldGroup TanksHoldGroupEnemy = new TanksHoldGroup();

    private static MainTerrain currentTerrain = null;

    public static MainTerrain getCurrentTerrain() {
        return currentTerrain;
    }

    public static final Map<Integer, Class<?>> mapping = new HashMap<>();

    Parameters param;
	Briefings brfg;
	
	Timer timer = null;

	public int coin, star;
	public float missionTime;
	public int countFire;
	public int damageUser;
	public float distance;
	
    public MainTerrain() {
        currentTerrain = this;
        GameStage = ObjectClass.GameStage;
        ground = new Group();
        tanks = new Group();
        walls = new Group();
        builds = new Group();
        decor = new Group();
        root = new Group();
        road = new Group();
        bonus = new Group();
        track = new Group();
        decor_ground = new Group();
        mines = new Group();
        bullet = new Group();
        health = new Group();
        explosions = new Group();
        smoke = new Group();
        overlayer = new Group();
        ring = new Group();
		trigger = new Group();

        tanks_unity = new TanksUnity();
        tanks_enemy = new TanksEnemy();
        //RingTanks = new RingTanks();

        //root.addActor(TanksHoldGroupUnity);
        //root.addActor(TanksHoldGroupEnemy);

        tanks.addActor(tanks_unity);
        tanks.addActor(tanks_enemy);
        root.addActor(ground);
        root.addActor(decor_ground);
        root.addActor(road);
        root.addActor(mines);
        root.addActor(bonus);
		root.addActor(trigger);
        root.addActor(track);
        root.addActor(walls);
        root.addActor(overlayer);
        root.addActor(builds);
        root.addActor(ring);
        root.addActor(tanks);
        root.addActor(smoke);
        root.addActor(explosions);
        root.addActor(bullet);
        if (!Settings.edit_map_mode)
            root.addActor(health);
        root.addActor(decor);
        //root.addActor(new Debug());

        GameStage.TankUser = null;

        if (Settings.start_game) {
            ObjectClass.PanelStage.toasts.clear();
            ObjectClass.PanelStage.addToast("Миссия " + (GameStage.next_level + 1));
			MusicLoader.getInstance().getTrack((GameStage.next_level % 5) + 1).play();
        } else {
			MusicLoader.getInstance().getIntro().play();
		}
			
        //root.setCullingArea(new Rectangle(0, 0, GameStage.cam.viewportWidth / 2, GameStage.cam.viewportHeight / 2));
        addActor(root);
    }

	@Override
	public void act(float delta) {
		missionTime += delta;
		super.act(delta);
	}

    Array<Node> node = new Array<>();
    public void loadMap(String name) throws Exception{
        MapBinReader map = MapsDatabase.getInstance().getMap(name);
        loadMap(map);
    }
	
	public Briefings getBriefings() {
		return brfg;
	}

    public Parameters getParameters() {
        return param;
    }

    public void loadMap(MapBinReader map) throws Exception {
		System.out.println("MAP " + map.getName());
		
        ObjectVarable.size_enemy = 0;
        ObjectVarable.size_unity = 0;

        float pointXY[] = {-1, -1};

        loadBinaryMap(map);
		ObjectVarable.max_tanks_enemy = param.getKey(1);
		ObjectVarable.max_tanks_ally = param.getKey(10);
		ObjectVarable.level_diffculty = param.getKey(24);

		int[] tanks_type_enemy = new int[8];
		for (int i = 0; i < 8; i++) {
			tanks_type_enemy[i] = param.getKey(i + 2);
			ObjectVarable.all_size_enemy += param.getKey(i + 2);
		}
		ObjectVarable.tanks_type_enemy = tanks_type_enemy;

		int[] tanks_type_ally = new int[8];
		for (int i = 0; i < 8; i++) {
			tanks_type_ally[i] = param.getKey(i + 11);
			ObjectVarable.all_size_unity += param.getKey(i + 11);
		}
		ObjectVarable.tanks_type_ally = tanks_type_ally;
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
        GameStage.world_buildes = new ObjBuild[(int) pointXY[0]][(int) pointXY[1]];
        GameStage.world_bonus = new Actor[(int) pointXY[0]][(int) pointXY[1]];
        GameStage.world_bullets_enemy = new Bullet[(int) pointXY[0]][(int) pointXY[1]];
        GameStage.world_bullets_unity = new Bullet[(int) pointXY[0]][(int) pointXY[1]];
		GameStage.world_trigger = new Trigger[(int) pointXY[0]][(int) pointXY[1]];
        this.rect = new Rectangle(-1, -1, GameStage.world_wight, GameStage.world_height);
		GameStage.cam.position.set(pointXY[0] / 2f - 1f, pointXY[1] / 2f - 1, 0);
        AStar = new AStar(GameStage.world_nodes, new AStar.AStarListener() {

            @Override
            public float h(AStarNode end, AStarNode current) {
                // TODO: Implement this method
                return (float) Math.pow(end.x - current.x, 2) + (float) Math.pow(end.y - current.y, 2) + ((current.parent != null) ? ((current.code == current.parent.code) ? 0 : 100) : 0);
            }

            @Override
            public float g(AStarNode begin, AStarNode current) {
                // TODO: Implement this method
                return (float) Math.pow(begin.x - current.x, 2) + (float) Math.pow(begin.y - current.y, 2);
            }
        });
    }

    public void loadBinaryMap(final MapBinReader input) throws Exception {
        System.out.println("Binary map reading started...");
		//Gdx.app.log("Tanchiki", "Binary map reading started...");

		param = new Parameters(input.getParametersPart());
		/*PrintStream p = new PrintStream(new File("/sdcard/prm.txt"));
		for (int i = 0; i < input.getParametersPart().length; i++) {
			p.println(input.getParametersPart()[i]);
		}*/
		brfg = new Briefings(input.getHints());
		final int width = input.getSizeMapPart()[0];
		final int height = input.getSizeMapPart()[1];
        setSize(new float[] {width * 2, height * 2});

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
				s.init();
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
			tanks_enemy.addActor(new Turret(j * 2, i * 2, ObjectVarable.tank_enemy, 0));
		}
		if (code == 160 || code == 200 || code == 240) {
			tanks_unity.addActor(new Turret(j * 2, i * 2, ObjectVarable.tank_unity, 0));
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
			builds.addActor(new AngarUnity(j * 2, i * 2));
		}
		if (code == 105) {
			ground.addActor(new Plate(j * 2, i * 2));
			builds.addActor(new AngarUnity(j * 2, i * 2));
		}
		if (code == 110) {
			ground.addActor(new Sand(j * 2, i * 2));
			builds.addActor(new AngarUnity(j * 2, i * 2));
		}
		if (code == 101) {
			ground.addActor(new Grass(j * 2, i * 2));
			builds.addActor(new AngarEnemy(j * 2, i * 2));
		}
		if (code == 106) {
			ground.addActor(new Plate(j * 2, i * 2));
			builds.addActor(new AngarEnemy(j * 2, i * 2));
		}
		if (code == 111) {
			ground.addActor(new Sand(j * 2, i * 2));
			builds.addActor(new AngarEnemy(j * 2, i * 2));
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
			ground.addActor(new Grass(j * 2, i* 2));
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
			ground.addActor(new Plate(j * 2, i* 2));
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
			ground.addActor(new Sand(j * 2, i* 2));
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
				case 35: MainTerrain.getCurrentTerrain().setTimer(1 * 60, MainTerrain.Timer.MODE.LOSE); break;
				case 31:
				case 41:	
				case 36: MainTerrain.getCurrentTerrain().setTimer(2 * 60, MainTerrain.Timer.MODE.LOSE); break;
				case 32:
				case 42:	
				case 37: MainTerrain.getCurrentTerrain().setTimer(3 * 60, MainTerrain.Timer.MODE.LOSE); break;
				
				case 20: MainTerrain.getCurrentTerrain().setTimer(40, MainTerrain.Timer.MODE.WIN); break;
				case 50:
				case 21: MainTerrain.getCurrentTerrain().setTimer(1 * 60, MainTerrain.Timer.MODE.WIN); break;
				case 51:
				case 22: MainTerrain.getCurrentTerrain().setTimer(2 * 60, MainTerrain.Timer.MODE.WIN); break;
				case 52:
				case 23: MainTerrain.getCurrentTerrain().setTimer(3 * 60, MainTerrain.Timer.MODE.WIN); break;
				case 53:
				case 24: MainTerrain.getCurrentTerrain().setTimer(5 * 60, MainTerrain.Timer.MODE.WIN); break;
				case 54: MainTerrain.getCurrentTerrain().setTimer(7 * 60, MainTerrain.Timer.MODE.WIN); break;
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
					if (ObjectVarable.all_size_enemy == 0) win(); break;
				
				case 12: 
					if (ObjectVarable.all_size_unity < 1) lose();
					if (ObjectVarable.all_size_enemy == 0) win(); break;
				case 13: 
					if (ObjectVarable.all_size_unity < 2) lose();
					if (ObjectVarable.all_size_enemy == 0) win(); break;
				case 14: 
					if (ObjectVarable.all_size_unity < 3) lose();
					if (ObjectVarable.all_size_enemy == 0) win(); break;
					
				case 15: if (ObjectVarable.all_size_unity < 1) lose(); break;
				case 16: if (ObjectVarable.all_size_unity < 2) lose(); break;
				case 17: if (ObjectVarable.all_size_unity < 3) lose(); break;
				
				case 50:
				case 51:
				case 52:
				case 53:
				case 54: if (ObjectVarable.all_size_radar_allies == 0) lose(); break;
				case 55: if (ObjectVarable.all_size_radar_allies == 0) lose();
						 if (ObjectVarable.all_size_enemy == 0) win();	break;
						 
				case 56: if (ObjectVarable.all_size_radar_enemy == 0) win(); break;
				case 57: if (ObjectVarable.all_size_reactor_enemy == 0) win(); break;
				case 58: if (ObjectVarable.all_size_radar_enemy == 0 && ObjectVarable.all_size_reactor_enemy == 0) win(); break;
				case 59: if (ObjectVarable.all_size_turrets_enemy == 0) win(); break;
				case 60: if (ObjectVarable.all_size_turrets_enemy == 0 && ObjectVarable.all_size_enemy == 0) win(); break;
				case 66: break;
				default: remove();
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
					case WIN: win(); break;
					case LOSE: lose(); break;
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
		
		public static enum MODE {
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
	
    public static class Node {
        public boolean visited = false;

        public float x, y;

        public Node parent = null;

        public Node child = null;

        public Array<Integer> code_way = new Array<Integer>();

        public Node() {
        }

        public int CODE;

        public boolean CODE_RIGHT = false,
                CODE_LEFT = false,
                CODE_UP = false,
                CODE_DOWN = false;

        public Node(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public
        static
        final
        int
                UP = 2,
                DOWN = -2,
                RIGHT = 1,
                LEFT = -1;

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
}
