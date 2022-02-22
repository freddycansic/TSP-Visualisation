package com.tsp;

import java.util.ArrayList;
import java.util.Random;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.tsp.screens.MenuScreen;

public class Main extends Game {

	public final Random r = new Random();
	
	public int WIDTH, HEIGHT;
	public long startTime;
	
	public ShapeRenderer sr;
	public BitmapFont font;
	public SpriteBatch batch;
	
	@Override
	public void create () {
		
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
		sr = new ShapeRenderer();
		batch = new SpriteBatch();
		font = new BitmapFont(); 

		font.setColor(Color.BLACK);
		font.usesIntegerPositions();

		startTime = System.currentTimeMillis();
		
		this.setScreen(new MenuScreen(this));

	}

	@Override
	public void render () { // turn on anti aliasing
		super.render();
	}

	@Override
	public void dispose () {
		batch.dispose();
		sr.dispose();
		font.dispose();
	}

	public void drawLine(ShapeRenderer sr, Node n1, Node n2, Color color, int lineWidth) {
		sr.setColor(color);
		sr.rectLine(n1.getVecPos(), n2.getVecPos(), lineWidth);
	}
	
	public void drawNode(ShapeRenderer sr, Node node, Color color) {
		sr.setColor(color);
		sr.circle(node.x, node.y, 4);
	}

	public boolean openNodesExist(ArrayList<Node> nodes) {
		for (Node node : nodes) {
			if (node.isOpen()) {
				return true;
			}
		}
		return false;
	}

}
