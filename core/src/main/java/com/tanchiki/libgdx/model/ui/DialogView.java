package com.tanchiki.libgdx.model.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.tanchiki.libgdx.stage.GameStage;
import com.tanchiki.libgdx.util.FontLoader;
import com.tanchiki.libgdx.util.TextureLoader;

import java.util.LinkedList;

public class DialogView extends Table {
    private Window window;

    private TextArea textArea;

    private TextureRegion[] r = TextureLoader.getInstance().getIcons()[0];

    private TextureRegionDrawable d = new TextureRegionDrawable(new TextureRegion(new Texture("texture/ui/billet.png")));

    private LinkedList<TextButton> listButton = new LinkedList<>();

    public DialogView() {
        setSize(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 3);
        setPosition(Gdx.graphics.getWidth() - getWidth() / 2, Gdx.graphics.getHeight() - getHeight() / 2);

        window = new Window("Empty", new Window.WindowStyle(FontLoader.f24, Color.WHITE, new TextureRegionDrawable(r[14])));
        //window.setTitleAlignment(Align.bottom);
        add(window).row();

        textArea = new TextArea("Empty", new TextArea.TextFieldStyle(FontLoader.f16, Color.WHITE, null, null, new NinePatchDrawable(new NinePatch(r[14], 7, 7, 7, 7))));
        textArea.setTouchable(Touchable.disabled);
        window.row().fill();
        window.add(textArea).expand();
        //.width(Gdx.graphics.getWidth()/2).height(Gdx.graphics.getHeight()/3);
        row();

    }

    public void setTitleText(String s) {
        window.getTitleLabel().setText(s);
    }

    public void setTextArea(String s) {
        textArea.setText(s);
    }

    public void addButton(String name, OnClickListener listener) {
        TextButton button = new TextButton(name, new TextButton.TextButtonStyle(new NinePatchDrawable(new NinePatch(r[14], 7, 7, 7, 7)), d, new NinePatchDrawable(new NinePatch(r[14], 7, 7, 7, 7)), FontLoader.f24));
        button.addListener(listener);


        addActor(button);
    }

    public static abstract class OnClickListener extends ClickListener {

        @Override
        public void clicked(InputEvent event, float x, float y) {
            // TODO: Implement this method
            super.clicked(event, x, y);
            OnClick(event, x, y);
        }

        public abstract void OnClick(InputEvent event, float x, float y);
    }
}
