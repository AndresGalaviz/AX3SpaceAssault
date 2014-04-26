package com.me.spaceassault.resources;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Tile {

	public final float SIZE = 1f;
	
	Vector2 pos = new Vector2();
	Rectangle bounds = new Rectangle();
	
	public Tile(Vector2 position) {
		this.pos = position;
		this.bounds.setX(pos.x);
		this.bounds.setY(pos.y);
		this.bounds.height = SIZE;
		this.bounds.width = SIZE;
	}

	/**
	 * Regresa la posicion del cuadro
	 * @return pos
	 */
	public Vector2 getPosition() {
		return pos;
	}
	
	/**
	 * Regresa el borde del cuadro, para manejo de colisiones
	 * @return bounds
	 */
	public Rectangle getBounds() {
		return bounds;
	}
	
	
	


}
