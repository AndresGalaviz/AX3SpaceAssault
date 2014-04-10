package com.me.spaceassault.control;

import java.util.HashMap;
import java.util.Map;

import com.me.spaceassault.resources.Hero;
import com.me.spaceassault.world.World;



public class WorldController {
	
	enum Keys {
		LEFT, RIGHT, JUMP, FIRE
	}
	
	private World world;
	private Hero hero;
	
    static Map<Keys, Boolean> keys = new HashMap<WorldController.Keys, Boolean>();
    static {
    	keys.put(Keys.LEFT,  false);
    	keys.put(Keys.RIGHT, false);
    	keys.put(Keys.JUMP,  false);
    	keys.put(Keys.FIRE, false);
    	
    };
    
    public WorldController(World world) {
    	this.world = world;
    	this.hero = world.getHero();
    }
    
	public void leftPressed() {
		keys.get(keys.put(Keys.LEFT, true));
	}

	public void rightPressed() {
		keys.get(keys.put(Keys.RIGHT, true));
	}

	public void jumpPressed() {
		keys.get(keys.put(Keys.JUMP, true));
	}

	public void firePressed() {
		keys.get(keys.put(Keys.FIRE, false));
	}

	public void leftReleased() {
		keys.get(keys.put(Keys.LEFT, false));
	}

	public void rightReleased() {
		keys.get(keys.put(Keys.RIGHT, false));
	}

	public void jumpReleased() {
		keys.get(keys.put(Keys.JUMP, false));
	}

	public void fireReleased() {
		keys.get(keys.put(Keys.FIRE, false));
	}
	
	public void update(float delta) {
		processInput();
		hero.update(delta);
	}
	private void processInput() {
		if (keys.get(Keys.LEFT)) {
			// left is pressed
			hero.setFacingLeft(true);
			hero.setState(Hero.State.WALK);
			hero.getVelocity().x = -Hero.SPEED;
		}
		if (keys.get(Keys.RIGHT)) {
			// left is pressed
			hero.setFacingLeft(false);
			hero.setState(Hero.State.WALK);
			hero.getVelocity().x = Hero.SPEED;
		}
		// need to check if both or none direction are pressed, then Hero is idle
		if ((keys.get(Keys.LEFT) && keys.get(Keys.RIGHT)) ||
				(!keys.get(Keys.LEFT) && !(keys.get(Keys.RIGHT)))) {
			hero.setState(Hero.State.IDLE);
			// acceleration is 0 on the x
			hero.getAcceleration().x = 0;
			// horizontal speed is 0
			hero.getVelocity().x = 0;
		}
	}
}
