package com.me.spaceassault.screens;

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
import com.echo.game.Launcher;
import com.echo.game.utilities.TextureManager;
import com.echo.tweenAccessors.ActorAccessor;


public class MainMenu implements Screen{

	private Stage stage;
	private TextureAtlas atlas;


	private Skin skin;
	private Table table;
	private TextButton buttonPlay, buttonMultiplayer, buttonProfile, buttonLeaderboards;
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
		
		tweenManager.update(delta);
		
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
		buttonPlay = new TextButton("Single", textButtonStyle);
		buttonPlay.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.log("SCREEN", "LevelSelect...");
				MainMenu.this.dispose();
				((Launcher) Gdx.app.getApplicationListener()).setScreen(new SelectLevel());
			}
		});
		buttonPlay.pad(20);
		
		buttonMultiplayer = new TextButton("Multi", textButtonStyle);
		buttonMultiplayer.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.log("SCREEN", "MatchScreen...");
				MainMenu.this.dispose();
				((Launcher) Gdx.app.getApplicationListener()).setScreen(new MatchScreen());
			}
		});
		buttonMultiplayer.pad(20);
		
		//creating exitbutton
		buttonProfile = new TextButton("Profile", textButtonStyle);
		
		buttonProfile.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
//				Gdx.app.exit();
//				if (Walker.actionResolver.getSignedInGPGS()) Walker.actionResolver.getLeaderboardGPGS();
//				else Walker.actionResolver.loginGPGS();
			}
		});
		buttonProfile.pad(20);
		
		
		//creating buttonAchievements
		buttonLeaderboards = new TextButton("Trophies", textButtonStyle);
		
		buttonLeaderboards.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.log("login", "achievements...");
				if(Gdx.app.getType()==ApplicationType.Android){

					if (Launcher.actionResolver.getSignedInGPGS())
						Launcher.actionResolver.getAchievementsGPGS();
					else Launcher.actionResolver.loginGPGS();
				}
//				Gdx.app.exit();
//				if (Walker.actionResolver.getSignedInGPGS()) Walker.actionResolver.getAchievementsGPGS();
//				else Walker.actionResolver.loginGPGS();
			}
		});
		buttonLeaderboards.pad(20);
		//LOADING FILE
//		highscore = prefs.getInteger("highscore");
		
//		Gdx.app.log("FILE", "highscore"+highscore);
		
		
		
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
		table.add(buttonMultiplayer).size(200, 100).spaceLeft(10);
		table.getCell(buttonMultiplayer).spaceBottom(25);
		table.row();
		table.add(buttonProfile).size(400, 100).colspan(2);
		table.getCell(buttonProfile).spaceBottom(25);
		
		table.row();
		table.add(buttonLeaderboards).size(400, 100).colspan(2);
		table.getCell(buttonLeaderboards).spaceBottom(25);
		table.row();
//		table.add(hscore).spaceBottom(50);
		table.debug();//debug
		stage.addActor(table);
		
		//creating animations
		tweenManager = new TweenManager();
		Tween.registerAccessor(Actor.class , new ActorAccessor());
		
		//deploying animations
//		Timeline.createSequence().beginSequence()
//			.push(Tween.to(heading, ActorAccessor.RGB, 0.5f).target(0,0,1))
//			.push(Tween.to(heading, ActorAccessor.RGB, 0.5f).target(0,1,0))
//			.push(Tween.to(heading, ActorAccessor.RGB, 0.5f).target(1,0,0))
//			.end().repeat(Tween.INFINITY, 0).start(tweenManager);
		
		Timeline.createSequence().beginSequence()
			.push(Tween.set(buttonPlay, ActorAccessor.ALPHA).target(0))
			.push(Tween.set(buttonMultiplayer, ActorAccessor.ALPHA).target(0))
			.push(Tween.set(buttonProfile, ActorAccessor.ALPHA).target(0))
			.push(Tween.set(buttonLeaderboards, ActorAccessor.ALPHA).target(0))
			.push(Tween.set(hscore, ActorAccessor.ALPHA).target(0))
			.beginParallel()
			.push(Tween.from(heading, ActorAccessor.ALPHA, 1f).target(0))
			.pushPause(350)
			.push(Tween.to(buttonPlay, ActorAccessor.ALPHA, 1f).target(1))
			.pushPause(350)
			.push(Tween.to(buttonMultiplayer, ActorAccessor.ALPHA, 1f).target(1))
			.pushPause(350)
			.push(Tween.to(buttonProfile, ActorAccessor.ALPHA, 1f).target(1))
			.pushPause(350)
			.push(Tween.to(buttonLeaderboards, ActorAccessor.ALPHA, 1f).target(1))
			.pushPause(350)
			.push(Tween.to(hscore, ActorAccessor.ALPHA, 1f).target(1))
			.end()
			.end().start(tweenManager);
		
//		Tween.from(table, ActorAccessor.ALPHA, 0.5f).target(0).start(tweenManager);
//		Tween.from(table, ActorAccessor.ALPHA, 0.5f).target(Gdx.graphics.getHeight()/8).start(tweenManager);
		Tween.from(table, ActorAccessor.Y, .75f).target(Gdx.graphics.getHeight() / 8).start(tweenManager);

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
