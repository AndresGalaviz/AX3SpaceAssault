package com.me.spaceassault.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ObjectMap;

public class TextureManager {

	private static ObjectMap<String, Texture> textures = new ObjectMap<String, Texture>();
	
	private TextureManager() { }
	
	public static Texture getTexture(String location) {
		if(textures.containsKey(location)) {
			return textures.get(location);
		}
		
		Texture texture = new Texture(Gdx.files.internal("images/" + location));
		textures.put(location, texture);
		return texture;
	}
	
	public static void disposeTexture(String location) {
		if(textures.containsKey(location)) {
			textures.get(location).dispose();
			textures.remove(location);
		}
	}
	
	public static void disposeAll() {
		for(Texture texture : textures.values()) {
			texture.dispose();
		}
		textures.clear();
	}
	
}
