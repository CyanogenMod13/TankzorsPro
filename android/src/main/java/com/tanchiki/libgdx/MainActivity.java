package com.tanchiki.libgdx;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.tanchiki.libgdx.screens.GameScreen;

public class MainActivity extends AndroidApplication {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		View game = initializeForView(new GameScreen());
        final RelativeLayout main = new RelativeLayout(this);
		setContentView(main, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
		main.addView(game);
    }
}
