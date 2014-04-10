package com.me.spaceassault.screens;
import aurelienribon.tweenengine.Timeline;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.me.spaceassault.utilities.TextureManager;


public class MainMenu implements Screen{

	private Stage stage;
	private TextureAtlas atlas;


	private Skin skin;
	private Table table;
	private TextButton buttonPlay, buttonInstructions, buttonCredits;
	private BitmapFont white, black;
	private Label heading;
	private Label hscore;
	private int highscore;
	private Texture texture;
//	private Preferences prefs = Gdx.app.getPreferences("myPreferences");
	
	private SpriteBatch batch;
	
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(texture, 0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//		batch.draw(Assets.moon, Gdx.graphics.getWidth()/4 *3, Gdx.graphics.getHeight()/4 *3);
//		batch.draw(Assets.background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.end();
		
		stage.act(delta);
		stage.draw();
//		Table.drawDebug(stage);//debug
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
		table.invalidateHierarchy();
		table.setSize(width, height);
	}

	@Override
	public void show() {
//		Walker.myRequestHandler.showAds(true);
		stage = new Stage();
		
		Gdx.input.setInputProcessor(stage);
		Gdx.input.setCatchBackKey(false);//back button disable
		
		atlas = new TextureAtlas("images/menu/button2.pack");
		skin = new Skin(atlas);
		
		table = new Table(skin);
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		texture = TextureManager.getTexture("menu/menuBackground.png");
		
		//creating fonts
		white = new BitmapFont(Gdx.files.internal("images/menu/whitefont.fnt"));
		black = new BitmapFont(Gdx.files.internal("images/menu/blackfont.fnt"));
		
		batch = new SpriteBatch();
		
		//creating textbuttonstyle
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.getDrawable("button2");
		textButtonStyle.down = skin.getDrawable("button2pressed");
		textButtonStyle.pressedOffsetX = 1;
		textButtonStyle.pressedOffsetY = -1;
		textButtonStyle.font = black;
		textButtonStyle.font.setScale(1.5f);
		
		//creating playbutton
		buttonPlay = new TextButton("Play", textButtonStyle);
		buttonPlay.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.log("SCREEN", "LevelSelect...");
				MainMenu.this.dispose();
				// setScreen(new GameScreen())
			}
		});
		buttonPlay.pad(20);
		
		buttonInstructions = new TextButton("Instructions", textButtonStyle);
		buttonInstructions.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.log("SCREEN", "MatchScreen...");
				MainMenu.this.dispose();
				// setScreen(new InstructionsScreen())
			}
		});
		buttonInstructions.pad(20);
		
		buttonCredits = new TextButton("Credits", textButtonStyle);
		
		buttonCredits.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
//				Gdx.app.exit();
//				if (Walker.actionResolver.getSignedInGPGS()) Walker.actionResolver.getLeaderboardGPGS();
//				else Walker.actionResolver.loginGPGS();
			}
		});
		buttonCredits.pad(20);
		
		
		
		
		//creating heading
		LabelStyle headingStyle = new LabelStyle(white, Color.WHITE);
		heading = new Label("Echo", headingStyle);
		
		heading.setFontScale(3f);
		hscore = new Label("Highscore: "+Integer.toString(highscore), headingStyle);
		hscore.setFontScale(1.5f);
		
		//creating sprite
//		texture = new Texture("player/character2.png");
//		Image walker = new Image(texture);
		
		//putting stuff together
//		table.add(walker).size(250);
//		table.getCell(walker).spaceBottom(50);
//		table.row();
		table.add(heading).spaceBottom(50).colspan(2);
		table.row();
		table.add(buttonPlay).size(200, 100).spaceRight(10);
		table.getCell(buttonPlay).spaceBottom(25);
//		table.row();
		table.add(buttonInstructions).size(200, 100).spaceLeft(10);
		table.getCell(buttonInstructions).spaceBottom(25);
		table.row();
		table.add(buttonCredits).size(400, 100).colspan(2);
		table.getCell(buttonCredits).spaceBottom(25);
		
		table.row();
//		table.add(hscore).spaceBottom(50);
		table.debug();//debug
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
		stage.dispose();
		atlas.dispose();
		skin.dispose();
		white.dispose();
		black.dispose();
//		texture.dispose();
//		background.dispose();
//		moon.dispose();
		
		
		
	}
	
}
