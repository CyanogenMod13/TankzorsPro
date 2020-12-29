package com.tanchiki.libgdx.model.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.tanchiki.libgdx.model.tanks.TankUser;
import com.tanchiki.libgdx.stage.GameStage;
import com.tanchiki.libgdx.stage.MiniMapStage;
import com.tanchiki.libgdx.util.FontLoader;
import com.tanchiki.libgdx.util.ObjectClass;
import com.tanchiki.libgdx.util.Settings;
import com.tanchiki.libgdx.util.WeaponData;
import com.tanchiki.libgdx.util.*;

public class TSButton extends Table {
    TextureRegion reg[];
    TextButton angar, weapon, fix, fire, mines, air, radar;
    float size = Gdx.graphics.getHeight() / 7.2f;
    SpriteDrawable t, t1;
    GameStage GameStage;

    TextButtonStyle style;

    public TSButton() {
        GameStage = ObjectClass.GameStage;

        Texture texture = new Texture("texture/ui/knob.png");
        Sprite sp1 = new Sprite(texture);
        Sprite sp2 = new Sprite(texture);
        sp2.setAlpha(0.5f);
        //this.center();
        t = new SpriteDrawable(sp1);
        t1 = new SpriteDrawable(sp2);

        style = new TextButtonStyle(t, t1, t, FontLoader.f16);

        Texture t = new Texture("texture/ui/icons_ts.png");
        reg = TextureRegion.split(t, t.getWidth() / 12, t.getHeight())[0];
        fix = new TextButton("", new TextButtonStyle(new TextureRegionDrawable(reg[9]), new TextureRegionDrawable(reg[10]), null, FontLoader.f16));
        fix.setSize(size, size);
        fix.setPosition(Gdx.graphics.getWidth() - fix.getWidth() - 5, 5);
        addActor(fix);
        fix.addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                super.clicked(e, x, y);
                if (GameStage.TankUser != null) {
                    if (GameStage.TankUser.HP != GameStage.TankUser.HPbackup)
                        if (WeaponData.fix > 0) {
                            GameStage.TankUser.HP = GameStage.TankUser.HPbackup;
                            WeaponData.fix -= 1;
							SoundLoader.getInstance().getRepairPickup().play(Settings.volumeEffect);
                        } else {
                            ObjectClass.PanelStage.addToast("Рем. комплект закончился");
                        }
                    else
                        ObjectClass.PanelStage.addToast("Танк не нуждаеться в ремонте");
                }
            }
        });
        angar = new TextButton("", new TextButtonStyle(new TextureRegionDrawable(reg[6]), new TextureRegionDrawable(reg[7]), null, FontLoader.f16));
        angar.setSize(size, size);
        angar.setPosition(Gdx.graphics.getWidth() - angar.getWidth() - 5, fix.getHeight() + 5);
        addActor(angar);
        angar.addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                super.clicked(e, x, y);
                TankUser t = GameStage.TankUser;
                if (GameStage.world_buildes[(int) t.getCenterX()][(int) t.getCenterY()] != null) {
                    ObjectClass.StoreStage.show();
                } else {
                    ObjectClass.PanelStage.addToast("Вернитесь на базу!");
                }
            }
        });
        weapon = new TextButton("", new TextButtonStyle(new TextureRegionDrawable(reg[0]), new TextureRegionDrawable(reg[1]), null, FontLoader.f16));
        weapon.setSize(size, size);
        weapon.setPosition(Gdx.graphics.getWidth() - weapon.getWidth() - 5, angar.getHeight() * 2 + 5);
        addActor(weapon);
        weapon.addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                super.clicked(e, x, y);
                //Settings.pause = true;
                ObjectClass.WeaponMenuStage.showMenu();
            }
        });

        fire = new FireButton();
        addActor(fire);

        mines = new MinesButton();
        addActor(mines);

        air = new AirButton();
        addActor(air);
		
		/*radar = new RadarButton();
		addActor(radar);*/
    }

    private class RadarButton extends TextButton {
        TextureRegion[] t;
        Sprite s;

        public RadarButton() {
            super("", style);
            setSize(size, size);
            setPosition(weapon.getX(), weapon.getY() + getHeight());
            addListener(new ClickListener() {
                public void clicked(InputEvent e, float x, float y) {
                    super.clicked(e, x, y);
                    MiniMapStage.show();
                    //if(GameStage.TankUser != null)
                    //GameStage.TankUser.AI.AIR();
                }

            });
            t = GameStage.TextureLoader.getIcons()[0];
            s = new Sprite(t[0]);
        }

        @Override
        public void draw(Batch batch, float parentAlpha) {
            // TODO: Implement this method
            super.draw(batch, parentAlpha);
            s.setSize(size / 2.5f, size / 2.5f);
            s.setPosition(this.getX(), this.getY());
            s.setRegion(t[WeaponData.Type.radar]);
            s.draw(batch);
            //FontLoader.f16.draw(batch,""+count,s.getX(),s.getY());
        }

    }

    private class AirButton extends TextButton {
        TextureRegion[] t;
        Sprite s;

        public AirButton() {
            super("", style);
            setSize(size, size);
            setPosition(fix.getX() - getWidth(), fix.getY());
            addListener(new ClickListener() {
                public void clicked(InputEvent e, float x, float y) {
                    super.clicked(e, x, y);
                    //if(GameStage.TankUser != null)
                    GameStage.TankUser.AI.AIR();
                }

            });
            t = GameStage.TextureLoader.getIcons()[0];
            s = new Sprite(t[0]);
        }

        @Override
        public void draw(Batch batch, float parentAlpha) {
            // TODO: Implement this method
            super.draw(batch, parentAlpha);
            s.setSize(size / 2.5f, size / 2.5f);
            s.setCenter(this.getX(Align.center), this.getY(Align.center));
            int count = 0;
            switch (Settings.TankUserSettings.plus_bullet_type2) {
                case 1: {
                    count = WeaponData.art;
                    s.setRegion(t[WeaponData.Type.art]);
                    break;
                }
                case 2: {
                    count = WeaponData.air;
                    s.setRegion(t[WeaponData.Type.air]);
                    break;
                }
            }
            s.draw(batch);
            FontLoader.f16.draw(batch, "" + count, s.getX(), s.getY());
        }

    }

    private class MinesButton extends TextButton {
        TextureRegion[] t;
        Sprite s;

        public MinesButton() {
            super("", style);
            setSize(size, size);
            setPosition(angar.getX() - getWidth(), angar.getY());
            addListener(new ClickListener() {
                public void clicked(InputEvent e, float x, float y) {
                    super.clicked(e, x, y);
                    //if(GameStage.TankUser != null)
                    GameStage.TankUser.AI.MINES();
                }

            });
            t = GameStage.TextureLoader.getIcons()[0];
            s = new Sprite(t[0]);
        }

        @Override
        public void draw(Batch batch, float parentAlpha) {
            // TODO: Implement this method
            super.draw(batch, parentAlpha);
            s.setSize(size / 2.5f, size / 2.5f);
            s.setCenter(this.getX(Align.center), this.getY(Align.center));
            int count = 0;
            switch (Settings.TankUserSettings.plus_bullet_type1) {
                case 1: {
                    count = WeaponData.mine1;
                    s.setRegion(t[WeaponData.Type.mine1]);
                    break;
                }
                case 2: {
                    count = WeaponData.mine2;
                    s.setRegion(t[WeaponData.Type.mine2]);
                    break;
                }
                case 3: {
                    count = WeaponData.tnt1;
                    s.setRegion(t[WeaponData.Type.tnt1]);
                    break;
                }
                case 4: {
                    count = WeaponData.tnt2;
                    s.setRegion(t[WeaponData.Type.tnt2]);
                    break;
                }
                case 5: {
                    count = WeaponData.tnt3;
                    s.setRegion(t[WeaponData.Type.tnt3]);
                    break;
                }
            }
            s.draw(batch);
            FontLoader.f16.draw(batch, "" + count, s.getX(), s.getY());
        }

    }

    private class FireButton extends TextButton {
        TextureRegion[] t;
        Sprite s;
        boolean touch = false;

        public FireButton() {
            super("", style);
            setSize(size, size);
            setPosition(weapon.getX() - getWidth(), weapon.getY());
            addListener(new ClickListener() {

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    touch = true;
                    return super.touchDown(event, x, y, pointer, button);
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    super.touchUp(event, x, y, pointer, button);
                    touch = false;
                }
            });
            t = GameStage.TextureLoader.getIcons()[0];
            s = new Sprite(t[0]);
        }

        @Override
        public void draw(Batch batch, float parentAlpha) {
            // TODO: Implement this method
            super.draw(batch, parentAlpha);
            s.setSize(size / 2.5f, size / 2.5f);
            s.setCenter(this.getX(Align.center), this.getY(Align.center));
            int count = 0;
            switch (Settings.TankUserSettings.bullet_type) {
                case 1: {
                    count = WeaponData.light_bullet;
                    s.setRegion(t[WeaponData.Type.light_bullet]);
                    break;
                }
				case 2: {
						count = WeaponData.plazma;
						s.setRegion(t[WeaponData.Type.plazma]);
						break;
					}
				case 3: {
						count = WeaponData.double_light_bullet;
						s.setRegion(t[WeaponData.Type.double_light_bullet]);
						break;
					}	
                case 4: {
                    count = WeaponData.double_palzma;
                    s.setRegion(t[WeaponData.Type.double_plazma_bullet]);
                    break;
                }
                case 5: {
                    count = WeaponData.bronet_bullet;
                    s.setRegion(t[WeaponData.Type.bronet_bullet]);
                    break;
                }
                case 6: {
                    count = WeaponData.bronet_bullet2;
                    s.setRegion(t[WeaponData.Type.bronet_bullet2]);
                    break;
                }
                case 7: {
                    count = WeaponData.rocket;
                    s.setRegion(t[WeaponData.Type.rocket]);
                    break;
                }
            }
            s.draw(batch);
            FontLoader.f16.draw(batch, "" + count, s.getX(), s.getY());
        }

        @Override
        public void act(float delta) {
            if (touch) GameStage.TankUser.AI.BULLET();
            super.act(delta);
        }
    }
}
