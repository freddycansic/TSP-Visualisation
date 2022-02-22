package com.tsp.screens;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.tsp.Main;
import com.tsp.Node;
import com.tsp.RouteIndexGenerator;

public class TwoOptScreen implements Screen {

	private final Main main;
	private ArrayList<Node> route;
	
	private RouteIndexGenerator indexGenerator;
	private int initialDistance, numSwaps = 0;
	private int[] edges;
	private boolean locMinFound = false, swapsMade = false;
	
	public TwoOptScreen(final Main main, final ArrayList<Node> route) {
		this.main = main;
		this.route = route;
	}
	
	@Override
	public void show() {
		indexGenerator = new RouteIndexGenerator(route);
		initialDistance = totalRouteDistance(route);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT | (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0));
		Gdx.gl.glClearColor(1, 1, 1, 1);
		
		drawRoute();

		if (locMinFound) {
			if (Gdx.input.isKeyPressed(Keys.ENTER)) Gdx.app.exit();
			return;
		}

		// get next set of indices to swap
		edges = indexGenerator.next();
		if (edges == null) return;
		
		// if we are checking the first edge
		if (Arrays.equals(edges, new int[]{route.size()-1, 0, route.size()-3, route.size()-2})) {
			if (!swapsMade) { // if no swaps have been made in the last iteration
				System.out.println("-------------------------------------------" +
						"\nLOCAL MINIMUM FOUND." + 
						"\n-------------------------------------------" + 
						"\nExecution took " + ((float) (System.currentTimeMillis()-main.startTime)/1000.0f) + "s" +
						"\nPress enter to exit...");
				locMinFound = true;
				return;
			}
			// start new check 
			swapsMade = false;
		}

		// deep copy route
		ArrayList<Node> swappedRoute = new ArrayList<Node>();
		swappedRoute.addAll(route);			

		// 2 opt swap with end of first edge and start of second edge
		Collections.swap(swappedRoute, edges[1], edges[2]);

		// if the swap saved distance then keep it
		int swappedDistance = totalRouteDistance(swappedRoute);
		if (swappedDistance < totalRouteDistance(route)) {
			swapsMade = true;
			numSwaps++;
			System.out.println("-------------------------------------------" +
					"\nInitial distance = " + initialDistance +
					"\nCurrent distance = " + swappedDistance +
					"\nDistance reduced = " + (initialDistance - swappedDistance) + " (" + (100 - (((float) swappedDistance / (float) initialDistance) * 100)) + "%)" +
					"\nTotal swaps made = " + numSwaps);

			route = swappedRoute;
		}
		
	}

	private int totalRouteDistance(ArrayList<Node> nodes) {
		int distance = 0;
		for (int i = 0; i < nodes.size()-1; i++) {
			distance += nodes.get(i).distTo(nodes.get(i+1));
		}
		return distance + nodes.get(nodes.size()-1).distTo(nodes.get(0));
	}

	private void drawRoute() {
		main.sr.begin(ShapeType.Line);
		main.sr.setColor(Color.GRAY);

		// draw lines between all nodes
		for (int i = 0; i < route.size()-1; i++) {
			
			//System.out.println("Drawing line between " + allNodes.indexOf(route.get(i)) + " " + allNodes.indexOf(route.get(i+1)));
			main.drawLine(main.sr, route.get(i), route.get(i+1), Color.GRAY, 1);
		}
		main.drawLine(main.sr, route.get(0), route.get(route.size()-1), Color.GRAY, 1);
		
		main.sr.end();
		
		main.sr.begin(ShapeType.Filled);
		// draw nodes points
		if (edges != null && !locMinFound) {

			main.drawLine(main.sr, route.get(edges[0]), route.get(edges[1]), Color.BLUE, 3);
			main.drawLine(main.sr, route.get(edges[2]), route.get(edges[3]), Color.BLUE, 3);

			for (int i = 0; i<route.size(); i++) {
				if (i == edges[0] || i == edges[1] || i == edges[2] || i == edges[3]) continue;
				main.drawNode(main.sr, route.get(i), Color.GRAY);
			}
			
			for (int i = 0; i < edges.length; i++) {
				main.drawNode(main.sr, route.get(edges[i]), Color.BLUE);
			}
		} else {
			for (int i = 0; i < route.size(); i++) {
				main.drawNode(main.sr, route.get(i), Color.GRAY);
			}
		}
		
		main.sr.end();
				
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
