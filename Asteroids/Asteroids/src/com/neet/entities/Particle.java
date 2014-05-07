/*
 *Class Particle
 *
 *@Author Sergio Cordero
 *@Matricula A01191167
 */
package com.neet.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;

public class Particle extends SpaceObject{
	
	private float timer;
	private float time;
	private boolean remove;
	
	/*
	 * Metodo <I>constructor</I> de la clase <code>Particle</code>
	 *
	 * @paramx tipo de dato <code>float</code> que define la posicion
	 * en x del objeto.
	 *
	 * @paramy tipo de dato <code>float</code> que define la posicion
	 * en y del objeto.
	 */
	public Particle(float x, float y) {
		this.x = x;
		this.y = y;
		width = height = 2;
		
		speed = MathUtils.random(20, 70);
		radians = MathUtils.random(2 * 3.1416f);
		dx = MathUtils.cos(radians) * speed;
		dy = MathUtils.sin(radians) * speed;
		
		timer = 0;
		time = 1;
	}
	
	/*
	* Metodo <I>shouldRemove()</I> de la clase <code>Particle</code>.
	* Regresa un tipo de dato <code>boolean</code> que dice si se 
	* debe de borrar el objeto.
	*/
	public boolean shouldRemove() { return remove; }
	
	/*
	 * Metodo <I>update</I> de la clase <code>Particle</code>.
	 * Actualiza el objeto dependiendo del valor de dt que reciba.
	 * 
	 * @paramdt tipo de dato <code>float</code> que modifica la posicion
	 * del objeto dependiendo de dt.
	 */
	public void update(float dt) {
		
		x += dx * dt;
		y += dy * dt;
		
		timer += dt;
		if(timer > time) {
			remove = true;
		}
		
	}
	
	/*
	 * Metodo <I>draw</I> de la clase <code>Asteroide</code>.
	 * Pinta el objeto usando ShapeRenderer sr
	 * 
	 * @paramsr tipo de dato <code>ShapeRenderer</code> que es
	 * lo que se pinta.
	 */
	public void draw(ShapeRenderer sr) {
		sr.setColor(.38f, .29f, .09f, 1);
		sr.begin(ShapeType.Filled);
		sr.circle(x - width / 2, y - height / 2, width / 2);
		sr.end();
	}

}
