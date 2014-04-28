package com.me.spaceassault.resources;

import com.badlogic.gdx.math.Vector2;

public class BadGuy extends Object{

	private boolean moving;
	private boolean facingLeft;
	
	public BadGuy(Vector2 position, int life) {
		super (position);
		this.life = life;
	}
	
	public void setMovingTime(boolean moving) {
		this.moving = moving;
	}
	
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
