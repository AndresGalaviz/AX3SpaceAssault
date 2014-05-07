/*
 *Class Bullet
 *
 *@Author Sergio Cordero
 *@Matricula A01191167
 */
package com.neet.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;

public class Bullet extends SpaceObject{

	private float lifeTime;
	private float lifeTimer;
	
	private boolean remove;
	
	/*
	 * Metodo <I>constructor</I> de la clase <code>Bullet</code>
	 *
	 * @paramx tipo de dato <code>float</code> que define la posicion
	 * en x del objeto.
	 *
	 * @paramy tipo de dato <code>float</code> que define la posicion
	 * en y del objeto.
	 *
	 * @paramradians tipo de dato <code>float</code> que define la
	 * direccion de la bala.
	 */
	public Bullet(float x, float y, float radians) {
		this.x = x;
		this.y = y;
		this.radians = radians;
		
		float speed = 350;
		dx = MathUtils.cos(radians) * speed;
		dy = MathUtils.sin(radians) * speed;
		
		width = height = 2;
		
		lifeTimer = 0;
		lifeTime = 1;
	}
	
	/*
	* Metodo <I>shouldRemove()</I> de la clase <code>Bullet</code>.
	* Regresa un tipo de dato <code>boolean</code> que dice si se 
	* debe de borrar el objeto.
	*/
	public boolean shouldRemove() { return remove; }
	
	/*
	 * Metodo <I>update</I> de la clase <code>Bullet</code>.
	 * Actualiza el objeto dependiendo del valor de dt que reciba.
	 * 
	 * @paramdt tipo de dato <code>float</code> que modifica la posicion
	 * del objeto dependiendo de dt.
	 */
	public void update(float dt) {
		
		x += dx * dt;
		y += dy * dt;
		
		lifeTimer += dt;
		if(lifeTimer > lifeTime) {
			remove = true;
		}
		
		//screen wrap
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
	    
		sr.setColor(1, 0, 0, 1);
		
		sr.begin(ShapeType.Filled);
		
		sr.circle(x - width / 2, y - height / 2, 2);
		
		sr.end();
	}
	
}
