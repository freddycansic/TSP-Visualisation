package com.tsp.scene;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.tsp.ui.UIComponent;

public abstract class Scene extends Stage{

	public Scene() {
	}
	
	public abstract void render();
	
	public void addUIComponent(UIComponent component) {
		this.addActor(component.getActor());
	}
}
