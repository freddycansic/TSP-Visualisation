package com.tsp.scene;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
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

		fps = new TextBox(rm.defaultSkin, "small", Integer.toString(Gdx.graphics.getFramesPerSecond()), Align.topLeft, 0, rm.WIDTH-10, 100, 100);
		logBox = new TextBox(rm.defaultSkin, "small", "", Align.topLeft, rm.WIDTH + 10, 0, rm.LOG_WIDTH, rm.LOG_HEIGHT);
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
		this.draw();
		
		timeSeconds += Gdx.graphics.getDeltaTime();
		
		if (timeSeconds > 0) Main.setScene(new TwoOptScene(rm, route));
		
		rm.sr.begin(ShapeType.Line);
		rm.sr.setColor(Color.GRAY);

		// draw lines between all nodes
		for (int i = 0; i < route.size()-1; i++) {
			
			//System.out.println("Drawing line between " + allNodes.indexOf(route.get(i)) + " " + allNodes.indexOf(route.get(i+1)));
			Utils.drawLine(rm.sr, route.get(i), route.get(i+1), Color.GRAY, 1);
		}
		Utils.drawLine(rm.sr, route.get(0), route.get(route.size()-1), Color.GRAY, 1);
		
		rm.sr.end();

		rm.sr.begin(ShapeType.Filled);
		
		// draw nodes points
		for (int i = 0; i<route.size(); i++) {
			Utils.drawNode(rm.sr, route.get(i));
		}
		rm.sr.end();

		fps.setText(Integer.toString(Gdx.graphics.getFramesPerSecond()));
		
//		rm.batch.begin();
//		rm.regularFont.draw(
//				rm.batch,
//				"Nearest neighbour generated path with " + numNodes + " nodes and a selection proximity of " + proximity, 
//				rm.WIDTH/2, 
//				rm.HEIGHT/2, 
//				0,
//				Align.center,
//				false
//		);
//		rm.batch.end();

	}	
	
}
