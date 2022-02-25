package com.tsp;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.tsp.scene.MenuScene;
import com.tsp.scene.Scene;
import com.tsp.util.ResourceManager;

public class Main extends Game {

	private static Main instance;
	private ResourceManager rm;
	private Scene currentScene;

	@Override
	public void create() {
		instance = this;
		this.rm = new ResourceManager();
		
		Main.setScene(new MenuScene(rm));
	}

	@Override
	public void render() { // turn on anti aliasing
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT | (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0));
		Gdx.gl.glClearColor(1, 1, 1, 1);
		
		currentScene.act();
		currentScene.render();
	}

	@Override
	public void dispose() {
		rm.dispose();
	}

	public static void setScene(Scene scene) {
		instance.currentScene = scene;
		Gdx.input.setInputProcessor(scene);
	}
	
}
