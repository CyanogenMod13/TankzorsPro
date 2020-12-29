package com.tanchiki.libgdx.stage;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.tanchiki.libgdx.model.ui.MiniMap;
import com.tanchiki.libgdx.util.Settings;

public class MiniMapStage extends Stage {
    public MiniMapStage() {
        //super(ObjectClass.viewport,new SpriteBatch());
        MiniMap map = new MiniMap();
        //addActor(map);
    }

    @Override
    public boolean keyDown(int keyCode) {
        if (keyCode == Input.Keys.BACK) {
            hide();
        }
        // TODO: Implement this method
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
