package com.tsp.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.tsp.Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = 900;
		config.width = 900;
		config.title = "Travelling Salesman";
		config.initialBackgroundColor = Color.WHITE;
		config.samples = 3; // anti aliasing
		config.forceExit = false;
		new LwjglApplication(new Main(), config);
	}
}
