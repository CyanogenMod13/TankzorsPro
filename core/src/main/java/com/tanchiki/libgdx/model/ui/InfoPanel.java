package com.tanchiki.libgdx.model.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.tanchiki.libgdx.model.tanks.Tank;
import com.tanchiki.libgdx.model.terrains.MainTerrain;
import com.tanchiki.libgdx.stage.GameStage;
import com.tanchiki.libgdx.stage.PanelStage;
import com.tanchiki.libgdx.util.*;

public class InfoPanel extends Group {
    Sprite background;
    Sprite enemy, unity, star, coin;
    private float padding = 12;
    BitmapFont f = FontLoader.f20;
    GameStage GameStage;

    public InfoPanel() {
        GlyphLayout layout = new GlyphLayout();
        layout.setText(f, "A");

        GameStage = GameStage.getInstance();

        setSize(Gdx.graphics.getHeight() / 7.2f, Gdx.graphics.getHeight() / 4);
        setPosition(Gdx.graphics.getWidth() - getWidth(), Gdx.graphics.getHeight() - getHeight());

        background = new Sprite(TextureLoader.getInstance().getIcons()[0][14]);
        background.setSize(getWidth(), getHeight());
        background.setPosition(getX(), getY());

        enemy = new Sprite(new Texture("texture/ui/enemy_icon.png"));
        enemy.setSize(getHeight() / 4 - padding, getHeight() / 4 - padding);
        enemy.setPosition(getX() + getWidth() - enemy.getWidth() - padding, getY() + getHeight() - enemy.getHeight() - padding / 2);

        unity = new Sprite(new Texture("texture/ui/unity_icon.png"));
        unity.setSize(enemy.getWidth(), enemy.getHeight());
        unity.setPosition(enemy.getX(), enemy.getY() - enemy.getHeight() - padding);

        star = new Sprite(TextureLoader.getInstance().getBonus()[0][12]);
        star.setSize(enemy.getWidth(), enemy.getHeight());
        star.setPosition(enemy.getX(), enemy.getY() - enemy.getHeight() * 2 - padding * 2);

        coin = new Sprite(TextureLoader.getInstance().getBonus()[0][15]);
        coin.setSize(enemy.getWidth(), enemy.getHeight());
        coin.setPosition(enemy.getX(), enemy.getY() - enemy.getHeight() * 3 - padding * 3);

        PanelStage.getInstance().addActor(new PanelHealth());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        super.draw(batch, parentAlpha);
        background.draw(batch);
        enemy.draw(batch);
        unity.draw(batch);
        coin.draw(batch);
        star.draw(batch);

        String text = "" + ObjectVariables.all_size_enemies;
        float h = f.getData().getGlyph('A').height;
        f.draw(batch, text, getX() + padding, enemy.getY() + enemy.getHeight() / 2 + h / 2);

        text = "" + ObjectVariables.all_size_allies;
        //h = f.getBounds(text).height;
        f.draw(batch, text, getX() + padding, unity.getY() + unity.getHeight() / 2 + h / 2);

        text = "" + Settings.TankUserSettings.coin;
        //h = f.getBounds(text).height;
        f.draw(batch, text, getX() + padding, coin.getY() + coin.getHeight() / 2 + h / 2);

        text = "" + Settings.TankUserSettings.star;
        //h = f.getBounds(text).height;
        f.draw(batch, text, getX() + padding, star.getY() + star.getHeight() / 2 + h / 2);

    }

    public class PanelHealth extends Actor {
        Sprite block, slive, sfix, sspeed, stime, stimer;
        TextureRegion plus, minus, live, fix, speed, time, bminus, bplus, timer;
        float start_x, start_y;

        public PanelHealth() {
            minus = TextureLoader.getInstance().getPanelHealth()[0][1];
            plus = TextureLoader.getInstance().getPanelHealth()[0][0];

            bminus = TextureLoader.getInstance().getPanelHealth()[0][5];
            bplus = TextureLoader.getInstance().getPanelHealth()[0][4];


            fix = TextureLoader.getInstance().getIcons()[0][WeaponData.Type.fix];
            live = TextureLoader.getInstance().getBonus()[0][5];

            speed = TextureLoader.getInstance().getIcons()[0][WeaponData.Type.speed];
            time = TextureLoader.getInstance().getIcons()[0][WeaponData.Type.time];

            timer = TextureLoader.getInstance().getIcons()[0][42];

            slive = new Sprite(live);

            sfix = new Sprite(fix);

            block = new Sprite(plus);//(int)GameStage.TankUser.HPbackup];

            sspeed = new Sprite(speed);
            stime = new Sprite(time);

            stimer = new Sprite(timer);
        }

