package com.me.spaceassault.world;

import com.me.spaceassault.resources.BadGuy;
import com.me.spaceassault.resources.Bullet;
import com.me.spaceassault.resources.Hero;
import com.me.spaceassault.resources.Tile;
import com.me.spaceassault.world.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

/**
 * Metodo que dibuja el mundo en la pantalla del juego
 *
 */

public class WorldRenderer {

	private World world;
	private OrthographicCamera cam;

	private static final float CAMERA_WIDTH = 10f;
	private static final float CAMERA_HEIGHT = 7f;
    private static final float RUNNING_FRAME_DURATION = 0.04f;

    /** for debug rendering **/
	ShapeRenderer debugRenderer = new ShapeRenderer();
	
	/** equipo del jugador 0/1 **/
	private int team;
	
	/** Textures **/
    private TextureRegion[] heroIdleLeft = new TextureRegion[2];
    private TextureRegion[] heroIdleRight = new TextureRegion[2];
    private TextureRegion[] heroJumpLeft = new TextureRegion[2];
	private TextureRegion[] heroFallLeft = new TextureRegion[2];
	private TextureRegion[] heroJumpRight = new TextureRegion[2];
	private TextureRegion[] heroFallRight = new TextureRegion[2];
	
	private Texture tileTexture;
    private TextureRegion heroFrame;
    private TextureRegion badGuyFrame;

    /** Animations **/
    private Animation[] walkLeftAnimation = new Animation[2];
    private Animation[] walkRightAnimation = new Animation[2];
	
    private boolean first;
    
	private Texture redBullet;
	private Texture textureFont;

    private SpriteBatch spriteBatch;
	private boolean debug = false;
	private int width;
	private int height;
	private float ppuX;
	private float ppuY;
	
	
	private BitmapFont font;
	
	public void setSize (int w, int h) {
		this.width = w;
		this.height = h;
		ppuX = width/CAMERA_WIDTH;
		ppuY = height/CAMERA_HEIGHT;
	}
	

	/**
	 * Metodo constructor
	 * @param world, el mundo que se usar
	 * @param debug, si esta en modo debug o no
	 */
	
	public WorldRenderer(World world, boolean debug) {
	    this.first = true;
	    this.world = world;
	    this.cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
	    this.cam.setToOrtho(false,CAMERA_WIDTH,CAMERA_HEIGHT);
	    this.cam.position.set(CAMERA_WIDTH / 2f, CAMERA_HEIGHT / 2f, 0);
	    this.cam.update();
	    this.debug = debug;
	    this.team = 1;
	    spriteBatch = new SpriteBatch();
	  
	    
	   
	    textureFont = new Texture(Gdx.files.internal("data/256BYTES_0.png"), true);
	    textureFont.setFilter(TextureFilter.MipMapLinearNearest, TextureFilter.Linear); // linear filtering in nearest mipmap image
	    font = new BitmapFont(Gdx.files.internal("data/256BYTES.fnt"), new TextureRegion(textureFont), false);
	    font.setScale(.09f);

	    
	    loadTextures();
	}

	
	/**
	 * Carga las texturas para las imagenes
	 */
	public void loadTextures(){
		tileTexture = new Texture(Gdx.files.internal("images/terrain/grass.png"));
		redBullet = new Texture(Gdx.files.internal("images/bullets/bullet.png"));
		
		
		/** CARGAR PERSONAJE AZUL **/
		
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("images/sprites/monito.pack"));
		
		heroIdleRight[0] = atlas.findRegion("idle");
		heroIdleLeft[0] = new TextureRegion(heroIdleRight[0]);
		heroIdleLeft[0].flip(true, false);
		
		TextureRegion[] walkRightFrames = new TextureRegion[17];
		for (int i = 1; i <= 17; i++) {
			walkRightFrames[i-1] = atlas.findRegion("run" + (i < 10 ? "0" : "") + i);
		}
		walkRightAnimation[0] = new Animation(RUNNING_FRAME_DURATION, walkRightFrames);
		
		TextureRegion[] walkLeftFrames = new TextureRegion[17];
		for (int i = 0; i < 17; i++) {
			walkLeftFrames[i] = new TextureRegion(walkRightFrames[i]);
			walkLeftFrames[i].flip(true, false);
		}
		walkLeftAnimation[0] = new Animation(RUNNING_FRAME_DURATION, walkLeftFrames);
		
