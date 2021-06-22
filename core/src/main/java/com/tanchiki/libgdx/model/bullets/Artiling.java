package com.tanchiki.libgdx.model.bullets;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.tanchiki.libgdx.model.explosions.BiggestExplosion;
import com.tanchiki.libgdx.util.ObjectVariables;
import com.tanchiki.libgdx.util.SoundLoader;
import com.tanchiki.libgdx.util.TextureLoader;

public class Artiling extends Bullet {
    float x, y;
    Sprite ball;
    Sprite ball1, ball2, ball3;

    private float distance, dst2;
    private float radius;
    private float c = 1f / 4;
    public int damage = 12;
    public int diameter = 6 * 2;

    public Artiling(float x, float y, float distance, int angle) {
        super(x, y, angle, 0.2f, ObjectVariables.tank_ally, new Array<>(new TextureRegion[]{
                TextureLoader.getInstance().getBullets()[0][12],
                TextureLoader.getInstance().getBullets()[0][12],
                TextureLoader.getInstance().getBullets()[0][12],
                TextureLoader.getInstance().getBullets()[0][12]
        }));
        this.x = x;
        this.y = y;

        sound = SoundLoader.getInstance().getShellArtillery();

        this.distance = distance;
        dst2 = distance * distance;
        radius = distance / 2;

        ball = new Sprite(TextureLoader.getInstance().getBullets()[0][22]);
        ball1 = new Sprite(TextureLoader.getInstance().getBullets()[0][21]);
        ball2 = new Sprite(TextureLoader.getInstance().getBullets()[0][20]);
        ball3 = new Sprite(TextureLoader.getInstance().getBullets()[0][19]);
        ball.setSize(ball.getWidth() / 10 * ObjectVariables.size_block, ball.getHeight() / 10 * ObjectVariables.size_block);
        ball1.setSize(ball1.getWidth() / 10 * ObjectVariables.size_block, ball1.getHeight() / 10 * ObjectVariables.size_block);
        ball2.setSize(ball2.getWidth() / 10 * ObjectVariables.size_block, ball2.getHeight() / 10 * ObjectVariables.size_block);
        ball3.setSize(ball3.getWidth() / 10 * ObjectVariables.size_block, ball3.getHeight() / 10 * ObjectVariables.size_block);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        switch (angle) {
            case 4:
                ball.setCenter(getX(Align.center), halfCircleX(getX(Align.center) + radius));
                ball1.setCenter(getX(Align.center) + 1, halfCircleX(getX(Align.center) + 1 + radius));
                ball2.setCenter(getX(Align.center) + 2, halfCircleX(getX(Align.center) + 2 + radius));
                ball3.setCenter(getX(Align.center) + 3, halfCircleX(getX(Align.center) + 3 + radius));
                break;
            case 2:
                ball.setCenter(getX(Align.center), halfCircleX(getX(Align.center) - radius));
                ball1.setCenter(getX(Align.center) - 1, halfCircleX(getX(Align.center) - 1 - radius));
                ball2.setCenter(getX(Align.center) - 2, halfCircleX(getX(Align.center) - 2 - radius));
                ball3.setCenter(getX(Align.center) - 3, halfCircleX(getX(Align.center) - 3 - radius));
                break;
            case 3:
                ball.setCenter(halfCircleY(getY(Align.center) - radius), getY(Align.center));
                ball1.setCenter(halfCircleY(getY(Align.center) - 1 - radius), getY(Align.center) - 1);
                ball2.setCenter(halfCircleY(getY(Align.center) - 2 - radius), getY(Align.center) - 2);
                ball3.setCenter(halfCircleY(getY(Align.center) - 3 - radius), getY(Align.center) - 3);
                break;
            case 1:
                ball.setCenter(halfCircleY(getY(Align.center) + radius), getY(Align.center));
                ball1.setCenter(halfCircleY(getY(Align.center) + 1 + radius), getY(Align.center) + 1);
                ball2.setCenter(halfCircleY(getY(Align.center) + 2 + radius), getY(Align.center) + 2);
                ball3.setCenter(halfCircleY(getY(Align.center) + 3 + radius), getY(Align.center) + 3);
                break;
            /*case 3:
                ball.setCenter(f(getY(Align.center) - D / 2), getY(Align.center)); break;
            case 1:
                ball.setCenter(f(getY(Align.center) + D / 2), getY(Align.center)); break;*/
        }
        ball.draw(batch);
        ball1.draw(batch);
        ball2.draw(batch);
        ball3.draw(batch);
    }

    public float halfCircleX(float x) {
        float s = radius * radius - (x - this.x) * (x - this.x);
        return (float) Math.sqrt(s >= 0 ? s : 1000000) + y;
    }

    public float halfCircleY(float y) {
        float s = radius * radius - (y - this.y) * (y - this.y);
        return (float) Math.sqrt(s >= 0 ? s : 1000000) * c + x;
    }

    boolean expl = false;

    @Override
    public void destroyBullet() {
        int x = (int) getX(Align.center);
        int y = (int) getY(Align.center);

        x += x % 2;
        y += y % 2;

        if (Vector2.dst2(x, y, this.x, this.y) >= dst2 && !expl) {
            gameStage.mainTerrain.decorGround.addActor(new BiggestExplosion(x, y, diameter, damage));
            expl = true;
        }

        if ((ball3.getX() >= 50 || ball3.getY() >= 50) && expl) remove();
    }

    @Override
    public void act(float delta) {
        destroyBullet();
        speed(delta);
    }
}
