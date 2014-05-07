/*
 *Class Player
 *
 *@Author Sergio Cordero
 *@Matricula A01191167
 */
package com.neet.entities;

import java.awt.geom.Line2D;

import com.neet.gamestates.PlayState;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.neet.main.Game;
import com.neet.managers.Jukebox;

public class Player extends SpaceObject{

	
	private final int MAX_BULLETS = 4;
	private ArrayList<Bullet> bullets;
	
	private float[] flamex;
	private float[] flamey;
	private float color;
	
	private boolean left;
	private boolean right;
	private boolean up;

	private float maxSpeed;
	private float acceleration;
	private float friction;
	private float acceleratingTimer;
	
	private boolean hit;
	private boolean dead;
	
	private float hitTimer;
	private float hitTime;
	private Line2D.Float[] hitLines;
	private Point2D.Float[] hitLinesVector;
	
	private long score;
	private int extraLives;

	/*
	 * Metodo <I>constructor</I> de la clase <code>Player</code>
	 *
	 * @parambullets tipo de dato <code>ArrayList<Bullet></code> que
	 * es el arreglo de balas del jugador.
	 */
	public Player(ArrayList<Bullet> bullets) {
		
		this.bullets = bullets;
		
		x = Game.WIDTH / 2;
		y = Game.HEIGHT / 2;
		
		maxSpeed = 1000;
		acceleration = 200;
		friction = 10;
		
		shapex = new float[4];
		shapey = new float[4];
		flamex = new float[3];
		flamey = new float[3];
		color = 1;
		
		radians = 3.1416f / 2;
		rotationSpeed = 3;
		
		hit = false;
		hitTimer = 0;
		hitTime = 2;
		
		score = 0;
		extraLives = 3;
		
	}
	
	/* Metodo <I>hit</I> de la clase <code>Player</code>
	* Usado cuando el jugador es golpeado y destruido.
	*/
	public void hit() {
		
		if(hit) return;
		Jukebox.stop("thruster");
		
		hit = true;
		dx = dy = 0;
		left = right = up = false;
		
		hitLines = new Line2D.Float[4];
		for(int i = 0, j = hitLines.length - 1;
				i < hitLines.length;
				j = i++) {
			hitLines[i] = new Line2D.Float(shapex[i], shapey[i], shapex[j], shapey[j]);
		}
		
		hitLinesVector = new Point2D.Float[4];
		hitLinesVector[0] = new Point2D.Float(
			MathUtils.cos(radians + 1.5f),
			MathUtils.sin(radians + 1.5f)
		);
		hitLinesVector[1] = new Point2D.Float(
				MathUtils.cos(radians - 1.5f),
				MathUtils.sin(radians - 1.5f)
			);
		hitLinesVector[2] = new Point2D.Float(
				MathUtils.cos(radians - 2.8f),
				MathUtils.sin(radians - 2.8f)
			);
		hitLinesVector[3] = new Point2D.Float(
				MathUtils.cos(radians + 2.8f),
				MathUtils.sin(radians + 2.8f)
			);
	}
	
	/*
	 * Metodo <I>setFlame</I> de la clase <code>Player</code>.
	 * Define la forma del fuego.
	 */
	private void setFlame() {
		flamex[0] = x + MathUtils.cos(radians - 5 * 3.1416f / 6) * 5;
		flamey[0] = y + MathUtils.sin(radians - 5 * 3.1416f / 6) * 5;
		
		flamex[1] = x + MathUtils.cos(radians - 3.1416f) * 
				(6 + acceleratingTimer * 50);
		flamey[1] = y + MathUtils.sin(radians - 3.1416f) * 
				(6 + acceleratingTimer * 50);
		
		flamex[2] = x + MathUtils.cos(radians + 5 * 3.1416f / 6) * 5;
		flamey[2] = y + MathUtils.sin(radians + 5 * 3.1416f / 6) * 5;
	}

