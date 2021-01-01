package com.app;

import com.badlogic.gdx.backends.lwjgl.LwjglAWTCanvas;
import com.tanchiki.libgdx.model.terrains.Grass;
import com.tanchiki.libgdx.model.terrains.MainTerrain;
import com.tanchiki.libgdx.model.terrains.Sand;
import com.tanchiki.libgdx.screens.GameScreen;
import com.tanchiki.libgdx.stage.GameStage;
import com.tanchiki.libgdx.util.ObjectVarable;
import com.tanchiki.libgdx.util.Settings;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

@Deprecated
public class Editor extends JFrame {

    public boolean isEdit = false;

    Toolkit kit = Toolkit.getDefaultToolkit();

    TilesTools tilesTools;

    public Editor() {
        setTitle("Map Editor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Settings.edit_map_mode = true;
        Settings.show_main_menu = false;
        Settings.pause = true;
        Settings.fixed_move_camera = false;

        initJMenuBar();

        LwjglAWTCanvas canvas = new LwjglAWTCanvas(new GameScreen());
        getContentPane().add(canvas.getCanvas());

        pack();
        setVisible(true);
        setSize(800, 600);
        setLocation(kit.getScreenSize().width / 2 - getWidth() / 2, kit.getScreenSize().height / 2 - getHeight() / 2);

        tilesTools = new TilesTools(this);
    }

    private File tmpFile;
    private String mapName;

    private void initJMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu file = new JMenu("File");
        file.add("New Map").addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                final JDialog dialog = new JDialog(Editor.this, "New Map", true);
                Container container = dialog.getContentPane();
                container.setLayout(new GridLayout(4, 1));

                JPanel namePanel = new JPanel();
                namePanel.add(new JLabel("Name: "));
                final JEditorPane name = new JEditorPane();
                namePanel.add(name);
                container.add(namePanel);

                JPanel sizePanel = new JPanel();
                sizePanel.add(new JLabel("Size: "));
                final JTextField x = new JTextField(2);
                final JTextField y = new JTextField(2);
                sizePanel.add(x);
                sizePanel.add(new JLabel(" X "));
                sizePanel.add(y);
                container.add(sizePanel);

                JPanel typePanel = new JPanel();
                typePanel.add(new JLabel("Type: "));
                final JComboBox<String> list = new JComboBox<>(new String[] {Grass.class.getName(), Sand.class.getName()});
                list.setEditable(false);
                typePanel.add(list);
                container.add(typePanel);

                JPanel buttonsPanel = new JPanel();
                buttonsPanel.add(new JButton("OK")).addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        try {
                            File file = new File(name.getText() + ".xml");
                            file.createNewFile();
                            tmpFile = file;

                            PrintWriter p = new PrintWriter(file);
                            p.println("<?xml version='1.0' encoding='utf-8' standalone='yes' ?>");
                            p.println("<map>");
                            //p.println("<about "+String.format("name=\"%s\"></about>",a.getText().toString()));
                            p.println("<string name=\"size_world\" " + String.format("source=\"%s:%s\"></string>", x.getText(), y.getText()));
                            p.print("<string ");
                            p.print("name=\"" + list.getSelectedItem().toString() + "\" parent=\"ground\" ");
                            StringBuilder source = new StringBuilder();
                            for (int i = 0; i < Integer.parseInt(x.getText()); i++)
                                for (int j = 0; j < Integer.parseInt(y.getText()); j++)
                                    source.append(i + ":" + j + ":");
                            p.print("source=\"" + source.deleteCharAt(source.length() - 1).toString() + "\"></string>");
                            p.println();
                            p.println("</map>");
                            p.flush();
                            p.close();

                            loadMap(file);
                            dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                            dialog.setVisible(false);
                        } catch (Exception ex) {ex.printStackTrace();}
                    }
                });
                container.add(buttonsPanel);

                //dialog.setPreferredSize(new Dimension(300, 400));
                dialog.pack();
                dialog.setVisible(true);
            }
        });
        file.add("Open Map").addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                try {
                    JFileChooser chooser = new JFileChooser();
                    chooser.setCurrentDirectory(tmpFile);
                    chooser.setFileFilter(new FileNameExtensionFilter("Map file (*.xml, *.map)", "xml", "map"));
                    if (chooser.showOpenDialog(Editor.this) == JFileChooser.APPROVE_OPTION) {
                        tmpFile = chooser.getSelectedFile();
                        loadMap(tmpFile);
                    }
                } catch (Exception ex) {}
            }
        });
        file.addSeparator();
        file.add("Save").addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                try {
                    saveMap(tmpFile.getAbsoluteFile());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        file.add("Save at").addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                try {
                    JFileChooser chooser = new JFileChooser();
                    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    chooser.setDialogTitle("Save at");
                    chooser.setCurrentDirectory(new File(tmpFile.getPath()));

                    if (chooser.showSaveDialog(Editor.this) == JFileChooser.APPROVE_OPTION) {
                        tmpFile = new File(chooser.getSelectedFile().getPath() + "\\" + tmpFile.getName());
                        tmpFile.createNewFile();
                        saveMap(tmpFile);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        file.addSeparator();
        file.add("Close Map").addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                disposeMap();
            }
        });
        file.addSeparator();
        file.add("Exit").addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                System.exit(0);
            }
        });
        menuBar.add(file);

        JMenu options = new JMenu("Options");
        menuBar.add(options).addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                String missions[] = {"Destroy all", "Destroy radars of enemy", "Waiting 5 min", "Waiting 10 min", "Protecting radars of friends"};

                JDialog dialog = new JDialog(Editor.this, "Options", true);
                Container container = dialog.getContentPane();
                container.setLayout(new GridLayout(4 + missions.length, 1));

                container.add(new JLabel("Max Tanks"));
                final JEditorPane maxTanks = new JEditorPane();
                maxTanks.setText("" + ObjectVarable.max_tanks);
                container.add(maxTanks);

                container.add(new JLabel("Missions Check"));

                Map<Integer, Boolean> map = new HashMap<>();

                for (int i = 0;i < missions.length;i++)
                    map.put(i, false);

                for (int code : MainTerrain.missions)
                    map.put(code, true);

                for (int i = 0;i < missions.length;i++) {
                    JCheckBox jCheckBox = new JCheckBox(missions[i], map.get(i));
                    final int num = i;
                    container.add(jCheckBox).addMouseListener(new MouseAdapter() {
                        boolean checked = map.get(num);
                        @Override
                        public void mousePressed(MouseEvent e) {
                            checked = !checked;
                            map.put(num, checked);
                        }
                    });
                }

                container.add(new JButton("Apply")).addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        ObjectVarable.max_tanks = Integer.parseInt(maxTanks.getText());
                        MainTerrain.missions.clear();
                        for (int code : map.keySet())
                            if (map.get(code))
                                MainTerrain.missions.add(code);
                         dialog.setVisible(false);
                    }
                });

                dialog.pack();
                dialog.setVisible(true);
            }
        });

        JMenu tanks = new JMenu("Tank");
        tanks.add(new JMenuItem("Allies")).addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                showTankDialog(Settings.tanktypeunityData);
            }
        });
        tanks.add(new JMenuItem("Enemies")).addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                showTankDialog(Settings.tanktypeenemyData);
            }
        });
        menuBar.add(tanks);

        setJMenuBar(menuBar);
    }

    private void showTankDialog(ArrayList<String> arrayList) {
        JDialog dialog = new JDialog(Editor.this, "Allies", true);
        Container container = dialog.getContentPane();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        JTabbedPane tabs = new JTabbedPane();

        String[] column = new String[] {"Count", "Type", "Weapon", "Mode", "HP", "HP of shield"};
        String[][] data = new String[arrayList.size()][];
        for (int i = 0; i < data.length; i++) {
            data[i] = arrayList.get(i).split(":");
        }
        DefaultTableModel model = new DefaultTableModel(data, column);
        JTable table = new JTable(model);
        table.setRowHeight(20);
        container.add(new JScrollPane(table));

        JPanel panelButtons = new JPanel();
        panelButtons.add(new JButton("Add")).addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                model.addRow(new String[] {"0", "TankLight", "1", "0", "5", "0"});
            }
        });
        panelButtons.add(new JButton("Remove")).addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                model.removeRow(table.getSelectedRow());
            }
        });
        panelButtons.add(new JButton("Apply")).addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                arrayList.clear();
                for (Vector<String> str : model.getDataVector()) {
                    String data = "";
                    for (String s : str) data += ":" + s;
                    data = data.substring(1);
                    arrayList.add(data);
                }
                dialog.setVisible(false);
            }
        });
        container.add(panelButtons);

        dialog.pack();
        dialog.setVisible(true);
    }

    private void disposeMap() {
        setTitle("Map Editor");
        isEdit = false;
        GameStage.getInstance().disposeTerrain();
    }

    private void saveMap(File file) throws Exception {
        //MainTerrain.getCurrentTerrain().saveMap(file.getAbsoluteFile());
        setTitle("Map Editor - [" + file.getAbsolutePath() + ']');
    }

    private void loadMap(File file) throws Exception {
        isEdit = true;
        //GameStage.getInstance().createTerrain(new MapXMLParser(new FileInputStream(file)));
        setTitle("Map Editor - [" + file.getAbsolutePath() + ']');
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Editor();
            }
        });
    }

}
