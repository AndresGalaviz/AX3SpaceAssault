package com.me.spaceassault;

import com.me.spaceassault.screens.SplashScreen;
import com.badlogic.gdx.Game;

public class SpaceAssault extends Game {

	/**
	 * Juego principal.
	 * Llama la primera pantalla
	 */
	@Override
	public void create() {
		setScreen(new SplashScreen());
		//setScreen(new GameScreen());
	}
}
