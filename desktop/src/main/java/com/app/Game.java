package com.app;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.tanchiki.libgdx.screens.GameScreen;

public class Game {
    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.height *= 1.5;
        cfg.width *= 1.5;
        //cfg.fullscreen = true;
        cfg.title = "Tankzors Pro";
        cfg.addIcon("texture/ui/icon.png", Files.FileType.Internal);
        new LwjglApplication(new GameScreen(), cfg);
    }
}
