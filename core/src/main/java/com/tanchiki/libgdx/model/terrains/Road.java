package com.tanchiki.libgdx.model.terrains;

import com.badlogic.gdx.math.MathUtils;
import com.tanchiki.libgdx.model.terrains.Object.Terrains;
import com.tanchiki.libgdx.util.ObjectVarable;

public class Road extends Terrains {
    Object center = last_block;

    public Road(float x, float y) {
        super(x, y);
    }

    public void init() {
        int xx = (int) getCenterX();
        int yy = (int) getCenterY();

        Object right = null;
        if (g.world_obj.length > xx + ObjectVarable.size_block * 2)
            right = g.world_obj[xx + ObjectVarable.size_block * 2][yy];

        Object left = null;
        if (xx - ObjectVarable.size_block * 2 >= 0)
            left = g.world_obj[xx - ObjectVarable.size_block * 2][yy];

        Object top = null;
        if (g.world_obj[0].length > yy + ObjectVarable.size_block * 2)
            top = g.world_obj[xx][yy + ObjectVarable.size_block * 2];

        Object bottom = null;
        if (yy - ObjectVarable.size_block * 2 >= 0)
            bottom = g.world_obj[xx][yy - ObjectVarable.size_block * 2];

        if (center instanceof Sand) {
            if (right instanceof Road && left instanceof Road) {
                s.setRegion(t[91]);
                //s.setRegion(t[MathUtils.random(91,92)]);
            } else if (top instanceof Road && bottom instanceof Road) {
                s.setRegion(t[94]);
                //s.setRegion(t[MathUtils.random(94,95)]);
            } else if (left instanceof Road) {
                s.setRegion(t[93]);
            } else if (right instanceof Road) {
                s.setRegion(t[90]);
            } else if (bottom instanceof Road) {
                s.setRegion(t[96]);
            } else if (top instanceof Road) {
                s.setRegion(t[97]);
            }
        } else if (center instanceof Grass) {
            if (right instanceof Road && left instanceof Road) {
                s.setRegion(t[24]);
                //s.setRegion(t[MathUtils.random(24,25)]);
            } else if (top instanceof Road && bottom instanceof Road) {
                s.setRegion(t[27]);
                //s.setRegion(t[MathUtils.random(27,28)]);
            } else if (top instanceof Road) {
                s.setRegion(t[30]);
            } else if (bottom instanceof Road) {
                s.setRegion(t[29]);
            } else if (left instanceof Road) {
                s.setRegion(t[26]);
            } else if (right instanceof Road) {
                s.setRegion(t[23]);
            }
        }
        isDraw = false;
    }

    private boolean isDraw = true;

    /*@Override
    public void draw(Batch batch, float parentAlpha) {
        if (isDraw)
            init();
        // TODO: Implement this method
        super.draw(batch, parentAlpha);
    }*/

    public static void createRoadGrass(MainTerrain mt, float x, float y, float w, float h) {
        int block = ObjectVarable.size_block * 2;
        //mt.ground.addActor(new Begin(x,y,2));
        if (h == 0)
            for (int i = 0; i < w; i++) {
                if (i == 0) {
                    mt.road.addActor(new Begin(x, y, 1));
                    continue;
                }
                mt.road.addActor(new R(x + block * i, y, 1));
                if (i == (x - 1))
                    mt.road.addActor(new End(x + block * i, y, 1));
            }
        else if (w == 0)
            for (int i = 0; i < h; i++) {
                if (i == 0) {
                    mt.road.addActor(new Begin(x, y, 2));
                    continue;
                }

                mt.road.addActor(new R(x, y + block * i, 2));

                if (i == (h - 1))
                    mt.road.addActor(new End(x, y + block * i, 2));
            }
    }

    public static void createRoadSand(MainTerrain mt, float x, float y, float w, float h) {
        int block = ObjectVarable.size_block * 2;
        //mt.ground.addActor(new Begin(x,y,2));
        if (h == 0)
            for (int i = 0; i < w; i++) {
                if (i == 0) {
                    mt.road.addActor(new Begin(x, y, 1, 1));
                    continue;
                }
                mt.road.addActor(new R(x + block * i, y, 1, 1));
                if (i == (x - 1))
                    mt.road.addActor(new End(x + block * i, y, 1, 1));
            }
        else if (w == 0)
            for (int i = 0; i < h; i++) {
                if (i == 0) {
                    mt.road.addActor(new Begin(x, y, 2, 1));
                    continue;
                }

                mt.road.addActor(new R(x, y + block * i, 2, 1));

                if (i == (h - 1))
                    mt.road.addActor(new End(x, y + block * i, 2, 1));
            }
    }

    private static class Begin extends Road {
        public Begin(float x, float y, int o) {
            super(x, y);
            if (o == 1)
                s.setRegion(t[23]);
            else
                s.setRegion(t[30]);
        }


        public Begin(float x, float y, int o, int i) {
            super(x, y);
            if (o == 1)
                s.setRegion(t[90]);
            else
                s.setRegion(t[97]);
        }
    }

    private static class R extends Road {
        public R(float x, float y, int o) {
            super(x, y);
            if (o == 1)
                s.setRegion(t[MathUtils.random(24, 25)]);
            else
                s.setRegion(t[MathUtils.random(27, 28)]);
            //toFront();
        }


        public R(float x, float y, int o, int i) {
            super(x, y);
            if (o == 1)
                s.setRegion(t[MathUtils.random(91, 92)]);
            else
                s.setRegion(t[MathUtils.random(94, 95)]);
            //toFront();
        }
    }

    private static class End extends Road {
        public End(float x, float y, int o) {
            super(x, y);
            if (o == 1)
                s.setRegion(t[26]);
            else
                s.setRegion(t[29]);
            //toFront();
        }


        public End(float x, float y, int o, int i) {
            super(x, y);
            if (o == 1)
                s.setRegion(t[93]);
            else
                s.setRegion(t[96]);
            //toFront();
        }
    }
}
