package com.tanchiki.libgdx.util;

public class ObjectVarable {
    public static float global_scale;

    public static final int size_block = 1;

    public static final short tank_unity = 0x0001;

    public static final short tank_enemy = -0x0001;

    public static int max_tanks = 0;
	
	public static int max_tanks_enemy = 0;
	
	public static int max_tanks_ally = 0;
	
	public static int level_diffculty = 0;

    public static int size_enemy = 0;

    public static int size_unity = 0;

    public static int all_size_enemy = 0;

    public static int all_size_unity = 0;
	
	public static int all_size_boss_enemy = 0;
	
	public static int all_size_boss_ally = 0;
	
	public static int all_size_radar_enemy = 0;
	
	public static int all_size_radar_allies = 0;
	
	public static int all_size_reactor_enemy = 0;
	
	public static int all_size_turrets_enemy = 0;
	
	public static int[] tanks_type_enemy = null;
	
	public static int[] tanks_type_ally = null;
	
	public static boolean makeWinByTrigger = false;

    //public static int size_of_enemy_attack = 0;

    //public static int size_of_enemy_protecting = 0;

    public static final int coin_id = 100, star_id = 101;

    public static String[] name_weapon = {
            "Снаряд",
            "Плазма",
            "Двойной снаряд",
            "Двойная плазма",
            "Легкий бронеснаряд",
            "Тежолый бронеснаряд",
            "Артирелия",
            "Боеголовка",
            "Авиаудар",
            "Мины",
            "Мина M-1",
            "Мина M-2",
            "Динамит",
            "Динамит ДТ-1",
            "Динамит ДТ-2",
            "Часовая бомба ДТ-3",
            "Бронепластины",
            "Постоянная броня",
            "Временная броня"},
            about_weapon = {
                    "",
                    "Основное оружие боя, имеет малую скорость и малый урон.\nВ принцепе, ни чего особенного ?\n\nУрон: 1HP\nЦена: 10 монет за 10 шт.",
                    "Сгусток ионов, под высокой температурой, движушися с большой скоростю в сторону противника(ему будет не сладко??).\nСамое скрострельбное оружие, но урон так и остался прежним?\n\nУрон: 1HP\nЦена: 15 монет за 10 шт.",
                    "Наши новейшие технологии позволили сделать двойную обойму, да так, что-бы он могла выстреливать с одноствольного дула(не спрашивайте, как это так, это всё — \"новейшие технологии\".\n\nУрон: 2\nЦена : 20 монет за 10 шт."
            };

    public static void init() {
        global_scale = 1;//(1280f / 720f) * (Gdx.graphics.getWidth() / Gdx.graphics.getHeight());
    }
}
