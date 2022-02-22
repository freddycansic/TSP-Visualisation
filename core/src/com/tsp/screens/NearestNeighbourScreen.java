package com.tsp.screens;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Align;
import com.tsp.Main;
import com.tsp.Node;

public class NearestNeighbourScreen implements Screen {

	private final Main main;
	
	private ArrayList<Node> allNodes = new ArrayList<Node>();
	private ArrayList<Node> route = new ArrayList<Node>();
	private int numNodes, proximity;
	private float timeSeconds = 0;
	
	public NearestNeighbourScreen(final Main main, int numNodes, int proximity) {
		this.main = main;
		this.numNodes = numNodes;
		this.proximity = proximity;
	}
	
	@Override
	public void show() {
		for (int i = 0; i < numNodes; i++) {
			Node node = new Node(10 + main.r.nextInt(main.WIDTH - 10), 10 + main.r.nextInt(main.HEIGHT - 10));
			System.out.println("Node created " + node);
			allNodes.add(node);
		}
		
		allNodes.get(0).setOpen(false);
		Node currentNode = allNodes.get(0);
		
		route.add(currentNode);
		
		while (main.openNodesExist(allNodes)) {
			
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
			
			currentNode = chooseFrom.get(main.r.nextInt(chooseFrom.size()));
			currentNode.setOpen(false);
			
			route.add(currentNode);
			
		}
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT | (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0));
		Gdx.gl.glClearColor(1, 1, 1, 1);
		
		timeSeconds += Gdx.graphics.getDeltaTime();
		
		if (timeSeconds > 5) main.setScreen(new TwoOptScreen(main, route)); 
		
		main.sr.begin(ShapeType.Line);
		main.sr.setColor(Color.GRAY);

		// draw lines between all nodes
		for (int i = 0; i < route.size()-1; i++) {
			
			//System.out.println("Drawing line between " + allNodes.indexOf(route.get(i)) + " " + allNodes.indexOf(route.get(i+1)));
			main.drawLine(main.sr, route.get(i), route.get(i+1), Color.GRAY, 1);
		}
		main.drawLine(main.sr, route.get(0), route.get(route.size()-1), Color.GRAY, 1);
		
		// draw nodes points
		for (int i = 0; i<route.size(); i++) {
			main.drawNode(main.sr, route.get(i), Color.GRAY);
		}

		main.sr.end();
		
		main.batch.begin();
		main.font.draw(
				main.batch,
				"Nearest neighbour generated path with selection proximity of " + proximity, 
				main.WIDTH/4, 
				3*main.HEIGHT/4, 
				300,
				Align.center,
				false
		);
		main.batch.end();

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	

}
