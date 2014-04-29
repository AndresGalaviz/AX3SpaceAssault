package com.me.spaceassault.resources;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Object {

	// character states
	public enum State {
		IDLE,
		WALK,
		DEAD,
		JUMP
	}
	
	//public static final float SPEED = 50f; // character velocity
	public final float SPEED;
	public final float JUMP_SPEED = 4f; // jumping speed
	//public final float SIZE = .8f; // character size
	public final float WIDTH;
	public final float HEIGHT;
	
	Vector2 pos = new Vector2(); 
	Vector2 accel = new Vector2(); 
	Vector2 vel = new Vector2(); 
	Rectangle bounds = new Rectangle();
	boolean dir_left = true;
	State state = State.IDLE;
	float stateTime = 0;
	protected int life;
	
	public Object(Vector2 position, float W, float H, float SPEED, int life) {
		this.pos = position;
		this.bounds.x = position.x;
		this.bounds.y = position.y;
		this.bounds.height = this.HEIGHT = H;
		this.bounds.width = this.WIDTH = W;
		this.SPEED = SPEED;
		this.life = life;
	}
	
	public Object(Vector2 position, float W, float H, float SPEED) {
		this(position, W, H, SPEED, 0);
	}
	
	public Object getObject() {
		return this;
	}
	
	/**
	 * Regresa la posicion del jugador
	 * @return un Vector2 con la posicion
	 */
	public Vector2 getPosition() {
		return pos;
	}
	
	/**
	 * Regresa los limites del jugador
	 * @return bounds
	 */
	public Rectangle getBounds() {
		return bounds;
	}
	
	/**
	 * Regresa el estado actual
	 * @return state
	 */
	public State getState() {
		return state;
	}
	
	/**
	 * Define un nuevo estado para el jugador
	 * @param newState
	 */
	public void setState(State newState) {
		this.state = newState;
	}
	
	/**
	 * Sirve para las animaciones
	 * @return
	 */
	public float getStateTime() {
		return stateTime;
	}
	
	/**
	 * Actualiza el tiempo de la animacion y posicion del personaje
	 * @param delta
	 */
	public void update(float delta) {
		stateTime += delta;
	}
	
	/**
	 * Establece la direccion a la que voltea el personaje
	 * @param dir
	 */
	public void setFacingLeft(boolean dir) {
		
		dir_left = dir;
	}
	
	/**
	 * Regresa la velocidad
	 * @return vel
	 */
	public Vector2 getVelocity() {
		return vel;
	}
	/**
	 * Regresa la aceleracion del salto
	 * @return accel
	 */
	public Vector2 getAcceleration() {
		return accel;
	}
	/**
	 * Regresa la direccion del personaje
	 * @return dir_left (boolean)
	 */
	public boolean isFacingLeft() {
		return dir_left;
	}
	
	public void setPosition(Vector2 position) {
		pos = position;
		this.bounds.setX(position.x);
		this.bounds.setY(position.y);
	}
	
	public int getLife() {
		return life;
	}
	
	public void setLife(int l) {
		life = l;
	}
	
}
