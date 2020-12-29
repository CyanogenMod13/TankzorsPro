package com.tanchiki.libgdx;

import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import com.badlogic.gdx.backends.android.*;
import com.tanchiki.libgdx.*;
import com.tanchiki.libgdx.screens.*;
import java.io.*;

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
