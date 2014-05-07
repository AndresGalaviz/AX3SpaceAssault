package com.me.spaceassault.resources;

import com.badlogic.gdx.math.Vector2;


public class Hero extends Object {

	int score;

	public Hero(Vector2 position) {
		super (position, 0.8f, 0.8f, 50f, 100);
		score = 0;
	}

	public int getScore() {
		
		return score;
	}
	public void addScore(int addition) {
		score += addition;
	}

}
