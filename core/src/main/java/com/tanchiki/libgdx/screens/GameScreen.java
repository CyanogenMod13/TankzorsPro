package com.tanchiki.libgdx.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.input.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.utils.*;
import com.kotcrab.vis.ui.*;
import com.tanchiki.libgdx.stage.*;
import com.tanchiki.libgdx.util.*;

public class GameScreen implements ApplicationListener {
    GameStage GameStage;
    PanelStage PanelStage;
    MenuStage MenuStage;
    MiniMapStage MiniMapStage;
    StoreStage StoreStage;
    WeaponMenuStage WeaponMenuStage;
	LoadingStage LoadingStage;
	PauseStage PauseStage;
	
    public InputMultiplexer input;
    public boolean isLoading = true;

	@Override
	public void create() {
		ObjectVarable.init();
        FontLoader.init();
		TextureLoader.load();
		SoundLoader.load();
		ObjectClass.GameScreen = this;
		
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
		}
		
        Gdx.gl20.glClearColor(0, 0, 0, 0);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl20.glClear(GL20.GL_DEPTH_BUFFER_BIT);
		
		if (TextureLoader.manager.update() && SoundLoader.manager.update() && GameStage != null)
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
		if (GameStage == null) return;
		GameStage.drawStage(fps);

		if (!Settings.pause && Settings.start_game) {
			PanelStage.getRoot().setTouchable(Touchable.enabled);
			PanelStage.act();
			PanelStage.draw();
		} else {
			PanelStage.getRoot().setTouchable(Touchable.disabled);
		}

		if (Settings.show_map) {
			MiniMapStage.act();
			MiniMapStage.draw();
		}

		WeaponMenuStage.act();
		WeaponMenuStage.draw();

		if (Settings.show_main_menu) {
			MenuStage.getRoot().setTouchable(Touchable.enabled);
			MenuStage.act();
			MenuStage.draw();
		} else {
			MenuStage.getRoot().setTouchable(Touchable.disabled);
		}

		if (Settings.store_menu) {
			StoreStage.getRoot().setTouchable(Touchable.enabled);
			StoreStage.act();
			StoreStage.draw();
		} else {
			StoreStage.getRoot().setTouchable(Touchable.disabled);
		}
		if (Settings.pause && !Settings.pauseView) {
			AboutStage.getInstance().act();
			AboutStage.getInstance().draw();
		}
		if (Settings.pauseView) {
			PauseStage.getRoot().setTouchable(Touchable.enabled);
			PauseStage.act();
			PauseStage.draw();
		} else {
			PauseStage.getRoot().setTouchable(Touchable.disabled);
		}
	}

    private void init() {
		if (!VisUI.isLoaded()) VisUI.load(VisUI.SkinScale.X2);

        TextureLoader.init();
        ObjectClass.init();
        SkinLoader.init();

        initStage();
        initInput();
    }

    private void initStage() {
        GameStage = new GameStage();
		GameStage.gameStage = GameStage;
        PanelStage = new PanelStage();
        WeaponMenuStage = new WeaponMenuStage();
        MenuStage = new MenuStage();
        StoreStage = new StoreStage();
        MiniMapStage = new MiniMapStage();
		PauseStage = new PauseStage();
    }

    private void initInput() {
        input = new InputMultiplexer();

        input.addProcessor(PauseStage);
        input.addProcessor(MenuStage);
        input.addProcessor(WeaponMenuStage);
		input.addProcessor(AboutStage.getInstance());
        input.addProcessor(PanelStage);
        input.addProcessor(StoreStage);
        if (Settings.edit_map_mode) input.addProcessor(new GestureDetector(GameStage.listener));
        input.addProcessor(GameStage);
        input.addProcessor(MiniMapStage);		

        Gdx.input.setCatchBackKey(true);
        Gdx.input.setInputProcessor(input);
    }
}
