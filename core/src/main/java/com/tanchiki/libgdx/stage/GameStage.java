package com.tanchiki.libgdx.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.tanchiki.libgdx.model.aircraft.Airplane;
import com.tanchiki.libgdx.model.buildes.ObjBuild;
import com.tanchiki.libgdx.model.bullets.Bullet;
import com.tanchiki.libgdx.model.tanks.Tank;
import com.tanchiki.libgdx.model.tanks.TankUser;
import com.tanchiki.libgdx.model.terrains.Block;
import com.tanchiki.libgdx.model.terrains.Clound;
import com.tanchiki.libgdx.model.terrains.MainTerrain;
import com.tanchiki.libgdx.model.terrains.Trigger;
import com.tanchiki.libgdx.model.ui.MissionCompleted;
import com.tanchiki.libgdx.util.*;

import java.rmi.RemoteException;

public class GameStage extends Stage {
    private static GameStage gameStage = null;

    public static GameStage getInstance() {
        if (gameStage == null) gameStage = new GameStage();
        return gameStage;
    }

    public OrthographicCamera cam;
    public MainTerrain mainTerrain = null;
    public TankUser tankUser;

    public int[][] world_block;

    public Actor[][] world_mines;

    public Object[][] world_obj;

    public Tank[][] world_tank;

    public int[][] world_nodes;

    public Block[][] world_physic_block;

    public Actor[][] world_bonus;

    public ObjBuild[][] worldBuilds;

    public Bullet[][] world_bullets_unity, world_bullets_enemy;

    public Trigger[][] world_trigger;

    public int world_wight, world_height;
    public Actor touchActor = null;
    boolean createAircraft = false;
    public static int next_level = 0;
    public static float timer_enemy = 0, timer_unity = 0;

    public int airplaneX, airplaneY;

    private Rectangle areaVisible;

    private GameStage() {
        GameStage.gameStage = this;

        SavePreferences.getInstance().loadSettings();
        loadMap();

        float h = Gdx.graphics.getHeight();
        float w = Gdx.graphics.getWidth();
        float vw = 64;
        float vh = vw * (h / w);
        cam = new OrthographicCamera(vw, vh);
        System.out.println("Camera viewport " + vw + " " + vh);
        cam.position.set(0, 0, 0);
        areaVisible = new Rectangle(0, 0, vw, vh);
        cam.zoom = 1f / Settings.zoom;
        cam.update();
        getViewport().setCamera(cam);

        if (!Settings.edit_map_mode)
            createTerrain("map_background");
    }

    public void createAircraft() {
        Settings.pause = true;
        touchActor = null;
        createAircraft = true;
    }

    @Override
    public Actor hit(float stageX, float stageY, boolean touchable) {
        airplaneX = Math.round(stageX);
        airplaneY = Math.round(stageY);

        airplaneX += airplaneX % 2;
        airplaneY += airplaneY % 2;

        touchActor = super.hit(stageX, stageY, touchable);
        return touchActor;
    }

    public void startLevel(int num) {
        if (num <= 0) throw new IllegalArgumentException("Number must be upper than 0");

        Settings.show_main_menu = false;
        Settings.start_game = true;
        createTerrain("map" + (num - 1));
    }

    public void startLevel(byte[] data) throws RemoteException {
        Settings.show_main_menu = false;
        Settings.start_game = true;
        createTerrain(data);
    }

    public MainTerrain createTerrain(String n) {
        disposeTerrain();
        mainTerrain = new MainTerrain();
        try {
            mainTerrain.loadMap(n);
        } catch (Exception e) {
            e.printStackTrace();
        }
        addActor(mainTerrain);
        return mainTerrain;
    }

    public MainTerrain createTerrain(MapBinReader n) {
        disposeTerrain();
        mainTerrain = new MainTerrain();
        try {
            mainTerrain.loadMap(n);
        } catch (Exception e) {
            e.printStackTrace();
        }
        addActor(mainTerrain);
        return mainTerrain;
    }

    public MainTerrain createTerrain(byte[] n) throws RemoteException {
        disposeTerrain();
        mainTerrain = new MainTerrain();
        try {
            mainTerrain.loadMap(new MapBinReader(n));
        } catch (Exception e) {
            e.printStackTrace();
        }
        addActor(mainTerrain);
        return mainTerrain;
    }

    public void disposeTerrain() {
        ObjectVariables.all_size_boss_allies = ObjectVariables.all_size_boss_enemies = ObjectVariables.all_size_radar_allies = ObjectVariables.all_size_radar_enemy = ObjectVariables.all_size_reactor_enemy = ObjectVariables.all_size_turrets_enemy = ObjectVariables.all_size_enemies = ObjectVariables.all_size_allies = ObjectVariables.size_enemies = ObjectVariables.size_allies = ObjectVariables.max_tanks = 0;
        timer_enemy = 0;
        Tank.stop_enemy = false;
        MissionCompleted.isShow = false;
        MissionCompleted.show = true;
        MissionCompleted.isMissionCompleted = false;
        if (mainTerrain != null) {
            mainTerrain.dispose();
            mainTerrain = null;
        }
    }

    float a = 0;
    float deltaX;
    float deltaY;

    public void moveCam(float x, float y, float speed) {
        float w = x - cam.position.x;
        float h = y - cam.position.y;
        float angle = (float) Math.atan2(h, w);
        float cos = (float) Math.cos(angle);
        float sin = (float) Math.sin(angle);
        float dis = new Vector2(cam.position.x, cam.position.y).dst(x, y);
        a = Math.min(1, (int) (dis));

        deltaX = cos * a * speed * dis;
        deltaY = sin * a * speed * dis;

        cam.translate(deltaX, deltaY);
    }

    private void loadMap() {
        try {
            MapsDatabase database = MapsDatabase.getInstance();
            int index = 0;
            while (true) {
                FileHandle map = Gdx.files.internal(String.format("map/%03d.map", index));
                if (!map.exists()) break;
                database.putMap("map" + index, map.read());
                index++;
            }
            database.putMap("map_background", database.getMap("map0"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stop_time(short f) {
        timer_enemy = 0;
        Tank.stop_enemy = true;
    }

    public void updateAirplane() {
        if (createAircraft) {
            if (touchActor != null) {
                mainTerrain.decor.addActor(new Airplane(airplaneX, airplaneY, 5 + (WeaponData.Upgrade.air == 3 ? 2 : 0), 12 + WeaponData.Upgrade.air * 4));
                createAircraft = false;
                Settings.pause = false;
                WeaponData.air -= 1;
            }
        }
    }

    @Override
    public void act(float delta) {
        if (Tank.stop_enemy) {
            timer_enemy += delta;
            if (timer_enemy > 50) {
                Tank.stop_enemy = false;
            }
        }

        if (Tank.stop_unity) {
            timer_unity += delta;
            if (timer_unity > 30) {
                Tank.stop_unity = false;
            }
        }
        super.act(delta);
    }

    private void GPUOptimization(Group group) {
        areaVisible.setCenter(cam.position.x, cam.position.y);
        Array<Actor> actors = group.getChildren();
        for (int i = 0; i < actors.size; i++) {
            Actor actor = actors.get(i);
            if (actor instanceof Group) {
                GPUOptimization((Group) actor);
                continue;
            }
            actor.setVisible(areaVisible.contains(actor.getX(Align.center), actor.getY(Align.center)));
        }
    }

    public void drawStage(float delta) {
        if (mainTerrain != null && !Settings.edit_map_mode)
            Clound.random(delta);
        updateAirplane();

        GPUOptimization(mainTerrain.root);
        if (!Settings.pause) act(delta);
        draw();
    }
}
