package com.me.spaceassault.world;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.math.Vector2;
import com.me.spaceassault.resources.Hero;
import com.me.spaceassault.resources.Tile;

/**
 * Clase que contiene la informaci�n del mapa
 * @author Alberto
 *
 */
public class World {

	Array<Tile> tiles = new Array<Tile>();
	Hero Pyro;
	
	public World() {
		createDemoWorld();
	}
    public Array<Tile> getTiles() {
    	return tiles;
    }
    public Hero getHero() {
    	return Pyro;
    }
	
    /**
     * Metodo que coloca objetos en el mundo
     */
	private void createDemoWorld() {
		Pyro = new Hero(new Vector2(7,2));
		
        for (int i = 0; i < 10; i++) {
        	tiles.add(new Tile(new Vector2(i, 0)));                       
        	tiles.add(new Tile(new Vector2(i, 6)));
        	if (i > 2) {
        		tiles.add(new Tile(new Vector2(i, 1)));
        	}
        }
        tiles.add(new Tile(new Vector2(9, 2)));
        tiles.add(new Tile(new Vector2(9, 3)));
        tiles.add(new Tile(new Vector2(9, 4)));
        tiles.add(new Tile(new Vector2(9, 5)));
        
        tiles.add(new Tile(new Vector2(6, 3)));
        tiles.add(new Tile(new Vector2(6, 4)));
        tiles.add(new Tile(new Vector2(6, 5)));

	}
}
