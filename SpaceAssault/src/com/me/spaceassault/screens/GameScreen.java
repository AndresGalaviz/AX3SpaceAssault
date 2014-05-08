package com.me.spaceassault.screens;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.me.spaceassault.world.World;
import com.me.spaceassault.world.WorldRenderer;
import com.me.spaceassault.control.WorldController;

/**
 * Metodo principal para el manejo de los inputs en el sistema
 * 
 * @author AndresG
 *
 */
public class GameScreen implements Screen, InputProcessor {

	private World world;
	private WorldRenderer renderer;
	private WorldController controller;
	private Music music = Gdx.audio.newMusic(Gdx.files.internal("data/music.mp3"));;
	private int width, height;
	private long delay = 3000, startTime;
	private boolean started = false;
	
	public GameScreen() {
		world = new World();
		renderer = new WorldRenderer(world, false);
		controller = new WorldController(world, renderer.getCameraWidth(), renderer.getCameraHeight());
		Gdx.input.setInputProcessor(this);
		startTime = System.currentTimeMillis();
		
	}

    /**
     * Metodo <I>render</I> de la clase <code>GameScreen</code>, En este
     * metodo se pintan los objetos y se actualizan variables que involucran
     * tiempo.
     * @paramdelta tipo de dato <code>float</code> es el valor utilizado para
     * manejar la diferencia de tiempo
     */
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if (started) {
			controller.update(delta);
		} else {
			started = (System.currentTimeMillis() - startTime > delay);
		}
		renderer.render();
	}

    /**
     * Metodo <I>resize</I> de la clase <code>GameScreen</code>, En este
     * metodo se redefine el tamano de la pantalla
     *
     * @paramwidth tipo de dato <code>Entero</code> es el valor utilizado para
     * manejar el tamano en x.
     * @paramheight tipo de dato <code>Entero</code> es el valor utilizado para
     * manejar el tamano en y.
     */
	@Override
	public void resize(int width, int height) {
		renderer.setSize(width, height);
		this.width = width;
		this.height = height;

	}

    /**
     * Metodo <I>show</I> de la clase <code>GameScreen</code>, En este
     * metodo se definen los actores de la clase
     *
     */
	@Override
	public void show() {


		// TODO Auto-generated method stub
	}

    /**
     * Metodo <I>hide</I> de la clase <code>GameScreen</code>, En este
     * metodo se minimiza la pantalla
     */
	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
	}
	
	/**
     * Metodo <I>pause</I> de la clase <code>GameScreen</code>, En este
     * metodo se pausa la pantalla
     */
	@Override
	public void pause() {
		// TODO Auto-generated method stub
	}
	
	/**
     * Metodo <I>resume</I> de la clase <code>GameScreen</code>, En este
     * metodo se resume la pantalla.
     */
	@Override
	public void resume() {
		// TODO Auto-generated method stub
	}
	
	/**
     * Metodo <I>dispose</I> de la clase <code>GameScreen</code>, En este
     * metodo se hace dispose para limpiar los contenedores utilizados en la clase.
     */
	@Override
	public void dispose() {
        Gdx.input.setInputProcessor(null);
	}

	/**
	 * Lo que se hace cuando se presiona una tecla
	 */
	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.LEFT)
			controller.leftPressed();
		if (keycode == Keys.RIGHT)
			controller.rightPressed();
		if (keycode == Keys.Z)
			controller.jumpPressed();
		if (keycode == Keys.X)
			controller.firePressed();
		return true;
	}

	/**
	 * Lo que hace al liberar una tecla
	 */
	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Keys.LEFT)
			controller.leftReleased();
		if (keycode == Keys.RIGHT)
			controller.rightReleased();
		if (keycode == Keys.Z)
			controller.jumpReleased();
		if (keycode == Keys.X)
			controller.fireReleased();
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Lo que hace al tocar la pantalla
	 */
	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		if (!Gdx.app.getType().equals(ApplicationType.Android)) {
			return false;
		}		
		if (x < width / 3) {
			if(!controller.isRightOn())
				controller.leftPressed();
		} else if (x < 2*width/3 ) {
			controller.firePressed();
		} else {
			controller.rightPressed();
		}
		if(y < height / 2){
			controller.jumpPressed();
		}
		return true;
	}

	/**
	 * Metodo <I>touchUp</I> de la clase <code>GameScreen</code>
	 * Lo que hace al dejar de tocar la pantalla
	 */
	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		if (!Gdx.app.getType().equals(ApplicationType.Android)) {
			return false;
		}
		if (x < width / 3) {
			if(!controller.isRightOn())
				controller.leftReleased();
		} else if (x < 2*width/3 ) {
			controller.fireReleased();
		} else{
			controller.rightReleased();
		}
		if(y < height / 2){
			controller.jumpReleased();
		}
		return true;
	}
	
	
	/**
	 * Metodo <I>touchDragged</I> de la clase <code>GameScreen</code> 
	 * Se manda llamar cuando el dedo se arrastra en la pantalla de android
	 */
	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		if (!Gdx.app.getType().equals(ApplicationType.Android)){
			return false;
		}		
		if (x < width / 3) {
			if(!controller.isRightOn()){
				controller.jumpReleased();
				controller.rightReleased();
				controller.leftPressed();
			}
				
		} else if (x < 2*width/3 ) {
			controller.leftReleased();
			controller.rightReleased();
		} else {
			controller.jumpReleased();
			controller.leftReleased();
			controller.rightPressed();
		}
		if (y < height / 2) {

			controller.jumpPressed();
		} else {
			controller.jumpReleased();
		}
		return false;
	}

	/**
	 * Metdo <I>mouseMoved</I> de la clase <code>GameScreen</code> que detecta el movimiento del mouse
	 */
	@Override
	public boolean mouseMoved(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}
