package com.tanchiki.libgdx.util;

import java.io.InputStream;

public class MapBinReader {
    private int[] data;
    private String mapName = "";
    private final String[] briefing = new String[2];
    private final String[] hints = new String[7];
    private final int[] sizeMapPart = new int[2];
    private final int[] parametersPart = new int[198];
    private int[] mapDataPart;
    private int version;

    public MapBinReader(InputStream stream) {
        try {
            data = new int[stream.available()];
            for (int i = 0; i < data.length; i++)
                data[i] = stream.read();
            next();
            mapName = FontLoader.format(readStringName());
            for (int i = 0; i < sizeMapPart.length; i++) sizeMapPart[i] = next();
            next();
            for (int i = 0; i < parametersPart.length; i++) parametersPart[i] = next();
            mapDataPart = new int[sizeMapPart[0] * sizeMapPart[1]];
            for (int i = 0; i < mapDataPart.length; i++) mapDataPart[i] = next();
            for (int i = 0; i < briefing.length; i++) briefing[i] = FontLoader.format(readString());
            for (int i = 0; i < hints.length; i++) hints[i] = FontLoader.format(readString());
            version = parametersPart[0];
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int pos;

    private int next() {
        return data[pos++];
    }

    private String readStringName() {
        next();
        int len = next() / 2;
        StringBuilder data = new StringBuilder();
        for (int i = 0; i < len; i++) {
            data.append((char) (next() * 256 + next()));
        }
        return data.toString();
    }

    private String readString() {
        int len = (next() * 256 + next()) / 2;
        StringBuilder data = new StringBuilder();
        for (int i = 0; i < len; i++) {
            data.append((char) (next() * 256 + next()));
        }
        return data.toString();
    }

    public int[] getSizeMapPart() {
        return sizeMapPart;
    }

    public String getName() {
        return mapName;
    }

    public String[] getBriefing() {
        return briefing;
    }

    public String[] getHints() {
        return hints;
    }

    public int[] getParametersPart() {
        return parametersPart;
    }

    public int[] getMapDataPart() {
        return mapDataPart;
    }

    public int[] getData() {
        return data.clone();
    }

    public int getVersion() {
        return version;
    }
}
