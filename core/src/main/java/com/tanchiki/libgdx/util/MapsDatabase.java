package com.tanchiki.libgdx.util;

import java.io.InputStream;
import java.util.HashMap;

public class MapsDatabase {

    private static MapsDatabase mapsDatabase = null;

    public static MapsDatabase getInstance() {
        if (mapsDatabase == null) mapsDatabase = new MapsDatabase();
        return mapsDatabase;
    }

    HashMap<String, MapBinReader> database = new HashMap<>();
    int size;

    private MapsDatabase() {
    }

    public void putMap(String name, InputStream stream) {
        MapBinReader parser = new MapBinReader(stream);
        putMap(name, parser);
    }

    public void putMap(String name, MapBinReader parser) {
        size++;
        database.put(name, parser);
    }

    public MapBinReader getMap(String name) {
        return database.get(name);
    }

    public int getSize() {
        return size;
    }

    @Deprecated
    public String getMapAbout(String name) {
        return null;
    }

    public void clear() {
        database.clear();
    }
}
