package com.me.spaceassault.tween;
import aurelienribon.tweenengine.TweenAccessor;
import com.badlogic.gdx.graphics.g2d.Sprite;
/**
 * Clase para modificar y accesar los Sprites
 * @author AndresG
 *
 */
public class SpriteAccessor implements TweenAccessor<Sprite> {

public static final int ALPHA = 0;

	
	/**
	 * Obtiene los valores para accesar el sprite
	 */
	@Override
	public int getValues(Sprite target, int tweenType, float[] returnValues) {
		switch(tweenType){
		case ALPHA:
			returnValues[0] = target.getColor().a;
			return 1;
		default:
			assert false;
			return -1;
		}
	}

	/**
	 * Actaliza los valores de los sprites
	 */
	@Override
	public void setValues(Sprite target, int tweenType, float[] newValues) {
		// TODO Auto-generated method stub
		switch(tweenType){
		case ALPHA:
			target.setColor(target.getColor().r,target.getColor().g,target.getColor().b,newValues[0]);
			break;
		default:
			assert false;
			
		}
	}

}
