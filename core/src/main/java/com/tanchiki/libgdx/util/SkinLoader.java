package com.tanchiki.libgdx.util;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class SkinLoader {
    @Deprecated
    public final Skin defaultSkin = new Skin();

    //public final Skin visSkin = new Skin(Gdx.files.internal("data/uiskin.json"));

    public SkinLoader() {
        //defaultSkin();
    }

    public static void init() {
        ObjectClass.SkinLoader = new SkinLoader();
    }

    private void visSkin() {

    }
	
	/*@Deprecated
	private void defaultSkin()
	{
		defaultSkin.add("small-font", FontLoader.f16, BitmapFont.class);
		defaultSkin.add("medium-font", FontLoader.f20, BitmapFont.class);
		defaultSkin.add("big-font", FontLoader.f24, BitmapFont.class);
		
		defaultSkin.add("default", new TextButton.TextButtonStyle(null, null, new TextureRegionDrawable(new TextureRegion(new Texture("texture/ui/billet.png"))), visSkin.getFont("small-font")), TextButton.TextButtonStyle.class);
	}*/
}
