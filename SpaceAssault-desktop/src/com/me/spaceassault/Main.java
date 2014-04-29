package com.me.spaceassault;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

public class Main {
	public static void main(String[] args) {
		new LwjglApplication(new SpaceAssault(), "Star Assault", 1000, 640, true);
	}
}
