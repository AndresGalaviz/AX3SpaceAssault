package com.me.spaceassault.control;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.me.spaceassault.resources.Hero;
import com.me.spaceassault.resources.Tile;
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
	private static final float MAX_VEL 			= 50f;
	
	private World world;
	private Hero hero;
	private long jumpPressedTime;
	private boolean jumpingPressed;
	private static final float WIDTH = 10f;
	private Array<Tile> collidable = new Array<Tile>();
	
	private Pool<Rectangle> rectPool = new Pool<Rectangle>() {
		@Override
		protected Rectangle newObject() {
			return new Rectangle();
		}
	};
	
	private boolean grounded = false;
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

	public boolean isLeftOn() {
		return keys.get(Keys.LEFT);
	}
	public boolean isRightOn() {
		return keys.get(Keys.RIGHT);
	}
	public boolean isFireOn() {
		return keys.get(Keys.FIRE);
	}
	public boolean isJumpOn() {
		return keys.get(Keys.JUMP);
	}

	
	/** The main update method **/
	public void update(float delta) {
		processInput();
		if (grounded && hero.getState().equals(Hero.State.JUMP)) {
			hero.setState(Hero.State.IDLE);
		}
		hero.getAcceleration().y = GRAVITY;
		hero.getAcceleration().scl(delta);
		hero.getVelocity().add(hero.getAcceleration().x, hero.getAcceleration().y);
		checkCollisionWithTiles(delta);
		hero.getVelocity().x *= DAMP;
		if (hero.getVelocity().x > MAX_VEL) {
			hero.getVelocity().x = MAX_VEL;
		}
		if (hero.getVelocity().x < -MAX_VEL) {
			hero.getVelocity().x = -MAX_VEL;
		}
		hero.update(delta);
	}
	
	/** Collision checking **/
	private void checkCollisionWithTiles(float delta) {
		// scale velocity to frame units 
		hero.getVelocity().scl(delta);

		// Obtain the rectangle from the pool instead of instantiating it
		Rectangle heroRect = rectPool.obtain();
		// set the rectangle to hero's bounding box
		heroRect.set(hero.getBounds().x, hero.getBounds().y, hero.getBounds().width, hero.getBounds().height);

		// we first check the movement on the horizontal X axis
		int startX, endX;
		int startY = (int) hero.getBounds().y;
		int endY = (int) (hero.getBounds().y + hero.getBounds().height);
		// if hero is heading left then we check if he collides with the tile on his left
		// we check the tile on his right otherwise
		if (hero.getVelocity().x < 0) {
			startX = endX = (int) Math.floor(hero.getBounds().x + hero.getVelocity().x);
		} else {
			startX = endX = (int) Math.floor(hero.getBounds().x + hero.getBounds().width + hero.getVelocity().x);
		}

		// get the tile(s) hero can collide with
		populateCollidableTiles(startX, startY, endX, endY);

		// simulate hero's movement on the X
		heroRect.x += hero.getVelocity().x;

		// clear collision boxes in world
		world.getCollisionRects().clear();

		// if hero collides, make his horizontal velocity 0
		for (Tile tile : collidable) {
			if (tile == null) continue;
			if (heroRect.overlaps(tile.getBounds())) {
				hero.getVelocity().x = 0;
				world.getCollisionRects().add(tile.getBounds());
				break;
			}
		}

		// reset the x position of the collision box
		heroRect.x = hero.getPosition().x;

		// the same thing but on the vertical Y axis
		startX = (int) hero.getBounds().x;
		endX = (int) (hero.getBounds().x + hero.getBounds().width);
		if (hero.getVelocity().y < 0) {
			startY = endY = (int) Math.floor(hero.getBounds().y + hero.getVelocity().y);
		} else {
			startY = endY = (int) Math.floor(hero.getBounds().y + hero.getBounds().height + hero.getVelocity().y);
		}

		populateCollidableTiles(startX, startY, endX, endY);

		heroRect.y += hero.getVelocity().y;

		for (Tile tile : collidable) {
			if (tile == null) continue;
			if (heroRect.overlaps(tile.getBounds())) {
				if (hero.getVelocity().y < 0) {
					grounded = true;
				}
				hero.getVelocity().y = 0;
				world.getCollisionRects().add(tile.getBounds());
				break;
			}
		}
		// reset the collision box's position on Y
		heroRect.y = hero.getPosition().y;

		// update hero's position
		hero.getPosition().add(hero.getVelocity());
		hero.getBounds().x = hero.getPosition().x;
		hero.getBounds().y = hero.getPosition().y;

		// un-scale velocity (not in frame time)
		hero.getVelocity().scl(1 / delta);

	}
	
	/** populate the collidable array with the blocks found in the enclosing coordinates **/
	private void populateCollidableTiles(int startX, int startY, int endX, int endY) {
		collidable.clear();
		for (int x = startX; x <= endX; x++) {
			for (int y = startY; y <= endY; y++) {
				if (x >= 0 && x < world.getLevel().getWidth() && y >=0 && y < world.getLevel().getHeight()) {
					collidable.add(world.getLevel().get(x, y));
				}
			}
		}
	}

	/** Change hero's state and parameters based on input controls **/
	/** Change hero's state and parameters based on input controls **/
	private boolean processInput() {
		if (keys.get(Keys.JUMP)) {
			if (!hero.getState().equals(Hero.State.JUMP)) {
				jumpingPressed = true;
				jumpPressedTime = System.currentTimeMillis();
				hero.setState(Hero.State.JUMP);
				hero.getVelocity().y = MAX_JUMP_SPEED; 
				grounded = false;
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
