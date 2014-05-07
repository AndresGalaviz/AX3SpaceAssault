/*
 *Class Game
 *
 *@Author Sergio Cordero
 *@Matricula A01191167
 */
package com.neet.main;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.neet.managers.GameInputProcessor;
import com.neet.managers.GameKeys;
import com.neet.managers.GameStateManager;
import com.neet.managers.Jukebox;

public class Game implements ApplicationListener{

	public static int WIDTH;
	public static int HEIGHT;
	
	public static OrthographicCamera cam;
	
	private GameStateManager gsm;
	
	/* Metodo <I>create</I> de la clase <code>Game</code>
	* Crea el juego.
	*/
	public void create() {
		
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
		
		cam = new OrthographicCamera(WIDTH, HEIGHT);
		cam.translate(WIDTH/2, HEIGHT/2);
		cam.update();
		
		Gdx.input.setInputProcessor(
			new GameInputProcessor()
		);
		
		Jukebox.load("sounds/explode.ogg", "explode");
		Jukebox.load("sounds/extralife.ogg", "extralife");
		Jukebox.load("sounds/largesaucer.ogg", "largesaucer");
		Jukebox.load("sounds/pulsehigh.ogg", "pulsehigh");
		Jukebox.load("sounds/pulselow.ogg", "pulselow");
		Jukebox.load("sounds/smallsaucer.ogg", "smallsaucer");
		Jukebox.load("sounds/saucershoot.ogg", "saucershoot");
		Jukebox.load("sounds/shoot.ogg", "shoot");
		Jukebox.load("sounds/thruster.ogg", "thruster");
		
		
		gsm = new GameStateManager();
	}
	
	/* Metodo <I>render</I> de la clase <code>Game</code>
	* Hace un render.
	*/
	public void render() {
		
		//set screen to black
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.draw();
		
		GameKeys.update();
	}
	
	/* Metodo <I>resize</I> de la clase <code>Game</code>
	* Cambia el tamaño de la pantalla dependiendo de 
	* <code>width</code> y <code>height</code>
	*
	* @paramwidth valor para el nuevo ancho.
	*
	* @paramheight valor para la nueva altura.
	*/
	public void resize(int width, int height) {
		
	}
	
	/* Metodo <I>pausa</I> de la clase <code>Game</code>
	* Hace pausa.
	*/
	public void pause() {
		
	}
	
	/* Metodo <I>resume</I> de la clase <code>Game</code>
	* Quita la pausa.
	*/
	public void resume() {
		
	}
	
	/* Metodo <I>dispose</I> de la clase <code>Game</code>
	* Tira el juego.
	*/
	public void dispose() {
		
	}
	
}
