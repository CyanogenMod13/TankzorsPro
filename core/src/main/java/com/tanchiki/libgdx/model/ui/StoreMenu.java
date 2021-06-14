package com.tanchiki.libgdx.model.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.tanchiki.libgdx.stage.GameStage;
import com.tanchiki.libgdx.stage.MenuStage;
import com.tanchiki.libgdx.util.FontLoader;
import com.tanchiki.libgdx.util.Settings;
import com.tanchiki.libgdx.util.TextureLoader;
import com.tanchiki.libgdx.util.WeaponData;

public class StoreMenu extends Table {
    NinePatch background = new NinePatch(TextureLoader.getInstance().getIcons()[0][14], 4, 4, 4, 4);
    final float height = Gdx.graphics.getHeight() / 10f;
    Table context;
    TextureLoader textureLoader = TextureLoader.getInstance();

    private Table info;
    final float width;

    public StoreMenu() {
        setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        setPosition(0, 0);
        setBackground(new NinePatchDrawable(background));

        width = getWidth() - getWidth() * 0.10f * 2;

        createInfo();
        createContext();
    }

    private void createContext() {
        context = new Table();
		/*context.setSize(getWidth(), getHeight() - height);
		context.setPosition(0, 0);*/
        context.setBackground(new NinePatchDrawable(background));
        ScrollPane scrollPane = new ScrollPane(context);
        scrollPane.setScrollingDisabled(true, false);
        add(scrollPane).size(width, getHeight() - 2 * height).center().row();
		
		/*scrollPane.setSize(getWidth(), getHeight() - height);
		scrollPane.setPosition(0, 0);*/
        //add(scrollPane);

        addSeparator(textureLoader.getIcons()[0][4], "Базовое вооружение");

        addElement(new ElementInterface() {

            @Override
            public String getInfo() {
                return "#wСнаряд. ~#rБазовое оружие танка. Для всех видов снарядов цена указана в монетах за 10 штук. ~~Время перезарядки - #w20_единиц #r(приблизительно 1 секунда), при использовании ускорения время перезарядки сокращается еще на 5 единиц. Наносимый урон - #w1 HP.";
            }

            @Override
            public TextureRegion getMainIcon() {
                return textureLoader.getIcons()[0][4];
            }

            @Override
            public TextureRegion getValueIcon() {
                return textureLoader.getBonus()[0][17];
            }

            @Override
            public String getName() {
                return "Снаряд";
            }

            @Override
            public int getMaxCount() {
                return 100;
            }

            @Override
            public int getCount() {
                return WeaponData.light_bullet;
            }

            @Override
            public int getPrice() {
                return 5;
            }

            @Override
            public int getValue() {
                return Settings.TankUserSettings.coin;
            }

            @Override
            public boolean getEnable() {
                return WeaponData.Upgrade.bullet >= 0;
            }

            @Override
            public void accept() {
                WeaponData.light_bullet += 10;
                Settings.TankUserSettings.coin -= getPrice();
            }
        });
        addElement(new ElementInterface() {
            @Override
            public String getInfo() {
                return "#wПлазма. [X] ~#rГенератор плазмы - наша новая разработка. Время перезарядки #w15_единиц. #rНаносимый урон - #w1_HP. #rИспользование ускорения снижает время перезарядки еще на 5_единиц. ~~Для возможности покупки необходимо исследование снарядов ниже, в разделе исследований.	";
            }

            @Override
            public TextureRegion getMainIcon() {
                return textureLoader.getIcons()[0][5];
            }

            @Override
            public TextureRegion getValueIcon() {
                return textureLoader.getBonus()[0][17];
            }

            @Override
            public String getName() {
                return "Плазма";
            }

            @Override
            public int getMaxCount() {
                return 100;
            }

            @Override
            public int getCount() {
                return WeaponData.plazma;
            }

            @Override
            public int getPrice() {
                return 10;
            }

            @Override
            public int getValue() {
                return Settings.TankUserSettings.coin;
            }

            @Override
            public boolean getEnable() {
                return WeaponData.Upgrade.bullet >= 1;
            }

            @Override
            public void accept() {
                WeaponData.plazma += 10;
                Settings.TankUserSettings.coin -= getPrice();
            }
        });
        addElement(new ElementInterface() {
            @Override
            public String getInfo() {
                return "#wУдвоенный снаряд. [X] ~#rВремя перезарядки - #w20_единиц. #rНаносимый урон - #w2_HP. ~~#rПри лобовом столкновении двойного снаряда с обычным снарядом или плазмой - уничтожается только один снаряд из пары, второй же продолжает движение. ~~Для возможности покупки необходимо исследование снарядов второго уровня. ~~Для установки на танк более 40 единиц необходимо #wусовершенствованное шасси.";
            }

            @Override
            public TextureRegion getMainIcon() {
                return textureLoader.getIcons()[0][6];
            }

            @Override
            public TextureRegion getValueIcon() {
                return textureLoader.getBonus()[0][17];
            }

            @Override
            public String getName() {
                return "Двойной cнаряд";
            }

            @Override
            public int getMaxCount() {
                return WeaponData.modern_tank == 1 ? 100 : 40;
            }

            @Override
            public int getCount() {
                return WeaponData.double_light_bullet;
            }

            @Override
            public int getPrice() {
                return 15;
            }

            @Override
            public int getValue() {
                return Settings.TankUserSettings.coin;
            }

            @Override
            public boolean getEnable() {
                return WeaponData.Upgrade.bullet >= 2;
            }

            @Override
            public void accept() {
                WeaponData.double_light_bullet += 10;
                Settings.TankUserSettings.coin -= getPrice();
            }
        });
        addElement(new ElementInterface() {
            @Override
            public String getInfo() {
                return "#wУдвоенная плазма. [X] ~#rВремя перезарядки - #w15_единиц. #rНаносимый урон - #w2_HP. ~~#rДля возможности покупки необходимо исследование снарядов третьего уровня. ~~Для установки на танк более 40_единиц необходимо #wусовершенствованное шасси.";
            }

            @Override
            public TextureRegion getMainIcon() {
                return textureLoader.getIcons()[0][7];
            }

            @Override
            public TextureRegion getValueIcon() {
                return textureLoader.getBonus()[0][17];
            }

            @Override
            public String getName() {
                return "Двойнaя плaзма";
            }

            @Override
            public int getMaxCount() {
                return WeaponData.modern_tank == 1 ? 100 : 40;
            }

            @Override
            public int getCount() {
                return WeaponData.double_palzma;
            }

            @Override
            public int getPrice() {
                return 20;
            }

            @Override
            public int getValue() {
                return Settings.TankUserSettings.coin;
            }

            @Override
            public boolean getEnable() {
                return WeaponData.Upgrade.bullet >= 3;
            }

            @Override
            public void accept() {
                WeaponData.double_palzma += 10;
                Settings.TankUserSettings.coin -= getPrice();
            }
        });
        addElement(new ElementInterface() {
            @Override
            public String getInfo() {
                return "#wБронебойный снаряд. [X] ~#rВремя перезарядки - #w20_единиц. #rНаносимый урон - #w3_HP. ~~#rПри столкновении с обычным снарядом бронебойный снаряд теряет одну единицу урона, и продолжает движение. Т.е. одним выстрелом возможно уничтожить два встречных снаряда и при этом попасть во вражеский объект. ~~Для возможности покупки необходимо исследование снарядов четвертого уровня. ~~Для установки на танк более 10 единиц необходимо #wусовершенствованное шасси.";
            }

            @Override
            public TextureRegion getMainIcon() {
                return textureLoader.getIcons()[0][15];
            }

            @Override
            public TextureRegion getValueIcon() {
                return textureLoader.getBonus()[0][17];
            }

            @Override
            public String getName() {
                return "Бронебойный снаряд";
            }

            @Override
            public int getMaxCount() {
                return WeaponData.modern_tank == 1 ? 100 : 10;
            }

            @Override
            public int getCount() {
                return WeaponData.bronet_bullet;
            }

            @Override
            public int getPrice() {
                return 30;
            }

            @Override
            public int getValue() {
                return Settings.TankUserSettings.coin;
            }

            @Override
            public boolean getEnable() {
                return WeaponData.Upgrade.bullet >= 4;
            }

            @Override
            public void accept() {
                WeaponData.bronet_bullet += 10;
                Settings.TankUserSettings.coin -= getPrice();
            }
        });
        addElement(new ElementInterface() {
            @Override
            public String getInfo() {

                return "#wУсиленный бронебойный снаряд. [X] ~#rВремя перезарядки - #w20_единиц. #rНаносимый урон - #w5_HP. ~~#rДля возможности покупки необходимо исследование снарядов пятого уровня. ~~Для установки на танк более 10_единиц необходимо приобрести #wусовершенствованное шасси.";
            }

            @Override
            public TextureRegion getMainIcon() {
                return textureLoader.getIcons()[0][16];
            }

            @Override
            public TextureRegion getValueIcon() {
                return textureLoader.getBonus()[0][17];
            }

            @Override
            public String getName() {
                return "Усиленный бронеб.";
            }

            @Override
            public int getMaxCount() {
                return WeaponData.modern_tank == 1 ? 100 : 10;
            }

            @Override
            public int getCount() {
                return WeaponData.bronet_bullet2;
            }

            @Override
            public int getPrice() {
                return 35;
            }

            @Override
            public int getValue() {
                return Settings.TankUserSettings.coin;
            }

            @Override
            public boolean getEnable() {
                return WeaponData.Upgrade.bullet >= 5;
            }

            @Override
            public void accept() {
                WeaponData.bronet_bullet2 += 10;
                Settings.TankUserSettings.coin -= getPrice();
            }
        });
        addElement(new ElementInterface() {
            @Override
            public String getInfo() {

                return "#wБоеголовка. [X] ~#rДальность стрельбы не ограничена. ~~Наносимый урон - 9_HP #rв эпицентре. По всей области взрыва также уничтожаются вражеские мины. ~~Боеголовка НЕ пролетает над стенами, НЕ уничтожает бетонные стены и ежи. ~~Для возможности покупки необходимо исследование снарядов пятого уровня. ~~Для установки на танк более 10 единиц необходимо #wусовершенствованное шасси.";
            }

            @Override
            public TextureRegion getMainIcon() {
                return textureLoader.getIcons()[0][8];
            }

            @Override
            public TextureRegion getValueIcon() {
                return textureLoader.getBonus()[0][17];
            }

            @Override
            public String getName() {
                return "Боеголовка";
            }

            @Override
            public int getMaxCount() {
                return WeaponData.modern_tank == 1 ? 100 : 10;
            }

            @Override
            public int getCount() {
                return WeaponData.rocket;
            }

            @Override
            public int getPrice() {
                return 45;
            }

            @Override
            public int getValue() {
                return Settings.TankUserSettings.coin;
            }

            @Override
            public boolean getEnable() {
                return WeaponData.Upgrade.bullet >= 6;
            }

            @Override
            public void accept() {
                WeaponData.rocket += 10;
                Settings.TankUserSettings.coin -= getPrice();
            }
        });
        addSeparator(textureLoader.getIcons()[0][11], "Доп. вооружение");

        addElement(new ElementInterface() {
            @Override
            public String getInfo() {
                return "#wАртиллерия. [X] ~#rДальность стрельбы, клеток: " + (7 + (WeaponData.Upgrade.art >= 3 ? 1 : 0)) + ". #rПерелетает НАД любыми стенами и прочими препятствиями. ~~Наносимый урон - " + (9 + WeaponData.Upgrade.art * 3) + "_HP #rв эпицентре взрыва. Диаметр взрыва, клеток: " + (WeaponData.Upgrade.art == 5 ? 7 : 5) + ". #rС каждой клеткой вокруг эпицентра урон уменьшается на 1_HP. Уничтожает вражеские мины. ~~Для возможности покупки необходимо исследование артиллерии. Все параметры улучшаются с увеличением уровня исследования артиллеррии (указаны текущие параметры).";
            }

            @Override
            public TextureRegion getMainIcon() {
                return textureLoader.getIcons()[0][9];
            }

            @Override
            public TextureRegion getValueIcon() {
                return textureLoader.getBonus()[0][17];
            }

            @Override
            public String getName() {
                return "Артиллерия";
            }

            @Override
            public int getMaxCount() {
                return Integer.MAX_VALUE;
            }

            @Override
            public int getCount() {
                return WeaponData.art;
            }

            @Override
            public int getPrice() {
                return 60;
            }

            @Override
            public int getValue() {
                return Settings.TankUserSettings.coin;
            }

            @Override
            public boolean getEnable() {
                return WeaponData.Upgrade.art >= 1;
            }

            @Override
            public void accept() {
                WeaponData.art += 1;
                Settings.TankUserSettings.coin -= getPrice();
            }
        });
        addElement(new ElementInterface() {
            @Override
            public String getInfo() {

                return "#wМины ТМД-5. [X] ~#rНаносимый урон - #w5_HP. #rАктивируется при наезжании вражеского танка на мину, для танка игрока безвредна. ~~Для возможности покупки необходимо исследование мин.";
            }

            @Override
            public TextureRegion getMainIcon() {
                return textureLoader.getIcons()[0][10];
            }

            @Override
            public TextureRegion getValueIcon() {
                return textureLoader.getBonus()[0][17];
            }

            @Override
            public String getName() {
                return "Мина ТМД-5";
            }

            @Override
            public int getMaxCount() {
                return Integer.MAX_VALUE;
            }

            @Override
            public int getCount() {
                return WeaponData.mine1;
            }

            @Override
            public int getPrice() {
                return 10;
            }

            @Override
            public int getValue() {
                return Settings.TankUserSettings.coin;
            }

            @Override
            public boolean getEnable() {
                return WeaponData.Upgrade.mine >= 1;
            }

            @Override
            public void accept() {
                WeaponData.mine1 += 1;
                Settings.TankUserSettings.coin -= getPrice();
            }
        });
        addElement(new ElementInterface() {
            @Override
            public String getInfo() {

                return "#wМины ТМД-9. [X] ~#rНаносимый урон - #w9_HP. #rАктивируется при наезжании вражеского танка на мину, для танка игрока безвредна. ~~Для возможности покупки необходимо исследование мин второго уровня.";
            }

            @Override
            public TextureRegion getMainIcon() {
                return textureLoader.getIcons()[0][18];
            }

            @Override
            public TextureRegion getValueIcon() {
                return textureLoader.getBonus()[0][17];
            }

            @Override
            public String getName() {
                return "Мина ТМД-9";
            }

            @Override
            public int getMaxCount() {
                return Integer.MAX_VALUE;
            }

            @Override
            public int getCount() {
                return WeaponData.mine2;
            }

            @Override
            public int getPrice() {
                return 25;
            }

            @Override
            public int getValue() {
                return Settings.TankUserSettings.coin;
            }

            @Override
            public boolean getEnable() {
                return WeaponData.Upgrade.mine >= 2;
            }

            @Override
            public void accept() {
                WeaponData.mine2 += 1;
                Settings.TankUserSettings.coin -= getPrice();
            }
        });
        addElement(new ElementInterface() {
            @Override
            public String getInfo() {

                return "#wДинамит х70 ЭМ (электромагнитный) [X] ~#Наносимый урон - #w8_HP. #rАктивируется через 5 секунд, диаметр взрыва, клеток: #w5. ~~#rВсе типы динамита уничтожают противотанковые ежи и жёлтые бетонные стены, электромагнитная волна от взрыва ослабляет силовое поле танков. ~~Для возможности покупки необходимо исследование динамита.";
            }

            @Override
            public TextureRegion getMainIcon() {
                return textureLoader.getIcons()[0][19];
            }

            @Override
            public TextureRegion getValueIcon() {
                return textureLoader.getBonus()[0][17];
            }

            @Override
            public String getName() {
                return "Динамит х70";
            }

            @Override
            public int getMaxCount() {
                return Integer.MAX_VALUE;
            }

            @Override
            public int getCount() {
                return WeaponData.tnt1;
            }

            @Override
            public int getPrice() {
                return 35;
            }

            @Override
            public int getValue() {
                return Settings.TankUserSettings.coin;
            }

            @Override
            public boolean getEnable() {
                return WeaponData.Upgrade.tnt >= 1;
            }

            @Override
            public void accept() {
                WeaponData.tnt1 += 1;
                Settings.TankUserSettings.coin -= getPrice();
            }
        });
        addElement(new ElementInterface() {
            @Override
            public String getInfo() {

                return "#wДинамит х100 ЭМ (электромагнитный) [X] ~#Наносимый урон - #w12_HP. #rАктивируется через 5 секунд, диаметр взрыва, клеток: #w7, #rуменьшение повреждения вокруг эпицентра с каждой клеткой #w1_HP. ~~#rУничтожает противотанковые ежи и желтые бетонные стены. ~~Для возможности покупки необходимо исследование динамита второго уровня.";
            }

            @Override
            public TextureRegion getMainIcon() {
                return textureLoader.getIcons()[0][11];
            }

            @Override
            public TextureRegion getValueIcon() {
                return textureLoader.getBonus()[0][17];
            }

            @Override
            public String getName() {
                return "Динамит х100";
            }

            @Override
            public int getMaxCount() {
                return Integer.MAX_VALUE;
            }

            @Override
            public int getCount() {
                return WeaponData.tnt2;
            }

            @Override
            public int getPrice() {
                return 60;
            }

            @Override
            public int getValue() {
                return Settings.TankUserSettings.coin;
            }

            @Override
            public boolean getEnable() {
                return WeaponData.Upgrade.tnt >= 2;
            }

            @Override
            public void accept() {
                WeaponData.tnt2 += 1;
                Settings.TankUserSettings.coin -= getPrice();
            }
        });
        addElement(new ElementInterface() {
            @Override
            public String getInfo() {

                return "#wДинамит х160 ЭМ (электромагнитный) [X] ~#Наносимый урон - #w20_HP. #rОгромная площадь взрыва! Активируется через 5 секунд, диаметр взрыва, клеток: 9, #rуменьшение повреждения вокруг эпицентра с каждой клеткой #w1_HP. ~~#rУничтожает противотанковые ежи и желтые бетонные стены. ~~Для возможности покупки необходимо исследование динамита третьего уровня.";
            }

            @Override
            public TextureRegion getMainIcon() {
                return textureLoader.getIcons()[0][20];
            }

            @Override
            public TextureRegion getValueIcon() {
                return textureLoader.getBonus()[0][17];
            }

            @Override
            public String getName() {
                return "Динамит х160";
            }

            @Override
            public int getMaxCount() {
                return Integer.MAX_VALUE;
            }

            @Override
            public int getCount() {
                return WeaponData.tnt3;
            }

            @Override
            public int getPrice() {
                return 110;
            }

            @Override
            public int getValue() {
                return Settings.TankUserSettings.coin;
            }

            @Override
            public boolean getEnable() {
                return WeaponData.Upgrade.tnt >= 3;
            }

            @Override
            public void accept() {
                WeaponData.tnt3 += 1;
                Settings.TankUserSettings.coin -= getPrice();
            }
        });
        addElement(new ElementInterface() {
            @Override
            public String getInfo() {

                return "#wАвиаудар. [X] ~#rВызов бомбардировщика: наносимый урон - " + (5 + (WeaponData.Upgrade.air == 3 ? 2 : 0)) + " взрывов #rпо " + (12 + WeaponData.Upgrade.air * 4) + " HP #rкаждый вдоль траектории полёта бомбардировщика. Диаметр взрывов, клеток: " + (WeaponData.Upgrade.air >= 2 ? 7 : 5) + ". #rУничтожает противотанковые ежи и жёлтые бетонные стены. ~~Все параметры улучшаются с увеличением уровня исследования бомбардировки (указаны текущие параметры). ~~Для возможности покупки и вызова авиаудара необходимо провести исследование авиаудара и установить радар на танк.";
            }

            @Override
            public TextureRegion getMainIcon() {
                return textureLoader.getIcons()[0][22];
            }

            @Override
            public TextureRegion getValueIcon() {
                return textureLoader.getBonus()[0][17];
            }

            @Override
            public String getName() {
                return "Авиаудар";
            }

            @Override
            public int getMaxCount() {
                return Integer.MAX_VALUE;
            }

            @Override
            public int getCount() {
                return WeaponData.air;
            }

            @Override
            public int getPrice() {
                return 170;
            }

            @Override
            public int getValue() {
                return Settings.TankUserSettings.coin;
            }

            @Override
            public boolean getEnable() {
                return WeaponData.Upgrade.air >= 1 && WeaponData.radar == 1;
            }

            @Override
            public void accept() {
                WeaponData.air += 1;
                Settings.TankUserSettings.coin -= getPrice();
            }
        });
        //addSeparator(textureLoader.getIcons()[0][27], "Союзники");
        addSeparator(textureLoader.getIcons()[0][25], "Защита");
        addElement(new ElementInterface() {
            @Override
            public String getInfo() {

                return "#wВременная броня. [X] ~#rКаждая покупка добавляет вашему танку #w+1_HP. #rПосле потери жизни один уровень брони снимается. ~~Максимальный уровень временной брони " + (WeaponData.Upgrade.brone >= 3 ? 20 : 10) + " HP #r(зависит от уровня исследования, указан текущий максимум). ~~Для возможности покупки необходимо исследование брони.";
            }

            @Override
            public TextureRegion getMainIcon() {
                return textureLoader.getIcons()[0][24];
            }

            @Override
            public TextureRegion getValueIcon() {
                return textureLoader.getBonus()[0][17];
            }

            @Override
            public String getName() {
                return "Временная броня";
            }

            @Override
            public int getMaxCount() {
                return WeaponData.Upgrade.brone >= 3 ? 20 : 10;
            }

            @Override
            public int getCount() {
                return WeaponData.brone1;
            }

            @Override
            public int getPrice() {
                int p = 95 + getCount() * 5;
                return p - (int) (WeaponData.Upgrade.brone == 3 ? p * 0.3f : 0);
            }

            @Override
            public int getValue() {
                return Settings.TankUserSettings.coin;
            }

            @Override
            public boolean getEnable() {
                return WeaponData.Upgrade.brone >= 1;
            }

            @Override
            public void accept() {
                Settings.TankUserSettings.coin -= getPrice();
                WeaponData.brone1 += 1;
            }
        });
        addElement(new ElementInterface() {
            @Override
            public String getInfo() {
                return "#wПостоянная броня. [X] ~#rКаждая покупка добавляет вашему танку #w+1_HP. #rПосле потери жизни броня сохраняется. ~~Максимальный уровень постоянной брони " + (WeaponData.Upgrade.brone >= 3 ? 40 : 20) + " HP #r(зависит от уровня исследования, указан текущий максимум). ~~Для возможности покупки необходимо исследование брони второго уровня.";
            }

            @Override
            public TextureRegion getMainIcon() {
                return textureLoader.getIcons()[0][25];
            }

            @Override
            public TextureRegion getValueIcon() {
                return textureLoader.getBonus()[0][17];
            }

            @Override
            public String getName() {
                return "Постоянная броня";
            }

            @Override
            public int getMaxCount() {
                return WeaponData.Upgrade.brone >= 3 ? 40 : 20;
            }

            @Override
            public int getCount() {
                return WeaponData.brone2;
            }

            @Override
            public int getPrice() {
                int p = 120 + getCount() * 5;
                return p - (int) (WeaponData.Upgrade.brone == 3 ? p * 0.3f : 0);
            }

            @Override
            public int getValue() {
                return Settings.TankUserSettings.coin;
            }

            @Override
            public boolean getEnable() {
                return WeaponData.Upgrade.brone >= 2;
            }

            @Override
            public void accept() {
                Settings.TankUserSettings.coin -= getPrice();
                WeaponData.brone2 += 1;
            }
        });
        addElement(new ElementInterface() {
            @Override
            public String getInfo() {

                return "#wСиловое поле. [X] ~#rКаждая покупка добавляет вашему танку #w+1 силовой (синий) HP. #rСиловое поле восстанавливается самостоятельно (тем быстрее, чем больше у вас основного здоровья, т.е. если отремонтировать танк, силовое поле восстановится быстрее). ~~После потери жизни силовое поле исчезает. Максимальный общий уровень силового поля - " + (WeaponData.Upgrade.shield >= 3 ? 25 : 15) + " #r(зависит от уровня исследования силового поля, указан текущий максимум). ~~Купленное поле нужно активировать во время игры, выбрав его в списке оружия. ~~Для возможности покупки необходимо исследование силового поля.";
            }

            @Override
            public TextureRegion getMainIcon() {
                return textureLoader.getIcons()[0][23];
            }

            @Override
            public TextureRegion getValueIcon() {
                return textureLoader.getBonus()[0][17];
            }

            @Override
            public String getName() {
                return "Силовое поле";
            }

            @Override
            public int getMaxCount() {
                return Integer.MAX_VALUE;
            }

            @Override
            public int getCount() {
                return WeaponData.shield;
            }

            @Override
            public int getPrice() {
                int p = 95;
                return p - (int) (WeaponData.Upgrade.brone == 3 ? p * 0.3f : 0);
            }

            @Override
            public int getValue() {
                return Settings.TankUserSettings.coin;
            }

            @Override
            public boolean getEnable() {
                return WeaponData.Upgrade.shield >= 1;
            }

            @Override
            public void accept() {
                Settings.TankUserSettings.coin -= getPrice();
                WeaponData.shield += 1;
            }
        });
        addSeparator(textureLoader.getIcons()[0][26], "Ремонт");
        addElement(new ElementInterface() {
            @Override
            public String getInfo() {

                return "#wРемонтный комплект. [X] ~#rВосстанавливает основное HP танка на поле боя. На силовое поле не действует (силовое поле восстанавливается самостоятельно, либо с помощью пункта 'Ремонт танка' в мастерской). ~~Стоимость ремкомплекта равна #w[X28]#C #rза каждый пункт брони (цену можно уменьшить с помощью дополнительных исследований брони. Указана текущая стоимость).";
            }

            @Override
            public TextureRegion getMainIcon() {
                return textureLoader.getIcons()[0][26];
            }

            @Override
            public TextureRegion getValueIcon() {
                return textureLoader.getBonus()[0][17];
            }

            @Override
            public String getName() {
                return "Ремкомплект";
            }

            @Override
            public int getMaxCount() {
                return Integer.MAX_VALUE;
            }

            @Override
            public int getCount() {
                return WeaponData.fix;
            }

            @Override
            public int getPrice() {
                if (GameStage.getInstance().TankUser == null) return 0;
                int p = (int) (GameStage.getInstance().TankUser.HPBackup);
                return p * (WeaponData.Upgrade.brone == 3 ? 4 : 8);
            }

            @Override
            public int getValue() {
                return Settings.TankUserSettings.coin;
            }

            @Override
            public boolean getEnable() {
                return true;
            }

            @Override
            public void accept() {
                WeaponData.fix += 1;
                Settings.TankUserSettings.coin -= getPrice();
            }
        });
        addElement(new ElementInterface() {
            @Override
            public String getInfo() {

                return "#wРемонт и обслуживание танка. ~#rВо время боя можно вернуться в мастерскую и провести здесь ремонт, который обойдётся дешевле, чем использование ремкомплекта. Также обслуживание восстанавливает силовое поле полностью. ~~Стоимость ремонта равна #w[X29]#C #rза каждый потерянный пункт брони (цену можно уменьшить с помощью дополнительных исследований брони. Указана текущая стоимость без учета скидки).";
            }

            @Override
            public TextureRegion getMainIcon() {
                return textureLoader.getIcons()[0][27];
            }

            @Override
            public TextureRegion getValueIcon() {
                return textureLoader.getBonus()[0][17];
            }

            @Override
            public String getName() {
                return "Ремонт танка";
            }

            @Override
            public int getMaxCount() {
                return Integer.MAX_VALUE;
            }

            @Override
            public int getCount() {
                return 1;
            }

            @Override
            public int getPrice() {
                if (GameStage.getInstance().TankUser == null) return 0;
                int p = (int) (GameStage.getInstance().TankUser.HPBackup - GameStage.getInstance().TankUser.HP);
                return p * (WeaponData.Upgrade.brone == 3 ? 3 : 6);
            }

            @Override
            public int getValue() {
                return Settings.TankUserSettings.coin;
            }

            @Override
            public boolean getEnable() {
                if (GameStage.getInstance().TankUser == null) return false;
                return GameStage.getInstance().TankUser.HPBackup > GameStage.getInstance().TankUser.HP;
            }

            @Override
            public void accept() {
                Settings.TankUserSettings.coin -= getPrice();
                GameStage.getInstance().TankUser.HP = GameStage.getInstance().TankUser.HPBackup;
                GameStage.getInstance().TankUser.HPShield = GameStage.getInstance().TankUser.HPShieldBackup;
            }
        });
		/*addElement(new ElementInterface() {

				@Override
				public TextureRegion getMainIcon() {
					return textureLoader.getIcons()[0][27];
				}

				@Override
				public TextureRegion getValueIcon() {
					return textureLoader.getBonus()[0][17];
				}

				@Override
				public String getName() {
					return "Ремонт союзников";
				}

				@Override
				public int getMaxCount() {
					return Integer.MAX_VALUE;
				}

				@Override
				public int getCount() {
					return 1;
				}

				@Override
				public int getPrice() {
					int p = (int) (GameStage.getInstance().TankUser.HPbackup - GameStage.getInstance().TankUser.HP);
					return p * (WeaponData.Upgrade.brone == 3 ? 3 : 6);
				}

				@Override
				public int getValue() {
					return Settings.TankUserSettings.coin;
				}

				@Override
				public boolean getEnable() {
					return GameStage.getInstance().TankUser.HPbackup > GameStage.getInstance().TankUser.HP;
				}

				@Override
				public void accept() {
					Settings.TankUserSettings.coin -= getPrice();
					GameStage.getInstance().TankUser.HP = GameStage.getInstance().TankUser.HPbackup;
				}
			});*/
        addSeparator(textureLoader.getIcons()[0][28], "Прочие");
        addElement(new ElementInterface() {
            @Override
            public String getInfo() {

                return "#wРадар. [X] ~Позволяет увидеть карту территории. При дополнительной установке тепловизора на карте будут отмечены танки, пушки, бонусы. ~~Также необходим для возможности вызова авиаудара!";
            }

            @Override
            public TextureRegion getMainIcon() {
                return textureLoader.getIcons()[0][28];
            }

            @Override
            public TextureRegion getValueIcon() {
                return textureLoader.getBonus()[0][17];
            }

            @Override
            public String getName() {
                return "Радар";
            }

            @Override
            public int getMaxCount() {
                return 1;
            }

            @Override
            public int getCount() {
                return WeaponData.radar;
            }

            @Override
            public int getPrice() {
                return 210;
            }

            @Override
            public int getValue() {
                return Settings.TankUserSettings.coin;
            }

            @Override
            public boolean getEnable() {
                return true;
            }

            @Override
            public void accept() {
                WeaponData.radar += 1;
                Settings.TankUserSettings.coin -= getPrice();
            }
        });
        addElement(new ElementInterface() {
            @Override
            public String getInfo() {

                return "#wТепловизор. [X] ~#rПосле установки - на карте радара обозначаются противники, бонусы, мины. ~~Каждый уровень тепловизора добавляет #w+1 клетку #rк дальности разведки мин (по умолчанию мины видны в 2-х клетках от танка). ~~Максимальная мощность тепловизора, единиц: 4";
            }

            @Override
            public TextureRegion getMainIcon() {
                return textureLoader.getIcons()[0][29];
            }

            @Override
            public TextureRegion getValueIcon() {
                return textureLoader.getBonus()[0][17];
            }

            @Override
            public String getName() {
                return "Тепловизор";
            }

            @Override
            public int getMaxCount() {
                return 4;
            }

            @Override
            public int getCount() {
                return WeaponData.termovisor;
            }

            @Override
            public int getPrice() {
                return 150;
            }

            @Override
            public int getValue() {
                return Settings.TankUserSettings.coin;
            }

            @Override
            public boolean getEnable() {
                return WeaponData.radar >= 1;
            }

            @Override
            public void accept() {
                WeaponData.termovisor += 1;
                Settings.TankUserSettings.coin -= getPrice();
            }
        });
        addElement(new ElementInterface() {
            @Override
            public String getInfo() {

                return " #wУсовершенствованное шасси. [X] ~#rПозволяет нести на танке 99 бронебойных снарядов (вместо 10), 99 двойных снарядов и двойной плазмы (вместо 40), ~~увеличивает базовую броню на #w[X32]_HP, ~~#rувеличивает время действия ускорения на #w[X51] секунд #rи заморозки на #w[X52]";
            }

            @Override
            public TextureRegion getMainIcon() {
                return textureLoader.getTankHeavy()[0][0];
            }

            @Override
            public TextureRegion getValueIcon() {
                return textureLoader.getBonus()[0][17];
            }

            @Override
            public String getName() {
                return "Усов. шасси танка";
            }

            @Override
            public int getMaxCount() {
                return 1;
            }

            @Override
            public int getCount() {
                return WeaponData.modern_tank;
            }

            @Override
            public int getPrice() {
                return 910;
            }

            @Override
            public int getValue() {
                return Settings.TankUserSettings.coin;
            }

            @Override
            public boolean getEnable() {
                return true;
            }

            @Override
            public void accept() {
                WeaponData.modern_tank += 1;
                Settings.TankUserSettings.coin -= getPrice();
            }
        });
        addElement(new ElementInterface() {
            @Override
            public String getInfo() {

                return "#wУскорение. [X] ~#rУвеличивает скорость танка на #w1_единицу, #rуменьшает время перезарядки на #w5_единиц. #rВремя действия - #w[X33] секунд #r(если вы на песке, то ускорение длится на треть дольше).";
            }

            @Override
            public TextureRegion getMainIcon() {
                return textureLoader.getIcons()[0][13];
            }

            @Override
            public TextureRegion getValueIcon() {
                return textureLoader.getBonus()[0][17];
            }

            @Override
            public String getName() {
                return "Ускорение";
            }

            @Override
            public int getMaxCount() {
                return Integer.MAX_VALUE;
            }

            @Override
            public int getCount() {
                return WeaponData.speed;
            }

            @Override
            public int getPrice() {
                return 35;
            }

            @Override
            public int getValue() {
                return Settings.TankUserSettings.coin;
            }

            @Override
            public boolean getEnable() {
                return true;
            }

            @Override
            public void accept() {
                WeaponData.speed += 1;
                Settings.TankUserSettings.coin -= getPrice();
            }
        });
        addElement(new ElementInterface() {
            @Override
            public String getInfo() {

                return "#wЗаморозка. [X] ~#rОстанавливает движение вражеских танков, пушек и патронов на #w[X34] секунд. ~~#rЕсли же вы подбираете песочные часы на поле боя, то время действия составит #w[X35] секунд.";
            }

            @Override
            public TextureRegion getMainIcon() {
                return textureLoader.getIcons()[0][12];
            }

            @Override
            public TextureRegion getValueIcon() {
                return textureLoader.getBonus()[0][17];
            }

            @Override
            public String getName() {
                return "Заморозка";
            }

            @Override
            public int getMaxCount() {
                return Integer.MAX_VALUE;
            }

            @Override
            public int getCount() {
                return WeaponData.time;
            }

            @Override
            public int getPrice() {
                return 120;
            }

            @Override
            public int getValue() {
                return Settings.TankUserSettings.coin;
            }

            @Override
            public boolean getEnable() {
                return true;
            }

            @Override
            public void accept() {
                WeaponData.time += 1;
                Settings.TankUserSettings.coin -= getPrice();
            }
        });
        addElement(new ElementInterface() {
            @Override
            public String getInfo() {

                return "#wДополнительная жизнь. [X] ~#rДля прохождения некоторых уровней нужно иметь больше одной жизни в запасе. ~~Если вам не удаётся пройти уровень - попробуйте купить дополнительные жизни, и у вас будет больше попыток.";
            }

            @Override
            public TextureRegion getMainIcon() {
                return textureLoader.getBonus()[0][5];
            }

            @Override
            public TextureRegion getValueIcon() {
                return textureLoader.getBonus()[0][17];
            }

            @Override
            public String getName() {
                return "Доп. жизнь";
            }

            @Override
            public int getMaxCount() {
                return Integer.MAX_VALUE;
            }

            @Override
            public int getCount() {
                return WeaponData.live;
            }

            @Override
            public int getPrice() {
                return 170;
            }

            @Override
            public int getValue() {
                return Settings.TankUserSettings.coin;
            }

            @Override
            public boolean getEnable() {
                return true;
            }

            @Override
            public void accept() {
                WeaponData.live += 1;
                Settings.TankUserSettings.coin -= getPrice();
            }
        });
        addSeparator(textureLoader.getIcons()[0][23], "Исследования");
        addElement(new ElementInterface() {
            @Override
            public String getInfo() {

                return "#wДобавочные проценты. ~#rВ конце каждого уровня вы получаете к вашей заработанной сумме монет процент, который вы можете увеличить, исследовав добавочные проценты. ~~Максимальный уровень - 15%.";
            }

            @Override
            public TextureRegion getMainIcon() {
                return textureLoader.getBonus()[0][17];
            }

            @Override
            public TextureRegion getValueIcon() {
                return textureLoader.getBonus()[0][14];
            }

            @Override
            public String getName() {
                return "Добавочные проценты";
            }

            @Override
            public int getMaxCount() {
                return 3;
            }

            @Override
            public int getCount() {
                return WeaponData.Upgrade.adding_percent;
            }

            @Override
            public int getPrice() {
                int p = 9 + getCount() * 2;
                return p;
            }

            @Override
            public int getValue() {
                return Settings.TankUserSettings.star;
            }

            @Override
            public boolean getEnable() {
                return true;
            }

            @Override
            public void accept() {
                Settings.TankUserSettings.star -= getPrice();
                WeaponData.Upgrade.adding_percent += 1;
            }
        });
        addElement(new ElementInterface() {
            @Override
            public String getInfo() {
                return "#wИсследовать снаряды. ~#rМаксимальный уровень исследования - 6. ~~#w1 уровень #rоткрывает для покупки плазму. ~#w2 уровень #r- двойной снаряд. ~#w3 уровень #r- двойную плазму. ~#w4 уровень #r- бронебойный снаряд. ~#w5 уровень #r- усиленный бронебойный снаряд. ~~#w6 уровень - боеголовку";
            }

            @Override
            public TextureRegion getMainIcon() {
                return textureLoader.getIcons()[0][15];
            }

            @Override
            public TextureRegion getValueIcon() {
                return textureLoader.getBonus()[0][14];
            }

            @Override
            public String getName() {
                return "Иссл. снаряды";
            }

            @Override
            public int getMaxCount() {
                return 6;
            }

            @Override
            public int getCount() {
                return WeaponData.Upgrade.bullet;
            }

            @Override
            public int getPrice() {
                int p = 5 + getCount() * 2;
                return p;
            }

            @Override
            public int getValue() {
                return Settings.TankUserSettings.star;
            }

            @Override
            public boolean getEnable() {
                return true;
            }

            @Override
            public void accept() {
                Settings.TankUserSettings.star -= getPrice();
                WeaponData.Upgrade.bullet += 1;
            }
        });
        addElement(new ElementInterface() {
            @Override
            public String getInfo() {

                return "#wИсследовать артиллерию. ~#rМаксимальный уровень - 5. ~~#w1 уровень #rпозволяет закупать артиллерию - заряд, перелетающий через все препятствия, стены. Сила взрыва в эпицентре - 12_HP. ~~#w2 уровень #rувеличивает силу взрыва до 15_HP. ~~#w3 уровень #rувеличивает дальность стрельбы на 1 клетку. ~~#w4 уровень #rувеличивает силу взрыва до 17_HP. ~~#w5 уровень #rувеличивает диаметр взрыва на 2_клетки и силу взрыва до 24_HP в эпицентре.";
            }

            @Override
            public TextureRegion getMainIcon() {
                return textureLoader.getIcons()[0][9];
            }

            @Override
            public TextureRegion getValueIcon() {
                return textureLoader.getBonus()[0][14];
            }

            @Override
            public String getName() {
                return "Иссл. артиллерию";
            }

            @Override
            public int getMaxCount() {
                return 5;
            }

            @Override
            public int getCount() {
                return WeaponData.Upgrade.art;
            }

            @Override
            public int getPrice() {
                int p = 6 + getCount() * 2;
                return p;
            }

            @Override
            public int getValue() {
                return Settings.TankUserSettings.star;
            }

            @Override
            public boolean getEnable() {
                return true;
            }

            @Override
            public void accept() {
                Settings.TankUserSettings.star -= getPrice();
                WeaponData.Upgrade.art += 1;
            }
        });
        addElement(new ElementInterface() {
            @Override
            public String getInfo() {

                return "#wИсследовать мины. ~#rМаксимальный уровень - 2. ~~#w1 уровень #rпозволяет закупать мины ТМД-5, ~~#w2 уровень #rпозволяет закупать мины ТМД-9. ~~Мины представляют опасность только для вражеских танков.";
            }

            @Override
            public TextureRegion getMainIcon() {
                return textureLoader.getIcons()[0][10];
            }

            @Override
            public TextureRegion getValueIcon() {
                return textureLoader.getBonus()[0][14];
            }

            @Override
            public String getName() {
                return "Иссл. мины";
            }

            @Override
            public int getMaxCount() {
                return 2;
            }

            @Override
            public int getCount() {
                return WeaponData.Upgrade.mine;
            }

            @Override
            public int getPrice() {
                int p = 6 + getCount() * 2;
                return p;
            }

            @Override
            public int getValue() {
                return Settings.TankUserSettings.star;
            }

            @Override
            public boolean getEnable() {
                return true;
            }

            @Override
            public void accept() {
                Settings.TankUserSettings.star -= getPrice();
                WeaponData.Upgrade.mine += 1;
            }
        });
        addElement(new ElementInterface() {
            @Override
            public String getInfo() {

                return "#wИсследовать динамит. ~#rМаксимальный уровень - 3. ~~#w1 уровень #rпозволяет закупать динамит x70. ~~#w2 уровень #rпозволяет закупать динамит x100. ~~#w3 уровень #rпозволяет закупать динамит x160 c гораздо большей площадью взрыва. ~~Все типы динамита уничтожают противотанковые ежи, желтые бетонные стены, мины. ~~Также динамит эффективен против силового поля.";
            }

            @Override
            public TextureRegion getMainIcon() {
                return textureLoader.getIcons()[0][11];
            }

            @Override
            public TextureRegion getValueIcon() {
                return textureLoader.getBonus()[0][14];
            }

            @Override
            public String getName() {
                return "Иссл. динамит";
            }

            @Override
            public int getMaxCount() {
                return 3;
            }

            @Override
            public int getCount() {
                return WeaponData.Upgrade.tnt;
            }

            @Override
            public int getPrice() {
                int p = 6 + getCount() * 2;
                return p;
            }

            @Override
            public int getValue() {
                return Settings.TankUserSettings.star;
            }

            @Override
            public boolean getEnable() {
                return true;
            }

            @Override
            public void accept() {
                Settings.TankUserSettings.star -= getPrice();
                WeaponData.Upgrade.tnt += 1;
            }
        });
        addElement(new ElementInterface() {
            @Override
            public String getInfo() {

                return "#wИсследовать броню. ~#rМаксимальный уровень - 3. ~~#w1 уровень #rоткрывает возможность установки на танк временной брони (при каждой потере жизни танком снимается по одному_HP временной брони). ~~#w2 уровень #rоткрывает возможность установки на танк постоянной брони. ~~#w3 уровень #rделает дешевле каждую единицу брони, снижает базовую цену на броню и силовое поле на 30%, увеличивает максимальный уровень временной и постоянной брони, делает дешевле ремонт танка и ремкомплект за каждый пункт HP.";
            }

            @Override
            public TextureRegion getMainIcon() {
                return textureLoader.getIcons()[0][25];
            }

            @Override
            public TextureRegion getValueIcon() {
                return textureLoader.getBonus()[0][14];
            }

            @Override
            public String getName() {
                return "Иссл. броню";
            }

            @Override
            public int getMaxCount() {
                return 3;
            }

            @Override
            public int getCount() {
                return WeaponData.Upgrade.brone;
            }

            @Override
            public int getPrice() {
                int p = 8 + getCount() * 2;
                return p;
            }

            @Override
            public int getValue() {
                return Settings.TankUserSettings.star;
            }

            @Override
            public boolean getEnable() {
                return true;
            }

            @Override
            public void accept() {
                Settings.TankUserSettings.star -= getPrice();
                WeaponData.Upgrade.brone += 1;
            }
        });
        addElement(new ElementInterface() {
            @Override
            public String getInfo() {

                return "#wИсследовать силовое поле. ~#rМаксимальный уровень - 4. ~~#w1 уровень #rоткрывает возможность установки на танк силового поля. ~~#w2, 3 уровень #rувеличивает скорость восстановления силового поля. ~~#w4 уровень #rтакже увеливичает скорость восстановления и увеличивает максимальный уровень силового поля";
            }

            @Override
            public TextureRegion getMainIcon() {
                return textureLoader.getIcons()[0][23];
            }

            @Override
            public TextureRegion getValueIcon() {
                return textureLoader.getBonus()[0][14];
            }

            @Override
            public String getName() {
                return "Иссл. силовое поле";
            }

            @Override
            public int getMaxCount() {
                return 4;
            }

            @Override
            public int getCount() {
                return WeaponData.Upgrade.shield;
            }

            @Override
            public int getPrice() {
                int p = 8 + getCount() * 2;
                return p;
            }

            @Override
            public int getValue() {
                return Settings.TankUserSettings.star;
            }

            @Override
            public boolean getEnable() {
                return true;
            }

            @Override
            public void accept() {
                Settings.TankUserSettings.star -= getPrice();
                WeaponData.Upgrade.shield += 1;
            }
        });
        addElement(new ElementInterface() {
            @Override
            public String getInfo() {

                return "#wИсследовать авиаудар. ~#rМаксимальный уровень - 3. ~~#w1 уровень и наличие радара#r открывают возможность покупки вызова бомбардировщика. Повреждение от каждой бомбы - 16 HP по всей площади взрыва. ~~#w2 уровень #rувеличивает повреждения от каждой сбрасываемой бомбы до 20_HP и увеличивает диаметр взрыва на 2 клетки, ~~#w3 уровень #rувеличивает количество сбрасываемых бомб с 5 до 7 и увеличивает силу каждого взрыва до 24_HP. ~~Взрывы от бомб уничтожают противотанковые ежи, желтые бетонные стены, мины.";
            }

            @Override
            public TextureRegion getMainIcon() {
                return textureLoader.getIcons()[0][22];
            }

            @Override
            public TextureRegion getValueIcon() {
                return textureLoader.getBonus()[0][14];
            }

            @Override
            public String getName() {
                return "Иссл. авиаудар";
            }

            @Override
            public int getMaxCount() {
                return 3;
            }

            @Override
            public int getCount() {
                return WeaponData.Upgrade.air;
            }

            @Override
            public int getPrice() {
                int p = 9 + getCount() * 2;
                return p;
            }

            @Override
            public int getValue() {
                return Settings.TankUserSettings.star;
            }

            @Override
            public boolean getEnable() {
                return true;
            }

            @Override
            public void accept() {
                Settings.TankUserSettings.star -= getPrice();
                WeaponData.Upgrade.air += 1;
            }
        });
		/*addElement(new ElementInterface() {

				@Override
				public TextureRegion getMainIcon() {
					return textureLoader.getIcons()[0][27];
				}

				@Override
				public TextureRegion getValueIcon() {
					return textureLoader.getBonus()[0][14];
				}

				@Override
				public String getName() {
					return "Иссл. союзн. танки";
				}

				@Override
				public int getMaxCount() {
					return 2;
				}

				@Override
				public int getCount() {
					return WeaponData.Upgrade.tank_unity;
				}

				@Override
				public int getPrice() {
					int p = 10 + getCount() * 2;
					return p;
				}

				@Override
				public int getValue() {
					return Settings.TankUserSettings.star;
				}

				@Override
				public boolean getEnable() {
					return true;
				}

				@Override
				public void accept() {
					Settings.TankUserSettings.star -= getPrice();
					WeaponData.Upgrade.tank_unity += 1;
				}
			});*/
    }

