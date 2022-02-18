package com.tsp;

import java.util.ArrayList;

public class RouteIndexGenerator {

	private int i = 0, j = -1;
	private ArrayList<Node> route;
	
	public RouteIndexGenerator(ArrayList<Node> route) {
		this.route = route;
	}
	
	public int[] next() {
		j++;
		
		if (j == route.size()) {
			j = 0;
			i++;
		}
		if (i == route.size()) {
			if (!Main.swapsMade) return new int[] {-1}; // return this if no swaps were made after a full rotation
			i = 0;
			Main.swapsMade = false;
		}
		
		int edgeStartIndex1 = i;
		int edgeEndIndex1 = i+1;
		int edgeStartIndex2 = j;
		int edgeEndIndex2 = j+1;
		
		if (edgeEndIndex1 == route.size())
			edgeEndIndex1 = 0;
		
		if (edgeEndIndex2 == route.size())
			edgeEndIndex2 = 0;
		
		// probably the worst code i have ever written
		if (edgeStartIndex1 == edgeStartIndex2) return null;
		
		if (edgeStartIndex1 == edgeEndIndex2) return null;
		
		if (edgeEndIndex1 == edgeStartIndex2) return null;
		
		if (edgeEndIndex1 == edgeEndIndex2)	return null;

		return new int[] {edgeStartIndex1, edgeEndIndex1, edgeStartIndex2, edgeEndIndex2};
	}
	
}
