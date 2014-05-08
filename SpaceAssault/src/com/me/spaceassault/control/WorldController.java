package com.me.spaceassault.control;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.me.spaceassault.resources.BadGuy;
import com.me.spaceassault.resources.Bullet;
import com.me.spaceassault.resources.Hero;
import com.me.spaceassault.resources.HighScore;
import com.me.spaceassault.resources.Tile;
import com.me.spaceassault.screens.GameOverScreen;
import com.me.spaceassault.screens.GameScreen;
import com.me.spaceassault.screens.InstructionsScreen;
import com.me.spaceassault.screens.MenuScreen;
import com.me.spaceassault.world.World;

/**
 * Clase que controla el input durante la pantalla del juego
 * Maneja tambien las vidas y el score
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
	private static final float MAX_BADGUY_JUMP  = 9f;
	private static final float DAMP 			= 0.90f;
	private static final float MAX_VEL 			= 50f;
	
	private final float W, H;
	
	private World world;  // Mundo en el que el heroe se mueve
	private Hero hero;  // Instancia del heroe principal
	private long jumpPressedTime;
	private boolean jumpingPressed;
	private Array<BadGuy> badGuys;
	private static final float WIDTH = 10f;
	private Array<Bullet> bullets;
	private Array<Bullet> badBullets;
	private Array<Tile> collidable = new Array<Tile>();
	
	private Pool<Rectangle> rectPool = new Pool<Rectangle>() {
		@Override
		protected Rectangle newObject() {
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

    /**
     * Constructor de la clase
     * @param world es el mundo que se va a controlar
     * @param W ancho del mundo
     * @param H alto del mundo
     */
    public WorldController(World world, float W, float H) {
    	this.world = world;
    	this.hero = world.getHero();
    	this.badGuys = world.getBadGuys();
    	this.bullets = world.getBullets();
    	this.badBullets = world.getBadBullets();
    	this.W = W;
    	this.H = H;
    }
    /**
     * Se activa cuando se presiona la tecla izquierda
     */
	public void leftPressed() {
		keys.get(keys.put(Keys.LEFT, true));
	}
	/**
	 * Se activa cuando se presiona la tecla derecha
	 */
	public void rightPressed() {
		keys.get(keys.put(Keys.RIGHT, true));
	}

	/**
	 * Se activa cuando se presiona la tecla de brincar
	 */
	public void jumpPressed() {
		keys.get(keys.put(Keys.JUMP, true));
	}

	/**
	 * Se activa cuando se presiona la tecla de disparar
	 */
	public void firePressed() {
		keys.get(keys.put(Keys.FIRE, true));
	}

	/**
	 * Se activa cuando se libera la tecla izquierda
	 */
	public void leftReleased() {
		keys.get(keys.put(Keys.LEFT, false));
	}

	/**
	 * Se activa cuando se libera la tecla derecha
	 */
	public void rightReleased() {
		keys.get(keys.put(Keys.RIGHT, false));
	}

	/**
	 * Se activa cuando se libera la tecla de brinco
	 */
	public void jumpReleased() {
		keys.get(keys.put(Keys.JUMP, false));
	}

	/**
	 * Se activa cuando se libera la tecla de disparar
	 */
	public void fireReleased() {
		keys.get(keys.put(Keys.FIRE, false));
	}

	/**
	 * Se utiliza para revisar si se esta presionando la tecla izquierda
	 * @return el valor booleano de esta pregunta
	 */
	public boolean isLeftOn() {
		return keys.get(Keys.LEFT);
	}
	/**
	 * Se utiliza para revisar si se esta presionando la tecla derecha
	 * @return el valor booleano de esta pregunta
	 */
	public boolean isRightOn() {
		return keys.get(Keys.RIGHT);
	}
	/**
	 * Se utiliza para revisr si se esta presionando la tecla de disparo
	 * @return el valor booleano de esta pregunta
	 */
	public boolean isFireOn() {
		return keys.get(Keys.FIRE);
	}
	/**
	 * Se utiliza para revisar si se esta presionando la tecla para brncar
	 * @return el valor booleano de esta pregunta
	 */
	public boolean isJumpOn() {
		return keys.get(Keys.JUMP);
	}

	
	/** 
	 * The main update method 
	 **
	 */
	public void update(float delta) {
		processInput();
		
		if (hero.isGrounded() && hero.getState().equals(Hero.State.JUMP)) {
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
		
		//  Verifica que las balas no colisionen con las paredes
		for (Bullet bullet : bullets) {
			if (checkCollisionBulletTiles(bullet, delta)) {
				bullets.removeValue(bullet, true);
			} else {
				bullet.update(delta);
			}
		}
		
		for (Bullet badBullet : badBullets) {
			if (checkCollisionBulletTiles(badBullet, delta)) {
				badBullets.removeValue(badBullet, true);
			} else {
				badBullet.update(delta);
			}
		}
		
		//  Verifica las colisiones entre los enemigos y las balas
		for (Bullet bullet : bullets) {
			for (BadGuy badGuy : badGuys) {
				if (checkCollisionBulletBadGuy(bullet, badGuy)) {
					bullets.removeValue(bullet, true);
					
					badGuy.setLife(badGuy.getLife() - 1);
					if (badGuy.getLife() <= 0) {
						
						hero.addScore(badGuy.getStrength());
						System.out.println(badGuy.getStrength());
						badGuys.removeValue(badGuy, true);
					}
				}
			}
		}
		
		//  Verifica colisiones entre balas enemigas y heroes
		for (Bullet badBullet : badBullets) {
			if (checkCollisionBulletHero(badBullet, hero)) {
				hero.setLife(hero.getLife()-5*badBullet.getStrength());
				if(hero.getLife() <= 0) {
					keys.get(keys.put(Keys.LEFT, false));
					keys.get(keys.put(Keys.RIGHT, false));
					keys.get(keys.put(Keys.FIRE, false));
					keys.get(keys.put(Keys.JUMP, false));
					HighScore.setHighScore(hero.getScore());
					System.out.println(hero.getScore() + "-----" + HighScore.getHighScore());
					((Game) Gdx.app.getApplicationListener()).setScreen(new GameOverScreen());
					
				}
				badBullets.removeValue(badBullet, true);
			}
		}
		
		//  Verifica que no haya colision entre los enemigos y las paredes
		for (BadGuy badGuy : badGuys) {
			badGuy.getAcceleration().y = GRAVITY;
			badGuy.getAcceleration().scl(delta);
			badGuy.getVelocity().add(0, badGuy.getAcceleration().y);
			if (badGuy.isMoving()) {
				badGuyDirection(badGuy);
				checkCollisionBadGuyTiles(badGuy, delta);
				badGuy.shoot(badBullets, hero);
			} else {
				badGuy.startMoving(hero, W, H);
			}
			if (checkCollisionWithBadGuy(badGuy)) {
				hero.setLife(hero.getLife()-5*badGuy.getStrength());
				if(hero.getLife() <= 0) {
					keys.get(keys.put(Keys.LEFT, false));
					keys.get(keys.put(Keys.RIGHT, false));
					keys.get(keys.put(Keys.FIRE, false));
					keys.get(keys.put(Keys.JUMP, false));
					HighScore.setHighScore(hero.getScore());
					((Game) Gdx.app.getApplicationListener()).setScreen(new GameOverScreen());
					
				}
			}
		}
	}
	
	/** Collision checking **/
	
	/**
	 * Colision de Hero con Tiles
	 * @param delta
	 */
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

		// Verifica si existe overlap entre los cuadros con los que se puede chocar y el personaje principal
		for (Tile tile : collidable) {
			if (tile == null) continue;
			if (heroRect.overlaps(tile.getBounds())) {
				if (hero.getVelocity().y < 0) {
					hero.setGrounded(true);
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
	
	/**
	 * Colision de una bala con los tiles
	 * @param bullet bala
	 * @param delta
	 * @return
	 */
	private boolean checkCollisionBulletTiles(Bullet bullet, float delta) {
		boolean collides = false;
		
		// scale velocity to frame units 
		bullet.getVelocity().scl(delta);

		// Obtain the rectangle from the pool instead of instantiating it
		Rectangle bulletRect = rectPool.obtain();
		// set the rectangle to bullet's bounding box
		bulletRect.set(bullet.getBounds().x, bullet.getBounds().y, bullet.getBounds().width, bullet.getBounds().height);

		// we first check the movement on the horizontal X axis
		int startX, endX;
		int startY = (int) bullet.getBounds().y;
		int endY = (int) (bullet.getBounds().y + bullet.getBounds().height);
		// if bullet is heading left then we check if he collides with the tile on his left
		// we check the tile on his right otherwise
		if (bullet.getVelocity().x < 0) {
			startX = endX = (int) Math.floor(bullet.getBounds().x + bullet.getVelocity().x);
		} else {
			startX = endX = (int) Math.floor(bullet.getBounds().x + bullet.getBounds().width + bullet.getVelocity().x);
		}

		// get the tile(s) bullet can collide with
		populateCollidableTiles(startX, startY, endX, endY);

		// simulate bullet's movement on the X
		bulletRect.x += bullet.getVelocity().x;

		
		// clear collision boxes in world
		world.getCollisionRects().clear();

		// if bullet collides, make his horizontal velocity 0
		for (Tile tile : collidable) {
			if (tile == null) continue;
			if (bulletRect.overlaps(tile.getBounds())) {
				collides = true;
				world.getCollisionRects().add(tile.getBounds());
				break;
			}
		}
		
		

		// reset the x position of the collision box
		bulletRect.x = bullet.getPosition().x;
		
		bullet.getVelocity().scl(1 / delta);
		
		// update bullet's position
		bullet.getPosition().add(bullet.getVelocity());
		bullet.getBounds().x = bullet.getPosition().x;
	
		return collides;
	}
	
	
	
	
	/**
	 * Colision de una bala del personaje con un enemigo
	 * @param bullet
	 * @param badGuy
	 * @return
	 */
	private boolean checkCollisionBulletBadGuy(Bullet bullet, BadGuy badGuy) {
		return bullet.getBounds().overlaps(badGuy.getBounds());
	}
	
	private boolean checkCollisionBulletHero(Bullet badBullet, Hero hero) {
		return badBullet.getBounds().overlaps(hero.getBounds());
	}
	
	/**
	 * Colision de personaje con enemigos
	 * @param badGuy enemigo
	 * @return
	 */
	private boolean checkCollisionWithBadGuy(BadGuy badGuy) {
		return hero.getBounds().overlaps(badGuy.getBounds());
	}

	/**
	 * Colision de enemigos con los tiles
	 * @param badGuy
	 * @param delta
	 */
	private void checkCollisionBadGuyTiles(BadGuy badGuy, float delta) {
		// scale velocity to frame units 
		badGuy.getVelocity().scl(delta);
		
		// Obtain the rectangle from the pool instead of instantiating it
		Rectangle badGuyRect = rectPool.obtain();
		// set the rectangle to badGuy's bounding box
		badGuyRect.set(badGuy.getBounds().x, badGuy.getBounds().y, badGuy.getBounds().width, badGuy.getBounds().height);

		
		
		// we first check the movement on the horizontal X axis
		int startX, endX;
		int startY = (int) badGuy.getBounds().y;
		int endY = (int) (badGuy.getBounds().y + badGuy.getBounds().height);
		// if badGuy is heading left then we check if he collides with the tile on his left
		// we check the tile on his right otherwise
		if (badGuy.getVelocity().x < 0) {
			startX = endX = (int) Math.floor(badGuy.getBounds().x + badGuy.getVelocity().x);
		} else {
			startX = endX = (int) Math.floor(badGuy.getBounds().x + badGuy.getBounds().width + badGuy.getVelocity().x);
		}

		// get the tile(s) badGuy can collide with
		populateCollidableTiles(startX, startY, endX, endY);

		// simulate badGuy's movement on the X
		badGuyRect.x += badGuy.getVelocity().x;

		// clear collision boxes in world
		world.getCollisionRects().clear();

		boolean jump = false;
		
		// if badGuy collides, make his horizontal velocity 0
		for (Tile tile : collidable) {
			if (tile == null) continue;
			if (badGuyRect.overlaps(tile.getBounds())) {
				badGuy.getVelocity().x = 0;
				world.getCollisionRects().add(tile.getBounds());
				jump = true;
				break;
			}
		}

		// reset the x position of the collision box
		badGuyRect.x = badGuy.getPosition().x;
		
		if (!jump) {
			badGuy.move(badGuy.isFacingLeft(), delta);
		}
		
		if (jump && badGuy.isGrounded()) {
			badGuy.jump(delta);
			badGuy.setGrounded(false);
		}

		// the same thing but on the vertical Y axis
		startX = (int) badGuy.getBounds().x;
		endX = (int) (badGuy.getBounds().x + badGuy.getBounds().width);
		if (badGuy.getVelocity().y < 0) {
			startY = endY = (int) Math.floor(badGuy.getBounds().y + badGuy.getVelocity().y);
		} else {
			startY = endY = (int) Math.floor(badGuy.getBounds().y + badGuy.getBounds().height + badGuy.getVelocity().y);
		}

		populateCollidableTiles(startX, startY, endX, endY);

		badGuyRect.y += badGuy.getVelocity().y;

		badGuy.setGrounded(false);
		
//		jump = (badGuy.getVelocity().y < badGuy.getAcceleration().y && badGuy.isGrounded());
		for (Tile tile : collidable) {
			if (tile == null) continue;
			if (badGuyRect.overlaps(tile.getBounds())) {
//				jump = false;
				if (badGuy.getVelocity().y < 0) {
					badGuy.setGrounded(true);
				}
				badGuy.getVelocity().y = 0;
				world.getCollisionRects().add(tile.getBounds());
				break;
			}
		}
		
		
//		if (jump && badGuy.isGrounded()) {
//			System.out.println("Jump2");
//			badGuy.jump(delta);
//			badGuy.setGrounded(false);
//		}
		
		// reset the collision box's position on Y
		badGuyRect.y = badGuy.getPosition().y;

		// update badGuy's position
		badGuy.getPosition().add(badGuy.getVelocity());
		badGuy.getBounds().x = badGuy.getPosition().x;
		badGuy.getBounds().y = badGuy.getPosition().y;

		// un-scale velocity (not in frame time)
		badGuy.getVelocity().scl(1 / delta);
	}

	/**
	 * Cambia la direccion de los enemigos
	 * @param badGuy enemigo
	 */
	private void badGuyDirection(BadGuy badGuy) {
		if (badGuy.getPosition().x < hero.getPosition().x) {
			badGuy.getVelocity().x = BadGuy.getSpeed();
			badGuy.setFacingLeft(false);
		} 
		
		if (badGuy.getPosition().x > hero.getPosition().x) {
			badGuy.getVelocity().x = -BadGuy.getSpeed();
			badGuy.setFacingLeft(true);
		}
	}
	
	/**
	 * Populate the collidable array with the blocks found in the enclosing coordinates
	 * @param startX  Direccion inicio X
	 * @param startY  Direccion inicio Y
	 * @param endX  Direccion final X
	 * @param endY  Direccion final Y
	 */
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

	/**
	 * Change hero's state and parameters based on input controls
	 * @return
	 */
	private boolean processInput() {
		if (keys.get(Keys.JUMP)) {
			if (!hero.getState().equals(Hero.State.JUMP)) {
				jumpingPressed = true;
				jumpPressedTime = System.currentTimeMillis();
				hero.setState(Hero.State.JUMP);
				hero.getVelocity().y = MAX_JUMP_SPEED; 
				hero.setGrounded(false);
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
		if (keys.get(Keys.FIRE)) {
			bullets.add(new Bullet(hero, 1));
			fireReleased();
		}
		return false;
	}

}
