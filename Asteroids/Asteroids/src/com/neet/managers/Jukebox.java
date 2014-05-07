/*
 *Class Jukebox
 *
 *@Author Sergio Cordero
 *@Matricula A01191167
 */
package com.neet.managers;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class Jukebox {
	
	private static HashMap<String, Sound> sounds;
	
	static {
		sounds = new HashMap<String, Sound>();
	}
	
	/*
	 * Metodo <I>load</I> de la clase <code>Jukebox</code>.
	 * Carga el sonido en la direccion <code>path</code> con 
	 * el nombre <code>name</code>.
	 * 
	 * @parampath tipo de dato <code>string</code> que da la
	 * ubicacion del archivo por cargar.
	 * 
	 * @paramname tipo de dato <code>string</code> que da
	 * el nombre del archivo por cargar.
	 */
	public static void load(String path, String name) {
		Sound sound = Gdx.audio.newSound(Gdx.files.internal(path));
		sounds.put(name,  sound);
	}
	
	/*
	 * Metodo <I>play</I> de la clase <code>Jukebox</code>.
	 * Reproduce el sonido en <code>name</code>.
	 * 
	 * @paramname da el nombre del sonido por reproducir.
	 */
	public static void play(String name) {
		sounds.get(name).play();
	}
	
	/*
	 * Metodo <I>loop</I> de la clase <code>Jukebox</code>.
	 * Reproduce el sonido en <code>name</code> en loop.
	 * 
	 * @paramname da el nombre del sonido por reproducir.
	 */
	public static void loop(String name) {
		sounds.get(name).loop();
	}
	
	/*
	 * Metodo <I>stop</I> de la clase <code>Jukebox</code>.
	 * Detiene el sonido en <code>name</code>.
	 * 
	 * @paramname da el nombre del sonido por detener.
	 */
	public static void stop(String name) {
		sounds.get(name).stop();
	}
	
	/*
	 * Metodo <I>stopAll</I> de la clase <code>Jukebox</code>.
	 * Detiene todos los sonidos
	 */
	public static void stopAll() {
		for(Sound s: sounds.values()) {
			s.stop();
		}
	}

}
