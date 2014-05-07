package com.me.spaceassault.resources;

import com.badlogic.gdx.math.Vector2;


public class Hero extends Object {

	int score; 
	/**
	 * Metodo constructor
	 * @param position en la que se posiciona el enemigo
	 */
	public Hero(Vector2 position) {
		super (position, 0.8f, 0.8f, 50f, 100);
		score = 0;
	}

	/**
	 * Obtiene el score asociado al personaje
	 * @return
	 */
	public int getScore() {
		
		return score;
	}
	/**
	 * Agrega al score el valor
	 * @param addition
	 */
	public void addScore(int addition) {
		score += addition;
	}

}