    private void addSeparator(TextureRegion region, String name) {
        Table table = new Table();
        table.setBackground(new TextureRegionDrawable(new Texture("texture/ui/billet.png")));
        table.left().add(new Image(region)).pad(3);
        table.left().add(new Label(name, new Label.LabelStyle(FontLoader.f16, Color.WHITE)));
        context.add(table).top().width(width).row();
    }

    private void addElement(ElementInterface element) {
        context.add(new Element(element)).top().width(getWidth() / 2).padTop(3).row();
    }

    private void createInfo() {
        info = new Table();
		/*info.setSize(getWidth(), height);
		info.setPosition(0, getHeight() - info.getHeight());*/
        info.setBackground(new NinePatchDrawable(background));
        add(info).center().width(width).row();

        Table element1 = new Table();
        Image img1 = new Image(TextureLoader.getInstance().getBonus()[0][14]);
        Label text1 = new Label("10", new Label.LabelStyle(FontLoader.f24, Color.WHITE)) {
            @Override
            public void draw(Batch b, float a) {
                setText("Звезд: " + Settings.TankUserSettings.star);
                super.draw(b, a);
            }
        };
        element1.center().add(img1).pad(3);
        element1.center().add(text1);

        Table element2 = new Table();
        Image img2 = new Image(TextureLoader.getInstance().getBonus()[0][17]);
        Label text2 = new Label("10", new Label.LabelStyle(FontLoader.f24, Color.WHITE)) {
            @Override
            public void draw(Batch b, float a) {
                setText("Монет: " + Settings.TankUserSettings.coin);
                super.draw(b, a);
            }
        };
        element2.center().add(img2).pad(3);
        element2.center().add(text2);

        info.center().add(element2).pad(10);
        info.center().add(element1).pad(10);
    }

