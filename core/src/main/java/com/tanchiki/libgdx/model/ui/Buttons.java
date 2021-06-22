package com.tanchiki.libgdx.model.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.tanchiki.libgdx.model.tanks.TankUser;
import com.tanchiki.libgdx.stage.GameStage;

public class Buttons extends Group {
    SpriteDrawable t, t1;
    TextButton button1;
    TextButton button2;
    TextButton button3;
    TextButton button4;
    TextButton button5;
    TextButton.TextButtonStyle s1;
    GameStage gameStage = GameStage.getInstance();
    float size = Gdx.graphics.getHeight() / 7.2f;

    public Buttons() {
        Texture texture = new Texture("texture/ui/black/play.png");
        Texture texture1 = new Texture("texture/ui/black/play_h.png");

        Sprite sp1 = new Sprite(texture);
        Sprite sp2 = new Sprite(texture1);
        sp1.rotate90(false);
        sp2.rotate90(false);
        sp1.rotate90(false);
        sp2.rotate90(false);
        t = new SpriteDrawable(sp1);
        t1 = new SpriteDrawable(sp2);

        setSize(size * 3, size * 3);
        setPosition(0, 0);

        //addActor(new JoyStick());
        button1 = new TextButton("", new TextButton.TextButtonStyle(t, t1, t, new BitmapFont()));
        button1.setSize(size, size);
        button1.setPosition(0, button1.getHeight());
        button1.addListener(new ClickListener() {

            final TankUser.Vec current = TankUser.Vec.LEFT;

            private void move() {
                gameStage.tankUser.setStateMotion(current);
            }

            private void stop() {
                gameStage.tankUser.setStateMotion(TankUser.Vec.NONE);
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                move();
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                stop();
                super.touchUp(event, x, y, pointer, button);
            }

            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == current.key) move();
                return super.keyDown(event, keycode);
            }

            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                stop();
                return super.keyUp(event, keycode);
            }
        });
        addActor(button1);

        sp1 = new Sprite(texture);
        sp2 = new Sprite(texture1);

        sp1.rotate90(false);
        sp2.rotate90(false);

        t = new SpriteDrawable(sp1);
        t1 = new SpriteDrawable(sp2);

        //this.add(button1).center().pad(50)
        button2 = new TextButton("", new TextButton.TextButtonStyle(t, t1, t, new BitmapFont()));
        button2.setSize(size, size);
        button2.setPosition(button2.getWidth(), button2.getHeight() * 2);
        button2.addListener(new ClickListener() {

            final TankUser.Vec current = TankUser.Vec.UP;

            private void move() {
                gameStage.tankUser.setStateMotion(current);
            }

            private void stop() {
                gameStage.tankUser.setStateMotion(TankUser.Vec.NONE);
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                move();
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                stop();
                super.touchUp(event, x, y, pointer, button);
            }

            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == current.key) move();
                return super.keyDown(event, keycode);
            }

            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                stop();
                return super.keyUp(event, keycode);
            }
        });
        addActor(button2);
        //button2.setRotation(90);
        //this.add(button2).center().pad(50).top().row();

        sp1 = new Sprite(texture);
        sp2 = new Sprite(texture1);


        t = new SpriteDrawable(sp1);
        t1 = new SpriteDrawable(sp2);

        button3 = new TextButton("", new TextButton.TextButtonStyle(t, t1, t, new BitmapFont()));
        button3.setSize(size, size);
        button3.setPosition(button3.getWidth() * 2, button3.getHeight());
        button3.addListener(new ClickListener() {

            final TankUser.Vec current = TankUser.Vec.RIGHT;

            private void move() {
                gameStage.tankUser.setStateMotion(current);
            }

            private void stop() {
                gameStage.tankUser.setStateMotion(TankUser.Vec.NONE);
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                move();
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                stop();
                super.touchUp(event, x, y, pointer, button);
            }

            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == current.key) move();
                return super.keyDown(event, keycode);
            }

            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                stop();
                return super.keyUp(event, keycode);
            }
        });
        //button3.setRotation(180);
        addActor(button3);
        //this.add(button3).center().pad(50);

        sp1 = new Sprite(texture);
        sp2 = new Sprite(texture1);

        sp1.rotate90(true);
        sp2.rotate90(true);

        t = new SpriteDrawable(sp1);
        t1 = new SpriteDrawable(sp2);

        button4 = new TextButton("", new TextButton.TextButtonStyle(t, t1, t, new BitmapFont()));
        button4.setSize(size, size);
        button4.setPosition(button4.getWidth(), 0);
        button4.addListener(new ClickListener() {

            final TankUser.Vec current = TankUser.Vec.DOWN;

            private void move() {
                gameStage.tankUser.setStateMotion(current);
            }

            private void stop() {
                gameStage.tankUser.setStateMotion(TankUser.Vec.NONE);
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                move();
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                stop();
                super.touchUp(event, x, y, pointer, button);
            }

            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == current.key) move();
                return super.keyDown(event, keycode);
            }

            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                stop();
                return super.keyUp(event, keycode);
            }
        });
        addActor(button4);

    }
}
