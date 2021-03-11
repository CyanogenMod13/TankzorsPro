package com.tanchiki.libgdx.util;

public class ObjectVariables {
    public static final int size_block = 1;

    public static final short tank_ally = 0x0001;

    public static final short tank_enemy = -0x0001;

    public static int max_tanks = 0;
	
	public static int max_tanks_enemy = 0;
	
	public static int max_tanks_ally = 0;
	
	public static int level_difficulty = 0;

    public static int size_enemies = 0;

    public static int size_allies = 0;

    public static int all_size_enemies = 0;

    public static int all_size_allies = 0;
	
	public static int all_size_boss_enemies = 0;
	
	public static int all_size_boss_allies = 0;
	
	public static int all_size_radar_enemy = 0;
	
	public static int all_size_radar_allies = 0;
	
	public static int all_size_reactor_enemy = 0;
	
	public static int all_size_turrets_enemy = 0;
	
	public static int[] tanks_type_enemy = null;
	
	public static int[] tanks_type_ally = null;

    public static final int coin_id = 100, star_id = 101;

    /*public static int getMaxTanks() {
    	return max_tanks;
	}

	public static void setMaxTanks(int max_tanks) {
    	ObjectVariables.max_tanks = max_tanks;
	}

	public static int getMaxTanks(short f) {
    	switch (f) {
			case tank_enemy: return max_tanks_enemy;
			case tank_unity: return max_tanks_ally;
			default: return -1;
		}
	}

	public static void setMaxTanks(int max_tanks, short f) {
		switch (f) {
			case tank_enemy: max_tanks_enemy = max_tanks; return;
			case tank_unity: max_tanks_ally = max_tanks; return;
			default: return;
		}
	}

	public static int getMaxTanks(short f) {
		switch (f) {
			case tank_enemy: return max_tanks_enemy;
			case tank_unity: return max_tanks_ally;
			default: return -1;
		}
	}

	public static void setMaxTanks(int max_tanks, short f) {
		switch (f) {
			case tank_enemy: max_tanks_enemy = max_tanks; return;
			case tank_unity: max_tanks_ally = max_tanks; return;
			default: return;
		}
	}*/
}
