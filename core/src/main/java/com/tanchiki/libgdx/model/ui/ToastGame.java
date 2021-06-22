package com.tanchiki.libgdx.model.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.tanchiki.libgdx.util.FontLoader;
import com.tanchiki.libgdx.util.ObjectVariables;

public class ToastGame extends Label {
    public ToastGame(String text) {
        super(text, new Label.LabelStyle(FontLoader.f24, Color.WHITE));//, null, null, ));
        setSize(ObjectVariables.size_block * 2 * 3, ObjectVariables.size_block * 2);
        setPosition(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f, Align.center);
        setAlignment(Align.center);
        setVisible(false);
    }

    float time = 0;

    @Override
    public void act(float delta) {
        if (getParent().getChildren().get(0) == this) {
            setVisible(true);
            time += delta;
            if (time > 5) remove();
        }
        super.act(delta);
    }
}
