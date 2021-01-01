package com.tanchiki.libgdx.stage;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.tanchiki.libgdx.model.ui.StoreMenu;
import com.tanchiki.libgdx.util.ObjectClass;
import com.tanchiki.libgdx.util.Settings;

public class StoreStage extends Stage {
    private StoreMenu storeMenu = new StoreMenu();
	
    public StoreStage() {
        ObjectClass.StoreStage = this;
    }

    public void show() {
        Settings.store_menu = true;
		Settings.pause = true;
        addActor(storeMenu);
    }

    @Override
    public boolean keyDown(int keyCode) {
        if (Settings.store_menu)
            switch (keyCode) {
                case Input.Keys.BACK:
                case Input.Keys.ESCAPE:
                    Settings.store_menu = false;
                    Settings.pause = false;
                    clear();
            }
        return super.keyDown(keyCode);
    }
}
