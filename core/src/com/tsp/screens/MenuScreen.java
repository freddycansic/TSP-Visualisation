package com.tsp.screens;

import java.util.Scanner;

import com.badlogic.gdx.Screen;
import com.tsp.Main;

public class MenuScreen implements Screen{

	private final Main main;

	private int numNodes, proximity;
	private Scanner s;
	
	public MenuScreen(final Main main) {
		this.main = main;
	}
	
	@Override
	public void show() {
		s = new Scanner(System.in);
		
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
		
		main.setScreen(new NearestNeighbourScreen(main, numNodes, proximity));
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		
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
		s.close();
		
	}

}
