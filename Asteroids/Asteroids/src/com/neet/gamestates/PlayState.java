package com.neet.gamestates;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.neet.entities.Bullet;
import com.neet.entities.FlyingSaucer;
import com.neet.entities.Particle;
import com.neet.entities.Player;
import com.neet.entities.Asteroid;
import com.neet.main.Game;
import com.neet.managers.GameKeys;
import com.neet.managers.GameStateManager;
import com.neet.managers.Jukebox;

public class PlayState extends GameState {

	private SpriteBatch sb;
	private ShapeRenderer sr;

	private BitmapFont font;
	private Player hudPlayer;

	private Player player;
	private ArrayList<Bullet> bullets;
	private ArrayList<Asteroid> asteroids;

	private FlyingSaucer flyingSaucer;
	private ArrayList<Bullet> enemyBullets;
	private float fsTimer;
	private float fsTime;

	private ArrayList<Particle> particles;

	private int level;
	private float accuracy;
	private int totalAsteroids;
	private int numAsteroidsLeft;
	private int shotsMissed;

	private float maxDelay;
	private float minDelay;
	private float currentDelay;
	private float bgTimer;
	private boolean playLowPulse;

	public PlayState(GameStateManager gsm) {
		super(gsm);
	}

	public void init() {

		sb = new SpriteBatch();
		sr = new ShapeRenderer();

		// set font
		FreeTypeFontGenerator gen = new FreeTypeFontGenerator(
				Gdx.files.internal("256BYTES.TTF"));
		font = gen.generateFont(20);

		bullets = new ArrayList<Bullet>();

		player = new Player(bullets);

		asteroids = new ArrayList<Asteroid>();

		particles = new ArrayList<Particle>();

		accuracy = 100.0f;
		shotsMissed = 0;
		level = 1;
		spawnAsteroids();

		hudPlayer = new Player(null);

		fsTimer = 0;
		fsTime = 15;
		enemyBullets = new ArrayList<Bullet>();

		// set up bg music
		maxDelay = 1;
		minDelay = 0.25f;
		currentDelay = maxDelay;
		bgTimer = maxDelay;
		playLowPulse = true;

	}

	private void createParticles(float x, float y) {
		for (int i = 0; i < MathUtils.random(3, 7); i++) {
			particles.add(new Particle(x, y));
		}
	}

	public void shotMissed() {
		shotsMissed++;
	}

	private void splitAsteroids(Asteroid a) {
		player.incrementScore(a.getScore());
		shotsMissed--;
		createParticles(a.getX(), a.getY());
		numAsteroidsLeft--;
		currentDelay = ((maxDelay - minDelay) * numAsteroidsLeft / totalAsteroids)
				+ minDelay;
		if (a.getType() == Asteroid.LARGE) {
			asteroids.add(new Asteroid(a.getX(), a.getY(), Asteroid.MEDIUM));
			asteroids.add(new Asteroid(a.getX(), a.getY(), Asteroid.MEDIUM));
		}
		if (a.getType() == Asteroid.MEDIUM) {
			asteroids.add(new Asteroid(a.getX(), a.getY(), Asteroid.SMALL));
			asteroids.add(new Asteroid(a.getX(), a.getY(), Asteroid.SMALL));
		}
	}

	private void spawnAsteroids() {

		asteroids.clear();

		int numToSpawn = 4 + level - 1;
		totalAsteroids = numToSpawn * 7;
		numAsteroidsLeft = totalAsteroids;
		currentDelay = maxDelay;

		for (int i = 0; i < numToSpawn; i++) {
			float x = MathUtils.random(Game.WIDTH);
			float y = MathUtils.random(Game.HEIGHT);

			float dx = x - player.getX();
			float dy = y - player.getY();
			float dist = (float) Math.sqrt(dx * dx + dy * dy);

			while (dist < 100) {
				x = MathUtils.random(Game.WIDTH);
				y = MathUtils.random(Game.HEIGHT);
				dx = x - player.getX();
				dy = y - player.getY();
				dist = (float) Math.sqrt(dx * dx + dy * dy);
			}

			asteroids.add(new Asteroid(x, y, Asteroid.LARGE));
		}

	}

