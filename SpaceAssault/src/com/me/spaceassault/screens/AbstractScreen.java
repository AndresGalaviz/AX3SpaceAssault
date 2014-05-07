package com.me.spaceassault.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.me.spaceassault.SpaceAssault;
/**
 * La clase base de todas las pantallas 
 * @author AndresG
 *
 */
public abstract class AbstractScreen implements Screen
{
    protected final SpaceAssault game;
    protected final BitmapFont font;
    protected final SpriteBatch batch;

    /**
     * Metodo constructor
     * @param game
     */
    public AbstractScreen(SpaceAssault game)
    {
        this.game = game;
        this.font = new BitmapFont();
        this.batch = new SpriteBatch();
    }

    /**
     * Metodo que se llama cuando se muestra la aplicacion de nuevo
     */
    @Override
    public void show()
    {
    }

    /**
     * Metodo que se llama cuando la pantalla cambia de tamano
     */
    @Override
    public void resize(
        int width,
        int height )
    {
    }

    /**
     * Metodo en el que se dibujan los elementos de la pantalla
     */
    @Override
    public void render(
        float delta )
    {
        // the following code clears the screen with the given RGB color (black)
        Gdx.gl.glClearColor( 0f, 0f, 0f, 1f );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );
    }

    /**
     * Metodo que se manda llamar cuando la aplicacion se esconde
     */
    @Override
    public void hide()
    {
    }

    /**
     * Metodo que se manda llamar cuando la aplicacion se pausa
     */
    @Override
    public void pause()
    {
    }

    /**
     * Metodo que se manda llamar cuando la aplicacion se resume
     */
    @Override
    public void resume()
    {
    }

    /**
     * Metodo para liberar todos los recursos que se han utilizado
     */
    @Override
    public void dispose()
    {
        font.dispose();
        batch.dispose();
    }
}