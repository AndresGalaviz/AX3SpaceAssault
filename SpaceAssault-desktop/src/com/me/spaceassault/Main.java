package com.me.spaceassault;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		new LwjglApplication(new SpaceAssault(), "Star Assault", 480, 320, true);
	}
}
