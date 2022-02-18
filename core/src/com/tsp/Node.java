package com.tsp;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class Node implements Comparable<Node>{
	
	public int x, y;
	private boolean open;
	private final int nodeRadius = 6;
	
	public Node(int x, int y) {
		this.x = x;
		this.y = y;
		this.setOpen(true);
	}
	
	public void draw(ShapeRenderer sr, Color color) {
		sr.begin(ShapeType.Filled);
		sr.setColor(color);
		sr.circle(x, y, nodeRadius);
		sr.end();
	}
	
	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}
	
	public int distTo(Node node) {
		return (int) (Math.pow((this.x - node.x), 2) + Math.pow((this.y - node.y), 2));
	}
	
	public Vector2 getVecPos() {
		return new Vector2(x, y);
	}
	
	@Override
	public String toString() {
		return "(" + x + ", " + y + ") " + (open ? "Open" : "Closed");
	}

	@Override
    public int compareTo(Node node) {
        return (int)((this.x - node.x) + (this.y - node.y));
    }
}
