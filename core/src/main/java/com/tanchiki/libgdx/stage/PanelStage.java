package com.tanchiki.libgdx.stage;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import com.badlogic.gdx.utils.*;
import com.tanchiki.libgdx.model.tanks.*;
import com.tanchiki.libgdx.model.ui.*;
import com.tanchiki.libgdx.util.*;
import com.badlogic.gdx.graphics.g2d.*;

public class PanelStage extends Stage {
    public Group toasts;
    Array<TextTable> texts = new Array<TextTable>();

    //PlayPad.Knob knob = null;
    public PanelStage() {
        ObjectClass.PanelStage = this;
        toasts = new Group();
        addActor(new CornerOverlayer());
        addActor(new Buttons());
        addActor(new TSButton());
        addActor(new InfoPanel());
        MiniMap map = new MiniMap();
        map.setPosition(0, getHeight() - map.getHeight());
        addActor(map);
        addActor(toasts);
		addActor(new PauseButton());
        addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                switch (keycode) {
                    case Input.Keys.A:
                        GameStage.getInstance().TankUser.setMotion(TankUser.Vec.LEFT); break;
                    case Input.Keys.W:
                        GameStage.getInstance().TankUser.setMotion(TankUser.Vec.UP); break;
                    case Input.Keys.S:
                        GameStage.getInstance().TankUser.setMotion(TankUser.Vec.DOWN); break;
                    case Input.Keys.D:
                        GameStage.getInstance().TankUser.setMotion(TankUser.Vec.RIGHT); break;
                }
                return super.keyDown(event, keycode);
            }

            @Override
            public boolean keyUp(InputEvent event, int keycode) {
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
