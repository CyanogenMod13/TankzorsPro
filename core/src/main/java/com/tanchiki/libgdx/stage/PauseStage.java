package com.tanchiki.libgdx.stage;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.tanchiki.libgdx.util.ObjectClass;
import com.tanchiki.libgdx.util.SavePreferences;
import com.tanchiki.libgdx.util.Settings;
import com.tanchiki.libgdx.util.TextureLoader;

public class PauseStage extends Stage {
	private Table viewRoot;
	
	public PauseStage() {
		viewRoot = new Table();
       	viewRoot.setSize(getWidth(), getHeight());
		viewRoot.setPosition(0, 0);

		addActor(viewRoot);
		
		Menu menu = new Menu(viewRoot);
		viewRoot.add(menu).center().row();
	}
	
	private class Menu extends MenuStage.ViewTable {
		private String[] names = {
			"Продолжить игру",
			"Начать заново",
			"Настройки",
			"Помощь",
			"Выйти в главное меню"
		};

        public Menu(Table root) {
			super(root);
			setBackground(new NinePatchDrawable(new NinePatch(TextureLoader.getInstance().getIcons()[0][14], 4, 4, 4, 4)));
			put(3, new MenuStage.HelpView(root, this));
			put(2, new MenuStage.SettingsView(root, this));
            init(names, new Listener() {
					@Override
					public void run(int idx) {
						switch (idx) {
							case 0:
								Settings.pause = false;
								Settings.pauseView = false;
								break;
							case 1:
								SavePreferences.getInstance().loadContinues();
								GameStage.getInstance().startLevel(GameStage.next_level + 1);
								Settings.pause = true;
								Settings.pauseView = false;
								AboutStage.getInstance().show();
								break;
							case 4:
								Settings.pauseView = false;
								Settings.pause = false;
								Settings.start_game = false;
								Settings.show_main_menu = true;
								ObjectClass.GameStage.createTerrain("map_background");
								break;
							default: 
								super.run(idx);
						}
					}
				});
        }
    }
}
