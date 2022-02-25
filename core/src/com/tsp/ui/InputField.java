package com.tsp.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.tsp.scene.Scene;

public class InputField extends UIComponent {
	
	private TextField inputField;
	
	/**
	 * @param scene	Scene to add button to
	 * @param skin	Skin to render button with
	 * @param text	Text to display in input field
	 * @param x	coordinate of input field
	 * @param y	coordinate of input field
	 * @param width	Width of input field
	 * @param height	Height of input field
	 */
	
	public InputField(Skin skin, String placeholder, float x, float y, float width, float height) {
		super(x, y, width, height);
		inputField = new TextField(placeholder, skin);
		inputField.setPosition(x, y);
		inputField.setSize(width, height);
	}

	public String getCurrentInput() {
		return inputField.getText();
	}
	
	@Override
	public Actor getActor() {
		return inputField;
	}

}
