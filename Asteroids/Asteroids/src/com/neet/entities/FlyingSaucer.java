/*
 *Class FlyingSaucer
 *
 *@Author Sergio Cordero
 *@Matricula A01191167
 */
package com.neet.entities;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.neet.main.Game;
import com.neet.managers.Jukebox;

public class FlyingSaucer extends SpaceObject {

	private ArrayList<Bullet> bullets;

	private int type;
	public static final int LARGE = 0;
	public static final int SMALL = 1;

	private int score;

	private float fireTimer;
	private float fireTime;

	private Player player;

	private float pathTimer;
	private float pathTime1;
	private float pathTime2;

	private int direction;
	public static final int LEFT = 0;
	public static final int RIGHT = 1;

	public static final int NORMAL = 0;
	public static final int INVERSE = 1;

	private boolean remove;

	/*
	 * Metodo <I>constructor</I> de la clase <code>FlyingSaucer</code>
	 *
	 * @paramtype tipo de dato <code>int</code> que define el tipo de
	 * ovni que es.
	 *
	 * @paramdirection tipo de dato <code>int</code> que define la ruta
	 * que tomara el ovni.
	 *
	 * @paramplayer tipo de dato <code>Player</code> que es el jugador 
	 * para poder dispararle a el.
	 */
	public FlyingSaucer(int type, int direction, Player player,
			ArrayList<Bullet> bullets) {

		this.type = type;
		this.direction = direction;
		this.player = player;
		this.bullets = bullets;

		speed = 70;
		if (direction == LEFT) {
			dx = -speed;
			x = Game.WIDTH;
		} else if (direction == RIGHT) {
			dx = speed;
			x = 0;
		}
		y = MathUtils.random(Game.HEIGHT);

		shapex = new float[6];
		shapey = new float[6];
		setShape();

		if (type == LARGE) {
			score = 200;
			Jukebox.loop("largesaucer");
		}
		if (type == SMALL) {
			score = 1000;
			Jukebox.loop("smallsaucer");
		}

		fireTimer = 0;
		fireTime = 1;

		pathTimer = 0;
		pathTime1 = 1;
		pathTime2 = pathTime1 + 2;
	}

	/*
	 * Metodo <I>setShape</I> de la clase <code>FlyingSaucer</code>.
	 * Define la forma del ovni.
	 */
	private void setShape() {
		if (type == LARGE) {
			shapex[0] = x - 10;
			shapey[0] = y;
			
			shapex[1] = x - 3;
			shapey[1] = y - 5;
			
			shapex[2] = x + 3;
			shapey[2] = y - 5;
			
			shapex[3] = x + 10;
			shapey[3] = y;
			
			shapex[4] = x - 3;
			shapey[4] = y + 5;
			
			shapex[5] = x + 3;
			shapey[5] = y + 5;

		} else if (type == SMALL) {
			shapex[0] = x - 6;
			shapey[0] = y;
			
			shapex[1] = x - 2;
			shapey[1] = y - 3;
			
			shapex[2] = x + 2;
			shapey[2] = y - 3;
			
			shapex[3] = x + 6;
			shapey[3] = y;
			
			shapex[4] = x - 2;
			shapey[4] = y + 3;
			
			shapex[5] = x + 2;
			shapey[5] = y + 3;
		}
	}
	
	/*
	 *  Metodo <I>getScore</I> de la clase <code>FlyingSaucer</code>. 
	 *  Regresa la cantidad de puntos que otorga el ovni.
	 */
	public int getScore() { return score; }

	/*
	* Metodo <I>shouldRemove()</I> de la clase <code>FlyingSaucer</code>.
	* Regresa un tipo de dato <code>boolean</code> que dice si se 
	* debe de borrar el objeto.
	*/
	public boolean shouldRemove() { return remove; }
	
	/*
	 * Metodo <I>update</I> de la clase <code>FlyingSaucer</code>.
	 * Actualiza el objeto dependiendo del valor de dt que reciba.
	 * 
	 * @paramdt tipo de dato <code>float</code> que modifica la posicion
	 * del objeto dependiendo de dt.
	 */
	public void update(float dt) {
		
		// fire
		if(!player.isHit()) {
			fireTimer += dt;
			if(fireTimer > fireTime) {
				fireTimer = 0;
				if(type == LARGE) {
					radians = MathUtils.random(2 * 3.1416f);
				}
				else if(type == SMALL) {
					radians = MathUtils.atan2(
							player.getY() - y,
							player.getX() - x
							);
				}
				bullets.add(new Bullet(x, y, radians));
				Jukebox.play("saucershoot");
			}
		}
		
		// move along path
		pathTimer += dt;
		
			// move forward
		if(pathTimer < pathTime1) {
			dy = 0;
		}
		
			// move downward
		if(pathTimer > pathTime1 && pathTimer < pathTime2) {
			dy = -speed;
		}
		
			// move to end of screen
		if(pathTimer > pathTime1 && pathTimer > pathTime2) {
			dy = 0;
		}
		
		x += dx * dt;
		y += dy * dt;
		
		// screen wrap
		if(y < 0) y = Game.HEIGHT;
		
		// set shape
		setShape();
		
		// check if remove
		if((direction == RIGHT && x > Game.WIDTH) || 
			(direction == LEFT && x < 0)) {
				remove = true;
		}
		
	}
	
	/*
	 * Metodo <I>draw</I> de la clase <code>Asteroide</code>.
	 * Pinta el objeto usando ShapeRenderer sr
	 * 
	 * @paramsr tipo de dato <code>ShapeRenderer</code> que es
	 * lo que se pinta.
	 */
	public void draw(ShapeRenderer sr) {
		
		sr.setColor(.1f, 1, 0, 1);
		sr.begin(ShapeType.Line);
		for(int i = 0, j = shapex.length - 1; i < shapex.length; j = i++){
			sr.line(shapex[i], shapey[i], shapex[j], shapey[j]);
		}
		
		sr.line(shapex[0], shapey[0], shapex[3], shapey[3]);
		sr.end();
		
	}

}
