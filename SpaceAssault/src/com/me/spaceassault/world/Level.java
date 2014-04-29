package com.me.spaceassault.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.me.spaceassault.resources.Tile;

public class Level {

	private int width;
	private int height;
	private Tile[][] tiles;

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Tile[][] getTiles() {
		return tiles;
	}

	public void setTiles(Tile[][] Tiles) {
		this.tiles = Tiles;
	}

	public Level() {
		//loadDemoLevel();
		loadDemoLevelFile("levels/level1.txt");
	}

	public Tile get(int x, int y) {
		return tiles[x][y];
	}

	private void loadDemoLevelFile(String fileName) {
		FileHandle handle = Gdx.files.internal(fileName);
		String fileContent = handle.readString();
		
		String[] splitResult = fileContent.split(" ");
		
		width = Integer.valueOf(splitResult[0]);
		height = Integer.valueOf(splitResult[1]);
		tiles = new Tile[width][height];
		
		for (int i = 2; i < splitResult.length; i += 2) {
			int a = Integer.valueOf(splitResult[i]);
			int b = Integer.valueOf(splitResult[i+1]);
			System.out.println(a);
			tiles[a][b] = new Tile(new Vector2(a, b));
			
		}
		System.out.println();
	}
	
	
	
}