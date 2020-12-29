package com.tanchiki.libgdx.model.aircraft;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.tanchiki.libgdx.graphics.GameActor;
import com.tanchiki.libgdx.model.explosions.BiggestExplosion;
import com.tanchiki.libgdx.stage.GameStage;
import com.tanchiki.libgdx.util.ObjectClass;
import com.tanchiki.libgdx.util.ObjectVarable;
import com.tanchiki.libgdx.util.WeaponData;

public class airplane extends GameActor {
    Sprite plane;
    Sprite back;
    Sprite point;
    GameStage GameStage;
    float targetX, targetY;
    float deltaX, deltaY;
    int count;
    int radius;
	int damage = 10;

    public airplane(float targetX, float targetY, int count, int damage) {
		this.damage = damage;
        this.radius = (damage <= 16) ? 6 * 2 : 8 * 2;
        this.count = count;
        float b = targetY - (float) Math.tan(Math.toRadians(45)) * targetX;
        float x = -b;

        float h = targetY - 0;
        float w = targetX - x;

        float angle = MathUtils.atan2(h, w);

        deltaX = MathUtils.cos(angle) / 5;
        deltaY = MathUtils.sin(angle) / 5;

        setSize(ObjectVarable.size_block * 2 * 6, ObjectVarable.size_block * 2 * 6);
        setCenterPosition(x, 0);

        this.targetX = targetX;
        this.targetY = targetY;
        GameStage = ObjectClass.GameStage;

        plane = new Sprite(GameStage.TextureLoader.getAirplane()[0][0]);
        back = new Sprite(GameStage.TextureLoader.getAirplane()[0][1]);
        point = new Sprite(GameStage.TextureLoader.getBullets()[0][17]);
    }

    @Override
    public void act(float delta) {
        moveBy(deltaX, deltaY);
        if (count > 0)
            if (Vector2.dst2(getCenterX(), getCenterY(), targetX, targetY) < 3) {
                GameStage.addActor(new BiggestExplosion(targetX, targetY, radius, damage));
                targetX += ObjectVarable.size_block * 2 * 3;
                targetY += ObjectVarable.size_block * 2 * 3;
                count--;
            }
        setVisible(true);
        // TODO: Implement this method
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // TODO: Implement this method
        super.draw(batch, parentAlpha);
        plane.setSize(getWidth(), getHeight());
        plane.setPosition(getX(), getY());
        plane.draw(batch);

        back.setSize(getWidth(), getHeight());
        back.setPosition(getX() + ObjectVarable.size_block * 2 * 3, getY() - ObjectVarable.size_block * 2 * 3);
        back.draw(batch);

        point.setSize(ObjectVarable.size_block * 2, ObjectVarable.size_block * 2);
        point.setCenter(targetX, targetY);
        if (count > 0)
            point.draw(batch);
    }

}
