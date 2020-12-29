package com.tanchiki.libgdx.util;
import com.badlogic.gdx.audio.*;
import java.util.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.assets.*;

public class SoundLoader {
	private static SoundLoader soundLoader = null;
	public static AssetManager manager = new AssetManager();
	
	public static SoundLoader getInstance() {
		if (soundLoader == null) soundLoader = new SoundLoader();
		return soundLoader;
	}
	
	Map<String, Sound> sounds = new HashMap<>();
	
	public SoundLoader() {
	}
	
	public static void load() {
		manager.load("sounds/explosion_small01.wav", Sound.class);
		manager.load("sounds/explosion_small02.wav", Sound.class);
		manager.load("sounds/explosion_medium01.wav", Sound.class);
		manager.load("sounds/explosion_medium02.wav", Sound.class);
		manager.load("sounds/shell_plasma.wav", Sound.class);
		manager.load("sounds/shell_default.wav", Sound.class);
		manager.load("sounds/shell_warhead.wav", Sound.class);
		manager.load("sounds/shell_artillery.wav", Sound.class);
		manager.load("sounds/hit_metal01.wav", Sound.class);
		manager.load("sounds/hit_metal02.wav", Sound.class);
		manager.load("sounds/hit_wall_def01.wav", Sound.class);
		manager.load("sounds/hit_wall_def02.wav", Sound.class);
		manager.load("sounds/hit_energy_field_1.wav", Sound.class);
		manager.load("sounds/hit_energy_field_2.wav", Sound.class);
		manager.load("sounds/hit_bush.wav", Sound.class);
		manager.load("sounds/fx_flag_pickup.wav", Sound.class);
		manager.load("sounds/fx_mine_deploy.wav", Sound.class);
		manager.load("sounds/fx_powerup_get_star.wav", Sound.class);
		manager.load("sounds/fx_powerup_pickup.wav", Sound.class);
		manager.load("sounds/fx_powerup_use_freeze.wav", Sound.class);
		manager.load("sounds/fx_powerup_use_repair.wav", Sound.class);
		manager.load("sounds/fx_powerup_use_speed.wav", Sound.class);
	}
	
	private Sound load(String name) {
		/*Sound sound = sounds.get(name);
		if (sound == null) {
			sound = Gdx.audio.newSound(Gdx.files.internal("sounds/" + name + ".wav"));
			sounds.put(name, sound);
		}*/
		return manager.get("sounds/" + name + ".wav");
	}
	
	public Sound getMediumExpl() {
		return load("explosion_medium0" + MathUtils.random(1, 2));
	}
	
	public Sound getExpl() {
		return load("explosion_small0" + MathUtils.random(1, 2));
	}
	
	public Sound getShellPlazma() {
		return load("shell_plasma");
	}
	
	public Sound getShellBullet() {
		return load("shell_default");
	}
	
	public Sound getShellRocket() {
		return load("shell_warhead");
	}
	
	public Sound getShellArtillery() {
		return load("shell_artillery");
	}
	
	public Sound getHitMetal() {
		return load("hit_metal0" + MathUtils.random(1, 2));
	}
	
	public Sound getHitWall() {
		return load("hit_wall_def0" + MathUtils.random(1, 2));
	}
	
	public Sound getHitShield() {
		return load("hit_energy_field_" + MathUtils.random(1, 2));
	}
	
	public Sound getHitBush() {
		return load("hit_bush");
	}
	
	public Sound getFlagPickup() {
		return load("fx_flag_pickup");
	}
	
	public Sound getMineDeploy() {
		return load("fx_mine_deploy");
	}
	
	public Sound getStarPickup() {
		return load("fx_powerup_get_star");
	}
	
	public Sound getPowerupPickup() {
		return load("fx_powerup_pickup");
	}
	
	public Sound getFreezePickup() {
		return load("fx_powerup_use_freeze");
	}
	
	public Sound getRepairPickup() {
		return load("fx_powerup_use_repair");
	}
	
	public Sound getSpeedPickup() {
		return load("fx_powerup_use_speed");
	}
}
