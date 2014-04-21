package com.me.spaceassault.control;

import java.util.HashMap;
import java.util.Map;

import com.me.spaceassault.control.WorldController.Keys;
import com.me.spaceassault.resources.Hero;
import com.me.spaceassault.world.World;

public class HeroController {

    static Map<Keys, Boolean> keys = new HashMap<HeroController.Keys, Boolean>();

	enum Keys {
		LEFT, RIGHT, JUMP, FIRE
	}
	

	private static final long JUMP_PRESS = 150l;
	private static final float ACCEL = 20f;
	private static final float GRAVITY = -20f;
	private static final float MAX_JUMP = 7f;
	private static final float DAMP = 0.90f;
	private static final float MAX_VEL = 4f;
	
	
	private static final float WIDTH = 10f;
	//this is for commit
	
	
	private World world;
	private Hero hero;
	private long jumpPressedTime;
	private boolean jumpingPressed;
	
	public void jumpReleased() {
		keys.get(keys.put(Keys.JUMP, false));
		jumpingPressed = true;
	}
	
	
	public void update(float delta) {
		processInput();
		hero.getAcceleration().y = GRAVITY;
		hero.getAcceleration().scl(delta);
		hero.getVelocity().add(hero.getAcceleration().x, hero.getAcceleration().y);
		if(hero.getAcceleration().x == 0) {
			hero.getVelocity().x*=DAMP;
		}
		if(hero.getVelocity().x > MAX_VEL) {
			hero.getVelocity().x = MAX_VEL;
		}
		
		
	}
	
	public void processInput() {
		
	
	}
}