	public void update(float dt) {

		// get user input
		handleInput();

		// game over
		if (player.getLives() == 0) {
			this.dispose();
		}

		// next level
		if (asteroids.size() == 0) {
			level++;
			spawnAsteroids();
		}

		// update player
		player.update(dt);
		if (player.isDead()) {
			if (player.getLives() == 0) {
				Jukebox.stopAll();
				gsm.setState(GameStateManager.MENU);
				return;
			}
			player.reset();
			player.loseLife();
			flyingSaucer = null;
			Jukebox.stop("smallsaucer");
			Jukebox.stop("largesaucer");
			return;
		}

		// update player bullets
		for (int i = 0; i < bullets.size(); i++) {
			bullets.get(i).update(dt);
			if (bullets.get(i).shouldRemove()) {
				bullets.remove(i);
				i--;
			}
		}

		// update flying saucer
		if (flyingSaucer == null) {
			fsTimer += dt;
			if (fsTimer >= fsTime) {
				fsTimer = 0;
				fsTime = MathUtils.random(10, 20);
				int type = MathUtils.random() < 0.5 ? FlyingSaucer.SMALL
						: FlyingSaucer.LARGE;
				int direction = MathUtils.random() < 0.5 ? FlyingSaucer.RIGHT
						: FlyingSaucer.LEFT;
				flyingSaucer = new FlyingSaucer(type, direction, player,
						enemyBullets);
			}
		}
		// if there is a flying saucer already
		else {
			flyingSaucer.update(dt);
			if (flyingSaucer.shouldRemove()) {
				flyingSaucer = null;
				Jukebox.stop("smallsaucer");
				Jukebox.stop("largesaucer");
			}
		}

		// update fs bullets
		for (int i = 0; i < enemyBullets.size(); i++) {
			enemyBullets.get(i).update(dt);
			if (enemyBullets.get(i).shouldRemove()) {
				enemyBullets.remove(i);
				i--;
			}
		}

		// update asteroids
		for (int i = 0; i < asteroids.size(); i++) {
			asteroids.get(i).update(dt);
			if (asteroids.get(i).shouldRemove()) {
				asteroids.remove(i);
				i--;
			}
		}

		// update particles
		for (int i = 0; i < particles.size(); i++) {
			particles.get(i).update(dt);
			if (particles.get(i).shouldRemove()) {
				particles.remove(i);
				i--;
			}

		}

		// check collision
		checkCollision();

		// play bg music
		bgTimer += dt;
		if (!player.isHit() && bgTimer >= currentDelay) {
			if (playLowPulse) {
				Jukebox.play("pulselow");
			} else {
				Jukebox.play("pulsehigh");
			}
			playLowPulse = !playLowPulse;
			bgTimer = 0;
		}

	}

