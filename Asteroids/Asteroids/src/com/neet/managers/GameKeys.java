/*
 *Class GameKeys
 *
 *@Author Sergio Cordero
 *@Matricula A01191167
 */
package com.neet.managers;

public class GameKeys {

	private static boolean[] keys;
	private static boolean[] pkeys;
	
	private static final int NUM_KEYS = 8;
	public static final int UP = 0;
	public static final int LEFT = 1;
	public static final int DOWN = 2;
	public static final int RIGHT = 3;
	public static final int ENTER = 4;
	public static final int ESCAPE = 5;
	public static final int SPACE = 6;
	public static final int SHIFT = 7;
	
	static {
		keys = new boolean[NUM_KEYS];
		pkeys = new boolean[NUM_KEYS];
	}
	
	/*
	 * Metodo <I>update</I> de la clase <code>GameKeys</code>.
	 * Iguala el arreglo <code>pkeys</code> para poder usar
	 * keyPressed.
	 */
	public static void update() {
		for(int i = 0; i < NUM_KEYS; i++) {
			pkeys[i] = keys[i];
		}
	}
	
	/* Metodo <I>setKey</I> de la clase <code>GameKeys</code>
	* Cambia el estado de la tecla <code>k</code> a lo que diga
	* <code>b</code>.
	*
	* @paramk es el numero de la tecla por cambiar.
	*
	* @paramb es el estado al cual cambiar la tecla.
	*/
	public static void setKey(int k, boolean b) {
		keys[k] = b;
	}
	
	/* Metodo <I>isDown</I> de la clase <code>GameKeys</code>
	* Regresa true si la tecla <code>k</code> esta abajo.
	*
	* @paramk es el numero de la tecla por revisar.
	*/
	public static boolean isDown(int k) {
		return keys[k];
	}
	
	/* Metodo <I>isPressed</I> de la clase <code>GameKeys</code>
	* Regresa true si la tecla <code>k</code> esta presionada.
	*
	* @paramk es el numero de la tecla por revisar.
	*/
	public static boolean isPressed(int k){
		return keys[k] && !pkeys[k];
	}
	
	
}
