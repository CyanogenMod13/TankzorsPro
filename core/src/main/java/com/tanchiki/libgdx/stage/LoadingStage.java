package com.tanchiki.libgdx.stage;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.kotcrab.vis.ui.widget.*;
import com.tanchiki.libgdx.util.*;

public class LoadingStage extends Stage {
	private Texture intro = new Texture("texture/ui/intro_logo.png");
	private Table viewRoot;
	
	public LoadingStage() {
		viewRoot = new Table();
       	viewRoot.setSize(getWidth(), getHeight());
		viewRoot.setPosition(0, 0);
		addActor(viewRoot);
		
		Image imgIntro = new Image(intro);
		viewRoot.add(imgIntro).size(getWidth() * 0.25f, (getWidth() * 0.25f * imgIntro.getHeight() / imgIntro.getWidth())).center().row();
		viewRoot.add(new Label("Загрузка", new Label.LabelStyle(FontLoader.f24, Color.WHITE)));
	}
}
