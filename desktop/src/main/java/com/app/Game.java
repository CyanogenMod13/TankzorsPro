package com.app;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.tanchiki.libgdx.screens.GameScreen;

public class Game {
    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.height *= 2;
        cfg.width *= 2;
        //cfg.fullscreen = true;
        cfg.title = "Tankzors Pro";
        new LwjglApplication(new GameScreen(), cfg);
    }
}
