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
public class InstructionsAndroidScreen implements Screen {

	private Stage stage;
	private TextureAtlas atlas;
	private Skin skin;
	private Table table;
	private TextButton buttonBack,buttonMainMenu;
	private BitmapFont white, black;
	private Label heading, name1,name2,name3;
	
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act(delta);
		
		//Table.drawDebug(stage);
		
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

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
		
		
		//creating button
		TextButtonStyle textButtonStyle = new TextButtonStyle(); 
		textButtonStyle.up = skin.getDrawable("buttonUp");
		textButtonStyle.down = skin.getDrawable("buttonPressed");
		textButtonStyle.pressedOffsetX = 1;
		textButtonStyle.pressedOffsetY = -1;
		textButtonStyle.font = black;
	
		
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
		
		LabelStyle nameStyle = new LabelStyle(white,Color.WHITE);
		
		
		heading = new Label("Instructions", headingStyle);
		heading.setFontScale(2);
		
		
		name1 = new Label("Touch upper screen - Jumping",nameStyle);
		name2 = new Label("Touch sides - Movement",nameStyle);
		name3 = new Label("Touch middle down screen - Shooting",nameStyle);
		name1.setFontScale((float) .5);
		name2.setFontScale((float) .5);
		name3.setFontScale((float) .5);
		
		
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
		table.add(buttonMainMenu);
		table.row();
		table.add(buttonBack);
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
