package com.app;

import com.tanchiki.libgdx.model.buildes.*;
import com.tanchiki.libgdx.model.mine.MineEnemy1;
import com.tanchiki.libgdx.model.mine.MineEnemy2;
import com.tanchiki.libgdx.model.mine.MineUnity1;
import com.tanchiki.libgdx.model.mine.MineUnity2;
import com.tanchiki.libgdx.model.terrains.*;
import com.tanchiki.libgdx.stage.GameStage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

@Deprecated
public class TilesTools extends JDialog {
    TilesTools(JFrame jFrame) {
        super(jFrame, "Tiles", false);
        getContentPane().setLayout(new GridLayout(1, 6));
        initImages();
        init();
        pack();
        setVisible(true);
        setResizable(false);
        //setSize(300, 300);
    }

    BufferedImage[] terrains;
    BufferedImage[] walls;
    BufferedImage[] spikes;
    BufferedImage[] mines;
    BufferedImage[] palms;
    BufferedImage[] builds;
    BufferedImage[] buildings;
    BufferedImage[] turret;

    private void initImages() {
        try {
            BufferedImage img = ImageIO.read(getClass().getClassLoader().getResource("texture/terrain/ground.png"));
            final int size = 20;
            terrains = new BufferedImage[img.getWidth() / size];
            for (int x = 0, i = 0; x < img.getWidth(); x += size, i++) {
                terrains[i] = img.getSubimage(x, 0, size, size);
            }

            img = ImageIO.read(getClass().getClassLoader().getResource("texture/terrain/walls.png"));
            walls = new BufferedImage[img.getWidth() / size];
            for (int x = 0, i = 0; x < img.getWidth(); x += size, i++) {
                walls[i] = img.getSubimage(x, 0, size, size);
            }

            img = ImageIO.read(getClass().getClassLoader().getResource("texture/terrain/spikes.png"));
            spikes = new BufferedImage[img.getWidth() / size];
            for (int x = 0, i = 0; x < img.getWidth(); x += size, i++) {
                spikes[i] = img.getSubimage(x, 0, size, size);
            }

            img = ImageIO.read(getClass().getClassLoader().getResource("texture/terrain/mines.png"));
            mines = new BufferedImage[img.getWidth() / size];
            for (int x = 0, i = 0; x < img.getWidth(); x += size, i++) {
                mines[i] = img.getSubimage(x, 0, size, size);
            }

            img = ImageIO.read(getClass().getClassLoader().getResource("texture/terrain/palms.png"));
            palms = new BufferedImage[img.getWidth() / 88];
            for (int x = 0, i = 0; x < img.getWidth(); x += 88, i++) {
                palms[i] = img.getSubimage(x, 0, 88, 88);
            }

            img = ImageIO.read(getClass().getClassLoader().getResource("texture/builds/big_builds.png"));
            builds = new BufferedImage[img.getWidth() / 40];
            for (int x = 0, i = 0; x < img.getWidth(); x += 40, i++) {
                builds[i] = img.getSubimage(x, 0, 40, 40);
            }

            img = ImageIO.read(getClass().getClassLoader().getResource("texture/builds/buildings.png"));
            buildings = new BufferedImage[img.getWidth() / size];
            for (int x = 0, i = 0; x < img.getWidth(); x += size, i++) {
                buildings[i] = img.getSubimage(x, 0, size, size);
            }

            img = ImageIO.read(getClass().getClassLoader().getResource("texture/tanks/turret.png"));
            turret = new BufferedImage[img.getWidth() / 26];
            for (int x = 0, i = 0; x < img.getWidth(); x += 26, i++) {
                turret[i] = img.getSubimage(x, 0, 26, 26);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() {
        addButton(terrains[0], Grass.class, "ground");
        addButton(terrains[56], Sand.class, "ground");
        addButton(terrains[24], Road.class, "road");
        addButton(terrains[33], Plate.class, "ground");
        addButton(walls[0], StoneWall.class, "walls");
        addButton(walls[15], SmallTree.class, "walls");

        addButton(walls[12], OrangeIronWall.class, "walls");
        addButton(walls[11], BlueIronWall.class, "walls");
        addButton(walls[8], Concrete1Wall.class, "walls");
        addButton(walls[9], Concrete2Wall.class, "walls");
        addButton(walls[10], Concrete3Wall.class, "walls");
        addButton(walls[13], Cactus.class, "walls");

        addButton(spikes[0], Spike.class, "walls");
        addButton(mines[1], MineUnity1.class, "mines");
        addButton(mines[2], MineUnity2.class, "mines");
        addButton(mines[0], MineEnemy1.class, "mines");
        addButton(mines[0], MineEnemy2.class, "mines");
        addButton(palms[0], Palm.class, "decor");

        addButton(builds[0], Radar.RadarUnity.class, "builds");
        addButton(builds[1], ReactorCore.ReactorCoreUnity.class, "builds");
        addButton(builds[0], Radar.RadarEnemy.class, "builds");
        addButton(builds[1], ReactorCore.ReactorCoreEnemy.class, "builds");
        addButton(buildings[0], AngarUnity.class, "builds");
        addButton(buildings[1], AngarEnemy.class, "builds");

        addButton(buildings[2], BlueFlag.class, "builds");
        addButton(buildings[3], RedFlag.class, "builds");
        addButton(buildings[4], YellowFlag.class, "builds");
    }

    int w = 0;

    private void addButton(BufferedImage img, Class<?> obj, String parent) {
        if (++w == 6) {
            GridLayout layout = (GridLayout) getContentPane().getLayout();
            layout.setRows(1 + layout.getRows());
            w = 0;
        }
        CustomButton customButton = new CustomButton(new ImageIcon(img), obj, parent);
        customButton.setPreferredSize(new Dimension(30, 30));
        getContentPane().add(customButton);
    }

    private class CustomButton extends JButton {
        public CustomButton(ImageIcon icon, Class<?> obj, String parent) {
            super(icon);
            //setBorderPainted(false);
            setContentAreaFilled(false);
            //setFocusPainted(false);
            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    GameStage gameStage = GameStage.getInstance();
                    gameStage.currentObj = obj;
                    gameStage.parentObj = parent;
                }
            });
        }
    }
}
