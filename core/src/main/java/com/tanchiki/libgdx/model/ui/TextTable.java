package com.tanchiki.libgdx.model.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.tanchiki.libgdx.util.FontLoader;
import com.tanchiki.libgdx.util.ObjectClass;

public class TextTable extends TextArea {
    public Array<String> texts = new Array<String>();

    public TextTable(String... t) {
        super("", new TextArea.TextFieldStyle(FontLoader.f20, Color.WHITE, null, null, new TextureRegionDrawable(ObjectClass.GameStage.TextureLoader.getIcons()[0][14])));
        setSize(Gdx.graphics.getWidth() / 3, Gdx.graphics.getHeight() / 5);
        setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() - getHeight());
        texts.addAll(t);
        setTouchable(Touchable.disabled);
        //addAction(Actions.moveTo(100,100,0.1f,Interpolation.bounce));
    }

    float time = 5;
    int index = 0;

    @Override
    public void act(float delta) {
        if (index < texts.size) {
            if (time > 5) {
                setText(texts.get(index));
                index++;
                time = 0;
            }
        } else {
            remove();
        }
        time += delta;
        // TODO: Implement this method
        super.act(delta);
    }
}
