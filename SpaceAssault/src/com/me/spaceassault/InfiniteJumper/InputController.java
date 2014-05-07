package com.me.mygdxgame;

import com.badlogic.gdx.InputProcessor;

public class InputController implements InputProcessor {
	/**
     * Metodo <I>keyDown</I> de la clase <code>InputController</code>, En este
     * metodo se recibe la tecla oprimida
     * @paramkeycode tipo de dato <code>Entero</code> es el valor utilizado para
     * manejar la tecla oprimida.
     */
	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}
	/**
     * Metodo <I>keyUp</I> de la clase <code>InputController</code>, En este
     * metodo se recibe la tecla oprimida
     * @paramkeycode tipo de dato <code>Entero</code> es el valor utilizado para
     * manejar la tecla liberada.
     */
	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}
	/**
     * Metodo <I>keyTyped</I> de la clase <code>InputController</code>, En este
     * metodo se recibe la tecla oprimida
     * @paramcharacter tipo de dato <code>char</code> es el valor utilizado para
     * manejar introducir la secuencia tecleada.
     */
	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}
	/**
     * Metodo <I>touchDown</I> de la clase <code>InputController</code>, En este
     * metodo se recibe posicion de la pantalla oprimida.
     * @paramscreenX tipo de dato <code>Entero</code> es el valor utilizado para
     * manejar el punto en x de la pantalla oprimido.
     * @paramscreenY tipo de dato <code>Entero</code> es el valor utilizado para
     * manejar el punto en y de la pantalla oprimido.
     * @parampointer tipo de dato <code>Entero</code> es el valor utilizado para
     * manejar apuntador de la pantalla.
     * @parambutton tipo de dato <code>Entero</code> es el valor utilizado para
     * manejar el boton oprimido en la pantalla.
     */
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}
	/**
     * Metodo <I>touchUp</I> de la clase <code>InputController</code>, En este
     * metodo se recibe la posicion de la pantalla liberada.
     * @paramscreenX tipo de dato <code>Entero</code> es el valor utilizado para
     * manejar el punto en x de la pantalla previamente oprimido.
     * @paramscreenY tipo de dato <code>Entero</code> es el valor utilizado para
     * manejar el punto en y de la pantalla previamente oprimido.
     * @parampointer tipo de dato <code>Entero</code> es el valor utilizado para
     * manejar apuntador de la pantalla.
     * @parambutton tipo de dato <code>Entero</code> es el valor utilizado para
     * manejar el boton despues de oprimir en la pantalla.
     */
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}
	/**
     * Metodo <I>touchDragged</I> de la clase <code>InputController</code>, En este
     * metodo se recibe la posicion del arrastramiento de la pantalla.
     * @paramscreenX tipo de dato <code>Entero</code> es el valor utilizado para
     * manejar el punto en x de la pantalla.
     * @paramscreenY tipo de dato <code>Entero</code> es el valor utilizado para
     * manejar el punto en y de la pantalla.
     * @parampointer tipo de dato <code>Entero</code> es el valor utilizado para
     * manejar apuntador de la pantalla.
     */
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}
	/**
     * Metodo <I>mouseMoved</I> de la clase <code>InputController</code>, En este
     * metodo se reciben las coordenadas del raton.
     * @paramscreenX tipo de dato <code>Entero</code> es el valor utilizado para
     * manejar el punto en x del raton en la pantalla.
     * @paramscreenY tipo de dato <code>Entero</code> es el valor utilizado para
     * manejar el punto en y del raton en la pantalla.
     */
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}
	/**
     * Metodo <I>scrolled</I> de la clase <code>InputController</code>, En este
     * metodo se recibe la cantidad de scroll.
     * @paramamaount tipo de dato <code>Entero</code> es el valor utilizado para
     * manejar la cantidad de scroll.
     */
	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
