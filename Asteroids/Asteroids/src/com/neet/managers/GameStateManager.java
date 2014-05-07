/*
 *Class Jukebox
 *
 *@Author Sergio Cordero
 *@Matricula A01191167
 */
package com.neet.managers;

import com.neet.gamestates.GameState;
import com.neet.gamestates.PlayState;

public class GameStateManager {

	// current game state
	private GameState gameState;
	
	public static final int MENU = 0;
	public static final int PLAY = 1;
	
	/*
	 * Metodo <I>constructor</I> de la clase <code>GameStateManager</code>.
	 * Inicia el estado PLAY.
	 */
	public GameStateManager() {
		setState(PLAY);
	}
	
	/*
	 * Metodo <I>setState</I> de la clase <code>GameStateManager</code>.
	 * Cambia el estado dependiendo de lo recibido en <code>state</code>.
	 *
	 * @paramstate da el nombre del estado por poner.
	 */
	public void setState(int state) {
		if(gameState != null) gameState.dispose();
		if(state == MENU) {
			//switch to menu state
		}
		if(state == PLAY) {
			gameState = new PlayState(this);
		}
	}
	
	/*
	 * Metodo <I>update</I> de la clase <code>GameStateManager</code>.
	 * Actualiza el objeto dependiendo del valor de dt que reciba.
	 * 
	 * @paramdt tipo de dato <code>float</code> que modifica la posicion
	 * del objeto dependiendo de dt.
	 */
	public void update(float dt) {
		gameState.update(dt);
	}
	
	/*
	 * Metodo <I>draw</I> de la clase <code>GameStateManager</code>.
	 * Pinta el objeto usando ShapeRenderer sr
	 * 
	 * @paramsr tipo de dato <code>ShapeRenderer</code> que es
	 * lo que se pinta.
	 */
	public void draw() {
		gameState.draw();
	}
	
	
}
