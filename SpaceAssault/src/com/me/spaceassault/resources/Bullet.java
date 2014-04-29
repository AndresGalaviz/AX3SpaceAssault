package com.me.spaceassault.resources;

import com.badlogic.gdx.math.Vector2;

public class Bullet extends Object {
	private static final float SPEED = .2f;
	
	public Bullet(Hero hero) {
		super(new Vector2(hero.getPosition()), .4f, .1f, SPEED);
		setFacingLeft(hero.isFacingLeft());
		getPosition().y += hero.HEIGHT/2 - HEIGHT;
		if (isFacingLeft()) {
			getVelocity().x = -SPEED;
			getPosition().x -= hero.WIDTH/2;
		} else {
			getVelocity().x = SPEED;
			getPosition().x += hero.WIDTH;
		}
		getVelocity().y = 0;
	}
}
