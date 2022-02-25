package com.tsp.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.utils.Align;
import com.tsp.Main;
import com.tsp.ui.Action;
import com.tsp.ui.Button;
import com.tsp.ui.InputField;
import com.tsp.ui.Picture;
import com.tsp.ui.TextBox;
import com.tsp.util.ResourceManager;

public class MenuScene extends Scene {

	private int numNodes, proximity;
	private Action nextScene;
	
	public MenuScene(final ResourceManager rm) {
		
		this.addUIComponent(new Picture("textures/logo.png", rm.ALL_WIDTH/2 - 256/2, 4*rm.ALL_HEIGHT/7 - 256/2, 256, 256));
		
		this.addUIComponent(new TextBox(rm.defaultSkin, "default", "Number of nodes:", rm.ALL_WIDTH/2 - 175, rm.ALL_HEIGHT/4 + 75, 100, 100));
		this.addUIComponent(new TextBox(rm.defaultSkin, "default", "Proximity of nodes:", rm.ALL_WIDTH/2 + 20, rm.ALL_HEIGHT/4 + 75, 100, 100));
		this.addUIComponent(new TextBox(rm.defaultSkin, "large", "Travelling Salesman Problem\nVisualisation", Align.center, rm.ALL_WIDTH/2, 3*rm.ALL_HEIGHT/4 + 100, 0, 0));
		this.addUIComponent(new TextBox(rm.defaultSkin, "small", "Created by Freddy Cansick 02/2022", Align.center, rm.ALL_WIDTH/2, rm.ALL_HEIGHT/9, 0, 0));
		
		final InputField numNodesInputField = new InputField(rm.defaultSkin, "", rm.ALL_WIDTH/2 - 200, rm.ALL_HEIGHT/4 + 75, 200, 50);
		final InputField proximityInputField = new InputField(rm.defaultSkin, "", rm.ALL_WIDTH/2, rm.ALL_HEIGHT/4 + 75, 200, 50);
		this.addUIComponent(numNodesInputField);
		this.addUIComponent(proximityInputField);
		
		nextScene = new Action() {
			@Override
			public void action() {
				
				try {
					numNodes = Integer.parseInt(numNodesInputField.getCurrentInput());
					proximity = Integer.parseInt(proximityInputField.getCurrentInput());
					
					if (numNodes >= 4 && proximity >= 1)
						Main.setScene(new NearestNeighbourScene(rm, numNodes, proximity));
				} catch (Exception e) {
					return;
				}
			}
		};
		
		this.addUIComponent(new Button(rm.defaultSkin, "Confirm", rm.ALL_WIDTH/2 - 200/2, rm.ALL_HEIGHT/2 - 225, 200, 75, nextScene));
	}

	@Override
	public void render() {		
		if (Gdx.input.isKeyPressed(Keys.ENTER)) nextScene.action();
		
		this.draw();
	}
	
	
}