	/*
	 * Metodo <I>setShape</I> de la clase <code>Player</code>.
	 * Define la forma del jugador.
	 */
	private void setShape() {
		shapex[0] = x + MathUtils.cos(radians) * 8;
		shapey[0] = y + MathUtils.sin(radians) * 8;
		
		shapex[1] = x + MathUtils.cos(radians - 4 * 3.1416f / 5) * 8;
		shapey[1] = y + MathUtils.sin(radians - 4 * 3.1416f / 5) * 8;
		
		shapex[2] = x + MathUtils.cos(radians + 3.1416f) * 5;
		shapey[2] = y + MathUtils.sin(radians + 3.1416f) * 5;
		
		shapex[3] = x + MathUtils.cos(radians + 4 * 3.1416f / 5) * 8;
		shapey[3] = y + MathUtils.sin(radians + 4 * 3.1416f / 5) * 8;
	}
	
	/*
	 * Metodo <I>setLeft</I> de la clase <code>Player</code>.
	 * Define si la tecla de la izquierda esta presionada.
	 * 
	 * @paramdb tipo de dato <code>boolean</code> que define
	 * si la tecla esta presionada.
	 */
	public void setLeft(boolean b) { left = b; }

	/*
	 * Metodo <I>setRight</I> de la clase <code>Player</code>.
	 * Define si la tecla de la derecha esta presionada.
	 * 
	 * @paramdb tipo de dato <code>boolean</code> que define
	 * si la tecla esta presionada.
	 */
	public void setRight(boolean b) { right = b; }

	/*
	 * Metodo <I>setUp</I> de la clase <code>Player</code>.
	 * Define si la tecla de arriba esta presionada.
	 * 
	 * @paramdb tipo de dato <code>boolean</code> que define
	 * si la tecla esta presionada.
	 */
	public void setUp(boolean b) { 
		if(b && !up && !hit) {
			Jukebox.loop("thruster");
		}
		else if(!b) {
			Jukebox.stop("thruster");
		}
		up = b; }
	
	/*
	 * Metodo <I>setPosition</I> de la clase <code>spaceObject</code>.
	 * Cambia la posicion del objeto dependiendo de los valores de 
	 * x y y que reciba.
	 * 
	 * @paramx tipo de dato <code>float</code> que modifica la posicion
	 * del objeto en x.
	 * 
	 * @paramy tipo de dato <code>float</code> que modifica la posicion
	 * del objeto en y.
	 */
	public void setPosition(float x, float y) {
		super.setPosition(x, y);
		setShape();
	}
	
	/*
	* Metodo <I>isHit()</I> de la clase <code>Player</code>.
	* Regresa un tipo de dato <code>boolean</code> que dice si
	* el jugador fue golpeado.
	*/
	public boolean isHit() { return hit; }

	/*
	* Metodo <I>isDead()</I> de la clase <code>Player</code>.
	* Regresa un tipo de dato <code>boolean</code> que dice si el
	* jugador esta muerto.
	*/
	public boolean isDead() {return dead; }
	
	/*
	* Metodo <I>reset</I> de la clase <code>Player</code>.
	* Reinicia todos los valores de posicion del jugador
	* y lo vuelve a dibujar.
	*/
	public void reset() {
		color = 1;
		x = Game.WIDTH / 2;
		y = Game.HEIGHT / 2;
		setShape();
		hit = dead = false;
	}

	/*
	 *  Metodo <I>getScore</I> de la clase <code>Player</code>. 
	 *  Regresa el puntaje acumulado.
	 */
	public long getScore() { return score; }

	/*
	 *  Metodo <I>getLives</I> de la clase <code>Player</code>. 
	 *  Regresa la cantidad de vidas restantes.
	 */
	public int getLives() { return extraLives; }
	
	/*
	* Metodo <I>loseLife</I> de la clase <code>Player</code>.
	* Reduce la vida del jugador en uno.
	*/
	public void loseLife() { extraLives--; }

