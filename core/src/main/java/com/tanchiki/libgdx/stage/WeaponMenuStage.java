package com.tanchiki.libgdx.stage;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.tanchiki.libgdx.model.ui.WeaponMenu.Container;

public class WeaponMenuStage extends Stage {
    private static WeaponMenuStage weaponMenuStage = null;

    public static WeaponMenuStage getInstance() {
        if (weaponMenuStage == null) weaponMenuStage = new WeaponMenuStage();
        return weaponMenuStage;
    }

    private WeaponMenuStage() {
        WeaponMenuStage.weaponMenuStage = this;
    }

    public void showMenu() {
        addActor(new Container());
    }

    public void hideMenu() {
        clear();
    }
}
