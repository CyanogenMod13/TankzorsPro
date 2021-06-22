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
            parsing();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public MapBinReader(byte[] byteData) {
        data = new int[byteData.length];
        for (int i = 0; i < data.length; i++)
            data[i] = byteData[i] - Byte.MIN_VALUE;
        parsing();
    }

    public MapBinReader(int[] byteData) {
        data = byteData;
        parsing();
    }

    private void parsing() {
        skip();
        mapName = FontLoader.format(nextStringName());
        for (int i = 0; i < sizeMapPart.length; i++) sizeMapPart[i] = next();
        skip();
        for (int i = 0; i < parametersPart.length; i++) parametersPart[i] = next();
        mapDataPart = new int[sizeMapPart[0] * sizeMapPart[1]];
        for (int i = 0; i < mapDataPart.length; i++) mapDataPart[i] = next();
        for (int i = 0; i < briefing.length; i++) briefing[i] = FontLoader.format(nextString());
        for (int i = 0; i < hints.length; i++) hints[i] = FontLoader.format(nextString());
        version = parametersPart[0];
    }

    private int pos;

    private int next() {
        return data[pos++];
    }

    private void skip() {
        skip(1);
    }

    private void skip(int count) {
        pos += count;
    }

    private String nextStringName() {
        skip();
        int len = next() / 2;
        return nextString(len);
    }

    private String nextString() {
        int len = (next() * 256 + next()) / 2;
        return nextString(len);
    }

    private String nextString(int len) {
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

    public byte[] getByteData() {
        byte[] byteData = new byte[data.length];
        for (int i = 0; i < byteData.length; i++)
            byteData[i] = (byte) (data[i] + Byte.MIN_VALUE);
        return byteData;
    }

    public int getVersion() {
        return version;
    }
}
