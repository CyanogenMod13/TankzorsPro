package com.tanchiki.libgdx.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Align;
import com.tanchiki.libgdx.model.terrains.MainTerrain;
import com.tanchiki.libgdx.util.*;

public class AboutStage extends Stage {
    private static AboutStage aboutStage = null;

    public static AboutStage getInstance() {
        if (aboutStage == null) aboutStage = new AboutStage();
        return aboutStage;
    }

    Table table = new Table();
    Texture line = new Texture("texture/ui/horiz_line.png");

    public AboutStage() {
        table.setSize(getWidth(), getHeight());
        table.setPosition(0, 0);
        addActor(table);
    }

    public void show() {
        table.clear();
        table.center().add(new AboutView()).row();
        move();
    }

    public void showEnd() {
        Settings.pause = true;
        table.clear();
        table.center().add(new EndView()).row();
        move();
    }

    public void showFailed() {
        Settings.pause = true;
        table.clear();
        table.center().add(new FailView()).size(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()).row();
    }

    public void showEndGame() {
        Settings.pause = true;
        Settings.start_game = false;
        Settings.show_main_menu = false;
        GameStage.getInstance().createTerrain("map_background");
        table.clear();
        table.center().add(new EndGameView()).row();
    }

    private void move() {
        table.setX((table.getWidth()) / -2f - getWidth());
        table.setY(0);
        table.addAction(Actions.moveTo(0, 0, 0.3f, Interpolation.fade));
    }

    private class EndGameView extends MenuStage.ViewTable {
        String s = "Последний уровень в игре пройден! Вы победили! ~~Но... Честно говоря, мы и сами не ожидали, что вы дойдёте сюда, поэтому финальный текст мы даже не подготовили - так что его, к сожалению, просто не существует. ~~Да и в том, что вы прошли все эти сложные (а иногда и очень сложные) уровни, на самом деле, нет ничего хорошего: оглянитесь вокруг, после вас остались горы поломанных и испорченных танчиков, пушечек, уничтоженных уровней. А представляете, сколько погибло маленьких танкистиков, и осталось одиноких семей и осиротевших голодных детишек... Вам жаль их, правда? Вы сожалеете о хаосе и разрушениях, привнесенных в наш мир? Но с другой стороны, ведь все они были ненастоящие, и этот мир вымышленный? ~~Так или иначе - это горечь победы! ~~И мы все-таки обрадуем вас, заодно немного скрасив вашу победу - в игре есть множество онлайн-уровней, в которых будет разворачиваться еще более увлекательная и интересная борьба между танчиками. Тут мы можем вам пожелать вырваться вперед среди многих игроков и занять первые позиции рейтингов. ~~Удачи в новых боях и во всех ваших играх, какими бы они не были, друзья! ";

        public EndGameView() {
            super(null, null);
            setBackground(new NinePatchDrawable(new NinePatch(TextureLoader.getInstance().getIcons()[0][14], 4, 4, 4, 4)));
            put(0, parent);
            MenuStage.ViewTable table = new MenuStage.ViewTable(null, null);
            table.area(FontLoader.format(s));
            ScrollPane scroll = new ScrollPane(table);
            scroll.setScrollingDisabled(true, false);
            add(scroll).pad(2).center().size(Gdx.graphics.getWidth() / 3f, Gdx.graphics.getHeight() / 2f).row();
            addSeparator();
            button("Выйти в главное меню", new ClickListener() {
                @Override
                public void clicked(InputEvent e, float x, float y) {
                    super.clicked(e, x, y);
                    Settings.show_main_menu = true;
                    remove();
                }
            });
        }
    }

    private class AboutView extends MenuStage.ViewTable {
        public AboutView() {
            super(null, null);
            setSize(Gdx.graphics.getWidth() / 3f, Gdx.graphics.getHeight() / 3f);
            setPosition(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f, Align.center);
            setBackground(new NinePatchDrawable(new NinePatch(TextureLoader.getInstance().getIcons()[0][14], 4, 4, 4, 4)));
            int level = GameStage.next_level;
            MapBinReader map = MapsDatabase.getInstance().getMap("map" + level);
            label(map.getName());
            addSeparator();
            area(map.getBriefing()[0]);
            addSeparator();
            button("Поехали", new ClickListener() {
                @Override
                public void clicked(InputEvent e, float x, float y) {
                    super.clicked(e, x, y);
                    Settings.pause = false;
                    AboutView.this.remove();
                }
            });
        }
    }

