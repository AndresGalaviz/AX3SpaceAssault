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
/**
 * Clase <I>InstructionsAndroidScreen</I> utilizada para desplegar las pantallas de instrucciones
 * 
 * @author AndresG
 *
 */
public class InstructionsAndroidScreen implements Screen {

	
	private Stage stage;
	private TextureAtlas atlas;
	private Skin skin;
	private Table table;
	private TextButton buttonBack,buttonMainMenu;
	private BitmapFont white, black, text, text2;
	private Label heading, name1,name2,name3;
	
    /**
     * Metodo <I>render</I> de la clase <code>InstructionsAndroidScreen</code>, En este
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
     * Metodo <I>resize</I> de la clase <code>InstructionsAndroidScreen</code>, En este
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
     * Metodo <I>show</I> de la clase <code>InstructionsAndroidScreen</code>, En este
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
		text2 = new BitmapFont(Gdx.files.internal("data/WhiteSpaceRanger.fnt"),false);
		
		//creating button
		TextButtonStyle textButtonStyle = new TextButtonStyle(); 
		textButtonStyle.up = skin.getDrawable("buttonUp");
		textButtonStyle.down = skin.getDrawable("buttonPressed");
		textButtonStyle.pressedOffsetX = 1;
		textButtonStyle.pressedOffsetY = -1;
		textButtonStyle.font = text;
	
		
		buttonMainMenu = new TextButton("Main Menu",textButtonStyle);
		buttonMainMenu.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				((Game) Gdx.app.getApplicationListener()).setScreen(new MenuScreen());
			}
		});
		
		buttonMainMenu.pad(15);
		
		buttonBack = new TextButton("Back",textButtonStyle);
		buttonBack.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				((Game) Gdx.app.getApplicationListener()).setScreen(new InstructionsScreen());
			}
		});
		
		buttonBack.pad(15);
		
		//creating heading
		LabelStyle headingStyle = new LabelStyle(white, Color.RED);
		
		LabelStyle nameStyle = new LabelStyle(text2,Color.WHITE);
		
		
		heading = new Label("Instructions", headingStyle);
		heading.setFontScale(2);
		
		
		name1 = new Label("Touch upper screen - Jumping",nameStyle);
		name2 = new Label("Touch sides - Movement",nameStyle);
		name3 = new Label("Touch middle down screen - Shooting",nameStyle);

		
		
		//table manipulation
		table.add(heading);
		table.getCell(heading).spaceBottom(60);
		table.row();
		table.add(name1);
		table.getCell(name1).spaceBottom(20);
		table.row();
		table.add(name2);
		table.getCell(name2).spaceBottom(20);
		table.row();
		table.add(name3);
		table.getCell(name3).spaceBottom(20);
		table.row();
		table.add(buttonMainMenu).width(buttonMainMenu.getWidth()+120);
		table.row();
		table.add(buttonBack).width(buttonMainMenu.getWidth()+120);
		table.debug(); //remove later
		stage.addActor(table);
		

	}
	
	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

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
		stage.dispose();
		atlas.dispose();
		skin.dispose();
		white.dispose();
		black.dispose();
		
	}

}
