package com.tanchiki.libgdx.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import java.util.*;

public class FontLoader {
    public static String c = "QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnmЙЦУКЕНГШЩЗХФЫВАПРОЛДЖЭЯЧСМИТЪЬБЮЁйцукенгшщзхфывапролджэячсмитъьбюё,.+-—/*?!;:'1234567890_#@()^~`";

	public static BitmapFont f30;
	
    public static BitmapFont f24;

    public static BitmapFont f20;

    public static BitmapFont f16;
	
	public static final Map<String, String> replacePart = new HashMap<>();

    public static void init() {
		f16 = createFont(16);
		f20 = createFont(20);
		f24 = createFont(24);
		f30 = createFont(30);
		
		markupMap();
    }

	private static int normalizeFontSize(int size) {
		return Math.round(Gdx.graphics.getWidth() * size / 1280);
	}
	
	private static void markupMap() {
		replacePart.put("^A","" + (char) 549);
		replacePart.put("^B","" + (char) 550);
		replacePart.put("^C","" + (char) 551);
		replacePart.put("~", "\n");
		replacePart.put("#w", "");
		replacePart.put("#r", "");
		replacePart.put("#t", "");
		replacePart.put("[X]", "");
		replacePart.put("#f", "" + (char) 513);
		replacePart.put("#m", "" + (char) 512);
		replacePart.put("#C", "" + (char) 51);
		replacePart.put("#S", "" + (char) 51);
		replacePart.put("*", "" + (char) 51);
		replacePart.put("<", "" + (char) 511);
		replacePart.put("^s", "" + (char) 542);
		replacePart.put("^t", "" + (char) 543);
		replacePart.put("^u", "" + (char) 544);
		replacePart.put("^L", "" + (char) 554);
	}
	
	private static BitmapFont createFont(int size) {
		FileHandle ttf = Gdx.files.internal("font/rb.ttf");

        FreeTypeFontGenerator.FreeTypeFontParameter prm = new FreeTypeFontGenerator.FreeTypeFontParameter();
        prm.characters = c;
        prm.flip = false;
		prm.size = normalizeFontSize(size);
		
        BitmapFont font = new FreeTypeFontGenerator(ttf).generateFont(prm);
		font.getData().markupEnabled = true;
		
		addExtraGlyphs(font);
		
		return font;
	}
	
    private static void addExtraGlyphs(BitmapFont font) {
        FreeTypeFontGenerator.FreeTypeBitmapFontData data = (FreeTypeFontGenerator.FreeTypeBitmapFontData) font.getData();

        BitmapFont.Glyph exampl = data.getGlyph('B');

        Pixmap tmp1 = new Pixmap(Gdx.files.internal("texture/ui/icons.png"));
        Pixmap tmp2 = new Pixmap(43 * exampl.height * 2, exampl.height * 2, tmp1.getFormat());
        tmp2.drawPixmap(tmp1,
                0, 0, tmp1.getWidth(), tmp1.getHeight(),
                0, 0, tmp2.getWidth(), tmp2.getHeight());

        TextureRegion page = new TextureRegion(new Texture(tmp2));//"texture/ui/icons.png"));

        font.getRegions().add(page);

        int size = page.getTexture().getHeight();
        char id = 512;

        for (int i = 0; i < 43; i++) {
            BitmapFont.Glyph g = new BitmapFont.Glyph();
            g.srcX = i * size;
            g.srcY = 0;
            g.width = size;
            g.height = size;
            g.id = id++;
            g.page = 1;

            g.xadvance = exampl.xadvance + 5;
            g.xoffset = exampl.xoffset;
            g.yoffset = exampl.yoffset - 5;

            data.setGlyphRegion(g, page);
            data.setGlyph(g.id, g);
        }
    }
	
	public static String format(String s) {
		String buff = new String(s);
		for (String key : replacePart.keySet())
			buff = buff.replace(key, replacePart.get(key));
		return buff;
	}
}