    private class EndView extends MenuStage.ViewTable {
        public EndView() {
            super(null, null);
            setSize(Gdx.graphics.getWidth() / 3f, Gdx.graphics.getHeight() / 3f);
            setPosition(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f, Align.center);
            setBackground(new NinePatchDrawable(new NinePatch(TextureLoader.getInstance().getIcons()[0][14], 4, 4, 4, 4)));
            int level = GameStage.next_level;
            MapBinReader map = MapsDatabase.getInstance().getMap("map" + level);

            MainTerrain.getCurrentTerrain().coin += Math.max(ObjectVariables.level_difficulty, 1) * 40;
            MainTerrain.getCurrentTerrain().star += Math.max(ObjectVariables.level_difficulty, 1) + 4;
            Settings.TankUserSettings.coin += Math.max(ObjectVariables.level_difficulty, 1) * 40;
            Settings.TankUserSettings.star += Math.max(ObjectVariables.level_difficulty, 1) + 4;
            int bonus = Math.round(MainTerrain.getCurrentTerrain().coin * 0.05f * WeaponData.Upgrade.adding_percent);
            Settings.TankUserSettings.coin += bonus;
            int s = (int) MainTerrain.getCurrentTerrain().missionTime;
            int m = s / 60;
            int h = m / 60;
            int sm = m % 60;
            int ss = s % 60;

            String text = map.getBriefing()[1] + "\n\n";
            MenuStage.ViewTable context = new MenuStage.ViewTable(null, null);
            context.area(text);
            context.add(new Image(line)).center().fillX().row();
            context.area((char) 512 + "   Занимательная статистика:" + '\n' +
                    "Всего монет: " + MainTerrain.getCurrentTerrain().coin + '\n' +
                    "Бонусный процент: " + bonus + '\n' +
                    "Всего звезд: " + MainTerrain.getCurrentTerrain().star + '\n' +
                    "Время миссии: " + String.format("%02d:%02d:%02d", h, sm, ss) + "\n" +
                    "Выстрелов: " + MainTerrain.getCurrentTerrain().countFire + '\n' +
                    "Урон танчику: " + MainTerrain.getCurrentTerrain().damageUser + " HP" + '\n' +
                    "Пробег танчика: " + MainTerrain.getCurrentTerrain().distance + " m");
            ScrollPane scroll = new ScrollPane(context);
            scroll.setScrollingDisabled(true, false);
            add(scroll).row();//.size(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f).row();
            addSeparator();
            button("Продолжить", new ClickListener() {
                @Override
                public void clicked(InputEvent e, float x, float y) {
                    super.clicked(e, x, y);
                    if (GameStage.next_level > MapsDatabase.getInstance().getSize() - 2) {
                        AboutStage.getInstance().showEndGame();
                    } else {
                        GameStage.getInstance().startLevel(GameStage.next_level + 1);
                        Settings.pause = true;
                        AboutStage.getInstance().show();
                    }
                }
            });
			/*button("Сохранить", new ClickListener() {
					@Override
					public void clicked(InputEvent e, float x, float y) {
						super.clicked(e, x, y);
						Settings.pause = false;
					}
				});*/
            button("Главное меню", new ClickListener() {
                @Override
                public void clicked(InputEvent e, float x, float y) {
                    super.clicked(e, x, y);
                    Settings.pause = false;
                    Settings.start_game = false;
                    Settings.show_main_menu = true;
                    GameStage.getInstance().createTerrain("map_background");
                }
            });
        }
    }

    private class FailView extends MenuStage.ViewTable {
        public FailView() {
            super(null, null);
            setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            setPosition(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f, Align.center);
            setBackground(new NinePatchDrawable(new NinePatch(TextureLoader.getInstance().getIcons()[0][14], 4, 4, 4, 4)));
            label((char) 515 + "   Миссия провалена!");

            addSeparator();
            button("Переиграть", new ClickListener() {
                @Override
                public void clicked(InputEvent e, float x, float y) {
                    super.clicked(e, x, y);
                    SavePreferences.getInstance().loadContinues();
                    GameStage.getInstance().startLevel(GameStage.next_level + 1);
                    Settings.pause = true;
                    AboutStage.getInstance().show();
                }
            });
            button("Главное меню", new ClickListener() {
                @Override
                public void clicked(InputEvent e, float x, float y) {
                    super.clicked(e, x, y);
                    Settings.pause = false;
                    Settings.start_game = false;
                    Settings.show_main_menu = true;
                    GameStage.getInstance().createTerrain("map_background");
                }
            });
        }
    }
}
