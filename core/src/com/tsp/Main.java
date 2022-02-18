package com.tsp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class Main extends ApplicationAdapter {

	private final Random r = new Random();
	private final Scanner s = new Scanner(System.in);
	private int WIDTH, HEIGHT;
	private float timeSeconds = 0;
	private int initialDistance;
	private int[] edges;
	private int numSwaps = 0;
	public static boolean swapsMade;
	private boolean running = true;
	private long startTime;
	
	private ArrayList<Node> allNodes = new ArrayList<Node>();
	private ArrayList<Node> route = new ArrayList<Node>();
	private RouteIndexGenerator indexGenerator;
	
	private ShapeRenderer sr;
	private BitmapFont font;
	private SpriteBatch batch;

	// user args
	private int proximity;
	private int numNodes;
	
	@Override
	public void create () {
		System.out.print("Enter the number of nodes in the route: ");
		numNodes = s.nextInt();
		
		if (numNodes < 3) {
			System.out.println("Must be at least 3.");
			System.exit(0);
		}

		System.out.print("Enter number of nearest nodes to choose from when creating the base route: ");
		proximity = s.nextInt();
		
		if (proximity < 1) {
			System.out.println("Must be at least 1.");
			System.exit(0);
		}

		proximity = 2;
		
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
		sr = new ShapeRenderer();
		batch = new SpriteBatch();
		font = new BitmapFont(); 

		font.setColor(Color.BLACK);
		font.usesIntegerPositions();

		startTime = System.currentTimeMillis();
		
		for (int i = 0; i < numNodes; i++) {
			Node node = new Node(10 + r.nextInt(WIDTH - 10), 10 + r.nextInt(HEIGHT - 10));
			allNodes.add(node);
		}
//
//		allNodes.add(new Node(201, 200));
//		allNodes.add(new Node(701, 700));
//		allNodes.add(new Node(200, 703));
//		allNodes.add(new Node(700, 204));
		
		route = nearestNeighbour(allNodes, proximity);
		
		indexGenerator = new RouteIndexGenerator(route);
		initialDistance = totalRouteDistance(route);
//		
//		System.out.println(allNodes.toString());
//		System.out.println(route.toString());
	}

	@Override
	public void render () { // turn on anti aliasing
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT | (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0));
		Gdx.gl.glClearColor(1, 1, 1, 1);
		
		drawRoute(sr, route);

		timeSeconds += Gdx.graphics.getDeltaTime();
		if ( running && timeSeconds > 0.0f) {			
			timeSeconds = 0.0f;

			// get next set of indices to swap
			edges = indexGenerator.next();
			if (edges == null) return;
			
			if (edges[0] == -1) { // if a full rotation was made without any swaps occurring
				System.out.println("-------------------------------------------" +
						"\nLOCAL MINIMUM FOUND." + 
						"\n-------------------------------------------" + 
						"\nCTRL+C to exit." + 
						"\nExecution took " + ((float) (System.currentTimeMillis()-startTime)/1000.0f) + "s");
				running = false;
				return;
			}
			
			// deep copy route
			ArrayList<Node> swappedRoute = new ArrayList<Node>();
			swappedRoute.addAll(route);			
			
			// 2 opt swap
			Collections.swap(swappedRoute, edges[0], edges[2]);
			
			// if the swap saved distance then keep it
			int swappedDistance = totalRouteDistance(swappedRoute);
			if (swappedDistance < totalRouteDistance(route)) {
				numSwaps++;
				swapsMade = true;
				System.out.println("-------------------------------------------" +
						"\nInitial distance = " + initialDistance +
						"\nCurrent distance = " + swappedDistance +
						"\nDistance reduced = " + (initialDistance - swappedDistance) + " (" + (100 - (((float) swappedDistance / (float) initialDistance) * 100)) + "%)" +
						"\nTotal swaps made = " + numSwaps);
				
				route = swappedRoute;
			}
		}

	}

	@Override
	public void dispose () {
		batch.dispose();
		sr.dispose();
		font.dispose();
		s.close();
	}
	
	private ArrayList<Node> nearestNeighbour(ArrayList<Node> nodes, int proximity) {
		nodes.get(0).setOpen(false);
		ArrayList<Node> route = new ArrayList<Node>();
		
		for (int i = 0; i < nodes.size()-1; i++) {
			Node n1 = nodes.get(i);
			
			// get distances from current node to all other nodes
			Map<Integer, Node> allDistancesToNodes = new LinkedHashMap<Integer, Node>(); // (distance to node, node) map

			for (Node n2 : nodes) {
				if (n1 == n2) continue;
				// add distance from n2, n2 to map
				allDistancesToNodes.put(n1.distTo(n2), n2);
			}

			// convert to treemap = sort map by natural order of keys
			Map<Integer, Node> sortedNodes = new TreeMap<Integer, Node>(allDistancesToNodes); 

//			for (Map.Entry<Integer, Node> entry : sortedNodes.entrySet() ) {
//				System.out.println(nodes.indexOf(n1) + "-" + nodes.indexOf(entry.getValue()) + " = " + entry.getKey());
//			}
			
			// get first n nodes to current node = closest n nodes
			ArrayList<Node> closestNodes = new ArrayList<Node>();
			for(int j = 0; j < proximity; j++)
				closestNodes.add(sortedNodes.get(sortedNodes.keySet().toArray()[j]));
			
			// SELECT PATH FROM CLOSEST NODES
			if (openNodesExist(closestNodes)) { // if any of the closest nodes are open
				Node randomNode = pickRandomOpenNode(closestNodes); // pick a random one
				randomNode.setOpen(false); // close it
				route.add(randomNode); // add it to the route

			} else { // otherwise 
				Node randomNode = pickRandomOpenNode(nodes); // pick a random open node from all nodes
				randomNode.setOpen(false); // close it
				route.add(randomNode); // add it to route
			}
			
//			System.out.println("Chose " + nodes.indexOf(route.get(route.size()-1)));
//			System.out.println(route.toString());
//			System.out.println();
			
		}
		route.add(nodes.get(0));
		Collections.reverse(route);
		
		return route;
	}

	private void drawRoute(ShapeRenderer sr, ArrayList<Node> route) {
		//if (route.size() == 0) return;
		
		sr.begin(ShapeType.Line);
		sr.setColor(Color.GRAY);

		// draw lines between all nodes
		for (int i = 0; i < route.size()-1; i++) {
			
			//System.out.println("Drawing line between " + allNodes.indexOf(route.get(i)) + " " + allNodes.indexOf(route.get(i+1)));
			sr.line(route.get(i).getVecPos(), route.get(i+1).getVecPos());
		}
		sr.line(route.get(0).getVecPos(), route.get(route.size()-1).getVecPos());
		
		if (running && edges != null) {			
			sr.setColor(Color.BLUE);
			
			line(sr, route.get(edges[0]), route.get(edges[1]), 3);
			line(sr, route.get(edges[2]), route.get(edges[3]), 3);
//			sr.line(route.get(edges[0]).getVecPos(), route.get(edges[1]).getVecPos());
//			sr.line(route.get(edges[2]).getVecPos(), route.get(edges[3]).getVecPos());
		}
		
		sr.end();

		// draw nodes points
		for (int i = 0; i<allNodes.size(); i++) {
			allNodes.get(i).draw(sr, Color.GRAY);
		}
		

		//		allNodes.get(0).draw(sr, Color.GREEN);
//		allNodes.get(allNodes.size()-1).draw(sr, Color.RED);

		batch.begin();
		for (Node node : allNodes) {
			font.draw(batch, allNodes.indexOf(node) + "\n(" + node.x + ", " + node.y + ")", node.x, node.y);			
		}
		batch.end();
	}

	private void line(ShapeRenderer sr, Node n1, Node n2, int lineWidth) {
		sr.rectLine(n1.getVecPos(), n2.getVecPos(), lineWidth);
	}
	
	private Node pickRandomOpenNode(ArrayList<Node> nodes) {
		ArrayList<Node> openNodes = new ArrayList<Node>();

		for (Node node : nodes) {
			if (node.isOpen()) openNodes.add(node);
		}

		if (openNodes.size() > 0) {
			return openNodes.get(r.nextInt(openNodes.size()));
		} else {
			return null;
		}
	}

	private int totalRouteDistance(ArrayList<Node> nodes) {
		int distance = 0;
		for (int i = 0; i < nodes.size()-1; i++) {
			distance += nodes.get(i).distTo(nodes.get(i+1));
		}
		return distance + nodes.get(nodes.size()-1).distTo(nodes.get(0));
	}

	private boolean openNodesExist(ArrayList<Node> nodes) {
		for (Node node : nodes) {
			if (node.isOpen()) {
				return true;
			}
		}
		return false;
	}

}
