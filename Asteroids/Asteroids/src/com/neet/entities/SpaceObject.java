/*
 *Class SpaceObject
 *
 *@Author Sergio Cordero
 *@Matricula A01191167
 */
package com.neet.entities;

import com.neet.main.Game;

public class SpaceObject {

	protected float x;
	protected float y;
	
	protected float dx;
	protected float dy;
	
	protected float radians;
	protected float speed;
	protected float rotationSpeed;
	
	protected int width;
	protected int height;
	
	protected float[] shapex;
	protected float[] shapey;
	
	/*
	 *  Metodo <I>getX</I> de la clase <code>spaceObject</code>. 
	 *  Regresa el valor del objeto en x.
	 */
	public float getX() { return x; }
	
	/*
	 *  Metodo <I>getY</I> de la clase <code>spaceObject</code>. 
	 *  Regresa el valor del objeto en x.
	 */
	public float getY() { return y; }
	
	/*
	 *  Metodo <I>getShapex</I> de la clase <code>spaceObject</code>. 
	 *  Regresa el arreglo de los diferentes valores de x para los
	 *  puntos que dibujan el objeto.
	 */
	public float[] getShapex() { return shapex; }
	
	/*
	 *  Metodo <I>getShapey</I> de la clase <code>spaceObject</code>. 
	 *  Regresa el arreglo de los diferentes valores de y para los
	 *  puntos que dibujan el objeto.
	 */
	public float[] getShapey() { return shapey; }
	
	/*
	 * Metodo <I>setPosition</I> de la clase <code>spaceObject</code>.
	 * Cambia la posicion del objeto dependiendo de los valores de 
	 * x y y que reciba.
	 * 
	 * @paramx tipo de dato <code>float</code> que modifica la posicion
	 * del objeto en x.
	 * 
	 * * @paramy tipo de dato <code>float</code> que modifica la posicion
	 * del objeto en y.
	 */
	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	/*
	 * Metodo <I>intersects</I> de la clase <code>spaceObject</code>.
	 * Regresa verdadero si el objeto recibido intersecta con el 
	 * primero.
	 * 
	 * @paramother tipo de dato <code>SpaceObject</code> con el cual se 
	 * busca interseccion.
	 */
	public boolean intersects(SpaceObject other) {
		float[] sx = other.getShapex();
		float[] sy = other.getShapey();
		for(int i = 0; i < sx.length; i++) {
			if(contains(sx[i], sy[i])) {
				return true;
			}
		}
		return false;
	}
	
	/*
	 * Metodo <I>contains</I> de la clase <code>spaceObject</code>.
	 * Regresa verdadero si el objeto tiene el punto en las coordenadas
	 * de x y y.
	 * 
	 * @paramx tipo de dato <code>float</code> con el cual se busca el
	 * punto en la coordenada x.
	 * 
	 * @paramy tipo de dato <code>float</code> con el cual se busca el
	 * punto en la coordenada y.
	 */
	public boolean contains(float x, float y) {
		boolean b = false;
		for(int i = 0, j = shapex.length - 1;
				i < shapex.length; 
				j = i++) {
			if((shapey[i] > y) != (shapey[j] > y) &&
					(x < (shapex[j] - shapex[i]) *
					(y - shapey[i]) / (shapey[j] - shapey[i])
					+ shapex[i])) {
				b = !b;
			}
		}
		return b;
	}
	
	/*
	 * Metodo <I>wrap</I> de la clase <code>spaceObject</code>.
	 * Hace que al salir de la pantalla vuelva a aprecer el
	 * objeto del otro lado.
	 */
	protected void wrap() {
		if(x < 0) x = Game.WIDTH;
		if(x > Game.WIDTH) x = 0;
		if(y < 0) y = Game.HEIGHT;
		if(y > Game.HEIGHT) y = 0; 
	}
	
}
