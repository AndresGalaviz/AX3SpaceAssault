package com.me.spaceassault.resources;

import com.badlogic.gdx.math.Vector2;

public class Bullet extends Object {
	private static final float SPEED = .2f;
	private int strength;
	
	/**
	 * Constructor de la clase
	 * @param hero heroe principal que dispara las balas
	 */
	public Bullet(Object obj, int strength) {
		
		super(new Vector2(obj.getPosition()), .3f, .1f, SPEED);
		setFacingLeft(obj.isFacingLeft());
		getPosition().y += obj.HEIGHT/2 - HEIGHT;  //  Actualiza la posicion en y de la bala
		if (isFacingLeft()) {
			getVelocity().x = -SPEED;
			getPosition().x -= WIDTH;
		} else {
			getVelocity().x = SPEED;
			getPosition().x += obj.WIDTH;
		}
		this.strength = strength;
		getVelocity().y = 0;
		
	}
	
	public int getStrength() {
		return strength;
	}
}
