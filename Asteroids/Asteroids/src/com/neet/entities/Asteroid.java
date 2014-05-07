/*
 *Class Asteroid
 *
 *@Author Sergio Cordero
 *@Matricula A01191167
 */
package com.neet.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;

public class Asteroid extends SpaceObject{
	
	private int type;
	public static final int SMALL = 0;
	public static final int MEDIUM = 1;
	public static final int LARGE = 2;
	
	private int numPoints;
	private float[] dists;
	
	private int score;
	
	private boolean remove;
	
	/*
	 * Metodo <I>constructor</I> de la clase <code>Asteroid</code>
	 *
	 * @paramx tipo de dato <code>float</code> que define la posicion
	 * en x del objeto.
	 *
	 * @paramy tipo de dato <code>float</code> que define la posicion
	 * en y del objeto.
	 *
	 * @paramtype tipo de dato <code>int</code> que define el tipo de
	 * asteroide.
	 */
	public Asteroid(float x, float y, int type) {
		this.x = x;
		this.y = y;
		this.type = type;
		
		if(type == SMALL) {
			numPoints  = 8;
			width = height = 12;
			speed = MathUtils.random(70, 100);
			score = 100;
		}
		
		if(type == MEDIUM) {
			numPoints  = 10;
			width = height = 20;
			speed = MathUtils.random(50, 60);
			score = 50;
		}
		
		if(type == LARGE) {
			numPoints  = 12;
			width = height = 40;
			speed = MathUtils.random(20, 30);
			score = 20;
		}
		
		rotationSpeed = MathUtils.random(-1, 1);
		
		radians = MathUtils.random(2 * 3.1416f);
		dx = MathUtils.cos(radians) * speed;
		dy = MathUtils.sin(radians) * speed;
		
		shapex = new float[numPoints];
		shapey = new float[numPoints];
		dists = new float[numPoints];
		
		int radius = width / 2;
		for(int i = 0; i < numPoints; i++) {
			dists[i] = MathUtils.random(radius / 2, radius);
		}
		
		setShape();
	}
	
	/*
	 * Metodo <I>setShape</I> de la clase <code>Asteroid</code>.
	 * Define la forma del asteroide.
	 */
	private void setShape() {
		float angle = 0;
		for(int i = 0; i < numPoints; i++) {
			shapex[i] = x + MathUtils.cos(angle + radians) * dists[i];
			shapey[i] = y + MathUtils.sin(angle + radians) * dists[i];
			angle += 2 * 3.1416f / numPoints;
		}
	}
	
	/*
	 *  Metodo <I>getType</I> de la clase <code>Asteroid</code>. 
	 *  Regresa el tipo de asteroide.
	 */
	public int getType() { return type; }

	/*
	* Metodo <I>shouldRemove()</I> de la clase <code>Asteroid</code>.
	* Regresa un tipo de dato <code>boolean</code> que dice si se 
	* debe de borrar el objeto.
	*/
	public boolean shouldRemove() { return remove; }

	/*
	 *  Metodo <I>getScore</I> de la clase <code>Asteroid</code>. 
	 *  Regresa el puntaje que da el asteroide.
	 */
	public int getScore() { return score; }
	
	/*
	 * Metodo <I>update</I> de la clase <code>Asteroide</code>.
	 * Actualiza el objeto dependiendo del valor de dt que reciba.
	 * 
	 * @paramdt tipo de dato <code>float</code> que modifica la posicion
	 * del objeto dependiendo de dt.
	 */
	public void update(float dt) {
		
		x += dx * dt;
		y += dy * dt;
		
		radians += rotationSpeed * dt;
		setShape();
		
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
		sr.setColor(.66f, .42f, .23f, 1);
		sr.begin(ShapeType.Line);
		for(int i = 0, j = shapex.length - 1;
			i < shapex.length;
			j = i++){
			sr.line(shapex[i], shapey[i], shapex[j], shapey[j]);
		}
		sr.end();
	}

}
