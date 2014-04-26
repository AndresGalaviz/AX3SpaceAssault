package com.me.spaceassault.world;

import java.awt.List;
import java.util.ArrayList;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.me.spaceassault.resources.Hero;
import com.me.spaceassault.resources.Tile;

/**
 * Clase que contiene la informacion del mapa
 * @author Alberto
 *
 */
public class World {

	/** Our player controlled hero **/
	Hero pyro;
	/** A world has a level through which Bob needs to go through **/
	Level level;
	
	/** The collision boxes **/
	Array<Rectangle> collisionRects = new Array<Rectangle>();

	// Getters -----------
	
	public Array<Rectangle> getCollisionRects() {
		return collisionRects;
	}
	public Hero getHero() {
		return pyro;
	}
	public Level getLevel() {
		return level;
	}
	/** Return only the blocks that need to be drawn **/
	public Array<Tile> getDrawableBlocks(int width, int height) {
		int x = (int)pyro.getPosition().x - width;
		int y = (int)pyro.getPosition().y - height;
		if (x < 0) {
			x = 0;
		}
		if (y < 0) {
			y = 0;
		}
		int x2 = x + 2 * width;
		int y2 = y + 2 * height;
		if (x2 > level.getWidth()) {
			x2 = level.getWidth() - 1;
		}
		if (y2 > level.getHeight()) {
			y2 = level.getHeight() - 1;
		}
		
		Array<Tile> blocks = new Array<Tile>();
		Tile block;
		for (int col = x; col <= x2; col++) {
			for (int row = y; row <= y2; row++) {
				block = level.getTiles()[col][row];
				if (block != null) {
					blocks.add(block);
				}
			}
		}
		return blocks;
	}

	// --------------------
	public World() {
		createDemoWorld();
	}
	
	

	private void createDemoWorld() {
		pyro = new Hero(new Vector2(7, 2));
		level = new Level();
	}
}


