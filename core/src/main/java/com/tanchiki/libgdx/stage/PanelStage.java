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
import com.tanchiki.libgdx.util.ObjectClass;
import com.tanchiki.libgdx.util.Settings;
import com.tanchiki.libgdx.util.WeaponData;

public class PanelStage extends Stage {
    public Group toasts;
    public PanelStage() {
        ObjectClass.PanelStage = this;
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
            int keycode;
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (GameStage.getInstance().TankUser != null)
                    switch (keycode) {
                        case Input.Keys.A:
                            this.keycode = keycode;
                            GameStage.getInstance().TankUser.setMotion(TankUser.Vec.LEFT); break;
                        case Input.Keys.W:
                            this.keycode = keycode;
                            GameStage.getInstance().TankUser.setMotion(TankUser.Vec.UP); break;
                        case Input.Keys.S:
                            this.keycode = keycode;
                            GameStage.getInstance().TankUser.setMotion(TankUser.Vec.DOWN); break;
                        case Input.Keys.D:
                            this.keycode = keycode;
                            GameStage.getInstance().TankUser.setMotion(TankUser.Vec.RIGHT); break;
                        case Input.Keys.ESCAPE:
                            if (!Settings.start_game || Settings.pause) break;
                            Settings.pauseView = true;
                            Settings.pause = true;
                            break;
                        case Input.Keys.Q:
                            GameStage.getInstance().TankUser.enterHangar();
                            break;
                        case Input.Keys.E:
                            ObjectClass.WeaponMenuStage.showMenu();
                            break;
                        case Input.Keys.R:
                            GameStage.getInstance().TankUser.doRepair();
                            break;
                        case Input.Keys.NUMPAD_1:
                            GameStage.getInstance().TankUser.AI.BULLET(); break;
                        case Input.Keys.NUMPAD_2:
                            GameStage.getInstance().TankUser.AI.MINES(); break;
                        case Input.Keys.NUMPAD_3:
                            GameStage.getInstance().TankUser.AI.AIR(); break;

                    }
                return super.keyDown(event, keycode);
            }

            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                if (GameStage.getInstance().TankUser != null && this.keycode == keycode)
                    GameStage.getInstance().TankUser.setMotion(TankUser.Vec.NONE);
                return super.keyUp(event, keycode);
            }
        });
    }

    public void addToast(String text) {
        toasts.addActor(new ToastGame(text));
    }
	
	private class PauseButton extends TextButton {
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
				setPosition(0, Gdx.graphics.getHeight() - getHeight() - Gdx.graphics.getHeight() / 3);
			}
		}
	}
}