        @Override
        public void draw(Batch batch, float parentAlpha) {

            super.draw(batch, parentAlpha);
            try {
                float lastx = 0;
                for (int i = 0; i < Settings.TankUserSettings.HPbackup; i++) {
                    if ((Settings.TankUserSettings.HP - 1) >= i)
                        block.setRegion(plus);
                    else
                        block.setRegion(minus);
                    block.setSize((InfoPanel.this.getHeight() / 4 - padding) / 2, (InfoPanel.this.getHeight() / 4 - padding) / 2);
                    block.setPosition(InfoPanel.this.getX() - block.getWidth() - block.getWidth() * i, InfoPanel.this.getY() + InfoPanel.this.getHeight() - block.getHeight() - padding);
                    block.draw(batch);
                    lastx = InfoPanel.this.getX() - block.getWidth() - block.getWidth() * i;
                }

                for (int i = 0; i < Settings.TankUserSettings.HPShieldBackup; i++) {
                    if ((Settings.TankUserSettings.HPShield - 1) >= i)
                        block.setRegion(bplus);
                    else
                        block.setRegion(bminus);
                    block.setSize((InfoPanel.this.getHeight() / 4 - padding) / 2, (InfoPanel.this.getHeight() / 4 - padding) / 2);
                    block.setPosition(lastx - block.getWidth() - block.getWidth() * i, InfoPanel.this.getY() + InfoPanel.this.getHeight() - block.getHeight() - padding);
                    block.draw(batch);
                    //lastx = InfoPanel.this.getX()-block.getWidth()-block.getWidth()*i;
                }

                slive.setSize((InfoPanel.this.getHeight() / 4 - padding), (InfoPanel.this.getHeight() / 4 - padding));
                slive.setPosition(InfoPanel.this.getX() - slive.getWidth(), InfoPanel.this.getY() + InfoPanel.this.getHeight() - slive.getHeight() - padding - block.getHeight());
                slive.draw(batch);

                String s = " " + WeaponData.live;
                GlyphLayout layout = new GlyphLayout();
                layout.setText(f, s);
                //float h = f.getData().getGlyph('A').height;
                //float w = f.getData().getGlyph('A').width * s.length();
                f.draw(batch, s, slive.getX() - layout.width, slive.getY() + slive.getHeight() / 2 + layout.height / 2);

                sfix.setSize((InfoPanel.this.getHeight() / 4 - padding), (InfoPanel.this.getHeight() / 4 - padding));
                sfix.setPosition(slive.getX() - layout.width - padding / 2 - slive.getWidth(), InfoPanel.this.getY() + InfoPanel.this.getHeight() - slive.getHeight() - padding - block.getHeight());
                sfix.draw(batch);

                s = " " + WeaponData.fix;
                layout.setText(f, s);
                //h = f.getBounds(s).height;
                //w = f.getData().getGlyph('A').width * s.length();
                f.draw(batch, s, sfix.getX() - layout.width, sfix.getY() + sfix.getHeight() / 2 + layout.height / 2);

                float last_x = sfix.getX(), last_y = sfix.getY();

                if (Tank.stop_enemy) {
                    stime.setSize((InfoPanel.this.getHeight() / 4 - padding), (InfoPanel.this.getHeight() / 4 - padding));
                    stime.setPosition(last_x - layout.width - padding / 2 - stime.getWidth(), InfoPanel.this.getY() + InfoPanel.this.getHeight() - stime.getHeight() - padding - block.getHeight());
                    stime.draw(batch);

                    int t = Math.round(50 - GameStage.timer_enemy);
                    s = "0:" + (t < 10 ? "0" : "") + t;
                    //h = f.getBounds(s).height;
                    //w = f.getData().getGlyph('A').width * s.length();
                    layout.setText(f, s);
                    f.draw(batch, s, stime.getX() - layout.width, stime.getY() + stime.getHeight() / 2 + layout.height / 2);


                    last_x = stime.getX();
                }

                if (GameStage.tankUser.speedHack) {
                    sspeed.setSize((InfoPanel.this.getHeight() / 4 - padding), (InfoPanel.this.getHeight() / 4 - padding));
                    sspeed.setPosition(last_x - layout.width - padding / 2 - sspeed.getWidth(), InfoPanel.this.getY() + InfoPanel.this.getHeight() - sspeed.getHeight() - padding - block.getHeight());
                    sspeed.draw(batch);

                    int t = Math.round(30 - GameStage.tankUser.speedTime);
                    s = "0:" + (t < 10 ? "0" : "") + t;
                    layout.setText(f, s);
                    //h = f.getBounds(s).height;
                    //w = f.getData().getGlyph('A').width * s.length();
                    f.draw(batch, s, sspeed.getX() - layout.width, sspeed.getY() + sspeed.getHeight() / 2 + layout.height / 2);

                    last_x = sspeed.getX();
                }

                MainTerrain.Timer mainTimer = MainTerrain.getCurrentTerrain().getTimer();

                if (mainTimer != null) {
                    stimer.setSize((InfoPanel.this.getHeight() / 4 - padding), (InfoPanel.this.getHeight() / 4 - padding));
                    stimer.setPosition(last_x - layout.width - padding / 2 - stimer.getWidth(), InfoPanel.this.getY() + InfoPanel.this.getHeight() - stimer.getHeight() - padding - block.getHeight());
                    stimer.draw(batch);

                    int t = Math.round(mainTimer.time - mainTimer.delta);
                    int min = t / 60;
                    int sec = t - min * 60;
                    s = min + ":" + (sec < 10 ? "0" : "") + sec;
                    layout.setText(f, s);
                    //h = f.getBounds(s).height;
                    //w = f.getData().getGlyph('A').width * s.length();
                    f.draw(batch, s, stimer.getX() - layout.width, stimer.getY() + stimer.getHeight() / 2 + layout.height / 2);

                    last_x = stimer.getX();
                }
            } catch (Exception e) {
            }
        }
    }
}
