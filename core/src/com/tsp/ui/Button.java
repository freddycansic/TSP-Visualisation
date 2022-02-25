package com.tsp.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.tsp.scene.Scene;

public class Button extends UIComponent {
		
	private TextButton button;
	
	/**
	 * @param scene	Scene to add button to
	 * @param skin	Skin to render button with
	 * @param text	Text to display in button
	 * @param x	coordinate of button
	 * @param y	coordinate of button
	 * @param width	Width of button
	 * @param height	Height of button
	 * @param action	Method to perform on button click
	 */
	
	public Button(Skin skin, String text, float x, float y, float width, float height, final Action action) {
		super(x, y, width, height);
		button = new TextButton(text, skin, "default");
		button.setPosition(x, y);
		button.setSize(width, height);
		
		button.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				action.action();
			}
		});
	}
	
	@Override
	public Actor getActor() {
		return button;
	}
	
}
