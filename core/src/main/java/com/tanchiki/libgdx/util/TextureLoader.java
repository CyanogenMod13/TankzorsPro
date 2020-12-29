package com.tanchiki.libgdx.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.tanchiki.libgdx.screens.*;
import com.badlogic.gdx.assets.*;

public class TextureLoader {

    private static TextureLoader textureLoader = null;

    public static TextureLoader getInstance() {
        if (textureLoader == null) textureLoader = new TextureLoader();
        return textureLoader;
    }

    private Texture bonus;
    private Texture icons;
    private Texture expl;
    private Texture tank_light;
    private Texture tank_heavy;
    private Texture tank_siege;
    private Texture tank_kamikaze;
    private Texture walls;
    private Texture terrains;
    private Texture rings;
    private Texture bullets;
    private Texture builds;
    private Texture buildings;
    private Texture track;
    private Texture tree;
    private Texture mines;
    private Texture health_red, health_yell;
    private Texture airplane;
    private Texture spike;
    private Texture cloud;
    private Texture art;
    private Texture triggers;
    private Texture panel_health;
    private Texture overrlaps;
    private Texture turret;
    private Texture overlay_corner;
	private Texture explRed;
	private Texture explPlazma;
    private TextureRegion[][] tl;
    private TextureRegion[][] te;
    private TextureRegion[][] th;
    private TextureRegion[][] w;
    private TextureRegion[][] t;
    private TextureRegion[][] ts;
    private TextureRegion[][] tk;
    private TextureRegion[][] r;
    private TextureRegion[][] b;
    private TextureRegion[][] e;
    private TextureRegion[][] bu;
    private TextureRegion[][] bui;
    private TextureRegion[][] tr;
    private TextureRegion[][] hr, hy;
    private TextureRegion[][] i;
    private TextureRegion[][] bo;
    private TextureRegion[][] m;
    private TextureRegion[][] air;
    private TextureRegion[][] s;
    private TextureRegion[][] c;
    private TextureRegion[][] tu;
    private TextureRegion[][] trig;
    private TextureRegion[][] ph;
    private TextureRegion[][] o;
    private TextureRegion[][] co;
	private TextureRegion[][] er;
	private TextureRegion[][] ep;

    public TextureRegion d = new TextureRegion(new Texture("texture/ui/billet.png"));
	public static AssetManager manager = new AssetManager();
	
    public TextureLoader() {
		icons = manager.get("texture/ui/icons.png");
		track = manager.get("texture/terrain/tracks.png");
		buildings = manager.get("texture/builds/buildings.png");
		expl = manager.get("texture/explosion/expl_big.png");
		builds = manager.get("texture/builds/big_builds.png");
		tank_heavy = manager.get("texture/tanks/tank_heavy.png");
		tank_light = manager.get("texture/tanks/tank_light.png");
		tank_siege = manager.get("texture/tanks/tank_siege.png");
		tank_kamikaze = manager.get("texture/tanks/tank_kamikaze.png");
		rings = manager.get("texture/ui/rings.png");
		walls = manager.get("texture/terrain/walls.png");
		bullets = manager.get("texture/bullet/bullets.png");
		terrains = manager.get("texture/terrain/ground.png");
		health_red = manager.get("texture/ui/health_red.png");
		health_yell = manager.get("texture/ui/health_yell.png");
		tree = manager.get("texture/ui/palms.png");
		bonus = manager.get("texture/ui/powerups.png");
		mines = manager.get("texture/terrain/mines.png");
		airplane = manager.get("texture/builds/aircrafts.png");
		spike = manager.get("texture/terrain/spikes.png");
		cloud = manager.get("texture/terrain/clouds.png");
		triggers = manager.get("texture/terrain/triggers.png");
		panel_health = manager.get("texture/ui/panel_health.png");
		overrlaps = manager.get("texture/ui/overlays.png");
		turret = manager.get("texture/tanks/turret.png");
		overlay_corner = manager.get("texture/ui/overlay_corn.png");
		explRed = manager.get("texture/explosion/expl_big_red.png");
		explPlazma = manager.get("texture/explosion/expl_big_plasma.png");
		split();
	}//throws Exception
    
	public static void load() {
		manager.load("texture/ui/icons.png", Texture.class);
		manager.load("texture/terrain/tracks.png", Texture.class);
		manager.load("texture/builds/buildings.png", Texture.class);
		manager.load("texture/explosion/expl_big.png", Texture.class);
		manager.load("texture/builds/big_builds.png", Texture.class);
		manager.load("texture/tanks/tank_heavy.png", Texture.class);
		manager.load("texture/tanks/tank_light.png", Texture.class);
		manager.load("texture/tanks/tank_siege.png", Texture.class);
		manager.load("texture/tanks/tank_kamikaze.png", Texture.class);
		manager.load("texture/ui/rings.png", Texture.class);
		manager.load("texture/terrain/walls.png", Texture.class);
		manager.load("texture/bullet/bullets.png", Texture.class);
		manager.load("texture/terrain/ground.png", Texture.class);
		manager.load("texture/ui/health_red.png", Texture.class);
		manager.load("texture/ui/health_yell.png", Texture.class);
		manager.load("texture/ui/palms.png", Texture.class);
		manager.load("texture/ui/powerups.png", Texture.class);
		manager.load("texture/terrain/mines.png", Texture.class);
		manager.load("texture/builds/aircrafts.png", Texture.class);
		manager.load("texture/terrain/spikes.png", Texture.class);
		manager.load("texture/terrain/clouds.png", Texture.class);
		manager.load("texture/terrain/triggers.png", Texture.class);
		manager.load("texture/ui/panel_health.png", Texture.class);
		manager.load("texture/ui/overlays.png", Texture.class);
		manager.load("texture/tanks/turret.png", Texture.class);
		manager.load("texture/ui/overlay_corn.png", Texture.class);
		manager.load("texture/explosion/expl_big_red.png", Texture.class);
		manager.load("texture/explosion/expl_big_plasma.png", Texture.class);
	}
	
