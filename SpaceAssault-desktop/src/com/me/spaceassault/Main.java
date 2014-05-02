package com.me.spaceassault;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

public class Main {
	public static void main(String[] args) {
		new LwjglApplication(new SpaceAssault(), "Space Assault", 1000, 640);
	}
}
