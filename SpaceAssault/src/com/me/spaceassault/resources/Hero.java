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
	
	public static final float SPEED = 4f; // character velocity
	public final float JUMP_SPEED = 1f; // jumping speed
	public final float SIZE = 0.5f; // character size
	
	Vector2 pos = new Vector2(); 
	Vector2 accel = new Vector2(); 
	Vector2 vel = new Vector2(); 
	Rectangle bounds = new Rectangle();
	boolean dir_left = true;
	State state = State.IDLE;
	float stateTime = 0;
	
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
	
	public State getState() {
		return state;
	}
	
	public void setState(State newState) {
		this.state = newState;
	}
	
	public float getStateTime() {
		return stateTime;
	}
	
	public void update(float delta) {
		stateTime += delta;
		pos.add(vel.cpy().scl(delta));
	}
	
	public void setFacingLeft(boolean dir) {
		dir_left = dir;
	}
	
	public Vector2 getVelocity() {
		return vel;
	}
	public Vector2 getAcceleration() {
		return accel;
	}
	public boolean isFacingLeft() {
		return dir_left;
	}
	
	

}
