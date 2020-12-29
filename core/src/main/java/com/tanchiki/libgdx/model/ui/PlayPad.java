package com.tanchiki.libgdx.model.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.tanchiki.libgdx.graphics.GameActor;

public class PlayPad extends GameActor {
    Sprite baseknob;

    public PlayPad() {
        baseknob = new Sprite(new Texture("texture/ui/knob.png"));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        setSize(300, 300);
        setPosition(50, 50);
        baseknob.setSize(getWidth(), getHeight());
        baseknob.setPosition(getX(), getY());
        baseknob.draw(batch);
        // TODO: Implement this method
        super.draw(batch, parentAlpha);

    }

    public static class Knob extends GameActor {
        Sprite knob = new Sprite(new Texture("texture/ui/knob.png"));
        boolean draw = true;

        public Knob() {
            //super((new Texture("texture/ui/knob.png")));
            setSize(150, 150);
            setCenterPosition(200, 200);

            //addListener(new Listener());
        }

        @Override
        public void draw(Batch batch, float parentAlpha) {
            // TODO: Implement this method
            super.draw(batch, parentAlpha);
            knob.setSize(getWidth(), getHeight());
            knob.setCenter(getCenterX(), getCenterY());
            knob.draw(batch);
        }

        private class Listener extends InputListener {

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                // TODO: Implement this method
                super.touchDragged(event, x, y, pointer);
                setSize(150, 150);
                setCenterPosition(x, y);
                knob.setSize(getWidth(), getHeight());
                knob.setCenter(getCenterX(), getCenterY());
            }

        }
    }


}
