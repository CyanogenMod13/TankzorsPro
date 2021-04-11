package com.tanchiki.libgdx.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.kotcrab.vis.ui.widget.ScrollableTextArea;
import com.kotcrab.vis.ui.widget.VisSlider;
import com.kotcrab.vis.ui.widget.VisTable;
import com.tanchiki.libgdx.server.GameServer;
import com.tanchiki.libgdx.util.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MenuStage extends Stage {
	private static MenuStage menuStage = null;
	public static MenuStage getInstance() {
		if (menuStage == null) menuStage = new MenuStage();
		return menuStage;
	}

    private Texture intro = new Texture("texture/ui/intro_logo.png");

	private Table viewRoot;
	
    private MenuStage() {
		MenuStage.menuStage = this;
		
		viewRoot = new Table();
       	viewRoot.setSize(getWidth(), getHeight());
		viewRoot.setPosition(0, 0);
		
		addActor(viewRoot);
		
		Pixmap pix = new Pixmap(100, 100, Pixmap.Format.RGBA8888);
		pix.setColor(Color.BLACK);
		pix.fill();
		Image background = new Image(new Texture(pix));
		background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		background.setPosition(0, 0);
		addActor(background);
		background.addAction(Actions.color(new Color(0, 0, 0, 0), 1f, Interpolation.fade));
		background.setTouchable(Touchable.disabled);
		
		Image imgIntro = new Image(intro);
		imgIntro.setSize(getWidth() * 0.25f, (getWidth() * 0.25f * imgIntro.getHeight() / imgIntro.getWidth()));
		imgIntro.setPosition(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f, Align.center);
		imgIntro.addAction(Actions.moveTo(imgIntro.getX(), getHeight() - imgIntro.getHeight(), 1f, Interpolation.fade));
		addActor(imgIntro);
		
		MenuButtons menuButtons = new MenuButtons(viewRoot);
		menuButtons.show();    
	}
	
	private static class Background extends Table {
		public Background() {
			setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			setPosition(0, 0);
			setBackground(new TextureRegionDrawable(new Texture("texture/ui/index.jpg")));
		}
	}

    private static class MenuButtons extends ViewTable {
		private String[] names = {
			"Начать игру",
			"Настройки",
			"Об игре",
			"Помощь",
			"Выход"
		};
		
        public MenuButtons(Table root) {
			super(root);
			setBackground(new NinePatchDrawable(new NinePatch(TextureLoader.getInstance().getIcons()[0][14], 4, 4, 4, 4)));
			put(0, new LevelsMenu(root, this));
			put(3, new HelpView(root, this));
			put(2, new AboutView(root, this));
			put(1, new SettingsView(root, this));
            init(names, new ViewTable.Listener() {
				@Override
				public void run(int idx) {
					switch (idx) {
						case 4:
							System.exit(0); break;
						default: 
							super.run(idx);
					}
				}
			});
        }
    }
	
	public static class SettingsView extends ViewTable {
		private String[] names = {
			"Назад"
		};
		
		public SettingsView(Table root, ViewTable parent) {
			super(root, parent);
			put(0, parent);
			setBackground(new NinePatchDrawable(new NinePatch(TextureLoader.getInstance().getIcons()[0][14], 4, 4, 4, 4)));
			label("Музыка");
			final VisSlider slider = new VisSlider(0, 1, 0.01f, false);
			slider.setValue(Settings.volumeMusic);
			slider.addListener(new ClickListener() {
					@Override
					public void clicked(InputEvent e, float x, float y) {
						Settings.volumeMusic = slider.getValue();
						MusicLoader.getInstance().setVolume(Settings.volumeMusic);
					}
					
					public void touchDragged(InputEvent event, float x, float y, int pointer) {
						Settings.volumeMusic = slider.getValue();
						MusicLoader.getInstance().setVolume(Settings.volumeMusic);
					}
					
					@Override
					public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
						super.touchUp(event, x, y, pointer, button);
						SavePreferences.getInstance().saveSettings();
					}
			});
			add(slider).pad(2).center().width(Gdx.graphics.getWidth() / 3f).row();
			
			label("Эффекты");
			final VisSlider slider1 = new VisSlider(0, 1, 0.01f, false);
			slider1.setValue(Settings.volumeEffect);
			slider1.addListener(new ClickListener() {
					private void run() {
						Settings.volumeEffect = slider1.getValue();
					}
					
					@Override
					public void clicked(InputEvent e, float x, float y) {
						super.clicked(e, x, y);
						run();
					}
					
					@Override
					public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
						super.touchUp(event, x, y, pointer, button);
						SavePreferences.getInstance().saveSettings();
					}
					
					@Override
					public void touchDragged(InputEvent event, float x, float y, int pointer) {
						run();
					}
				});
			add(slider1).pad(2).center().width(Gdx.graphics.getWidth() / 3f).row();
			
			label("Масштаб");
			final VisSlider slider2 = new VisSlider(1, 4, 0.1f, false);
			slider2.setValue(Settings.zoom);
			slider2.addListener(new ClickListener() {
					private void run() {
						Settings.zoom = slider2.getValue();
						GameStage.getInstance().cam.zoom = 1f / Settings.zoom;
						GameStage.getInstance().cam.update();
					}
					
					@Override
					public void clicked(InputEvent e, float x, float y) {
						super.clicked(e, x, y);
						run();
					}
					
					@Override
					public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
						super.touchUp(event, x, y, pointer, button);
						SavePreferences.getInstance().saveSettings();
					}
					
					@Override
					public void touchDragged(InputEvent e, float x, float y, int pointer) {
						run();
					}
				});
			add(slider2).pad(2).center().width(Gdx.graphics.getWidth() / 3f).row();
			addSeparator();
			init(names);
		}
	}
	
	public static class AboutView extends ViewTable {
		private String[] names = {
			"Назад"
		};
		
		public AboutView(Table root, ViewTable parent) {
			super(root, parent);
			setBackground(new NinePatchDrawable(new NinePatch(TextureLoader.getInstance().getIcons()[0][14], 4, 4, 4, 4)));
			put(0, parent);
			label("Tankzors Pro (c) 2020");
			label("Version 1.4 Alpha");
			label("");
			label("programmed by: Shovkan Bogdan");
			label("");
			label("My Telegram: @CM_13");
			addSeparator();
			init(names);
		}
	}

    private static class LevelsMenu extends ViewTable {
		private String[] names = {
			"Продолжить",
			"Новая игра",
			"Загрузить игру",
			"Сетевой режим",
			"Назад"
		};
			
        public LevelsMenu(Table root, ViewTable parent) {
            super(root, parent);
			setBackground(new NinePatchDrawable(new NinePatch(TextureLoader.getInstance().getIcons()[0][14], 4, 4, 4, 4)));
			put(2, new LoadMenu(root, this));
			put(3, new NetworkMenu(root, this));
			put(names.length - 1, parent);
			init(names, new ViewTable.Listener() {
					@Override
					public void run(int idx) {
						switch (idx) {
							case 0:
								if (GameStage.next_level > MapsDatabase.getInstance().getSize() - 2) {
									AboutStage.getInstance().showEndGame();
								} else {
									SavePreferences.getInstance().loadContinues();
									GameStage.getInstance().startLevel(GameStage.next_level + 1);
									GameServer server = GameServer.getInstance();
									server.sendData("Hello".getBytes());
									Settings.pause = true;
									AboutStage.getInstance().show();
								}
								break;
							case 1:
								SavePreferences.getInstance().reset();
								GameStage.getInstance().startLevel(GameStage.next_level + 1);
								Settings.pause = true;
								AboutStage.getInstance().show();
								break;
							default: 
								super.run(idx);
						}
					}
				} );
        }
    }

	public static class HelpView extends ViewTable {
		private String[] names = {
			"Назад"
		};
		
		public HelpView(Table root, ViewTable parent) {
			super(root, parent);
			setBackground(new NinePatchDrawable(new NinePatch(TextureLoader.getInstance().getIcons()[0][14], 4, 4, 4, 4)));
			put(0, parent);
			String s = "#f    Игра. ~Тут все просто: нужно ездить на танке и выполнять задания, которые даются перед миссиями. Уничтожая вражеские танчики и пушки вы зарабатываете «монеты», которые можно потратить в мастерской (чем мощнее вражеская техника, тем больше монет вы за ее уничтожение получаете, вот). И еще: в конце каждой миссии вы получаете дополнительный процент к заработанной сумме. Этот процент можно увеличить в мастерской в разделе исследований. ~#f    Мастерская. ~Здесь на заработанные монеты можно модернизировать танк, покупая новое оружие. Но для покупки новых типов оружия надо сначала провести исследования, используя «звёзды», собранные во время игры. ~Учтите, чем больший остаток монет у вас будет после модернизации танка, тем больший процент вы получите в конце следующей миссии. Старайтесь экономить в начале игры, тогда в последующих уровнях вы будете гораздо богаче. ~~В мастерскую можно попасть во время игры вернувшись на базу (желтый квадрат). ~~#f    Сохранение игры. ~После каждого пройденного уровня игра автоматически сохраняется. Игру можно загрузить, выбрав пункт 'Продолжить' или 'Загрузить игру'.  ~~~И, наконец, самое главное: старайтесь играть с интересом, сосредоточившись на игре, чтобы вас при этом ничего не отвлекало. Крайне желательно перед началом игры быть в умиротворенном расположении духа. Или наоборот. Впрочем, это все не обязательно - противник постарается вас развлечь в любом случае!";
			ViewTable table = new ViewTable(null, null);
			table.area(FontLoader.format(s));
			ScrollPane scroll = new ScrollPane(table);
			scroll.setScrollingDisabled(true, false);
			add(scroll).pad(2).center().size(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f).row();
			addSeparator();
			init(names);
		}
	}
	
	private static class LoadMenu extends ViewTable {
		private String[] names = {
			"Назад"
		};
		private ViewTable table;
		
		public LoadMenu(Table root, ViewTable parent) {
			super(root, parent);
			setBackground(new NinePatchDrawable(new NinePatch(TextureLoader.getInstance().getIcons()[0][14], 4, 4, 4, 4)));
			put(0, parent);
			table = new ViewTable(null, null);
			
			ScrollPane scroll = new ScrollPane(table);
			scroll.setScrollingDisabled(true, false);
			add(scroll).pad(2).center().width(Gdx.graphics.getWidth() / 3f).maxHeight(Gdx.graphics.getHeight() / 2f).row();
			addSeparator();
			init(names);
		}
		
		private void createList() {
			table.clear();
			table.button(new Date().toLocaleString(), new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						SavePreferences.getInstance().load(0);
						GameStage.getInstance().startLevel(GameStage.next_level + 1);
						Settings.pause = true;
						AboutStage.getInstance().show();
					}
				});
		}

		@Override
		public void show() {
			super.show();
			createList();
		}
		
	}

	private static class NetworkMenu extends ViewTable {
		private String[] names = {
				"Создать сервер",
				"Подключится к серверу",
				"Назад"
		};

    	public NetworkMenu(Table root, ViewTable parent) {
    		super(root, parent);
			setBackground(new NinePatchDrawable(new NinePatch(TextureLoader.getInstance().getIcons()[0][14], 4, 4, 4, 4)));
			put(1, new ConnectMenu(root, this));
			put(0, new CreateServerMenu(root, this));
			put(names.length - 1, parent);
			init(names);
		}

		private class ConnectMenu extends ViewTable {
			private final String[] names = {
					"Назад"
			};
    		ConnectMenu(Table root, ViewTable parent) {
    			super(root, parent);
				setBackground(new NinePatchDrawable(new NinePatch(TextureLoader.getInstance().getIcons()[0][14], 4, 4, 4, 4)));
				Label.LabelStyle labelStyle = new Label.LabelStyle(FontLoader.f24, Color.WHITE);
				TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle(FontLoader.f24, Color.WHITE, null, null, new NinePatchDrawable(new NinePatch(TextureLoader.getInstance().getIcons()[0][14], 4, 4, 4, 4)));
				add(new Label("IP", labelStyle));
				add(new TextField("127.0.0.1", textFieldStyle)).fill().row();
				add(new Label("Port", labelStyle));
				add(new TextField("5123", textFieldStyle)).fill().row();
				put(names.length - 1, parent);
				button("Connect", new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						
					}
				});
    			addSeparator().fill();
				init(names);
			}
		}

		private class CreateServerMenu extends ViewTable {
    		CreateServerMenu(Table root, ViewTable parent) {
    			super(root, parent);

			}
		}
	}

	public static class ViewTable extends VisTable {
		public static final String SEPARATOR = "SEP";
		public ViewTable parent = null;
		public Table viewRoot = null;
		public Map<Integer, ViewTable> mapping = new HashMap<>();
		public ViewTable lastView = this;
		public TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle(
			null,
			new TextureRegionDrawable(new Texture("texture/ui/billet.png")),
			null,
			FontLoader.f30);
		public Label.LabelStyle labelStyle = new Label.LabelStyle(FontLoader.f30, Color.WHITE);
		public TextArea.TextFieldStyle areaStyle = new TextArea.TextFieldStyle(FontLoader.f30, Color.WHITE, null, null, null);
		
		public ViewTable(Table viewRoot) {
			this(viewRoot, null);
		}
			
		public ViewTable(Table viewRoot, ViewTable parent) {
			this.viewRoot = viewRoot;
			this.parent = parent;
		}
		
		public void init(final String[] names) {
			init(names, new Listener());
		}
		
		public void init(final String[] names, final Listener click) {
			for (int i = 0; i < names.length; i++) {
				final int idx = i;
				String name = names[i];
				switch (name) {
					case SEPARATOR:
						addSeparator();
						break;
					default:
						button(name, new ClickListener() {
								@Override
								public void clicked(InputEvent e, float x, float y) {
									click.run(idx);
								}
							});
				}
			}
		}
		
		public void button(String name, ClickListener l) {
			TextButton button = new TextButton(name, buttonStyle);
			button.addListener(l);
			add(button).pad(2).center().fillX().row();
		}
		
		public void area(String name) {
			ScrollableTextArea label = new ScrollableTextArea(name, new ScrollableTextArea.VisTextFieldStyle(FontLoader.f30, Color.WHITE, null, null, null));
			float areaWidth = Gdx.graphics.getWidth() / 2f;
			GlyphLayout layout = new GlyphLayout(label.getStyle().font, name, Color.WHITE, areaWidth, 5, true);
		
			label.setTouchable(Touchable.disabled);
			add(label).center().size(layout.width, layout.height + label.getStyle().font.getCapHeight()).row();
		}
		
		public void label(String name) {
			Label label = new Label(name, labelStyle);
			add(label).pad(2).center().row();
		}
		
		public void show() {
			lastView = this;
			viewRoot.center().add(this).row();
			
			viewRoot.setX((viewRoot.getWidth() - getWidth()) / -2f - getWidth());
			viewRoot.setY(0);
			viewRoot.addAction(Actions.moveTo(0, 0, 0.3f, Interpolation.fade));
		}
		
		public void hide() {
			remove();
		}
		
		public void put(int idx, ViewTable view) {
			mapping.put(idx, view);
		}
		
		public ViewTable get(int idx) {
			return mapping.get(idx);
		}
		
		public class Listener {
			public void run(int idx) {
				ViewTable view = mapping.get(idx);
				
				if (view != null) {
					lastView.hide();
					view.show();
				}
			}
		}
	}
}
