package com.tanchiki.libgdx.stage;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.tanchiki.libgdx.model.tanks.TankUser;
import com.tanchiki.libgdx.model.ui.*;
import com.tanchiki.libgdx.util.Settings;
import com.tanchiki.libgdx.util.WeaponData;

public class PanelStage extends Stage {
    private static PanelStage panelStage = null;

    public static PanelStage getInstance() {
        if (panelStage == null) panelStage = new PanelStage();
        return panelStage;
    }

    public Group toasts;

    private PanelStage() {
        PanelStage.panelStage = this;

        toasts = new Group();
        addActor(new CornerOverlayer());
        addActor(new TSButton());
        if (!(Gdx.app.getType().equals(Application.ApplicationType.Desktop) ||
                Gdx.app.getType().equals(Application.ApplicationType.WebGL))) {
            addActor(new Buttons());
            addActor(new PauseButton());
        }
        addActor(new InfoPanel());
        MiniMap map = new MiniMap();
        map.setPosition(0, getHeight() - map.getHeight());
        addActor(map);
        addActor(toasts);

        addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (GameStage.getInstance().tankUser != null)
                    switch (keycode) {
                        case Input.Keys.A:
                            GameStage.getInstance().tankUser.setStateMotion(TankUser.Vec.LEFT);
                            break;
                        case Input.Keys.W:
                            GameStage.getInstance().tankUser.setStateMotion(TankUser.Vec.UP);
                            break;
                        case Input.Keys.S:
                            GameStage.getInstance().tankUser.setStateMotion(TankUser.Vec.DOWN);
                            break;
                        case Input.Keys.D:
                            GameStage.getInstance().tankUser.setStateMotion(TankUser.Vec.RIGHT);
                            break;
                    }
                return super.keyDown(event, keycode);
            }

            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                if (GameStage.getInstance().tankUser != null && keycode == GameStage.getInstance().tankUser.getCurrentStateMotion().key)
                    GameStage.getInstance().tankUser.setStateMotion(TankUser.Vec.NONE);
                return super.keyUp(event, keycode);
            }
        });
        addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                switch (keycode) {
                    case Input.Keys.ESCAPE:
                        if (!Settings.start_game || Settings.pause) break;
                        Settings.pauseView = true;
                        Settings.pause = true;
                        break;
                    case Input.Keys.Q:
                        GameStage.getInstance().tankUser.enterHangar();
                        break;
                    case Input.Keys.E:
                        WeaponMenuStage.getInstance().showMenu();
                        break;
                    case Input.Keys.R:
                        GameStage.getInstance().tankUser.doRepair();
                        break;
                }
                return super.keyDown(event, keycode);
            }
        });
        addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (GameStage.getInstance().tankUser != null)
                    switch (keycode) {
                        case Input.Keys.NUMPAD_1:
                            GameStage.getInstance().tankUser.setStateFire(TankUser.Fire.BULLET);
                            break;
                        case Input.Keys.NUMPAD_2:
                            GameStage.getInstance().tankUser.defaultAI.MINES();
                            break;
                        case Input.Keys.NUMPAD_3:
                            GameStage.getInstance().tankUser.setStateFire(TankUser.Fire.AIR);
                            break;
                    }
                return super.keyDown(event, keycode);
            }

            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                if (GameStage.getInstance().tankUser != null && keycode == GameStage.getInstance().tankUser.getCurrentStateFire().key)
                    GameStage.getInstance().tankUser.setStateFire(TankUser.Fire.NONE);
                return super.keyUp(event, keycode);
            }
        });
    }

    public void addToast(String text) {
        toasts.addActor(new ToastGame(text));
    }

    private static class PauseButton extends TextButton {
        TextureRegionDrawable reg1 = new TextureRegionDrawable(new Texture("texture/ui/black/pause.png"));
        TextureRegionDrawable reg2 = new TextureRegionDrawable(new Texture("texture/ui/black/pause_h.png"));

        public PauseButton() {
            super("", new TextButton.TextButtonStyle(null, null, null, new BitmapFont()));
            getStyle().up = reg1;
            getStyle().checked = reg1;
            getStyle().down = reg2;

            setSize(Gdx.graphics.getHeight() / 7.2f, Gdx.graphics.getHeight() / 7.2f);
            addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent e, float x, float y) {
                    super.clicked(e, x, y);
                    Settings.pauseView = true;
                    Settings.pause = true;
                }
            });
        }

        @Override
        public void act(float delta) {
            if (WeaponData.radar == 0) {
                setPosition(0, Gdx.graphics.getHeight() - getHeight());
            } else {
                setPosition(0, Gdx.graphics.getHeight() - getHeight() - Gdx.graphics.getHeight() / 3f);
            }
        }
    }
}
