package com.tanchiki.libgdx.model.terrains;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.tanchiki.libgdx.util.ObjectVariables;

public class DecorGrass {
    public static void createBulgeGrass(MainTerrain mt, float x, float y) {
        switch (MathUtils.random(1, 2)) {
            case 1: {
                mt.decor_ground.addActor(new BugleGrass(x, y));
                break;
            }
            case 2: {
                mt.decor_ground.addActor(new BugleGrass2(x, y));
                break;
            }
        }

    }

    public static void createSandGrass(MainTerrain mt, float x, float y) {
        mt.decor_ground.addActor(new SandGrass(x, y));
    }

    private static class SandGrass extends Sand {
        float x;
        float y;
        int block = ObjectVariables.size_block * 2;

        public SandGrass(float x, float y) {
            super(x, y);
            this.x = x;
            this.y = y;
        }

        @Override
        public void draw(Batch batch, float parentAlpha) {
            // TODO: Implement this method
            s.setRegion(t[10]);
            setCenterPosition(x, y);
            super.draw(batch, parentAlpha);
            s.setRegion(t[11]);
            setCenterPosition(x + block, y);
            super.draw(batch, parentAlpha);
            s.setRegion(t[12]);
            setCenterPosition(x, y - block);
            super.draw(batch, parentAlpha);
            s.setRegion(t[13]);
            setCenterPosition(x + block, y - block);
            super.draw(batch, parentAlpha);
        }

    }

    private static class BugleGrass extends Grass {
        float x;
        float y;
        int block = ObjectVariables.size_block * 2;

        public BugleGrass(float x, float y) {
            super(x, y);
            this.x = x;
            this.y = y;
        }

        @Override
        public void draw(Batch batch, float parentAlpha) {
            // TODO: Implement this method
            s.setRegion(t[6]);
            setCenterPosition(x, y);
            super.draw(batch, parentAlpha);
            s.setRegion(t[7]);
            setCenterPosition(x + block, y);
            super.draw(batch, parentAlpha);
            s.setRegion(t[8]);
            setCenterPosition(x, y - block);
            super.draw(batch, parentAlpha);
            s.setRegion(t[9]);
            setCenterPosition(x + block, y - block);
            super.draw(batch, parentAlpha);
        }

    }

    private static class BugleGrass2 extends Grass {
        float x;
        float y;
        int block = ObjectVariables.size_block * 2;

        public BugleGrass2(float x, float y) {
            super(x, y);
            this.x = x;
            this.y = y;
        }

        @Override
        public void draw(Batch batch, float parentAlpha) {
            // TODO: Implement this method
            s.setRegion(t[14]);
            setCenterPosition(x, y);
            super.draw(batch, parentAlpha);
            s.setRegion(t[15]);
            setCenterPosition(x + block, y);
            super.draw(batch, parentAlpha);
            s.setRegion(t[16]);
            setCenterPosition(x, y - block);
            super.draw(batch, parentAlpha);
            s.setRegion(t[17]);
            setCenterPosition(x + block, y - block);
            super.draw(batch, parentAlpha);
        }

    }
}
