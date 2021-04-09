package com.tanchiki.libgdx.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.tanchiki.libgdx.model.aircraft.Airplane;
import com.tanchiki.libgdx.model.buildes.Object.ObjBuild;
import com.tanchiki.libgdx.model.bullets.Object.Bullet;
import com.tanchiki.libgdx.model.tanks.Tank;
import com.tanchiki.libgdx.model.tanks.TankUser;
import com.tanchiki.libgdx.model.terrains.*;
import com.tanchiki.libgdx.model.terrains.Block;
import com.tanchiki.libgdx.model.ui.MissionCompleted;
import com.tanchiki.libgdx.util.*;

public class GameStage extends Stage {
    public static GameStage gameStage = null;
    public static GameStage getInstance() {
        if (gameStage == null) gameStage = new GameStage();
        return gameStage;
    }

    public TextureLoader TextureLoader;
    public OrthographicCamera cam;
    public MainTerrain MT = null;
    public TankUser TankUser;

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

    public GestureDetector.GestureListener listener = new Listener();

    public Class<?> currentObj = Grass.class;
    public String parentObj = "ground";
	
	public int airplaneX, airplaneY;
	
	private Rectangle areaVisible;

    public GameStage() {
		SavePreferences.getInstance().loadSettings();
        ObjectClass.GameStage = this;
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
        TextureLoader = ObjectClass.TextureLoader;

        if (!Settings.edit_map_mode)
            createTerrain("map_background");
    }

    public void createAircraft() {
        //Gdx.input.setInputProcessor(this);
        Settings.pause = true;
        touchActor = null;
        createAircraft = true;
    }

    @Override
    public Actor hit(float stageX, float stageY, boolean touchable) {
        // TODO: Implement this method
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
        ObjectClass.GameStage.createTerrain("map" + (num - 1));
    }

    public void destroyLevel() {

    }

    public MainTerrain createTerrain(String n) {
        disposeTerrain();
        MT = new MainTerrain();
        try {
            MT.loadMap(n);
        } catch (Exception e) {
            e.printStackTrace();
        }
        addActor(MT);
        return MT;
    }

    public MainTerrain createTerrain(MapBinReader n) {
        disposeTerrain();
        MT = new MainTerrain();
        try {
            MT.loadMap(n);
        } catch (Exception e) {
            e.printStackTrace();
        }
        addActor(MT);
        return MT;
    }

    public void disposeTerrain() {
        ObjectVariables.all_size_boss_allies = ObjectVariables.all_size_boss_enemies = ObjectVariables.all_size_radar_allies = ObjectVariables.all_size_radar_enemy = ObjectVariables.all_size_reactor_enemy = ObjectVariables.all_size_turrets_enemy = ObjectVariables.all_size_enemies = ObjectVariables.all_size_allies = ObjectVariables.size_enemies = ObjectVariables.size_allies = ObjectVariables.max_tanks = 0;
		timer_enemy = 0;
		Tank.stop_enemy = false;
		MissionCompleted.isShow = false;
		MissionCompleted.show = true;
		MissionCompleted.isMissionCompleted = false;
        if (MT != null) {
            MT.dispose();
            MT = null;
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
		int rx = (int) (cam.position.x * 10);
		int ry = (int) (cam.position.y * 10);
		int dx = 0;//rx % 2;
		int dy = 0;//ry % 2;
		cam.position.set((rx + dx) / 10f, (ry + dy) / 10f, 0);
       	//System.out.println(cam.position.x + " " + cam.position.y);
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
        }
        catch (Exception e) {
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
                MT.decor.addActor(new Airplane(airplaneX, airplaneY, 5 + (WeaponData.Upgrade.air == 3 ? 2 : 0), 12 + WeaponData.Upgrade.air * 4));
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
        long d = System.currentTimeMillis();
        if (MT != null && !Settings.edit_map_mode)
            Clound.random(delta);
        updateAirplane();

		GPUOptimization(MT.root);
        if (!Settings.pause) act(delta);
        draw();
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        if (Settings.edit_map_mode) {
            Vector3 pos = cam.unproject(new Vector3(screenX, screenY, 0));
            hit(pos.x, pos.y, true);
            if (touchActor != null)
                touchActor.setColor(Color.RED);
        }
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        editBlock(screenX, screenY);
        return true;
    }

    private void editBlock(int screenX, int screenY) {
        if (Settings.edit_map_mode) {
            if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
                Vector3 pos = cam.unproject(new Vector3(screenX, screenY, 0));
                hit(pos.x, pos.y, true);
                try {
                    if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
                        if (touchActor.getClass() == currentObj) {
                            touchActor.remove();
                        }
                        return;
                    }

                    if (touchActor.getClass() == currentObj) return;

                    Actor obj = (Actor) currentObj.getConstructor(float.class, float.class).newInstance(touchActor.getX(Align.center), touchActor.getY(Align.center));
                    Group parent = (Group) MainTerrain.class.getField(parentObj).get(MT);
                    parent.addActor(obj);
                    for (Actor act : MT.ground.getChildren())
                        if (act instanceof Sand) ((Sand) act).postInit();
                    for (Actor act : MT.road.getChildren())
                        if (act instanceof Road) ((Road) act).init();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public boolean keyDown(int keyCode) {
        if (Settings.edit_map_mode)
            switch (keyCode) {
                case Input.Keys.PLUS:
                    cam.zoom -= 0.1f; break;
                case Input.Keys.MINUS:
                    cam.zoom += 0.1f; break;
            }
        return super.keyDown(keyCode);
    }

    public class Listener implements GestureDetector.GestureListener
    {
        private float zoom = 0;
        @Override
        public boolean touchDown(float p1, float p2, int p3, int p4)
        {
            zoom = cam.zoom;
            // TODO: Implement this method
            return true;
        }

        @Override
        public boolean tap(float p1, float p2, int p3, int p4)
        {
            // TODO: Implement this method
            return false;
        }

        @Override
        public boolean longPress(float p1, float p2)
        {
            // TODO: Implement this method
            return false;
        }

        @Override
        public boolean fling(float p1, float p2, int p3)
        {

            // TODO: Implement this method
            return true;
        }

        @Override
        public boolean pan(float p1, float p2, float p3, float p4)
        {
            if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
                cam.position.set(cam.position.x - p3/10*cam.zoom,cam.position.y + p4/10*cam.zoom,0);
                return true;
            }
            return false;
        }

        @Override
        public boolean panStop(float p1, float p2, int p3, int p4)
        {
            // TODO: Implement this method
            return false;
        }

        @Override
        public boolean zoom(float p1, float p2)
        {
            cam.zoom = zoom*(p1/p2);
            cam.update();
            // TODO: Implement this method
            return false;
        }

        @Override
        public boolean pinch(Vector2 p1, Vector2 p2, Vector2 p3, Vector2 p4)
        {
            // TODO: Implement this method
            return false;
        }

        @Override
        public void pinchStop() {

        }
    }
}
