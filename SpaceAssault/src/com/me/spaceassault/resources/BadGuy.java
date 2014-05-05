package com.me.spaceassault.resources;

import com.badlogic.gdx.math.Vector2;

public class BadGuy extends Object{
	private static final float SPEED = .01f;
	private boolean moving;
	private int strength;
	
	/**
	 * Metodo constructor para los enemigos del personaje
	 * @param position
	 * @param life
	 */
	public BadGuy(Vector2 position, int life, int strength) {
		super (position, 0.8f, 0.8f, SPEED, life);
		getVelocity().x = -SPEED;
		moving = false;
		this.strength = strength;
		setGrounded(true);
	}
	
	public void setMoving(boolean moving) {
		this.moving = moving;
	}
		
	public boolean isMoving() {
		return moving;
	}
	
	public int getStrength() {
		return strength;
	}
	public void startMoving(Hero hero, float w, float h) {
		moving = (getPosition().x < hero.getPosition().x + w/2 && 
				getPosition().x > hero.getPosition().x - w/2 - getBounds().width && 
				getPosition().y < hero.getPosition().y + h/2 && 
				getPosition().y > hero.getPosition().y - h/2 - getBounds().height);
	}
	
	public static float getSpeed() {
		return SPEED;
	}
}