		heroJumpRight[0] = atlas.findRegion("jump");
		heroJumpLeft[0] = new TextureRegion(heroJumpRight[0]);
		heroJumpLeft[0].flip(true, false);
		
		heroFallRight[0] = atlas.findRegion("fall");
		heroFallLeft[0] = new TextureRegion(heroFallRight[0]);
		heroFallLeft[0].flip(true, false);
		
		/** CARGAR PERSONAJE ROJO **/
		
		TextureAtlas atlas2 = new TextureAtlas(Gdx.files.internal("images/sprites/malo.pack"));
		
		heroIdleRight[1] = atlas2.findRegion("idle");
		heroIdleLeft[1] = new TextureRegion(heroIdleRight[1]);
		heroIdleLeft[1].flip(true, false);
		
		TextureRegion[] walkRightFrames2 = new TextureRegion[17];
		for (int i = 1; i <= 17; i++) {
			walkRightFrames2[i-1] = atlas2.findRegion("run" + (i < 10 ? "0" : "") + i);
		}
		walkRightAnimation[1] = new Animation(RUNNING_FRAME_DURATION, walkRightFrames2);
		
		TextureRegion[] walkLeftFrames2 = new TextureRegion[17];
		for (int i = 0; i < 17; i++) {
			walkLeftFrames2[i] = new TextureRegion(walkRightFrames2[i]);
			walkLeftFrames2[i].flip(true, false);
		}
		walkLeftAnimation[1] = new Animation(RUNNING_FRAME_DURATION, walkLeftFrames2);
		
		heroJumpRight[1] = atlas2.findRegion("jump");
		heroJumpLeft[1] = new TextureRegion(heroJumpRight[1]);
		heroJumpLeft[1].flip(true, false);
		
