package com.me.spaceassault.screens;
import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.me.spaceassault.tween.SpriteAccessor;
/**
 * Clase que se utiliza para cargar las splash screen
 * @author AndresG
 *
 */
public class SplashScreen implements Screen{
	
	private Sprite splash;
	private SpriteBatch batch;
	private TweenManager tweenManager;
	Texture SplashTexture;
	/**
     * Metodo <I>render</I> de la clase <code>SplashScreen</code>, En este
     * metodo se pintan los objetos y se actualizan variables que involucran
     * tiempo.
     * @paramdelta tipo de dato <code>float</code> es el valor utilizado para
     * manejar la diferencia de tiempo
     */
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		tweenManager.update(delta);
		
		batch.begin();
		splash.draw(batch);
		batch.end();
	}
	
    /**
     * Metodo <I>resize</I> de la clase <code>SplashScreen</code>, En este
     * metodo se redefine el tamano de la pantalla
     *
     * @paramwidth tipo de dato <code>Entero</code> es el valor utilizado para
     * manejar el tamano en x.
     * @paramheight tipo de dato <code>Entero</code> es el valor utilizado para
     * manejar el tamano en y.
     */
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}
    /**
     * Metodo <I>show</I> de la clase <code>SplashScreen</code>, En este
     * metodo se definen los actores de la clase
     *
     */
	@Override
	public void show() {
		// TODO Auto-generated method stub
		tweenManager = new TweenManager();
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());
		
		batch = new SpriteBatch();
		SplashTexture = new Texture(Gdx.files.internal("data/AX3GameStudios.png"));
		
		
		splash = new Sprite(SplashTexture);
		splash.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		
		Tween.set(splash, SpriteAccessor.ALPHA).target(0).start(tweenManager);
		Tween.to(splash, SpriteAccessor.ALPHA, 2).target(1).repeatYoyo(1, 0.5f).setCallback(new TweenCallback(){
			@Override
			public void onEvent(int type, BaseTween<?> source){
				((Game) Gdx.app.getApplicationListener()).setScreen(new MenuScreen());
			}
		}).start(tweenManager);
		}
	
	

	/**
	 * Metodo que se llama cuando se esconde la aplicaion
	 */
	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Metodoo que se llama 
	 */
	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		batch.dispose();
		splash.getTexture().dispose();
	}

}
