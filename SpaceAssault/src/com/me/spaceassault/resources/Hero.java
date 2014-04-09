package com.me.spaceassault.resources;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;


public class Hero {
	
	// character states
	public enum State {
		IDLE,
		WALK,
		DEAD,
		JUMP
	}
	
	static final float SPEED = 2f; // character velocity
	public final float JUMP_SPEED = 1f; // jumping speed
	public final float SIZE = 0.5f; // character size
	
	Vector2 pos = new Vector2(); 
	Vector2 accel = new Vector2(); 
	Vector2 vel = new Vector2(); 
	Rectangle bounds = new Rectangle();
	boolean dir_left = true;
	State state = State.IDLE;
	
	public Hero(Vector2 position) {
		this.pos = position;
		this.bounds.height = SIZE;
		this.bounds.width = SIZE;
	}
	
	public Hero getHero() {
		return this;
	}
	
	public Vector2 getPosition() {
		return pos;
	}
	
	public Rectangle getBounds() {
		return bounds;
	}
	

}
