package com.tanchiki.libgdx.stage;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.tanchiki.libgdx.model.ui.MiniMap;
import com.tanchiki.libgdx.util.Settings;

public class MiniMapStage extends Stage {
    private static MiniMapStage miniMapStage = null;

    public static MiniMapStage getInstance() {
        if (miniMapStage == null) miniMapStage = new MiniMapStage();
        return miniMapStage;
    }

    private MiniMapStage() {
        miniMapStage = this;
        MiniMap map = new MiniMap();
    }

    @Override
    public boolean keyDown(int keyCode) {
        if (keyCode == Input.Keys.BACK) {
            hide();
        }
        return super.keyDown(keyCode);
    }

    public static void show() {
        Settings.pause = true;
        Settings.show_map = true;
    }

    public static void hide() {
        Settings.pause = false;
        Settings.show_map = false;
    }
}
