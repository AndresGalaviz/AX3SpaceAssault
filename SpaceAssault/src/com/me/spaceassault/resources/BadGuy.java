package com.me.spaceassault.resources;

import com.badlogic.gdx.math.Vector2;

public class BadGuy extends Object{

	private boolean moving;
	private boolean facingLeft;
	
	public BadGuy(Vector2 position, int life) {
		super (position, 0.8f, 0.8f, 50f, life);
	}
	
	public void setMovingTime(boolean moving) {
		this.moving = moving;
	}
	
	@Override
	public void setFacingLeft(boolean facingLeft) {
		this.facingLeft = facingLeft;
	}
	
	public boolean getMoving() {
		return moving;
	}
	
	public boolean getFacingLeft() {
		return facingLeft;
	}
	
}