	private void checkCollision() {

		// saucer collision
		if (flyingSaucer != null) {

			// player-saucer collision
			if (player.intersects(flyingSaucer)) {
				player.hit();
				createParticles(player.getX(), player.getY());

				createParticles(flyingSaucer.getX(), player.getY());

				flyingSaucer = null;
				Jukebox.stop("smallsaucer");
				Jukebox.stop("largesaucer");
				Jukebox.play("explode");
			}
		}

		if(flyingSaucer != null) {
			// bullet-saucer collision
			for (int i = 0; i < bullets.size(); i++) {
				Bullet b = bullets.get(i);
				if (flyingSaucer.contains(b.getX(), b.getY())) {
					bullets.remove(i);
					i--;
					player.incrementScore(flyingSaucer.getScore());

					createParticles(flyingSaucer.getX(), player.getY());

					flyingSaucer = null;
					Jukebox.stop("smallsaucer");
					Jukebox.stop("largesaucer");
					Jukebox.play("explode");

					break;
				}

			}
		}

		if(flyingSaucer != null) {
			// enemy bullet-asteroid collsion
			for (int i = 0; i < enemyBullets.size(); i++) {
				Bullet b = enemyBullets.get(i);
				for (int j = 0; j < asteroids.size(); j++) {
					Asteroid a = asteroids.get(j);
					if (a.contains(b.getX(), b.getY())) {
						asteroids.remove(j);
						j--;
						splitAsteroids(a);
						enemyBullets.remove(i);
						i--;
						createParticles(a.getX(), a.getY());
						Jukebox.play("explode");
						break;
					}
				}
			}
		}

		if(flyingSaucer != null) {
			// fs-asteroid collision
			for (int i = 0; i < asteroids.size(); i++) {
				Asteroid a = asteroids.get(i);
				if (a.intersects(flyingSaucer)) {
					asteroids.remove(i);
					i--;
					splitAsteroids(a);
					createParticles(a.getX(), a.getY());

					createParticles(flyingSaucer.getX(), player.getY());

					flyingSaucer = null;
					Jukebox.stop("smallsaucer");
					Jukebox.stop("largesaucer");
					Jukebox.play("explode");
				}
			}
		}

		// player collisions
		if (!player.isHit()) {

			// player-asteroid collision
			for (int i = 0; i < asteroids.size(); i++) {
				Asteroid a = asteroids.get(i);
				// if player intersects asteroid
				if (a.intersects(player)) {
					player.hit();
					asteroids.remove(i);
					i--;
					splitAsteroids(a);
					Jukebox.play("explode");
					break;
				}
			}

			// player-fs bullet collision
			for (int i = 0; i < enemyBullets.size(); i++) {
				Bullet b = enemyBullets.get(i);
				if (player.contains(b.getX(), b.getY())) {
					player.hit();
					enemyBullets.remove(i);
					i--;
					Jukebox.play("explode");
					break;
				}
			}
		}

		// bullet-asteroid collision
		for (int i = 0; i < bullets.size(); i++) {
			Bullet b = bullets.get(i);
			for (int j = 0; j < asteroids.size(); j++) {
				Asteroid a = asteroids.get(j);
				// if asteroid a contains the point b
				if (a.contains(b.getX(), b.getY())) {
					bullets.remove(i);
					i--;
					asteroids.remove(j);
					j--;
					splitAsteroids(a);
					Jukebox.play("explode");
					break;
				}
			}
		}

	}

	public void draw() {

		// draw player
		player.draw(sr);

		// draw bullets
		for (int i = 0; i < bullets.size(); i++) {
			bullets.get(i).draw(sr);
		}

		// draw flying saucer
		if (flyingSaucer != null) {
			flyingSaucer.draw(sr);
		}

		// draw fs bullets
		for (int i = 0; i < enemyBullets.size(); i++) {
			enemyBullets.get(i).draw(sr);
		}

		// draw asteroids
		for (int i = 0; i < asteroids.size(); i++) {
			asteroids.get(i).draw(sr);
		}

		// draw particles
		for (int i = 0; i < particles.size(); i++) {
			particles.get(i).draw(sr);
		}

		// draw score
		sb.setColor(1, 1, 1, 1);
		sb.begin();
		font.draw(sb, Long.toString(player.getScore()), 40, 390);
		sb.end();

		// draw lives
		for (int i = 0; i < player.getLives(); i++) {
			hudPlayer.setPosition(40 + i * 10, 360);
			hudPlayer.draw(sr);
		}

	}

	public void handleInput() {
		player.setLeft(GameKeys.isDown(GameKeys.LEFT));
		player.setRight(GameKeys.isDown(GameKeys.RIGHT));
		player.setUp(GameKeys.isDown(GameKeys.UP));
		if (GameKeys.isPressed(GameKeys.SPACE)) {
			player.shoot();
		}
	}

	public void dispose() {

	}

}
