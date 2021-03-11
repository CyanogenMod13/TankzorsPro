package com.tanchiki.libgdx.graphics;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class GameActor extends Actor {
	
    public float getCenterX() {
        return getX() + getWidth() / 2;
    }

    public float getCenterY() {
        return getY() + getHeight() / 2;
    }

    public void setCenterX(float x) {
        setX(x - getWidth() / 2);
    }

    public void setCenterY(float y) {
        setY(y - getHeight() / 2);
    }

    public void setCenterPosition(float x, float y) {
        setCenterX(x);
        setCenterY(y);
    }
}
