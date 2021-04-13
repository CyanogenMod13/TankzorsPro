package com.tanchiki.libgdx.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class UIUtils {
    public static TextureRegion[] r = TextureLoader.getInstance().getIcons()[0];

    public static TextureRegionDrawable d = new TextureRegionDrawable(new TextureRegion(new Texture("texture/ui/billet.png")));

    public static Button newButton(String name) {
        TextButton button = new TextButton(name, new TextButton.TextButtonStyle(new NinePatchDrawable(new NinePatch(r[14], 7, 7, 7, 7)), d, new NinePatchDrawable(new NinePatch(r[14], 7, 7, 7, 7)), FontLoader.f24));
        return button;
    }

    public static Label newLabel(String name) {
        return new Label(name, new Label.LabelStyle(FontLoader.f24, Color.WHITE));
    }

    public static Dialog newDialog(String name, Button... buttons) {
        Dialog dialog = new Dialog("", new Window.WindowStyle(FontLoader.f16, Color.WHITE, new TextureRegionDrawable(TextureLoader.getInstance().getIcons()[0][14])));
        dialog.text(newLabel(name));
        dialog.text(newLabel(""));
        for (Button button : buttons) dialog.button(button);
        return dialog;
    }

    public static Dialog newMassageDialog(String name) {
        return newDialog(String.format("     %s     ", name), newButton("ОК"));
    }
}
