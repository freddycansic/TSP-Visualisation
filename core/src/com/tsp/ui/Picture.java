package com.tsp.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Picture extends UIComponent {

	private Image image;
	
	/**
	 * Single texture file based picture.
	 * @param scene	Scene to add image actor to.
	 * @param texturePath	Internal path of texture.
	 * @param x	X coordinate of picture.
	 * @param y	Y coordinate of picture.
	 * @param width	Width of picture.
	 * @param height	Height of picture.
	 */
	
	public Picture(String texturePath, float x, float y, float width, float height) {
		super(x, y, width, height);
		image = new Image(new Texture(Gdx.files.internal(texturePath)));
		image.setSize(width, height);
		image.setPosition(x, y);
	}
	
	/**
	 * Skin based picture.
	 * @param scene	Scene to add image actor to.
	 * @param skin
	 * @param drawableName
	 * @param x	X coordinate of picture.
	 * @param y	Y coordinate of picture.
	 * @param width	Width of picture.
	 * @param height	Height of picture.
	 */
	
	public Picture(Skin skin, String drawableName, float x, float y, float width, float height) {
		super(x, y, width, height);
		image = new Image(skin, drawableName);
		image.setSize(width, height);
		image.setPosition(x, y);
	}

	@Override
	public Actor getActor() {
		return image;
	}
	
}
