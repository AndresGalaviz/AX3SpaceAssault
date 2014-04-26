package com.me.spaceassault.control;

import java.util.HashMap;
import java.util.Map;

import com.me.spaceassault.resources.Hero;
import com.me.spaceassault.world.World;

/**
 * Clase que controla el input durante la pantalla del juego
 *
 */

public class WorldController {
	
	enum Keys {
		LEFT, RIGHT, JUMP, FIRE
	}
	private static final long LONG_JUMP_PRESS 	= 150l;
	private static final float ACCELERATION 	= 20f;
	private static final float GRAVITY 			= -20f;
	private static final float MAX_JUMP_SPEED	= 7f;
	private static final float DAMP 			= 0.90f;
	private static final float MAX_VEL 			= 4f;
	
	private World world;
	private Hero hero;
	private long jumpPressedTime;
	private boolean jumpingPressed;
	private static final float WIDTH = 10f;

	
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
	

	
	/** The main update method **/
	public void update(float delta) {
		processInput();
		
		hero.getAcceleration().y = GRAVITY;
		hero.getAcceleration().scl(delta);
		hero.getVelocity().add(hero.getAcceleration().x, hero.getAcceleration().y);
		if (hero.getAcceleration().x == 0) hero.getVelocity().x *= DAMP;
		if (hero.getVelocity().x > MAX_VEL) {
			hero.getVelocity().x = MAX_VEL;
		}
		if (hero.getVelocity().x < -MAX_VEL) {
			hero.getVelocity().x = -MAX_VEL;
		}
		
		hero.update(delta);
		if (hero.getPosition().y < 0) {
			hero.getPosition().y = 0f;
			hero.setPosition(hero.getPosition());
			if (hero.getState().equals(Hero.State.JUMP)) {
					hero.setState(Hero.State.IDLE);
			}
		}
		if (hero.getPosition().x < 0) {
			hero.getPosition().x = 0;
			hero.setPosition(hero.getPosition());
			if (!hero.getState().equals(Hero.State.JUMP)) {
				hero.setState(Hero.State.IDLE);
			}
		}
		if (hero.getPosition().x > WIDTH - hero.getBounds().width ) {
			hero.getPosition().x = WIDTH - hero.getBounds().width;
			hero.setPosition(hero.getPosition());
			if (!hero.getState().equals(Hero.State.JUMP)) {
				hero.setState(Hero.State.IDLE);
			}
		}
	}

	/** Change hero's state and parameters based on input controls **/
	private boolean processInput() {
		if (keys.get(Keys.JUMP)) {
			if (!hero.getState().equals(Hero.State.JUMP)) {
				jumpingPressed = true;
				jumpPressedTime = System.currentTimeMillis();
				hero.setState(Hero.State.JUMP);
				hero.getVelocity().y = MAX_JUMP_SPEED; 
			} else {
				if (jumpingPressed && ((System.currentTimeMillis() - jumpPressedTime) >= LONG_JUMP_PRESS)) {
					jumpingPressed = false;
				} else {
					if (jumpingPressed) {
						hero.getVelocity().y = MAX_JUMP_SPEED;
					}
				}
			}
		}
		if (keys.get(Keys.LEFT)) {
			// left is pressed
			hero.setFacingLeft(true);
			if (!hero.getState().equals(Hero.State.JUMP)) {
				hero.setState(Hero.State.WALK);
			}
			hero.getAcceleration().x = -ACCELERATION;
		} else if (keys.get(Keys.RIGHT)) {
			// left is pressed
			hero.setFacingLeft(false);
			if (!hero.getState().equals(Hero.State.JUMP)) {
				hero.setState(Hero.State.WALK);
			}
			hero.getAcceleration().x = ACCELERATION;
		} else {
			if (!hero.getState().equals(Hero.State.JUMP)) {
				hero.setState(Hero.State.IDLE);
			}
			hero.getAcceleration().x = 0;
			
		}
		return false;
	}
}
