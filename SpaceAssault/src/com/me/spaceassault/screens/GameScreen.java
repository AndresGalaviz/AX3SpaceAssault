package com.me.spaceassault.screens;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.me.spaceassault.world.World;
import com.me.spaceassault.world.WorldRenderer;
import com.me.spaceassault.control.WorldController;


public class GameScreen implements Screen, InputProcessor {

	private World world;
	private WorldRenderer renderer;
	private WorldController controller;

	private int width, height;

	/**
	 * Render de la pantalla del juego
	 */
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		controller.update(delta);
		renderer.render();
	}

	/**
	 * Ajusta el tamano de la pantalla
	 */
	@Override
	public void resize(int width, int height) {
		renderer.setSize(width, height);
		
		this.width = width;
		this.height = height;

	}

	/**
	 * Lo que se muestra cuando la pantalla esta activa
	 */
	@Override
	public void show() {
		world = new World();
		renderer = new WorldRenderer(world, false);
		controller = new WorldController(world);
		Gdx.input.setInputProcessor(this);

		// TODO Auto-generated method stub
	}

	/**
	 * Lo que se hace cuando la pantalla esta inactiva
	 */
	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
	}

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
	 * Lo que hace cuando se arrastra el dedo en la pantalla
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
