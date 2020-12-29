package com.app;

import com.badlogic.gdx.backends.lwjgl.LwjglAWTCanvas;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.tanchiki.libgdx.graphics.GameActor;
import com.tanchiki.libgdx.screens.GameScreen;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Game {

    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.height = 600;
        cfg.width = 800;
        GameScreen gameScreen = new GameScreen();
        //LwjglAWTCanvas canvas = new LwjglAWTCanvas(gameScreen);
        new LwjglApplication(gameScreen, cfg);
    }

}
