package com.me.spaceassault.InfiniteJumper;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactFilter;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Player extends InputAdapter implements ContactFilter, ContactListener {

	private Body body;
	private Fixture fixture;
	public final float WIDTH, HEIGHT;
	private Vector2 velocity = new Vector2();
	private float movementForce = 100, jumpPower = 13;
	private boolean space=false;
	/**
     * Metodo <I>Player</I>, constructor de la clase <code>Player</code>, En este
     * metodo se inicializan todos los atributos de la clase.
     * @paramworld del tipo de dato <code>World</code> es el objeto que controla
     * el mundo.
     * @paramx del tipo de dato <code>float</code> es el la posicion en x del objeto.
     * @paramy del tipo de dato <code>float</code> es la posicion en y del objeto.
     * @paramwidth del tipo de dato <code>float</code> es la anchura del objeto.
     */
	public Player(World world, float x, float y, float width) {
		this.WIDTH = width;
		HEIGHT = width * 2;

		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(x, y);
		bodyDef.fixedRotation = true;

		CircleShape shape = new CircleShape();
		shape.setRadius(.5f);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.restitution = 0;
		fixtureDef.friction = .8f;
		fixtureDef.density = 3;

		body = world.createBody(bodyDef);
		fixture = body.createFixture(fixtureDef);
	}
	/**
     * Metodo <I>update</I> de la clase <code>Player</code>, En este
     * metodo actualiza el objeto.
     */
	public void update() {
		body.applyForceToCenter(velocity, true);
	}
	/**
     * Metodo <I>shouldCollide</I> de la clase <code>Player</code>, En este
     * metodo se hace cargo de ver si el objeto debe colisionar.
     * @paramfixtureA del tipo de dato <code>Fixture</code> este parametro 
     * contiene la fixture A para ver si colisiona con el parametro fixtureB.
     * @paramfixtureB del tipo de dato <code>Fixture</code> este parametro 
     * contiene la fixture B para ver si colisiona con el parametro fixtureA.
     */
	@Override
	public boolean shouldCollide(Fixture fixtureA, Fixture fixtureB) {
		if(fixtureA == fixture || fixtureB == fixture)
			return body.getLinearVelocity().y < 0;
		return false;
	}
	/**
     * Metodo <I>beginContact</I> de la clase <code>Player</code>, En este
     * metodo se hace cargo de actualizar en el momento del contacto.
     * @paramcontact del tipo de dato <code>Contact</code> define el contacto
     * generado.
     */
	@Override
	public void beginContact(Contact contact) {
	}
	
	/**
     * Metodo <I>preSolve</I> de la clase <code>Player</code>, En este
     * metodo se encarga de resolver pre contacto.
     * @paramcontact del tipo de dato <code>Contact</code> define el contacto
     * generado
     * @paramoldManifold del tipo de dato<code>Manifold</code> define previa solucion a
     * la colision.
     */
	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
	}
	/**
     * Metodo <I>postSolve</I> de la clase <code>GameOver</code>, En este
     * metodo se hace dispose para limpiar los contenedores utilizados en la clase.
     * @paramcontact del tipo de dato <code>Contact</code> define el contacto generado.
     * @paramimpulse del tipo de dato <code>ContactImpulse</code> define el impulso generado
     * por el contacto.
     */
	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		if(contact.getFixtureA() == fixture || contact.getFixtureB() == fixture){
			body.applyLinearImpulse(0, jumpPower, body.getWorldCenter().x, body.getWorldCenter().y, true);
		}

	}
	/**
     * Metodo <I>endContact</I> de la clase <code>Player</code>, En este
     * metodo se define el final del contacto.
     */
	@Override
	public void endContact(Contact contact) {
	}
	
	/**
     * Metodo <I>keyDown</I> de la clase <code>Player</code>, En este
     * metodo se recibe la tecla oprimida y se actualiza la posicion del jugador.
     * @paramkeycode tipo de dato <code>Entero</code> es el valor utilizado para
     * manejar la tecla oprimida.
     */
	@Override
	public boolean keyDown(int keycode) {
		switch(keycode) {
		case Keys.LEFT:
			velocity.x = -movementForce;
			break;
		case Keys.RIGHT:
			velocity.x = movementForce;
			break;
		// TODO remove this case
		case Keys.SPACE:
			if(!space){
			body.applyLinearImpulse(0, jumpPower*5, body.getWorldCenter().x, body.getWorldCenter().y, true);
			space=true;
			}
			break;

			
		default:
			return false;
		}
		return true;
	}
	
	/**
	 * Lo que hace al tocar la pantalla
	 */
	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		if (!Gdx.app.getType().equals(ApplicationType.Android)) {
			return false;
		}
		if (x < Gdx.graphics.getWidth()) {
			if(!space){
				body.applyLinearImpulse(0, jumpPower*5, body.getWorldCenter().x, body.getWorldCenter().y, true);
				space=true;
			}
			velocity.x = -movementForce;
		} else if (x > Gdx.graphics.getWidth()) {
			if(!space){
				body.applyLinearImpulse(0, jumpPower*5, body.getWorldCenter().x, body.getWorldCenter().y, true);
				space=true;
			}
			velocity.x = movementForce;
		} 
		return true;
	}

	/**
	 * Lo que hace al dejar de tocar la pantalla
	 */
	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		if (!Gdx.app.getType().equals(ApplicationType.Android)) {
			return false;
		}
		if (x < Gdx.graphics.getWidth()) {
			velocity.x = 0;
		} else if (x > Gdx.graphics.getWidth()) {
			velocity.x=0;
		} 
		
		return true;
	}
	
	
	/**
     * Metodo <I>keyUp</I> de la clase <code>Player</code>, En este
     * metodo se recibe la tecla oprimida liberada
     * @paramkeycode tipo de dato <code>Entero</code> es el valor utilizado para
     * manejar la tecla liberada.
     */
	@Override
	public boolean keyUp(int keycode) {
		if(keycode == Keys.LEFT || keycode == Keys.RIGHT)
			velocity.x = 0;
		else
			return false;
		return true;
	}
	/**
     * Metodo <I>getRestitution</I> de la clase <code>Player</code>, En este
     * metodo se regresa el valor de la restitucion del objeto.
     */
	public float getRestitution() {
		return fixture.getRestitution();
	}
	/**
     * Metodo <I>setRestitution</I> de la clase <code>Player</code>, En este
     * metodo se modifica el valor de la restitucion del objeto.
     * @paramrestitution del tipo de dato <code>float</code> el cual contiene
     * la nueva restitucion para el objeto.
     */
	public void setRestitution(float restitution) {
		fixture.setRestitution(restitution);
	}
	/**
     * Metodo <I>getBody</I> de la clase <code>Player</code>, En este
     * metodo se regresa el cuerpo del objeto.
     */
	public Body getBody() {
		return body;
	}
	/**
     * Metodo <I>getFixture</I> de la clase <code>Player</code>, En este
     * metodo se regresa el fixture del objeto.
     */
	public Fixture getFixture() {
		return fixture;
	}

}  