    private class Element extends Table {
        ElementInterface inter;

        public Element(final ElementInterface inter) {
            this.inter = inter;
            Table name = new Table();
            add(name).width(width / 3);
            name.left();
            name.add(new Image(inter.getMainIcon())).pad(3);
            name.add(new Label(inter.getName(), new Label.LabelStyle(FontLoader.f24, Color.WHITE)));

            Table count = new Table();
            add(count).width(width / 3);
            count.right();
            TextButton.TextButtonStyle bstyle = new TextButton.TextButtonStyle(null, null, null, FontLoader.f24);
            TextButton sale = new TextButton("-", bstyle);
            sale.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent e, float x, float y) {

                }
            });
            //count.add(sale).pad(10);
            count.add(new Label("10", new Label.LabelStyle(FontLoader.f24, Color.WHITE)) {
                @Override
                public void act(float delta) {
                    super.act(delta);
                    if (GameStage.getInstance().TankUser != null) setText(inter.getCount());
                }
            }).pad(20);
            TextButton pay = new TextButton("+", bstyle);
            pay.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent e, float x, float y) {
                    if (inter.getValue() >= inter.getPrice() && inter.getEnable() && inter.getCount() < inter.getMaxCount())
                        inter.accept();
                }
            });
            count.add(pay).pad(20);
            final TextButton info = new TextButton("?", bstyle);
            info.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent e, float x, float y) {
                    final MenuStage.ViewTable table = new MenuStage.ViewTable(null, null);
                    context.setVisible(false);
                    StoreMenu.this.info.setVisible(false);
                    //table.setSize(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);
                    table.setBackground(new NinePatchDrawable(new NinePatch(TextureLoader.getInstance().getIcons()[0][14], 4, 4, 4, 4)));
                    MenuStage.ViewTable area = new MenuStage.ViewTable(null, null);
                    area.area(FontLoader.format(inter.getInfo()));
                    ScrollPane scroll = new ScrollPane(area);
                    scroll.setScrollingDisabled(true, false);
                    table.add(scroll).pad(2).center().size(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f).row();
                    table.addSeparator();
                    table.button("Назад", new ClickListener() {
                        @Override
                        public void clicked(InputEvent e, float x, float y) {
                            super.clicked(e, x, y);
                            context.setVisible(true);
                            StoreMenu.this.info.setVisible(true);
                            table.remove();
                        }
                    });
                    table.pack();
                    table.setPosition(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f, Align.center);
                    StoreMenu.this.addActor(table);
                }
            });
            count.add(info).pad(20);

            Table price = new Table();
            add(price).width(width / 3);
            price.right();
            price.add(new Label("", new Label.LabelStyle(FontLoader.f24, Color.WHITE)) {
                @Override
                public void act(float delta) {
                    super.act(delta);
                    if (GameStage.getInstance().TankUser != null) setText(inter.getPrice());
                }
            });
            price.add(new Image(inter.getValueIcon())).pad(3);
        }

        @Override
        public void act(float delta) {
            if (inter.getValue() >= inter.getPrice() && inter.getEnable() && inter.getCount() < inter.getMaxCount())
                setColor(1, 1, 1, 1);
            else
                setColor(1, 1, 1, 0.5f);
            super.act(delta);
        }
    }

    private interface ElementInterface {
        TextureRegion getMainIcon();

        TextureRegion getValueIcon();

        String getName();

        String getInfo();

        int getMaxCount();

        int getCount();

        int getPrice();

        int getValue();

        boolean getEnable();

        void accept();
    }
}
