package com.tanchiki.libgdx.stage;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.tanchiki.libgdx.model.ui.WeaponMenu.Container;
import com.tanchiki.libgdx.util.ObjectClass;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.math.*;

public class WeaponMenuStage extends Stage {
    public WeaponMenuStage() {
        //super(ObjectClass.viewport,new SpriteBatch());
        //setDebugAll(true);
        ObjectClass.WeaponMenuStage = this;
    }

    public void showMenu() {
        //Gdx.input.setInputProcessor(this);
		Container container = new Container();
		//container.setX(container.x - container.getWidth());
		//container.addAction(Actions.moveTo(0, container.getY(), 1f, Interpolation.fade));
        addActor(container);
    }

    public void hideMenu() {
        clear();
    }
}
