package com.me.spaceassault;

import com.me.spaceassault.screens.GameScreen;

import com.badlogic.gdx.Game;

public class SpaceAssault extends Game {

	@Override
	public void create() {
		setScreen(new GameScreen());
	}
}
