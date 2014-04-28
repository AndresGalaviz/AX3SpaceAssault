package com.me.spaceassault.world;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.me.spaceassault.resources.BadGuy;
import com.me.spaceassault.resources.Hero;
import com.me.spaceassault.resources.Tile;

/**
 * Clase que contiene la informacion del mapa
 * @author Alberto
 *
 */
public class World {

	
	Hero hero;
	Level level;
	BadGuy badGuy;
	/*Cajas de colision*/
	
	Array<Rectangle> collisionRects = new Array<Rectangle>();
	Array<BadGuy> badGuys = new Array<BadGuy>();
	
	
	public Array<Rectangle> getCollisionRects() {
		return collisionRects;
	}


    public Hero getHero() {
    	return hero;
    }
    public Level getLevel() {
    	return level;
    }
    
    public Array<BadGuy> getBdadGuys() {
    	return badGuys;
    }
    public BadGuy getBadGuy() {
    	return badGuy;
    }
    
    public List<Tile> getDrawableBlocks(int width, int height) {
    	int x, y, x2, y2;
    	x = (int)hero.getPosition().x - width;
    	y = (int)hero.getPosition().y - height;
    	if(x<0) {
    		x = 0;
    	}
    	if(y<0) {
    		y = 0;
    	}
    	x2 = x + 2 * width;
    	y2 = y + 2* height;
    	if(x2 >= level.getWidth()) {
    		x2 = level.getWidth() - 1;
    	}
   
    	if(y2 >= level.getHeight()) {
    		y2 = level.getHeight() - 1;
    	}
    	List<Tile> tiles = new ArrayList <Tile>();
    	Tile tile;
    	for(int col = x; col <= x2; col++) {
    		for(int row = y; row <= y2;  row++) {
    			tile = level.getTiles()[col][row];
    			if(tile != null) {
    				tiles.add(tile);
    			}
    		}
    	}
    	return tiles;
    }
	public World() {
		createDemoWorld();
	}
	
    /**
     * Metodo que coloca objetos en el mundo
     */
	private void createDemoWorld() {

		hero = new Hero(new Vector2(6,26));
		badGuy = new BadGuy(new Vector2(10,2), 12);

		level = new Level();
	}
}
