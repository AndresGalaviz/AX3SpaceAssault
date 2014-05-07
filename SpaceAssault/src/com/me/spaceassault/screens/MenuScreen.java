package com.me.spaceassault.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
public class MenuScreen implements Screen {

	private Stage stage;
	private TextureAtlas atlas;
	private Skin skin;
	private Table table;
	private TextButton buttonPlay, buttonExit, buttonCredits, buttonInstructions;
	private BitmapFont white, black, text;
	private Label heading;
	/**
     * Metodo <I>render</I> de la clase <code>MenuScreen</code>, En este
     * metodo se pintan los objetos y se actualizan variables que involucran
     * tiempo.
     * @paramdelta tipo de dato <code>float</code> es el valor utilizado para
     * manejar la diferencia de tiempo
     */
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act(delta);
		
		//Table.drawDebug(stage);
		
		stage.draw();
	}

    /**
     * Metodo <I>resize</I> de la clase <code>MenuScreen</code>, En este
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
     * Metodo <I>show</I> de la clase <code>MenuScreen</code>, En este
     * metodo se definen los actores de la clase
     *
     */
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
		stage = new Stage();
		
		Gdx.input.setInputProcessor(stage); 
		
		atlas = new TextureAtlas("data/button.pack");
		skin = new Skin(atlas);
		
		table = new Table(skin);
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		

		//creating fonts
		white = new BitmapFont(Gdx.files.internal("data/whiteHigher.fnt"),false);
		black = new BitmapFont(Gdx.files.internal("data/blackHigher.fnt"),false);
		text = new BitmapFont(Gdx.files.internal("data/SpaceRanger.fnt"),false);
		
		//creating button
		TextButtonStyle textButtonStyle = new TextButtonStyle(); 
		textButtonStyle.up = skin.getDrawable("buttonUp");
		textButtonStyle.down = skin.getDrawable("buttonPressed");
		textButtonStyle.pressedOffsetX = 1;
		textButtonStyle.pressedOffsetY = -1;
		textButtonStyle.font = text;
	
		
		
		buttonPlay = new TextButton("Play",textButtonStyle);
		buttonPlay.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				dispose();
				((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen());
				
			}
		});
		buttonPlay.pad(15);
		
		buttonInstructions = new TextButton("Help",textButtonStyle);
		buttonInstructions.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				dispose();
				((Game) Gdx.app.getApplicationListener()).setScreen(new InstructionsScreen());
			}
		});
		buttonInstructions.pad(15);
		
		buttonCredits = new TextButton("Credits",textButtonStyle);
		buttonCredits.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				dispose();
				((Game) Gdx.app.getApplicationListener()).setScreen(new CreditsScreen());
			}
		});
		buttonCredits.pad(15);
		
		buttonExit = new TextButton("Exit", textButtonStyle);
		buttonExit.addCaptureListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				Gdx.app.exit();
			}
		});
		buttonExit.pad(20);
		
		
		//creating heading
		LabelStyle headingStyle = new LabelStyle(white, Color.WHITE);
		
		heading = new Label("Space Assault", headingStyle);
		heading.setFontScale(2);
		
		
		//table manipulation
		table.add(heading);
		table.getCell(heading).spaceBottom(100);
		table.row();
		table.add(buttonPlay).width(buttonInstructions.getWidth()+150).fillX();
		table.row();
		table.add(buttonCredits).width(buttonInstructions.getWidth()+150).fillX();
		table.row();
		table.add(buttonInstructions).width(buttonInstructions.getWidth()+150).fillX();
		table.row();
		table.add(buttonExit).width(buttonInstructions.getWidth()+150).fillX();
		stage.addActor(table);

		

	}

	/**
	 * Metodo que se utiliza paa esconder la aplicacion
	 */
	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Metodo que se llama cuando se pausa la aplicacion
	 */
	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Metodo que se llama cuando se resume la aplicacion
	 */
	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		stage.dispose();
		atlas.dispose();
		skin.dispose();
		white.dispose();
		black.dispose();
		
		
	}

}
