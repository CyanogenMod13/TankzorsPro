package com.tanchiki.libgdx.model.ui.WeaponMenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.tanchiki.libgdx.util.WeaponData;

public class Container extends Table {
    public float x, y, h;

    public Container() {
        h = Gdx.graphics.getHeight() / 10f;
        x = 0;
        y = Gdx.graphics.getHeight() - h;

        createElements(WeaponData.Type.light_bullet, WeaponData.light_bullet);
        y -= h;

        if (WeaponData.plazma != 0) {
            createElements(WeaponData.Type.plazma, WeaponData.plazma);
            y -= h;
        }

        if (WeaponData.double_light_bullet != 0) {
            createElements(WeaponData.Type.double_light_bullet, WeaponData.double_light_bullet);
            y -= h;
        }

        if (WeaponData.double_palzma != 0) {
            createElements(WeaponData.Type.double_plazma_bullet, WeaponData.double_palzma);
            y -= h;
        }

        if (WeaponData.bronet_bullet != 0) {
            createElements(WeaponData.Type.bronet_bullet, WeaponData.bronet_bullet);
            y -= h;
        }

        if (WeaponData.bronet_bullet2 != 0) {
            createElements(WeaponData.Type.bronet_bullet2, WeaponData.bronet_bullet2);
            y -= h;
        }

        if (WeaponData.rocket != 0) {
            createElements(WeaponData.Type.rocket, WeaponData.rocket);
            y -= h;
        }

        if (WeaponData.shield != 0) {
            createElements(WeaponData.Type.shield, WeaponData.shield);
            y -= h;
        }

        if (WeaponData.air != 0) {
            createElements(WeaponData.Type.air, WeaponData.air);
            y -= h;
        }
        if (WeaponData.art != 0) {
            createElements(WeaponData.Type.art, WeaponData.art);
            y -= h;
        }

        if (WeaponData.mine1 != 0) {
            createElements(WeaponData.Type.mine1, WeaponData.mine1);
            y -= h;
        }

        if (WeaponData.mine2 != 0) {
            createElements(WeaponData.Type.mine2, WeaponData.mine2);
            y -= h;
        }

        check();
        if (WeaponData.tnt1 != 0) {
            createElements(WeaponData.Type.tnt1, WeaponData.tnt1);
            y -= h;
        }

        check();
        if (WeaponData.tnt2 != 0) {
            createElements(WeaponData.Type.tnt2, WeaponData.tnt2);
            y -= h;
        }

        check();
        if (WeaponData.tnt3 != 0) {
            createElements(WeaponData.Type.tnt3, WeaponData.tnt3);
            y -= h;
        }

        check();
        if (WeaponData.live != 0) {
            createElements(WeaponData.Type.live, WeaponData.live);
            y -= h;
        }

        check();
        if (WeaponData.speed != 0) {
            createElements(WeaponData.Type.speed, WeaponData.speed);
            y -= h;
        }

        check();
        if (WeaponData.time != 0) {
            createElements(WeaponData.Type.time, WeaponData.time);
            y -= h;
        }

        check();
        if (WeaponData.fix != 0) {
            createElements(WeaponData.Type.fix, WeaponData.fix);
            y -= h;
        }
    }

    private void check() {
        if (y <= -h) {
            y = Gdx.graphics.getHeight() - h;
            x += h;
        }
    }

    public Weapon createElements(int icon, int count) {
        //if (count == 0) return null;
        Weapon w = new Weapon(icon, count);
        w.setSize(h, h);
        w.setPosition(x, y);
        addActor(w);
        return w;
    }
}
