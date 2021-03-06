package com.me.spaceassault.world;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.me.spaceassault.resources.BadGuy;
import com.me.spaceassault.resources.Bullet;
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
	/*Cajas de colision*/
	
	Array<Rectangle> collisionRects = new Array<Rectangle>();
	Array<BadGuy> badGuys = new Array<BadGuy>();
	Array<Bullet> bullets = new Array<Bullet>();
	Array<Bullet> badBullets = new Array<Bullet>();
	
	
	public Array<Rectangle> getCollisionRects() {
		return collisionRects;
	}


    public Hero getHero() {
    	return hero;
    }
    public Level getLevel() {
    	return level;
    }
    
    public Array<BadGuy> getBadGuys() {
    	return badGuys;
    }
    
    public Array<Bullet> getBullets() {
    	return bullets;
    }
    
    public Array<Bullet> getBadBullets() {
    	return badBullets;
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


		//hero = new Hero(new Vector2(3,24));
		//badGuys.add(new BadGuy(new Vector2(10,1), 12, 30));
		//badGuys.add(new BadGuy(new Vector2(5,24), 16, 20));
		//badGuys.add(new BadGuy(new Vector2(15,24), 10, 50));
		//hero = new Hero(new Vector2(1,4));
		//badGuys.add(new BadGuy(new Vector2(11, 3), 12, 30));
		//level = new Level();
		
		Vector2 dim = new Vector2(0,0);
		readGeneralLevelInfo(dim, "levels/level2grl.txt");
		level = new Level(1, dim, "levels/level2lvl.txt");
		
	}
	
	private void readGeneralLevelInfo(Vector2 dim, String fileName) {
		FileHandle handle = Gdx.files.internal(fileName);
		String fileContent = handle.readString();
		
		String[] splitResult = fileContent.split(" ");
		
		dim.x = Integer.valueOf(splitResult[0]);
		dim.y = Integer.valueOf(splitResult[1]);
		
		int x, y, l, s;
		x = Integer.valueOf(splitResult[2]);
		y = Integer.valueOf(splitResult[3]);
		hero = new Hero(new Vector2(x, y));
		
		for (int i = 4; i < splitResult.length; i += 4) {
			x = Integer.valueOf(splitResult[i]);
			y = Integer.valueOf(splitResult[i+1]);
			l = Integer.valueOf(splitResult[i+2]);
			s = Integer.valueOf(splitResult[i+3]);
			
			badGuys.add(new BadGuy(new Vector2(x, y), l, s));
			
		}
	}
	
	
}
