package com.tanchiki.libgdx.stage;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.tanchiki.libgdx.util.ObjectClass;
import com.tanchiki.libgdx.util.Settings;
import com.tanchiki.libgdx.model.ui.*;

public class StoreStage extends Stage {
    public static StoreStage s = null;
    //public Store c;
    //public GestureDetector gesture;
    private boolean a = true;
    public Group root = new Group();

	private StoreMenu storeMenu = new StoreMenu();
	
    public StoreStage() {
        //super(ObjectClass.viewport,new SpriteBatch());
        //setDebugAll(true);
        ObjectClass.StoreStage = this;
        s = this;
        /*try {
            c = new Store();
        } catch (NoSuchFieldException e) {
        }
        addActor(c);*/
    }

    public void show() {
        Settings.store_menu = true;
		Settings.pause = true;
        addActor(storeMenu);
    }

    @Override
    public boolean keyDown(int keyCode) {
        if (keyCode == Input.Keys.BACK) {
            //c.hide();
			Settings.store_menu = false;
			Settings.pause = false;
			clear();
        }
        // TODO: Implement this method
        return super.keyDown(keyCode);
    }
}
