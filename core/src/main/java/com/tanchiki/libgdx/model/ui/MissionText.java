package com.tanchiki.libgdx.model.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.tanchiki.libgdx.graphics.GameGroup;
import com.tanchiki.libgdx.stage.PanelStage;
import com.tanchiki.libgdx.util.FontLoader;
import com.tanchiki.libgdx.util.ObjectClass;

public class MissionText extends GameGroup {
    private static Label num;
    private static Label n;

    private static Action show = Actions.alpha(1, 0.01f);
    private static Action hide = Actions.alpha(0, 0.1f);

    private static MissionText obj = null;

    private float time = 0;

    public MissionText() {
        Label.LabelStyle style = new Label.LabelStyle(FontLoader.f20, Color.WHITE);

        num = new Label("Миссия " + 1, style);
        //num.setAlignment(Align.center);
        num.setSize(Gdx.graphics.getWidth() / 4, Gdx.graphics.getHeight() / 20);
        num.setPosition(Gdx.graphics.getWidth() / 2 - getWidth() / 2, Gdx.graphics.getHeight() / 2 - getHeight() / 2 + num.getHeight() / 2);
        addActor(num);

        n = new Label("jsjsjsjsjd", style);
        //n.setAlignment(Align.center);
        n.setSize(Gdx.graphics.getWidth() / 4, Gdx.graphics.getHeight() / 20);
        n.setPosition(Gdx.graphics.getWidth() / 2 - getWidth() / 2, Gdx.graphics.getHeight() / 2 - getHeight() / 2 - n.getHeight() / 2);
        addActor(n);

        //addAction(Actions.alpha(0));
    }

    @Override
    public void act(float delta) {
        if (time >= 3f)
            remove();

        time += delta;
        // TODO: Implement this method
        super.act(delta);
    }


    public static void show(int number, String name) {
        PanelStage p = ObjectClass.PanelStage;

        if (obj != null)
            obj.remove();

        obj = new MissionText();
        num.setText("Миссия " + number);
        n.setText(name);

        p.addActor(obj);

        //show.reset();
        //hide.reset();
        //obj.addAction(show);
        //obj.addAction(Actions.alpha(1,0.01f));
    }
}