		heroFallRight[1] = atlas2.findRegion("fall");
		heroFallLeft[1] = new TextureRegion(heroFallRight[1]);
		heroFallLeft[1].flip(true, false);
		
		
	}

	/**
	 * Dibuja las imagenes en la pantalla
	 */
	public void render() {
		Hero hero = world.getHero();
	    moveCamera(hero.getPosition().x, hero.getPosition().y);
	    //spriteBatch.setProjectionMatrix(cam.combined);
//	    Matrix4 normalProjection = new Matrix4().setToOrtho2D(0, 0, Gdx.graphics.getWidth(),  Gdx.graphics.getHeight());
	   
	    spriteBatch.setProjectionMatrix(cam.combined);
	    
	    spriteBatch.begin();
	        drawTiles();
	        drawHero();
	        drawBadGuys();
	        drawBullets();


	    spriteBatch.end();
	    spriteBatch.begin();
        font.draw(spriteBatch, String.valueOf(hero.getScore()), cam.position.x - 5, cam.position.y + 3);

        spriteBatch.end();
	    
	    //if (debug) drawDebug();
	}
	
	/**
	 * Sirve para modo debug
	 */
	private void drawCollisionBlocks() {
		debugRenderer.setProjectionMatrix(cam.combined);
		debugRenderer.begin(ShapeType.Filled);
		debugRenderer.setColor(new Color(1, 1, 1, 1));
		for (Rectangle rect : world.getCollisionRects()) {
			debugRenderer.rect(rect.x, rect.y, rect.width, rect.height);
		}
		debugRenderer.end();

	}

	/**
	 * Mueve la camara para que siga al personaje, y la limita al tamano del mundo
	 * @param x
	 * @param y
	 */
	public void moveCamera(float x,float y){
		Hero hero = world.getHero();

		if (hero.getPosition().x > CAMERA_WIDTH / 2 &&  hero.getPosition().x < world.getLevel().getWidth() - CAMERA_WIDTH/2){
	    	cam.position.x = x;

			
		} 
		if (hero.getPosition().y > CAMERA_HEIGHT/2 &&  hero.getPosition().y < world.getLevel().getHeight() - CAMERA_HEIGHT/2 ){
	    	cam.position.y = y;
		} 
	    cam.update();
	    

	}
	/**
	 * Dibuja si el renderer esta en modo debug
	 */
	public void drawDebug() {
		debugRenderer.setProjectionMatrix(cam.combined);
		debugRenderer.begin(ShapeType.Line);
		for (Tile tile : world.getDrawableBlocks((int)CAMERA_WIDTH, (int)CAMERA_HEIGHT)) {
			Rectangle rect = tile.getBounds();
			float x1 = tile.getPosition().x + rect.x;
			float y1 = tile.getPosition().y + rect.y;
			debugRenderer.setColor(new Color(1, 0, 0, 1));
			debugRenderer.rect(x1, y1, rect.width, rect.height);
		}
		// render Hero
		Hero Pyro = world.getHero();
		Rectangle rect = Pyro.getBounds();
		float x1 = Pyro.getPosition().x + rect.x;
		float y1 = Pyro.getPosition().y + rect.y;
		debugRenderer.setColor(new Color(0, 1, 0, 1));
		debugRenderer.rect(x1, y1, rect.width, rect.height);
		debugRenderer.end();
	}
	
	/**
	 * Dibuja los bloques que delimitan el mapa
	 */	
	
	private void drawTiles() {
		for (Tile tile : world.getDrawableBlocks((int)CAMERA_WIDTH, (int)CAMERA_HEIGHT)) {
			spriteBatch.draw(tileTexture, tile.getPosition().x, tile.getPosition().y, tile.SIZE, tile.SIZE);
		}
	}
	/**
	 * Dibuja el personaje principal
	 */
	private void drawHero() {
		
		
		Hero hero = world.getHero();
		heroFrame = hero.isFacingLeft() ? heroIdleLeft[team] : heroIdleRight[team];
		if(hero.getState().equals(Hero.State.WALK)) {
			heroFrame = hero.isFacingLeft() ? walkLeftAnimation[team].getKeyFrame(hero.getStateTime(), true) : walkRightAnimation[team].getKeyFrame(hero.getStateTime(), true);
		} else if (hero.getState().equals(Hero.State.JUMP)) {
			if (hero.getVelocity().y > 0) {
				heroFrame = hero.isFacingLeft() ? heroJumpLeft[team] : heroJumpRight[team];
			} else {
				heroFrame = hero.isFacingLeft() ? heroFallLeft[team] : heroFallRight[team];
			}
		}
		spriteBatch.draw(heroFrame, hero.getPosition().x, hero.getPosition().y, hero.WIDTH , hero.HEIGHT);
		

	}
	
	/**
	 * Dibuja el arreglo de enemigos
	 */
	private void drawBadGuys() {
		Array<BadGuy> badGuys = world.getBadGuys();
		TextureRegion badGuyFrame;
		for (BadGuy badGuy : badGuys) {
			if (badGuy.isGrounded()) {
				if (badGuy.getVelocity().x == 0) {
					badGuyFrame = badGuy.isFacingLeft() ? heroIdleLeft[1-team] : heroIdleRight[1-team];
				} else {
					badGuyFrame = badGuy.isFacingLeft() ? walkLeftAnimation[1-team].getKeyFrame(world.getHero().getStateTime(), true) : walkRightAnimation[1-team].getKeyFrame(world.getHero().getStateTime(), true); 
				}
			} else if (badGuy.getVelocity().y > 0) {
				badGuyFrame = badGuy.isFacingLeft() ? heroJumpLeft[1-team] : heroJumpRight[1-team];
			} else {
				badGuyFrame = badGuy.isFacingLeft() ? heroFallLeft[1-team] : heroFallRight[1-team];
			}
			spriteBatch.draw(badGuyFrame, badGuy.getPosition().x, badGuy.getPosition().y, badGuy.WIDTH, badGuy.HEIGHT);
		}
	}
	
	/**
	 * Dibuja las balas
	 */
	private void drawBullets() {
		Array<Bullet> bullets = world.getBullets();
		for (Bullet bullet : bullets) {
			spriteBatch.draw(redBullet, bullet.getPosition().x, bullet.getPosition().y, bullet.WIDTH, bullet.HEIGHT);
		}
	}
	
	public float getCameraWidth() {
		return CAMERA_WIDTH;
	}
	
	public float getCameraHeight() {
		return CAMERA_HEIGHT;
	}
}
