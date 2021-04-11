package com.tanchiki.libgdx.screens;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.kotcrab.vis.ui.VisUI;
import com.tanchiki.libgdx.server.GameServer;
import com.tanchiki.libgdx.stage.*;
import com.tanchiki.libgdx.util.*;

public class GameScreen implements ApplicationListener {
    GameStage gameStage;
    PanelStage panelStage;
    MenuStage menuStage;
    MiniMapStage miniMapStage;
    StoreStage storeStage;
    WeaponMenuStage weaponMenuStage;
	LoadingStage LoadingStage;
	PauseStage pauseStage;
	
    public InputMultiplexer input;
    public boolean isLoading = true;

	@Override
	public void create() {
        FontLoader.init();
		TextureLoader.load();
		SoundLoader.load();

		LoadingStage = new LoadingStage();
	}

	@Override
	public void resize(int i, int i1) {

	}

	@Override
    public void render() {
		if (TextureLoader.manager.update() && SoundLoader.manager.update() && isLoading) {
			init();
			isLoading = false;
			System.out.println(isLoading);
		}
		
        Gdx.gl20.glClearColor(0, 0, 0, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //Gdx.gl20.glClear(GL20.GL_DEPTH_BUFFER_BIT);
		
		if (TextureLoader.manager.update() && SoundLoader.manager.update() && gameStage != null)
			draw();
		else {
			LoadingStage.act();
			LoadingStage.draw();
		}
    }

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {

	}

	private void draw() {
		final float fps = Gdx.graphics.getDeltaTime();
		if (gameStage == null) return;
		gameStage.drawStage(fps);

		if (!Settings.pause && Settings.start_game) {
			panelStage.getRoot().setTouchable(Touchable.enabled);
			panelStage.act();
			panelStage.draw();
		} else {
			panelStage.getRoot().setTouchable(Touchable.disabled);
		}

		if (Settings.show_map) {
			miniMapStage.act();
			miniMapStage.draw();
		}

		weaponMenuStage.act();
		weaponMenuStage.draw();

		if (Settings.show_main_menu) {
			menuStage.getRoot().setTouchable(Touchable.enabled);
			menuStage.act();
			menuStage.draw();
		} else {
			menuStage.getRoot().setTouchable(Touchable.disabled);
		}

		if (Settings.store_menu) {
			storeStage.getRoot().setTouchable(Touchable.enabled);
			storeStage.act();
			storeStage.draw();
		} else {
			storeStage.getRoot().setTouchable(Touchable.disabled);
		}
		if (Settings.pause && !Settings.pauseView) {
			AboutStage.getInstance().act();
			AboutStage.getInstance().draw();
		}
		if (Settings.pauseView) {
			pauseStage.getRoot().setTouchable(Touchable.enabled);
			pauseStage.act();
			pauseStage.draw();
		} else {
			pauseStage.getRoot().setTouchable(Touchable.disabled);
		}
	}

    private void init() {
		if (!VisUI.isLoaded()) VisUI.load(VisUI.SkinScale.X2);

        TextureLoader.init();

        initStage();
        initInput();
    }

    private void initStage() {
        gameStage = GameStage.getInstance();
        panelStage = PanelStage.getInstance();
        weaponMenuStage = WeaponMenuStage.getInstance();
        menuStage = MenuStage.getInstance();
        storeStage = StoreStage.getInstance();
        miniMapStage = new MiniMapStage();
		pauseStage = PauseStage.getInstance();
		GameServer.getInstance().waitForClient();
    }

    private void initInput() {
        input = new InputMultiplexer();

        input.addProcessor(pauseStage);
        input.addProcessor(menuStage);
        input.addProcessor(weaponMenuStage);
		input.addProcessor(AboutStage.getInstance());
        input.addProcessor(panelStage);
        input.addProcessor(storeStage);
        if (Settings.edit_map_mode) input.addProcessor(new GestureDetector(gameStage.listener));
        input.addProcessor(gameStage);
        input.addProcessor(miniMapStage);

        Gdx.input.setCatchBackKey(true);
        Gdx.input.setInputProcessor(input);
    }
}
