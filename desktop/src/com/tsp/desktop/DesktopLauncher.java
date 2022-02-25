package com.tsp.desktop;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.tsp.Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = 900;
		config.width = 1200;
		config.title = "Travelling Salesman";
		config.initialBackgroundColor = Color.WHITE;
		config.samples = 3; // anti aliasing
		config.forceExit = false;
		config.resizable = false;
		
		config.vSyncEnabled = false;
		config.foregroundFPS = 0;
		config.backgroundFPS = 0;
		
		config.addIcon("icons/icon_128.png", FileType.Internal);
		config.addIcon("icons/icon_32.png", FileType.Internal);
		config.addIcon("icons/icon_16.png", FileType.Internal);
		new LwjglApplication(new Main(), config);
	}
}
