package com.tanchiki.libgdx.model.buildes;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.tanchiki.libgdx.model.tanks.TankUser;
import com.tanchiki.libgdx.model.terrains.MainTerrain;
import com.tanchiki.libgdx.model.ui.MissionCompleted;
import com.tanchiki.libgdx.util.ObjectClass;
import com.tanchiki.libgdx.util.ObjectVariables;

public abstract class Flag extends ObjBuild {
    public Sprite s;
    TextureRegion[] r;
    float size = ObjectVariables.size_block * 2;
    public boolean isgrab = false;
    public boolean yellow = false;
    public boolean blue = false;

	public boolean active = true;
	
    public Flag(float x, float y, int color) {
        r = ObjectClass.TextureLoader.getBuildings()[0];
        s = new Sprite(r[color + 2]);
        switch (color) {
            case 0: {
                blue = true;
                break;
            }
            case 1: {
                isgrab = true;
                break;
            }
            case 2: {
                yellow = true;
                break;
            }
        }
        setSize(size, size);
        setCenterPosition(x, y);
        s.setSize(getWidth(), getHeight());
        s.setCenter(getCenterX(), getCenterY());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // TODO: Implement this method
        super.draw(batch, parentAlpha);
        s.setSize(getWidth(), getHeight());
        s.setCenter(getCenterX(), getCenterY());
        s.draw(batch);
        //setBounds(s.getX(), s.getY(), s.getWidth(), s.getHeight());
    }

    boolean isGrabed = false;

    @Override
    public void setCenterPosition(float x, float y) {
        // TODO: Implement this method
        //ObjectClass.GameStage.world_buildes[(int) getCenterX()][(int) getCenterY()] = null;
        //ObjectClass.GameStage.world_buildes[(int) x][(int) y] = this;
        super.setCenterPosition(x, y);
    }
	
	abstract void clicked();
	
	@Override
	public void act(float delta) {
		if (ObjectClass.GameStage.world_tank[(int) getCenterX()][(int) getCenterY()] instanceof TankUser && active) clicked();
		super.act(delta);
	}
	
	protected void win() {
		MissionCompleted.show(MissionCompleted.MISSION_COMPLETED);
		active = false;
		MainTerrain.getCurrentTerrain().removeTimer();
	}
}
