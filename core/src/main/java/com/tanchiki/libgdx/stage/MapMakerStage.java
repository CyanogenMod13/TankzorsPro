package com.tanchiki.libgdx.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.tanchiki.libgdx.model.terrains.MainTerrain;

import java.io.File;
import java.io.IOException;

public class MapMakerStage extends Stage {
    MainTerrain MT;
    FileHandle map;

    public MapMakerStage() {

    }

    public void createMap(String name, int w, int h, int type) throws IOException {
        File f = new File("/data/data/com.tanchiki.libgdx/shared_prefs/" + name + ".xml");
        f.createNewFile();
        map = Gdx.files.absolute(f.getPath());

    }

}
