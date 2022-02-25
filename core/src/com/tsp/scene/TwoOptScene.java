package com.tsp.scene;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Align;
import com.tsp.Node;
import com.tsp.RouteIndexGenerator;
import com.tsp.ui.TextBox;
import com.tsp.util.ResourceManager;
import com.tsp.util.Utils;

public class TwoOptScene extends Scene {

	private final ResourceManager rm;
	private ArrayList<Node> route;
	
	private RouteIndexGenerator indexGenerator;
	private int initialDistance, numSwaps = 0, swappedDistance;
	private int[] edges;
	private boolean locMinFound = false, swapsMade = false;
	private long startTime;
	private TextBox logBox, fps;
	
	public TwoOptScene(final ResourceManager rm, final ArrayList<Node> route) {
		this.rm = rm;
		this.route = route;
		indexGenerator = new RouteIndexGenerator(route);
		initialDistance = Utils.totalRouteDistance(route);
		
		startTime = System.currentTimeMillis();
		
		logBox = new TextBox(rm.defaultSkin, "small", "", Align.topLeft, rm.WIDTH + 10, 0, rm.LOG_WIDTH, rm.LOG_HEIGHT);
		fps = new TextBox(rm.defaultSkin, "small", Integer.toString(Gdx.graphics.getFramesPerSecond()), Align.topLeft, 0, rm.WIDTH-10, 100, 100);
		this.addUIComponent(fps);
		this.addUIComponent(logBox);
	}

	@Override
	public void render() {
		if (Gdx.input.isKeyPressed(Keys.ESCAPE)) Gdx.app.exit();
		
		this.draw();
		
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
				logBox.setText(logBox.getText() + "\nLOCAL MINIMUM FOUND.\n\nExecution took " + ((float) (System.currentTimeMillis()-startTime)/1000.0f) + "s\nPress enter to exit...");
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
		swappedDistance = Utils.totalRouteDistance(swappedRoute);
		if (swappedDistance < Utils.totalRouteDistance(route)) {
			swapsMade = true;
			numSwaps++;
			
			logBox.setText("\nInitial distance:\n" + initialDistance +
					"\n\nCurrent distance:\n" + swappedDistance +
					"\n\nDistance reduced by:\n" + (initialDistance - swappedDistance) + "\n(" + (100 - (((float) swappedDistance / (float) initialDistance) * 100)) + "%)" +
					"\n\nTotal swaps made:\n" + numSwaps);
			
			route = swappedRoute;
		}
		
	}

	private void drawRoute() {
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
		if (edges != null && !locMinFound) {

			Utils.drawLine(rm.sr, route.get(edges[0]), route.get(edges[1]), Color.BLUE, 3);
			Utils.drawLine(rm.sr, route.get(edges[2]), route.get(edges[3]), Color.BLUE, 3);

			for (int i = 0; i<route.size(); i++) {
				if (i == edges[0] || i == edges[1] || i == edges[2] || i == edges[3]) continue;
				Utils.drawNode(rm.sr, route.get(i));
			}
			
			for (int i = 0; i < edges.length; i++) {
				Utils.drawNode(rm.sr, route.get(edges[i]));
			}
		} else {
			for (int i = 0; i < route.size(); i++) {
				Utils.drawNode(rm.sr, route.get(i));
			}
		}
		
		rm.sr.end();

		fps.setText(Integer.toString(Gdx.graphics.getFramesPerSecond()));
	}	

}
