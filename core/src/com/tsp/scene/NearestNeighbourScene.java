package com.tsp.scene;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Align;
import com.tsp.Main;
import com.tsp.Node;
import com.tsp.ui.TextBox;
import com.tsp.util.ResourceManager;
import com.tsp.util.Utils;

public class NearestNeighbourScene extends Scene {

	private final ResourceManager rm;
	
	private ArrayList<Node> allNodes = new ArrayList<Node>();
	private ArrayList<Node> route = new ArrayList<Node>();
	private float timeSeconds = 0;
	private TextBox logBox, fps;
	
	public NearestNeighbourScene(final ResourceManager rm, int numNodes, int proximity) {
		this.rm = rm;

		logBox = new TextBox(rm.defaultSkin, "small", "Nearest neighbour path\ngenerated with " + numNodes + " nodes\nand a selection proximity of " + proximity + ".", Align.topLeft, rm.WIDTH + 10, 0, rm.LOG_WIDTH, rm.LOG_HEIGHT);
		fps = new TextBox(rm.defaultSkin, "small", Integer.toString(Gdx.graphics.getFramesPerSecond()), Align.topLeft, 0, rm.WIDTH+30, rm.LOG_WIDTH, 30);
		this.addUIComponent(logBox);
		this.addUIComponent(fps);
		
		for (int i = 0; i < numNodes; i++) {
			Node node = new Node(10 + rm.r.nextInt(rm.WIDTH - 10), 10 + rm.r.nextInt(rm.HEIGHT - 10));
			allNodes.add(node);
		}
		
		allNodes.get(0).setOpen(false);
		Node currentNode = allNodes.get(0);
		
		route.add(currentNode);
		
		while (Utils.openNodesExist(allNodes)) {
			
			Map<Integer, Node> distancesNodes = new TreeMap<Integer, Node>(); // (distance to node, node) map
			for (Node n1 : allNodes) {
				if (n1 == currentNode || !n1.isOpen()) continue;
				distancesNodes.put(currentNode.distTo(n1), n1);
			}
			
			ArrayList<Node> chooseFrom = new ArrayList<Node>();
			
			if (proximity > distancesNodes.size()) {
				for (int i = 0; i < distancesNodes.size(); i++) {
					chooseFrom.add(distancesNodes.get(distancesNodes.keySet().toArray()[i]));
				}
			} else {
				for (int i = 0; i < proximity; i++) {
					chooseFrom.add(distancesNodes.get(distancesNodes.keySet().toArray()[i]));
				}				
			}
			
			currentNode = chooseFrom.get(rm.r.nextInt(chooseFrom.size()));
			currentNode.setOpen(false);
			
			route.add(currentNode);
			
		}
	}
	
	@Override
	public void render() {		
		// show this screen for 5 seconds
		timeSeconds += Gdx.graphics.getDeltaTime();
		if (timeSeconds > 3 || Gdx.input.isKeyJustPressed(Keys.ENTER)) Main.setScene(new TwoOptScene(rm, route));
		
		this.draw();
		
		rm.sr.begin(ShapeType.Line);
		rm.sr.setColor(Color.GRAY);

		// draw lines between all nodes
		for (int i = 0; i < route.size()-1; i++) {
			Utils.drawLine(rm.sr, route.get(i), route.get(i+1), Color.GRAY, 1);
		}
		Utils.drawLine(rm.sr, route.get(0), route.get(route.size()-1), Color.GRAY, 1); // draw line from last to first point = complete the loop
		
		rm.sr.end();

		rm.sr.begin(ShapeType.Filled);
		
		// draw nodes points
		for (int i = 0; i<route.size(); i++) {
			Utils.drawNode(rm.sr, route.get(i));
		}
		//separator rect
		rm.sr.rect(rm.WIDTH, 0, 5, rm.HEIGHT);

		rm.sr.end();
	
		// draw fps
		fps.setText(Integer.toString(Gdx.graphics.getFramesPerSecond()));
	}	
	
}
