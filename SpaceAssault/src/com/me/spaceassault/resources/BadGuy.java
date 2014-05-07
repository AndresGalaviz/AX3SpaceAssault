package com.me.spaceassault.resources;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
/**
 * Clase para el control de los enemigos
 * @author AndresG
 *
 */
public class BadGuy extends Object{
	private static final float SPEED = 1.5f;
	private static final float JUMP_VEL = 9f;
	private static final long SHOOT_SLEEP = 400;
	private boolean moving;
	private int strength;
	private long lastShoot;
	
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
		lastShoot = 0;
	}
	/**
	 * Metodo para activar el movimiento
	 * @param moving
	 */
	public void setMoving(boolean moving) {
		this.moving = moving;
	}
	/**
	 * Metodo para verificar si se esta moviendo
	 * @return el valor de esta variable
	 */
	public boolean isMoving() {
		return moving;
	}
	/**
	 * Metodo para obtener la fuerza de los enemigos
	 * @return
	 */
	public int getStrength() {
		return strength;
	}
	/**
	 * metodo para activar el movimiento
	 * @param hero el heroe principal
	 * @param w ancho
	 * @param h alto
	 */
	public void startMoving(Hero hero, float w, float h) {
		moving = (getPosition().x < hero.getPosition().x + w/2 && 
				getPosition().x > hero.getPosition().x - w/2 - getBounds().width && 
				getPosition().y < hero.getPosition().y + h/2 && 
				getPosition().y > hero.getPosition().y - h/2 - getBounds().height);
	}
	
	/**
	 * Hacer que el enemigo brinque
	 * @param delta
	 */
	public void jump (float delta) {
		getVelocity().scl(1/delta);
		getVelocity().y = JUMP_VEL;
		getVelocity().scl(delta);
	}
	/**
	 * Hacer que el enemigo se mueva
	 * @param left
	 * @param delta
	 */
	public void move (boolean left, float delta) {
		getVelocity().scl(1/delta);
		getVelocity().x = (left ? -1 : 1)*SPEED;
		getVelocity().scl(delta);
	}
	
	/**
	 * Metodo para obtener el valor de la velocidad
	 * @return
	 */
	public static float getSpeed() {
		return SPEED;
	}
	
	public void shoot(Array<Bullet> badBullets, Hero hero) {
		if (hero.getPosition().y < this.getPosition().y + this.HEIGHT && 
				hero.getPosition().y + hero.HEIGHT > this.getPosition().y) {
			if (System.currentTimeMillis() - lastShoot > SHOOT_SLEEP) {
				lastShoot = System.currentTimeMillis();
				badBullets.add(new Bullet(this, strength));
		}
		}
	}
	
}
