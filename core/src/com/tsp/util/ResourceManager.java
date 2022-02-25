package com.tsp.util;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class ResourceManager {

	public Skin defaultSkin;
	
	public final Random r = new Random();

	public int WIDTH, HEIGHT, LOG_WIDTH, LOG_HEIGHT, ALL_WIDTH, ALL_HEIGHT;
	
	public ShapeRenderer sr;
	public SpriteBatch batch;

	public ResourceManager() {

		LOG_WIDTH = 300;
		WIDTH = Gdx.graphics.getWidth() - LOG_WIDTH;

		HEIGHT = Gdx.graphics.getHeight();
		LOG_HEIGHT = HEIGHT;
		
		ALL_WIDTH = LOG_WIDTH + WIDTH;
		ALL_HEIGHT = HEIGHT;

		sr = new ShapeRenderer();
		batch = new SpriteBatch();

		defaultSkin = new Skin(Gdx.files.internal("ui/holo/uiskin.json"));
	}
	
	public void dispose() {
		sr.dispose();
		batch.dispose();
		defaultSkin.dispose();		
	}
}
