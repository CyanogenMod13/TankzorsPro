package com.tanchiki.libgdx.model.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.tanchiki.libgdx.util.FontLoader;

public class ListViewGroup extends Table {
    Table list;
    ScrollPane scroll;

    public ListViewGroup() {
        setSize(Gdx.graphics.getWidth() - 200, Gdx.graphics.getHeight() - 200);
        setPosition(Gdx.graphics.getWidth() / 2 - getWidth() / 2, Gdx.graphics.getHeight() / 2 - getHeight() / 2);
        //setFillParent(true);
        list = new Table();
        list.setSize(getWidth() / 2, getHeight() / 2);
        //list.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture("texture/ui/menu_back_2.png"))));
    }

    public void addItem(String... s) {
        //ListElement l[] = new ListElement[s.length];
        for (int i = 0; i < s.length; i++) {
            add(new ListElement(s[i])).fill().left().row();
        }
        add(list).fill().center().left();
    }

    private class ListElement extends TextButton {
        public ListElement(String s) {
            super(s, new TextButtonStyle(null, new TextureRegionDrawable(new TextureRegion(new Texture("texture/ui/billet.png"))), null, FontLoader.f24));
            setSize((Gdx.graphics.getWidth() - 100) / 2, (Gdx.graphics.getHeight() - 100) / 2);
        }
    }
}
