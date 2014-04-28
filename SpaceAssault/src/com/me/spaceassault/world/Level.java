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
		width = 200;
		height = 31;
		tiles = new Tile[width][height];
		
		FileHandle handle = Gdx.files.internal(fileName);
		String fileContent = handle.readString();
		//System.out.println(fileContent);
		fileContent.replace('\n', ' ');
		String[] splitResult = fileContent.split(" ");
		for (int i = 0; i < splitResult.length; i += 2) {
			int a = Integer.valueOf(splitResult[i]);
			int b = Integer.valueOf(splitResult[i+1]);
			tiles[a][b] = new Tile(new Vector2(a, b));
		}
		System.out.println();
	}
	
	
	private void loadDemoLevel() {
		width = 200;
		height = 50;
		tiles = new Tile[width][height];
//		for (int col = 0; col < width; col++) {
//			for (int row = 0; row < height; row++) {
//				tiles[col][row] = null;
//			}
//		}
//
//		for (int col = 0; col < 10; col++) {
//			tiles[col][0] = new Tile(new Vector2(col, 0));
//			tiles[col][6] = new Tile(new Vector2(col, 6));
//			if (col > 2) {
//				tiles[col][1] = new Tile(new Vector2(col, 1));
//			}
//		}
		
		
	}
	
}