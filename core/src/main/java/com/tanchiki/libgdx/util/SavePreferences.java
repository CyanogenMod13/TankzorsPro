package com.tanchiki.libgdx.util;

import com.badlogic.gdx.*;
import com.tanchiki.libgdx.stage.*;
import java.io.*;
import java.lang.reflect.*;
import java.util.*;
import java.nio.*;

public class SavePreferences {
	private static SavePreferences instance = null;
	public static SavePreferences getInstance() {
		return instance == null ? instance = new SavePreferences() : instance;
	}
	
	private final String dir = Gdx.app.getType().equals(Application.ApplicationType.Android) ? "/data/data/com.tanchiki.libgdx/files/" : "";
	
	private SavePreferences() {
		
	}
	
	public void save(int slot) {
		save("slot" + slot);
	}
	
	public void load(int slot) {
		load("slot" + slot);
	}
	
	public void loadContinues() {
		load("slot_autosave");
	}
	
	public void saveContinues() {
		save("slot_autosave");
	}
	
	public void reset() {
		try {
			GameStage.next_level = 0;
			for (Field f : WeaponData.class.getFields()) {
				f.set(null, 0);
			}
			for (Field f : WeaponData.Upgrade.class.getFields()) {
				f.set(null, 0);
			}
			Settings.TankUserSettings.coin = 100;
			Settings.TankUserSettings.star = 0;
			Settings.TankUserSettings.tank_level = 1;
			Settings.TankUserSettings.bullet_type = 1;
			Settings.TankUserSettings.plus_bullet_type1 = 1;
			Settings.TankUserSettings.plus_bullet_type2 = 1;
			
			WeaponData.live = 4;
			WeaponData.light_bullet = 100;
		} catch (Exception e) {}
	}
	
	public boolean haveContinues() {
		loadContinues();
		return GameStage.next_level > 0;
	}
	
	private synchronized void save(String name) {
		try {
			FileOutputStream out = new FileOutputStream(dir + name);
			out.write(ByteBuffer.allocate(4).putInt(GameStage.next_level).array());
			for (Field f : WeaponData.class.getFields()) {
				out.write(ByteBuffer.allocate(4).putInt(f.getInt(null)).array());
			}
			for (Field f : Settings.TankUserSettings.class.getFields()) {
				out.write(ByteBuffer.allocate(4).putInt(f.getInt(null)).array());
			}
			for (Field f : WeaponData.Upgrade.class.getFields()) {
				out.write(ByteBuffer.allocate(4).putInt(f.getInt(null)).array());
			}
			out.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void load(String name) {
		try {
			FileInputStream in = new FileInputStream(dir + name);
			byte[] bytes = new byte[4];
			in.read(bytes);
			GameStage.next_level = ByteBuffer.wrap(bytes).getInt();
			for (Field f : WeaponData.class.getFields()) {
				in.read(bytes);
				f.set(null, ByteBuffer.wrap(bytes).getInt());
			}
			for (Field f : Settings.TankUserSettings.class.getFields()) {
				in.read(bytes);
				f.set(null, ByteBuffer.wrap(bytes).getInt());
			}
			for (Field f : WeaponData.Upgrade.class.getFields()) {
				in.read(bytes);
				f.set(null, ByteBuffer.wrap(bytes).getInt());
			}
			in.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void saveSettings() {
		try {
			FileOutputStream out = new FileOutputStream(dir + "settings");
			out.write(ByteBuffer.allocate(10).putFloat(Settings.volumeMusic).array());
			out.write(ByteBuffer.allocate(10).putFloat(Settings.volumeEffect).array());
			out.write(ByteBuffer.allocate(10).putFloat(Settings.zoom).array());
		} catch (Exception e) {}
	}
	
	public void loadSettings() {
		try {
			FileInputStream in = new FileInputStream(dir + "settings");
			byte[] bytes = new byte[10];
			in.read(bytes);
			Settings.volumeMusic = ByteBuffer.wrap(bytes).getFloat();
			in.read(bytes);
			Settings.volumeEffect = ByteBuffer.wrap(bytes).getFloat();
			in.read(bytes);
			Settings.zoom = ByteBuffer.wrap(bytes).getFloat();
		} catch (Exception e) {}
	}
}