	/*
	 * Metodo <I>incrementScore</I> de la clase <code>Player</code>.
	 * Incrementa el puntaje dependiendo del numero l que reciba.
	 * 
	 * @paraml tipo de dato <code>long</code> con el cual se 
	 * incrementa el puntaje.
	 */
	public void incrementScore(long l) { score += l; }
	
	/* Metodo <I>incrementScore</I> de la clase <code>Player</code>
	* Dispara una bala del personaje en la direccion en que este 
	* apuntando el jugador.
	*/
	public void shoot() {
		if(this.hit) return;
		if(bullets.size() == MAX_BULLETS) return;
		Jukebox.play("shoot");
		bullets.add(new Bullet(x, y, radians));		
	}
	
	/*
	 * Metodo <I>update</I> de la clase <code>Player</code>.
	 * Actualiza el objeto dependiendo del valor de dt que reciba.
	 * 
	 * @paramdt tipo de dato <code>float</code> que modifica la posicion
	 * del objeto dependiendo de dt.
	 */
	public void update(float dt) {
		
		// check if hit
		if(hit) {
			hitTimer += dt;
			if(hitTimer > hitTime) {
				dead = true;
				hitTimer = 0;
			}
			for(int i = 0; i < hitLines.length; i++) {
				hitLines[i].setLine(
					hitLines[i].x1 + hitLinesVector[i].x * 10 * dt,
					hitLines[i].y1 + hitLinesVector[i].y * 10 * dt,
					hitLines[i].x2 + hitLinesVector[i].x * 10 * dt,
					hitLines[i].y2 + hitLinesVector[i].y * 10 * dt
				);
			}
			return;
		}
		
		//turning
		if(left){
			radians += rotationSpeed * dt;
		}
		if(right){
			radians -= rotationSpeed * dt;
		}
		
		//accelerating
		if(up){
			dx += MathUtils.cos(radians) * acceleration * dt;
			dy += MathUtils.sin(radians) * acceleration * dt;
			acceleratingTimer += dt;
			if(acceleratingTimer > 0.1f) {
				acceleratingTimer = 0;
			}
		}
		else {
			acceleratingTimer = 0;
		}
		
		//friction
		float vec = (float) Math.sqrt(dx * dx + dy * dy);
		if(vec > 0) {
			dx -= (dx / vec) * friction * dt;
			dy -= (dy / vec) * friction * dt;
		}
		if(vec > maxSpeed) {
			dx = (dx / vec) * maxSpeed;
			dy = (dy / vec) * maxSpeed;
		}
		
		// set position
		x += dx * dt;
		y += dy * dt;
		
		// set shape
		setShape();
		
		// set flame
		if(up) {
			setFlame();
		}
		
		// screen wrap
		wrap();
		
		
	}
	
	/*
	 * Metodo <I>draw</I> de la clase <code>Asteroide</code>.
	 * Pinta el objeto usando ShapeRenderer sr
	 * 
	 * @paramsr tipo de dato <code>ShapeRenderer</code> que es
	 * lo que se pinta.
	 */
	public void draw(ShapeRenderer sr) {
		
		sr.setColor(0, 1, 1, 1);
		
		sr.begin(ShapeType.Line);
		
		// check if hit
		if(hit) {
			sr.setColor(0, color -= .01f, color -= .01f, 1);
			for(int i = 0; i < hitLines.length; i++) {
				sr.line(
						hitLines[i].x1,
						hitLines[i].y1,
						hitLines[i].x2,
						hitLines[i].y2
				);
			}
			sr.end();
			return;
		}
		
		//draw ship
		for(int i = 0, j = shapex.length - 1;
			i < shapex.length;
			j = i++){
			sr.line(shapex[i], shapey[i], shapex[j], shapey[j]);
		}
		//draw flame
		if(up) {
			for(int i = 0, j = flamex.length - 1;
					i < flamex.length;
					j = i++){
					sr.line(flamex[i], flamey[i], flamex[j], flamey[j]);
				}
		}
				
				
		
		sr.end();
	}

}
