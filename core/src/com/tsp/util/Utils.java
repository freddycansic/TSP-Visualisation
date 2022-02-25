package com.tsp.util;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.tsp.Node;

public class Utils {
	
	private final static Color DARK_GRAY = new Color(0.3f, 0.3f, 0.3f, 1.0f);
	
	public static void drawLine(ShapeRenderer sr, Node n1, Node n2, Color color, int lineWidth) {
		sr.setColor(color);
		sr.rectLine(n1.getVecPos(), n2.getVecPos(), lineWidth);
	}

	public static void drawNode(ShapeRenderer sr, Node node) {
		sr.setColor(DARK_GRAY);
		sr.circle(node.x, node.y, 4);
	}

	public static boolean openNodesExist(ArrayList<Node> nodes) {
		for (Node node : nodes) {
			if (node.isOpen()) {
				return true;
			}
		}
		return false;
	}
	
	public static int totalRouteDistance(ArrayList<Node> nodes) {
		int distance = 0;
		for (int i = 0; i < nodes.size()-1; i++) {
			distance += nodes.get(i).distTo(nodes.get(i+1));
		}
		return distance + nodes.get(nodes.size()-1).distTo(nodes.get(0));
	}
}
