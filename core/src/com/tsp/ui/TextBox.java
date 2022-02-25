package com.tsp.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.tsp.scene.Scene;

public class TextBox extends UIComponent {

	private Label label;
	
	public TextBox(Skin skin, String font, CharSequence text, float x, float y, float width, float height) {
		super(x, y, width, height);
		label = new Label(text, skin, font);
		label.setSize(width, height);
		label.setPosition(x, y);
	}
	
	public TextBox(Skin skin, String font, CharSequence text, int alignment, float x, float y, float width, float height) {
		super(x, y, width, height);
		label = new Label(text, skin, font);
		label.setSize(width, height);
		label.setPosition(x, y);
		label.setAlignment(alignment);
	}
	
	public void setText(CharSequence text) {
		label.setText(text);
	}
	
	public CharSequence getText() {
		return label.getText();
	}
	
	@Override
	public Actor getActor() {
		return label;
	}
}
