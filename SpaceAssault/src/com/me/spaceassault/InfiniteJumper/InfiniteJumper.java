package com.me.mygdxgame;



import com.me.mygdxgame.Play;
import com.badlogic.gdx.Game;

public class InfiniteJumper extends Game {

	/**
     * Metodo <I>create</I> de la clase <code>InfiniteJumper</code>, En este
     * metodo crea la instancia del juego.
     */
	@Override
	public void create() {
		setScreen(new Play());
		//setScreen(new GameScreen());
	}
}
