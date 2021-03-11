package com.tanchiki.libgdx.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class MapBinReader {
    private ByteArrayInputStream stream;
	private String mapName = "";
	private final String[] briefing = new String[2];
	private final String[] hints = new String[7];
	private final int[] sizeMapPart = new int[2];
	private final int[] parametersPart = new int[198];
	private int[] mapDataPart;
	private int version;

    public MapBinReader(InputStream stream) {
        try {
			stream.read();
			mapName = FontLoader.format(readStringName(stream));
			for (int i = 0; i < sizeMapPart.length; i++) sizeMapPart[i] = stream.read();
			stream.read();
			for (int i = 0; i < parametersPart.length; i++) parametersPart[i] = stream.read();
			mapDataPart = new int[sizeMapPart[0] * sizeMapPart[1]];
			for (int i = 0; i < mapDataPart.length; i++) mapDataPart[i] = stream.read();
			for (int i = 0; i < briefing.length; i++) briefing[i] = FontLoader.format(readString(stream));
			for (int i = 0; i < hints.length; i++) hints[i] = FontLoader.format(readString(stream));
			version = parametersPart[0];
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	private String readStringName(InputStream in) throws Exception {
		in.read();
		int len = (in.read()) / 2;
		String data = "";
		for (int i = 0; i < len; i++) {
			data += (char) (in.read() * 256 + in.read());
		}
		return data;
	}
	
	private String readString(InputStream in) throws Exception {
		int len = (in.read() * 256 + in.read()) / 2;
		String data = "";
		for (int i = 0; i < len; i++) {
			data += (char) (in.read() * 256 + in.read());
		}
		return data;
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
	
	public int getVersion() {
		return version;
	}
}
