package com.tsp.scene;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Align;
import com.tsp.Main;
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
	private TextBox logBox, fps, time;
	
	public TwoOptScene(final ResourceManager rm, final ArrayList<Node> route) {
		this.rm = rm;
		this.route = route;
		indexGenerator = new RouteIndexGenerator(route);
		initialDistance = Utils.totalRouteDistance(route);
		
		startTime = System.currentTimeMillis();
		
		time = new TextBox(rm.defaultSkin, "small", "", Align.topLeft, rm.WIDTH + 30, 50, rm.LOG_WIDTH, 50);
		fps = new TextBox(rm.defaultSkin, "small", "FPS: " + Gdx.graphics.getFramesPerSecond(), Align.topLeft, rm.WIDTH + 30, 0, rm.LOG_WIDTH, 50);
		logBox = new TextBox(rm.defaultSkin, "small", "", Align.topLeft, rm.WIDTH + 30, 0, rm.LOG_WIDTH, rm.LOG_HEIGHT);
		this.addUIComponent(fps);
		this.addUIComponent(logBox);
		this.addUIComponent(time);
	}

	@Override
	public void render() {
		this.draw();
		drawRoute();

		if (Gdx.input.isKeyPressed(Keys.ESCAPE)) Gdx.app.exit();

		if (locMinFound) {
			if (Gdx.input.isKeyPressed(Keys.ENTER)) Main.setScene(new MenuScene(rm));
			return;
		}

		// get next set of indices to swap
		edges = indexGenerator.next();
		if (edges == null) return;
		
		// if we are checking the first edge
		if (Arrays.equals(edges, new int[]{route.size()-1, 0, route.size()-3, route.size()-2})) {
			if (!swapsMade) { // if no swaps have been made in the last iteration
				logBox.setText(logBox.getText() + "\n\nLOCAL MINIMUM FOUND.\n\nExecution took " + ((float) (System.currentTimeMillis()-startTime)/1000.0f) + "s\n\nPress enter to return\nto the main menu...");
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
			Utils.drawLine(rm.sr, route.get(i), route.get(i+1), Color.GRAY, 1);
		}
		Utils.drawLine(rm.sr, route.get(0), route.get(route.size()-1), Color.GRAY, 1);
		
		rm.sr.end();
		
		rm.sr.begin(ShapeType.Filled);
		
		// draw nodes points
		if (edges != null && !locMinFound) {
			Utils.drawLine(rm.sr, route.get(edges[0]), route.get(edges[1]), Color.RED, 3);
			Utils.drawLine(rm.sr, route.get(edges[2]), route.get(edges[3]), Color.RED, 3);

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
		
		//separator rect
		rm.sr.rect(rm.WIDTH, 0, 5, rm.HEIGHT);
		
		rm.sr.end();
		
		// if program is still going then print the execution time
		time.setText((!locMinFound ? "Time elapsed: " + ((float) (System.currentTimeMillis()-startTime)/1000.0f) + "s" : ""));
		
		fps.setText("FPS: " + Gdx.graphics.getFramesPerSecond());
	}	

}
