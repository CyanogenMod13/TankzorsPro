package com.tanchiki.libgdx.util;

import com.tanchiki.libgdx.screens.GameScreen;
import com.tanchiki.libgdx.stage.*;

public class ObjectClass {
    //public static MainActivity MainActivity = null;

    public static GameStage GameStage = null;

    public static StoreStage StoreStage = null;

    public static GameScreen GameScreen = null;

    public static MenuStage MenuStage = null;

    //public static CoreView CoreView = null;

    public static TextureLoader TextureLoader = null;

    public static SkinLoader SkinLoader = null;

    //public static GameScreen boot = new GameScreen();

    //public static LogoBoot logo = new LogoBoot();

    public static PanelStage PanelStage = null;

    public static WeaponMenuStage WeaponMenuStage = null;

    //public static Viewport viewport;

    public static void init() {
        //viewport = new ScalingViewport(Scaling.stretch,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        //viewport.setScreenBounds(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
    }
}
