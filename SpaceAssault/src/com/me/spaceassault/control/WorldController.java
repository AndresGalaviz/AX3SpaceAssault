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
	private static final float MAX_VEL 			= 4f;
	
	private World world;
	private Hero hero;
	private long jumpPressedTime;
	private boolean jumpingPressed;
	private static final float WIDTH = 10f;
	private Array<Tile> collidable = new Array<Tile>();
	public boolean grounded = true;
	
	private Pool<Rectangle> rectPool = new Pool<Rectangle>() {
		@Override
		protected Rectangle newObject () {
			return new Rectangle();
		}
	};
	
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
	
	private void checkCollisionWithTiles(float delta) {
		hero.getVelocity().scl(delta);
		Rectangle heroRect = rectPool.obtain();
		heroRect.set(hero.getBounds().x, hero.getBounds().y, hero.getBounds().width, hero.getBounds().height);
		int startX, endX;
		int startY = (int) hero.getBounds().y;
		int endY = (int) (hero.getBounds().y + hero.getBounds().height);
		if (hero.getVelocity().x < 0) {
			startX = endX = (int) Math.floor(hero.getBounds().x + hero.getVelocity().x);
		} else {
			startX = endX = (int) Math.floor(hero.getBounds().x + hero.getBounds().width + hero.getVelocity().x);
		}
		populateCollidableTiles(startX, startY, endX, endY);
		heroRect.x += hero.getVelocity().x;
		world.getCollisionRects().clear();
		for (Tile tile : collidable) {
			if (tile == null) continue;
			if (heroRect.overlaps(tile.getBounds())) {
				hero.getVelocity().x = 0;
				world.getCollisionRects().add(tile.getBounds());
				break;
			}
		}
		heroRect.x = hero.getPosition().x;
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
		heroRect.y = hero.getPosition().y;
		hero.getPosition().add(hero.getVelocity());
		hero.getBounds().x = hero.getPosition().x;
		hero.getBounds().y = hero.getPosition().y;
		hero.getVelocity().scl(1 / delta);
	}
	
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
