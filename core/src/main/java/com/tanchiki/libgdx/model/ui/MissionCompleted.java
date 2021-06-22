package com.tanchiki.libgdx.model.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.tanchiki.libgdx.stage.AboutStage;
import com.tanchiki.libgdx.stage.GameStage;
import com.tanchiki.libgdx.stage.PanelStage;
import com.tanchiki.libgdx.util.FontLoader;
import com.tanchiki.libgdx.util.SavePreferences;

public class MissionCompleted {
    public static boolean isShow = false;
    public static boolean show = true;
    public static boolean isMissionCompleted = false;

    public static final int MISSION_FAILED = -1,
            MISSION_COMPLETED = 1;

    public static void show(int mode) {
        show(mode, "Миссия выполнена!");
    }

    public static void show(int mode, String msg) {
        if (!isShow) {
            Stage stage = PanelStage.getInstance();
            switch (mode) {
                case MISSION_COMPLETED:
                    isMissionCompleted = true;
                    if (!show) return;
                    PanelStage.getInstance().addToast(msg);
                    TextButton button = new TextButton((char) 514 + " Нажмите чтобы продолжить", new TextButton.TextButtonStyle(
                            null,
                            new TextureRegionDrawable(new Texture("texture/ui/billet.png")),
                            null,
                            FontLoader.f30));
                    button.setY(0);
                    button.setX(stage.getWidth() / 2, Align.center);
                    button.addListener(new Completed(button));
                    stage.addActor(button);
                    break;
                case MISSION_FAILED:
                    AboutStage.getInstance().showFailed();
                    break;
            }
            isShow = true;
        }
    }

    private static class Completed extends ClickListener {
        TextButton button;

        public Completed(TextButton button) {
            this.button = button;
        }

        @Override
        public void clicked(InputEvent event, float x, float y) {
            super.clicked(event, x, y);
            AboutStage.getInstance().showEnd();
            ++GameStage.next_level;
            SavePreferences.getInstance().saveContinues();
            button.remove();
        }
    }
}
