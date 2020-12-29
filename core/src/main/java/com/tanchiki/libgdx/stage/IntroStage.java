package com.tanchiki.libgdx.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;

public class IntroStage extends Stage {
    //float time = 0;

    LibGDXLogo LibGDXLogo;

    VendorLogo VendorLogo;

    GameLogo GameLogo;

    public IntroStage() {
        LibGDXLogo = new LibGDXLogo();

        VendorLogo = new VendorLogo();

        GameLogo = new GameLogo();
        addActor(LibGDXLogo);

    }


    private static class GameLogo extends Actor {
        Texture libgdx_logo = new Texture("texture/ui/intro_logo.png");
        Sprite s = new Sprite(libgdx_logo);

        float time = 0;

        public GameLogo() {
            s.setSize(s.getWidth() * 2, s.getHeight() * 2);
            s.setCenter(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);

        }

        @Override
        public void draw(Batch batch, float parentAlpha) {
            // TODO: Implement this method
            super.draw(batch, parentAlpha);
            s.draw(batch);
        }

        @Override
        public void act(float delta) {

            // TODO: Implement this method
            super.act(delta);
        }

    }

    private static class VendorLogo extends Actor {
        Texture libgdx_logo = new Texture("texture/ui/vendor_logo.png");
        Sprite s = new Sprite(libgdx_logo);

        float alpha = 0;

        float time = 0;

        public VendorLogo() {
            //s.setSize(100,100);
            s.setCenter(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        }

        @Override
        public void draw(Batch batch, float parentAlpha) {
            // TODO: Implement this method
            super.draw(batch, parentAlpha);
            s.draw(batch);
        }

        @Override
        public void act(float delta) {
            s.setSize(s.getWidth() + 10 * delta, s.getHeight() + 10 * delta);
            s.setCenter(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);

            time += delta;

            if (alpha <= 1 && alpha >= 0)
                s.setAlpha(alpha);

            if (time <= 2)
                alpha += delta;
            else
                alpha -= delta;

            // TODO: Implement this method
            super.act(delta);
        }

    }

    private static class LibGDXLogo extends Actor {
        Texture libgdx_logo = new Texture("texture/ui/libgdx_logo.png");
        Sprite s = new Sprite(libgdx_logo);
        AlphaAction a;

        public LibGDXLogo() {
            s.setSize(Gdx.graphics.getWidth() / 1.5f, Gdx.graphics.getHeight() / 1.5f);
            s.setCenter(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);

            addAction(Actions.scaleBy(1, 1, 0.3f));
        }

        @Override
        public void draw(Batch batch, float parentAlpha) {
            // TODO: Implement this method
            super.draw(batch, parentAlpha);
            //s.setSize(getWidth(),getHeight());
            s.draw(batch);
        }

    }
}