	private void split() {
		tr = TextureRegion.split(track, track.getWidth() / 20, track.getHeight() / 1);
		tl = TextureRegion.split(tank_light, tank_light.getWidth() / 16, tank_light.getHeight() / 1);
		ts = TextureRegion.split(tank_siege, tank_siege.getWidth() / 16, tank_siege.getHeight() / 1);
		th = TextureRegion.split(tank_heavy, tank_light.getWidth() / 16, tank_light.getHeight() / 1);
		tk = TextureRegion.split(tank_kamikaze, tank_kamikaze.getWidth() / 16, tank_kamikaze.getHeight() / 1);
		w = TextureRegion.split(walls, walls.getHeight(), walls.getHeight() / 1);
		t = TextureRegion.split(terrains, terrains.getHeight(), terrains.getHeight() / 1);
		r = TextureRegion.split(rings, rings.getWidth() / 5, rings.getHeight() / 1);
		bu = TextureRegion.split(builds, builds.getWidth() / 2, builds.getHeight() / 1);
		e = TextureRegion.split(expl, expl.getWidth() / 28, expl.getHeight() / 1);
		bui = TextureRegion.split(buildings, buildings.getWidth() / 5, buildings.getHeight() / 1);
		b = TextureRegion.split(bullets, bullets.getWidth() / 23, bullets.getHeight() / 1);
		hr = TextureRegion.split(health_red, health_red.getWidth() / 12, health_red.getHeight() / 1);
		hy = TextureRegion.split(health_yell, health_yell.getWidth() / 12, health_yell.getHeight() / 1);
		te = TextureRegion.split(tree, tree.getWidth() / 5, tree.getHeight() / 1);
		i = TextureRegion.split(icons, icons.getWidth() / 43, icons.getHeight() / 1);
		bo = TextureRegion.split(bonus, bonus.getWidth() / 20, bonus.getHeight() / 1);
		m = TextureRegion.split(mines, mines.getWidth() / 5, mines.getHeight() / 1);
		air = TextureRegion.split(airplane, airplane.getWidth() / 2, airplane.getHeight());
		s = TextureRegion.split(spike, spike.getWidth() / 3, spike.getHeight());
		c = TextureRegion.split(cloud, cloud.getWidth() / 6, cloud.getHeight());
		trig = TextureRegion.split(triggers, triggers.getWidth() / 10, triggers.getHeight());
		ph = TextureRegion.split(panel_health, panel_health.getWidth() / 6, panel_health.getHeight() / 1);
		o = TextureRegion.split(overrlaps, overrlaps.getWidth() / 5, overrlaps.getHeight() / 1);
		tu = TextureRegion.split(turret, turret.getWidth() / 16, turret.getHeight() / 1);
		co = TextureRegion.split(overlay_corner, overlay_corner.getWidth() / 4, overlay_corner.getHeight());
		er = TextureRegion.split(explRed, explRed.getWidth() / 12, explRed.getHeight());
		ep = TextureRegion.split(explPlazma, explPlazma.getWidth() / 12, explPlazma.getHeight());
	}

    public static void init() {
        ObjectClass.TextureLoader = getInstance();
    }

    public TextureRegion[][] getOverlayCorner() {
        return co;
    }

    public TextureRegion[][] getTurrets() {
        return tu;
    }

    public TextureRegion[][] getOverlayers() {
        return o;
    }

    public TextureRegion[][] getPanelHealth() {
        return ph;
    }

    public TextureRegion[][] getTriggers() {
        return trig;
    }

    public TextureRegion[][] getClouds() {
        return c;
    }

    public TextureRegion[][] getSpike() {
        return s;
    }

    public TextureRegion[][] getAirplane() {
        return air;
    }

    public TextureRegion[][] getMines() {
        return m;
    }

    public TextureRegion[][] getBonus() {
        return bo;
    }

    public TextureRegion[][] getIcons() {
        return i;
    }

    public TextureRegion[][] getPalms() {
        return te;
    }

    public TextureRegion[][] getHealth_Red() {
        return hr;
    }

    public TextureRegion[][] getHealth_Yell() {
        return hy;
    }

    public TextureRegion[][] getTracks() {
        return tr;
    }

    public TextureRegion[][] getBuildings() {
        return bui;
    }

	public TextureRegion[][] getRedExpl() {
		return er;
	}
	
	public TextureRegion[][] getBlueExpl() {
		return ep;
	}
	
    public TextureRegion[][] getExpl() {
        return e;
    }

    public TextureRegion[][] getBuilds() {
        return bu;
    }

    public TextureRegion[][] getTankLight() {
        return tl;
    }

    public TextureRegion[][] getTankHeavy() {
        return th;
    }

    public TextureRegion[][] getTankSiege() {
        return ts;
    }

    public TextureRegion[][] getTankKamikaze() {
        return tk;
    }

    public TextureRegion[][] getWalls() {
        return w;
    }

    public TextureRegion[][] getTerrains() {
        return t;
    }

    public TextureRegion[][] getRings() {
        return r;
    }

    public TextureRegion[][] getBullets() {
        return b;
    }
}
