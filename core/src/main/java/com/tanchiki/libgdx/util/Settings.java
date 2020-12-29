package com.tanchiki.libgdx.util;

import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.Vector;

public class Settings {
    //public static Preferences setting = Gdx.app.get
	public static float volumeEffect = 0.5f;
	
	public static float volumeMusic = 1;
	
	public static float zoom = 1;
	
    public static boolean edit_map_mode = false;

    public static boolean fixed_move_camera = true;

    public static boolean show_map = false;

    public static boolean show_main_menu = true;

    public static boolean start_game = false;

    public static boolean campania_menu = false;

    public static boolean store_menu = false;

    public static boolean pause = false;
	
	public static boolean pauseView = false;

    public static Array<String> tanktypeunity = new Array<String>();

    public static Array<String> tanktypeenemy = new Array<String>();

    public static ArrayList<String> tanktypeunityData = new ArrayList<>();

    public static ArrayList<String> tanktypeenemyData = new ArrayList<>();

    public static Vector<Vector<String>> tanksData = new Vector<>();

    public static class TankUserSettings {
        public static int HP, HPbackup = 5;

        public static int HPShield, HPShieldBackup;

        public static int coin = 100, star = 0;

        public static int tank_level = 1;

        public static int bullet_type = 1;

        public static int plus_bullet_type1 = 1;

        public static int plus_bullet_type2 = 1;
    }
}
